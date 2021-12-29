package com.lobbyforplayers.service.impl;

import com.lobbyforplayers.domain.Item;
import com.lobbyforplayers.repository.ItemRepository;
import com.lobbyforplayers.service.ItemService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Item}.
 */
@Service
public class ItemServiceImpl implements ItemService {

    private final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item save(Item item) {
        log.debug("Request to save Item : {}", item);
        return itemRepository.save(item);
    }

    @Override
    public Optional<Item> partialUpdate(Item item) {
        log.debug("Request to partially update Item : {}", item);

        return itemRepository
            .findById(item.getId())
            .map(existingItem -> {
                if (item.getDescription() != null) {
                    existingItem.setDescription(item.getDescription());
                }
                if (item.getViews() != null) {
                    existingItem.setViews(item.getViews());
                }
                if (item.getSellerName() != null) {
                    existingItem.setSellerName(item.getSellerName());
                }
                if (item.getSellerId() != null) {
                    existingItem.setSellerId(item.getSellerId());
                }
                if (item.getListedFlag() != null) {
                    existingItem.setListedFlag(item.getListedFlag());
                }
                if (item.getPrice() != null) {
                    existingItem.setPrice(item.getPrice());
                }
                if (item.getPicturesPath() != null) {
                    existingItem.setPicturesPath(item.getPicturesPath());
                }
                if (item.getLevel() != null) {
                    existingItem.setLevel(item.getLevel());
                }
                if (item.getFixedPrice() != null) {
                    existingItem.setFixedPrice(item.getFixedPrice());
                }
                if (item.getGameName() != null) {
                    existingItem.setGameName(item.getGameName());
                }
                if (item.getPlatform() != null) {
                    existingItem.setPlatform(item.getPlatform());
                }
                if (item.getLanguage() != null) {
                    existingItem.setLanguage(item.getLanguage());
                }

                return existingItem;
            })
            .map(itemRepository::save);
    }

    @Override
    public Page<Item> findAll(Pageable pageable) {
        log.debug("Request to get all Items");
        return itemRepository.findAll(pageable);
    }

    public Page<Item> findAllWithEagerRelationships(Pageable pageable) {
        return itemRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    public Optional<Item> findOne(String id) {
        log.debug("Request to get Item : {}", id);
        return itemRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Item : {}", id);
        itemRepository.deleteById(id);
    }

    @Override
    public List<String> findAllGameName() {
        log.debug("Request to get All game names");
        return itemRepository.findDistinctGameName();
    }

    @Override
    public Double getMinimumPriceForGames(List<String> games) {
        log.debug("Request to get minimum price for games : {}", games);
        Page<Item> page = itemRepository.findMinPriceForGames(games, PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "price")));
        if (page.hasContent()) {
            return page.getContent().get(0).getPrice();
        }
        return 0.0;
    }

    @Override
    public Double getMaximumPriceForGames(List<String> games) {
        log.debug("Request to get maximum price for games: {}", games);
        Page<Item> page = itemRepository.findMinPriceForGames(games, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "price")));
        if (page.hasContent()) {
            return page.getContent().get(0).getPrice();
        }
        return 0.0;
    }

    @Override
    public Page<Item> getAllItemWithFilters(List<String> games, Double minPrice, Double maxPrice, String description, Pageable pageable) {
        log.debug(
            "Request to get maximum price for games: {} price between {} and  {} with text {}",
            games,
            minPrice,
            maxPrice,
            description
        );
        return itemRepository.findAllbyGameNameAndPriceRange(games, minPrice, maxPrice, description, pageable);
    }
}
