package com.kautiainen.antti.rpgs.courtofblades.rest;

import org.restexpress.RestExpress;
import static com.kautiainen.antti.rpgs.courtofblades.rest.CoterieConstants.Url.*;
import static com.kautiainen.antti.rpgs.courtofblades.rest.CoterieConstants.Routes.*;

import io.netty.handler.codec.http.HttpMethod;

/**
 * The class containing coterie routes.
 */
public class CoterieRoutes {
    
    /**
	 * Define the routes of the coterie server.
	 * @param config The configuration of the server.
	 * @param server The server instance.
	 */
	public static void define(CoterieConfiguration config, RestExpress server)
    {
		server.uri("/courtofblades/coteries/{" + COTERIE_ID_PARAMETER + "}.{format}", config.getCoterieController())
			.method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
			.name(SINGLE_COTERIE);

		server.uri("/courtofblades/coteries.{format}", config.getCoterieController())
			.action("readAll", HttpMethod.GET)
			.method(HttpMethod.POST)
			.name(COTERIE_COLLECTION);
    }


    /** Define teh routes of the server
	 * @param config The configuration of the server.
	 * @param server The server instance.
     */
    public static void define(Configuration config, RestExpress server) {
        if (config != null && config instanceof CoterieConfiguration coterieConfig) {
            define(coterieConfig, server);
        }
    }    
}
