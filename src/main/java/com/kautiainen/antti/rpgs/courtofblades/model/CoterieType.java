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

