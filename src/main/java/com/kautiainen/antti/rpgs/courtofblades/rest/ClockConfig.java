package com.kautiainen.antti.rpgs.courtofblades.rest;

import java.util.Properties;

import com.kautiainen.antti.rpgs.courtofblades.rest.controllers.ClockController;

/**
 * The interface containing clock configurations.
 */
public interface ClockConfig {
    

    /**
     * Initialize the clock controller.
     * @param properties The properties used to initialize the properties.
     * @throws IllegalArgumentException the initialization failed.
     * @throws IllegalStateException The configuration was already initialized.
     */
    void initializeClockController(Properties properties)
    throws IllegalArgumentException, IllegalStateException;

    /**
     * Get clock controller.
     * @return The current clock controller.
     */
    public ClockController getClockController();

}
