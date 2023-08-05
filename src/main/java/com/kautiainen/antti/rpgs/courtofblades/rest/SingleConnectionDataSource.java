package com.kautiainen.antti.rpgs.courtofblades.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Basic data source not using connection pooling.
 */
public class SingleConnectionDataSource extends DBDataSource {

	public SingleConnectionDataSource(String dbProtocol, String driverClassName,
					String hostName, Integer port, String database, String userName, String password) {
		super(dbProtocol, driverClassName,hostName, port, database, userName, password);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(getJDBCUrl(), getUserName().orElse(null), getPassword().orElse(null));
	}

	
}