package com.kautiainen.antti.rpgs.courtofblades.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Track represents a clock visualized as a row of lines.
 */
public class Track extends Clock {

    /**
     * Craete a new track.
     * @param value The current value.
     * @param max The maximal value.
     */
    @JsonCreator
    public Track(
        @JsonProperty("current") int value, 
        @JsonProperty("max")  int max) {
        super(value, max);
    }

    @JsonCreator
    public Track(
        @JsonProperty("current") int current,
        @JsonProperty("max") int max, 
        @JsonProperty("type") ClockType type) {
            super(current, max, type);
    }
    
}
