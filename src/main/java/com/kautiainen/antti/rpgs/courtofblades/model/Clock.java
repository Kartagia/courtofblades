package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Clock represents a counter.
 */
public class Clock extends Named {

    /**
     * THe class representing a clock type.
     */
    @JsonRootName(value="clock")
    public static class ClockType extends NamedAndDescribed {

        /**
         * The clock ends on both ends.
         * The tug-of-war is implicitely {@link #PROGRESS}, {@link #REGRESS}, 
         * {@link #DEPLETES_EMPTY}, {@link #COMPLETES_FULL}.
         */
        public static final String TUG_OF_WAR = "tug-of-war";

        /**
         * The clock may regress.
         */
        public static final String REGRESS = "regress";

        /**
         * The clock is linked to one or more clocks.
         */
        public static final String LINKED = "linked";

        /**
         * The clock unlocks one or more clocks.
         */
        public static final String CHAINED = "chained";

        /**
         * The clock may advance.
         */
        public static final String PROGRESS = "progress";

        /**
         * The clcok completes at maximum.
         */
        public static final String COMPLETES_FULL = "complete-full";

        /**
         * The clock completes at minimum.
         */
        public static final String DEPLETES_EMPTY = "complete-empty";

        /**
         * A romance clock is a racing clock representing building of a romance. On completion the character gains par-amour.
         */
        public static final Clock.ClockType ROMANCE_CLOCK = new Clock.ClockType(
            "Romance", "A warring clock indicating progress to win a paramour", TUG_OF_WAR);
        /**
         * One of a several clocks of a race only one clock may win.
         */
        public static final Clock.ClockType RACING_CLOCK = new Clock.ClockType(
            "Racing", "One of a group of clocks representing a race only one clock may win", 
            LINKED, CHAINED, COMPLETES_FULL);
        /**
         * A clock representing a danger clock causing some trouble on completion.
         */
        public static final Clock.ClockType DANGER_CLOCK = new Clock.ClockType("Danger", "A clock triggering something dangerous on completion");
        /**
         * A standard linked clock unlocking one or more clocks on completion.
         */
        public static final Clock.ClockType LINKED_CLOCK = new Clock.ClockType(
            "Linked", "A clock unlocking another clock on completion", LINKED, CHAINED, COMPLETES_FULL);
        /**
         * A warring clock also known as tug-of-war-clock.
         */
        public static final Clock.ClockType WARRING_CLOCK = new Clock.ClockType(
            "Warring", "A clock which may progress or regress with condition on both ends.",
            TUG_OF_WAR);
        /**
         * The standard progress clock type
         */
        public static final Clock.ClockType PROGRESS_CLOCK = new Clock.ClockType(
            "Progress", "A standard progress clock with somethign happening at the end.");

        public static final Clock.ClockType REGRESS_CLOCK = new Clock.ClockType(
            "Regress", "A regress clock starts at some tick amount, and ends when empty",
            REGRESS, DEPLETES_EMPTY  
        );
        /**
         * The standard progress clock type
         */
        public static final Clock.ClockType LONG_TERM_CLOCK = new Clock.ClockType(
            "Long Term Project", 
            "A progress clock representing a long term project usually advanced with downtime activities.");


        /**
         * The traits of the clock type.
         */
        private java.util.Set<String> traits = new java.util.HashSet<>();

        /**
         * Create an uninitialized clock type.
         * @implNote This should only be used if creating clock using properties.
         */
        public ClockType() {
            super();
        }

        /**
         * Create a progressive clock ending when full.
         * @param name The name of the clock.
         * @param description The description of the clock.
         */
        public ClockType(String name, String description) {
            this(name, description, PROGRESS, COMPLETES_FULL);
        }

        /**
         * Create a clock with given traits.
         * @param name The name of the clock.
         * @param description The description of the clock.
         * @param traits The tarits of the clock.
         */
        public ClockType(String name, String description, String... traits) {
            this(name, description, Arrays.asList(traits));
        }

