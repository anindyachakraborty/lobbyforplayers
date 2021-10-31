package com.lobbyforplayers.repository;

import com.lobbyforplayers.domain.Chats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Chats entity.
 */
@Repository
public interface ChatsRepository extends MongoRepository<Chats, String> {}
