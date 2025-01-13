package com.web.warehouse.sys.service;

import com.web.warehouse.sys.db.abstr.AllProductsDAO;
import com.web.warehouse.sys.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements EntityService<Product> {

    private final AllProductsDAO allProductsDAO;

    public ProductService(AllProductsDAO allProductsDAO) {
        this.allProductsDAO = allProductsDAO;
    }

    @Override
    public Optional<Product> get(long id) {
        return allProductsDAO.getById(id);
    }

    @Override
    public List<Product> getAll() {
        return allProductsDAO.getAll();
    }

    @Override
    public void add(Product product) {
        allProductsDAO.add(product);
    }

    @Override
    public void addAll(Product... products) {
        allProductsDAO.addAll(products);
    }

    @Override
    public void remove(long id) {
        allProductsDAO.remove(id);
    }

    @Override
    public void update(Product updated) {
        allProductsDAO.update(updated);
    }
}
