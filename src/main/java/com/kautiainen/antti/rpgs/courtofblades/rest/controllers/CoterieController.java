package com.kautiainen.antti.rpgs.courtofblades.rest.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.restexpress.Request;
import org.restexpress.exception.BadRequestException;

import com.kautiainen.antti.rpgs.courtofblades.model.Coterie;
import com.kautiainen.antti.rpgs.courtofblades.model.CoterieUpgrade;
import com.kautiainen.antti.rpgs.courtofblades.model.HouseModel;
import com.kautiainen.antti.rpgs.courtofblades.model.SpecialAbility;
import com.kautiainen.antti.rpgs.courtofblades.rest.CoterieConstants;
import com.kautiainen.antti.rpgs.courtofblades.rest.DBDataSource;

public class CoterieController implements Controller<Integer, Coterie> {


    /**
     * Coterie data source implements coterie service.
     */
    public static interface CoterieDataSource<ID> extends Controller.Service<ID, Coterie> {

        /**
         * Get coterie with index.
         * @param index The index of the seeked coterie.
         * @return The coterie, if any exists.
         * @throws UnsupportedOperationException The operations is not supported.
         */
        default Optional<Coterie> fetch(ID index) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Unimplemented method 'fetch'");            
        };

        /**
         * Delete coterie from the source.
         * @param index The index of the deleted coterie.
         * @return True, if and only if the coterie was deleted.
         * @throws UnsupportedOperationException The operations is not supported.
         */
        default boolean delete(ID index) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Unimplemented method 'delete'");
        }

        /**
         * Update coterie.
         * @param index The key of hte value.
         * @param value The updated value.
         * @return True, if and only if the coterie was updated.
         * @throws IllegalArgumentException Either the coterie or index was invalid.
         * @throws UnsupportedOperationException The operations is not supported.
         */
        default boolean update(ID index, Coterie value) throws IllegalArgumentException, UnsupportedOperationException {
            throw new UnsupportedOperationException("Unimplemented method 'update'");
        }


        /**
         * Get all coteries of the data source.
         * @return The list of all data source members.
         */
        default List<Coterie> fetchAll() {
            return Collections.emptyList();
        }
    }

    
    /**
     * Data source storing the coteries in memory.
     */
    protected static class MemoryDataSouce implements CoterieDataSource<Integer> {

        private ConcurrentHashMap<Integer, Coterie> entries;

        public MemoryDataSouce() {
            entries  = new ConcurrentHashMap<>(1);
        }

        public MemoryDataSouce(ConcurrentHashMap<Integer, Coterie> entries) {
            this.entries = entries;
        }

        public MemoryDataSouce(java.util.Map<Integer, Coterie> entries) {
            entries = new ConcurrentHashMap<>(entries);
        }

        @Override
        public synchronized boolean delete(Integer index) throws UnsupportedOperationException {
            return (entries.remove(index) != null);
        }

        @Override
        public synchronized Optional<Coterie> fetch(Integer index) throws UnsupportedOperationException {
            return Optional.ofNullable(entries.get(index));
        }

        @Override
        public synchronized List<Coterie> fetchAll() {
            return new ArrayList<>(entries.values());
        }

        @Override
        public synchronized boolean update(Integer index, Coterie value)
                throws IllegalArgumentException, UnsupportedOperationException {
            // If the update value is equal to original, nothing happened.
            return (!Objects.equals(value, entries.remove(index)));        
        }

        @Override
        public synchronized Integer create(Coterie content) throws IllegalArgumentException, UnsupportedOperationException {
            synchronized (entries) {
                // Getting the next available index for the index.
                int result = entries.keySet().stream().max(Comparator.naturalOrder()).orElse(0) +1;
                entries.put(result, content);
                return result;
            }
        }

        
    }

    /**
     * Data soruce storing the coteries in database.
     */
    protected static class DatabaseDataSource implements CoterieDataSource<Integer> {

        private final DBDataSource connection;

        /**
         * Service handling database operation of special abilities.
         */
        private final Controller.Service<Connection, SpecialAbility> specialAbilities = 
        new Controller.Service<>() {
            
        };

        /**
         * Service handling database operations for coterie upgrades.
         */
        private final Controller.Service<Connection, CoterieUpgrade> coterieUpgrades =
        new Controller.Service<>() {
            
        };
        /**
         * Service handling database operations for house entries.
         */
        private final Controller.Service<Connection, HouseModel> houses = 
        new Controller.Service<>() {
            
        };

        public DatabaseDataSource(DBDataSource dataSource) throws IllegalArgumentException {
            if (dataSource == null) throw new IllegalArgumentException("Invalid data source");
            this.connection = dataSource;
        }

        @Override
        public boolean delete(Integer index) throws UnsupportedOperationException {
            try {
                Connection con = this.connection.getConnection();
                con.beginRequest();
                PreparedStatement statement = con.prepareStatement("DELETE FROM coteries WHERE id=?");
                statement.setInt(1, index);
                if (statement.executeUpdate() == 0) {
                    // The deletion did not happen.
                    // TODO: Log error.
                    con.rollback();
                    return false;
                } else {
                    // TODO: Log success.
                }
                con.endRequest();
                con.commit();
                return true;
            } catch (SQLException sqle) {
                // TODO: Logging error
                throw new IllegalStateException("Could not delete the entry", sqle);
            }
        }

        @Override
        public List<Coterie> fetchAll() {
            // TODO Auto-generated method stub
            return CoterieDataSource.super.fetchAll();
        }

        @Override
        public Integer create(Coterie content) throws IllegalArgumentException, UnsupportedOperationException {
            // TODO Auto-generated method stub
            return CoterieDataSource.super.create(content);
        }

        
        
    }

    private final CoterieDataSource<Integer> coteries;

    @Override
    public CoterieDataSource<Integer> getService() {
        return this.coteries;
    }

    

    @Override
    public Coterie getEntity(Request request) throws BadRequestException {
        return request.getBodyAs(getEntityType(), "Invalid coterie");
    }



    @Override
    public Class<? extends Coterie> getEntityType() {
        return Coterie.class;
    }



    @Override
    public Integer getIndex(Request request) throws BadRequestException {
        try {
            return Integer.parseInt(request.getHeader(CoterieConstants.Url.COTERIE_ID_PARAMETER));
        } catch(NullPointerException npe) {
            throw new BadRequestException("Missing coterie index");
        } catch(NumberFormatException nfe) {
            throw new BadRequestException("Invalid coterie index", nfe);
        }
    }



    /**
     * Create a new coterie controller.
     */
    public CoterieController() {
        coteries = new MemoryDataSouce();
    }

    /**
     * Create coterie controller with database data source.
     * @param dataSource The database data source.
     */
    public CoterieController(DBDataSource dataSource) throws IllegalArgumentException {
        coteries = new DatabaseDataSource(dataSource);
    }



}
