package com.lobbyforplayers.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lobbyforplayers.IntegrationTest;
import com.lobbyforplayers.domain.Details;
import com.lobbyforplayers.repository.DetailsRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link DetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DetailsResourceIT {
    // private static final String DEFAULT_LOGIN_NAME = "AAAAAAAAAA";
    // private static final String UPDATED_LOGIN_NAME = "BBBBBBBBBB";

    // private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    // private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    // private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    // private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    // private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    // private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    // private static final String DEFAULT_SECURTIY_QUESTION = "AAAAAAAAAA";
    // private static final String UPDATED_SECURTIY_QUESTION = "BBBBBBBBBB";

    // private static final String DEFAULT_SECURITY_ANSWER = "AAAAAAAAAA";
    // private static final String UPDATED_SECURITY_ANSWER = "BBBBBBBBBB";

    // private static final String DEFAULT_PARENTAL_PASSWORD = "AAAAAAAAAA";
    // private static final String UPDATED_PARENTAL_PASSWORD = "BBBBBBBBBB";

    // private static final String DEFAULT_FIRST_CD_KEY = "AAAAAAAAAA";
    // private static final String UPDATED_FIRST_CD_KEY = "BBBBBBBBBB";

    // private static final String DEFAULT_OTHER_INFORMATION = "AAAAAAAAAA";
    // private static final String UPDATED_OTHER_INFORMATION = "BBBBBBBBBB";

    // private static final Instant DEFAULT_ENTERED_DATE = Instant.ofEpochMilli(0L);
    // private static final Instant UPDATED_ENTERED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    // private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    // private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    // private static final String ENTITY_API_URL = "/api/details";
    // private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    // private static Random random = new Random();
    // private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    // @Autowired
    // private DetailsRepository detailsRepository;

    // @Autowired
    // private EntityManager em;

    // @Autowired
    // private MockMvc restDetailsMockMvc;

    // private Details details;

    // /**
    //  * Create an entity for this test.
    //  *
    //  * This is a static method, as tests for other entities might also need it,
    //  * if they test an entity which requires the current entity.
    //  */
    // public static Details createEntity(EntityManager em) {
    //     Details details = new Details()
    //         .loginName(DEFAULT_LOGIN_NAME)
    //         .password(DEFAULT_PASSWORD)
    //         .lastName(DEFAULT_LAST_NAME)
    //         .firstName(DEFAULT_FIRST_NAME)
    //         .securtiyQuestion(DEFAULT_SECURTIY_QUESTION)
    //         .securityAnswer(DEFAULT_SECURITY_ANSWER)
    //         .parentalPassword(DEFAULT_PARENTAL_PASSWORD)
    //         .firstCdKey(DEFAULT_FIRST_CD_KEY)
    //         .otherInformation(DEFAULT_OTHER_INFORMATION)
    //         .enteredDate(DEFAULT_ENTERED_DATE)
    //         .orderDate(DEFAULT_ORDER_DATE);
    //     return details;
    // }

    // /**
    //  * Create an updated entity for this test.
    //  *
    //  * This is a static method, as tests for other entities might also need it,
    //  * if they test an entity which requires the current entity.
    //  */
    // public static Details createUpdatedEntity(EntityManager em) {
    //     Details details = new Details()
    //         .loginName(UPDATED_LOGIN_NAME)
    //         .password(UPDATED_PASSWORD)
    //         .lastName(UPDATED_LAST_NAME)
    //         .firstName(UPDATED_FIRST_NAME)
    //         .securtiyQuestion(UPDATED_SECURTIY_QUESTION)
    //         .securityAnswer(UPDATED_SECURITY_ANSWER)
    //         .parentalPassword(UPDATED_PARENTAL_PASSWORD)
    //         .firstCdKey(UPDATED_FIRST_CD_KEY)
    //         .otherInformation(UPDATED_OTHER_INFORMATION)
    //         .enteredDate(UPDATED_ENTERED_DATE)
    //         .orderDate(UPDATED_ORDER_DATE);
    //     return details;
    // }

    // @BeforeEach
    // public void initTest() {
    //     details = createEntity(em);
    // }

    // @Test
    // @Transactional
    // void createDetails() throws Exception {
    //     int databaseSizeBeforeCreate = detailsRepository.findAll().size();
    //     // Create the Details
    //     restDetailsMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(details)))
    //         .andExpect(status().isCreated());

    //     // Validate the Details in the database
    //     List<Details> detailsList = detailsRepository.findAll();
    //     assertThat(detailsList).hasSize(databaseSizeBeforeCreate + 1);
    //     Details testDetails = detailsList.get(detailsList.size() - 1);
    //     assertThat(testDetails.getLoginName()).isEqualTo(DEFAULT_LOGIN_NAME);
    //     assertThat(testDetails.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    //     assertThat(testDetails.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    //     assertThat(testDetails.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
    //     assertThat(testDetails.getSecurtiyQuestion()).isEqualTo(DEFAULT_SECURTIY_QUESTION);
    //     assertThat(testDetails.getSecurityAnswer()).isEqualTo(DEFAULT_SECURITY_ANSWER);
    //     assertThat(testDetails.getParentalPassword()).isEqualTo(DEFAULT_PARENTAL_PASSWORD);
    //     assertThat(testDetails.getFirstCdKey()).isEqualTo(DEFAULT_FIRST_CD_KEY);
    //     assertThat(testDetails.getOtherInformation()).isEqualTo(DEFAULT_OTHER_INFORMATION);
    //     assertThat(testDetails.getEnteredDate()).isEqualTo(DEFAULT_ENTERED_DATE);
    //     assertThat(testDetails.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
    // }

    // @Test
    // @Transactional
    // void createDetailsWithExistingId() throws Exception {
    //     // Create the Details with an existing ID
    //     details.setId(1L);

    //     int databaseSizeBeforeCreate = detailsRepository.findAll().size();

    //     // An entity with an existing ID cannot be created, so this API call must fail
    //     restDetailsMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(details)))
    //         .andExpect(status().isBadRequest());

    //     // Validate the Details in the database
    //     List<Details> detailsList = detailsRepository.findAll();
    //     assertThat(detailsList).hasSize(databaseSizeBeforeCreate);
    // }

    // @Test
    // @Transactional
    // void checkLoginNameIsRequired() throws Exception {
    //     int databaseSizeBeforeTest = detailsRepository.findAll().size();
    //     // set the field null
    //     details.setLoginName(null);

    //     // Create the Details, which fails.

    //     restDetailsMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(details)))
    //         .andExpect(status().isBadRequest());

    //     List<Details> detailsList = detailsRepository.findAll();
    //     assertThat(detailsList).hasSize(databaseSizeBeforeTest);
    // }

    // @Test
    // @Transactional
    // void checkPasswordIsRequired() throws Exception {
    //     int databaseSizeBeforeTest = detailsRepository.findAll().size();
    //     // set the field null
    //     details.setPassword(null);

    //     // Create the Details, which fails.

    //     restDetailsMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(details)))
    //         .andExpect(status().isBadRequest());

    //     List<Details> detailsList = detailsRepository.findAll();
    //     assertThat(detailsList).hasSize(databaseSizeBeforeTest);
    // }

    // @Test
    // @Transactional
    // void checkEnteredDateIsRequired() throws Exception {
    //     int databaseSizeBeforeTest = detailsRepository.findAll().size();
    //     // set the field null
    //     details.setEnteredDate(null);

    //     // Create the Details, which fails.

    //     restDetailsMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(details)))
    //         .andExpect(status().isBadRequest());

    //     List<Details> detailsList = detailsRepository.findAll();
    //     assertThat(detailsList).hasSize(databaseSizeBeforeTest);
    // }

    // @Test
    // @Transactional
    // void getAllDetails() throws Exception {
    //     // Initialize the database
    //     detailsRepository.saveAndFlush(details);

    //     // Get all the detailsList
    //     restDetailsMockMvc
    //         .perform(get(ENTITY_API_URL + "?sort=id,desc"))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andExpect(jsonPath("$.[*].id").value(hasItem(details.getId().intValue())))
    //         .andExpect(jsonPath("$.[*].loginName").value(hasItem(DEFAULT_LOGIN_NAME)))
    //         .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
    //         .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
    //         .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
    //         .andExpect(jsonPath("$.[*].securtiyQuestion").value(hasItem(DEFAULT_SECURTIY_QUESTION)))
    //         .andExpect(jsonPath("$.[*].securityAnswer").value(hasItem(DEFAULT_SECURITY_ANSWER)))
    //         .andExpect(jsonPath("$.[*].parentalPassword").value(hasItem(DEFAULT_PARENTAL_PASSWORD)))
    //         .andExpect(jsonPath("$.[*].firstCdKey").value(hasItem(DEFAULT_FIRST_CD_KEY)))
    //         .andExpect(jsonPath("$.[*].otherInformation").value(hasItem(DEFAULT_OTHER_INFORMATION)))
    //         .andExpect(jsonPath("$.[*].enteredDate").value(hasItem(DEFAULT_ENTERED_DATE.toString())))
    //         .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())));
    // }

    // @Test
    // @Transactional
    // void getDetails() throws Exception {
    //     // Initialize the database
    //     detailsRepository.saveAndFlush(details);

    //     // Get the details
    //     restDetailsMockMvc
    //         .perform(get(ENTITY_API_URL_ID, details.getId()))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andExpect(jsonPath("$.id").value(details.getId().intValue()))
    //         .andExpect(jsonPath("$.loginName").value(DEFAULT_LOGIN_NAME))
    //         .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
    //         .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
    //         .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
    //         .andExpect(jsonPath("$.securtiyQuestion").value(DEFAULT_SECURTIY_QUESTION))
    //         .andExpect(jsonPath("$.securityAnswer").value(DEFAULT_SECURITY_ANSWER))
    //         .andExpect(jsonPath("$.parentalPassword").value(DEFAULT_PARENTAL_PASSWORD))
    //         .andExpect(jsonPath("$.firstCdKey").value(DEFAULT_FIRST_CD_KEY))
    //         .andExpect(jsonPath("$.otherInformation").value(DEFAULT_OTHER_INFORMATION))
    //         .andExpect(jsonPath("$.enteredDate").value(DEFAULT_ENTERED_DATE.toString()))
    //         .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()));
    // }

    // @Test
    // @Transactional
    // void getNonExistingDetails() throws Exception {
    //     // Get the details
    //     restDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    // }

    // @Test
    // @Transactional
    // void putNewDetails() throws Exception {
    //     // Initialize the database
    //     detailsRepository.saveAndFlush(details);

    //     int databaseSizeBeforeUpdate = detailsRepository.findAll().size();

    //     // Update the details
    //     Details updatedDetails = detailsRepository.findById(details.getId()).get();
    //     // Disconnect from session so that the updates on updatedDetails are not directly saved in db
    //     em.detach(updatedDetails);
    //     updatedDetails
    //         .loginName(UPDATED_LOGIN_NAME)
    //         .password(UPDATED_PASSWORD)
    //         .lastName(UPDATED_LAST_NAME)
    //         .firstName(UPDATED_FIRST_NAME)
    //         .securtiyQuestion(UPDATED_SECURTIY_QUESTION)
    //         .securityAnswer(UPDATED_SECURITY_ANSWER)
    //         .parentalPassword(UPDATED_PARENTAL_PASSWORD)
    //         .firstCdKey(UPDATED_FIRST_CD_KEY)
    //         .otherInformation(UPDATED_OTHER_INFORMATION)
    //         .enteredDate(UPDATED_ENTERED_DATE)
    //         .orderDate(UPDATED_ORDER_DATE);

    //     restDetailsMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, updatedDetails.getId())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(TestUtil.convertObjectToJsonBytes(updatedDetails))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the Details in the database
    //     List<Details> detailsList = detailsRepository.findAll();
    //     assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
    //     Details testDetails = detailsList.get(detailsList.size() - 1);
    //     assertThat(testDetails.getLoginName()).isEqualTo(UPDATED_LOGIN_NAME);
    //     assertThat(testDetails.getPassword()).isEqualTo(UPDATED_PASSWORD);
    //     assertThat(testDetails.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    //     assertThat(testDetails.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
    //     assertThat(testDetails.getSecurtiyQuestion()).isEqualTo(UPDATED_SECURTIY_QUESTION);
    //     assertThat(testDetails.getSecurityAnswer()).isEqualTo(UPDATED_SECURITY_ANSWER);
    //     assertThat(testDetails.getParentalPassword()).isEqualTo(UPDATED_PARENTAL_PASSWORD);
    //     assertThat(testDetails.getFirstCdKey()).isEqualTo(UPDATED_FIRST_CD_KEY);
    //     assertThat(testDetails.getOtherInformation()).isEqualTo(UPDATED_OTHER_INFORMATION);
    //     assertThat(testDetails.getEnteredDate()).isEqualTo(UPDATED_ENTERED_DATE);
    //     assertThat(testDetails.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
    // }

    // @Test
    // @Transactional
    // void putNonExistingDetails() throws Exception {
    //     int databaseSizeBeforeUpdate = detailsRepository.findAll().size();
    //     details.setId(count.incrementAndGet());

    //     // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //     restDetailsMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, details.getId())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(TestUtil.convertObjectToJsonBytes(details))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Details in the database
    //     List<Details> detailsList = detailsRepository.findAll();
    //     assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void putWithIdMismatchDetails() throws Exception {
    //     int databaseSizeBeforeUpdate = detailsRepository.findAll().size();
    //     details.setId(count.incrementAndGet());

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restDetailsMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, count.incrementAndGet())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(TestUtil.convertObjectToJsonBytes(details))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Details in the database
    //     List<Details> detailsList = detailsRepository.findAll();
    //     assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void putWithMissingIdPathParamDetails() throws Exception {
    //     int databaseSizeBeforeUpdate = detailsRepository.findAll().size();
    //     details.setId(count.incrementAndGet());

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restDetailsMockMvc
    //         .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(details)))
    //         .andExpect(status().isMethodNotAllowed());

    //     // Validate the Details in the database
    //     List<Details> detailsList = detailsRepository.findAll();
    //     assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void partialUpdateDetailsWithPatch() throws Exception {
    //     // Initialize the database
    //     detailsRepository.saveAndFlush(details);

    //     int databaseSizeBeforeUpdate = detailsRepository.findAll().size();

    //     // Update the details using partial update
    //     Details partialUpdatedDetails = new Details();
    //     partialUpdatedDetails.setId(details.getId());

    //     partialUpdatedDetails
    //         .loginName(UPDATED_LOGIN_NAME)
    //         .password(UPDATED_PASSWORD)
    //         .lastName(UPDATED_LAST_NAME)
    //         .securityAnswer(UPDATED_SECURITY_ANSWER)
    //         .parentalPassword(UPDATED_PARENTAL_PASSWORD)
    //         .enteredDate(UPDATED_ENTERED_DATE)
    //         .orderDate(UPDATED_ORDER_DATE);

    //     restDetailsMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, partialUpdatedDetails.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetails))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the Details in the database
    //     List<Details> detailsList = detailsRepository.findAll();
    //     assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
    //     Details testDetails = detailsList.get(detailsList.size() - 1);
    //     assertThat(testDetails.getLoginName()).isEqualTo(UPDATED_LOGIN_NAME);
    //     assertThat(testDetails.getPassword()).isEqualTo(UPDATED_PASSWORD);
    //     assertThat(testDetails.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    //     assertThat(testDetails.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
    //     assertThat(testDetails.getSecurtiyQuestion()).isEqualTo(DEFAULT_SECURTIY_QUESTION);
    //     assertThat(testDetails.getSecurityAnswer()).isEqualTo(UPDATED_SECURITY_ANSWER);
    //     assertThat(testDetails.getParentalPassword()).isEqualTo(UPDATED_PARENTAL_PASSWORD);
    //     assertThat(testDetails.getFirstCdKey()).isEqualTo(DEFAULT_FIRST_CD_KEY);
    //     assertThat(testDetails.getOtherInformation()).isEqualTo(DEFAULT_OTHER_INFORMATION);
    //     assertThat(testDetails.getEnteredDate()).isEqualTo(UPDATED_ENTERED_DATE);
    //     assertThat(testDetails.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
    // }

    // @Test
    // @Transactional
    // void fullUpdateDetailsWithPatch() throws Exception {
    //     // Initialize the database
    //     detailsRepository.saveAndFlush(details);

    //     int databaseSizeBeforeUpdate = detailsRepository.findAll().size();

    //     // Update the details using partial update
    //     Details partialUpdatedDetails = new Details();
    //     partialUpdatedDetails.setId(details.getId());

    //     partialUpdatedDetails
    //         .loginName(UPDATED_LOGIN_NAME)
    //         .password(UPDATED_PASSWORD)
    //         .lastName(UPDATED_LAST_NAME)
    //         .firstName(UPDATED_FIRST_NAME)
    //         .securtiyQuestion(UPDATED_SECURTIY_QUESTION)
    //         .securityAnswer(UPDATED_SECURITY_ANSWER)
    //         .parentalPassword(UPDATED_PARENTAL_PASSWORD)
    //         .firstCdKey(UPDATED_FIRST_CD_KEY)
    //         .otherInformation(UPDATED_OTHER_INFORMATION)
    //         .enteredDate(UPDATED_ENTERED_DATE)
    //         .orderDate(UPDATED_ORDER_DATE);

    //     restDetailsMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, partialUpdatedDetails.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetails))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the Details in the database
    //     List<Details> detailsList = detailsRepository.findAll();
    //     assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
    //     Details testDetails = detailsList.get(detailsList.size() - 1);
    //     assertThat(testDetails.getLoginName()).isEqualTo(UPDATED_LOGIN_NAME);
    //     assertThat(testDetails.getPassword()).isEqualTo(UPDATED_PASSWORD);
    //     assertThat(testDetails.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    //     assertThat(testDetails.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
    //     assertThat(testDetails.getSecurtiyQuestion()).isEqualTo(UPDATED_SECURTIY_QUESTION);
    //     assertThat(testDetails.getSecurityAnswer()).isEqualTo(UPDATED_SECURITY_ANSWER);
    //     assertThat(testDetails.getParentalPassword()).isEqualTo(UPDATED_PARENTAL_PASSWORD);
    //     assertThat(testDetails.getFirstCdKey()).isEqualTo(UPDATED_FIRST_CD_KEY);
    //     assertThat(testDetails.getOtherInformation()).isEqualTo(UPDATED_OTHER_INFORMATION);
    //     assertThat(testDetails.getEnteredDate()).isEqualTo(UPDATED_ENTERED_DATE);
    //     assertThat(testDetails.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
    // }

    // @Test
    // @Transactional
    // void patchNonExistingDetails() throws Exception {
    //     int databaseSizeBeforeUpdate = detailsRepository.findAll().size();
    //     details.setId(count.incrementAndGet());

    //     // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //     restDetailsMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, details.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(TestUtil.convertObjectToJsonBytes(details))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Details in the database
    //     List<Details> detailsList = detailsRepository.findAll();
    //     assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void patchWithIdMismatchDetails() throws Exception {
    //     int databaseSizeBeforeUpdate = detailsRepository.findAll().size();
    //     details.setId(count.incrementAndGet());

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restDetailsMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, count.incrementAndGet())
    //                 .contentType("application/merge-patch+json")
    //                 .content(TestUtil.convertObjectToJsonBytes(details))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Details in the database
    //     List<Details> detailsList = detailsRepository.findAll();
    //     assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void patchWithMissingIdPathParamDetails() throws Exception {
    //     int databaseSizeBeforeUpdate = detailsRepository.findAll().size();
    //     details.setId(count.incrementAndGet());

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restDetailsMockMvc
    //         .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(details)))
    //         .andExpect(status().isMethodNotAllowed());

    //     // Validate the Details in the database
    //     List<Details> detailsList = detailsRepository.findAll();
    //     assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void deleteDetails() throws Exception {
    //     // Initialize the database
    //     detailsRepository.saveAndFlush(details);

    //     int databaseSizeBeforeDelete = detailsRepository.findAll().size();

    //     // Delete the details
    //     restDetailsMockMvc
    //         .perform(delete(ENTITY_API_URL_ID, details.getId()).accept(MediaType.APPLICATION_JSON))
    //         .andExpect(status().isNoContent());

    //     // Validate the database contains one less item
    //     List<Details> detailsList = detailsRepository.findAll();
    //     assertThat(detailsList).hasSize(databaseSizeBeforeDelete - 1);
    // }
}
