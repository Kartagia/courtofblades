package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.Optional;

/**
 * Events representing an excess of the clock.
 */
public class ExcessClockEvent extends SimpleClockEvent {

    /**
     * The clock event indicating the clock advances.
     */
    public static class AdvanceClockEvent extends ExcessClockEvent {
        
        public static String getEventName(Clock clock) {
            return String.format("% advanced", Optional.ofNullable(clock.getName()).orElse("Clock"));
        }
        
        public AdvanceClockEvent(Clock clock, int amount) throws IllegalArgumentException {
            super(clock, getEventName(clock), amount);
        }

        @Override
        public void setExcess(int excess) throws IllegalArgumentException {
            if (excess < 0) {
                throw new IllegalArgumentException("Invalid amount");
            }
            super.setExcess(excess);
        }

        
    }

    /**
     * The clock event indicating the clock regresses.
     */
    public static class RegressClockEvent extends ExcessClockEvent {

        public static String getEventName(Clock clock) {
            return String.format("% regressed", Optional.ofNullable(clock.getName()).orElse("Clock"));
        }

        public RegressClockEvent(Clock clock, int amount) throws IllegalArgumentException {
            super(clock, getEventName(clock), amount);
        }

        @Override
        public void setExcess(int excess) throws IllegalArgumentException {
            if (excess > 0) {
                throw new IllegalArgumentException("Invalid amount");
            }
            super.setExcess(excess);
        }

    }
    

    /**
     * Createa  new excess event for a clock.
     * @param clock
     * @param eventName
     * @param excess
     */
    public ExcessClockEvent(Clock clock, String eventName, int excess) {
        super(clock, eventName, excess);
    }
}
