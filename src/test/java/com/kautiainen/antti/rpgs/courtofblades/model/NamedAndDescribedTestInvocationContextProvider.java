package com.kautiainen.antti.rpgs.courtofblades.model;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import com.kautiainen.antti.rpgs.courtofblades.model.GenericTypedParameterResolver.ValueConverter;

/**
 * The context provider creaeting test cases for anmed and described values.
 */
public abstract class NamedAndDescribedTestInvocationContextProvider<T extends NamedAndDescribed> implements TestTemplateInvocationContextProvider {

    /**
     * The constructor of the instances.
     */
    private final Supplier<NamedAndDescribedTestCase<T>> constructor;

    /**
     * The list of test case names.
     */
    private volatile List<String> testCaseNames = new ArrayList<>();

    /**
     * The list of names.
     */
    private volatile List<String> names = new ArrayList<>();

    /**
     * THe list of descriptions.
     */
    private volatile List<String> descriptions = new ArrayList<>();


    /**
     * The test case names of the default test cases.
     * @return
     */
    public static List<String> defaultTestCaseNames() {
        return Arrays.asList("Basic test", "An empty description", "With special characters", "An undefined description", 
        "An undefined name", "Both name and description undefined");
    }

    /**
     * Get the names of the default test case.
     * @return The list of the names of the default test cases.
     */
    public static List<String> defaultNames() {
        return Arrays.asList("Test", "Empty", "Äryjyvä", "Undefined", null, null);
    }
    /**
     * Get the descriptions of the default test case.
     * @return The list of the descriptions of the default test cases.
     */
    public static List<String> defaultDescriptions() {
        return Arrays.asList("Test case", "", "ÄRRRRRRR", null, "Undefined name", null);
    }

    /**
     * Get converter which converts an element of list.
     * If the list does not have element at the given index, an undefined
     * value is converted using given converter.
     * 
     * The converter would throw the error IllegalArgumentException in case of failure
     * of the conversion.
     *
     * @param <T> The returned type.
     * @param index The index of the list.
     * @param converter The converter converting the list elemeent into value.
     * @return The converter returning the value from a list.
     * @throws IllegalArgumentException The converter was invalid. 
     */
    public static<T> ValueConverter<T> getValueExtractor(int index, ValueConverter<T> converter, String parameterName) 
    throws IllegalArgumentException {
        if (converter == null) throw new IllegalArgumentException("Invalid converter");
        return (Object source) -> {
            if (source instanceof List<?> list) {
                try {
                    return converter.apply(list.get(index)); 
                } catch(IndexOutOfBoundsException ioobe) {
                    return converter.apply(null);
                } catch(Exception e) {
                    throw new IllegalArgumentException(String.format("Invalid %s at index %d", 
                    Optional.ofNullable(parameterName).orElse("value"), index), e);
                }
            } else {
                return null;
            }
        };
    }


    /**
     * Get converter which converts an element of list.
     * The converter will return an undefined value in any case of error.
     *
     * @param <T> The returned type.
     * @param index The index of the list.
     * @param converter The converter converting the list elemeent into value.
     * @return The converter returning the value from a list. 
     */
    public static<T> ValueConverter<T> getSafeValueExtractor(int index, ValueConverter<T> converter, String parameterName) 
    throws IllegalArgumentException {
        final ValueConverter<T> unsafeConverter = getValueExtractor(index, converter, parameterName);        
        return (Object source) -> {
            try {
                return unsafeConverter.apply(source);
            } catch(Exception e) {
                return null;
            }
        };
    }

