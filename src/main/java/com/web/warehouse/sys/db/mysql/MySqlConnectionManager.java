package com.web.warehouse.sys.db.mysql;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.web.warehouse.sys.db.abstr.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class MySqlConnectionManager implements ConnectionManager {
    final MySqlConfig config;
    private final MysqlConnectionPoolDataSource dataSource;

    public MySqlConnectionManager(MySqlConfig config) {
        this.config = config;
        dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setUrl(config.getUrl());
        dataSource.setUser(config.getUser());
        dataSource.setPassword(config.getPassword());
    }

    @Override
    public Connection getConnection() {
        return getConnection(true, Connection.TRANSACTION_READ_COMMITTED);
    }

    @Override
    public Connection getConnection(boolean autoCommit) {
        return getConnection(autoCommit, Connection.TRANSACTION_READ_COMMITTED);
    }

    public Connection getConnection(boolean autoCommit, int transactionLevel)
            throws ConnectionException {
        try {
            Connection con = dataSource.getConnection();
            con.setAutoCommit(autoCommit);
            con.setTransactionIsolation(transactionLevel);

            return con;
        } catch (SQLException e) {
            throw new ConnectionException("Error while initializing Connection", e);
        }
    }
}
