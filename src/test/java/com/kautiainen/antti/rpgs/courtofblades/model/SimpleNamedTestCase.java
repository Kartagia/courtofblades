package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.Optional;
import java.util.function.Supplier;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Simple implementation of the named test case.
 */
public class SimpleNamedTestCase<TYPE extends Named, PARAMETERS, RESULT> 
extends SimpleTestCase<TYPE, PARAMETERS, RESULT>
implements NamedTestCase<TYPE, PARAMETERS, RESULT> {

    /**
     * Get the field valeu supplier supplying a field value for a test case.
     * @param <FIELDTYPE> The resutl type of the field value.
     * @param <TYPE> The type of the test target.
     * @param <PARAMETER> The type of the test parameter.
     * @param <RESULT> The type of the test result type.
     * @param baseNamedTestCase The base named test case. 
     * @param field The field, whose value from baseNameTestCase is acquried.
     * @return The optional containing the field value of the given test case,
     * if it exists, and is assignable to the wanted type. Otherwise, an empty value.
     */
    public static<FIELDTYPE, TYPE  extends Named, PARAMETER, RESULT> Supplier<? extends Optional<FIELDTYPE>> getFieldValueSupplier(
        NamedTestCase<TYPE, PARAMETER, RESULT> baseNamedTestCase, TestCaseField<? extends FIELDTYPE> field) {
        Optional<ListConverter<? extends FIELDTYPE>> conveter = baseNamedTestCase.getValueExtractor(field);
        if (conveter.isEmpty()) {
            // Field does not exists.
            return () -> (Optional.empty());
        } else {
            return () -> Optional.ofNullable(conveter.get().safeConvert(baseNamedTestCase.getListEntry()));
        }
    }

    


    /**
     * Create an uninitialized simple named test case.
     */
    public SimpleNamedTestCase() {

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
    public SimpleNamedTestCase(
        @JsonProperty("testName") String testName,
        @JsonProperty("target") TYPE testTarget,
        @JsonProperty("parameters") PARAMETERS parameters,
        @JsonProperty("expectedResult") RESULT expectedResult,
        @JsonProperty("expectedException") Throwable expectedException)
        throws IllegalArgumentException {
        super(testName, testTarget, parameters, expectedResult, expectedException);
    }

}
