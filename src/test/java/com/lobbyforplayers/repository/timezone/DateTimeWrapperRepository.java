package com.lobbyforplayers.repository.timezone;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link DateTimeWrapper} entity.
 */
@Repository
public interface DateTimeWrapperRepository extends MongoRepository<DateTimeWrapper, String> {}
