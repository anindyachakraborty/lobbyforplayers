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
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
// import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ChatsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChatsResourceIT {
    // private static final String DEFAULT_FROM_USER_ID = "AAAAAAAAAA";
    // private static final String UPDATED_FROM_USER_ID = "BBBBBBBBBB";

    // private static final String DEFAULT_TO_USER_ID = "AAAAAAAAAA";
    // private static final String UPDATED_TO_USER_ID = "BBBBBBBBBB";

    // private static final Instant DEFAULT_TIME_STAMP = Instant.ofEpochMilli(0L);
    // private static final Instant UPDATED_TIME_STAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    // private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    // private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    // private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    // private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    // private static final String ENTITY_API_URL = "/api/chats";
    // private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    // private static Random random = new Random();
    // private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    // @Autowired
    // private ChatsRepository chatsRepository;

    // @Autowired
    // private EntityManager em;

    // @Autowired
    // private MockMvc restChatsMockMvc;

    // private Chats chats;

    // /**
    //  * Create an entity for this test.
    //  *
    //  * This is a static method, as tests for other entities might also need it,
    //  * if they test an entity which requires the current entity.
    //  */
    // public static Chats createEntity(EntityManager em) {
    //     Chats chats = new Chats()
    //         .fromUserId(DEFAULT_FROM_USER_ID)
    //         .toUserId(DEFAULT_TO_USER_ID)
    //         .timeStamp(DEFAULT_TIME_STAMP)
    //         .message(DEFAULT_MESSAGE)
    //         .language(DEFAULT_LANGUAGE);
    //     return chats;
    // }

    // /**
    //  * Create an updated entity for this test.
    //  *
    //  * This is a static method, as tests for other entities might also need it,
    //  * if they test an entity which requires the current entity.
    //  */
    // public static Chats createUpdatedEntity(EntityManager em) {
    //     Chats chats = new Chats()
    //         .fromUserId(UPDATED_FROM_USER_ID)
    //         .toUserId(UPDATED_TO_USER_ID)
    //         .timeStamp(UPDATED_TIME_STAMP)
    //         .message(UPDATED_MESSAGE)
    //         .language(UPDATED_LANGUAGE);
    //     return chats;
    // }

    // @BeforeEach
    // public void initTest() {
    //     chats = createEntity(em);
    // }

    // @Test
    // @Transactional
    // void createChats() throws Exception {
    //     int databaseSizeBeforeCreate = chatsRepository.findAll().size();
    //     // Create the Chats
    //     restChatsMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chats)))
    //         .andExpect(status().isCreated());

    //     // Validate the Chats in the database
    //     List<Chats> chatsList = chatsRepository.findAll();
    //     assertThat(chatsList).hasSize(databaseSizeBeforeCreate + 1);
    //     Chats testChats = chatsList.get(chatsList.size() - 1);
    //     assertThat(testChats.getFromUserId()).isEqualTo(DEFAULT_FROM_USER_ID);
    //     assertThat(testChats.getToUserId()).isEqualTo(DEFAULT_TO_USER_ID);
    //     assertThat(testChats.getTimeStamp()).isEqualTo(DEFAULT_TIME_STAMP);
    //     assertThat(testChats.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    //     assertThat(testChats.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    // }

    // @Test
    // @Transactional
    // void createChatsWithExistingId() throws Exception {
    //     // Create the Chats with an existing ID
    //     chats.setId(1L);

    //     int databaseSizeBeforeCreate = chatsRepository.findAll().size();

    //     // An entity with an existing ID cannot be created, so this API call must fail
    //     restChatsMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chats)))
    //         .andExpect(status().isBadRequest());

    //     // Validate the Chats in the database
    //     List<Chats> chatsList = chatsRepository.findAll();
    //     assertThat(chatsList).hasSize(databaseSizeBeforeCreate);
    // }

    // @Test
    // @Transactional
    // void checkFromUserIdIsRequired() throws Exception {
    //     int databaseSizeBeforeTest = chatsRepository.findAll().size();
    //     // set the field null
    //     chats.setFromUserId(null);

    //     // Create the Chats, which fails.

    //     restChatsMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chats)))
    //         .andExpect(status().isBadRequest());

    //     List<Chats> chatsList = chatsRepository.findAll();
    //     assertThat(chatsList).hasSize(databaseSizeBeforeTest);
    // }

    // @Test
    // @Transactional
    // void checkToUserIdIsRequired() throws Exception {
    //     int databaseSizeBeforeTest = chatsRepository.findAll().size();
    //     // set the field null
    //     chats.setToUserId(null);

    //     // Create the Chats, which fails.

    //     restChatsMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chats)))
    //         .andExpect(status().isBadRequest());

    //     List<Chats> chatsList = chatsRepository.findAll();
    //     assertThat(chatsList).hasSize(databaseSizeBeforeTest);
    // }

    // @Test
    // @Transactional
    // void checkTimeStampIsRequired() throws Exception {
    //     int databaseSizeBeforeTest = chatsRepository.findAll().size();
    //     // set the field null
    //     chats.setTimeStamp(null);

    //     // Create the Chats, which fails.

    //     restChatsMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chats)))
    //         .andExpect(status().isBadRequest());

    //     List<Chats> chatsList = chatsRepository.findAll();
    //     assertThat(chatsList).hasSize(databaseSizeBeforeTest);
    // }

    // @Test
    // @Transactional
    // void checkMessageIsRequired() throws Exception {
    //     int databaseSizeBeforeTest = chatsRepository.findAll().size();
    //     // set the field null
    //     chats.setMessage(null);

    //     // Create the Chats, which fails.

    //     restChatsMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chats)))
    //         .andExpect(status().isBadRequest());

    //     List<Chats> chatsList = chatsRepository.findAll();
    //     assertThat(chatsList).hasSize(databaseSizeBeforeTest);
    // }

    // @Test
    // @Transactional
    // void checkLanguageIsRequired() throws Exception {
    //     int databaseSizeBeforeTest = chatsRepository.findAll().size();
    //     // set the field null
    //     chats.setLanguage(null);

    //     // Create the Chats, which fails.

    //     restChatsMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chats)))
    //         .andExpect(status().isBadRequest());

    //     List<Chats> chatsList = chatsRepository.findAll();
    //     assertThat(chatsList).hasSize(databaseSizeBeforeTest);
    // }

    // @Test
    // @Transactional
    // void getAllChats() throws Exception {
    //     // Initialize the database
    //     chatsRepository.saveAndFlush(chats);

    //     // Get all the chatsList
    //     restChatsMockMvc
    //         .perform(get(ENTITY_API_URL + "?sort=id,desc"))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andExpect(jsonPath("$.[*].id").value(hasItem(chats.getId().intValue())))
    //         .andExpect(jsonPath("$.[*].fromUserId").value(hasItem(DEFAULT_FROM_USER_ID)))
    //         .andExpect(jsonPath("$.[*].toUserId").value(hasItem(DEFAULT_TO_USER_ID)))
    //         .andExpect(jsonPath("$.[*].timeStamp").value(hasItem(DEFAULT_TIME_STAMP.toString())))
    //         .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
    //         .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));
    // }

    // @Test
    // @Transactional
    // void getChats() throws Exception {
    //     // Initialize the database
    //     chatsRepository.saveAndFlush(chats);

    //     // Get the chats
    //     restChatsMockMvc
    //         .perform(get(ENTITY_API_URL_ID, chats.getId()))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andExpect(jsonPath("$.id").value(chats.getId().intValue()))
    //         .andExpect(jsonPath("$.fromUserId").value(DEFAULT_FROM_USER_ID))
    //         .andExpect(jsonPath("$.toUserId").value(DEFAULT_TO_USER_ID))
    //         .andExpect(jsonPath("$.timeStamp").value(DEFAULT_TIME_STAMP.toString()))
    //         .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
    //         .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE));
    // }

    // @Test
    // @Transactional
    // void getNonExistingChats() throws Exception {
    //     // Get the chats
    //     restChatsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    // }

    // @Test
    // @Transactional
    // void putNewChats() throws Exception {
    //     // Initialize the database
    //     chatsRepository.saveAndFlush(chats);

    //     int databaseSizeBeforeUpdate = chatsRepository.findAll().size();

    //     // Update the chats
    //     Chats updatedChats = chatsRepository.findById(chats.getId()).get();
    //     // Disconnect from session so that the updates on updatedChats are not directly saved in db
    //     em.detach(updatedChats);
    //     updatedChats
    //         .fromUserId(UPDATED_FROM_USER_ID)
    //         .toUserId(UPDATED_TO_USER_ID)
    //         .timeStamp(UPDATED_TIME_STAMP)
    //         .message(UPDATED_MESSAGE)
    //         .language(UPDATED_LANGUAGE);

    //     restChatsMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, updatedChats.getId())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(TestUtil.convertObjectToJsonBytes(updatedChats))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the Chats in the database
    //     List<Chats> chatsList = chatsRepository.findAll();
    //     assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
    //     Chats testChats = chatsList.get(chatsList.size() - 1);
    //     assertThat(testChats.getFromUserId()).isEqualTo(UPDATED_FROM_USER_ID);
    //     assertThat(testChats.getToUserId()).isEqualTo(UPDATED_TO_USER_ID);
    //     assertThat(testChats.getTimeStamp()).isEqualTo(UPDATED_TIME_STAMP);
    //     assertThat(testChats.getMessage()).isEqualTo(UPDATED_MESSAGE);
    //     assertThat(testChats.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    // }

    // @Test
    // @Transactional
    // void putNonExistingChats() throws Exception {
    //     int databaseSizeBeforeUpdate = chatsRepository.findAll().size();
    //     chats.setId(count.incrementAndGet());

    //     // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //     restChatsMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, chats.getId())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(TestUtil.convertObjectToJsonBytes(chats))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Chats in the database
    //     List<Chats> chatsList = chatsRepository.findAll();
    //     assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void putWithIdMismatchChats() throws Exception {
    //     int databaseSizeBeforeUpdate = chatsRepository.findAll().size();
    //     chats.setId(count.incrementAndGet());

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restChatsMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, count.incrementAndGet())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(TestUtil.convertObjectToJsonBytes(chats))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Chats in the database
    //     List<Chats> chatsList = chatsRepository.findAll();
    //     assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void putWithMissingIdPathParamChats() throws Exception {
    //     int databaseSizeBeforeUpdate = chatsRepository.findAll().size();
    //     chats.setId(count.incrementAndGet());

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restChatsMockMvc
    //         .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chats)))
    //         .andExpect(status().isMethodNotAllowed());

    //     // Validate the Chats in the database
    //     List<Chats> chatsList = chatsRepository.findAll();
    //     assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void partialUpdateChatsWithPatch() throws Exception {
    //     // Initialize the database
    //     chatsRepository.saveAndFlush(chats);

    //     int databaseSizeBeforeUpdate = chatsRepository.findAll().size();

    //     // Update the chats using partial update
    //     Chats partialUpdatedChats = new Chats();
    //     partialUpdatedChats.setId(chats.getId());

    //     partialUpdatedChats.fromUserId(UPDATED_FROM_USER_ID).timeStamp(UPDATED_TIME_STAMP);

    //     restChatsMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, partialUpdatedChats.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChats))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the Chats in the database
    //     List<Chats> chatsList = chatsRepository.findAll();
    //     assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
    //     Chats testChats = chatsList.get(chatsList.size() - 1);
    //     assertThat(testChats.getFromUserId()).isEqualTo(UPDATED_FROM_USER_ID);
    //     assertThat(testChats.getToUserId()).isEqualTo(DEFAULT_TO_USER_ID);
    //     assertThat(testChats.getTimeStamp()).isEqualTo(UPDATED_TIME_STAMP);
    //     assertThat(testChats.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    //     assertThat(testChats.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    // }

    // @Test
    // @Transactional
    // void fullUpdateChatsWithPatch() throws Exception {
    //     // Initialize the database
    //     chatsRepository.saveAndFlush(chats);

    //     int databaseSizeBeforeUpdate = chatsRepository.findAll().size();

    //     // Update the chats using partial update
    //     Chats partialUpdatedChats = new Chats();
    //     partialUpdatedChats.setId(chats.getId());

    //     partialUpdatedChats
    //         .fromUserId(UPDATED_FROM_USER_ID)
    //         .toUserId(UPDATED_TO_USER_ID)
    //         .timeStamp(UPDATED_TIME_STAMP)
    //         .message(UPDATED_MESSAGE)
    //         .language(UPDATED_LANGUAGE);

    //     restChatsMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, partialUpdatedChats.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChats))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the Chats in the database
    //     List<Chats> chatsList = chatsRepository.findAll();
    //     assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
    //     Chats testChats = chatsList.get(chatsList.size() - 1);
    //     assertThat(testChats.getFromUserId()).isEqualTo(UPDATED_FROM_USER_ID);
    //     assertThat(testChats.getToUserId()).isEqualTo(UPDATED_TO_USER_ID);
    //     assertThat(testChats.getTimeStamp()).isEqualTo(UPDATED_TIME_STAMP);
    //     assertThat(testChats.getMessage()).isEqualTo(UPDATED_MESSAGE);
    //     assertThat(testChats.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    // }

    // @Test
    // @Transactional
    // void patchNonExistingChats() throws Exception {
    //     int databaseSizeBeforeUpdate = chatsRepository.findAll().size();
    //     chats.setId(count.incrementAndGet());

    //     // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //     restChatsMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, chats.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(TestUtil.convertObjectToJsonBytes(chats))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Chats in the database
    //     List<Chats> chatsList = chatsRepository.findAll();
    //     assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void patchWithIdMismatchChats() throws Exception {
    //     int databaseSizeBeforeUpdate = chatsRepository.findAll().size();
    //     chats.setId(count.incrementAndGet());

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restChatsMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, count.incrementAndGet())
    //                 .contentType("application/merge-patch+json")
    //                 .content(TestUtil.convertObjectToJsonBytes(chats))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Chats in the database
    //     List<Chats> chatsList = chatsRepository.findAll();
    //     assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void patchWithMissingIdPathParamChats() throws Exception {
    //     int databaseSizeBeforeUpdate = chatsRepository.findAll().size();
    //     chats.setId(count.incrementAndGet());

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restChatsMockMvc
    //         .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(chats)))
    //         .andExpect(status().isMethodNotAllowed());

    //     // Validate the Chats in the database
    //     List<Chats> chatsList = chatsRepository.findAll();
    //     assertThat(chatsList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void deleteChats() throws Exception {
    //     // Initialize the database
    //     chatsRepository.saveAndFlush(chats);

    //     int databaseSizeBeforeDelete = chatsRepository.findAll().size();

    //     // Delete the chats
    //     restChatsMockMvc
    //         .perform(delete(ENTITY_API_URL_ID, chats.getId()).accept(MediaType.APPLICATION_JSON))
    //         .andExpect(status().isNoContent());

    //     // Validate the database contains one less item
    //     List<Chats> chatsList = chatsRepository.findAll();
    //     assertThat(chatsList).hasSize(databaseSizeBeforeDelete - 1);
    // }
}
