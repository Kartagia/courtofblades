package com.kautiainen.antti.rpgs.courtofblades.rest;

/**
 * Interface representing the configuration of the clock service.
 */
public interface ClockConstants {
    /**
     * The initialization related constants.
     */
    public interface Init {

    }

    /**
     * The URL related constants.
     */
    public interface Url {

	  /**
		 * The parameter for clock identifier.
		 */
		public static final String CLOCK_ID_PARAMETER = "clockId";

    }

    /**
     * The route related constants.
     */
    public interface Routes {

		/**
		 * The single clock route name.
		 */
		public static final String SINGLE_CLOCK = "clock";
		
		/**
		 * The clock collection route name.
		 */
		public static final String CLOCK_COLLECTION = "clock";

    }    
}
