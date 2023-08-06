package com.kautiainen.antti.rpgs.courtofblades.rest.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.kautiainen.antti.rpgs.courtofblades.model.Clock;

/**
 * A clock service storing the clocks into memory.
 */
public class MemoryClockService implements Controller.Service<Integer, Clock> {

    private java.util.Map<Integer, Clock> entities = new java.util.HashMap<>();

    /**
     * Create a new empty memory service.
     */
    public MemoryClockService() {

    }

    public MemoryClockService(java.util.Map<Integer, ? extends Clock> intialClocks) {
        entities.putAll(intialClocks);
    }

    /**
     * Create a new index.
     * @return A new index for the content. 
     */
    protected synchronized Integer createNewIndex() {
        return entities.keySet().stream().max(Comparator.naturalOrder()).orElse(0) +1;
    }

    @Override
    public synchronized Integer create(Clock content) throws IllegalArgumentException, UnsupportedOperationException {
        if (validContent(content)) {
            Integer index = createNewIndex();
            entities.put(index, content);
            return index;
        } else {
            throw new IllegalArgumentException("Invalid content");
        }
    }

    /**
     * Test validity of the content.
     * @param content The tested content.
     * @return True, if and only if the given content is valid.
     */
    private boolean validContent(Clock content) {
        return (content != null);
    }

    @Override
    public synchronized boolean delete(Integer index) throws UnsupportedOperationException {
        if (entities.containsKey(index)) {
            return (entities.remove(index) != null);
        } else {
            return false;
        }
    }

    @Override
    public synchronized Optional<Clock> fetch(Integer index) throws UnsupportedOperationException {
        return Optional.ofNullable(entities.get(index));
    }

    @Override
    public synchronized List<Clock> fetchAll() {
        synchronized (entities) {
            return new ArrayList<>(entities.values());
        }
    }

    @Override
    public synchronized boolean update(Integer index, Clock value)
            throws IllegalArgumentException, UnsupportedOperationException {
        if (validContent(value)) {
            Clock replaced = entities.get(index);
            return Objects.equals(replaced, value);
        } else {
            throw new IllegalArgumentException("Invalid content");
        }
    }

    
}