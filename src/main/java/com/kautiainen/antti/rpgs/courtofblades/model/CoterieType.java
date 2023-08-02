package com.kautiainen.antti.rpgs.courtofblades.model;

/**
 * The coterie type of the coterie.
 * @author kautsu
 */
public class CoterieType extends CoterieUpgradeSource {

    private String name;
    
    public CoterieType() {
        super();
    }
    
    /**
     * Create a new coterie type with a name or source.
     *
     * @param name The name of the created coterie type.
     * @param source The source of the upgrades the type 
     * offers.
     */
    @SuppressWarnings("")
    public CoterieType(String name, CoterieUpgradeSource source) {
        super(source);
        this.setName(name);
    }
    
    /**
     * Get the coterie type name.
     *
     * @return THe coterie type name.
     * @return A string containing the name.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Set the name of the coterie type.
     *
     * @param name The name of the coterie type.
     */
    public void setName(String name) {
        this.name = name;
    }
}

