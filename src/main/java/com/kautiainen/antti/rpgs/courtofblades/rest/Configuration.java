package com.kautiainen.antti.rpgs.courtofblades.rest;

import java.util.Properties;

import org.restexpress.RestExpress;
import org.restexpress.util.Environment;

import static com.kautiainen.antti.rpgs.courtofblades.rest.Constants.Init.*;

public class Configuration
extends Environment
{


	//////////////////////////////////////////////////////////////////////////////////
	// The property values
	//////////////////////////////////////////////////////////////////////////////////

	/**
	 * The port of the server.
	 */
	private int port;

	/**
	 * The base URL of the server.
	 */
	private String baseUrl;

	/**
	 * The The executor thread pool size.
	 */
	private int executorThreadPoolSize;

	private String hostName;

	// TODO: Add property fields.

	//////////////////////////////////////////////////////////////////////////////////
	// The controller implementations
	//////////////////////////////////////////////////////////////////////////////////
	
	// TODO: Add the controller implementation instance fields

	protected DBDataSource getDatabaseSource(Properties p) {
		DBDataSource result = null;
		return result;
	}

	@Override
	protected void fillValues(Properties p)
	{

	}

	/**
	 * Initializa the configuration from the given properties.
	 * @param properties The properties from which the configuration is initialized.
	 */
	protected void initialize(Properties properties) {
		// Defining the server specific internal properties from the given properties.
		this.port = Integer.parseInt(properties.getProperty(PORT_PROPERTY, String.valueOf(RestExpress.DEFAULT_PORT)));
		this.hostName = properties.getProperty(HOST_PROPERTY, DEFAULT_HOST);
		this.baseUrl = properties.getProperty(BASE_URL_PROPERTY, hostName + ":" + String.valueOf(port));
		this.executorThreadPoolSize = Integer.parseInt(properties.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));
		// TODO: Add filling the property values from the envinroment.

		// The initialization of the controllers from properties
		initializeControllers(properties); 

	}
	
	/**
	 * Initialize the controller properties of the configuration.
	 * @param properties The environmental properites.
	 */
	protected void initializeControllers(Properties properties) {
	}

	/**
	 * Get the server host name.
	 * @return The current hostname of the server.
	 */
	public synchronized String getServerHost() {
		return hostName;
	}

	/**
	 * Get the server port.
	 * @return The current server port.
	 */
	public synchronized int getPort()
	{
		return port;
	}
	
	/**
	 * Get the server base URL.
	 * @return The current server base URL.
	 */
	public synchronized String getBaseUrl()
	{
		return baseUrl;
	}

	/**
	 * Get the thread pool size.
	 * @return The current thread pool size.
	 */
	public synchronized int getExecutorThreadPoolSize()
	{
		return executorThreadPoolSize;
	}

}