package com.kautiainen.antti.rpgs.courtofblades.model;

import java.beans.BeanProperty;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * The interface for special ability container.
 */
public interface SpecialAbilityContainer {

    /**
     * The collector returning an atomic boolean containing true, if and only if the stream contained
     * at least one true value. Otherwise, it returns atomic boolean containing false.
     * TODO: Move this constant to an utility class
     */
    public static Collector<Boolean, AtomicBoolean, AtomicBoolean> ATOMIC_TRUE_IFF_ONE_TRUE = 
        Collector.of(
                    (Supplier<AtomicBoolean>)()->(new AtomicBoolean(false)),
                (AtomicBoolean result, Boolean removalResult) -> {
                    if (removalResult != null && removalResult && !result.get()) {
                        result.set(true);
                    }
                },
                (BinaryOperator<AtomicBoolean>)(AtomicBoolean result, AtomicBoolean followingResult) -> {
                    return (result.get() ? result: followingResult);
                }, 
                Collector.Characteristics.IDENTITY_FINISH
                );

    /**
     * The collector returning true, if and only if at least one element is
     * true. Otherwise, returns false.
     * TODO: Move this constant to an utility class
     */
    public static Collector<Boolean, AtomicBoolean, Boolean> TRUE_IFF_ONE_TRUE = 
        Collector.of(
                    (Supplier<AtomicBoolean>)()->(new AtomicBoolean(false)),
                (AtomicBoolean result, Boolean removalResult) -> {
                    if (removalResult != null && removalResult && !result.get()) {
                        result.set(true);
                    }
                },
                (BinaryOperator<AtomicBoolean>)(AtomicBoolean result, AtomicBoolean followingResult) -> {
                    return (result.get() ? result: followingResult);
                }, 
                (AtomicBoolean result) -> (result != null && result.get())
                );


    /**
     * The message indicating a collection of ablities was invalid.
     */
    public static final String INVALID_ABILITIES_MESSAGE = "Invalid abilities";

    /**
     * The message indicating a single ability was invalid.
     */
    public static final String INVALID_ABILITY_MESSAGE = "Invalid ability";
    /**
     * The message indicating a value was rejected.
     */
    public static final String VALUE_REJECTED_MESSAGE = "Validation rejected the value";
    /**
     * THe message that the adding of the special abilities is not
     * supported.
     */
    public static final String ADD_SPECIAL_ABILITY_NOT_SUPPORTED_MESSAGE = "Adding special abilities is not supported";
    /**
     * The message that the setting of the special abiltiies is not
     * supported.
     */
    public static final String SET_SPECIAL_ABILITY_NOT_SUPPORTED_MESSAGE = 
    "Setting special abilities is not supported";
    /**
     * The message that the removal of the special abilities is not
     * supported.
     */
    public static final String REMOVE_SPECIAL_ABILITY_NOT_SUPPORTED_MESSAGE = 
    "Removing special abilities is not supported";

    /**
     * Greate a filter selecting special ability by a name.
     * @param name The seeked name.
     * @return A predicate selecting special ability with given name.
     */
    @JsonIgnore
    static Predicate<SpecialAbility> createNameFilter(String name) {
        return (SpecialAbility ability) -> (Objects.equals(name, ability.getName()));
    }

    /**
     * Get filter selecting special ability by a name.
     * @param name The seeked name.
     * @return A predicate selecting special ability with given name.
     * @implNote The default implementation uses the filter returned by 
     * {@link #createNameFilter(String)}.
     */
    @JsonIgnore
    default Predicate<SpecialAbility> getNameFilter(String name) {
       return createNameFilter(name);
    }

    /**
     * Get the special abilities of the container.
     * @return The list of special abilities of the container.
     * This value is always defined.
     */
    @JsonGetter("abilities")
    @BeanProperty
    public List<SpecialAbility> getSpecialAbilities();

