package com.kautiainen.antti.rpgs.courtofblades.rest;

import org.restexpress.RestExpress;
import static com.kautiainen.antti.rpgs.courtofblades.rest.HouseConstants.Url.*;
import static com.kautiainen.antti.rpgs.courtofblades.rest.HouseConstants.Routes.*;

import io.netty.handler.codec.http.HttpMethod;

public class HouseRoutes {
	/**
	 * Define the routes of the server.
	 * @param config The configuration of the server.
	 * @param server The server instance.
	 */
	public static void define(HouseConfiguration config, RestExpress server)
    {
		server.uri("/courtofblades/houses/{"+ HOUSE_ID_PARAMETER + "}.{format}", config.getHouseController())
		.method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
		.name(SINGLE_HOUSE);

		server.uri("/courtofblades/houses.{format}", config.getHouseController())
		.action("readAll", HttpMethod.GET)
		.method(HttpMethod.POST)
		.name(HOUSE_COLLECTION);
    
    }

    /** Define teh routes of the server
	 * @param config The configuration of the server.
	 * @param server The server instance.
     */
    public static void define(Configuration config, RestExpress server) {
        if (config != null && config instanceof HouseConfiguration houseConfig) {
            define(houseConfig, server);
        }
    }
}
