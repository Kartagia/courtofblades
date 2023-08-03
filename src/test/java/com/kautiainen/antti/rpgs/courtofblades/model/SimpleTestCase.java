package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Simple test case implements test case.
 * <p>The constructor performs initialization of the properties, whose assingment is not
 * supported. For properties with supported setter the supported setter is used.</p>
 */
public class SimpleTestCase<TYPE, PARAMETERS, RESULT> implements TestCase<TYPE, PARAMETERS, RESULT> {

    /**
     * The test name.
     */
    private String testName = null;

    /** 
     * The test target.
    */
    private TYPE testTarget = null;

    /**
     * The test parameters.
     */
    private PARAMETERS parameters = null;

    /**
     * The expected test result.
     */
    private RESULT result = null;

    /**
     * The expected exception.
     */
    private Throwable exception = null;


    /**
     * Create an empty simple test case.
     */
    public SimpleTestCase() {

    }

    /**
     * Craete a simple named test case with given parameters.
     * @param testName the test name.
     * @param parameters The test parameters.
     * @param expectedResult the expected result.
     * @param expectedException the expected exception.
     * @throws IllegalArgumentException Any parameter was invalid.
     * @implNote The constructor does try to set the properties with setter,
     * but in case of unsupported setter, assigns the value.
     */
    @JsonCreator
    public SimpleTestCase(
        @JsonProperty("testName") String testName,
        @JsonProperty("target") TYPE testTarget,
        @JsonProperty("parameters") PARAMETERS parameters,
        @JsonProperty("expectedResult") RESULT expectedResult,
        @JsonProperty("expectedException") Throwable expectedException)
        throws IllegalArgumentException {

        try {
            setTestCaseName(testName);
        } catch(UnsupportedOperationException uo) {
            this.testName = testName;
        }
        try {
            setTestTarget(testTarget);
        } catch (UnsupportedOperationException uo) {
            this.testTarget = testTarget;
        }
        try {
        setParameters(parameters);
        } catch(UnsupportedOperationException uo) {
            this.parameters = parameters;
        }
        try {
            setExpectedResult(expectedResult);
        } catch(UnsupportedOperationException uo) {
            this.result = expectedResult;
        }
        try {
        setExpectedException(expectedException);
        } catch(UnsupportedOperationException uo) {
            this.exception = expectedException;
        }
    }

    @JsonGetter("parameters")
    @Override
    public void setParameters(PARAMETERS parameters) throws IllegalArgumentException, UnsupportedOperationException {
        this.parameters = parameters;
    }

    @JsonGetter("testName")
    @Override
    public String getTestCaseName() {
        return this.testName;
    }

    @JsonGetter("target")
    @Override
    public Optional<TYPE> getTestTarget() {
        return Optional.ofNullable(testTarget);
    }

    @JsonGetter("parameters")
    @Override
    public PARAMETERS getParameters() {
        return parameters;
    }

    @JsonGetter("expectedResult")
    @Override
    public RESULT getExpectedResult() {
        return result;
    }

    @JsonGetter("expectedException")
    @Override
    public Throwable getExpectedException() {
        return exception;
    }
     
    @Override
    public void setTestCaseName(String testName) throws IllegalArgumentException, UnsupportedOperationException {
        this.testName = testName;
    }

    @Override
    public void setTestTarget(TYPE testTarget) throws IllegalArgumentException, UnsupportedOperationException {
        this.testTarget = testTarget;
    }

    @Override
    public void setExpectedException(Throwable exception)
            throws IllegalArgumentException, UnsupportedOperationException {
        this.exception = exception;
    }

    @Override
    public void setExpectedResult(RESULT result) throws IllegalArgumentException, UnsupportedOperationException {
        this.result = result;
    }

    @Override
    public <VALUE> Optional<ListConverter<? extends VALUE>> getValueExtractor(Field field) {
        return TestCase.super.getValueExtractor(field);
    }

    
}