    /**
     * Set the special abilities of the container. 
     * @param specialAbilities The new special abilities of the container.
     * @throws IllegalArgumentException The given special abitities, or any of its 
     * members, was invalid.
     * @throws UnsupportedOperationException The setting of special abilities is not
     * supported.
     */
    @JsonIgnore
    default void setSpecialAbilities(Collection<? extends SpecialAbility> specialAbilities) 
    throws IllegalArgumentException, UnsupportedOperationException {
        throw new UnsupportedOperationException(SET_SPECIAL_ABILITY_NOT_SUPPORTED_MESSAGE);
    }

    /**
     * Set the special abilities of the container. 
     * @param specialAbilities The new special abilities of the container.
     * @throws IllegalArgumentException The given special abitities, or any of its 
     * members, was invalid.
     * @throws UnsupportedOperationException The setting of special abilities is not
     * supported.
     */
    @JsonSetter("abilities")
    @BeanProperty
    default void setSpecialAbilities(List<SpecialAbility> specialAbilities)
    throws IllegalArgumentException, UnsupportedOperationException {
        setSpecialAbilities((Collection<? extends SpecialAbility>)specialAbilities);
    }

    /**
     * Test validity of the special ability.
     * @param ability The tested special ability.
     * @return True, if and only if the container accepts given special ability.
     */
    default boolean validSpecialAbility(SpecialAbility ability) {
        return ability != null;
    }

    /**
     * Add special ability to the container.
     * <p>The illegal argument exception is only thrown, if the value is invalid.</p>
     * <p>The container may refuse to add a valid value. F. ex.
     * a special ability which can exist only once in the list cannot be added again.</p>
     *
     * @param ability The added special ability.
     * @return True, if and only if the ability was added to the list.
     * @throws IllegalArgumentException The ability was invalid.
     * @throws UnsupportedOperationException The operation is not supported.
     * @implNote The default implementation tries to add a valid value to the collection
     * returned by {@link #getSpecialAbilities()}. 
     */
    default boolean addSpecialAbility(SpecialAbility ability)
    throws IllegalArgumentException, UnsupportedOperationException {
        try {
            if (validSpecialAbility(ability)) {
                return getSpecialAbilities().add(ability);
            } else {
                throw new IllegalArgumentException(VALUE_REJECTED_MESSAGE);
            }
        } catch(UnsupportedOperationException uo) {
            throw new UnsupportedOperationException(ADD_SPECIAL_ABILITY_NOT_SUPPORTED_MESSAGE, uo);
        } catch(Exception e) {
            throw new IllegalArgumentException(INVALID_ABILITY_MESSAGE, e);
        }
    } 


