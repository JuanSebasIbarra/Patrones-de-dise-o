package ui.impl;

import ui.Interfaces.PackageRepository;
import model.VacationPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryPackageRepository implements PackageRepository {
    private final List<VacationPackage> store = new ArrayList<>();

    @Override
    public List<VacationPackage> findAll() {
        return new ArrayList<>(store);
    }

    @Override
    public VacationPackage find(int index) {
        return store.get(index);
    }

    @Override
    public void save(VacationPackage pkg) {
        store.add(pkg);
    }

    @Override
    public void update(int index, VacationPackage pkg) {
        store.set(index, pkg);
    }

    @Override
    public void delete(int index) {
        store.remove(index);
    }

    @Override
    public void clear() {
        store.clear();
    }
}
