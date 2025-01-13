package com.web.warehouse.sys.db.mysql;

import com.web.warehouse.sys.db.TransactionManager;
import com.web.warehouse.sys.db.abstr.*;
import com.web.warehouse.sys.db.collectiondao.CustomerCollectionDAO;
import com.web.warehouse.sys.db.mysql.dao.MySqlAllProductsDAO;
import com.web.warehouse.sys.db.mysql.dao.MySqlCustomerDAO;
import com.web.warehouse.sys.db.mysql.dao.MySqlSupplierDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySqlDAOFactory implements DAOFactory {
    @Value("${database.type}")
    private String databaseType;

    @Override
    @Bean
    public CustomerDAO getCustomerDAO(ConnectionManager cm, TransactionManager tm) {
        switch (databaseType) {
            case "Collection":
                return new CustomerCollectionDAO();
            case "MySQL":
                return new MySqlCustomerDAO(cm, tm);
            default:
                throw new IllegalArgumentException("Unsupported database type: " + databaseType);
        }
    }

    @Override
    @Bean
    public SupplierDAO getSupplierDAO(ConnectionManager cm, TransactionManager tm) {
        return new MySqlSupplierDAO(cm, tm);
    }

    @Override
    @Bean
    public AllProductsDAO getAllProductsDAO(ConnectionManager cm, TransactionManager tm) {
        return new MySqlAllProductsDAO(cm, tm);
    }

    @Override
    @Bean
    public ConnectionManager getConnectionManager(MySqlConfig config) {
        return new MySqlConnectionManager(config);
    }
}
