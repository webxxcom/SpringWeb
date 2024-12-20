package ua.nure.st.kpp.example.demo.dao;

import ua.nure.st.kpp.example.demo.dao.abstr.ConnectionManager;
import ua.nure.st.kpp.example.demo.dao.abstr.CustomerDAO;

public interface DAOFactory {
    CustomerDAO getCustomerDAO(ConnectionManager cm);
    ConnectionManager getConnectionManager(MySqlConfig config);
}
