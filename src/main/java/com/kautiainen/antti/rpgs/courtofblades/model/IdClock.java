package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.kautiainen.antti.rpgs.courtofblades.rest.controllers.Controller.Identified;

/**
 * Clock with identifier.
 */
@JsonRootName(value = "id-clock")
public class IdClock<ID> extends Clock implements Identified<ID, Clock> {
    
    /**
     * The identifier of the clock. 
     * If this value is undefiend, the clock has no identifier.
     */
    private ID id;

    /**
     * The clock the current identified clock is based on.
     * If this value is defined, all changes are reflected to the based on.
     */
    private Clock basedOn = null;

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

    /**
     * Get the identifier of a clock.
     * @param <ID> The wanted dentifier type.
     * @param clock The identified clock.
     * @return If the given clock is defined, its identifier. Otherwise, an undefined
     * value.
     */
    public static<ID> ID getIdentifierOfClock(IdClock<? extends ID> clock) {
        return (clock == null ? null : clock.getId());
    }

    /**
     * Get the identifier of a clock.
     * @param <ID> The desired type of the identifier.
     * @param clock The clock whose identifier is acquried.
     * @return The identifier of the given clock, if it is an identified clock with
     * a suitable identifier. Otherwise, an undefined value is returned.
     */
    @SuppressWarnings("unchecked")
    public static<ID> ID getIdentifierOfClock(Clock clock) {
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
    @JsonCreator
    public IdClock(
        @JsonProperty("id") ID identifier, 
        @JsonProperty("content") Clock clock) {
        this(identifier, 
        Optional.ofNullable(clock).orElseThrow(()->(new IllegalArgumentException("Invalid clock"))).getName(), 
        clock.getCurrent(), clock.getMaximum(), clock.getType(), clock.isEnabled());
        this.basedOn = clock;
    }

    /**
     * Create a new identifier clock from given clock.
     * If the clock is identifier clock of a suitable identifier type, its 
     * identifier is used as identifier of the created clock. Otherwise, an
     * undefined identifier is used.
     * @param clock The clock from which the identified clock is created.
     * @throws IllegalArgumentException The clock was invalid.
     */
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

    @Override
    public synchronized int advance(int amount) {
        if (basedOn != null) return basedOn.advance(amount);
        else return super.advance(amount);
    }

    @Override
    public synchronized boolean advanceBeyondMaximum() {
        if (basedOn != null) return basedOn.advanceBeyondMaximum();
        else return super.advanceBeyondMaximum();
    }

    @Override
    public boolean equals(Clock other) {
        if (basedOn != null) return basedOn.equals(other);
        else return super.equals(other);
    }

    @Override
    public boolean equals(Object obj) {
        if (basedOn != null) return basedOn.equals(obj);
        else return super.equals(obj);
    }

    @Override
    public synchronized List<ClockEvent> getClockEvents() {
        if (basedOn != null) return basedOn.getClockEvents();
        else return super.getClockEvents();
    }

    @Override
    public synchronized int getCurrent() {
        if (basedOn != null) return basedOn.getCurrent();
        else return super.getCurrent();
    }

    @Override
    public synchronized int getMaximum() {
        if (basedOn != null) return basedOn.getMaximum();
        else return super.getMaximum();
    }

    @Override
    @JsonIgnore
    public synchronized int getMinimum() {
        if (basedOn != null) return basedOn.getMinimum();
        else return super.getMinimum();
    }

    @Override
    public synchronized ClockType getType() {
        if (basedOn != null) return basedOn.getType();
        else return super.getType();
    }

    @Override
    public synchronized boolean hasCompleted() {
        if (basedOn != null) return basedOn.hasCompleted();
        else return super.hasCompleted();
    }

    @Override
    public int hashCode() {
        if (basedOn != null) return basedOn.hashCode();
        else return super.hashCode();
    }

    @Override
    public synchronized boolean isCompleted() {
        if (basedOn != null) return basedOn.isCompleted();
        else return super.isCompleted();
    }

    @Override
    public synchronized boolean isDepleted() {
        if (basedOn != null) return basedOn.isDepleted();
        else return super.isDepleted();
    }

    @Override
    public synchronized boolean isDisabled() {
        if (basedOn != null) return basedOn.isDisabled();
        else return super.isDisabled();
    }

    @Override
    public synchronized boolean isEnabled() {
        if (basedOn != null) return basedOn.isEnabled();
        else return super.isEnabled();
    }

    @Override
    public synchronized boolean regressBeyondMinimum() {
        if (basedOn != null) return basedOn.regressBeyondMinimum();
        else return super.regressBeyondMinimum();
    }

    @Override
    public synchronized void setCurrent(int current) {
        if (basedOn != null) basedOn.setCurrent(current);
        else super.setCurrent(current);
    }

    @Override
    public synchronized void setEnabled(boolean enabled) {
        if (basedOn != null) basedOn.setEnabled(enabled);
        else super.setEnabled(enabled);
    }

    @Override
    public synchronized void setMaximum(int maximum) {
        if (basedOn != null) basedOn.setMaximum(maximum);
        else super.setMaximum(maximum);
    }

    @Override
    public synchronized void setType(ClockType type) throws IllegalArgumentException {
        if (basedOn != null) basedOn.setType(type);
        else super.setType(type);
    }

    @Override
    public String getName() {
        if (basedOn != null) return basedOn.getName();
        else return super.getName();
    }

    @Override
    public void setName(String name) throws IllegalArgumentException, UnsupportedOperationException {
        if (basedOn != null) basedOn.setName(name);
        else super.setName(name);
    }

    /**
     * Get the content of the identified clock.
     * @return The content of the identified clock.
     */
    public Clock getContent() {
        return Objects.requireNonNullElse(this.basedOn, this);
    }

}
