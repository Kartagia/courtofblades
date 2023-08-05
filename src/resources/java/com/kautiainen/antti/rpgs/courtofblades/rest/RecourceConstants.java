package com.kautiainen.antti.rpgs.courtofblades.rest;

/**
 * Resource config example.
 * <p>Replace <code>Resource</code> with Resource name, resource with resource name, and RESOURCE with
 * RESOURCE NAME</p>
 */
public interface RecourceConstants {
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
		 * The parameter for resource identifier.
		 */
		public static final String RESOURCE_ID_PARAMETER = "resourceId";

    }

    /**
     * The route related constants.
     */
    public interface Routes {

		/**
		 * The single resource route name.
		 */
		public static final String SINGLE_RESOURCE = "resource";
		
		/**
		 * The resource collection route name.
		 */
		public static final String RESOURCE_COLLECTION = "resource";

    }

}
