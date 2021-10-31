package com.lobbyforplayers.service;

import com.lobbyforplayers.domain.Details;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Details}.
 */
public interface DetailsService {
    /**
     * Save a details.
     *
     * @param details the entity to save.
     * @return the persisted entity.
     */
    Details save(Details details);

    /**
     * Partially updates a details.
     *
     * @param details the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Details> partialUpdate(Details details);

    /**
     * Get all the details.
     *
     * @return the list of entities.
     */
    List<Details> findAll();

    /**
     * Get the "id" details.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Details> findOne(String id);

    /**
     * Delete the "id" details.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
