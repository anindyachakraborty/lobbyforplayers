package com.lobbyforplayers.repository;

import com.lobbyforplayers.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Order entity.
 */
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {}
