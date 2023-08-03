package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Class representing a coterie of the player characters.
 * @author kautsu
 */
public class Coterie extends Named implements SpecialAbilityContainer {

    /**
     * The influence track.
     */
    private Track influece = new Track(0, 4);

    public Track getInfluece() {
        return influece;
    }

    public void setInfluece(Track influece) {
        this.influece = influece;
    }

    public Track getExposure() {
        return exposure;
    }

    public void setExposure(Track exposure) {
        this.exposure = exposure;
    }

    public Track getShame() {
        return shame;
    }

    public void setShame(Track shame) {
        this.shame = shame;
    }

    public Track getCoterieXp() {
        return coterieXp;
    }

    public void setCoterieXp(Track coterieXp) {
        this.coterieXp = coterieXp;
    }

    /**
     * The exposure track.
     */
    private Track exposure = new Track(0, 9);

    /**
     * The shame track.
     */
    private Track shame = new Track(0, 3);

    /**
     * The experience track.
     */
    private Track coterieXp = new Track(0, 10);

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
    @JsonGetter("uprades")
    public java.util.List<CoterieUpgrade> getUpgrades() {
        return this.upgrades;
    }
    
    /**
     * Set the coterie upgrades the coterie has.
     * @param upgrades The upgrades of the coterie.
     */
    @JsonSetter("uprades")
    public void setUpgrades(Collection<? extends CoterieUpgrade> upgrades) {
        this.upgrades = new ArrayList<>(
        java.util.Optional.ofNullable(upgrades).orElse(Collections.emptyList()));
    }
    
    /**
     * Get the house the coterie is serving.
     * @return The house coterie is serving.
     */
    @JsonGetter("house")
    public HouseModel getHouse() {
        return this.house;
    }
    
    /**
     * Set the house the coterie is serving.
     *
     * @param house The new house the coterie
     * is serving.
     */
    @JsonSetter("house")
    public void setHouse(HouseModel house) {
        this.house = house;
    }

    @JsonGetter("abilities")
    @Override
    public List<SpecialAbility> getSpecialAbilities() {
        return this.abilities;
    }

    @JsonIgnore
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

    @JsonSetter("abilities")
    @Override
    public boolean validSpecialAbility(SpecialAbility ability) {
        return (ability != null) && ability instanceof CoterieAbility;
    }

    @JsonIgnore
    @Override
    public Predicate<? super SpecialAbility> getEquivalentFilter(SpecialAbility seeked) {
        return SpecialAbilityContainer.super.getEquivalentFilter(seeked);
    }
    
    

}
