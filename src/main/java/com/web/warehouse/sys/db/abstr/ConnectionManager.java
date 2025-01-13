package com.web.warehouse.sys.db.abstr;

import com.web.warehouse.sys.db.mysql.ConnectionException;
import com.web.warehouse.sys.db.mysql.InvalidConnectionException;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionManager {
	Connection getConnection();
	Connection getConnection(boolean autoCommit);
	Connection getConnection(boolean autoCommit, int transactionIsolation);

	static void requireValidConnection(Connection con) throws SQLException {
		if(con == null || con.isClosed())
			throw new InvalidConnectionException("Connections passed to ConnectionManager must not be null and not be closed");
	}

	static void beginTransaction(Connection con){
		try {
			requireValidConnection(con);

			con.setAutoCommit(false);
		}catch(SQLException ex){
			throw new ConnectionException("Error when trying to begin transaction", ex);
		}
	}

	static void endTransaction(Connection con){
		try{
			requireValidConnection(con);

			con.commit();
			con.setAutoCommit(true);
		} catch(SQLException ex){
			throw new ConnectionException("Error when trying to end the transaction", ex);
		}
	}

	static void rollback(Connection con) {
		try {
			requireValidConnection(con);

			con.rollback();
		}catch (SQLException ex){
			throw new ConnectionException("Error when trying to rollback in connection", ex);
		}
	}

	static void closeConnection(Connection con) throws ConnectionException {
		try {
			if (con != null && !con.isClosed())
				con.close();
		}catch (SQLException ex) {
			throw new ConnectionException("Error when closing connection", ex);
		}
	}
}
