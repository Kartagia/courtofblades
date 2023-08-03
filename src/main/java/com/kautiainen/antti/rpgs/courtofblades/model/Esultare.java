package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing Esultare of the Irlienne.
 */
public class Esultare {

    /**
     * Esultare clock represents the politicla position among
     * the Great Houses of the Esultare.
     */
    public static class EsultareClock extends Clock implements Comparable<EsultareClock> {
        private String houseName;

        /**
         * Create a new esultare clock for a house starting at value.
         * @param house The house.
         * @param value The initial value of the house.
         */
        public EsultareClock(HouseModel house, int value) {
            this(house.getName(), value);
        }

        @JsonCreator
        public EsultareClock(
            @JsonProperty("house") String house, 
            @JsonProperty("current") int value) {
            super(value, 12, new ClockType("Esultare", "A racing clock of the Esultare houses"));
            this.houseName = house;
        }

        /**
         * Get the house name.
         * @return The house name of the clock owner.
         */
        @JsonGetter("house")
        public String getHouseName() {
            return this.houseName;
        }


        /**
         * The natural order of the clocks is determiend by the current
         * values.
         */
        public int compareTo(EsultareClock other) {
            return Integer.compare(this.getCurrent(), other.getCurrent());
        }
    }

    /**
     * Craete initial Esultare with default houses.
     */
    public Esultare() {

    }

    /**
     * The current status of the Esultare.
     */
    private List<EsultareClock> statusQuo = new ArrayList<>();

    /**
     * The size of the Esultare.
     */
    private int esultareSize = getInitialClockProgress().size();

    /**
     * The default Esultare initial progress clock positions.
     */
    public static final List<Integer> DEFAULT_POSTIONS = Arrays.asList(
        11,9,7,5,3,1
    );

    /**
     * Get the initial clock progress positions of the houses.
     * @return The list containing the initial clock progress positions of
     * the houses.
     */
    @JsonIgnore
    public List<Integer> getInitialClockProgress() {
        return DEFAULT_POSTIONS;
    }


    @JsonCreator
    public Esultare(
        @JsonProperty("positions") List<EsultareClock> houseClocks) {

    }

    /**
     * Get the current positions of the Esultare.
     * @return An unmodifiable list containing current positions of the
     * Esultare.
     */
    @JsonGetter("positions")
    public List<EsultareClock> getPositions() {
        return Collections.unmodifiableList(statusQuo);
    }


    /**
     * Crate a new esultare form given collection of houses, a player house and do we
     * perform setup.
     * @param houses The houses of the Esultare.
     * @param doSetup True, if and only the construction contains setup of the Esultare.
     * @param playerHouse The player house of the Esultare.
     */
    public Esultare(Collection<HouseModel> houses, boolean doSetup, HouseModel playerHouse) {
        List<Integer> initialPositions = this.getInitialClockProgress();
        Iterator<Integer> positions = initialPositions.iterator();
        List<HouseModel> statusQuo = new ArrayList<>(
            houses.stream().filter((house) ->(!playerHouse.equals(house))).toList());
        if (doSetup) {
            // Shuffling all but the last house of the list.
            Collections.shuffle(statusQuo.subList(0, statusQuo.size()));
            if (playerHouse != null) {
                // Adding the player house to the last position of the Esultare.
                this.esultareSize = Math.min(statusQuo.size()+1, initialPositions.size());
                statusQuo.add(this.esultareSize-1, playerHouse);
            }
        }
    
        // Creating the Esultare clocks.
        int index = 0;
        for (HouseModel house: statusQuo) {
            this.statusQuo.add(new EsultareClock(
                house, 
                (positions.hasNext() ? ( positions.next() + (isPopularHouse(house, index) ? 1 : 0) ) : 0)
            ));
            index++;
        }
    }

    /**
     * Create the esultare by performing a setup. 
     * @param houses The houses of the Esultare.
     * @param playerHouse The house of the players.
     * @return The Esultare with all given houses and player house.
     * The given houses are ordered in random positions.
     */
    public static Esultare setup(List<HouseModel> houses, HouseModel playerHouse) {
        return new Esultare(houses, true, playerHouse);
    }

    /**
     * Determines whether a house is popular house or not.
     * @param house The house.
     * @return True, if and only if the house contains some ability
     * giving it popularity.
     */
    public boolean isPopularHouse(HouseModel house) {
        // TODO: Change the fixed house determination to check the house features.
        return (house != null && "Bastien".equals(house.getName()));
    }

    /**
     * Is the house in the given position among Esultare popular.
     * @param house The house.
     * @param position The position in Esultare.
     * @return True, if and only if the position and the house indicates
     * the house is popular.
     * @implNote The default implementation uses default Court of Blades
     * setup with only the last house of the Esultare can get the popular
     * house status.
     */
    public boolean isPopularHouse(HouseModel house, int position) {
        return (position != getInitialClockProgress().size() -1) && isPopularHouse(house);
    }

    /**
     * Create the Esultare from set of hosues. The order
     * of the houses are randomized.
     * @param houses The houses of the esultare.
     */
    public Esultare(Set<HouseModel> houses) {
        this(houses, null);
    }

    /**
     * Create an Esultare with setup using given set of houses and player house.
     * @param houses The houses. If player house does not belong to the houses,
     * the player hosue will be added to the houses as last house on Esultare.
     * @param playerHouse The player house.
     */
    public Esultare(Set<HouseModel> houses, HouseModel playerHouse) {
        this(houses, true, playerHouse);
    }


    /**
     * Get the Esultare size.
     * @return The size of the Esultare.
     */
    @JsonGetter("size")
    public int getEsultareSize() {
        return esultareSize;
    }

    /**
     * Update the Esultare positions.
     * The update orders re-ordering the houses.
     * 
     */
    public synchronized void updatePositions() {
        Collections.sort(this.statusQuo);
    }


}
