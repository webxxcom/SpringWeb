package ua.nure.st.kpp.example.demo.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.st.kpp.example.demo.dao.*;
import ua.nure.st.kpp.example.demo.dao.abstr.*;
import ua.nure.st.kpp.example.demo.entity.*;

import java.util.*;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    public static final int NUMBER_OF_SUPPLIERS = 5;
    public static final int NUMBER_OF_CUSTOMERS = 4;
    public static final int NUMBER_OF_PRODUCTS = 8;

    /* DAOs */
    private final AllProductsDAO allProductsDAO;
    private final CustomerDAO customerDAO;
    private final SupplierDAO supplierDAO;

    @Autowired
    public WarehouseServiceImpl(DAOFactory mySqlDaoFactory, ConnectionManager cm) {
        Objects.requireNonNull(mySqlDaoFactory, "DAO factory cannot be null");

        this.allProductsDAO = mySqlDaoFactory.getAllProductsDAO(cm);
        this.customerDAO = mySqlDaoFactory.getCustomerDAO(cm);
        this.supplierDAO = mySqlDaoFactory.getSupplierDAO(cm);
    }

    public Optional<Product> getProduct(long id){
        return allProductsDAO.getById(id);
    }

    public Optional<Customer> getCustomer(long id){
        return customerDAO.getById(id);
    }

    public Optional<Supplier> getSupplier(long id){
        return supplierDAO.getById(id);
    }

    public long getEmptyProducts(){
        return allProductsDAO.getEmpty();
    }

    public void addSuppliers(Supplier... suppliers){
        addEntitiesTo(supplierDAO, suppliers);
    }

    public void addCustomers(Customer... customers){
        addEntitiesTo(customerDAO, customers);
    }

    public void addProducts(Product... products){
        allProductsDAO.insertAll(products);
    }

    private static <T extends Entity> void addEntitiesTo(EntityDAO<T> entityDAO, T[] entities){
        Objects.requireNonNull(entities);
        if(Arrays.stream(entities).anyMatch(Objects::isNull))
            throw new IllegalArgumentException("Null main.entity cannot be added to the database");

        entityDAO.insertAll(entities);
    }

    public void updateProductQuantity(long productId, int quantity){
        allProductsDAO.updateQuantity(productId, quantity);
    }

    public void addProductToCustomerCart(long customerId, ProductDescription prd) {
        Objects.requireNonNull(prd);

        customerDAO.addToCart(customerId, prd);
    }

    public void removeFromCustomerCart(long customerId, long productId){
        customerDAO.removeProductFromCart(customerId, productId);
    }

    public void addProductToCustomerWishlist(long customerId, ProductDescription prd) {
        customerDAO.addToWishList(customerId, prd);
    }

    public void removeFromCustomerWishlist(long customerId, long productId){
        customerDAO.removeProductFromWishlist(customerId, productId);
    }

    public void removeSupplier(long id){
        supplierDAO.delete(id);
    }

    public void removeCustomer(long id) {
        customerDAO.delete(id);
    }

    public void removeAvailableProduct(long id){
        allProductsDAO.delete(id);
    }

    public void editAvailableProduct(long id, Product editedProduct){
        allProductsDAO.edit(id, editedProduct);
    }

    public List<Product> getAllProducts(){
        return allProductsDAO.getAll();
    }

    public List<Customer> getCustomers(){
        return customerDAO.getAll();
    }

    public List<Supplier> getSuppliers(){
        return supplierDAO.getAll();
    }
}
