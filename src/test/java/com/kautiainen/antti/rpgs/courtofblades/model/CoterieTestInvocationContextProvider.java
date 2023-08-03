package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import com.kautiainen.antti.rpgs.courtofblades.model.GenericTypedParameterResolver.ValueConverter;
import com.kautiainen.antti.rpgs.courtofblades.model.ListConverter.ListEntryConverter;

public class CoterieTestInvocationContextProvider implements TestTemplateInvocationContextProvider {

    public static class CoterieInvocationContextProvider<PARAMETER, RESULT>
    implements NamedTestInvocationContextProvider<Coterie, PARAMETER, RESULT> {
        

        /**
         * The list of test entries used to generate the default test entries.
         */
        private Collection<List<?>> testEntries = new ArrayList<>();
        
        /**
         * Creates the default coterie invocation profider.
         */
        public CoterieInvocationContextProvider() {
            this.testEntries.addAll(getDefaultTestCaseDataEntries());
        }

        /**
         * Create a new coterie invocation cotnext provider.
         * @param testEntries
         */
        public CoterieInvocationContextProvider(List<List<?>> testEntries) {
            this.testEntries.addAll(testEntries);
        }

        

        @Override
        public List<List<?>> getDefaultTestCaseDataEntries() {
            // TODO Auto-generated method stub
            return NamedTestInvocationContextProvider.super.getDefaultTestCaseDataEntries();
        }

        /**
         * Generate the set of coterie upgrades from given litsts of values.
         * 
         * @param namedTestCaseLists The list of named test case 
         * @param coterieTypes
         * @param coterieFeatures
         * @param coterieUpgradeLists
         * @param specialAbilityLists
         */
        public<TESTED_CASE extends Named> CoterieInvocationContextProvider(
            Collection<? extends NamedTestCase<TESTED_CASE, ? extends PARAMETER, ? extends RESULT>> namedTestCaseLists,
            Collection<? extends CoterieType> coterieTypes,
            Collection<? extends SpecialFeature> coterieFeatures,
            Collection<? extends List<? extends CoterieUpgrade>> coterieUpgradeLists, 
            Collection<? extends List<? extends SpecialAbility>> specialAbilityLists
            ) {

            }


        /**
         * Generate the set of coterie upgrades from teh given lists of choices.
         * @param coterieType
         * @param coterieFeature
         * @param coterieUpgrades
         * @param specialAbilityProvider
         */
        public CoterieInvocationContextProvider(
            Collection<? extends CoterieType> coterieType,
            Collection<? extends SpecialFeature> coterieFeature,
            Collection<? extends List<? extends CoterieUpgrade>> coterieUpgrades, 
            Collection<? extends List<? extends SpecialAbility>> specialAbilityProvider
            ) {

            }



        @Override
        @SuppressWarnings("unchecked")
        public TestTemplateInvocationContext getTestContext(
                TestCase<? extends Coterie, ? extends PARAMETER, ? extends RESULT> testCase) {
            try {
                return getTestContext((CoterieTestCase<? extends PARAMETER, ? extends RESULT>)testCase);
            } catch(ClassCastException cce) {
                throw new IllegalArgumentException("Test case not suitable for coterie test case");
            }
        }

        /**
         * Get testtemplate invocation context for given test case.
         * @param testCase The tst case.
         * @return The coterie template invocation context.
         * @throws IllegalArgumentException THe test case was not suitable.
         */
        public TestTemplateInvocationContext getTestContext(
            CoterieTestCase<? extends PARAMETER, ? extends RESULT> testCase
        ) throws IllegalArgumentException {
            return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invovationIndex) {
                return testCase.getTestCaseName();
            }
 
            @Override
            public List<Extension> getAdditionalExtensions() {
                return Arrays.asList(
                    new GenericTypedParameterResolver<CoterieTestCase<PARAMETER,RESULT>>(
                        CoterieTestCase.from(testCase)),
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



        @Override
        public CoterieTestCase<PARAMETER, RESULT> createTestCase(List<?> testEntry)
                throws IllegalArgumentException {
            return new CoterieTestCase<PARAMETER, RESULT>(testEntry);
        }
    }

    /**
     * The test invocation context provider producing contexts for testing
     * serialization and deserialization of coteries.
     */
    public static class InstanceSerializationAndDerializatioContextProvider implements NamedTestInvocationContextProvider<Coterie, Void, Void> {

        private Collection<CoterieTestCase<Void,Void>> testCases = new ArrayList<>();

        /**
         * Create an instance serialization and deserialization context provider with default entries.
        */
        public InstanceSerializationAndDerializatioContextProvider() {

        }

        /**
         * Create coterie tet case collection.
         * @param testedValues The coterie test values.
         */
        public InstanceSerializationAndDerializatioContextProvider(Collection<? extends Coterie> testedValues) {
            testCases.addAll( testedValues.stream().map(
                this::createTestCase
            ).toList());
        }

        public CoterieTestCase<Void, Void> createTestCase(Coterie coterie) {
            return new CoterieTestCase<Void, Void>(null, coterie, null, null, (coterie == null ? 
            new NullPointerException() : null));
        }

        @Override
        public TestTemplateInvocationContext getTestContext(
                TestCase<? extends Coterie, ? extends Void, ? extends Void> testCase) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getTestContext'");
        }

        @Override
        public NamedTestCase<Coterie, Void, Void> createTestCase(List<?> testEntry) throws IllegalArgumentException {
            Coterie coterie = createCoterie(testEntry);
            return new CoterieTestCase<Void, Void>(
                (new ListConverter.ListEntryConverter<String>(0, 
                (Object object) -> (object == null ? null : object.toString()), "test case name")).convert(testEntry),
                coterie, null, null, (coterie == null ? new NullPointerException() : null));
        }

        @SuppressWarnings("unchecked")
        public static<TYPE> Function<Object, List<TYPE>> toListConverter() {
            return (Object object) -> { 
                try {
                    List<TYPE> result = (List<TYPE>)object;

                    return result;
                } catch(ClassCastException cce) {
                    throw new IllegalArgumentException("Invalid source", cce);
                }
            };
        } 

        public Coterie createCoterie(List<?> testEntry) throws IllegalArgumentException {
            int index = 1;
            Coterie result = new Coterie();
            result.setName(  
               (new ListConverter.ListEntryConverter<String>(index++, 
                (Object object) -> (object == null ? null : object.toString()), "name")).convert(testEntry));
            result.setType(  
               (new ListConverter.ListEntryConverter<CoterieType>(index++, 
                (Object object) -> (object != null && object instanceof CoterieType coterieType ? coterieType :  (CoterieType)null), 
                "house type").convert(testEntry)));
            result.setHouse(
               (new ListConverter.ListEntryConverter<HouseModel>(index++, 
                (Object object) -> (object != null && object instanceof HouseModel house ? house : null), 
                "house").convert(testEntry)));
            result.setUpgrades(
               (new ListConverter.ListEntryConverter<List<CoterieUpgrade>>(index++, 
               toListConveter(), "upgrades")).convert(testEntry));
            
               result.setSpecialAbilities(
               (new ListConverter.ListEntryConverter<List<SpecialAbility>>(index++, toListConveter(), "special abilities")).convert(testEntry));
            
               result.setUpgrades(
               (new ListConverter.ListEntryConverter<List<CoterieUpgrade>>(index++, toListConveter(), "upgrades")).convert(testEntry));

            return result;
        }

    }

    /**
     * The converter which will produce either list of given type, or an exception.
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static<T> Function<Object, List<T>> toListConveter() {
        return (Object object) -> {
            try {
                
                if (object instanceof List<?> list) {
                    return (List<T>)list;
                } else {
                    throw new ClassCastException("Not a list");
                }
            } catch (ClassCastException cce) {
                throw new IllegalArgumentException("Incompatible value", cce);
            }
        };
    }

    private List<List<?>> allSourceLists = new ArrayList<>();

    /**
     * The default source list.
     * @return Teh default source lists.
     */
    public static List<List<?>> defaultSourceLists() {
        List<List<?>> sourceList = new ArrayList<>();
        sourceList.add((List<?>)Arrays.asList(Void.class, Void.class, "Test case 1", (Void)null, (Void)null, "Le Petit Riens"));
        sourceList.add((List<?>)Arrays.asList(String.class, Void.class, "Test case 2", "Parameteri", (Void)null, "Le Petit Riens I"));
        sourceList.add((List<?>)Arrays.asList(Void.class, Integer.class, "Test case 3", (Void)null, 42, "Le Petit Riens III"));
        sourceList.add((List<?>)Arrays.asList(Void.class, Void.class, "Test case 4", (Void)null, (Void)null, "Le Petit Riens IV"));
        sourceList.add((List<?>)Arrays.asList(Void.class, Void.class, "Generic test case"));
        return sourceList;
    }

    public CoterieTestInvocationContextProvider() {
        super();
        allSourceLists.addAll(defaultSourceLists());
    }

    public<PARAM, RESULT> CoterieTestCase<PARAM, RESULT> createTestCase(
        List<?> list, 
        Class<? extends PARAM> parameterType, 
        Class<? extends RESULT> resultType) {
        return new CoterieTestCase<PARAM, RESULT>(list);
    }


    /**
     * Create test cases from the given source entry.
     * @param list The source entry.
     * @return The test case fo the given source entry.
     */
    public<PARAM, RESULT> CoterieTestCase<PARAM, RESULT> createTestCase(List<?> list) {
        int index = 0;
        Class<? extends PARAM> paramType = (new ListConverter.ListEntryConverter<Class<? extends PARAM>>(index++, 
        new TypeConverter<PARAM>(), 
        null).convert(list));
        Class<? extends RESULT> resultType = (new ListConverter.ListEntryConverter<Class<? extends RESULT>>(index++, 
        new TypeConverter<RESULT>(), 
        null)).convert(list); 
        return createTestCase(list.subList(index, list.size()), paramType, resultType);
    }

    public<PARAM, RESULT> CoterieTestCase<PARAM, RESULT> createTestCase(
        String testCaseName, Coterie coterie, PARAM parameters, RESULT expectedResult) {
        return new CoterieTestCase<PARAM, RESULT>(testCaseName, coterie, parameters, expectedResult);
    }

    /**
     * Get all source lists.
     * @return The source lists.
     */
    public List<List<?>> getAllSourceLists() {
        return allSourceLists;
    }

    /**
     * Get the index of the parameter type in the source list.
     */
    public int getParameterTypeFieldSourceIndex() {
        return 0;
    }

    /**
     * Get the index of the result type in the source list.
     */
    public int getResultTypeFieldSourceIndex() {
        return 1;
    }


    /**
     * Conveter which converts to the type of the given type.
     * @param <TYPE> The type of the wanted object.
     */
    public static class TypeConverter<TYPE> implements ValueConverter<Class<? extends TYPE>> {
        
        public TypeConverter() {
        }

        @Override
        @SuppressWarnings("unchecked")
        public Class<? extends TYPE> apply(Object source) throws IllegalArgumentException {
            try {
                return (Class<? extends TYPE>)source;
            } catch(ClassCastException cce) {
                throw new IllegalArgumentException("Invalid source", cce);
            }
        };
    }

    /**
     * A list conterverter extracting the type field from the list. 
     * @param <TYPE> the wanted type.
     */
    public static class TypeEntryExtractor<TYPE> extends ListEntryConverter<Class<? extends TYPE>> {

        /**
         * Create a type entry extractor with a field index.
         * The field name will be the default name of <code>"Entry #&lt;field index&gt;"</code>.
         * @param index The index of the field.
         * @param fieldName The parameter name of the field.
         */
        public TypeEntryExtractor(int index) {
            super(index, new TypeConverter<TYPE>(), null);
        }

        /**
         * Create a type entry extractor with a field index and a parameter name.
         * @param index The index of the field.
         * @param fieldName The parameter name of the field.
         */
        public TypeEntryExtractor(int index, String fieldName) {
            super(index, new TypeConverter<TYPE>(), fieldName);
        }
    }

    /**
     * Predicate testing that the value contains a type assignable to a type.
     * @param <TYPE> The variabletype.
     */
    public static class EntryTypePredicate<TYPE> implements Predicate<List<?>> {
        
        /**
         * The actual converter performing the conversion.
         */
        private final ListEntryConverter<Class<? extends TYPE>> converter;
        
        public EntryTypePredicate(ListEntryConverter<Class<? extends TYPE>> converter) 
        throws IllegalArgumentException {
            if (converter == null) 
            throw new IllegalArgumentException("Invalid converter", new NullPointerException());
            this.converter = converter;
        }

        /**
         * The index of the tested field in the list.
         * @param index The index of the type field in the array.
         */
        public EntryTypePredicate(int index) {
            this.converter = new TypeEntryExtractor<TYPE>(index);
        }

        public EntryTypePredicate(int index, String parameterName) {
            this.converter = new TypeEntryExtractor<TYPE>(index, parameterName);
        }

        @Override
        public boolean test(List<?> value) {
            try {
                return converter.convert(value) != null;
            } catch(IllegalArgumentException iae) {
                return false;
            }
        }
    }

    /**
     * Generate test cases.
     * @param <PARAM>
     * @param <RESULT>
     * @return
     */
    public<PARAM, RESULT> List<CoterieTestCase<? extends PARAM, ? extends RESULT>> getTestCases() {
        ValueConverter<Class<? extends PARAM>> parameterTypeConverter = 
        ListConverter.getValueExtractor(getParameterTypeFieldSourceIndex(), 
        new TypeConverter<PARAM>(), "parameter type");
        ValueConverter<Class<? extends RESULT>> resultTypeConverter = 
        ListConverter.getValueExtractor(getParameterTypeFieldSourceIndex(), 
        new TypeConverter<RESULT>(), "result type");
        Predicate<List<?>> parameterFilter = new EntryTypePredicate<PARAM>(getParameterTypeFieldSourceIndex());
        Predicate<List<?>> resultFilter = new EntryTypePredicate<RESULT>(getParameterTypeFieldSourceIndex());
        Function<List<?>, CoterieTestCase<? extends PARAM, ? extends RESULT>> testCaseCreator = 
            (List<?> list) -> {
            CoterieTestCase<PARAM, RESULT> testCase = 
                createTestCase(list.subList(2, list.size()), 
            parameterTypeConverter.apply(list), resultTypeConverter.apply(list));
            return testCase;
        };
        return getAllSourceLists().stream().filter(
            (List<?> entry) -> (parameterFilter.test(entry) && resultFilter.test(entry))
        ).map(testCaseCreator).toList();
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext extensionContext) {
        if (extensionContext.getTags().containsAll(Arrays.asList("serialization", "deserialization"))) {
            return (new InstanceSerializationAndDerializatioContextProvider()).provideTestTemplateInvocationContexts(extensionContext);
        } else {
            final Stream.Builder<TestTemplateInvocationContext> result = Stream.builder();
            getTestCases().forEach( (CoterieTestCase<?, ?> testCase) -> {
                result.add(getResultContext(extensionContext,testCase));
            });
            return result.build();
        }
    }

    public<PARAM, RESULT> TestTemplateInvocationContext getResultContext(
        ExtensionContext extensionContext, 
        CoterieTestCase<PARAM,RESULT> testCase) {
        return new TestTemplateInvocationContext() {
            
        };
    }


    @Override
    public boolean supportsTestTemplate(ExtensionContext extensionContext) {
        return true;
    }

}
