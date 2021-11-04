package com.lobbyforplayers.service;

import com.lobbyforplayers.domain.Payments;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Payments}.
 */
public interface PaymentsService {
    /**
     * Save a payments.
     *
     * @param payments the entity to save.
     * @return the persisted entity.
     */
    Payments save(Payments payments);

    /**
     * Partially updates a payments.
     *
     * @param payments the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Payments> partialUpdate(Payments payments);

    /**
     * Get all the payments.
     *
     * @return the list of entities.
     */
    List<Payments> findAll();

    /**
     * Get the "id" payments.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Payments> findOne(String id);

    /**
     * Delete the "id" payments.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
