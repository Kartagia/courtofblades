package com.kautiainen.antti.rpgs.courtofblades.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An entities with both name and description.
 */
public class NamedAndDescribed extends Named {

    private String description;

    /**
     * Create an entity without name nor description.
     */
    public NamedAndDescribed() {
        super();
    }

    /**
     * Create an entity with a name and a description.
     * @param name The name of the entity.
     * @param description The description of the entity.
     * @throws IllegalArgumentException Either tha name or the description was invalid.
     */
    public NamedAndDescribed(String name, String description) throws IllegalArgumentException {
        super(name);
        setDescription(description);
    }

    /**
     * Get the description of the special ability.
     *
     * @return The description of the special ability. An undefined value, 
     * if the special ability has no description.
     */
    @JsonProperty
    public String getDescription()  {
        return this.description;
    }

    /**
     * Set the description of the special ability. 
     * @param description The new descripotion of the special ability.
     */
    @JsonProperty
    public void setDescription(String description) {
        this.description = description;
    }



}