        /**
         * Create a clock with given traits.
         * @param name The name of the clock.
         * @param description The description of the clock.
         * @param traits The tarits of the clock.
         */
        @JsonCreator
        public ClockType(
            @JsonProperty("name") String name, 
            @JsonProperty("description") String description, 
            @JsonProperty("traits") java.util.Collection<String> traits) 
            throws IllegalArgumentException {
            super(name, description);
            if (traits != null) {
                this.traits.addAll(traits);
            }
        }

        /**
         * Create a clock with given traits.
         * @param name The name of the clock.
         * @param description The description of the clock.
         * @param traits The tarits of the clock.
         */
        @JsonCreator
        public ClockType(
            @JsonProperty("name") String name, 
            @JsonProperty("description") String description, 
            @JsonProperty("traits") java.util.List<String> traits)
            throws IllegalArgumentException {
            this(name, description, (java.util.Collection<String>)traits);
        }

        /**
         * Create a clock with given traits.
         * @param name The name of the clock.
         * @param description The description of the clock.
         * @param traits The tarits of the clock.
         */
        @JsonCreator
        public ClockType(
            @JsonProperty("name") String name, 
            @JsonProperty("description") String description, 
            @JsonProperty("traits") java.util.Set<String> traits)
            throws IllegalArgumentException {
            this(name, description, (java.util.Collection<String>)traits);
        }

        @JsonGetter("traits")
        public java.util.Set<String> getTraits() {
            return traits;
        }

        /**
         * Does the type contain a trait.
         * @param trait The tested trait.
         * @return True, if and only if the type contains the given traits.
         */
        public boolean hasTrait(String trait) {
            return trait != null && getTraits().contains(trait);
        }


        /**
         * Does the type has all traits.
         * @param traits The list of tested traits.
         * @return True, if and only if the type has all traits.
         */
        public boolean hasTraits(java.util.Collection<String> traits) {
            if (traits != null) {
                return traits.stream().allMatch(this::hasTrait);
            } else {
                return false;
            }

        }

        /**
         * Does the type has all traits.
         * @param traits The list of tested traits.
         * @return True, if and only if the type has all traits.
         */
        public boolean hasTraits(String... traits) {
            if (traits != null) {
                for (String trait: traits) {
                    if (!hasTrait(trait)) return false;
                } 
                return true;
            } else {
                return false;
            }
        }

        /**
         * Does the type has at least one of the listed traits.
         * @param traits The list of tested traits.
         */
        public boolean hasAnyTrait(java.util.Collection<String> traits) {
            if (traits != null) {
                return traits.stream().anyMatch(this::hasTrait);
            } else {
                return false;
            }
        }


        /**
         * Does the type has at least one of the listed traits.
         * @param traits The list of tested traits.
         */
        public boolean hasAnyTrait(String... traits) {
            if (traits != null) {
                for (String trait: traits) {
                    if (hasTrait(trait)) return true;
                }
                return false;
            } else {
                return false;
            }
        }

