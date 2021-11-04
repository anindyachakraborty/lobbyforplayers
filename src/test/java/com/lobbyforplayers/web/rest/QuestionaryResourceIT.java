package com.lobbyforplayers.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lobbyforplayers.IntegrationTest;
import com.lobbyforplayers.domain.Questionary;
import com.lobbyforplayers.repository.QuestionaryRepository;
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
 * Integration tests for the {@link QuestionaryResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class QuestionaryResourceIT {

    private static final String DEFAULT_PROCESS = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTIONS = "AAAAAAAAAA";
    private static final String UPDATED_QUESTIONS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/questionaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private QuestionaryRepository questionaryRepository;

    @Autowired
    private MockMvc restQuestionaryMockMvc;

    private Questionary questionary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Questionary createEntity() {
        Questionary questionary = new Questionary().process(DEFAULT_PROCESS).questions(DEFAULT_QUESTIONS);
        return questionary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Questionary createUpdatedEntity() {
        Questionary questionary = new Questionary().process(UPDATED_PROCESS).questions(UPDATED_QUESTIONS);
        return questionary;
    }

    @BeforeEach
    public void initTest() {
        questionaryRepository.deleteAll();
        questionary = createEntity();
    }

    @Test
    void createQuestionary() throws Exception {
        int databaseSizeBeforeCreate = questionaryRepository.findAll().size();
        // Create the Questionary
        restQuestionaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionary)))
            .andExpect(status().isCreated());

        // Validate the Questionary in the database
        List<Questionary> questionaryList = questionaryRepository.findAll();
        assertThat(questionaryList).hasSize(databaseSizeBeforeCreate + 1);
        Questionary testQuestionary = questionaryList.get(questionaryList.size() - 1);
        assertThat(testQuestionary.getProcess()).isEqualTo(DEFAULT_PROCESS);
        assertThat(testQuestionary.getQuestions()).isEqualTo(DEFAULT_QUESTIONS);
    }

    @Test
    void createQuestionaryWithExistingId() throws Exception {
        // Create the Questionary with an existing ID
        questionary.setId("existing_id");

        int databaseSizeBeforeCreate = questionaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionary)))
            .andExpect(status().isBadRequest());

        // Validate the Questionary in the database
        List<Questionary> questionaryList = questionaryRepository.findAll();
        assertThat(questionaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkProcessIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionaryRepository.findAll().size();
        // set the field null
        questionary.setProcess(null);

        // Create the Questionary, which fails.

        restQuestionaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionary)))
            .andExpect(status().isBadRequest());

        List<Questionary> questionaryList = questionaryRepository.findAll();
        assertThat(questionaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkQuestionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionaryRepository.findAll().size();
        // set the field null
        questionary.setQuestions(null);

        // Create the Questionary, which fails.

        restQuestionaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionary)))
            .andExpect(status().isBadRequest());

        List<Questionary> questionaryList = questionaryRepository.findAll();
        assertThat(questionaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllQuestionaries() throws Exception {
        // Initialize the database
        questionaryRepository.save(questionary);

        // Get all the questionaryList
        restQuestionaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionary.getId())))
            .andExpect(jsonPath("$.[*].process").value(hasItem(DEFAULT_PROCESS)))
            .andExpect(jsonPath("$.[*].questions").value(hasItem(DEFAULT_QUESTIONS)));
    }

    @Test
    void getQuestionary() throws Exception {
        // Initialize the database
        questionaryRepository.save(questionary);

        // Get the questionary
        restQuestionaryMockMvc
            .perform(get(ENTITY_API_URL_ID, questionary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionary.getId()))
            .andExpect(jsonPath("$.process").value(DEFAULT_PROCESS))
            .andExpect(jsonPath("$.questions").value(DEFAULT_QUESTIONS));
    }

    @Test
    void getNonExistingQuestionary() throws Exception {
        // Get the questionary
        restQuestionaryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewQuestionary() throws Exception {
        // Initialize the database
        questionaryRepository.save(questionary);

        int databaseSizeBeforeUpdate = questionaryRepository.findAll().size();

        // Update the questionary
        Questionary updatedQuestionary = questionaryRepository.findById(questionary.getId()).get();
        updatedQuestionary.process(UPDATED_PROCESS).questions(UPDATED_QUESTIONS);

        restQuestionaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQuestionary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQuestionary))
            )
            .andExpect(status().isOk());

        // Validate the Questionary in the database
        List<Questionary> questionaryList = questionaryRepository.findAll();
        assertThat(questionaryList).hasSize(databaseSizeBeforeUpdate);
        Questionary testQuestionary = questionaryList.get(questionaryList.size() - 1);
        assertThat(testQuestionary.getProcess()).isEqualTo(UPDATED_PROCESS);
        assertThat(testQuestionary.getQuestions()).isEqualTo(UPDATED_QUESTIONS);
    }

    @Test
    void putNonExistingQuestionary() throws Exception {
        int databaseSizeBeforeUpdate = questionaryRepository.findAll().size();
        questionary.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, questionary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionary))
            )
            .andExpect(status().isBadRequest());

        // Validate the Questionary in the database
        List<Questionary> questionaryList = questionaryRepository.findAll();
        assertThat(questionaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchQuestionary() throws Exception {
        int databaseSizeBeforeUpdate = questionaryRepository.findAll().size();
        questionary.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionary))
            )
            .andExpect(status().isBadRequest());

        // Validate the Questionary in the database
        List<Questionary> questionaryList = questionaryRepository.findAll();
        assertThat(questionaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamQuestionary() throws Exception {
        int databaseSizeBeforeUpdate = questionaryRepository.findAll().size();
        questionary.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionaryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionary)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Questionary in the database
        List<Questionary> questionaryList = questionaryRepository.findAll();
        assertThat(questionaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateQuestionaryWithPatch() throws Exception {
        // Initialize the database
        questionaryRepository.save(questionary);

        int databaseSizeBeforeUpdate = questionaryRepository.findAll().size();

        // Update the questionary using partial update
        Questionary partialUpdatedQuestionary = new Questionary();
        partialUpdatedQuestionary.setId(questionary.getId());

        partialUpdatedQuestionary.process(UPDATED_PROCESS);

        restQuestionaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestionary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestionary))
            )
            .andExpect(status().isOk());

        // Validate the Questionary in the database
        List<Questionary> questionaryList = questionaryRepository.findAll();
        assertThat(questionaryList).hasSize(databaseSizeBeforeUpdate);
        Questionary testQuestionary = questionaryList.get(questionaryList.size() - 1);
        assertThat(testQuestionary.getProcess()).isEqualTo(UPDATED_PROCESS);
        assertThat(testQuestionary.getQuestions()).isEqualTo(DEFAULT_QUESTIONS);
    }

    @Test
    void fullUpdateQuestionaryWithPatch() throws Exception {
        // Initialize the database
        questionaryRepository.save(questionary);

        int databaseSizeBeforeUpdate = questionaryRepository.findAll().size();

        // Update the questionary using partial update
        Questionary partialUpdatedQuestionary = new Questionary();
        partialUpdatedQuestionary.setId(questionary.getId());

        partialUpdatedQuestionary.process(UPDATED_PROCESS).questions(UPDATED_QUESTIONS);

        restQuestionaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestionary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestionary))
            )
            .andExpect(status().isOk());

        // Validate the Questionary in the database
        List<Questionary> questionaryList = questionaryRepository.findAll();
        assertThat(questionaryList).hasSize(databaseSizeBeforeUpdate);
        Questionary testQuestionary = questionaryList.get(questionaryList.size() - 1);
        assertThat(testQuestionary.getProcess()).isEqualTo(UPDATED_PROCESS);
        assertThat(testQuestionary.getQuestions()).isEqualTo(UPDATED_QUESTIONS);
    }

    @Test
    void patchNonExistingQuestionary() throws Exception {
        int databaseSizeBeforeUpdate = questionaryRepository.findAll().size();
        questionary.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, questionary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionary))
            )
            .andExpect(status().isBadRequest());

        // Validate the Questionary in the database
        List<Questionary> questionaryList = questionaryRepository.findAll();
        assertThat(questionaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchQuestionary() throws Exception {
        int databaseSizeBeforeUpdate = questionaryRepository.findAll().size();
        questionary.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionary))
            )
            .andExpect(status().isBadRequest());

        // Validate the Questionary in the database
        List<Questionary> questionaryList = questionaryRepository.findAll();
        assertThat(questionaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamQuestionary() throws Exception {
        int databaseSizeBeforeUpdate = questionaryRepository.findAll().size();
        questionary.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionaryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(questionary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Questionary in the database
        List<Questionary> questionaryList = questionaryRepository.findAll();
        assertThat(questionaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteQuestionary() throws Exception {
        // Initialize the database
        questionaryRepository.save(questionary);

        int databaseSizeBeforeDelete = questionaryRepository.findAll().size();

        // Delete the questionary
        restQuestionaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, questionary.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Questionary> questionaryList = questionaryRepository.findAll();
        assertThat(questionaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
