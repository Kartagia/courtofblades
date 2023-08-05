package com.kautiainen.antti.rpgs.courtofblades.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Track represents a progress clock visualized as a row of lines.
 */
public class Track extends Clock {

    /**
     * Craete a new progress track.
     * @param name The name of the track.
     * @param value The current value.
     * @param max The maximal value.
     */
    @JsonCreator
    public Track(
        @JsonProperty("name") String name,
        @JsonProperty("current") int value, 
        @JsonProperty("max")  int max) {
        super(name, value, max);
    }

    /**
     * Create a new progress track.
     * @param name The name of the track.
     * @param current The current value of the track.
     * @param max The maximum value of the track.
     * @param type The type of the track.
     */
    @JsonCreator
    public Track(
        @JsonProperty("name") String name,
        @JsonProperty("current") int current,
        @JsonProperty("max") int max, 
        @JsonProperty("type") ClockType type) {
            super(name, current, max, type);
    }
    
}