        /**
         * Is the other clock type equal to the current one.
         * @param other The ohter clock type.
         * @return True, if and only if the clock types are equals.
         */
        public boolean equals(ClockType other) {
            if (other == null) return false;
            if (other == this) return true;
            return Objects.equals(getName(), other.getName()) && 
            getTraits().equals(other.getTraits());
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + Objects.hash(getName());
            result = prime * result + ((traits == null) ? 0 : traits.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (ClockType.class.isAssignableFrom(obj.getClass())) {
                return equals((ClockType)obj);
            } else 
                return false;
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
        ClockType.ROMANCE_CLOCK,
        ClockType.LONG_TERM_CLOCK
    );

    /**
     * The maximum of the clock.
     */
    private int max;

    /**
     * The minimum of the clock.
     */
    private int current;

    /**
     * The type of the clock.
     */
    private ClockType type;

    /**
     * Is the clock enabled.
     */
    private boolean isEnabled = true;



    /**
     * Create a progress clock with a name, a current position,
     * and a full position.
     * @param name The name of the clock.
     * @param current The current position of the clock.
     * @param max The maximum position of a full clock.
     */
    @JsonCreator
    public Clock(
        @JsonProperty("name") String name,
        @JsonProperty("current") int current, 
        @JsonProperty("max") int max) {
        this(name, current, max, ClockType.PROGRESS_CLOCK);
    }

    /**
     * Create a clock with a name, a current position, 
     * a maximum number of ticks, and a type.
     * The clock will be enabled unless it is either completed
     * or depleted.
     * @param name The name of the clock.
     * @param current The current position of the clock.
     * @param max The maximum position of a full clock.
     * @param type The type of the clock.
     * @throws IllegalArgumentException Any of hte given parameters were invalid.
     */
    @JsonCreator
    public Clock(
        @JsonProperty("name") String name,
        @JsonProperty("current") int current,
        @JsonProperty("max") int max, 
        @JsonProperty("type") ClockType type) {
        super(name);
        setType(type);
        setMaximum(max);
        setCurrent(current);
        setEnabled(!isCompleted() && !isDepleted());
    }

    /**
     * Create a clock with a name, a current position, 
     * a maximum number of ticks, and a type.
     * The clock will be enabled unless it is either completed
     * or depleted.
     * @param name The name of the clock.
     * @param current The current position of the clock.
     * @param max The maximum position of a full clock.
     * @param type The type of the clock.
     * @throws IllegalArgumentException Any of hte given parameters were invalid.
     */
    @JsonCreator
    public Clock(
        @JsonProperty("name") String name,
        @JsonProperty("current") int current,
        @JsonProperty("max") int max, 
        @JsonProperty("type") ClockType type, 
        @JsonProperty("enabled") boolean enabled) throws IllegalArgumentException {
        super(name);
        setType(type);
        setMaximum(max);
        setCurrent(current);
        setEnabled(enabled);
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
     * @throws IllegalArgumentException The type is invalid.
     */
    public synchronized void setType(ClockType type) throws IllegalArgumentException {
        if (type == null) throw new IllegalArgumentException("Invalid clock type", 
        new NullPointerException("Type must be specified"));
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
    @JsonIgnore
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

    /**
     * Is the clock completed.
     * @return True, if and only if the clock is completed.
     */
    public synchronized boolean isCompleted() {
        return getType().hasAnyTrait(ClockType.TUG_OF_WAR, ClockType.COMPLETES_FULL) && getCurrent() >= getMaximum();
    }

    /**
     * Is the clock depleted.
     * @return True, if and only if the clock is depleted.
     */
    public synchronized boolean isDepleted() {
        return getType().hasAnyTrait(ClockType.TUG_OF_WAR, ClockType.DEPLETES_EMPTY) && getCurrent() <= getMinimum();
    }

    /**
     * Is the clock disabled.
     * @return True, if and only if the clock is disabled.
     */
    public synchronized boolean isDisabled() {
        return !isEnabled();
    }

    /**
     * Is the clock enabled.
     * @return True, if and only if the clock is enabled.
     */
    @JsonGetter("enabled")
    public synchronized boolean isEnabled() {
        return isEnabled;
    }

    /**
     * Set the enabled status of the clock.
     * @param enabled The enabled status of the clock.
     */
    public synchronized void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }


    /**
     * Generate the clock events the current clock state causes.
     * @return The list of clock events the current clock causes.
     * @implNote The default implementation only creates the 
     * clock compelted and clock depleted events.
     */
    @JsonIgnore
    public synchronized List<ClockEvent> getClockEvents() {
        if (isCompleted()) {
            // Creating the clock completed event.
            return Collections.singletonList(ClockEvent.completedClock(this,getName(), getCurrent() - getMaximum()));
        } else if (isDepleted()) {
            // Creating the clock depleted events.
            return Collections.singletonList(ClockEvent.depletedClock(this,getName(), getCurrent() + getMinimum()));
        } else {
            // There si no events to report.
            return Collections.emptyList();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + max;
        result = prime * result + current;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + (isEnabled ? 1231 : 1237);
        return result;
    }

    /**
     * Test equality with another clock.
     * @param other The other clock.
     * @return True, if and only if the other clock is equal to this.
     */
    public boolean equals(Clock other) {
        if (other == null) return false;
        if (getMaximum() != other.getMaximum())
            return false;
        if (getCurrent() != other.getCurrent())
            return false;
        return Objects.equals(getType(), other.getType())
        && isEnabled() == other.isEnabled();

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (Clock.class.isAssignableFrom(obj.getClass()))
            return equals((Clock)obj);
        else
            return false;
    }


    
}
