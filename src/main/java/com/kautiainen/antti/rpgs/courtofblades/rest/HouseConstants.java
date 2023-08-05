package com.kautiainen.antti.rpgs.courtofblades.rest;

/**
 * House config example.
 * <p>Replace House with house name</p>
 */
public interface HouseConstants {
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
		 * The parameter for house identifier.
		 */
		public static final String HOUSE_ID_PARAMETER = "houseId";

    }

    /**
     * The route related constants.
     */
    public interface Routes {

		/**
		 * The single house route name.
		 */
		public static final String SINGLE_HOUSE = "house";
		
		/**
		 * The house collection route name.
		 */
		public static final String HOUSE_COLLECTION = "house";

    }

}
