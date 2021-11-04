package com.lobbyforplayers.service;

import com.lobbyforplayers.domain.Reviews;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Reviews}.
 */
public interface ReviewsService {
    /**
     * Save a reviews.
     *
     * @param reviews the entity to save.
     * @return the persisted entity.
     */
    Reviews save(Reviews reviews);

    /**
     * Partially updates a reviews.
     *
     * @param reviews the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Reviews> partialUpdate(Reviews reviews);

    /**
     * Get all the reviews.
     *
     * @return the list of entities.
     */
    List<Reviews> findAll();

    /**
     * Get the "id" reviews.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Reviews> findOne(String id);

    /**
     * Delete the "id" reviews.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