    /**
     * Remove all special abilities.
     * @return True, if and only if any abililites was removed.
     * @throws UnsupportedOperationException The operation is not supported.
     * @implNote The default implementation tries to clear the collection
     * returned by {@link #getSpecialAbilities()}.
     */
    default boolean removeSpecialAbilities() throws UnsupportedOperationException {
        try {
            List<SpecialAbility> list = getSpecialAbilities();
            if (list.isEmpty()) {
                return false;
            } else {
                getSpecialAbilities().clear();
                return true;
            }
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * Test validity of the collection of the special abilities.
     * @param specialAbilities The collection of the special abilities.
     * @return True, if and only if teh collection special abilities of the collection
     * are valid for addition.
     * @implNote The default implementation requires that all abilities are valid.
     */
    default boolean validSpecialAbilities(Collection<? extends SpecialAbility> specialAbilities) {
        return specialAbilities != null && 
        specialAbilities.stream().allMatch(this::validSpecialAbility);
    }

    /**
     * Add all special abilities to the collection.
     * @param specialAbilities The added special abilities.
     * @return True, if and only if special abilities were added to the collection.
     * @throws IllegalArgumentException The collection was invalid.
     */
    default boolean addSpecialAbilities(Collection<? extends SpecialAbility> specialAbilities) {
        if (specialAbilities == null || specialAbilities.isEmpty()) {
            return false;
        }
        return validSpecialAbilities(specialAbilities)
            && specialAbilities.stream().map(
                this::addSpecialAbility
            ).collect(ATOMIC_TRUE_IFF_ONE_TRUE).get();
    }

    /**
     * Remove special abitities in the collection.
     * @param specialAbilities The colleciton of the removed special abilities.
     * An undefined collection dfeaults to an empty collection.
     * @return True, if and only if at least one special ability was removed
     * from the collection.
     * @throws UnsupportedOperationException The collection does not support
     * the operation.
     * @implNote The default implementation calls {@link #removeSpecialAbility(SpecialAbility)}
     * for all elements of the given collection of special abilities.
     */
    default boolean removeSpecialAbilities(Collection<? extends SpecialAbility> specialAbilities)
    throws UnsupportedOperationException {
        if (specialAbilities == null || specialAbilities.isEmpty()) return false;
        try {
            return (Boolean)specialAbilities.stream().map(
                this::removeSpecialAbility
            ).collect(ATOMIC_TRUE_IFF_ONE_TRUE
            ).get();
        } catch(UnsupportedOperationException uo) {
            throw new UnsupportedOperationException(REMOVE_SPECIAL_ABILITY_NOT_SUPPORTED_MESSAGE, uo);
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * Remove a special ability.
     * @param ability The removed special ability.
     * @return True, if and only if the special ability was removed.
     * @throws UnsupportedOperationException The operation is not supported.
     * @implNote The default implementation tries to remove a valid value
     * from collection returned by {@link #getSpecialAbilities()}.
     */
    default boolean removeSpecialAbility(SpecialAbility ability) 
    throws UnsupportedOperationException {
        try {
            if (validSpecialAbility(ability)) {
                // Removing the value using special abilities collection.
                return getSpecialAbilities().remove(ability);
            } else {
                // Removal failed.
                return false;
            }
        } catch(UnsupportedOperationException uo) {
            throw new UnsupportedOperationException(REMOVE_SPECIAL_ABILITY_NOT_SUPPORTED_MESSAGE, uo);
        } catch(Exception e) {
            // The removal failed due an exception.
            return false;
        }
    }

    /**
     * Does the container have given special ability.
     * @param ability The tested special ability.
     * @return True, if and only if the given special ability exists at least once
     * in the container.
     * @implNote The default implementation checks the containment of a valid value
     * in collection returned by {@link #getSpecialAbilities()}.
     */
    default boolean hasSpecialAbility(SpecialAbility ability) {
        try {
            return validSpecialAbility(ability) && getSpecialAbilities().contains(ability);
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * Get special ability with a name.
     * @param name The name of the sought special ability.
     * @return The given special ability, if it does exist.
     * @implNote The default implementation filters the values of the collection
     * returned by {@link #getSpecialAbilities()} with {@link #getNameFilter(String)}.
     */
    @JsonIgnore
    default Optional<SpecialAbility> getSpecialAbility(String name) {
        try {
            return getSpecialAbilities().stream().filter(getNameFilter(name)).findFirst();
        } catch(Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Get the stored version of a special ability.
     * @param seeked The seeked special ability.
     * @return If the container contains special ability equal to the seeked, the
     * special abiltiy is returned wrapped into Optional. Otherwise, an empty value
     * is returned.
     * @implNote The default implementation filters the values of the collection
     * returned by {@link #getSpecialAbilities()} with {@link #getEquivalentFilter(SpecialAbility)}.
     */
    @JsonIgnore
    default Optional<SpecialAbility> getSpecialAbility(SpecialAbility seeked) {
        try {
            return getSpecialAbilities().stream().filter(getEquivalentFilter(seeked)).findFirst();
        } catch(Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Get the filter selecting contained special abilities equal to a special ability.
     * If the special ability is not valid for the collection, the predicate will not
     * allow any value.
     * @param seeked The seeked special ability.
     * @return The predicate matching all special ablities collection deems equivalent to
     * the given special ability according to the collection.
     * @implNote The default implementation uses {@link Objects#equals(Object, Object)} to
     * determine the equivalence.
     */
    @JsonIgnore
    default Predicate<? super SpecialAbility> getEquivalentFilter(SpecialAbility seeked) {
        try {
            if (validSpecialAbility(seeked)) {
                return (SpecialAbility ability) -> (Objects.equals(seeked, ability));
            } else {
                return (SpecialAbility ability) -> (false);
            }
        } catch(Exception e) {
            return (SpecialAbility ability) -> (false);
        }
    }

}
