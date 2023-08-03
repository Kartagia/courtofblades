package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.Objects;

/**
 * Special ability.
 * @author kautsu
 */
public class SpecialAbility extends NamedAndDescribed {
    
 
    /**
     * Create a new uninitialized special ability.
     */
    public SpecialAbility() {
        super();
    }

    /**
     * Create a new special ability with a name and a description.
     * @param name the initial name of the special ability.
     * @param description the initial description of the special ability.
     */
    public SpecialAbility(String name, String description) throws IllegalArgumentException, UnsupportedOperationException {
        super(name, description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    /**
     * Special Abilities are equals, if they share same name.
     * @param other The other special ability.
     * @return True, if and only if the given special ability is equal with this.
     */
    public boolean equals(SpecialAbility other) {
        if (other == this) return true;
        return (other != null && Objects.equals(this.getName(), other.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        return (other != null && other instanceof SpecialAbility specialAbility) && equals(specialAbility);
    }
}
