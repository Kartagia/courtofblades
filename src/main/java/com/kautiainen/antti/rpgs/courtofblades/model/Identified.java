package com.kautiainen.antti.rpgs.courtofblades.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Interface indicating an identified version of a class.
 * @paran <ID> The identifier type.
 * @param <E> The type of identified values.
 */
public interface Identified<ID, E> {

    /**
     * Is an indentifier acceptable.
     * @param identifier The tested identifier.
     * @return True, if and only if the identifier was acceptable.
     */
    boolean validId(ID identifier);

    /**
     * Get the identifier.
     * @return The current identifier. An undefined value indicates
     * the identifier does not exist.
     */
    ID getId();

    /**
     * Set the identifier.
     * @param identifier The new identifier.
     * @return True, if and only if the value was set.
     * @throws IllegalArgumentException The given identifier was invalid.
     * @throws UnsupportedOperationException The operation is not supported.
     * @implSpec If the value is false, the value must be assigned by the implementer.
     */
    default boolean setId(ID identifier) throws IllegalArgumentException, UnsupportedOperationException {
        if (validId(identifier)) {
            // The value is not set.
            return false;
        } else {
            // The value is invalid.
            throw new IllegalArgumentException("Invalid identifier");
        }
    }

    @JsonCreator
    static<ID, E> Identified<ID, E> from(
        @JsonProperty("id") ID id, 
        @JsonProperty("content") E value) {
        return new Identified<ID, E>() {

            @Override
            public boolean validId(ID identifier) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'validId'");
            }

            @Override
            public ID getId() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'getId'");
            }

            public boolean setId(ID id) throws UnsupportedOperationException {
                throw new UnsupportedOperationException("Immutable object does not support setting properties");
            }
        };
    }

}