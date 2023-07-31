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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Class representing a coterie of the player characters.
 * @author kautsu
 */
public class Coterie {
    
    /**
     * The list of coterie upgrades the coterie
     * has.
     */
    private List<CoterieUpgrade> upgrades = null;
    
    /**
     * Th ecoterie type of the coterie.
     */
    private CoterieType type = null;
    
    /**
     * The house the coterie serves.
     */
    private HouseModel house = null;
    
    /**
     * The nane of the coterie.
     */
    private String name;
    
    /**
     * Create an uninitialized coterie.
     */
    public Coterie() {
        
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
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
    
}
