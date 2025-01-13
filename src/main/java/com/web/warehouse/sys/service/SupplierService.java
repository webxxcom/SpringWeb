package com.web.warehouse.sys.service;

import com.web.warehouse.sys.db.abstr.SupplierDAO;
import com.web.warehouse.sys.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService implements EntityService<Supplier> {

    private final SupplierDAO supplierDAO;

    @Autowired
    public SupplierService(SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
    }

    @Override
    public Optional<Supplier> get(long id) {
        return supplierDAO.getById(id);
    }

    @Override
    public List<Supplier> getAll() {
        return supplierDAO.getAll();
    }

    @Override
    public void add(Supplier supplier) {
        supplierDAO.add(supplier);
    }

    @Override
    public void addAll(Supplier... suppliers) {
        supplierDAO.addAll(suppliers);
    }

    @Override
    public void remove(long id) {
        supplierDAO.remove(id);
    }

    @Override
    public void update(Supplier updated) {
        supplierDAO.update(updated);
    }
}
