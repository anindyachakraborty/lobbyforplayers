package com.lobbyforplayers.repository;

import com.lobbyforplayers.domain.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
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

    @Aggregation(pipeline = { "{ '$group': { '_id' : '$game_name' } }" })
    List<String> findDistinctGameName();

    // get minimum price
    @Query("{'game_name': { $in:?0 } }")
    Page<Item> findMinPriceForGames(List<String> gameNames, Pageable pageable);

    // get maximum price
    @Query("{'game_name': { $in:?0 } }")
    Page<Item> findMaxPriceForGames(List<String> gameName, Pageable pageable);

    @Query("{'game_name': {$in: ?0},'price': {$gte: ?1, $lte: ?2}, 'description' : {$regex : ?3, $options : 'i'}, 'listed_flag' : true}")
    Page<Item> findAllbyGameNameAndPriceRange(
        List<String> gameName,
        Double minPrice,
        Double maxPrice,
        String description,
        Pageable pageable
    );

    @Query(
        value = "{'game_name': {$in: ?0},'price': {$gte: ?1, $lte: ?2}, 'description' : {$regex : ?3, $options : 'i'}, 'listed_flag' : true}",
        count = true
    )
    Long findCountbyGameNameAndPriceRange(List<String> gameName, Double minPrice, Double maxPrice, String description);
}
