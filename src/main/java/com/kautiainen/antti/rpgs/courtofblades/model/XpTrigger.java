package com.kautiainen.antti.rpgs.courtofblades.model;

/**
 * Class representing Experience Triggers.
 * @author Antti Kautiainen
 */
public class XpTrigger extends NamedAndDescribed {
    
    public XpTrigger() {
        super();
    }

    /**
     * Create a experience trigger with a name and a description.
     * @param name The name of the coterie upgrade.
     * @param description The description of the coterie upgrade.
     * @throws IllegalArgumentException Either the name or the description was invalid.
     */
    public XpTrigger(String name, String description) throws IllegalArgumentException {
        super(name, description);
    }

}
