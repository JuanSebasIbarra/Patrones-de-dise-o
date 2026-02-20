package ui;

import java.io.IOException;
import java.util.List;
import model.VacationPackage;


public class Interfaces {
    // Este archivo agrupa las interfaces como clases anidadas estáticas para fácil importación
    // Alternativamente pueden extraerse a archivos separados si lo prefieres.


    public static interface PackageRepository {
        List<VacationPackage> findAll();
        VacationPackage find(int index);
        void save(VacationPackage pkg);
        void update(int index, VacationPackage pkg);
        void delete(int index);
        void clear();
    }


    public static interface PackageService {
        List<VacationPackage> getAllPackages();
        void addPackage(VacationPackage pkg);
        void updatePackage(int index, VacationPackage pkg);
        void removePackage(int index);
        VacationPackage clonePackage(int index);
        void clearAll();
    }


    public static interface PackageView {
        void setController(PackageController controller);
        void displayPackages(List<VacationPackage> packages);
        void displayPackageDetails(VacationPackage pkg);
        void showError(String message);
        void showInfo(String message);
    }


    public static interface PackageFormView {

        VacationPackage collectPackage() throws IllegalStateException;
        void loadPackage(VacationPackage pkg);
        void clearForm();
    }


    public static interface PackageController {
        void onNew();
        void onSave(Integer selectedIndex);
        void onDelete(Integer selectedIndex);
        void onClone(Integer selectedIndex);
        void onSelect(Integer selectedIndex);
        void onLoadAll();
    }

    public static interface PackagePersistence {
        void saveToFile(String path, List<VacationPackage> packages) throws IOException;
        List<VacationPackage> loadFromFile(String path) throws IOException;
    }
}
