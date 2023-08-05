package com.kautiainen.antti.rpgs.courtofblades.model;

/**
 * Clock event representing completion of a clock.
 */
public class CompletedClockEvent extends SimpleClockEvent {
    
    public CompletedClockEvent(Clock target, String name) throws IllegalArgumentException {
        super(target, name);
    }
    public CompletedClockEvent(Clock target, String name, int excess) throws IllegalArgumentException {
        super(target, name, excess);
    }
    @Override
    public void setExcess(int excess) throws IllegalArgumentException {
        if (excess < 0) throw new IllegalArgumentException("Invalid excess", 
        new IllegalArgumentException("Negative excess")
        );
        super.setExcess(excess);
    }

    
}