package com.kautiainen.antti.rpgs.courtofblades.rest;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Abstract database data source.
 */
public abstract class DBDataSource {


	/**
	 * The protocol name.
	 */
	private String dbProtocol;

	/**
	 * The database of the connection.
	 */
	private String database;

	/**
	 * The host name of the connection.
	 */
	private String hostName;

	/**
	 * The port fo the connection.
	 */
	private Integer port; 

	/**
	 * The database user name.
	 */
	private String user;

	/**
	 * THe database user password.
	 */
	private String password;


	public DBDataSource(String protocolName) {
		this(protocolName, null);
	}

	public DBDataSource(String dbProtocol, String driverClassName) {
		this(dbProtocol, driverClassName, null, null, null, null, null);
	}



	public DBDataSource(String dbProtocol, String driverClass, 
	String hostName, Integer port, String database, String userName, String password)
	throws IllegalArgumentException {
		if (driverClass != null) {
			try {
				Class.forName(driverClass);
			} catch(ClassNotFoundException cnfe) {
				throw new IllegalArgumentException("Unsupported database type", cnfe);
			}
		}
		setProtocol(dbProtocol);
		setHostName(hostName);
		setPort(port);
		setUsername(userName);
		setPassword(password);
	}

	protected void setPassword(String password) throws IllegalArgumentException, UnsupportedOperationException {
		this.password = password;
	}

	protected void setUsername(String userName) throws IllegalArgumentException, UnsupportedOperationException {
		this.user = userName;
	}

	protected void setPort(Integer port) throws IllegalArgumentException, UnsupportedOperationException {
		this.port = port;
	}

	protected void setHostName(String hostName) throws IllegalArgumentException, UnsupportedOperationException {
		this.hostName = hostName;
	}

	protected void setProtocol(String protocol) throws IllegalArgumentException, UnsupportedOperationException {
		this.dbProtocol = protocol;
	}


	/**
	 * Get the JDBC Ulr of the connection.
	 * @return The JDCB url of the connection.
	 */
	protected String getJDBCUrl() {

		StringBuilder url = new StringBuilder("jdbc:");
		url.append(getProtocolString());
		url.append("//");
		url.append(getHostString());
		url.append("/");
		url.append(getDatabase());
		return url.toString();
	}

	/**
	 * Get the protocol string of the connection url.
	 * @return The protocol string unless the default protocol is used.
	 */
	protected Optional<String> getProtocolString() {
		return Optional.ofNullable((dbProtocol == null ? null : ":" + dbProtocol));
	}

	/**
	 * Get the database name. 
	 * @return The database name unless default database is used.
	 */
	protected Optional<String> getDatabase() {
		return Optional.ofNullable(this.database);
	}

	/**
	 * Get host name.
	 * @return The host name unless default host is used.
	 */
	protected Optional<String> getHostName() {
		return Optional.ofNullable(this.hostName);
	}

	/**
	 * Get the port of the database.
	 * @return The port of the database if non-standard port is used.
	 */
	protected Optional<Integer> getPort() {
		return Optional.ofNullable(this.port);
	}

	/**
	 * Get the host string.
	 * @eturn The host string of the url.
	 */
	protected String getHostString() {
		return getHostString(hostName, port);
	}

	/**
	 * Get host string of the url.
	 * @param hostName The host name.
	 * @param port The port.
	 * @return The host string of the given host name and port.
	 */
	protected String getHostString(String hostName, Integer port) {
		StringBuilder result = new StringBuilder();
		if (hostName != null) {
			result.append(hostName);
		}
		if (port != null) {
			result.append(":");
			result.append(port.toString());
		}
		return result.toString();

	}

	/**
	 * Get user name of the connection.
	 * @return The user name of the connection, unless default
	 * user is used.
	 */
	protected Optional<String> getUserName() {
		return Optional.ofNullable(user);
	}

	/**
	 * Get the password of the connection.
	 * @return The passwrod of the connection, if the
	 * user has password.
	 */
	protected Optional<String> getPassword() {
		return Optional.ofNullable(password);
	}


	/**
	 * Get connection.
	 * @return The connection to the database.
	 * @throws SQLException The connection was not established.
	 */
	public abstract Connection getConnection() throws SQLException;

}