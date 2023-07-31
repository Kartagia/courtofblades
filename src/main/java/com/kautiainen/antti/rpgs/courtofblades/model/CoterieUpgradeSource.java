package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.HashSet;
import java.util.Set;

/**
 * A source of any number of coterie upgrades.
 * @author Antti Kautiainen
 */
public abstract class CoterieUpgradeSource {
    
    /**
     * The coterie upgrades of the source.
     */
    private Set<CoterieUpgrade> coterieUpgrades;

    /**
     * Create a new coterie upgrade source.
     */
    protected CoterieUpgradeSource() {
    }
    
    /**
     * Create a copy of a coterie upgrade source.
     *
     * @param source The coterie upgrade source.
     * If undefined, a coterie without upgrade source
     * is created.
     */
    @SuppressWarnings("")
    public CoterieUpgradeSource(CoterieUpgradeSource source) {
        this();
        if (source != null) {
            this.setCoterieUpgrades(new HashSet<>(source.getCoterieUpgrades()));
        }
    }

    /**
     * Get the set of the coterie upgrades the house
     * gives to its coteries.
     * @return The set of the possible coterie upgrades.
     */
    public Set<CoterieUpgrade> getCoterieUpgrades() {
        return this.coterieUpgrades;
    }

    /**
     * Set the set of coterie upgrades the house
     * gives to its coteries.
     *
     * @param upgrades The upgrades the house gives
     * to its coteries.
     */
    public void setCoterieUpgrades(Set<CoterieUpgrade> upgrades) {
        this.coterieUpgrades = upgrades;
    }
    
}
