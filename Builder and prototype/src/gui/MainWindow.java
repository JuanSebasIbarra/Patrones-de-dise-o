package gui;

import ui.Interfaces.PackageController;
import ui.Interfaces.PackageView;
import ui.impl.InMemoryPackageRepository;
import ui.impl.SimplePackageService;
import ui.impl.SwingPackageController;
import ui.Interfaces.PackageFormView;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.VacationPackage;

public class MainWindow extends JFrame implements PackageView {

    private DefaultListModel<VacationPackage> listModel;
    private JList<VacationPackage> packageList;
    private VacationPackageFormPanel formPanel;

    private PackageController controller;

    public MainWindow() {
        super("Vacation Packages");
        initComponents();
        setupController();
        controller.onLoadAll();
    }

    private void setupController() {
        InMemoryPackageRepository repo = new InMemoryPackageRepository();
        SimplePackageService service = new SimplePackageService(repo);
        controller = new SwingPackageController(service, this, (PackageFormView) formPanel);

        // add a sample package if empty
        if (service.getAllPackages().isEmpty()) {
            List<String> activities = new java.util.ArrayList<>();
            activities.add("Tour Torre Eiffel");
            activities.add("Museo del Louvre");
            model.VacationPackage vp = new model.VacationPackage.Builder()
                    .setDestination("Paris")
                    .setDays(7)
                    .setHotel("Hotel 4 estrellas")
                    .setFlightIncluded(true)
                    .addActivities(activities)
                    .build();
            service.addPackage(vp);
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);

        listModel = new DefaultListModel<>();
        packageList = new JList<>(listModel);
        packageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Mostrar icono y texto en cada celda (miniatura)
        packageList.setCellRenderer(new ListCellRenderer<VacationPackage>() {
            private final JLabel renderer = new JLabel();
            @Override
            public Component getListCellRendererComponent(JList<? extends VacationPackage> list, VacationPackage value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value == null) return renderer;
                renderer.setOpaque(true);
                if (isSelected) {
                    renderer.setBackground(list.getSelectionBackground());
                    renderer.setForeground(list.getSelectionForeground());
                } else {
                    renderer.setBackground(list.getBackground());
                    renderer.setForeground(list.getForeground());
                }
                // Texto con destino y días
                String text = String.format("%s (%dd)", value.getDestination(), value.getDays());
                renderer.setText(text);
                // Cargar imagen si existe
                String img = value.getImagePath();
                if (img != null) {
                    try {
                        ImageIcon icon = new ImageIcon(img);
                        Image scaled = icon.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
                        renderer.setIcon(new ImageIcon(scaled));
                        renderer.setHorizontalTextPosition(SwingConstants.RIGHT);
                        renderer.setIconTextGap(12);
                    } catch (Exception e) {
                        renderer.setIcon(null);
                    }
                } else {
                    renderer.setIcon(null);
                }
                renderer.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
                return renderer;
            }
        });
        packageList.setFixedCellHeight(72);

        formPanel = new VacationPackageFormPanel();

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(packageList), formPanel);
        split.setDividerLocation(250);

        JPanel buttonPanel = new JPanel();
        JButton btnNew = new JButton("Nuevo");
        JButton btnSave = new JButton("Guardar");
        JButton btnClone = new JButton("Clonar");
        JButton btnDelete = new JButton("Borrar");

        buttonPanel.add(btnNew);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnClone);
        buttonPanel.add(btnDelete);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(split, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // List selection -> notify controller (proteger si controller es null)
        packageList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int idx = packageList.getSelectedIndex();
                if (controller != null) controller.onSelect(idx);
            }
        });

        btnNew.addActionListener(e -> {
            if (controller != null) controller.onNew();
        });

        btnSave.addActionListener(e -> {
            if (controller != null) controller.onSave(packageList.getSelectedIndex());
        });

        btnClone.addActionListener(e -> {
            if (controller != null) controller.onClone(packageList.getSelectedIndex());
        });

        btnDelete.addActionListener(e -> {
            if (controller != null) controller.onDelete(packageList.getSelectedIndex());
        });
    }

    @Override
    public void setController(PackageController controller) {
        this.controller = controller;
    }

    @Override
    public void displayPackages(List<VacationPackage> packages) {
        listModel.clear();
        for (VacationPackage p : packages) listModel.addElement(p);
    }

    @Override
    public void displayPackageDetails(VacationPackage pkg) {
        formPanel.loadPackage(pkg);
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
