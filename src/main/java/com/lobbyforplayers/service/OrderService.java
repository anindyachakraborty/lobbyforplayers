package com.lobbyforplayers.service;

import com.lobbyforplayers.domain.Order;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Order}.
 */
public interface OrderService {
    /**
     * Save a order.
     *
     * @param order the entity to save.
     * @return the persisted entity.
     */
    Order save(Order order);

    /**
     * Partially updates a order.
     *
     * @param order the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Order> partialUpdate(Order order);

    /**
     * Get all the orders.
     *
     * @return the list of entities.
     */
    List<Order> findAll();
    /**
     * Get all the Order where Item is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Order> findAllWhereItemIsNull();

    /**
     * Get the "id" order.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Order> findOne(Long id);

    /**
     * Delete the "id" order.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
