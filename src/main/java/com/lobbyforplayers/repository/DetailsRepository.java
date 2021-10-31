package com.lobbyforplayers.repository;

import com.lobbyforplayers.domain.Details;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Details entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailsRepository extends JpaRepository<Details, Long> {}
