package com.kautiainen.antti.rpgs.courtofblades.model;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import com.kautiainen.antti.rpgs.courtofblades.model.GenericTypedParameterResolver.ValueConverter;

public class ListConverter<TYPE> {

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

    private Function<? super List<?>, ? extends TYPE> converter;
    private String parameterName = null;


    /**
     * Conveter converting value of specific index to the value.
     */
    public static class ListEntryConverter<TYPE> extends ListConverter<TYPE> {

        protected final int index;

        /**
         * Create a conveter converting an element of the list into value using an element converter.
         * The converter will return undefined value, if the converted list does not have any value
         * at the given index. Otherwise the list entry is converted using given entry converter function.
         * @param index The index of the converted value.
         * @param entryConveter The function converting the entry at the given index to the value.
         * @param entryName The entry name used to determine the error message on the case of exception.
         */
        public ListEntryConverter(int index, Function<Object, ? extends TYPE> entryConveter, String entryName) {
            super((List<?> list) -> {
                String parameterName = Objects.requireNonNullElse(entryName, String.format("Entry #%d", index));
                try {
                    return (TYPE)entryConveter.apply(list.get(index));
                } catch(IndexOutOfBoundsException ioobe) {
                    throw new IllegalArgumentException(String.format("Missing %s", parameterName), 
                    new NoSuchElementException());
                } catch(Exception e) {
                    throw new IllegalArgumentException(String.format("Invalid %s",parameterName));
                }
            }, entryName);
            this.index = index;
        }
    }

    /**
     * Create converter with default parameter names.
     * The default parameter name is <code>"Element #&lt;index&gt;"</code>.
     * @param converter The converter used to convert list values.
     */
    public ListConverter(Function<? super List<?>, ? extends TYPE> converter) {
        Function<List<?>, TYPE> alwaysNull = (List<?> list) -> (null);
        this.converter = Objects.requireNonNullElse(converter, alwaysNull);
    }


    /**
     * Create a list conveter using a converter and a parameter name.
     * @param converter The conversion function covnerting list to the value.
     * @param parameterName If the parameter name is undefined, the parameter name
     *  of the source list is retunred.
     */
    public ListConverter(Function<? super List<?>, ? extends TYPE> converter, String parameterName) {
        this.converter = (converter == null ? (List<?> list) -> ((TYPE)null) : converter);
    }

    /**
     * Convert list to the 
     * @param source The converted list.
     * @return The conversion result.
     * @throws IllegalArgumentException The conversion failed due illegal source.
     */
    public TYPE convert(List<?> source) throws IllegalArgumentException {
        try {
            return converter.apply(source);
        } catch(IllegalArgumentException iae) {
            throw (parameterName != null ? iae : new IllegalArgumentException("Invalid source list", iae));
        } catch(Exception e) {
            throw new IllegalArgumentException(String.format("Invalid %s", Objects.requireNonNullElse(parameterName,"source list")), e);
        }
    }

    /**
     * COnvert the list to the value.
     * @param source The source list. 
     * @return If the source list conversion failes, an undefined value is retunred.
     * Otherwise the source list conversion rsult is returned.
     */
    public TYPE safeConvert(List<?> source) {
        try {
            return convert(source);
        } catch(IllegalArgumentException e) {
            return null;
        }
    }
}


