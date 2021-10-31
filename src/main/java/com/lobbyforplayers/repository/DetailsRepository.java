package com.lobbyforplayers.repository;

import com.lobbyforplayers.domain.Details;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Details entity.
 */
@Repository
public interface DetailsRepository extends MongoRepository<Details, String> {}
