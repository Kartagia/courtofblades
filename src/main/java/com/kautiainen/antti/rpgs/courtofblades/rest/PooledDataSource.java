package com.kautiainen.antti.rpgs.courtofblades.rest;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Database pooling data source using DBCP 
 */
public class PooledDataSource extends DBDataSource {

	/** The data source used for pooling. */
	private BasicDataSource ds = new BasicDataSource();

	public PooledDataSource(String dbProtocol) {
		this(dbProtocol, null);
	}

	public PooledDataSource(String dbProtocol,
			String hostName, Integer port, String database, String userName, String password) {
		this(dbProtocol, null, hostName, port, database, userName, password);
	}

	public PooledDataSource(String dbProtocol, String driverClass) {
		this(dbProtocol, driverClass, null, null, null, null, null);
	}

	public PooledDataSource(String dbProtocol, String driverClass, 
	String hostName, Integer port, String database, String userName, String password) {
		super(dbProtocol, driverClass, hostName, port, database, null, null);
		int minIdle = 5, maxIdle = 10, maxOpenPreparedStatements = 100;
		ds.setUrl(getJDBCUrl());
		ds.setUsername(userName);
		ds.setPassword(password);
		ds.setMinIdle(minIdle);
		ds.setMaxIdle(maxIdle);
		ds.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
	}

	@Override
	protected void setHostName(String hostName) throws IllegalArgumentException, UnsupportedOperationException {
		super.setHostName(hostName);
		ds.setUrl(getJDBCUrl());
	}

	@Override
	protected void setPassword(String password) throws IllegalArgumentException, UnsupportedOperationException {
		ds.setPassword(password);
	}

	@Override
	protected void setPort(Integer port) throws IllegalArgumentException, UnsupportedOperationException {
		super.setPort(port);
		ds.setUrl(getJDBCUrl());
	}

	@Override
	protected void setProtocol(String protocol) throws IllegalArgumentException, UnsupportedOperationException {
		super.setProtocol(protocol);
		ds.setUrl(getJDBCUrl());
	}

	@Override
	protected void setUsername(String userName) throws IllegalArgumentException, UnsupportedOperationException {
		ds.setUsername(userName);
	}

	public int getMinIdle() {
		return ds.getMinIdle();
	}

	public int getMaxIdle() {
		return ds.getMaxIdle();
	}

	public int getMaxOpenPreparedStatements() {
		return ds.getMaxOpenPreparedStatements();
	}

	protected void setMaxOpenPreparedStatements(int maxOpenStatements) throws IllegalArgumentException, UnsupportedOperationException {
		ds.setMaxOpenPreparedStatements(maxOpenStatements);
	}

	protected void setMaxIdle(int maxIdle) throws IllegalArgumentException, UnsupportedOperationException {
		ds.setMaxIdle(maxIdle);
	} 

	protected void setMinIdle(int minInde) throws IllegalArgumentException, UnsupportedOperationException {
		ds.setMinIdle(minInde);
	}

	/**
	 * Get connection.
	 * @return The connection to the database.
	 * @throws SQLException The connection was not established.
	 */
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

}