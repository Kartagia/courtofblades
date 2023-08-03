package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;

/**
 * Generic test case.
 * 
 * @param <TESTED> The type of the tested object.
 * @param <PARAM> The type of the tested operation parameters.
 * @param <RESULT> The type of the tested operation result.
 */
public interface TestCase<TESTED, PARAM, RESULT> {

    public static class Field {
        private String fieldName;

        protected Field() {
            fieldName = null;
        }

        public Field(String fieldName) throws IllegalArgumentException {
            setFieldName(fieldName);
        }


        public void setFieldName(String fieldName) throws IllegalArgumentException {
            if (fieldName == null) throw new IllegalArgumentException("Invalid field name");
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return this.fieldName;
        }
    }



    /**
     * The class representing a test case field.
     * @param <TYPE> the type of the field value.
     */
    public static class TestCaseField<TYPE> extends Field {

        /**
         * The field of the test case name.
         */
        public static final Field TEST_CASE_NAME = new Field("testCaseName");

        /**
         * The field of the expected result.
         */
        public static final Field EXPECTED_RESULT = new Field("expectedResult");

        /**
         * The field of the expected exception.
         */
        public static final Field EXPECTED_EXCEPTION = new Field("expectedException");

        /**
         * The field of the test target.
         */
        public static final Field TEST_TARGET = new Field("testTarget");

        /**
         * The field of the parameter.
         */
        public static final Field PARAMETER = new Field("parameter");

        private Class<? extends TYPE> fieldType;


        public void setFieldType(Class<? extends TYPE> fieldType) {
            this.fieldType = fieldType;
        }

        /**
         * Create an uninitialized test case.
         */
        protected TestCaseField() {
            super();
        }

        /**
         * Craete a test case field from given a field and a value type.
         * @param field The field of the created field.
         * @param type The value type attached to the field. If undefined,
         *   the standard cast is used instead of class based casting.
         */
        public TestCaseField(Field field, Class<? extends TYPE> type) {
            this(field == null ? null : field.getFieldName(), type);
            
        }

        /**
         * Create a test case field from a field name and field type.
         * @param fieldName The field name. 
         * @param type The value type of the field. If undefined, the 
         *  standard casting is used instead of type based casting.
         */
        public TestCaseField(String fieldName, Class<? extends TYPE> type) {
            super(fieldName);
            setFieldName(fieldName);
            setFieldType(type);
        }


        /**
         * The index of the field.
         * @return The index of the field.
         */
        public OptionalInt getFieldIndex() {
            return OptionalInt.empty();
        }

        /**
         * Get the field index with given modifier.
         * @param deltaIndex The change of the index.
         * @return The index altered by the given amount.
         */
        public OptionalInt getFieldIndex(int deltaIndex) {
            OptionalInt index = getFieldIndex();
            return (index.isPresent() ? OptionalInt.of(index.getAsInt() + deltaIndex): index);
        }

        /**
         * Get the extractor extracting the values from list field.
         * @return
         */
        public Optional<ListConverter<? extends TYPE>> getValueExtractor() {
            return Optional.empty();
        }

        public Class<? extends TYPE> getFieldType() {
            return fieldType;
        }
    }

    /**
     * Converter linking the test case field to an index.
     */
    public static class IndexedTestCaseField<TYPE> extends TestCaseField<TYPE> {

        private int index;

        private final TestCaseField<? extends TYPE> baseField; 

        /**
         * Create a new indexed test case from a test case field.
         * The field will have the default index of 0.
         * @param baseField The base field.
         * @throws IllegalArgumentException The given base field was invalid.
         */
        public IndexedTestCaseField(TestCaseField<? extends TYPE> baseField) throws IllegalArgumentException {
            this(baseField, 0);
        }

        /**
         * Create a new indexed test case from a test case field at an initial index.
         * @param baseField The test case field.
         * @param index The initial index of the test case field.
         * @throws IllegalArgumentException Either the base field or the index was invalid.
         */
        public IndexedTestCaseField(TestCaseField<? extends TYPE> baseField, int index) 
        throws IllegalArgumentException {
            super(
                (baseField == null ? null : baseField.getFieldName()),
                (Class<? extends TYPE>)(baseField == null ? null : baseField.getFieldType())
            );
            if (baseField == null) throw new IllegalArgumentException("Undefined base fields are not accepted");
            this.baseField = baseField;
            this.index = index;
        }

        @Override
        public String getFieldName() {
            return Optional.ofNullable(super.getFieldName()).orElse(baseField.getFieldName());
        }

