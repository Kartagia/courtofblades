package com.kautiainen.antti.rpgs.courtofblades.model;

/**
 * Clock event representing depletion of a clock.
 */
public class DepletedClockEvent extends SimpleClockEvent {
    
    public DepletedClockEvent(Clock target, String name) throws IllegalArgumentException {
        super(target, name);
    }
    public DepletedClockEvent(Clock target, String name, int excess) throws IllegalArgumentException {
        super(target, name, excess);
    }
    @Override
    public void setExcess(int excess) throws IllegalArgumentException {
        if (excess > 0) throw new IllegalArgumentException("Invalid excess", 
        new IllegalArgumentException("Positive excess")
        );
        super.setExcess(excess);
    }

    
}