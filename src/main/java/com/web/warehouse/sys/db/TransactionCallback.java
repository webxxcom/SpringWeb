package com.web.warehouse.sys.db;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionCallback {
    void doInTransaction(Connection connection) throws SQLException;
}

