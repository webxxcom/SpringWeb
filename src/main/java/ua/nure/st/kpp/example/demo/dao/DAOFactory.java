package ua.nure.st.kpp.example.demo.dao;

import ua.nure.st.kpp.example.demo.dao.abstr.*;

public interface DAOFactory {
    CustomerDAO getCustomerDAO(ConnectionManager cm);
    SupplierDAO getSupplierDAO(ConnectionManager cm);
    AllProductsDAO getAllProductsDAO(ConnectionManager cm);
    ConnectionManager getConnectionManager(MySqlConfig config);
}
