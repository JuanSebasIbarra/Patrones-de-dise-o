package ui.impl;

import ui.Interfaces.PackageRepository;
import ui.Interfaces.PackageService;
import model.VacationPackage;

import java.util.List;

public class SimplePackageService implements PackageService {
    private final PackageRepository repo;

    public SimplePackageService(PackageRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<VacationPackage> getAllPackages() {
        return repo.findAll();
    }

    @Override
    public void addPackage(VacationPackage pkg) {
        repo.save(pkg);
    }

    @Override
    public void updatePackage(int index, VacationPackage pkg) {
        repo.update(index, pkg);
    }

    @Override
    public void removePackage(int index) {
        repo.delete(index);
    }

    @Override
    public VacationPackage clonePackage(int index) {
        VacationPackage original = repo.find(index);
        return (VacationPackage) original.clone();
    }

    @Override
    public void clearAll() {
        repo.clear();
    }
}
