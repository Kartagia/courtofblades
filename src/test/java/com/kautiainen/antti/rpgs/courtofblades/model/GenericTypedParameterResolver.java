package com.kautiainen.antti.rpgs.courtofblades.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * Generic typed parameter resolver generating elemetns of specific type.
 */
public class GenericTypedParameterResolver<T> implements ParameterResolver {

    /**
     * Interface converting an object to given type, or throws an {@link IllegalArgumentException}
     * on vailure.
     * @param <TYPE> The type into which the value is converted.
     */
    public static interface ValueConverter<TYPE> extends java.util.function.Function<Object, TYPE> {

        /**
         * The function tries to convert the valeu to the type.
         * @param value The converted value.
         * @return The conversion result.
         * @throws IllegalArgumentException The converted value was not 
         * suitable for conversion.
         */
        public TYPE apply(Object value) throws IllegalArgumentException;
    }

    private Predicate<Class<?>> typeTester;

    private Predicate<Object> objectTester;

    private ValueConverter<T> objectResolver;

    /**
     * Get the default constructor.
     * @param <T> The type of the constructed object.
     * @param type The actual type of the created object.
     * @return The constructor of the given type without any parameter, if any exists.
     */
    public static<T> Optional<Constructor<? extends T>> getDefaultConstructor(Class<? extends T> type) {
        try {
            return Optional.ofNullable(type.getConstructor());
        } catch(NoSuchMethodException e) {
            return Optional.empty();
        }
    }

    /**
     * Get converter which tries to generate a new object from value provided to the constructor.
     * The converter first tries to seek a constructor of the type suitable for the parameter supplied
     * to the converter. If such constructor exists, and does return value without exception, the returned
     * value is returned as the converter value. Otherwise, the value of the default constructor without
     * parameters is returned, or an undefined value in case no such construction is possible.
     * 
     * @param <T> The type of the returned objects.
     * @param type THe type of the created converter.
     * @return A converter which tries first to create object of type with constructor using the value
     *  provided as construction paramter, and if that fails, the default constructor.
     * @throws IllegalArgumentException The given class was not suitable.
     */
    public static<T> ValueConverter<T> getConstructorGenerator(Class<? extends T> type) throws IllegalArgumentException {
        if (type == null) throw new IllegalArgumentException("Invalid type", new NullPointerException("Type muust be defined"));
        final Optional<Constructor<? extends T>> defaultConstructor = getDefaultConstructor(type);
        return (Object object) -> {
            try {
                if (object == null) {
                    return null;
                } else {
                    Class<?> objectType = object.getClass();
                    Constructor<? extends T> constructor = type.getConstructor(objectType);
                    try {
                        return constructor.newInstance(objectType.cast(object));
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        return null;
                    }
                }
            } catch(NoSuchMethodException nsme) {
                // The object constructor does not exist.
                try {
                    return defaultConstructor.get().newInstance();
                } catch (Exception e) {
                    return null;
                }
            }
        };
    }

    /**
     * A predicate testing that a class is assignable from given class.
     */
    public static class TypeTester<T> implements Predicate<Class<?>> {

        private final Class<? extends T> type;

        public TypeTester(Class<? extends T> type) throws IllegalArgumentException {
            if (type == null) throw new IllegalArgumentException("Undefined type");
            this.type = type;
        }

        @Override
        public boolean test(Class<?> tested) {
            if (tested == null) return false;
            return type.isAssignableFrom(tested);
        }

        /**
         * Is the given object sutiable for the predicate.
         * @param tested The tested object.
         * @return True, if and only if the given tested is
         * assignable to a variable of the class.
         */
        public boolean testInstance(Object tested) {
            return tested == null || test(tested.getClass());
        }

        /** 
         * Cast an object into the tested class.
         * @param source The source object casted to the type.
         * @return The source as an instance of the type.
         * @throws ClassCastException the source could not be casted
         * to the type.
         */
        public T castInstance(Object source) throws ClassCastException {
            return (T)type.cast(source);
        }

