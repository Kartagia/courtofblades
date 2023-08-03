package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.NoSuchElementException;

/**
 * Interface representing named test case.
 */
public interface NamedTestCase<T extends Named, PARAM, RESULT> extends TestCase<T, PARAM, RESULT> {

    /**
     * Get the name of the test case.
     * @return The name of the test case, if it has any.
     */
    default String getName() {
        try {
            return getTestTarget().get().getName();
        } catch(NullPointerException | NoSuchElementException nfe) {
            return null;
        }
    }

    /**
     * Set the name of the test case.
     * @param newName The new name of the test case.
     * @throws IllegalArgumentException The new name was invalid.
     * @throws UnsupportedOperationException The operation is not supported.
     */
    default void setName(String newName) throws IllegalArgumentException, UnsupportedOperationException {
        try {
            getTestTarget().get().setName(newName);
        } catch(NoSuchElementException | NullPointerException npe) {
            throw new UnsupportedOperationException("Setting name is not supported", npe);
        }
    }
}
