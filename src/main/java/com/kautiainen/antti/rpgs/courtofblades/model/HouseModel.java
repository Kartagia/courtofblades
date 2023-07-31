/*
 * The MIT License
 *
 * Copyright 2023 kautsu.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.kautiainen.antti.rpgs.courtofblades.model;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A Noble House.
 *
 * The class represents a noble house.
 * @author kautsu
 */
public class HouseModel extends CoterieUpgradeSource {

    private XpTrigger xpTrigger;
    private SpecialFeature specialFeature;
    private Set<SpecialAbility> specialAbilities;
    private HealerType healer;

    /**
     * The enumeration of the important resources.
     */
    public enum Resource {
        Intelligence, Magic, Wealth, Transport, Force, Supply
    }

    /**
     * The healer type determines the healer type of the house and the
     * coterie.
     */
    public enum HealerType {
        Grace("Magical"), Physician("Physical");
        
        /**
         * The name of the specialized Harm Type.
         */
        private final String harmType;
        
        /**
         * Create a new healer type with a harm type.
         *
         * @param harmType The harm type healer is  specialist to heal.
         */
        HealerType(String harmType) {
            this.harmType = harmType;
        }
        
        /**
         * The type of a harm the healer is specialized to heal.
         *
         * @return The harm type the healer is better healing with.
         */
        public Optional<String> getSpecilizedHarmType() {
            return Optional.ofNullable(this.harmType);
        }
    }

    /**
     * Default constructor creating an empty uninitialized house.
     */
    public HouseModel() {
    }

    /**
     * Create a new house.
     * @param name The name of the house.
     * @param primaryDomain The primary domain of the house.
     */
    public HouseModel(String name, Resource primaryDomain, HealerType healer) {
        setName(name);
        setStrengths(new HashSet<>());
        this.getStrengths().add(primaryDomain);
        setHealer(healer);
    }
    private String name;
    private Set<Resource> reputations;

    //@JsonGetter("name")
    public String getName() {
        return this.name;
    }

    //@JsonSetter("name")
    public void setName(String name) throws IllegalArgumentException {
        this.name = name;
    }

    //@JsonGetter("resoruces")
    public Set<Resource> getStrengths() {
        return this.reputations;
    }

    //@JsonSetter("resources")
    public void setStrengths(Set<Resource> strengths) {
        this.reputations = strengths;
    }

    public HealerType getHealer() {
        return this.healer;
    }

    /**
     * Set healer type of the house.
     *
     * @param healer The type of the healer speciality.
     */
    public void setHealer(HealerType healer) {
        this.healer = healer;
    }

    /**
     * Get the unique experience trigger of the
     * coteriesserving the house.
     * @return  The unique xp trigger of any
     * any coterie serving the house.
     */
    public XpTrigger getUniqueXpTrigger() {
        return this.xpTrigger;
    }

    /**
     * Set the unique experience trigger for the
     * coteries of the house.
     * @param trigger The new experience trigger
     * for the coteries of the house.
     */
    public void setUniqueXpTrigger(XpTrigger trigger) {
        this.xpTrigger = trigger;
    }

    /**
     * Get the special features of the coteries
     * serving the house.
     * @return The special feature of the coteries
     * serving the house.
     */
    public SpecialFeature getSpecialFeature() {
        return this.specialFeature;
    }

    /**
     * Set the special feature of the coterie
     * serving the house.
     *
     * @param feature the new feature of the house coteries.
     */
    public void setSpecialFeature(SpecialFeature feature) {
        this.specialFeature = feature;
    }


    /**
     * Get the special abilities available to the coteries serving
     * the house.
     *
     * @return the set of special abilities available to the coteries
     * serving the house.
     */
    public Set<SpecialAbility> getSpecialAbilities() {
        return this.specialAbilities;
    }

    /**
     * Set the special abilities available to the coteries from house.
     * @param abilities
     */
    public void setSpecialAbilities(Set<SpecialAbility> abilities) {
        this.specialAbilities = abilities;
    }

}
