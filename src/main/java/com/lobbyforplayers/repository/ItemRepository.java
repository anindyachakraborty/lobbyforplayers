package com.lobbyforplayers.repository;

import com.lobbyforplayers.domain.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Item entity.
 */
@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    @Query("{}")
    Page<Item> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Item> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Item> findOneWithEagerRelationships(String id);

    // @Query("{'id': ?0}")
    List<String> findDistinctGameName();
}