        /**
         * Overrides the to string to contain the class name.
         * @return The string representation of the object.
         */
        public String toString() {
            return String.format("%s[%s]", super.toString(), type.getName());
        }
    }

    /**
     * Create a class tester testing a class with a class or acceptiong all.
     * @param type The type the predicate passes. 
     * @return An undefined value, if the type is undefined. Otherwise a predicate
     * accepting only classes castable to the given type.
     */
    public static Predicate<Class<?>> isInstanceOrNull(Class<?> type) {
        if (type == null) return null;
        return new TypeTester(type);
    }

    /**
     * Get the converter function casting to the given type.
     * @param <T> The type of the casted value.
     * @param type The type into which the values are casted.
     * @return AN undefined value, if the given type is undefined.
     * Otherwise a function casting any object to the given type or throwing
     * exception.
     */
    public static<T> ValueConverter<T> getCastOrNull(Class<? extends T> type) {
        if (type == null) return null;
        return (Object source) -> {
            try {
                return type.cast(source);
            } catch(ClassCastException cce) {
                throw new IllegalArgumentException("Incompatible type of the converted value", cce);
            }
        };
    }

    /**
     * Create a generic parameter resolver generating an object.
     * @param generatedObject The generated object.
     */
    @SuppressWarnings("unchecked")
    public GenericTypedParameterResolver(T generatedObject) {
        this( (Class<? extends T>)(generatedObject == null ? null : generatedObject.getClass()), generatedObject);
    }

    /**
     * Create a generic paramter resolver generating the default constructor.
     * @param parameterClass 
     * @throws IllegalArgumentException The given class does not have default constructor, or generic
     * object cosntructor.
     */
    public GenericTypedParameterResolver(Class<? extends T> parameterClass) {
        this(isInstanceOrNull(parameterClass), null, getConstructorGenerator(parameterClass));
    }

    /**
     * Create a generic type resolver for either a type or the type of an object.
     * @param parameterClass The parameter type.
     * @param generatedObject The object, whose class is used as type in case parameter class is undefined.
     */
    public GenericTypedParameterResolver(Class<? extends T> parameterClass, T generatedObject) {
        this(isInstanceOrNull(parameterClass), null, (Object object)->(generatedObject));
    }

    /**
     * Create a generic typed parameter resolver using a type tester, an object tester, and a value converter.
     * @param classTester The tester testing type of the object. If the value is undefined, all types are accpeted.
     * @param objectTester The object tester testing the parameteer value. An undefined value accepts all objects.
     * @param converter The converter converting any object passing class tester and object tester into the
     *  wanted class.
     * @throws IllegalArgumentException The converter was not given.
     */
    public GenericTypedParameterResolver(Predicate<Class<?>> classTester, Predicate<Object> objectTester, Function<Object, T> converter) {
        if (converter == null) throw new IllegalArgumentException("Converter is required!");
        this.typeTester = Objects.requireNonNullElse(classTester, object -> (true));
        this.objectTester = Objects.requireNonNullElse(objectTester, object -> (true));
        this.objectResolver = ( converter instanceof ValueConverter<T> ? (ValueConverter<T>)converter : 
        (Object object) -> {
            try {
                return converter.apply(object);
            } catch(IllegalArgumentException e) {
                throw e;
            } catch(ClassCastException cce) {
                throw new IllegalArgumentException("Incompatible converted valeu", cce);
            }
        });
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
    throws ParameterResolutionException {
        if (supportsParameter(parameterContext, extensionContext)) {
            return objectResolver.apply(parameterContext.getTarget().orElse(null));
        } else {
            throw new ParameterResolutionException("Invalid parameter context");
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
    throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();
        boolean validType = typeTester.test(type);
        boolean validObject = objectTester.test(parameterContext.getTarget().orElse(null));
        return validType && validObject;
    }

}
