package com.kautiainen.antti.rpgs.courtofblades.rest.controllers;

import org.restexpress.Request;
import org.restexpress.exception.BadRequestException;

import com.kautiainen.antti.rpgs.courtofblades.model.Clock;
import com.kautiainen.antti.rpgs.courtofblades.rest.ClockConstants;

/**
 * Clock controller performs the rest services of the clocks.
 */
public class ClockController implements Controller<Integer, Clock> {


    /**
     * The service performing the handling of the clocks.
     */
    private Service<Integer, Clock> clockService;

    /**
     * Create a new clock controller with given clock service.
     * @param clockService The clock service implementing the actual storage of the controller.
     */
    public ClockController(Controller.Service<Integer, Clock> clockService) {
        if (validService(clockService)) {
            this.clockService = clockService;
        } else {
            throw new IllegalArgumentException("Invalid clock service");
        }
    }

    /**
     * Test validity of the clock service.
     * @param clockService The tested service.
     * @return True, if and only if the clock service is valid for this class.
     */
    public boolean validService(Service<Integer, Clock> clockService) {
        return clockService != null;
    }

    @Override
    public Class<? extends Clock> getEntityType() {
        return Clock.class;
    }

    @Override
    public Service<Integer, Clock> getService() {
        return clockService;
    }

    @Override
    public Integer getIndex(Request request) throws BadRequestException {
        try {
            return Integer.parseInt(request.getHeader(ClockConstants.Url.CLOCK_ID_PARAMETER, "Clock identifier not given"));
        } catch(NullPointerException | NumberFormatException e) {
            throw new BadRequestException("Invalid clock identifier", e);
        }
    }

    @Override
    public Clock getEntity(Request request) throws BadRequestException {
        try{
            Clock clock = request.getBodyAs(getEntityType(), "Invalid clock entity");
            return clock;
        } finally {

        }
    }
    
}
