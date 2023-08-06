package com.kautiainen.antti.rpgs.courtofblades.rest.serialization;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class SortedMapConverter implements Converter {

    @Override
    public boolean canConvert(Class type) {

        return type.equals(TypedMap.class);
    }


    @SuppressWarnings({"unchecked", "unused"}) 
    protected<KEY> void marshalComparator(Class<? extends KEY> keyType, java.util.Comparator<?> comparator, 
    HierarchicalStreamWriter writer, MarshallingContext context) {
        if (comparator == null) throw new ConversionException("Invalid comparator", new NullPointerException());
        try {
            Comparator<? super KEY> keyComparator = (Comparator<? super KEY>)comparator;
        } catch(ClassCastException cce) {
            throw new ConversionException("Invalid comparator", cce);
        }
        writer.startNode("comparator");
        context.convertAnother(comparator);
        writer.endNode();
    }

    @SuppressWarnings("unchecked")
    protected<KEY, VALUE> void marshalEntry(
        Class<? extends KEY> keyType, Class<? extends VALUE> valueType, java.util.Map.Entry<?, ?> entry, 
    HierarchicalStreamWriter writer, MarshallingContext context) {
        try {
            Map.Entry<KEY, VALUE> typedEntry = (Map.Entry<KEY, VALUE>)entry;
            writer.startNode("entry");
            writer.startNode("key");
            context.convertAnother(typedEntry.getKey());
            writer.endNode();;
            writer.startNode("value");
            context.convertAnother(typedEntry.getValue());
            writer.endNode();
            writer.endNode();
        } catch(ClassCastException cce) {
            throw new ConversionException("Invalid entry", cce);
        }
    }

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        if (source instanceof TypedMap<?, ?> typedMap)  {
            // The conversion is possible.
            writer.startNode("typed-map");
            writer.addAttribute("key-type", typedMap.getKeyType().toString());
            writer.addAttribute("value-type", typedMap.getValueType().toString());

            if (source instanceof java.util.SortedMap<?, ?> sortedMap) {
                marshalComparator(typedMap.getKeyType(), sortedMap.comparator(), writer, context);
            }
            writer.startNode("entries");
            typedMap.entrySet().forEach( 
                (Map.Entry<?, ?> entry) -> {
                    marshalEntry(typedMap.getKeyType(), typedMap.getValueType(), entry, writer, context);
                } 
            );
            writer.endNode();
            writer.endNode();
        } else {
            // Marshall fails
            throw new ConversionException("Incompatible class", new ClassCastException());
        }
    }

    public Object unmarshallMap(HierarchicalStreamReader reader, UnmarshallingContext context) {
        // Getting the types of the keys.
        String keyTypeString = reader.getAttribute("key-type");
        String valueTypeString = reader.getAttribute("value-type");
        if (keyTypeString == null || keyTypeString.isEmpty()) {
            keyTypeString = "java.lang.Object";
        }
        if (valueTypeString == null || valueTypeString.isEmpty()) {
            valueTypeString = "java.lang.Object";
        }
        Class<?> keyClass, valueClass;
        try {
            keyClass = ClassLoader.getSystemClassLoader().loadClass(keyTypeString);
        } catch(Exception e) {
            throw new ConversionException("Invalid key type: " + Objects.requireNonNullElse(keyTypeString, "Not given"), e);
        }
        try {
            valueClass = ClassLoader.getSystemClassLoader().loadClass(valueTypeString);
        } catch(Exception e) {
            throw new ConversionException("Invalid value type: " + Objects.requireNonNullElse(valueTypeString, "Not given"), e);
        }
        return unmarshalTypedMap(keyClass, valueClass, reader, context);

    }

    public<KEY, VALUE> java.util.Map.Entry<KEY, VALUE> unmarshallEntry(
        Class<? extends KEY> keyType, Class<? extends VALUE> valueType,
        HierarchicalStreamReader reader, UnmarshallingContext context 
    ) {
        // TODO: Add entry unmarshalling support.
        Map.Entry<KEY, VALUE> entry = new java.util.AbstractMap.SimpleEntry(null, null);
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            String nodeName = reader.getNodeName();
            switch (nodeName) {
                case "kay": 
                    reader.moveDown();

                    reader.moveUp();
                    break;
                case "value":
                    reader.moveDown();

                    reader.moveUp();
                default:
                // Unknown node. 
            }
            reader.moveUp();
        }
        return null;
    }

    public<KEY> Comparator<? super KEY> unmarshallComparator(Class<? extends KEY> keyType, 
        HierarchicalStreamReader reader, UnmarshallingContext context 
    ) {
        // TODO: Add comparator unmarshalling support.
        return null;
    }


    @SuppressWarnings("unchecked")
    public<KEY, VALUE> TypedMap<KEY, VALUE> unmarshalTypedMap(Class<? extends KEY> keyType, Class<? extends VALUE> valueType,
        HierarchicalStreamReader reader, UnmarshallingContext context 
    ) {
        Comparator<?> comparator = null;
        ArrayList<Object> entries = new ArrayList<>();

        while (reader.hasMoreChildren()) {
            reader.moveDown();
            String nodeName = reader.getNodeName();
            switch (nodeName) {
                case "entries": 
                    entries.add(unmarshallEntry(keyType, valueType, reader, context));
                    break;
                case "comparator":
                    comparator = unmarshallComparator(keyType, reader, context);
                    break;
                default:
                // Unknown node. 
            }
            reader.moveUp();
        }
        if (comparator != null) {
            // Creating a sorted map based typed map.
            // TODO: Add support for sorted maps.
            return null;
        } else {
            // Creating a unsorted map based typed map.
            final TypedMap<KEY, VALUE> result = new TypedMap<>(keyType, valueType);
            Consumer<Object> entryHandler = entry -> {
                if (entry != null && entry instanceof Map.Entry<?, ?>) {
                    try {
                        final Map.Entry<KEY, VALUE> mapEntry = (Map.Entry<KEY, VALUE>)entry;
                        result.put(mapEntry.getKey(), mapEntry.getValue());
                    } catch(ClassCastException | IllegalArgumentException | NullPointerException e) {
                        throw new ConversionException("Invalid map entry", e);
                    }
                }

            };
            entries.stream().forEach(entryHandler);
            return result;
        }
    }
        


    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unmarshal'");
    }
    
}
