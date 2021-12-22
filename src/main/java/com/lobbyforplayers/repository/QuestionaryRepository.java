package com.lobbyforplayers.repository;

import com.lobbyforplayers.domain.Questionary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Questionary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionaryRepository extends MongoRepository<Questionary, String> {}
