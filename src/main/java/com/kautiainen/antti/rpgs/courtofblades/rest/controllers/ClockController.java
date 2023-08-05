package com.kautiainen.antti.rpgs.courtofblades.rest.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.restexpress.Request;
import org.restexpress.exception.BadRequestException;

import com.kautiainen.antti.rpgs.courtofblades.model.Clock;
import com.kautiainen.antti.rpgs.courtofblades.rest.ClockConstants;

/**
 * Clock controller performs the rest services of the clocks.
 */
public class ClockController implements Controller<Integer, Clock> {


    /**
     * A clock service storing the clocks into memory.
     */
    public class MemoryClockService implements Controller.Service<Integer, Clock> {

        private java.util.Map<Integer, Clock> entities = new ConcurrentHashMap<>();

        /**
         * Create a new empty memory service.
         */
        public MemoryClockService() {

        }
    
        public MemoryClockService(java.util.Map<Integer, ? extends Clock> intialClocks) {
            entities.putAll(intialClocks);
        }

        /**
         * Create a new index.
         * @return A new index for the content. 
         */
        protected synchronized Integer createNewIndex() {
            return entities.keySet().stream().max(Comparator.naturalOrder()).orElse(0) +1;
        }

        @Override
        public synchronized Integer create(Clock content) throws IllegalArgumentException, UnsupportedOperationException {
            if (validContent(content)) {
                Integer index = createNewIndex();
                entities.put(index, content);
                return index;
            } else {
                throw new IllegalArgumentException("Invalid content");
            }
        }

        /**
         * Test validity of the content.
         * @param content The tested content.
         * @return True, if and only if the given content is valid.
         */
        private boolean validContent(Clock content) {
            return (content != null);
        }

        @Override
        public synchronized boolean delete(Integer index) throws UnsupportedOperationException {
            synchronized (entities) {
                if (entities.containsKey(index)) {
                    return (entities.remove(index) != null);
                } else {
                    return false;
                }
            }
        }

        @Override
        public synchronized Optional<Clock> fetch(Integer index) throws UnsupportedOperationException {
            return Optional.ofNullable(entities.get(index));
        }

        @Override
        public synchronized List<Clock> fetchAll() {
            synchronized (entities) {
                return new ArrayList<>(entities.values());
            }
        }

        @Override
        public synchronized boolean update(Integer index, Clock value)
                throws IllegalArgumentException, UnsupportedOperationException {
            if (validContent(value)) {
                synchronized (entities) {
                    Clock replaced = entities.get(index);
                    return Objects.equals(replaced, value);
                }
            } else {
                throw new IllegalArgumentException("Invalid content");
            }
        }

        
    }

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
