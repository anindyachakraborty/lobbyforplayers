package com.lobbyforplayers.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lobbyforplayers.IntegrationTest;
import com.lobbyforplayers.domain.Chats;
import com.lobbyforplayers.repository.ChatsRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ChatsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ChatsResourceIT {

    private static final String DEFAULT_FROM_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_FROM_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TO_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_TO_USER_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIME_STAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_STAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/chats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ChatsRepository chatsRepository;

    @Autowired
    private MockMvc restChatsMockMvc;

    private Chats chats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chats createEntity() {
        Chats chats = new Chats()
            .fromUserId(DEFAULT_FROM_USER_ID)
            .toUserId(DEFAULT_TO_USER_ID)
            .timeStamp(DEFAULT_TIME_STAMP)
            .message(DEFAULT_MESSAGE)
            .language(DEFAULT_LANGUAGE);
        return chats;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chats createUpdatedEntity() {
        Chats chats = new Chats()
            .fromUserId(UPDATED_FROM_USER_ID)
            .toUserId(UPDATED_TO_USER_ID)
            .timeStamp(UPDATED_TIME_STAMP)
            .message(UPDATED_MESSAGE)
            .language(UPDATED_LANGUAGE);
        return chats;
    }

    @BeforeEach
    public void initTest() {
        chatsRepository.deleteAll();
        chats = createEntity();
    }

    @Test
    void createChats() throws Exception {
        int databaseSizeBeforeCreate = chatsRepository.findAll().size();
        // Create the Chats
        restChatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chats)))
            .andExpect(status().isCreated());

        // Validate the Chats in the database
        List<Chats> chatsList = chatsRepository.findAll();
        assertThat(chatsList).hasSize(databaseSizeBeforeCreate + 1);
        Chats testChats = chatsList.get(chatsList.size() - 1);
        assertThat(testChats.getFromUserId()).isEqualTo(DEFAULT_FROM_USER_ID);
        assertThat(testChats.getToUserId()).isEqualTo(DEFAULT_TO_USER_ID);
        assertThat(testChats.getTimeStamp()).isEqualTo(DEFAULT_TIME_STAMP);
        assertThat(testChats.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testChats.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    void createChatsWithExistingId() throws Exception {
        // Create the Chats with an existing ID
        chats.setId("existing_id");

        int databaseSizeBeforeCreate = chatsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chats)))
            .andExpect(status().isBadRequest());

        // Validate the Chats in the database
        List<Chats> chatsList = chatsRepository.findAll();
        assertThat(chatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkFromUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = chatsRepository.findAll().size();
        // set the field null
        chats.setFromUserId(null);

        // Create the Chats, which fails.

        restChatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chats)))
            .andExpect(status().isBadRequest());

        List<Chats> chatsList = chatsRepository.findAll();
        assertThat(chatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkToUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = chatsRepository.findAll().size();
        // set the field null
        chats.setToUserId(null);

        // Create the Chats, which fails.

        restChatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chats)))
            .andExpect(status().isBadRequest());

        List<Chats> chatsList = chatsRepository.findAll();
        assertThat(chatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkTimeStampIsRequired() throws Exception {
        int databaseSizeBeforeTest = chatsRepository.findAll().size();
        // set the field null
        chats.setTimeStamp(null);

        // Create the Chats, which fails.

        restChatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chats)))
            .andExpect(status().isBadRequest());

        List<Chats> chatsList = chatsRepository.findAll();
        assertThat(chatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = chatsRepository.findAll().size();
        // set the field null
        chats.setMessage(null);

        // Create the Chats, which fails.

        restChatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chats)))
            .andExpect(status().isBadRequest());

        List<Chats> chatsList = chatsRepository.findAll();
        assertThat(chatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = chatsRepository.findAll().size();
        // set the field null
        chats.setLanguage(null);

        // Create the Chats, which fails.

        restChatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chats)))
            .andExpect(status().isBadRequest());

        List<Chats> chatsList = chatsRepository.findAll();
        assertThat(chatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllChats() throws Exception {
        // Initialize the database
        chatsRepository.save(chats);

        // Get all the chatsList
        restChatsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chats.getId())))
            .andExpect(jsonPath("$.[*].fromUserId").value(hasItem(DEFAULT_FROM_USER_ID)))
            .andExpect(jsonPath("$.[*].toUserId").value(hasItem(DEFAULT_TO_USER_ID)))
            .andExpect(jsonPath("$.[*].timeStamp").value(hasItem(DEFAULT_TIME_STAMP.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));
    }

    @Test
    void getChats() throws Exception {
        // Initialize the database
        chatsRepository.save(chats);

        // Get the chats
        restChatsMockMvc
            .perform(get(ENTITY_API_URL_ID, chats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chats.getId()))
            .andExpect(jsonPath("$.fromUserId").value(DEFAULT_FROM_USER_ID))
            .andExpect(jsonPath("$.toUserId").value(DEFAULT_TO_USER_ID))
            .andExpect(jsonPath("$.timeStamp").value(DEFAULT_TIME_STAMP.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE));
    }

    @Test
    void getNonExistingChats() throws Exception {
        // Get the chats
        restChatsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewChats() throws Exception {
        // Initialize the database
        chatsRepository.save(chats);

        int databaseSizeBeforeUpdate = chatsRepository.findAll().size();

        // Update the chats
        Chats updatedChats = chatsRepository.findById(chats.getId()).get();
        updatedChats
            .fromUserId(UPDATED_FROM_USER_ID)
            .toUserId(UPDATED_TO_USER_ID)
            .timeStamp(UPDATED_TIME_STAMP)
            .message(UPDATED_MESSAGE)
            .language(UPDATED_LANGUAGE);

        restChatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChats.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedChats))
            )
            .andExpect(status().isOk());

        // Validate the Chats in the database
        List<Chats> chatsList = chatsRepository.findAll();
        assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
        Chats testChats = chatsList.get(chatsList.size() - 1);
        assertThat(testChats.getFromUserId()).isEqualTo(UPDATED_FROM_USER_ID);
        assertThat(testChats.getToUserId()).isEqualTo(UPDATED_TO_USER_ID);
        assertThat(testChats.getTimeStamp()).isEqualTo(UPDATED_TIME_STAMP);
        assertThat(testChats.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testChats.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    void putNonExistingChats() throws Exception {
        int databaseSizeBeforeUpdate = chatsRepository.findAll().size();
        chats.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chats.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chats in the database
        List<Chats> chatsList = chatsRepository.findAll();
        assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchChats() throws Exception {
        int databaseSizeBeforeUpdate = chatsRepository.findAll().size();
        chats.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chats in the database
        List<Chats> chatsList = chatsRepository.findAll();
        assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamChats() throws Exception {
        int databaseSizeBeforeUpdate = chatsRepository.findAll().size();
        chats.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChatsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chats)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Chats in the database
        List<Chats> chatsList = chatsRepository.findAll();
        assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateChatsWithPatch() throws Exception {
        // Initialize the database
        chatsRepository.save(chats);

        int databaseSizeBeforeUpdate = chatsRepository.findAll().size();

        // Update the chats using partial update
        Chats partialUpdatedChats = new Chats();
        partialUpdatedChats.setId(chats.getId());

        partialUpdatedChats.fromUserId(UPDATED_FROM_USER_ID).timeStamp(UPDATED_TIME_STAMP);

        restChatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChats))
            )
            .andExpect(status().isOk());

        // Validate the Chats in the database
        List<Chats> chatsList = chatsRepository.findAll();
        assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
        Chats testChats = chatsList.get(chatsList.size() - 1);
        assertThat(testChats.getFromUserId()).isEqualTo(UPDATED_FROM_USER_ID);
        assertThat(testChats.getToUserId()).isEqualTo(DEFAULT_TO_USER_ID);
        assertThat(testChats.getTimeStamp()).isEqualTo(UPDATED_TIME_STAMP);
        assertThat(testChats.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testChats.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    void fullUpdateChatsWithPatch() throws Exception {
        // Initialize the database
        chatsRepository.save(chats);

        int databaseSizeBeforeUpdate = chatsRepository.findAll().size();

        // Update the chats using partial update
        Chats partialUpdatedChats = new Chats();
        partialUpdatedChats.setId(chats.getId());

        partialUpdatedChats
            .fromUserId(UPDATED_FROM_USER_ID)
            .toUserId(UPDATED_TO_USER_ID)
            .timeStamp(UPDATED_TIME_STAMP)
            .message(UPDATED_MESSAGE)
            .language(UPDATED_LANGUAGE);

        restChatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChats))
            )
            .andExpect(status().isOk());

        // Validate the Chats in the database
        List<Chats> chatsList = chatsRepository.findAll();
        assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
        Chats testChats = chatsList.get(chatsList.size() - 1);
        assertThat(testChats.getFromUserId()).isEqualTo(UPDATED_FROM_USER_ID);
        assertThat(testChats.getToUserId()).isEqualTo(UPDATED_TO_USER_ID);
        assertThat(testChats.getTimeStamp()).isEqualTo(UPDATED_TIME_STAMP);
        assertThat(testChats.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testChats.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    void patchNonExistingChats() throws Exception {
        int databaseSizeBeforeUpdate = chatsRepository.findAll().size();
        chats.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chats in the database
        List<Chats> chatsList = chatsRepository.findAll();
        assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchChats() throws Exception {
        int databaseSizeBeforeUpdate = chatsRepository.findAll().size();
        chats.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chats))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chats in the database
        List<Chats> chatsList = chatsRepository.findAll();
        assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamChats() throws Exception {
        int databaseSizeBeforeUpdate = chatsRepository.findAll().size();
        chats.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChatsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(chats)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Chats in the database
        List<Chats> chatsList = chatsRepository.findAll();
        assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteChats() throws Exception {
        // Initialize the database
        chatsRepository.save(chats);

        int databaseSizeBeforeDelete = chatsRepository.findAll().size();

        // Delete the chats
        restChatsMockMvc
            .perform(delete(ENTITY_API_URL_ID, chats.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chats> chatsList = chatsRepository.findAll();
        assertThat(chatsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
