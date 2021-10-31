package com.lobbyforplayers.repository;

import com.lobbyforplayers.domain.Bargain;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Bargain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BargainRepository extends JpaRepository<Bargain, Long> {}
