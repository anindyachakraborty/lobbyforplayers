package com.lobbyforplayers.repository;

import com.lobbyforplayers.domain.Chats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Chats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatsRepository extends JpaRepository<Chats, Long> {}
