package com.lobbyforplayers.service;

import com.lobbyforplayers.domain.Chats;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Chats}.
 */
public interface ChatsService {
    /**
     * Save a chats.
     *
     * @param chats the entity to save.
     * @return the persisted entity.
     */
    Chats save(Chats chats);

    /**
     * Partially updates a chats.
     *
     * @param chats the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Chats> partialUpdate(Chats chats);

    /**
     * Get all the chats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Chats> findAll(Pageable pageable);

    /**
     * Get the "id" chats.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Chats> findOne(String id);

    /**
     * Delete the "id" chats.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
