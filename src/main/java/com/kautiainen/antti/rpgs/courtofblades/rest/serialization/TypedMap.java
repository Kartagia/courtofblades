package com.kautiainen.antti.rpgs.courtofblades.rest.serialization;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Map with types of keys and values.
 */
public class TypedMap<KEY, VALUE> implements Serializable, java.util.Map<KEY, VALUE> {
    
    public static interface SerializedMap<KEY, VALUE> extends Serializable, java.util.Map<KEY, VALUE> {

        static<KEY, VALUE> SerializedMap<KEY, VALUE> from(java.util.Map<KEY, VALUE> source) {
            if (source != null && source instanceof Serializable) {
                return new SerializedMap<KEY,VALUE>() {

                    @Override
                    public int size() {
                        return source.size();
                    }

                    @Override
                    public boolean isEmpty() {
                        return source.isEmpty();
                    }

                    @Override
                    public boolean containsKey(Object key) {
                        return source.containsKey(key);
                    }

                    @Override
                    public boolean containsValue(Object value) {
                        return source.containsValue(value);
                    }

                    @Override
                    public VALUE get(Object key) {
                        return source.get(key);
                    }

                    @Override
                    public VALUE put(KEY key, VALUE value) {
                        return source.put(key, value);
                    }

                    @Override
                    public VALUE remove(Object key) {
                        return source.remove(key);
                    }

                    @Override
                    public void putAll(Map<? extends KEY, ? extends VALUE> m) {
                        source.putAll(m);
                    }

                    @Override
                    public void clear() {
                        source.clear();
                    }

                    @Override
                    public Set<KEY> keySet() {
                        return source.keySet();
                    }

                    @Override
                    public Collection<VALUE> values() {
                        return source.values();
                    }

                    @Override
                    public Set<Entry<KEY, VALUE>> entrySet() {
                        return source.entrySet();
                    }
                    
                };
            } else {
                throw new IllegalArgumentException("Invalid source map");
            }
        }

    }

    /**
     * The serialized map storing the values.
     */
    private SerializedMap<KEY, VALUE> source;

    private Class<? extends KEY> keyType;

    private Class<? extends VALUE> valueType;

    public TypedMap(java.util.Map<KEY, VALUE> source, Class<? extends KEY> keyType, Class<? extends VALUE> valueType) 
    throws IllegalArgumentException {
        this(keyType, valueType);
        if (source == null) throw new IllegalArgumentException("Invalid source", new NullPointerException());
        putAll(source);
    }

    public TypedMap(Class<? extends KEY> keyType, Class<? extends VALUE> valueType) throws IllegalArgumentException {
        if (keyType == null) throw new IllegalArgumentException("Invalid key type", new NullPointerException());
        if (valueType == null) throw new IllegalArgumentException("Invalid value type", new NullPointerException());
        this.keyType = keyType;
        this.valueType = valueType;
        this.source = SerializedMap.from(new java.util.HashMap<>());
    }

    /**
     * Get the key type of the map.
     * @return Always defined key type of the map.
     */
    public final Class<? extends KEY> getKeyType() {
        return keyType;
    }

    /**
     * Get the valeu type of the map.
     * @return Always defined value type of the map.
     */
    public final Class<? extends VALUE> getValueType() {
        return valueType;
    }


                    @Override
                    public int size() {
                        return source.size();
                    }

                    @Override
                    public boolean isEmpty() {
                        return source.isEmpty();
                    }

                    @Override
                    public boolean containsKey(Object key) {
                        return source.containsKey(key);
                    }

                    @Override
                    public boolean containsValue(Object value) {
                        return source.containsValue(value);
                    }

                    @Override
                    public VALUE get(Object key) {
                        return source.get(key);
                    }

                    @Override
                    public VALUE put(KEY key, VALUE value) {
                        return source.put(key, value);
                    }

                    @Override
                    public VALUE remove(Object key) {
                        return source.remove(key);
                    }

                    @Override
                    public void putAll(Map<? extends KEY, ? extends VALUE> m) {
                        source.putAll(m);
                    }

                    @Override
                    public void clear() {
                        source.clear();
                    }

                    @Override
                    public Set<KEY> keySet() {
                        return source.keySet();
                    }

                    @Override
                    public Collection<VALUE> values() {
                        return source.values();
                    }

                    @Override
                    public Set<Entry<KEY, VALUE>> entrySet() {
                        return source.entrySet();
                    }

}
