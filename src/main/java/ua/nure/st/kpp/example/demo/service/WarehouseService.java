package ua.nure.st.kpp.example.demo.service;

import ua.nure.st.kpp.example.demo.entity.*;

import java.util.List;
import java.util.Optional;

public interface WarehouseService {
    List<Product> getAllProducts();
    List<Customer> getCustomers();
    List<Supplier> getSuppliers();

    Optional<Product> getProduct(long id);
    Optional<Customer> getCustomer(long id);
    Optional<Supplier> getSupplier(long id);
    long getEmptyProducts();

    void addSuppliers(Supplier... suppliers);
    void addCustomers(Customer... customers);
    void addProducts(Product... products);
    void addProductToCustomerCart(long customerId, ProductDescription prd);
    void addProductToCustomerWishlist(long customerId, ProductDescription prd);

    void removeSupplier(long id);
    void removeCustomer(long id);
    void removeAvailableProduct(long id);
    void removeFromCustomerCart(long customerId, long productId);
    void removeFromCustomerWishlist(long customerId, long productId);

    void editAvailableProduct(long id, Product editedProduct);
    void updateProductQuantity(long productId, int quantity);
}
