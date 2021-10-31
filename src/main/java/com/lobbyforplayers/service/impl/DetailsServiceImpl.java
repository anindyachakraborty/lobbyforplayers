package com.lobbyforplayers.service.impl;

import com.lobbyforplayers.domain.Details;
import com.lobbyforplayers.repository.DetailsRepository;
import com.lobbyforplayers.service.DetailsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Details}.
 */
@Service
public class DetailsServiceImpl implements DetailsService {

    private final Logger log = LoggerFactory.getLogger(DetailsServiceImpl.class);

    private final DetailsRepository detailsRepository;

    public DetailsServiceImpl(DetailsRepository detailsRepository) {
        this.detailsRepository = detailsRepository;
    }

    @Override
    public Details save(Details details) {
        log.debug("Request to save Details : {}", details);
        return detailsRepository.save(details);
    }

    @Override
    public Optional<Details> partialUpdate(Details details) {
        log.debug("Request to partially update Details : {}", details);

        return detailsRepository
            .findById(details.getId())
            .map(existingDetails -> {
                if (details.getLoginName() != null) {
                    existingDetails.setLoginName(details.getLoginName());
                }
                if (details.getPassword() != null) {
                    existingDetails.setPassword(details.getPassword());
                }
                if (details.getLastName() != null) {
                    existingDetails.setLastName(details.getLastName());
                }
                if (details.getFirstName() != null) {
                    existingDetails.setFirstName(details.getFirstName());
                }
                if (details.getSecurtiyQuestion() != null) {
                    existingDetails.setSecurtiyQuestion(details.getSecurtiyQuestion());
                }
                if (details.getSecurityAnswer() != null) {
                    existingDetails.setSecurityAnswer(details.getSecurityAnswer());
                }
                if (details.getParentalPassword() != null) {
                    existingDetails.setParentalPassword(details.getParentalPassword());
                }
                if (details.getFirstCdKey() != null) {
                    existingDetails.setFirstCdKey(details.getFirstCdKey());
                }
                if (details.getOtherInformation() != null) {
                    existingDetails.setOtherInformation(details.getOtherInformation());
                }
                if (details.getEnteredDate() != null) {
                    existingDetails.setEnteredDate(details.getEnteredDate());
                }
                if (details.getOrderDate() != null) {
                    existingDetails.setOrderDate(details.getOrderDate());
                }

                return existingDetails;
            })
            .map(detailsRepository::save);
    }

    @Override
    public List<Details> findAll() {
        log.debug("Request to get all Details");
        return detailsRepository.findAll();
    }

    @Override
    public Optional<Details> findOne(String id) {
        log.debug("Request to get Details : {}", id);
        return detailsRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Details : {}", id);
        detailsRepository.deleteById(id);
    }
}
