package com.lobbyforplayers.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lobbyforplayers.IntegrationTest;
import com.lobbyforplayers.domain.Bargain;
import com.lobbyforplayers.repository.BargainRepository;
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
 * Integration tests for the {@link BargainResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BargainResourceIT {

    private static final Double DEFAULT_BARGAIN_PRICE = 1D;
    private static final Double UPDATED_BARGAIN_PRICE = 2D;

    private static final String DEFAULT_ITEM_ID = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SELLER_APPROVED = false;
    private static final Boolean UPDATED_SELLER_APPROVED = true;

    private static final Boolean DEFAULT_BUYER_APPROVED = false;
    private static final Boolean UPDATED_BUYER_APPROVED = true;

    private static final String DEFAULT_SELLER_ID = "AAAAAAAAAA";
    private static final String UPDATED_SELLER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BUYER_ID = "AAAAAAAAAA";
    private static final String UPDATED_BUYER_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bargains";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private BargainRepository bargainRepository;

    @Autowired
    private MockMvc restBargainMockMvc;

    private Bargain bargain;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bargain createEntity() {
        Bargain bargain = new Bargain()
            .bargainPrice(DEFAULT_BARGAIN_PRICE)
            .itemId(DEFAULT_ITEM_ID)
            .sellerApproved(DEFAULT_SELLER_APPROVED)
            .buyerApproved(DEFAULT_BUYER_APPROVED)
            .sellerId(DEFAULT_SELLER_ID)
            .buyerId(DEFAULT_BUYER_ID);
        return bargain;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bargain createUpdatedEntity() {
        Bargain bargain = new Bargain()
            .bargainPrice(UPDATED_BARGAIN_PRICE)
            .itemId(UPDATED_ITEM_ID)
            .sellerApproved(UPDATED_SELLER_APPROVED)
            .buyerApproved(UPDATED_BUYER_APPROVED)
            .sellerId(UPDATED_SELLER_ID)
            .buyerId(UPDATED_BUYER_ID);
        return bargain;
    }

    @BeforeEach
    public void initTest() {
        bargainRepository.deleteAll();
        bargain = createEntity();
    }

    @Test
    void createBargain() throws Exception {
        int databaseSizeBeforeCreate = bargainRepository.findAll().size();
        // Create the Bargain
        restBargainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bargain)))
            .andExpect(status().isCreated());

        // Validate the Bargain in the database
        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeCreate + 1);
        Bargain testBargain = bargainList.get(bargainList.size() - 1);
        assertThat(testBargain.getBargainPrice()).isEqualTo(DEFAULT_BARGAIN_PRICE);
        assertThat(testBargain.getItemId()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testBargain.getSellerApproved()).isEqualTo(DEFAULT_SELLER_APPROVED);
        assertThat(testBargain.getBuyerApproved()).isEqualTo(DEFAULT_BUYER_APPROVED);
        assertThat(testBargain.getSellerId()).isEqualTo(DEFAULT_SELLER_ID);
        assertThat(testBargain.getBuyerId()).isEqualTo(DEFAULT_BUYER_ID);
    }

    @Test
    void createBargainWithExistingId() throws Exception {
        // Create the Bargain with an existing ID
        bargain.setId("existing_id");

        int databaseSizeBeforeCreate = bargainRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBargainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bargain)))
            .andExpect(status().isBadRequest());

        // Validate the Bargain in the database
        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkBargainPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = bargainRepository.findAll().size();
        // set the field null
        bargain.setBargainPrice(null);

        // Create the Bargain, which fails.

        restBargainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bargain)))
            .andExpect(status().isBadRequest());

        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkItemIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bargainRepository.findAll().size();
        // set the field null
        bargain.setItemId(null);

        // Create the Bargain, which fails.

        restBargainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bargain)))
            .andExpect(status().isBadRequest());

        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkSellerApprovedIsRequired() throws Exception {
        int databaseSizeBeforeTest = bargainRepository.findAll().size();
        // set the field null
        bargain.setSellerApproved(null);

        // Create the Bargain, which fails.

        restBargainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bargain)))
            .andExpect(status().isBadRequest());

        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkBuyerApprovedIsRequired() throws Exception {
        int databaseSizeBeforeTest = bargainRepository.findAll().size();
        // set the field null
        bargain.setBuyerApproved(null);

        // Create the Bargain, which fails.

        restBargainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bargain)))
            .andExpect(status().isBadRequest());

        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkSellerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bargainRepository.findAll().size();
        // set the field null
        bargain.setSellerId(null);

        // Create the Bargain, which fails.

        restBargainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bargain)))
            .andExpect(status().isBadRequest());

        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkBuyerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bargainRepository.findAll().size();
        // set the field null
        bargain.setBuyerId(null);

        // Create the Bargain, which fails.

        restBargainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bargain)))
            .andExpect(status().isBadRequest());

        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllBargains() throws Exception {
        // Initialize the database
        bargainRepository.save(bargain);

        // Get all the bargainList
        restBargainMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bargain.getId())))
            .andExpect(jsonPath("$.[*].bargainPrice").value(hasItem(DEFAULT_BARGAIN_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID)))
            .andExpect(jsonPath("$.[*].sellerApproved").value(hasItem(DEFAULT_SELLER_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].buyerApproved").value(hasItem(DEFAULT_BUYER_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].sellerId").value(hasItem(DEFAULT_SELLER_ID)))
            .andExpect(jsonPath("$.[*].buyerId").value(hasItem(DEFAULT_BUYER_ID)));
    }

    @Test
    void getBargain() throws Exception {
        // Initialize the database
        bargainRepository.save(bargain);

        // Get the bargain
        restBargainMockMvc
            .perform(get(ENTITY_API_URL_ID, bargain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bargain.getId()))
            .andExpect(jsonPath("$.bargainPrice").value(DEFAULT_BARGAIN_PRICE.doubleValue()))
            .andExpect(jsonPath("$.itemId").value(DEFAULT_ITEM_ID))
            .andExpect(jsonPath("$.sellerApproved").value(DEFAULT_SELLER_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.buyerApproved").value(DEFAULT_BUYER_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.sellerId").value(DEFAULT_SELLER_ID))
            .andExpect(jsonPath("$.buyerId").value(DEFAULT_BUYER_ID));
    }

    @Test
    void getNonExistingBargain() throws Exception {
        // Get the bargain
        restBargainMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewBargain() throws Exception {
        // Initialize the database
        bargainRepository.save(bargain);

        int databaseSizeBeforeUpdate = bargainRepository.findAll().size();

        // Update the bargain
        Bargain updatedBargain = bargainRepository.findById(bargain.getId()).get();
        updatedBargain
            .bargainPrice(UPDATED_BARGAIN_PRICE)
            .itemId(UPDATED_ITEM_ID)
            .sellerApproved(UPDATED_SELLER_APPROVED)
            .buyerApproved(UPDATED_BUYER_APPROVED)
            .sellerId(UPDATED_SELLER_ID)
            .buyerId(UPDATED_BUYER_ID);

        restBargainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBargain.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBargain))
            )
            .andExpect(status().isOk());

        // Validate the Bargain in the database
        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeUpdate);
        Bargain testBargain = bargainList.get(bargainList.size() - 1);
        assertThat(testBargain.getBargainPrice()).isEqualTo(UPDATED_BARGAIN_PRICE);
        assertThat(testBargain.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testBargain.getSellerApproved()).isEqualTo(UPDATED_SELLER_APPROVED);
        assertThat(testBargain.getBuyerApproved()).isEqualTo(UPDATED_BUYER_APPROVED);
        assertThat(testBargain.getSellerId()).isEqualTo(UPDATED_SELLER_ID);
        assertThat(testBargain.getBuyerId()).isEqualTo(UPDATED_BUYER_ID);
    }

    @Test
    void putNonExistingBargain() throws Exception {
        int databaseSizeBeforeUpdate = bargainRepository.findAll().size();
        bargain.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBargainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bargain.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bargain))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bargain in the database
        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchBargain() throws Exception {
        int databaseSizeBeforeUpdate = bargainRepository.findAll().size();
        bargain.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBargainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bargain))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bargain in the database
        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamBargain() throws Exception {
        int databaseSizeBeforeUpdate = bargainRepository.findAll().size();
        bargain.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBargainMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bargain)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bargain in the database
        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateBargainWithPatch() throws Exception {
        // Initialize the database
        bargainRepository.save(bargain);

        int databaseSizeBeforeUpdate = bargainRepository.findAll().size();

        // Update the bargain using partial update
        Bargain partialUpdatedBargain = new Bargain();
        partialUpdatedBargain.setId(bargain.getId());

        partialUpdatedBargain.bargainPrice(UPDATED_BARGAIN_PRICE).itemId(UPDATED_ITEM_ID).sellerApproved(UPDATED_SELLER_APPROVED);

        restBargainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBargain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBargain))
            )
            .andExpect(status().isOk());

        // Validate the Bargain in the database
        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeUpdate);
        Bargain testBargain = bargainList.get(bargainList.size() - 1);
        assertThat(testBargain.getBargainPrice()).isEqualTo(UPDATED_BARGAIN_PRICE);
        assertThat(testBargain.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testBargain.getSellerApproved()).isEqualTo(UPDATED_SELLER_APPROVED);
        assertThat(testBargain.getBuyerApproved()).isEqualTo(DEFAULT_BUYER_APPROVED);
        assertThat(testBargain.getSellerId()).isEqualTo(DEFAULT_SELLER_ID);
        assertThat(testBargain.getBuyerId()).isEqualTo(DEFAULT_BUYER_ID);
    }

    @Test
    void fullUpdateBargainWithPatch() throws Exception {
        // Initialize the database
        bargainRepository.save(bargain);

        int databaseSizeBeforeUpdate = bargainRepository.findAll().size();

        // Update the bargain using partial update
        Bargain partialUpdatedBargain = new Bargain();
        partialUpdatedBargain.setId(bargain.getId());

        partialUpdatedBargain
            .bargainPrice(UPDATED_BARGAIN_PRICE)
            .itemId(UPDATED_ITEM_ID)
            .sellerApproved(UPDATED_SELLER_APPROVED)
            .buyerApproved(UPDATED_BUYER_APPROVED)
            .sellerId(UPDATED_SELLER_ID)
            .buyerId(UPDATED_BUYER_ID);

        restBargainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBargain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBargain))
            )
            .andExpect(status().isOk());

        // Validate the Bargain in the database
        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeUpdate);
        Bargain testBargain = bargainList.get(bargainList.size() - 1);
        assertThat(testBargain.getBargainPrice()).isEqualTo(UPDATED_BARGAIN_PRICE);
        assertThat(testBargain.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testBargain.getSellerApproved()).isEqualTo(UPDATED_SELLER_APPROVED);
        assertThat(testBargain.getBuyerApproved()).isEqualTo(UPDATED_BUYER_APPROVED);
        assertThat(testBargain.getSellerId()).isEqualTo(UPDATED_SELLER_ID);
        assertThat(testBargain.getBuyerId()).isEqualTo(UPDATED_BUYER_ID);
    }

    @Test
    void patchNonExistingBargain() throws Exception {
        int databaseSizeBeforeUpdate = bargainRepository.findAll().size();
        bargain.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBargainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bargain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bargain))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bargain in the database
        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchBargain() throws Exception {
        int databaseSizeBeforeUpdate = bargainRepository.findAll().size();
        bargain.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBargainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bargain))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bargain in the database
        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamBargain() throws Exception {
        int databaseSizeBeforeUpdate = bargainRepository.findAll().size();
        bargain.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBargainMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bargain)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bargain in the database
        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteBargain() throws Exception {
        // Initialize the database
        bargainRepository.save(bargain);

        int databaseSizeBeforeDelete = bargainRepository.findAll().size();

        // Delete the bargain
        restBargainMockMvc
            .perform(delete(ENTITY_API_URL_ID, bargain.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bargain> bargainList = bargainRepository.findAll();
        assertThat(bargainList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
