package com.lobbyforplayers.service;

import com.lobbyforplayers.domain.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Item}.
 */
public interface ItemService {
    /**
     * Save a item.
     *
     * @param item the entity to save.
     * @return the persisted entity.
     */
    Item save(Item item);

    /**
     * Partially updates a item.
     *
     * @param item the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Item> partialUpdate(Item item);

    /**
     * Get all the items.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Item> findAll(Pageable pageable);

    /**
     * Get all the items with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Item> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" item.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Item> findOne(String id);

    /**
     * Delete the "id" item.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Get all Distinct Game Names
     * @return list of Distinct Game Names
     */
    List<String> findAllGameName();

    /**
     * Get minimum price of an item
     * @param list of game names
     */
    Double getMinimumPriceForGames(List<String> games);

    /**
     * Get maximum price of an item
     * @param list of game names
     */
    Double getMaximumPriceForGames(List<String> games);
    /**
     * Get all Items for a given game and price range (inclusive)
     * @param list of games
     * @param minimum price of the user
     * @param maximum price of the user
     * @return all the items
     */
    Page<Item> getAllItemWithFilters(List<String> games, Double minPrice, Double maxPrice, String description, Pageable pageable);

    /**
     * Get all Item Count for a given game and price range (inclusive)
     * @param list of games
     * @param minimum price of the user
     * @param maximum price of the user
     * @return all the item count
     */
    Long getAllItemCountWithFilters(List<String> games, Double minPrice, Double maxPrice, String description);
}
