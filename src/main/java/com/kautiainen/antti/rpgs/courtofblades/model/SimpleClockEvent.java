package com.kautiainen.antti.rpgs.courtofblades.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Simple clock events implements a basic clock event.
 * It does not restrict name, target, nor excess.
 */
public abstract class SimpleClockEvent implements ClockEvent {

    private String name; 

    private int excess;

    /**
     * The clock target of the event.
     */
    private Clock target;

    public SimpleClockEvent(Clock target, String name) throws IllegalArgumentException {
        this(target, name, 0);
    }

    @JsonCreator
    public SimpleClockEvent(
        @JsonProperty("target") Clock target, 
        @JsonProperty("name") String name,
        @JsonProperty("excess") int excess) throws IllegalArgumentException  {
        setTarget(target);
        setEventName(name);
        setExcess(excess);
    }

    @JsonGetter("name")
    public String getEventName() {
        return this.name;
    }

    private void setEventName(String name) throws IllegalArgumentException {
        this.name = name;
    }

    @JsonGetter("excess")
    public int getExcess() {
        return excess;
    }

    public void setExcess(int excess) throws IllegalArgumentException {
        this.excess = excess;
    }

    public Clock getTarget() {
        return this.target;
    }

    public void setTarget(Clock target) throws IllegalArgumentException {
        this.target = target;
    }


}