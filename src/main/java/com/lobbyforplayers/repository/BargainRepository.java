package com.lobbyforplayers.repository;

import com.lobbyforplayers.domain.Bargain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Bargain entity.
 */
@Repository
public interface BargainRepository extends MongoRepository<Bargain, String> {}
