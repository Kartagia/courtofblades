package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

/**
 * The test invocation context provider provides test case invocations.
 */
interface TestInvocationContextProvider<TYPE, PARAM, RESULT> extends TestTemplateInvocationContextProvider {

    /**
     * Get the default test cases.
     * @return The default test cases for named test case.
     */
    default List<List<?>> getDefaultTestCaseDataEntries() {
        return Collections.emptyList();
    }

    /**
     * Create a test entry from the list test entry.
     * @param testEntry The list representation of the test entry.
     * @return The created test case for given test entry.
     * @throws IllegalArgumentException The given test entry was not a valid
     *   test entry for the provider.
     */
    public TestCase<TYPE, PARAM, RESULT> createTestCase(List<?> testEntry) throws IllegalArgumentException;

    /**
     * Get the test cases of the context provider.
     * @return The list of the test cases the provider returns.
     */
    default java.util.Collection<? extends TestCase<? extends TYPE, ? extends PARAM, ? extends RESULT>> getTestCases() {
        return this.getDefaultTestCaseDataEntries().stream().map(this::createTestCase).toList();
    }

    /**
     * Get thetest template invocation context for the given test case.
     * @param testCase The test case of the implementation.
     * @return The test template invocation context for the given test case.
     */
    public TestTemplateInvocationContext getTestContext(
        TestCase<? extends TYPE, ? extends PARAM, ? extends RESULT> testCase);

    @Override
    default boolean supportsTestTemplate(ExtensionContext extensionContext) {
        return true;
    }


    @Override
    default Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(
        ExtensionContext extensionContext) {
        return getTestCases().stream().map(this::getTestContext);
    }


}

/**
 * Provide named test case producing test invocations within their context.
 */
public interface NamedTestInvocationContextProvider<NAMED_TYPE extends Named, PARAM, RESULT> 
extends TestInvocationContextProvider<NAMED_TYPE, PARAM, RESULT> {

    /**
     * Get the test template invocation context for a list entry.
     * @param testEntry The list entry containing the data of the created named.
     * @return The test template invocation context for the given list entry.
     * @throws IllegalArgumentException The given test entry was invalid.
     */
    default TestTemplateInvocationContext getTestContext(java.util.List<?> testEntry) throws IllegalArgumentException {
        return getTestContext(createTestCase(testEntry));
    }

    /**
     * Create a test entry from the list test entry.
     * @param testEntry The list representation of the test entry.
     * @return The created test case for given test entry.
     * @throws IllegalArgumentException The given test entry was not a valid
     *   test entry for the provider.
     */
    public NamedTestCase<NAMED_TYPE, PARAM, RESULT> createTestCase(List<?> testEntry) throws IllegalArgumentException;

    /**
     * Get the test cases of the context provider.
     * @return The list of the test cases the provider returns.
     */
    default java.util.Collection<? extends NamedTestCase<? extends NAMED_TYPE, ? extends PARAM, ? extends RESULT>> getTestCases() {
        return this.getDefaultTestCaseDataEntries().stream().map(this::createTestCase).toList();
    }


    
}
