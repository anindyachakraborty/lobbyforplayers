package com.lobbyforplayers.service.impl;

import com.lobbyforplayers.domain.Questionary;
import com.lobbyforplayers.repository.QuestionaryRepository;
import com.lobbyforplayers.service.QuestionaryService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Questionary}.
 */
@Service
public class QuestionaryServiceImpl implements QuestionaryService {

    private final Logger log = LoggerFactory.getLogger(QuestionaryServiceImpl.class);

    private final QuestionaryRepository questionaryRepository;

    public QuestionaryServiceImpl(QuestionaryRepository questionaryRepository) {
        this.questionaryRepository = questionaryRepository;
    }

    @Override
    public Questionary save(Questionary questionary) {
        log.debug("Request to save Questionary : {}", questionary);
        return questionaryRepository.save(questionary);
    }

    @Override
    public Optional<Questionary> partialUpdate(Questionary questionary) {
        log.debug("Request to partially update Questionary : {}", questionary);

        return questionaryRepository
            .findById(questionary.getId())
            .map(existingQuestionary -> {
                if (questionary.getProcess() != null) {
                    existingQuestionary.setProcess(questionary.getProcess());
                }
                if (questionary.getQuestions() != null) {
                    existingQuestionary.setQuestions(questionary.getQuestions());
                }

                return existingQuestionary;
            })
            .map(questionaryRepository::save);
    }

    @Override
    public List<Questionary> findAll() {
        log.debug("Request to get all Questionaries");
        return questionaryRepository.findAll();
    }

    @Override
    public Optional<Questionary> findOne(String id) {
        log.debug("Request to get Questionary : {}", id);
        return questionaryRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Questionary : {}", id);
        questionaryRepository.deleteById(id);
    }
}
