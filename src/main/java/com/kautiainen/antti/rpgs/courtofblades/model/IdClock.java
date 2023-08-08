package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Clock with identifier.
 */
@JsonRootName(value = "id-clock")
public class IdClock<ID> extends Clock {
    
    /**
     * The identifier of the clock. 
     * If this value is undefiend, the clock has no identifier.
     */
    private ID id;

    /**
     * Create a prograss clock with a name, a current value, and a maximum. 
     * The created clock will not have an identifier, and its enabled
     * status is determined by the current state and maximum.
     * @param name The name of the created clock.
     * @param current The current value of the clock.
     * @param max The maximum of the clock. 
     * @throws IllegalArgumentException Any parameter was invalid.
     */
    @JsonCreator
    public IdClock(
        @JsonProperty("name") String name,
        @JsonProperty("current") int current,
        @JsonProperty("max") int max) throws IllegalArgumentException {
        this(null, name, current, max, ClockType.PROGRESS_CLOCK, (current <= max));
    }

    public static<ID> ID getIdentifierOfClock(IdClock<? extends ID> clock) {
        return (clock == null ? null : clock.getId());
    }

    @SuppressWarnings("unchecked")
    public static<ID> ID getIdentifierOfClock(Clock clock) throws IllegalArgumentException {
        if (clock instanceof IdClock<?> idClock) {
            try {
                return getIdentifierOfClock((IdClock<? extends ID>)idClock);
            } catch(ClassCastException cce) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Create an identified clock from given clock.
     * @param identifier The identifier.
     * @param clock The clock whose identified version is created.
     */
    public IdClock(ID identifier, Clock clock) {
        this(identifier, 
        Optional.ofNullable(clock).orElseThrow(()->(new IllegalArgumentException("Invalid clock"))).getName(), 
        clock.getCurrent(), clock.getMaximum(), clock.getType(), clock.isEnabled());
    }

    public IdClock(Clock clock) {
        this(getIdentifierOfClock(clock), clock);
    }


    /**
     * Create a prograss clock with an identifier, a name, a current value, and a maximum. 
     * The enabled status is determined by the current state and maximum.
     * @param identifier The identifier of the created clock.
     * @param name The name of the created clock.
     * @param current The current value of the clock.
     * @param max The maximum of the clock.
     * @throws IllegalArgumentException Any parameter was invalid.
     */
    @JsonCreator
    public IdClock(
        @JsonProperty("id") ID identifier,
        @JsonProperty("name") String name,
        @JsonProperty("current") int current,
        @JsonProperty("max") int max) throws IllegalArgumentException {
        this(identifier, name, current, max, ClockType.PROGRESS_CLOCK, (current <= max));
    }




    /**
     * Createa a clock with an identifier, a type, a name, a current value, a maximum value, and
     * an enabled status.
     * @param identifier The identifier of the created clock.
     * @param name The name of the created clock.
     * @param current The current value of the clock.
     * @param max The maximum of the clock.
     * @param type The type of the clock. An undefined value defaults to the {@link ClockType.PROGRESS_CLOCK}.
     * @param enabled Is the clock enabled or not.
     * @throws IllegalArgumentException Any parameter was invalid.
     */
    @JsonCreator
    public IdClock(
        @JsonProperty("id") ID identifier,
        @JsonProperty("name") String name,
        @JsonProperty("current") int current,
        @JsonProperty("max") int max, 
        @JsonProperty("type") ClockType type,
        @JsonProperty("enabled") boolean enabled) throws IllegalArgumentException {
        super(name, current, max, type, enabled);
        try {
            setId(identifier);
        } catch(UnsupportedOperationException uoe) {
            this.id = identifier;
        }
    }

    /**
     * Is an identifier valid identifier.
     * @param identifier The tested identifier.
     * @return True, if and only if the given identifier is a valid identifier.
     */
    public boolean validId(ID identifier) {
        return true;
    }

    /**
     * Get the identifier of the curretn clock.
     * @return The identifier of the clock.
     */
    @JsonGetter("id")
    public ID getId() {
        return this.id;
    }

    /**
     * Set the current identifier of the clock.
     * @param identifier The clock identifier.
     * @return Was the identifier changed.
     * @throws IllegalArgumentException The identifier was invalid.
     * @throws UnsupportedOperationException The operation is not supported.
     */
    public boolean setId(ID identifier) throws IllegalArgumentException, UnsupportedOperationException {
        if (validId(identifier)) {
            if (Objects.equals(this.id, identifier)) {
                return false;
            } else {
                this.id = identifier;
                return true;
            }

        } else {
            throw new IllegalArgumentException("Invalid identifier");
        }
    }

}
