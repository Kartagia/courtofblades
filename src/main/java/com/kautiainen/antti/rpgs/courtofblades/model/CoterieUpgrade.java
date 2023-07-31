package com.kautiainen.antti.rpgs.courtofblades.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A coterie upgrade represents upgrades only coteries can take.
 * @author Antti Kautiainen
 */
public class CoterieUpgrade extends NamedAndDescribed {
    
    /**
     * Create a new coterie upgrade.
     */
    public CoterieUpgrade() {
        super();
    }

    /**
     * Create a new coterie upgrade wiht a name and a description.
     * @param name The intiial name of the coterie upgrade.
     * @param description The initial description of the coterie upgrade.
     * @throws IllegalArgumentException Either the name or the description was invalid.
     */
    public CoterieUpgrade(String name, String description) throws IllegalArgumentException {
        super(name, description);
    }


    /**
     * The upgrade cost of the coterie upgrade.
     */
    private int cost = 1;


    
    /**
     * Get the upgrade cost.
     * @return The number of upgrades the coterie upgrade takes.
     */
    @JsonProperty
    public int getCost() {
        return cost;
    }

    /**
     * Set the upgrade cost.
     * @param cost The new coterie upgrade cost.
     * @throws IllegalArgumentException The given coterie upgrade cost was negative.
     */
    @JsonProperty
    public void setCost(int cost) throws IllegalArgumentException {
        if (cost < 0) throw new IllegalArgumentException("Negative cost is not allowed");
        this.cost = cost;
    }

}
