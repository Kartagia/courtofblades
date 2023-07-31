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
