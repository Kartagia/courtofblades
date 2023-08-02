package com.kautiainen.antti.rpgs.courtofblades.model;

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


}
