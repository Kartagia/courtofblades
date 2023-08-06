package com.kautiainen.antti.rpgs.courtofblades.rest;

import org.restexpress.RestExpress;

import io.netty.handler.codec.http.HttpMethod;

/**
 * The interface for clock routes.
 */
public interface ClockRoutes {
    

    public static void define(Configuration config, RestExpress server) {
        if (config instanceof ClockConfig clockConfig) {
        
        // Manipulate single clocks.
        server.uri("/courtofblades/clocks/{"+ ClockConstants.Url.CLOCK_ID_PARAMETER + "}", clockConfig.getClockController())
		.method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
		.name(ClockConstants.Routes.SINGLE_CLOCK);

        // Get all clocks.
        server.uri("/courtofblades/clocks.{format}", clockConfig.getClockController())
		.action("readAll", HttpMethod.GET)
		.method(HttpMethod.POST)
		.name(ClockConstants.Routes.CLOCK_COLLECTION);
        }

    }

}
