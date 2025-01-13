package com.web.warehouse.sys.db;

import com.web.warehouse.sys.db.abstr.ConnectionManager;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class TransactionManager {
    private final ConnectionManager cm;

    public TransactionManager(ConnectionManager connectionSource) {
        this.cm = connectionSource;
    }

    public void executeTransaction(TransactionCallback callback) {
        try (Connection con = cm.getConnection()) {
            tryExecute(callback, con);
        } catch (SQLException e) {
            throw new TransactionFailedException("Error managing transaction", e);
        }
    }

    private void tryExecute(TransactionCallback callback, Connection con) throws SQLException {
        try {
            ConnectionManager.beginTransaction(con);

            callback.doInTransaction(con);

            ConnectionManager.endTransaction(con);
        } catch (RuntimeException ex) {
            ConnectionManager.rollback(con);
            throw ex;
        }
    }
}
