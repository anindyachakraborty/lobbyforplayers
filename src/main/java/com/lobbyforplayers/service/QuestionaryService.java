package com.lobbyforplayers.service;

import com.lobbyforplayers.domain.Questionary;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Questionary}.
 */
public interface QuestionaryService {
    /**
     * Save a questionary.
     *
     * @param questionary the entity to save.
     * @return the persisted entity.
     */
    Questionary save(Questionary questionary);

    /**
     * Partially updates a questionary.
     *
     * @param questionary the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Questionary> partialUpdate(Questionary questionary);

    /**
     * Get all the questionaries.
     *
     * @return the list of entities.
     */
    List<Questionary> findAll();

    /**
     * Get the "id" questionary.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Questionary> findOne(String id);

    /**
     * Delete the "id" questionary.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
