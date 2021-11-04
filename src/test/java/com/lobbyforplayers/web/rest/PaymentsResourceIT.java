package com.lobbyforplayers.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lobbyforplayers.IntegrationTest;
import com.lobbyforplayers.domain.Payments;
import com.lobbyforplayers.repository.PaymentsRepository;
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
 * Integration tests for the {@link PaymentsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PaymentsResourceIT {

    private static final String DEFAULT_BUYER_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_BUYER_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SELLER_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_SELLER_USER_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SELLER_RECIEVED = false;
    private static final Boolean UPDATED_SELLER_RECIEVED = true;

    private static final String DEFAULT_BUYER_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_BUYER_TRANSACTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SELLER_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_SELLER_TRANSACTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PACKAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGE_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_GAME = "AAAAAAAAAA";
    private static final String UPDATED_GAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private MockMvc restPaymentsMockMvc;

    private Payments payments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payments createEntity() {
        Payments payments = new Payments()
            .buyerUserId(DEFAULT_BUYER_USER_ID)
            .sellerUserId(DEFAULT_SELLER_USER_ID)
            .sellerRecieved(DEFAULT_SELLER_RECIEVED)
            .buyerTransactionId(DEFAULT_BUYER_TRANSACTION_ID)
            .sellerTransactionId(DEFAULT_SELLER_TRANSACTION_ID)
            .orderId(DEFAULT_ORDER_ID)
            .packageId(DEFAULT_PACKAGE_ID)
            .amount(DEFAULT_AMOUNT)
            .game(DEFAULT_GAME)
            .status(DEFAULT_STATUS);
        return payments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payments createUpdatedEntity() {
        Payments payments = new Payments()
            .buyerUserId(UPDATED_BUYER_USER_ID)
            .sellerUserId(UPDATED_SELLER_USER_ID)
            .sellerRecieved(UPDATED_SELLER_RECIEVED)
            .buyerTransactionId(UPDATED_BUYER_TRANSACTION_ID)
            .sellerTransactionId(UPDATED_SELLER_TRANSACTION_ID)
            .orderId(UPDATED_ORDER_ID)
            .packageId(UPDATED_PACKAGE_ID)
            .amount(UPDATED_AMOUNT)
            .game(UPDATED_GAME)
            .status(UPDATED_STATUS);
        return payments;
    }

    @BeforeEach
    public void initTest() {
        paymentsRepository.deleteAll();
        payments = createEntity();
    }

    @Test
    void createPayments() throws Exception {
        int databaseSizeBeforeCreate = paymentsRepository.findAll().size();
        // Create the Payments
        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payments)))
            .andExpect(status().isCreated());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeCreate + 1);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getBuyerUserId()).isEqualTo(DEFAULT_BUYER_USER_ID);
        assertThat(testPayments.getSellerUserId()).isEqualTo(DEFAULT_SELLER_USER_ID);
        assertThat(testPayments.getSellerRecieved()).isEqualTo(DEFAULT_SELLER_RECIEVED);
        assertThat(testPayments.getBuyerTransactionId()).isEqualTo(DEFAULT_BUYER_TRANSACTION_ID);
        assertThat(testPayments.getSellerTransactionId()).isEqualTo(DEFAULT_SELLER_TRANSACTION_ID);
        assertThat(testPayments.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testPayments.getPackageId()).isEqualTo(DEFAULT_PACKAGE_ID);
        assertThat(testPayments.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPayments.getGame()).isEqualTo(DEFAULT_GAME);
        assertThat(testPayments.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    void createPaymentsWithExistingId() throws Exception {
        // Create the Payments with an existing ID
        payments.setId("existing_id");

        int databaseSizeBeforeCreate = paymentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payments)))
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkBuyerUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setBuyerUserId(null);

        // Create the Payments, which fails.

        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payments)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkSellerUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setSellerUserId(null);

        // Create the Payments, which fails.

        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payments)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkSellerRecievedIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setSellerRecieved(null);

        // Create the Payments, which fails.

        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payments)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkOrderIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setOrderId(null);

        // Create the Payments, which fails.

        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payments)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPackageIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setPackageId(null);

        // Create the Payments, which fails.

        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payments)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setAmount(null);

        // Create the Payments, which fails.

        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payments)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setStatus(null);

        // Create the Payments, which fails.

        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payments)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllPayments() throws Exception {
        // Initialize the database
        paymentsRepository.save(payments);

        // Get all the paymentsList
        restPaymentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payments.getId())))
            .andExpect(jsonPath("$.[*].buyerUserId").value(hasItem(DEFAULT_BUYER_USER_ID)))
            .andExpect(jsonPath("$.[*].sellerUserId").value(hasItem(DEFAULT_SELLER_USER_ID)))
            .andExpect(jsonPath("$.[*].sellerRecieved").value(hasItem(DEFAULT_SELLER_RECIEVED.booleanValue())))
            .andExpect(jsonPath("$.[*].buyerTransactionId").value(hasItem(DEFAULT_BUYER_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].sellerTransactionId").value(hasItem(DEFAULT_SELLER_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].packageId").value(hasItem(DEFAULT_PACKAGE_ID)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    void getPayments() throws Exception {
        // Initialize the database
        paymentsRepository.save(payments);

        // Get the payments
        restPaymentsMockMvc
            .perform(get(ENTITY_API_URL_ID, payments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payments.getId()))
            .andExpect(jsonPath("$.buyerUserId").value(DEFAULT_BUYER_USER_ID))
            .andExpect(jsonPath("$.sellerUserId").value(DEFAULT_SELLER_USER_ID))
            .andExpect(jsonPath("$.sellerRecieved").value(DEFAULT_SELLER_RECIEVED.booleanValue()))
            .andExpect(jsonPath("$.buyerTransactionId").value(DEFAULT_BUYER_TRANSACTION_ID))
            .andExpect(jsonPath("$.sellerTransactionId").value(DEFAULT_SELLER_TRANSACTION_ID))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.packageId").value(DEFAULT_PACKAGE_ID))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.game").value(DEFAULT_GAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    void getNonExistingPayments() throws Exception {
        // Get the payments
        restPaymentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewPayments() throws Exception {
        // Initialize the database
        paymentsRepository.save(payments);

        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // Update the payments
        Payments updatedPayments = paymentsRepository.findById(payments.getId()).get();
        updatedPayments
            .buyerUserId(UPDATED_BUYER_USER_ID)
            .sellerUserId(UPDATED_SELLER_USER_ID)
            .sellerRecieved(UPDATED_SELLER_RECIEVED)
            .buyerTransactionId(UPDATED_BUYER_TRANSACTION_ID)
            .sellerTransactionId(UPDATED_SELLER_TRANSACTION_ID)
            .orderId(UPDATED_ORDER_ID)
            .packageId(UPDATED_PACKAGE_ID)
            .amount(UPDATED_AMOUNT)
            .game(UPDATED_GAME)
            .status(UPDATED_STATUS);

        restPaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPayments.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPayments))
            )
            .andExpect(status().isOk());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getBuyerUserId()).isEqualTo(UPDATED_BUYER_USER_ID);
        assertThat(testPayments.getSellerUserId()).isEqualTo(UPDATED_SELLER_USER_ID);
        assertThat(testPayments.getSellerRecieved()).isEqualTo(UPDATED_SELLER_RECIEVED);
        assertThat(testPayments.getBuyerTransactionId()).isEqualTo(UPDATED_BUYER_TRANSACTION_ID);
        assertThat(testPayments.getSellerTransactionId()).isEqualTo(UPDATED_SELLER_TRANSACTION_ID);
        assertThat(testPayments.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testPayments.getPackageId()).isEqualTo(UPDATED_PACKAGE_ID);
        assertThat(testPayments.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayments.getGame()).isEqualTo(UPDATED_GAME);
        assertThat(testPayments.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    void putNonExistingPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payments.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payments)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePaymentsWithPatch() throws Exception {
        // Initialize the database
        paymentsRepository.save(payments);

        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // Update the payments using partial update
        Payments partialUpdatedPayments = new Payments();
        partialUpdatedPayments.setId(payments.getId());

        partialUpdatedPayments
            .buyerUserId(UPDATED_BUYER_USER_ID)
            .sellerUserId(UPDATED_SELLER_USER_ID)
            .sellerTransactionId(UPDATED_SELLER_TRANSACTION_ID)
            .orderId(UPDATED_ORDER_ID);

        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayments))
            )
            .andExpect(status().isOk());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getBuyerUserId()).isEqualTo(UPDATED_BUYER_USER_ID);
        assertThat(testPayments.getSellerUserId()).isEqualTo(UPDATED_SELLER_USER_ID);
        assertThat(testPayments.getSellerRecieved()).isEqualTo(DEFAULT_SELLER_RECIEVED);
        assertThat(testPayments.getBuyerTransactionId()).isEqualTo(DEFAULT_BUYER_TRANSACTION_ID);
        assertThat(testPayments.getSellerTransactionId()).isEqualTo(UPDATED_SELLER_TRANSACTION_ID);
        assertThat(testPayments.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testPayments.getPackageId()).isEqualTo(DEFAULT_PACKAGE_ID);
        assertThat(testPayments.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPayments.getGame()).isEqualTo(DEFAULT_GAME);
        assertThat(testPayments.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    void fullUpdatePaymentsWithPatch() throws Exception {
        // Initialize the database
        paymentsRepository.save(payments);

        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // Update the payments using partial update
        Payments partialUpdatedPayments = new Payments();
        partialUpdatedPayments.setId(payments.getId());

        partialUpdatedPayments
            .buyerUserId(UPDATED_BUYER_USER_ID)
            .sellerUserId(UPDATED_SELLER_USER_ID)
            .sellerRecieved(UPDATED_SELLER_RECIEVED)
            .buyerTransactionId(UPDATED_BUYER_TRANSACTION_ID)
            .sellerTransactionId(UPDATED_SELLER_TRANSACTION_ID)
            .orderId(UPDATED_ORDER_ID)
            .packageId(UPDATED_PACKAGE_ID)
            .amount(UPDATED_AMOUNT)
            .game(UPDATED_GAME)
            .status(UPDATED_STATUS);

        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayments))
            )
            .andExpect(status().isOk());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getBuyerUserId()).isEqualTo(UPDATED_BUYER_USER_ID);
        assertThat(testPayments.getSellerUserId()).isEqualTo(UPDATED_SELLER_USER_ID);
        assertThat(testPayments.getSellerRecieved()).isEqualTo(UPDATED_SELLER_RECIEVED);
        assertThat(testPayments.getBuyerTransactionId()).isEqualTo(UPDATED_BUYER_TRANSACTION_ID);
        assertThat(testPayments.getSellerTransactionId()).isEqualTo(UPDATED_SELLER_TRANSACTION_ID);
        assertThat(testPayments.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testPayments.getPackageId()).isEqualTo(UPDATED_PACKAGE_ID);
        assertThat(testPayments.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayments.getGame()).isEqualTo(UPDATED_GAME);
        assertThat(testPayments.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    void patchNonExistingPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, payments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(payments)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePayments() throws Exception {
        // Initialize the database
        paymentsRepository.save(payments);

        int databaseSizeBeforeDelete = paymentsRepository.findAll().size();

        // Delete the payments
        restPaymentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, payments.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
