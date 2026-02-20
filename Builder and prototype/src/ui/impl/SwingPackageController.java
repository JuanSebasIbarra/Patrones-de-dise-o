package ui.impl;

import ui.Interfaces.PackageController;
import ui.Interfaces.PackageFormView;
import ui.Interfaces.PackageView;
import ui.Interfaces.PackageService;
import model.VacationPackage;

import javax.swing.*;
import java.util.List;

public class SwingPackageController implements PackageController {
    private final PackageService service;
    private final PackageView view;
    private final PackageFormView form;

    public SwingPackageController(PackageService service, PackageView view, PackageFormView form) {
        this.service = service;
        this.view = view;
        this.form = form;
        this.view.setController(this);
    }

    @Override
    public void onNew() {
        form.clearForm();
    }

    @Override
    public void onSave(Integer selectedIndex) {
        try {
            VacationPackage vp = form.collectPackage();
            if (selectedIndex != null && selectedIndex >= 0) {
                service.updatePackage(selectedIndex, vp);
            } else {
                service.addPackage(vp);
            }
            onLoadAll();
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void onDelete(Integer selectedIndex) {
        if (selectedIndex != null && selectedIndex >= 0) {
            service.removePackage(selectedIndex);
            onLoadAll();
            form.clearForm();
        }
    }

    @Override
    public void onClone(Integer selectedIndex) {
        if (selectedIndex != null && selectedIndex >= 0) {
            VacationPackage cloned = service.clonePackage(selectedIndex);
            service.addPackage(cloned);
            onLoadAll();
        }
    }

    @Override
    public void onSelect(Integer selectedIndex) {
        if (selectedIndex != null && selectedIndex >= 0) {
            VacationPackage vp = service.getAllPackages().get(selectedIndex);
            form.loadPackage(vp);
        }
    }

    @Override
    public void onLoadAll() {
        List<VacationPackage> all = service.getAllPackages();
        view.displayPackages(all);
    }
}
