package com.lobbyforplayers.repository;

import com.lobbyforplayers.domain.Chats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Chats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatsRepository extends MongoRepository<Chats, String> {}
