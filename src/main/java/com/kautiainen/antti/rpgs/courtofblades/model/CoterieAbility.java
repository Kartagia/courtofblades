package com.kautiainen.antti.rpgs.courtofblades.model;

/**
 * Special abilities assignable for coteries.
 */
public class CoterieAbility extends SpecialAbility {
    
    /**
     * An uninitialized construction.
     */
    protected CoterieAbility() {
        super();
    }

    /**
     * Create a coterie special abiltiy with a name and a description.
     * @param name The name of the coterie special ability.
     * @param description The description of the special ability.
     * @throws IllegalArgumentException
     */
    public CoterieAbility(String name, String description) 
    throws IllegalArgumentException {
        super(name, description);
    }
}
