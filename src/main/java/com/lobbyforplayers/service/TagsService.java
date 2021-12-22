package com.lobbyforplayers.service;

import com.lobbyforplayers.domain.Tags;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Tags}.
 */
public interface TagsService {
    /**
     * Save a tags.
     *
     * @param tags the entity to save.
     * @return the persisted entity.
     */
    Tags save(Tags tags);

    /**
     * Partially updates a tags.
     *
     * @param tags the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Tags> partialUpdate(Tags tags);

    /**
     * Get all the tags.
     *
     * @return the list of entities.
     */
    List<Tags> findAll();

    /**
     * Get the "id" tags.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tags> findOne(String id);

    /**
     * Delete the "id" tags.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
