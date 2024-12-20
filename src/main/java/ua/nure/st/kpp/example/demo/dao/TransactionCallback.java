package ua.nure.st.kpp.example.demo.dao;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionCallback {
    void doInTransaction(Connection connection) throws SQLException;
}

