package com.kautiainen.antti.rpgs.courtofblades.model;

/**
 * Test case for named and described entities.
 */
public abstract class NamedAndDescribedTestCase<T extends NamedAndDescribed> extends NamedAndDescribed {

    /**
     * The test name.
     */
    private String testName;

    /**
     * The expected instance.
     */
    private T expectedInstance;

    /**
     * Craete a new uninitialized test case.
     */
    public NamedAndDescribedTestCase() {
        super();
    }

    /**
     * Create a test case with a name, a description, and an expected result.
     * @param name The name of the test case named and described.
     * @param description The description of the test case of the named and described.
     * @param expected The expected result of the test case.
     * @throws IllegalArgumentException Any parameter was invalid.
     */
    public NamedAndDescribedTestCase(String name, String description, NamedAndDescribed expected)
    throws IllegalArgumentException {
        super(name, description);
        setDescription(description);
    }


    /**
     * Get the expected instance.
     * @return The expected instance.
     */
    public T getExpectedInstance() {
        return this.expectedInstance;
    }

    /**
     * Set the expected instance.
     *
     * @param expected The expected object.
     * @throws IllegalArgumentException The given object was not suitable for the test case.
     */
    public void setExpectedInstance(T expected) throws IllegalArgumentException {
        this.expectedInstance = expected;
    }

    /**
     * The expected exception.
     */
    private Throwable exception = null;

    /**
     * Get the expected exception.
     * @return If no exception is expected, an undefiend value is returned.
     *  Otherwise a defined expected exception is returned.
     */
    public Throwable getExpectedException() {
        return this.exception;
    }

    /**
     * Set the expected exception.
     * @param exception The expected exception. An undefined value implies that
     * there will be no thrown exception.
     */
    public void setExpectedException(Throwable exception) {
        this.exception = exception;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

}
