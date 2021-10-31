package com.lobbyforplayers.repository;

import com.lobbyforplayers.domain.Bargain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Bargain entity.
 */
@Repository
public interface BargainRepository extends MongoRepository<Bargain, String> {}
