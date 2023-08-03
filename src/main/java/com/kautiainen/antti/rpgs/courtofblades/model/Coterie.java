package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Class representing a coterie of the player characters.
 * @author kautsu
 */
public class Coterie extends Named implements SpecialAbilityContainer {
    
    /**
     * The list of coterie upgrades the coterie
     * has.
     */
    private List<CoterieUpgrade> upgrades = null;
    
    /**
     * The coterie type of the coterie.
     */
    private CoterieType type = null;
    
    /**
     * Set the upgrades of the coterie.
     *
     * @param upgrades The new upgrades of the coterie.
     */
    public void setUpgrades(List<CoterieUpgrade> upgrades) throws IllegalArgumentException {
        this.upgrades = upgrades;
    }

    /**
     * Get the coterie type.
     * @return The current coterie type.
     */
    public CoterieType getType() {
        return type;
    }

    /**
     * Set the coterie type.
     * @param type The type of the coterie.
     * @throws IllegalArgumentException The given coterie type was invalid.
     */
    public void setType(CoterieType type) throws IllegalArgumentException {
        this.type = type;
    }

    /**
     * The house the coterie serves.
     */
    private HouseModel house = null;

    /**
     * The colleciton of the abilities.
     * TODO: Replace this with validating list refusing all values the coterie does not allow.
     */
    private List<SpecialAbility> abilities = new java.util.ArrayList<>();
    
    /**
     * Create an uninitialized coterie.
     */
    public Coterie() {
        
    }
    
    /**
     * Create a new coterie with given name.
     * @param name The name of the created coterie.
     * @throws IllegalArgumentException The given coterie name was invalid.
     */
    public Coterie(String name) throws IllegalArgumentException {
        super(name);
    }
    
    /**
     * Get the coterie upgrades the coterie has.
     * @return The list of coterie upgrades the coterie
     * has.
     */
    public java.util.List<CoterieUpgrade> getUpgrades() {
        return this.upgrades;
    }
    
    /**
     * Set the coterie upgrades the coterie has.
     * @param upgrades The upgrades of the coterie.
     */
    public void setUpgrades(Collection<? extends CoterieUpgrade> upgrades) {
        this.upgrades = new ArrayList<>(
        java.util.Optional.ofNullable(upgrades).orElse(Collections.emptyList()));
    }
    
    /**
     * Get the house the coterie is serving.
     * @return The house coterie is serving.
     */
    public HouseModel getHouse() {
        return this.house;
    }
    
    /**
     * Set the house the coterie is serving.
     *
     * @param house The new house the coterie
     * is serving.
     */
    public void setHouse(HouseModel house) {
        this.house = house;
    }

    @Override
    public List<SpecialAbility> getSpecialAbilities() {
        return this.abilities;
    }

    @Override
    public void setSpecialAbilities(Collection<? extends SpecialAbility> specialAbilities)
            throws IllegalArgumentException, UnsupportedOperationException {
        if (specialAbilities == null) {
            this.removeSpecialAbilities();
        } else if (validSpecialAbilities(specialAbilities)) {
            if (removeSpecialAbilities()) {
                addSpecialAbilities(specialAbilities);
            }
        } else {
            throw new IllegalArgumentException(INVALID_ABILITIES_MESSAGE);
        }
    }

    @Override
    public boolean validSpecialAbility(SpecialAbility ability) {
        return (ability != null) && ability instanceof CoterieAbility;
    }

    @Override
    public Predicate<? super SpecialAbility> getEquivalentFilter(SpecialAbility seeked) {
        return SpecialAbilityContainer.super.getEquivalentFilter(seeked);
    }
    
    

}
