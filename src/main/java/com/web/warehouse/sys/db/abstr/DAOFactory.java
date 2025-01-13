package com.web.warehouse.sys.db.abstr;

import com.web.warehouse.sys.db.TransactionManager;
import com.web.warehouse.sys.db.mysql.MySqlConfig;

public interface DAOFactory {
    CustomerDAO getCustomerDAO(ConnectionManager cm, TransactionManager tm);
    SupplierDAO getSupplierDAO(ConnectionManager cm, TransactionManager tm);
    AllProductsDAO getAllProductsDAO(ConnectionManager cm, TransactionManager tm);
    ConnectionManager getConnectionManager(MySqlConfig config);
}
