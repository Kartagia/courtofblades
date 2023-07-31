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
import java.util.Set;

/**
 *
 * @author kautsu
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
