package com.lobbyforplayers.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lobbyforplayers.IntegrationTest;
import com.lobbyforplayers.domain.Tags;
import com.lobbyforplayers.repository.TagsRepository;
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
 * Integration tests for the {@link TagsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TagsResourceIT {

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private MockMvc restTagsMockMvc;

    private Tags tags;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tags createEntity() {
        Tags tags = new Tags().tag(DEFAULT_TAG).language(DEFAULT_LANGUAGE);
        return tags;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tags createUpdatedEntity() {
        Tags tags = new Tags().tag(UPDATED_TAG).language(UPDATED_LANGUAGE);
        return tags;
    }

    @BeforeEach
    public void initTest() {
        tagsRepository.deleteAll();
        tags = createEntity();
    }

    @Test
    void createTags() throws Exception {
        int databaseSizeBeforeCreate = tagsRepository.findAll().size();
        // Create the Tags
        restTagsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tags)))
            .andExpect(status().isCreated());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeCreate + 1);
        Tags testTags = tagsList.get(tagsList.size() - 1);
        assertThat(testTags.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testTags.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    void createTagsWithExistingId() throws Exception {
        // Create the Tags with an existing ID
        tags.setId("existing_id");

        int databaseSizeBeforeCreate = tagsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTagsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tags)))
            .andExpect(status().isBadRequest());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkTagIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagsRepository.findAll().size();
        // set the field null
        tags.setTag(null);

        // Create the Tags, which fails.

        restTagsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tags)))
            .andExpect(status().isBadRequest());

        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagsRepository.findAll().size();
        // set the field null
        tags.setLanguage(null);

        // Create the Tags, which fails.

        restTagsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tags)))
            .andExpect(status().isBadRequest());

        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllTags() throws Exception {
        // Initialize the database
        tagsRepository.save(tags);

        // Get all the tagsList
        restTagsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tags.getId())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));
    }

    @Test
    void getTags() throws Exception {
        // Initialize the database
        tagsRepository.save(tags);

        // Get the tags
        restTagsMockMvc
            .perform(get(ENTITY_API_URL_ID, tags.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tags.getId()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE));
    }

    @Test
    void getNonExistingTags() throws Exception {
        // Get the tags
        restTagsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewTags() throws Exception {
        // Initialize the database
        tagsRepository.save(tags);

        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();

        // Update the tags
        Tags updatedTags = tagsRepository.findById(tags.getId()).get();
        updatedTags.tag(UPDATED_TAG).language(UPDATED_LANGUAGE);

        restTagsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTags.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTags))
            )
            .andExpect(status().isOk());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
        Tags testTags = tagsList.get(tagsList.size() - 1);
        assertThat(testTags.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testTags.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    void putNonExistingTags() throws Exception {
        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();
        tags.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tags.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tags))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTags() throws Exception {
        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();
        tags.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTagsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tags))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTags() throws Exception {
        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();
        tags.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTagsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tags)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTagsWithPatch() throws Exception {
        // Initialize the database
        tagsRepository.save(tags);

        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();

        // Update the tags using partial update
        Tags partialUpdatedTags = new Tags();
        partialUpdatedTags.setId(tags.getId());

        partialUpdatedTags.language(UPDATED_LANGUAGE);

        restTagsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTags.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTags))
            )
            .andExpect(status().isOk());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
        Tags testTags = tagsList.get(tagsList.size() - 1);
        assertThat(testTags.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testTags.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    void fullUpdateTagsWithPatch() throws Exception {
        // Initialize the database
        tagsRepository.save(tags);

        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();

        // Update the tags using partial update
        Tags partialUpdatedTags = new Tags();
        partialUpdatedTags.setId(tags.getId());

        partialUpdatedTags.tag(UPDATED_TAG).language(UPDATED_LANGUAGE);

        restTagsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTags.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTags))
            )
            .andExpect(status().isOk());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
        Tags testTags = tagsList.get(tagsList.size() - 1);
        assertThat(testTags.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testTags.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    void patchNonExistingTags() throws Exception {
        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();
        tags.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tags.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tags))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTags() throws Exception {
        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();
        tags.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTagsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tags))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTags() throws Exception {
        int databaseSizeBeforeUpdate = tagsRepository.findAll().size();
        tags.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTagsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tags)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tags in the database
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTags() throws Exception {
        // Initialize the database
        tagsRepository.save(tags);

        int databaseSizeBeforeDelete = tagsRepository.findAll().size();

        // Delete the tags
        restTagsMockMvc
            .perform(delete(ENTITY_API_URL_ID, tags.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tags> tagsList = tagsRepository.findAll();
        assertThat(tagsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
