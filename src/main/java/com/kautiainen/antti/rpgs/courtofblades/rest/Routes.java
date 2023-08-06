package com.kautiainen.antti.rpgs.courtofblades.rest;

import org.restexpress.RestExpress;

/**
 * The routes of the backend serfice.
 */
public abstract class Routes
{

	/**
	 * Define the routes of the server.
	 * @param config The configuration of the server.
	 * @param server The server instance.
	 */
	public static void define(Configuration config, RestExpress server)
    {
		// TODO: Add routes 
		ClockRoutes.define(config, server);

		// or...
		//		server.regex("/some.regex", config.getRouteController());
    }
}
