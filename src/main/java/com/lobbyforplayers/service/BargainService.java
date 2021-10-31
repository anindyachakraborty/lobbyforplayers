package com.lobbyforplayers.service;

import com.lobbyforplayers.domain.Bargain;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Bargain}.
 */
public interface BargainService {
    /**
     * Save a bargain.
     *
     * @param bargain the entity to save.
     * @return the persisted entity.
     */
    Bargain save(Bargain bargain);

    /**
     * Partially updates a bargain.
     *
     * @param bargain the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Bargain> partialUpdate(Bargain bargain);

    /**
     * Get all the bargains.
     *
     * @return the list of entities.
     */
    List<Bargain> findAll();

    /**
     * Get the "id" bargain.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Bargain> findOne(String id);

    /**
     * Delete the "id" bargain.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
