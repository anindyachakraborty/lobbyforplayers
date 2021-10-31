package com.lobbyforplayers.service.impl;

import com.lobbyforplayers.domain.Chats;
import com.lobbyforplayers.repository.ChatsRepository;
import com.lobbyforplayers.service.ChatsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Chats}.
 */
@Service
public class ChatsServiceImpl implements ChatsService {

    private final Logger log = LoggerFactory.getLogger(ChatsServiceImpl.class);

    private final ChatsRepository chatsRepository;

    public ChatsServiceImpl(ChatsRepository chatsRepository) {
        this.chatsRepository = chatsRepository;
    }

    @Override
    public Chats save(Chats chats) {
        log.debug("Request to save Chats : {}", chats);
        return chatsRepository.save(chats);
    }

    @Override
    public Optional<Chats> partialUpdate(Chats chats) {
        log.debug("Request to partially update Chats : {}", chats);

        return chatsRepository
            .findById(chats.getId())
            .map(existingChats -> {
                if (chats.getFromUserId() != null) {
                    existingChats.setFromUserId(chats.getFromUserId());
                }
                if (chats.getToUserId() != null) {
                    existingChats.setToUserId(chats.getToUserId());
                }
                if (chats.getTimeStamp() != null) {
                    existingChats.setTimeStamp(chats.getTimeStamp());
                }
                if (chats.getMessage() != null) {
                    existingChats.setMessage(chats.getMessage());
                }
                if (chats.getLanguage() != null) {
                    existingChats.setLanguage(chats.getLanguage());
                }

                return existingChats;
            })
            .map(chatsRepository::save);
    }

    @Override
    public Page<Chats> findAll(Pageable pageable) {
        log.debug("Request to get all Chats");
        return chatsRepository.findAll(pageable);
    }

    @Override
    public Optional<Chats> findOne(String id) {
        log.debug("Request to get Chats : {}", id);
        return chatsRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Chats : {}", id);
        chatsRepository.deleteById(id);
    }
}
