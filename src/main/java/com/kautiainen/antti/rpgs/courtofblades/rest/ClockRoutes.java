package com.kautiainen.antti.rpgs.courtofblades.rest;

import org.restexpress.RestExpress;

/**
 * The interface for clock routes.
 */
public interface ClockRoutes {
    

    public static void define(ClockConfig config, RestExpress server) {
        
        // Manipulate single clocks.
        server.regex("/courtofblades/clocks/{"+ ClockConstants.Url.CLOCK_ID_PARAMETER + "}", config.getClockController());

        // Get all clocks.
        server.regex("/courtofblades/clockss.{format}", config.getClockController());

    }

    /**
	 * Define the routes of the server.
	 * @param config The configuration of the server.
	 * @param server The server instance.
	 */
	public static void define(Configuration config, RestExpress server)
    {
        if (config instanceof ClockConfig clockConfig) {
            define(clockConfig, server);
        }   
    }
}
