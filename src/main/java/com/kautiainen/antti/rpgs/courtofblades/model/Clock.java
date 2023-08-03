package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Clock {

    /**
     * THe class representing a clock type.
     */
    public static class ClockType extends NamedAndDescribed {

        /**
         * A romance clock is a racing clock representing building of a romance. On completion the character gains par-amour.
         */
        public static final Clock.ClockType ROMANCE_CLOCK = new Clock.ClockType("Romance", "A warring clock indicating progress to win a paramour");
        /**
         * One of a several clocks of a race only one clock may win.
         */
        public static final Clock.ClockType RACING_CLOCK = new Clock.ClockType("Racing", "One of a group of clocks representing a race only one clock may win");
        /**
         * A clock representing a danger clock causing some trouble on completion.
         */
        public static final Clock.ClockType DANGER_CLOCK = new Clock.ClockType("Danger", "A clock triggering something dangerous on completion");
        /**
         * A standard linked clock unlocking one or more clocks on completion.
         */
        public static final Clock.ClockType LINKED_CLOCK = new Clock.ClockType("Linked", "A clock unlocking another clock on completion");
        /**
         * A warring clock also known as tug-of-war-clock.
         */
        public static final Clock.ClockType WARRING_CLOCK = new Clock.ClockType("Warring", "A clock which may progress or regress with condition on both ends.");
        /**
         * The standard progress clock type
         */
        public static final Clock.ClockType PROGRESS_CLOCK = new Clock.ClockType("Progress", "A standard progress clock with somethign happening at the end.");
        /**
         * The standard progress clock type
         */
        public static final Clock.ClockType LONG_TERM_CLOCK = new Clock.ClockType("Long Term Project", 
        "A progress clock representing a long term project usually advanced with downtime activities.");

        public ClockType() {

        }

        @JsonCreator
        public ClockType(
            @JsonProperty("name") String name, 
            @JsonProperty("description") String description) 
            throws IllegalArgumentException {
            super(name, description);
        }
    }

    /**
     * The default clock types.
     */
    public static final List<ClockType> CLOCK_TYPES = Arrays.asList(
        ClockType.PROGRESS_CLOCK,
        ClockType.WARRING_CLOCK,
        ClockType.LINKED_CLOCK,
        ClockType.DANGER_CLOCK,
        ClockType.RACING_CLOCK,
        ClockType.ROMANCE_CLOCK
    );
    private int max;
    private int current;
    private ClockType type;



    @JsonCreator
    public Clock(
        @JsonProperty("current") int current, 
        @JsonProperty("max") int max) {
        this(current, max, ClockType.PROGRESS_CLOCK);
    }

    @JsonCreator
    public Clock(
        @JsonProperty("current") int current,
        @JsonProperty("max") int max, 
        @JsonProperty("type") ClockType type) {
        setType(type);
        setMaximum(max);
        setCurrent(current);
    }

    /**
     * Get the type of the clock.
     * @return The type of the clock.
     */
    @JsonGetter("type")
    public synchronized ClockType getType() {
        return this.type;
    }

    /**
     * Set the type of the clock.
     * @param type The new type of the clock.
     */
    public synchronized void setType(ClockType type) {
        this.type = type;
    }

    /**
     * Get current progress of the clock.
     * @eturn the current progress of the clock.
     */
    @JsonGetter("current")
    public synchronized int getCurrent() {
        return this.current;
    }

    /**
     * Set the current progress of the clock.
     * @param current The new current proress of the clock.
     */
    public synchronized void setCurrent(int current) {
        this.current = current;
    }

    /**
     * Get the maximum of the clock.
     * @return the current maximum of the clock. 
     */
    @JsonGetter("max")
    public synchronized int getMaximum() {
        return this.max;
    }

    /**
     * Set teh maximum of the clock.
     * @param maximum The maximum of the clock.
     */
    public synchronized void setMaximum(int maximum) {
        this.max = maximum;
    }

    /**
     * Has the clock completed.
     * @return True, if and only if the lcokc has been completed.
     */
    public synchronized boolean hasCompleted() {
        return getCurrent() >= getMaximum();
    }

    /**
     * Does the clock advance beyond maximum.
     * @return True, if and only if the clock advances
     * beyond maximum.
     */
    public synchronized boolean advanceBeyondMaximum() {
        return false;
    }

    /**
     * Does the clock regress beyond the minimum.
     * @return Ture, if and only if the clock regress
     * beyond minimum.
     */
    public synchronized boolean regressBeyondMinimum() {
        return false;
    }

    /**
     * The minimum of the clock.
     * @return The minimum of the clock.
     */
    public synchronized int getMinimum() {
        return 0;
    }

    /**
     * Advance the clock. 
     * @param amount The advancement amount. If value
     * is negative, the clock regresses.
     * @return The amount of advancement not used.
     */
    public synchronized int advance(int amount) {
        if (!advanceBeyondMaximum() && amount >= 0) {
            // The amouny 
            if (this.current < getMaximum()) {
                int unused = Math.max(0, this.current + amount - getMaximum());
                this.current = this.current + amount - unused;
                return unused;
            }
        } else if (!regressBeyondMinimum() && amount < 0) {
            // Determine the unused amount.
            if (this.current > getMinimum()) {
                int unused = this.current +amount;
                if  (unused < getMinimum()) {
                    this.current = getMinimum();
                    return unused;
                } else {
                    this.current = unused;
                    return 0;
                }
            }
        } else {
            // Just adding the amount to the current..
            this.current += amount;
            return 0;
        }
        return amount;
    }
}
