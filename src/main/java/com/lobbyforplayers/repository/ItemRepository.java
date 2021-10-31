package com.lobbyforplayers.repository;

import com.lobbyforplayers.domain.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Item entity.
 */
@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    @Query(
        value = "select distinct item from Item item left join fetch item.tags"
        // countQuery = "select count(distinct item) from Item item"
    )
    Page<Item> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct item from Item item left join fetch item.tags")
    List<Item> findAllWithEagerRelationships();

    @Query("select item from Item item left join fetch item.tags where item.id =:id")
    Optional<Item> findOneWithEagerRelationships(@Param("id") String id);
}
