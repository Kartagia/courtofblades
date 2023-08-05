package com.kautiainen.antti.rpgs.courtofblades.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clock event represents clock events.
 */
public interface ClockEvent {

    /**
     * Create event by completing given clock.
     * @param completedClock The completed clock.
     * @return The event indicating the given clock was completed.
     * @throws IllegalArgumentException The given clock was not completed.
     */
    static ClockEvent completedClock(Clock completedClock) throws IllegalArgumentException {
        try {
            return new CompletedClockEvent(
                completedClock, String.format("%s completed", completedClock.getName()), 
                completedClock.getCurrent() - completedClock.getMaximum());
        } catch(IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid completed clock", e);
        }
    }

    /**
     * Create a completed clock event without excess.
     * @param completedClock The completed clock.
     * @param name The name of the event,
     * @return A completed clock event without excess.
     */
    static ClockEvent completedClock(Clock completedClock, String name) {
        return new CompletedClockEvent(completedClock, name);
    }

    /**
     * Create a compelted clock event with an excess.
     * @param completedClock The completed clock.
     * @param name The name of the event,
     * @param excess The excess of the completed clock.
     * @return A completed clock event with given excess.
     * @throws IllegalArugmentException The given excess was invalid.
     */
    static ClockEvent completedClock(Clock completedClock, String name, int excess) throws IllegalArgumentException {
        return new CompletedClockEvent(completedClock, name, excess);
    }

    /**
     * Create event of depleting a clock.
     * @param depletedClock The depleted clock.
     * @return The created depleted clock event.
     * @throws IllegalArgumentException The given clock was not depleted.
     */
    static ClockEvent depletedClock(Clock depletedClock) throws IllegalArgumentException {
        try {
            return new CompletedClockEvent(
                depletedClock, String.format("%s depleteted", depletedClock.getName()), 
                depletedClock.getCurrent() + depletedClock.getMinimum());
        } catch(IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid depleteted clock", e);
        }
    }


    /**
     * Create a depleted clock event with an excess.
     * @param depletedClock The depleted clock.
     * @param name The name of the event.
     * @param excess The excess of the depleted event.
     * The value should be negative or zero as depletion
     * is negative progress.
     * @return A depleted clock event with given excess.
     * @throws IllegalArugmentException The given excess was invalid.
     */
    static ClockEvent depletedClock(Clock depletedClock, String name, int excess) throws IllegalArgumentException {
        return new DepletedClockEvent(depletedClock, name, excess);
    }

    @JsonCreator
    static ClockEvent create(
        @JsonProperty("target") Clock clock,
        @JsonProperty("name") String name, 
        @JsonProperty("excess") int excess) {
        
        if (excess >= 0) {
            return completedClock(clock, name, excess);
        } else {
            return depletedClock(clock, name, excess);
        }
    }

    /**
     * Get the name of the event.
     * @return The name of the event.
     */
    @JsonGetter("name")
    public String getEventName();

    /**
     * Get the excess ticks. 
     * @return
     */
    @JsonGetter("excess")
    public int getExcess();


    /**
     * Get the target clock of the event.
     * @return The target clock of the event.
     */
    @JsonGetter("target")
    public Clock getTarget();
}
