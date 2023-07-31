/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kautiainen.antti.rpgs.courtofblades.model;

/**
 * 
 * @author kautsu
 */
public class SpecialFeature extends NamedAndDescribed {
    

    /**
     * Crate an unnamed special feature without description.
     */
    public SpecialFeature() {
        super();
    }

    /**
     * Create a new special feature with a name and a description.
     * @param name The initial name of the feature.
     * @param description The initial description of the feature.
     * @throws IllegalArgumentException Either name or description was invalid.
     */
    public SpecialFeature(String name, String description) throws IllegalArgumentException {
        super(name, description);
    }
}
