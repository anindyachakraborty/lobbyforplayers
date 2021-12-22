package com.lobbyforplayers.service.impl;

import com.lobbyforplayers.domain.Reviews;
import com.lobbyforplayers.repository.ReviewsRepository;
import com.lobbyforplayers.service.ReviewsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Reviews}.
 */
@Service
public class ReviewsServiceImpl implements ReviewsService {

    private final Logger log = LoggerFactory.getLogger(ReviewsServiceImpl.class);

    private final ReviewsRepository reviewsRepository;

    public ReviewsServiceImpl(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    @Override
    public Reviews save(Reviews reviews) {
        log.debug("Request to save Reviews : {}", reviews);
        return reviewsRepository.save(reviews);
    }

    @Override
    public Optional<Reviews> partialUpdate(Reviews reviews) {
        log.debug("Request to partially update Reviews : {}", reviews);

        return reviewsRepository
            .findById(reviews.getId())
            .map(existingReviews -> {
                if (reviews.getQuestion() != null) {
                    existingReviews.setQuestion(reviews.getQuestion());
                }
                if (reviews.getRating() != null) {
                    existingReviews.setRating(reviews.getRating());
                }
                if (reviews.getUsername() != null) {
                    existingReviews.setUsername(reviews.getUsername());
                }
                if (reviews.getOrderId() != null) {
                    existingReviews.setOrderId(reviews.getOrderId());
                }

                return existingReviews;
            })
            .map(reviewsRepository::save);
    }

    @Override
    public List<Reviews> findAll() {
        log.debug("Request to get all Reviews");
        return reviewsRepository.findAll();
    }

    @Override
    public Optional<Reviews> findOne(String id) {
        log.debug("Request to get Reviews : {}", id);
        return reviewsRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Reviews : {}", id);
        reviewsRepository.deleteById(id);
    }
}