        @Override
        public Class<? extends TYPE> getFieldType() {
            Class<? extends TYPE> result = super.getFieldType();
            if (result == null) {
                return baseField.getFieldType();
            } else {
                return result;
            }
        }

        /**
         * Updates the index by given amount.
         * @param deltaIndex The amount the index is updated.
         */
        public void updateIndex(int deltaIndex) {
            this.index += deltaIndex;
        }

        @Override
        public OptionalInt getFieldIndex() {
            return OptionalInt.of(index);
        }

        @Override
        public OptionalInt getFieldIndex(int deltaIndex) {
            return OptionalInt.of(index + deltaIndex);
        }

        /**
         * Creating converter for the field.
         * @return The conveter returning the value of the field.
         */
        public Optional<ListConverter<? extends TYPE>> getValueExtractor() {
            Function<Object, TYPE> extractor = (Object object) -> {
                    try {
                        return (TYPE)(getFieldType().cast(object));
                    } catch(Exception e) {
                        throw new IllegalArgumentException("Invalid source value");
                    }
                };
            ListConverter.ListEntryConverter<TYPE> result = 
            new ListConverter.ListEntryConverter<TYPE>(getFieldIndex().getAsInt(), extractor, getFieldName());
            return Optional.of(result);
        }

    }

    /**
     * Class containing the test field set.
     * The list does prevent adding same field more than once.
     */
    public static class TestFieldList implements Iterable<TestCaseField<?>> {

        /**
         * The list of the fields.
         */
        private List<IndexedTestCaseField<?>> fields = 
        Collections.synchronizedList(new ArrayList<>());

        public TestFieldList() {

        }

        /**
         * Create a test field set with given fields.
         * @param fields The initial fields.
         * @throws IllegalARgumentException The list of fields was invalid.
         */
        public TestFieldList(Collection<? extends TestCaseField<?>> fields)
        throws IllegalArgumentException {
            if (fields != null) {
                for (TestCaseField<?> field: fields) {
                    addField(field);
                }
            }
        }

        /**
         * Check validity of a field.
         * @param field The tested field.
         * @return True, if and only if hte field could be added to the field set.
         */
        public synchronized boolean validField(Field field) {
            return field != null &&  
                fields.stream().anyMatch( cursor -> (Objects.equals(field.getFieldName(), cursor.getFieldName())));
        }

        /**
         * Add field to the end of fields.
         * @param <TYPE>
         * @param field The added field.
         */
        public synchronized<TYPE> void addField(TestCaseField<TYPE> field) {
            if (validField(field)) {
                fields.add(new IndexedTestCaseField<>(field, fields.size()));
            } else {
                throw new IllegalArgumentException("Invalid field");
            }
        }

