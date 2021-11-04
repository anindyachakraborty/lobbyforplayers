package com.lobbyforplayers.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lobbyforplayers.IntegrationTest;
import com.lobbyforplayers.domain.Reviews;
import com.lobbyforplayers.repository.ReviewsRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ReviewsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ReviewsResourceIT {

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final Long DEFAULT_RATING = 1L;
    private static final Long UPDATED_RATING = 2L;

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/reviews";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private MockMvc restReviewsMockMvc;

    private Reviews reviews;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reviews createEntity() {
        Reviews reviews = new Reviews()
            .question(DEFAULT_QUESTION)
            .rating(DEFAULT_RATING)
            .username(DEFAULT_USERNAME)
            .orderId(DEFAULT_ORDER_ID);
        return reviews;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reviews createUpdatedEntity() {
        Reviews reviews = new Reviews()
            .question(UPDATED_QUESTION)
            .rating(UPDATED_RATING)
            .username(UPDATED_USERNAME)
            .orderId(UPDATED_ORDER_ID);
        return reviews;
    }

    @BeforeEach
    public void initTest() {
        reviewsRepository.deleteAll();
        reviews = createEntity();
    }

    @Test
    void createReviews() throws Exception {
        int databaseSizeBeforeCreate = reviewsRepository.findAll().size();
        // Create the Reviews
        restReviewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reviews)))
            .andExpect(status().isCreated());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeCreate + 1);
        Reviews testReviews = reviewsList.get(reviewsList.size() - 1);
        assertThat(testReviews.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testReviews.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testReviews.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testReviews.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
    }

    @Test
    void createReviewsWithExistingId() throws Exception {
        // Create the Reviews with an existing ID
        reviews.setId("existing_id");

        int databaseSizeBeforeCreate = reviewsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReviewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reviews)))
            .andExpect(status().isBadRequest());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewsRepository.findAll().size();
        // set the field null
        reviews.setQuestion(null);

        // Create the Reviews, which fails.

        restReviewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reviews)))
            .andExpect(status().isBadRequest());

        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkRatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewsRepository.findAll().size();
        // set the field null
        reviews.setRating(null);

        // Create the Reviews, which fails.

        restReviewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reviews)))
            .andExpect(status().isBadRequest());

        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewsRepository.findAll().size();
        // set the field null
        reviews.setUsername(null);

        // Create the Reviews, which fails.

        restReviewsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reviews)))
            .andExpect(status().isBadRequest());

        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllReviews() throws Exception {
        // Initialize the database
        reviewsRepository.save(reviews);

        // Get all the reviewsList
        restReviewsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reviews.getId())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID)));
    }

    @Test
    void getReviews() throws Exception {
        // Initialize the database
        reviewsRepository.save(reviews);

        // Get the reviews
        restReviewsMockMvc
            .perform(get(ENTITY_API_URL_ID, reviews.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reviews.getId()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID));
    }

    @Test
    void getNonExistingReviews() throws Exception {
        // Get the reviews
        restReviewsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewReviews() throws Exception {
        // Initialize the database
        reviewsRepository.save(reviews);

        int databaseSizeBeforeUpdate = reviewsRepository.findAll().size();

        // Update the reviews
        Reviews updatedReviews = reviewsRepository.findById(reviews.getId()).get();
        updatedReviews.question(UPDATED_QUESTION).rating(UPDATED_RATING).username(UPDATED_USERNAME).orderId(UPDATED_ORDER_ID);

        restReviewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReviews.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReviews))
            )
            .andExpect(status().isOk());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeUpdate);
        Reviews testReviews = reviewsList.get(reviewsList.size() - 1);
        assertThat(testReviews.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testReviews.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testReviews.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testReviews.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
    }

    @Test
    void putNonExistingReviews() throws Exception {
        int databaseSizeBeforeUpdate = reviewsRepository.findAll().size();
        reviews.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReviewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reviews.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reviews))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchReviews() throws Exception {
        int databaseSizeBeforeUpdate = reviewsRepository.findAll().size();
        reviews.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReviewsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reviews))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamReviews() throws Exception {
        int databaseSizeBeforeUpdate = reviewsRepository.findAll().size();
        reviews.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReviewsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reviews)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateReviewsWithPatch() throws Exception {
        // Initialize the database
        reviewsRepository.save(reviews);

        int databaseSizeBeforeUpdate = reviewsRepository.findAll().size();

        // Update the reviews using partial update
        Reviews partialUpdatedReviews = new Reviews();
        partialUpdatedReviews.setId(reviews.getId());

        partialUpdatedReviews.question(UPDATED_QUESTION).rating(UPDATED_RATING).orderId(UPDATED_ORDER_ID);

        restReviewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReviews.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReviews))
            )
            .andExpect(status().isOk());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeUpdate);
        Reviews testReviews = reviewsList.get(reviewsList.size() - 1);
        assertThat(testReviews.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testReviews.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testReviews.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testReviews.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
    }

    @Test
    void fullUpdateReviewsWithPatch() throws Exception {
        // Initialize the database
        reviewsRepository.save(reviews);

        int databaseSizeBeforeUpdate = reviewsRepository.findAll().size();

        // Update the reviews using partial update
        Reviews partialUpdatedReviews = new Reviews();
        partialUpdatedReviews.setId(reviews.getId());

        partialUpdatedReviews.question(UPDATED_QUESTION).rating(UPDATED_RATING).username(UPDATED_USERNAME).orderId(UPDATED_ORDER_ID);

        restReviewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReviews.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReviews))
            )
            .andExpect(status().isOk());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeUpdate);
        Reviews testReviews = reviewsList.get(reviewsList.size() - 1);
        assertThat(testReviews.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testReviews.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testReviews.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testReviews.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
    }

    @Test
    void patchNonExistingReviews() throws Exception {
        int databaseSizeBeforeUpdate = reviewsRepository.findAll().size();
        reviews.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReviewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reviews.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reviews))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchReviews() throws Exception {
        int databaseSizeBeforeUpdate = reviewsRepository.findAll().size();
        reviews.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReviewsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reviews))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamReviews() throws Exception {
        int databaseSizeBeforeUpdate = reviewsRepository.findAll().size();
        reviews.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReviewsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(reviews)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteReviews() throws Exception {
        // Initialize the database
        reviewsRepository.save(reviews);

        int databaseSizeBeforeDelete = reviewsRepository.findAll().size();

        // Delete the reviews
        restReviewsMockMvc
            .perform(delete(ENTITY_API_URL_ID, reviews.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