    /**
     * Create a new named and described test invocation context provider using given list of values.
     * 
     * @param type The list of value entry lists. The first element of the list contains the 
     * test case naems, the second and third should contain the name and the description of the test entry.
     * @param namesAndDescriptions
     * @throws IllegalArgumentException the given list of entries was invalid.
     */
    public NamedAndDescribedTestInvocationContextProvider(
        Class<? extends NamedAndDescribedTestCase<T>> type, List<List<?>> namesAndDescriptions) 
    throws IllegalArgumentException {
        this(type, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        addTestCases(namesAndDescriptions);
    }

    /**
     * Craete a test case context using names and descriptions.
     * Both lists are null-padded to ensure all descriptions have names and vice versa.
     * @param type Tye type of the created named and described. An undefined value defaults
     * to a new anonymous class instance of the named and described.
     * @param testCaseNames The names of the test cases. An undefined value defaults to an empty list.
     *  The value is padded with default test case namne "Test case #index". 
     * @param names The names of the test cases. An undefined value defaults to an empty list.
     * @param descriptions The descriptions of the test case. An undefined value defaults to an
     *  empty list.
     */
    public NamedAndDescribedTestInvocationContextProvider(
        Class<? extends NamedAndDescribedTestCase<T>> type, 
        List<String> testCaseNames, List<String> names, List<String> descriptions) {

        //TODO: Move construction generation to its own method.
        Constructor<? extends NamedAndDescribedTestCase<T>> constructor = 
        GenericTypedParameterResolver.getDefaultConstructor(type).orElse(null);

        this.constructor = (constructor == null ? ()->(null): 
        () -> {
            try {
                return (NamedAndDescribedTestCase<T>)constructor.newInstance();
            } catch(Exception e) {
                throw new IllegalArgumentException("Could not create a new instance");
            }
        });

        // Adding the entries from the given lists to the test cases.
        int end = Math.max(names == null ? 0 : names.size(), descriptions == null ? 0 : descriptions.size());
        java.util.Iterator<String> testCaseIter = (testCaseNames == null 
        ? java.util.Collections.emptyIterator() : testCaseNames.iterator());
        java.util.Iterator<String> nameIter = (names == null ? java.util.Collections.emptyIterator() : names.iterator());
        java.util.Iterator<String> descriptionIter = (descriptions == null ? java.util.Collections.emptyIterator() : descriptions.iterator());

        for (int index = 0; index < end; index++) {
            this.testCaseNames.add(
                Optional.ofNullable(testCaseIter.hasNext() ? testCaseIter.next() : null).orElse(String.format("Test case #%d"
                , index)));
            this.names.add(nameIter.hasNext() ? nameIter.next() : null);
            this.descriptions.add(descriptionIter.hasNext() ? descriptionIter.next() : null);
        }
    }


    /**
     * Create a new named and described test inviocation context provider providing
     * instances of the given type.
     * @param type The type of the returned values. If the value is undefiend, an instance
     * of an anonymous class of {@link NamedAndDescribed} is generated.
     */
    public NamedAndDescribedTestInvocationContextProvider(Class<? extends NamedAndDescribedTestCase<T>> type) {
        this(type, defaultTestCaseNames(), defaultNames(), defaultDescriptions());
    }


    /**
     * Add test case from a single entry data row.
     * @param entryData The entry data row.
     * @throws IllegalArgumentException The entry data row is invalid.
     */
    public void addTestCase(List<?> entryData) throws IllegalArgumentException {
        if (entryData == null) throw new IllegalArgumentException("Invalid entry data");
        try {
            int index = 0;
            testCaseNames.add(getSafeValueExtractor(index++, 
            GenericTypedParameterResolver.getCastOrNull(String.class), "test case name").apply(entryData));
            names.add(getSafeValueExtractor(index++, 
            GenericTypedParameterResolver.getCastOrNull(String.class), "name").apply(entryData));
            descriptions.add(getSafeValueExtractor(index++, 
            GenericTypedParameterResolver.getCastOrNull(String.class), "description").apply(entryData));
        } catch(Exception e) {
            throw new IllegalArgumentException("Invalid entry data", e);
        }
    }

    /**
     * Add test names of the given list entries.
     * @param entriesData The test cae entries.
     */
    public void addTestCases(List<List<?>> entriesData) {
        if (entriesData != null) {
            int index = 0;
            try {
                entriesData.forEach(this::addTestCase);
                index++;
            } catch(Exception e) {
                throw new IllegalArgumentException(String.format("Invalid entry at %d", index), e);
            }
        }
    }



    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext extensionContext) {
        final Stream.Builder<TestTemplateInvocationContext> result = Stream.builder();
        int end = testCaseNames.size();
        for (int index =0; index < end; index++) {
            result.add(getResultContext(index));
        }
        return result.build();
    }


    /**
     * Get the uoted string value of a text within a start and an end quote and 
     * an escape of the end quote.  
     * <p>If the escape of the end quote is undefined, no escaping is performed.</p>
     * <p>If the escape of the end quote is an empty string, all instances of the 
     * quotes are removed.</p>
     * 
     * @param text The quoted text.
     * @param startQuote The start quote. An undefined value defaults to an empty string.
     * @param endQuote The end quote. An undefiend value defaults to an empty string.
     * @param escapedEndQuote The escape of the end quote. An undefined value disables
     * the escaping.
     * @return If the text is undefined, the strign <code>null</code>. Otherwise, the
     * a string starting with startQuote followed by the text with end quotes escaped,
     * and ending to the end quote.
     */
    public static String quote(CharSequence text, 
    CharSequence startQuote, CharSequence endQuote, CharSequence escapedEndQuote) {
        //TODO: move the method to an untility class.
        if (text == null) return "null";
        StringBuilder result = new StringBuilder(text);

        // Escaping the end quote.
        if (endQuote != null && !endQuote.isEmpty() &&
        escapedEndQuote != null) {
            // Replacing all instances of the end quotes with escapes.
            String seeked = endQuote.toString(), replacement = escapedEndQuote.toString();
            int index = 0, replacementLength = replacement.length(), seekedLength = seeked.length();
            while ( (index = result.indexOf(seeked, index)) >= 0) {
                result.replace(index, index + seekedLength, replacement);
                index += replacementLength;
            }
        }

        if (startQuote != null) {
            result.insert(0, startQuote);
        }
        if (endQuote != null) {
            result.append(endQuote);
        }
        return result.toString();
    }