        protected synchronized<TYPE> void addField(TestCaseField<TYPE> field, int index) throws IndexOutOfBoundsException {
            if (validField(field)) {
            
                if (index == fields.size()) {
                    addField(field);
                } else if (index >= 0 && index < fields.size()){
                    IndexedTestCaseField<TYPE> indexedField = new IndexedTestCaseField<>(field, index);
                    synchronized (fields) {
                        fields.add(index, indexedField);
                        for (int cursor = index; cursor < fields.size(); cursor++) {
                            fields.get(index).updateIndex(1);
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException("Invalid field");
            }
        }

        /**
         * Get the field at the given index.
         * @param <TYPE> The type of the wanted field.
         * @param index The index of the wanted field.
         * @return If the given index contains test case field of suitable type, 
         *  retuns that field.
         */
        @SuppressWarnings("unchecked")
        public synchronized<TYPE> Optional<TestCaseField<? extends TYPE>> getFieldAt(int index) {
            try {
                TestCaseField<? extends TYPE> result = (TestCaseField<? extends TYPE>)fields.get(index);
                return Optional.ofNullable(result);
            } catch(Exception e) {
                return Optional.empty();
            }
        }

        /**
         * Get the field count of the set.
         * @return The number of fields the field set contains.
         */
        public synchronized int getFieldCount() {
            return fields.size();
        }


        /**
         * Get a read only iterator iterating through the test case fields.
         * @return Always defined iterator iterating through the test case fields.
         */
        public java.util.Iterator<TestCaseField<?>> iterator() {
            return new java.util.Iterator<TestCaseField<?>>() {

                private java.util.Iterator<IndexedTestCaseField<?>> iter = fields.iterator();

                @Override
                public synchronized boolean hasNext() {
                    return iter.hasNext();
                }

                @Override
                public synchronized TestCaseField<?> next() throws NoSuchElementException {
                    return iter.next();
                }
            };
        }

        public java.util.stream.Stream<? extends TestCaseField<?>> stream() {
            return fields.stream();
        }
    }

    /**
     * Get the field list of the test case.
     * @return The field list of the test case.
     */
    default List<TestCaseField<?>> getTestFieldList() {
        return Arrays.asList(
            new TestCaseField<>(TestCaseField.TEST_CASE_NAME, String.class), 
            new TestCaseField<TESTED>(TestCaseField.TEST_TARGET, null), 
            new TestCaseField<PARAM>(TestCaseField.PARAMETER, null), 
            new TestCaseField<RESULT>(TestCaseField.EXPECTED_RESULT, null),
            new TestCaseField<>(TestCaseField.EXPECTED_EXCEPTION, Throwable.class) 
            );
    }

    /**
     * Get value extractor of the given type.
     * @param <TYPE> The wanted type
     * @param field The feild whose value is extracted.
     * @return The value extractor for a field with extractor.
     * Otherwise, an undefined value.
     */
    @SuppressWarnings("unchecked")
    default<TYPE> Optional<ListConverter<? extends TYPE>> getValueExtractor(Field field) {
        Optional<TestCaseField<?>> testField = getTestFieldList().stream().filter(
           indexCase -> (Objects.equals(indexCase.getFieldName(), field.getFieldName())) 
        ).findFirst();
        try {
            if (testField.isEmpty())  {
                return null;
            }
            return Optional.ofNullable((ListConverter<? extends TYPE>)(testField.get().getValueExtractor().orElse(null)));
        } catch(ClassCastException cce) {
            return null;
        }
    }

    /**
     * Get the test name.
     * @return The test name.
     */
    String getTestCaseName();

    /**
     * Get the test target on which all operations are performed.
     * @return The test target.
     */
    Optional<TESTED> getTestTarget();

    /**
     * Get the perameters for the test.
     * @return The parameters for the test.
     */
    PARAM getParameters();

    /**
     * Get the expected result of the test.
     * @return The expected rsult of the test.
     */
    RESULT getExpectedResult();

    /**
     * Get the expected exception.
     * @return The expected exception thrown by the test.
     */
    Throwable getExpectedException();

    /**
     * Set the tst name.
     * @param name The new test name.
     * @throws IllegalArgumentException THe new test name is invalid.
     * @throws UnsupportedOperationException The operation is not supported.
     */
    default void setTestCaseName(String name) throws IllegalArgumentException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Setting the test case name not suppported");
    }

    /**
     * Set test target.
     * @param testTarget The new test target.
     * @throws IllegalArgumentException The test target is invalid.
     * @throws UnsupportedOperationException The operation is not supported.
     */
    default void setTestTarget(TESTED testTarget) throws IllegalArgumentException, UnsupportedOperationException  {
        throw new UnsupportedOperationException("Setting the test target not suppported");
    }

    /**
     * Set the test parameters.
     * @param parameters The parameters used for the test.
     * @throws IllegalArgumentException The given parameters was invalid.
     * @throws UnsupportedOperationException The operation is not supported.
     */
    default void setParameters(PARAM parameters) throws IllegalArgumentException, UnsupportedOperationException  {
        throw new UnsupportedOperationException("Setting the test parameters not suppported");
    }


    /**
     * Set the expected exception.
     * @param exception The new expected exception.
     * @throws IllegalArgumentException The new expected exception is invalid.
     * @throws UnsupportedOperationException The operation is not supported.
     */
    default void setExpectedException(Throwable exception) throws IllegalArgumentException, UnsupportedOperationException  {
        throw new UnsupportedOperationException("Setting the test expected exception not suppported");
    }

    /**
     * Set the expected result.
     * @param result The new expected result.
     * @throws IllegalArgumentException The new expected rsult is invalid.
     * @throws UnsupportedOperationException The operation is not supported.
     */
     default void setExpectedResult(RESULT result) throws IllegalArgumentException, UnsupportedOperationException  {
        throw new UnsupportedOperationException("Setting the test expected result not suppported");
    }


    /**
     * Get the list representation of the entry.
     * @return The list entry representation of the test case.
     */
    default List<?> getListEntry() {
        List<Object> result = new ArrayList<>();
        result.add(getTestCaseName());
        result.add(getTestTarget());
        result.add(getParameters());
        result.add(getExpectedResult());
        result.add(getExpectedException());
        return result;
    }
}