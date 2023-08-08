package com.kautiainen.antti.rpgs.courtofblades.rest.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.exception.BadRequestException;

import com.kautiainen.antti.rpgs.courtofblades.model.Identified;

import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * Controller interface gets rest queries and implements then.
 * @param <ID> The identifier type of the controller.
 * @param <E> The resource type of the controller.
 */
public interface Controller<ID, E> {

    /**
     * Generic service for serving values to controllers.
     */
    public static interface Service<ID, CONTENT> {
        /**
         * Get content with index.
         * @param index The index of the seeked content.
         * @return The content, if any exists.
         * @throws UnsupportedOperationException The operations is not supported.
         */
        default Optional<CONTENT> fetch(ID index) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Unimplemented method 'fetch'");            
        };

        /**
         * Delete content from the source.
         * @param index The index of the deleted content.
         * @return True, if and only if the content was deleted.
         * @throws UnsupportedOperationException The operations is not supported.
         */
        default boolean delete(ID index) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Unimplemented method 'delete'");
        }

        /**
         * Update content.
         * @param index The key of hte value.
         * @param value The updated value.
         * @return True, if and only if the content was updated.
         * @throws IllegalArgumentException Either the content or index was invalid.
         * @throws UnsupportedOperationException The operations is not supported.
         */
        default boolean update(ID index, CONTENT value) throws IllegalArgumentException, UnsupportedOperationException {
            throw new UnsupportedOperationException("Unimplemented method 'update'");
        }


        /**
         * Get all contents of the data source.
         * @return The list of all data source members.
         */
        default List<CONTENT> fetchAll() {
            return Collections.emptyList();
        }

        /**
         * Create a new content.
         * @param content The created content.
         * @return The identifier assigned to the created value.
         * @throws IllegalArgumentException The content was invalid.
         * @throws UnsupportedOperationException The operation is nto supported.
         */
        default ID create(CONTENT content) throws IllegalArgumentException, UnsupportedOperationException {
            throw new UnsupportedOperationException("Unimplemented method 'create'");
        }

    }


    /**
     * Get the entry type.
     * @return The entry type.
     */
    public Class<? extends E> getEntityType();

    /**
     * Get the service handling the controlled resource.
     * @return The service handling the controlled resource.
     */
    public Service<ID, E> getService();

    /**
     * Get identifier from request.
     * @param request the request.
     * @return The identifier from the request.
     * @throws BadRequestException The request did not contain identifier.
     */
    public ID getIndex(Request request) throws BadRequestException;

    /**
     * Get resource from request.
     * @param request The request.
     * @return The entity from request.
     * @throws BadRequestException The request did nto contain valid resource
     */
    public E getEntity(Request request)throws BadRequestException;

    /**
     * Handle creation of a new resource.
     * @param request The handled request.
     * @param response The response.
     * @return The created resource.
     */
    default Identified<ID, E> create(Request request, Response response) {
        E entity = getEntity(request);
        ID index = getService().create(entity);
        return Identified.from(index, getService().fetch(index).get());
    }

    /**
     * Handle fetching an existing resource.
     * If resource does not exist, sets the status of the response
     * to {@link HttpResponseStatus.NOT_FOUND} to fulfil the HTTP
     * specification.
     * @param request The handled request.
     * @param response The response.
     * @return The queried resource.
     * @implNote Default impolementation assumes index based request.
     */
    default E read(Request request, Response response) {
        ID index = getIndex(request);
        Optional<E> result = getService().fetch(index);
        if (result.isPresent()) {
            return result.get();
        } else {
            response.setResponseStatus(HttpResponseStatus.NOT_FOUND);
            return null;
        }
    }

    /**
     * Handle updating an existing resource.
     * If resource does not exist, sets the status of the response
     * to {@link HttpResponseStatus.NOT_FOUND} to fulfil the HTTP
     * specification.
     * @param request The handled request.
     * @param response The response.
     * @return The queried resource.
     * @implNote Default impolementation assumes index based request.
     */
    default void update(Request request, Response response) {
        ID index = getIndex(request);
        E content = getEntity(request);
        if (getService().update(index, content)) {
            // Update succeeded
            response.setResponseNoContent();
        } else {
            // Update did not change the value.
            response.setResponseStatus(HttpResponseStatus.NOT_MODIFIED);
        }
    }


    /**
     * Handle deletion of an existing resource.
     * If deletion did not happen, sets teh response status to not modified
     * as described in the HTTP specification.
     * @param request The handled request.
     * @param response The response.
     */
    default void delete(Request request, Response response) {
        ID index = getIndex(request);
        if (getService().delete(index)) {
            response.setResponseNoContent();
        } else {
            response.setResponseStatus(HttpResponseStatus.NOT_MODIFIED);
        }
    }

    /**
     * Handle fetching all resources.
     * @param request The handled request.
     * @param response The response.
     */
    default List<E> readAll(Request request, Response response) {
        return getService().fetchAll();
    }
}
