package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.List;

/**
 * Special Ability serialization testing.
 */
public class SpecialAbilityTestInvocationContextProvider extends NamedAndDescribedTestInvocationContextProvider<SpecialAbility> {

    public SpecialAbilityTestInvocationContextProvider() {
        this(SpecialAbilityTestCase.class);
    }

    /**
     * Create a test case from given subtype of the special ability test case.
     * @param type The type of the created test case.
     */
    protected SpecialAbilityTestInvocationContextProvider(Class<? extends NamedAndDescribedTestCase<SpecialAbility>> type) {
        super(type);
    }

    /**
     * Create a test case using given list of entries.
     * @param entriesData The entries data of the result.
     */
    public SpecialAbilityTestInvocationContextProvider(List<List<?>> entriesData) {
        super(SpecialAbilityTestCase.class, entriesData);
    }

    @Override
    protected SpecialAbility createExpectedInstance() {
        return new SpecialAbility();
    }    
}