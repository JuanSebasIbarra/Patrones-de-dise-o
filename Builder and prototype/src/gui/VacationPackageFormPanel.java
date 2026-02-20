package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import ui.Interfaces.PackageFormView;
import model.VacationPackage;

public class VacationPackageFormPanel extends JPanel implements PackageFormView {

    private JTextField txtDestination;
    private JSpinner spnDays;
    private JTextField txtHotel;
    private JCheckBox chkFlight;
    private DefaultListModel<String> activitiesModel;
    private JList<String> activitiesList;
    private JTextField txtNewActivity;

    // Image UI
    private JLabel lblImagePreview;
    private JButton btnChooseImage;
    private String selectedImagePath = null;

    public VacationPackageFormPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Destination
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(new JLabel("Destino:"), gbc);
        txtDestination = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = row++; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(txtDestination, gbc);

        // Days
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        form.add(new JLabel("Días:"), gbc);
        spnDays = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));
        gbc.gridx = 1; gbc.gridy = row++; gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(spnDays, gbc);

        // Hotel
        gbc.gridx = 0; gbc.gridy = row; gbc.fill = GridBagConstraints.NONE;
        form.add(new JLabel("Hotel:"), gbc);
        txtHotel = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = row++; gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(txtHotel, gbc);

        // Flight
        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Vuelo incluido:"), gbc);
        chkFlight = new JCheckBox();
        gbc.gridx = 1; gbc.gridy = row++; gbc.fill = GridBagConstraints.NONE;
        form.add(chkFlight, gbc);

        // Image chooser & preview
        gbc.gridx = 0; gbc.gridy = row; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.NORTHWEST;
        form.add(new JLabel("Imagen:"), gbc);
        JPanel imgPanel = new JPanel(new BorderLayout());
        lblImagePreview = new JLabel();
        lblImagePreview.setPreferredSize(new Dimension(180, 120));
        lblImagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblImagePreview.setHorizontalAlignment(SwingConstants.CENTER);
        btnChooseImage = new JButton("Seleccionar imagen");
        imgPanel.add(lblImagePreview, BorderLayout.CENTER);
        imgPanel.add(btnChooseImage, BorderLayout.SOUTH);
        gbc.gridx = 1; gbc.gridy = row++; gbc.fill = GridBagConstraints.BOTH;
        form.add(imgPanel, gbc);

        btnChooseImage.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int res = chooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                selectedImagePath = f.getAbsolutePath();
                updateImagePreview(selectedImagePath);
            }
        });

        // Activities
        gbc.gridx = 0; gbc.gridy = row; gbc.anchor = GridBagConstraints.NORTHWEST; gbc.fill = GridBagConstraints.NONE;
        form.add(new JLabel("Actividades:"), gbc);
        activitiesModel = new DefaultListModel<>();
        activitiesList = new JList<>(activitiesModel);
        JScrollPane activitiesScroll = new JScrollPane(activitiesList);
        activitiesScroll.setPreferredSize(new Dimension(200, 100));
        gbc.gridx = 1; gbc.gridy = row++; gbc.fill = GridBagConstraints.BOTH;
        form.add(activitiesScroll, gbc);

        // Add activity
        JPanel addAct = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtNewActivity = new JTextField(12);
        JButton btnAddAct = new JButton("Añadir");
        JButton btnRemoveAct = new JButton("Quitar");
        addAct.add(txtNewActivity);
        addAct.add(btnAddAct);
        addAct.add(btnRemoveAct);

        gbc.gridx = 1; gbc.gridy = row++; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.WEST;
        form.add(addAct, gbc);

        btnAddAct.addActionListener(e -> {
            String a = txtNewActivity.getText().trim();
            if (!a.isEmpty()) {
                activitiesModel.addElement(a);
                txtNewActivity.setText("");
            }
        });

        btnRemoveAct.addActionListener(e -> {
            int idx = activitiesList.getSelectedIndex();
            if (idx >= 0) activitiesModel.remove(idx);
        });

        add(form, BorderLayout.NORTH);
    }

    private void updateImagePreview(String path) {
        if (path == null) {
            lblImagePreview.setIcon(null);
            lblImagePreview.setText("No image");
            return;
        }
        try {
            ImageIcon icon = new ImageIcon(path);
            Image img = icon.getImage();
            Image scaled = img.getScaledInstance(lblImagePreview.getWidth(), lblImagePreview.getHeight(), Image.SCALE_SMOOTH);
            lblImagePreview.setIcon(new ImageIcon(scaled));
            lblImagePreview.setText("");
        } catch (Exception e) {
            lblImagePreview.setIcon(null);
            lblImagePreview.setText("No se puede cargar");
        }
    }

    @Override
    public void clearForm() {
        txtDestination.setText("");
        spnDays.setValue(1);
        txtHotel.setText("");
        chkFlight.setSelected(false);
        activitiesModel.clear();
        selectedImagePath = null;
        updateImagePreview(null);
    }

    @Override
    public void loadPackage(VacationPackage vp) {
        txtDestination.setText(vp.getDestination());
        spnDays.setValue(vp.getDays());
        txtHotel.setText(vp.getHotel());
        chkFlight.setSelected(vp.isFlightIncluded());
        activitiesModel.clear();
        for (String a : vp.getActivities()) activitiesModel.addElement(a);
        selectedImagePath = vp.getImagePath();
        updateImagePreview(selectedImagePath);
    }

    @Override
    public VacationPackage collectPackage() throws IllegalStateException {
        String dest = txtDestination.getText().trim();
        int days = (Integer) spnDays.getValue();
        String hotel = txtHotel.getText().trim();
        boolean flight = chkFlight.isSelected();
        List<String> acts = new ArrayList<>();
        for (int i = 0; i < activitiesModel.getSize(); i++) {
            acts.add(activitiesModel.getElementAt(i));
        }

        VacationPackage.Builder b = new VacationPackage.Builder()
                .setDestination(dest)
                .setDays(days)
                .setHotel(hotel)
                .setFlightIncluded(flight)
                .addActivities(acts)
                .setImagePath(selectedImagePath);
        return b.build();
    }
}
