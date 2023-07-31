package com.kautiainen.antti.rpgs.courtofblades.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing a named entities.
 */
public class Named {

    /**
     * Create without any name.
     */
    public Named() {
        setName(null);
    }

    /**
     * Create with a name.
     * @param name The initial name.
     */
    public Named(String name) throws IllegalArgumentException, UnsupportedOperationException {
        setName(name);
    }


    /**
     * The current name.
     */
    private String name;

    /**
     * Get the current name.
     *
     * @return The current name. If there is no name, an undefined
     * value is returned.
     */
    @JsonProperty
    public String getName() {
        return this.name;
    }

    /**
     * Set the curretn name.
     *
     * @param name The new name.
     * @throws IllegalArgumentException the given name is not a valid name.
     * @throws UnsupportedOperationException the changing of the name is not allowed.
     */
    @JsonProperty
    public void setName(String name) throws IllegalArgumentException, UnsupportedOperationException {
        this.name = name;
    }

}
