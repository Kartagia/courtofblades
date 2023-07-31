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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.lang.System.Logger.Level;
import java.util.stream.Collectors;

import com.kautiainen.antti.rpgs.courtofblades.model.HouseModel.HealerType;
import com.kautiainen.antti.rpgs.courtofblades.model.HouseModel.Resource;

/**
 * Class containing the default houses.
 *
 * @author kautsu
 */
public class DefaultHouses {
    
  
    public static final java.util.List<HouseModel> HOUSES;
    

    public static Optional<Resource> getPrimaryDomain(String houseName) {
        Optional<HouseModel> house = HOUSES.stream().filter( entry -> (houseName.equals(entry.getName()))).findFirst();
        try {
            return Optional.ofNullable(house.get().getStrengths().iterator().next());
        } catch(Exception e) {
            return Optional.empty();
        }
    }

    public static Optional<HealerType> getHealerType(String houseName) {
        Optional<HouseModel> house = HOUSES.stream().filter( entry -> (houseName.equals(entry.getName()))).findFirst();
        try {
            return Optional.ofNullable(house.get().getHealer());
        } catch(Exception e) {
            return Optional.empty();
        }
    }

    static  {
    
        java.util.List<HouseModel> result = new java.util.ArrayList<>();
        //TODO: Replace generation of the houses with generation from JSON/XML resource.
        List<String> houseNames = Arrays.asList("Corvetto", "Bastion", "Battalia", "Erlanda", "Lovell", "Al-Mari");
        Function<List<?>, String> getHouseName = (List<?> list) -> {
            try {
                return (String)list.get(0);
            } catch(Exception e) {
                return null;
            }
        };
        Function<List<?>, Resource> getPrimaryResource = (List<?> list) -> {
            try {
                return (Resource)list.get(1);
            } catch(Exception e) {
                return null;
            }
        };
        Function<List<?>, HealerType> getHealerType = (List<?> list) -> {
            try {
                return (HealerType)list.get(2);
            } catch(Exception e) {
                return null;
            }
        };
        Function<List<?>, SpecialFeature> getSpecialFeature = (List<?> list) -> {
            try {
                return (SpecialFeature)list.get(3);
            } catch(Exception e) {
                return null;
            }
        };
        Function<List<?>, java.util.Set<CoterieUpgrade>> getCoterieUpgrades = (List<?> list) -> {
            try {
                java.util.Set<CoterieUpgrade> upgrades = new java.util.HashSet<>();
                return upgrades;
            } catch(Exception e) {
                return null;
            }
        };
        Function<List<?>, java.util.Set<SpecialAbility>> getSpecialAbilities = (List<?> list) -> {
            try {
                java.util.Set<SpecialAbility> upgrades = new java.util.HashSet<>();
                return upgrades;
            } catch(Exception e) {
                return null;
            }
        };
        Function<List<?>, List<?>> toList = (List<?> list) -> (list);
        final java.util.Map<String, List<?>> houseData = Arrays.asList(
            (List<?>)Arrays.asList("Corvetto", Resource.Magic, HealerType.Grace, null, Arrays.asList()), 
            (List<?>)Arrays.asList("Al-Mari", Resource.Supply, HealerType.Physician, null, Arrays.asList()),
            (List<?>)Arrays.asList("Lovell", Resource.Intelligence, HealerType.Grace, null, Arrays.asList()),
            (List<?>)Arrays.asList("Bastien", Resource.Transport, HealerType.Grace, null, Arrays.asList()),
            (List<?>)Arrays.asList("Battalia",Resource.Force,  HealerType.Physician, null, Arrays.asList()),
            (List<?>)Arrays.asList("Erlanda", Resource.Wealth, HealerType.Physician, null, Arrays.asList())
        ).stream().collect(Collectors.toMap(getHouseName, toList));

        // Create houses from the house data.
        for (String houseName : houseNames) {
            List<?> data = houseData.get(houseName);
            if (data != null) {
                try {
                    HouseModel house = new HouseModel(); 
                    house.setName(getHouseName.apply(data));
                    house.setStrengths(Collections.singleton(getPrimaryResource.apply(data)));
                    house.setHealer(getHealerType.apply(data));
                    house.setSpecialFeature(getSpecialFeature.apply(data));
                    house.setCoterieUpgrades(getCoterieUpgrades.apply(data));
                    house.setSpecialAbilities(getSpecialAbilities.apply(data));
                    result.add(house);
                } catch(Exception e) {
                    System.getLogger("foo").log(Level.INFO, String.format("House with corrupted data: %s", houseName), e);
                }
            } else {
                    System.getLogger("foo").log(Level.INFO, String.format("House without data: %s", houseName));
            }
        }
        
        HOUSES = java.util.Collections.unmodifiableList(result);
        
    }
    
}
