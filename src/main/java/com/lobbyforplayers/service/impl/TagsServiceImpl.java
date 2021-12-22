package com.lobbyforplayers.service.impl;

import com.lobbyforplayers.domain.Tags;
import com.lobbyforplayers.repository.TagsRepository;
import com.lobbyforplayers.service.TagsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Tags}.
 */
@Service
public class TagsServiceImpl implements TagsService {

    private final Logger log = LoggerFactory.getLogger(TagsServiceImpl.class);

    private final TagsRepository tagsRepository;

    public TagsServiceImpl(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    @Override
    public Tags save(Tags tags) {
        log.debug("Request to save Tags : {}", tags);
        return tagsRepository.save(tags);
    }

    @Override
    public Optional<Tags> partialUpdate(Tags tags) {
        log.debug("Request to partially update Tags : {}", tags);

        return tagsRepository
            .findById(tags.getId())
            .map(existingTags -> {
                if (tags.getTag() != null) {
                    existingTags.setTag(tags.getTag());
                }
                if (tags.getLanguage() != null) {
                    existingTags.setLanguage(tags.getLanguage());
                }

                return existingTags;
            })
            .map(tagsRepository::save);
    }

    @Override
    public List<Tags> findAll() {
        log.debug("Request to get all Tags");
        return tagsRepository.findAll();
    }

    @Override
    public Optional<Tags> findOne(String id) {
        log.debug("Request to get Tags : {}", id);
        return tagsRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Tags : {}", id);
        tagsRepository.deleteById(id);
    }
}
