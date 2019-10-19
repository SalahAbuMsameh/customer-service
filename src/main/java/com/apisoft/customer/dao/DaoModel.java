package com.apisoft.customer.dao;

import java.util.List;
import java.util.Optional;

/**
 * Dao model behaviour.
 *
 * @author Salah Abu Msameh
 */
interface DaoModel<T> {

    /**
     * find entity by the given id.
     *
     * @param id entity id
     * @return entity object associated with the given id
     */
    Optional<T> findById(Long id);

    /**
     * Finds all entities.
     *
     * @return list of all existing entities
     */
    List<T> findAll();

    /**
     * Save given entity.
     *
     * @param entity to be persisted to the database
     * @return persisted entity
     */
    T save(T entity);

    /**
     * Updates the given entity.
     *
     * @param entity entity to be updated to the database
     * @return updated entity
     */
    T update(T entity);

    /**
     * Deletes the given entity.
     *
     * @param entity to be deleted from the database
     * @return deleted entity
     */
    T delete(T entity);
}
