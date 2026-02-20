import model.*;

public class Main {

    public static void main(String[] args) {
        boolean launchGui = true; // Default: mostrar GUI
        for (String a : args) {
            if ("--cli".equals(a)) {
                launchGui = false;
                break;
            }
            if ("--gui".equals(a)) {
                launchGui = true;
                break;
            }
        }

        if (launchGui) {

            try {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            // Lanzar la interfaz gráfica usando Swing en el Event Dispatch Thread
            javax.swing.SwingUtilities.invokeLater(() -> {
                gui.MainWindow window = new gui.MainWindow();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            });
            return;
        }

        // Modo consola (Builder + Prototype demo)
        VacationPackage parisTrip = new VacationPackage.Builder()
                .setDestination("Paris")
                .setDays(7)
                .setHotel("Hotel 4 estrellas")
                .setFlightIncluded(true)
                .addActivity("Tour Torre Eiffel")
                .addActivity("Museo del Louvre")
                .build();

        System.out.println("Paquete Original:");
        System.out.println(parisTrip);

        // Clone Prototype
        TravelPackage clone = parisTrip.clone();

        System.out.println("\nClon del Paquete:");
        System.out.println(clone);

        // Create variety
        VacationPackage luxuryParis = new VacationPackage.Builder()
                .setDestination(parisTrip.getDestination())
                .setDays(10)
                .setHotel("Hotel 5 estrellas lujo")
                .setFlightIncluded(true)
                .addActivities(parisTrip.getActivities())
                .addActivity("Cena en restaurante Michelin")
                .build();

        System.out.println("\nVersión de Lujo:");
        System.out.println(luxuryParis);
    }
}