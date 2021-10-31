package com.lobbyforplayers.repository;

import com.lobbyforplayers.domain.Tags;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tags entity.
 */
@Repository
public interface TagsRepository extends MongoRepository<Tags, String> {}
