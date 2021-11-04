package com.lobbyforplayers.repository;

import com.lobbyforplayers.domain.Reviews;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Reviews entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReviewsRepository extends MongoRepository<Reviews, String> {}
