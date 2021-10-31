package com.lobbyforplayers.repository;

import com.lobbyforplayers.domain.Tags;
import org.springframework.data.mongodb.repository.MongoRepository;
// import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Tags entity.
 */
// @SuppressWarnings("unused")
@Repository
public interface TagsRepository extends MongoRepository<Tags, String> {}