    /**
     * Get quoted value of a defined text.
     * @param text The quoted text.
     * @return The text "null", if the text is undefined.
     * Otherwise the given text within quotes.
     */
    public static String quote(CharSequence text) {
        //TODO: move the method to an untility class.
        return quote(text, "\"", "\"", null);
    }

    /**
     * Generate invalid result context for a test value of inded due an exception.
     * @param index The index of the test case.
     * @param testCase The test case at the moment of the failure.
     * @param exception The exception causing the failure.
     * @return The test invocation context containing the invalid
     * test case injection.
     */
    public TestTemplateInvocationContext invalidResultContext(int index, NamedAndDescribedTestCase<T> testCase, 
        Throwable exception) {
            testCase.setExpectedException(exception);
            testCase.setExpectedInstance(null);
            return new TestTemplateInvocationContext() {
                @Override
                public String getDisplayName(int invovationIndex) {
                    return String.format(
                        "Invalid test case %s when name is [%s] and description is [%s]", 
                        quote(testCase.getTestName()), 
                        quote(testCase.getName()), 
                        quote(testCase.getDescription()));
                }


                @Override
                public List<Extension> getAdditionalExtensions() {
                    return Arrays.asList(
                    new GenericTypedParameterResolver<NamedAndDescribedTestCase<T>>(testCase),
                        new BeforeEachCallback() {
                            @Override
                            public void beforeEach(ExtensionContext extensionContext) {
                                System.out.println("BeforeEachCallback:Invalid context");
                            }
                        },
                        new AfterEachCallback() {

                            @Override
                            public void afterEach(ExtensionContext extensionContext) {
                                System.out.println("AFterEachCallback:invalid context");
                            }

                        }
                    );
                }
            };
        }
     

    /**
     * Generate a valid result context for a test value of index.
     * @param index The index of the test case.
     * @param testCase The test case at the moment of the failure.
     * @return The test invocation context containing the valid
     * test case injection.
     */
    public TestTemplateInvocationContext validResultContext(int index, NamedAndDescribedTestCase<T> testCase) {
        return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invovationIndex) {
                return String.format("Valid test case %s when name is [%s] and description is [%s]", 
                    quote(testCase.getTestName()), 
                    quote(testCase.getName()), 
                    quote(testCase.getDescription()));
            }
 
            public List<Extension> getAdditionalExtensions() {
                return Arrays.asList(
                    new GenericTypedParameterResolver<NamedAndDescribedTestCase<T>>(testCase),
                    new BeforeEachCallback() {
                        @Override
                        public void beforeEach(ExtensionContext extensionContext) {
                            System.out.println("BeforeEachCallback:Valid context");
                        }
                    },
                    new AfterEachCallback() {

                        @Override
                        public void afterEach(ExtensionContext extensionContext) {
                            System.out.println("AFterEachCallback:Valid context");
                        }

                    }
                );
            }
 
            
        };
    }

    /**
     * Get the result context of the given index.
     * @param index The index of the context entry.
     * @return THe test template invocation contest.
     */
    protected TestTemplateInvocationContext getResultContext(int index) {

        NamedAndDescribedTestCase<T> testCase = constructor.get();
        try {
            testCase.setTestName(testCaseNames.get(index));
            testCase.setName(names.get(index));
            testCase.setDescription(descriptions.get(index));
            T expectedInstance = createExpectedInstance();
            expectedInstance.setName(testCase.getName());
            expectedInstance.setDescription(testCase.getDescription());
            testCase.setExpectedInstance(expectedInstance);
            return validResultContext(index, testCase);
        } catch(Throwable e) {
            return invalidResultContext(index, testCase, e);
        }
    }


    /**
     * Create an instance of the class.
     * @return The instance of the class.
     */
    protected abstract T createExpectedInstance();

    /**
     * Construct the valid case.
     * @param validCase The valid case construct parameters.
     * @return The valid test case. 
     */
    protected TestTemplateInvocationContext validResultContext(List<?> validCase) {
        NamedAndDescribedTestCase<T> testCase = constructor.get();
        int index = 0;
        try {
            testCase.setName(getValueExtractor(
                index++, 
            (Object object) -> {
                try {
                    return (String)object;
                } catch(Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }, "name").apply(validCase));
            testCase.setDescription(
                getValueExtractor(
                index++,
                (Object object) -> {
                try {
                    return (String)object;
                } catch(Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }, "description").apply(validCase));
            return validResultContext(index, testCase);
        } catch(Exception iae) {
            return invalidResultContext(index, testCase, iae);
        }
    }

    @Override
    public boolean supportsTestTemplate(ExtensionContext extensionContext) {
        return true;
    }

}
