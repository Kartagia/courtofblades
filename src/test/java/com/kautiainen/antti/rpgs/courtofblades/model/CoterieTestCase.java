package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Create a new coterie test case.
 * 
 * @param <PARAM> The parameters given to the tested.
 * @param <RESULT> The result of the tested function.
 */
public class CoterieTestCase<PARAM, RESULT> extends SimpleNamedTestCase<Coterie, PARAM, RESULT> {

    /**
     * The implemenation of the named.
     */
    private TestCase<? extends Coterie, ? extends PARAM, ? extends RESULT> namedTestCase;

    /**
     * Creating an empty coterie test case.
     */
    public CoterieTestCase() {

    }

    public CoterieTestCase(Coterie coterie, Optional<? extends PARAM> parameter, Optional<? extends RESULT> result, 
    Optional<? extends Throwable> exception, NamedTestCase<? extends Coterie, ? extends PARAM, ? extends RESULT> defaults) {

    }

    public CoterieTestCase(Coterie coterie, NamedTestCase<?, ? extends PARAM, ? extends RESULT> namedTestCase) {
        this.namedTestCase = new NamedTestCase<Coterie,PARAM,RESULT>() {

            @Override
            public String getTestCaseName() {
                return namedTestCase.getTestCaseName();
            }

            @Override
            public Optional<Coterie> getTestTarget() {
                return Optional.ofNullable(coterie);
            }

            @Override
            public PARAM getParameters() {
                return namedTestCase.getParameters();
            }

            @Override
            public RESULT getExpectedResult() {
                return namedTestCase.getExpectedResult();
            }

            @Override
            public Throwable getExpectedException() {
                return namedTestCase.getExpectedException();
            }
            
        };
    }

    public CoterieTestCase(String testCaseName, Coterie testTarget, 
    PARAM parameters, RESULT expectedResult) {
        this(testCaseName, testTarget, parameters, expectedResult, null);
    }
    
    /**
     * Create a coterie test case wiht a test case name, a test target,
     * a test parameters, an expected test result, and an expected test exception.
     * <p>If the expected exception and expected result are both defined, the
     * test can fail on several occasions, and the accumulated result of the test
     * should be equal to the expected result, and the expected exception should
     * be thrown.</p>
     * 
     * @param testCaseName The test case name.
     * @param testTarget The test target.
     * @param parameters The parameters of the test.
     * @param expectedResult The expected result.
     * @param expectedException The expected exception.
     */
    public CoterieTestCase(String testCaseName, Coterie testTarget, 
    PARAM parameters, RESULT expectedResult, Throwable expectedException) {
        super(testCaseName, testTarget, parameters, expectedResult, expectedException);
    }

    /**
     * Create a coterie test case using suppliers to generate getter values.
     * @param <PARAM> The test parameter type.
     * @param <RESULT> The test result type.
     * @param testCaseNameSupplier The supplier providing the test case name.
     * @param testTargetSupplier The supplier providing the test target.
     * @param name The name of the test case.
     * @param parametersSupplier The supplier providing the test parameters.
     * @param expectedResultSupplier The supplier providing the expected results.
     * @param expectedExceptionSupplier The supplier providing the expected exception.
     * @return The coterie test case using suppliers to generate the property values.
     */
    public static<PARAM, RESULT> CoterieTestCase<PARAM, RESULT> fromSuppliers(
        Supplier<? extends String> testCaseNameSupplier, 
        Supplier<? extends Coterie> testTargetSupplier,
        Supplier<? extends PARAM> parametersSupplier, 
        Supplier<? extends RESULT> expectedResultSupplier, 
        Supplier<? extends Throwable> expectedExceptionSupplier) {
        return new CoterieTestCase<PARAM,RESULT>() {

            @Override
            public Optional<Coterie> getTestTarget() {
                return Optional.ofNullable(testTargetSupplier.get());
            }

            @Override
            public PARAM getParameters() {
                return parametersSupplier.get();
            }

            @Override
            public RESULT getExpectedResult() {
                return expectedResultSupplier.get();
            }

            @Override
            public Throwable getExpectedException() {
                return expectedExceptionSupplier.get();
            }

            @Override
            public String getTestCaseName() {
                throw new UnsupportedOperationException("Unimplemented method 'getTestName'");
            }

            @Override
            public void setTestCaseName(String name) throws IllegalArgumentException, UnsupportedOperationException {
                throw new UnsupportedOperationException("Unimplemented method 'setTestName'");
            }

            @Override
            public void setTestTarget(Coterie testTarget)
                    throws IllegalArgumentException, UnsupportedOperationException {
                throw new UnsupportedOperationException("Unimplemented method 'setTestTarget'");
            }

            @Override
            public void setParameters(PARAM parameters) throws IllegalArgumentException, UnsupportedOperationException {
                throw new UnsupportedOperationException("Unimplemented method 'setParameters'");
            }

            @Override
            public void setExpectedException(Throwable exception)
                    throws IllegalArgumentException, UnsupportedOperationException {
                throw new UnsupportedOperationException("Unimplemented method 'setExpectedException'");
            }

            @Override
            public void setExpectedResult(RESULT result)
                    throws IllegalArgumentException, UnsupportedOperationException {
                throw new UnsupportedOperationException("Unimplemented method 'setExpectedResult'");
            }
            
        };
    }

    public CoterieTestCase(CoterieTestCase<? extends PARAM, ? extends RESULT> coterieTestCase) {
        this((TestCase<Coterie, ? extends PARAM, ? extends RESULT>)coterieTestCase);
    }

    public CoterieTestCase(TestCase<? extends Coterie, ? extends PARAM, ? extends RESULT> namedTestCase) {
        this.namedTestCase = namedTestCase;
    }

    public CoterieTestCase(java.util.List<?> listEntry) throws IllegalArgumentException {

    }

    @Override
    public Optional<Coterie> getTestTarget() {
        return Optional.ofNullable(namedTestCase.getTestTarget().get());
    }

    @Override
    public PARAM getParameters() {
        return namedTestCase.getParameters();
    }

    @Override
    public RESULT getExpectedResult() {
        return namedTestCase.getExpectedResult();
    }

    @Override
    public Throwable getExpectedException() {
        return namedTestCase.getExpectedException();
    }

    public static<PARAMETER, RESULT> CoterieTestCase<PARAMETER, RESULT> from(
            CoterieTestCase<? extends PARAMETER, ? extends RESULT> testCase) {
        return (testCase == null ? null : new CoterieTestCase<PARAMETER, RESULT>(testCase));
    }
}
