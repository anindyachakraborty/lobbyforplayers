package com.lobbyforplayers.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lobbyforplayers.IntegrationTest;
import com.lobbyforplayers.domain.Order;
import com.lobbyforplayers.repository.OrderRepository;
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
 * Integration tests for the {@link OrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderResourceIT {
    // private static final String DEFAULT_SELLER_NAME = "AAAAAAAAAA";
    // private static final String UPDATED_SELLER_NAME = "BBBBBBBBBB";

    // private static final String DEFAULT_BUYER_NAME = "AAAAAAAAAA";
    // private static final String UPDATED_BUYER_NAME = "BBBBBBBBBB";

    // private static final Double DEFAULT_PRICE_SETTLED = 1D;
    // private static final Double UPDATED_PRICE_SETTLED = 2D;

    // private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    // private static final String UPDATED_STATUS = "BBBBBBBBBB";

    // private static final Boolean DEFAULT_COMPLETED = false;
    // private static final Boolean UPDATED_COMPLETED = true;

    // private static final String ENTITY_API_URL = "/api/orders";
    // private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    // private static Random random = new Random();
    // private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    // @Autowired
    // private OrderRepository orderRepository;

    // @Autowired
    // private EntityManager em;

    // @Autowired
    // private MockMvc restOrderMockMvc;

    // private Order order;

    // /**
    //  * Create an entity for this test.
    //  *
    //  * This is a static method, as tests for other entities might also need it,
    //  * if they test an entity which requires the current entity.
    //  */
    // public static Order createEntity(EntityManager em) {
    //     Order order = new Order()
    //         .sellerName(DEFAULT_SELLER_NAME)
    //         .buyerName(DEFAULT_BUYER_NAME)
    //         .priceSettled(DEFAULT_PRICE_SETTLED)
    //         .status(DEFAULT_STATUS)
    //         .completed(DEFAULT_COMPLETED);
    //     return order;
    // }

    // /**
    //  * Create an updated entity for this test.
    //  *
    //  * This is a static method, as tests for other entities might also need it,
    //  * if they test an entity which requires the current entity.
    //  */
    // public static Order createUpdatedEntity(EntityManager em) {
    //     Order order = new Order()
    //         .sellerName(UPDATED_SELLER_NAME)
    //         .buyerName(UPDATED_BUYER_NAME)
    //         .priceSettled(UPDATED_PRICE_SETTLED)
    //         .status(UPDATED_STATUS)
    //         .completed(UPDATED_COMPLETED);
    //     return order;
    // }

    // @BeforeEach
    // public void initTest() {
    //     order = createEntity(em);
    // }

    // @Test
    // @Transactional
    // void createOrder() throws Exception {
    //     int databaseSizeBeforeCreate = orderRepository.findAll().size();
    //     // Create the Order
    //     restOrderMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(order)))
    //         .andExpect(status().isCreated());

    //     // Validate the Order in the database
    //     List<Order> orderList = orderRepository.findAll();
    //     assertThat(orderList).hasSize(databaseSizeBeforeCreate + 1);
    //     Order testOrder = orderList.get(orderList.size() - 1);
    //     assertThat(testOrder.getSellerName()).isEqualTo(DEFAULT_SELLER_NAME);
    //     assertThat(testOrder.getBuyerName()).isEqualTo(DEFAULT_BUYER_NAME);
    //     assertThat(testOrder.getPriceSettled()).isEqualTo(DEFAULT_PRICE_SETTLED);
    //     assertThat(testOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
    //     assertThat(testOrder.getCompleted()).isEqualTo(DEFAULT_COMPLETED);
    // }

    // @Test
    // @Transactional
    // void createOrderWithExistingId() throws Exception {
    //     // Create the Order with an existing ID
    //     order.setId(1L);

    //     int databaseSizeBeforeCreate = orderRepository.findAll().size();

    //     // An entity with an existing ID cannot be created, so this API call must fail
    //     restOrderMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(order)))
    //         .andExpect(status().isBadRequest());

    //     // Validate the Order in the database
    //     List<Order> orderList = orderRepository.findAll();
    //     assertThat(orderList).hasSize(databaseSizeBeforeCreate);
    // }

    // @Test
    // @Transactional
    // void checkSellerNameIsRequired() throws Exception {
    //     int databaseSizeBeforeTest = orderRepository.findAll().size();
    //     // set the field null
    //     order.setSellerName(null);

    //     // Create the Order, which fails.

    //     restOrderMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(order)))
    //         .andExpect(status().isBadRequest());

    //     List<Order> orderList = orderRepository.findAll();
    //     assertThat(orderList).hasSize(databaseSizeBeforeTest);
    // }

    // @Test
    // @Transactional
    // void checkBuyerNameIsRequired() throws Exception {
    //     int databaseSizeBeforeTest = orderRepository.findAll().size();
    //     // set the field null
    //     order.setBuyerName(null);

    //     // Create the Order, which fails.

    //     restOrderMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(order)))
    //         .andExpect(status().isBadRequest());

    //     List<Order> orderList = orderRepository.findAll();
    //     assertThat(orderList).hasSize(databaseSizeBeforeTest);
    // }

    // @Test
    // @Transactional
    // void checkPriceSettledIsRequired() throws Exception {
    //     int databaseSizeBeforeTest = orderRepository.findAll().size();
    //     // set the field null
    //     order.setPriceSettled(null);

    //     // Create the Order, which fails.

    //     restOrderMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(order)))
    //         .andExpect(status().isBadRequest());

    //     List<Order> orderList = orderRepository.findAll();
    //     assertThat(orderList).hasSize(databaseSizeBeforeTest);
    // }

    // @Test
    // @Transactional
    // void checkStatusIsRequired() throws Exception {
    //     int databaseSizeBeforeTest = orderRepository.findAll().size();
    //     // set the field null
    //     order.setStatus(null);

    //     // Create the Order, which fails.

    //     restOrderMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(order)))
    //         .andExpect(status().isBadRequest());

    //     List<Order> orderList = orderRepository.findAll();
    //     assertThat(orderList).hasSize(databaseSizeBeforeTest);
    // }

    // @Test
    // @Transactional
    // void checkCompletedIsRequired() throws Exception {
    //     int databaseSizeBeforeTest = orderRepository.findAll().size();
    //     // set the field null
    //     order.setCompleted(null);

    //     // Create the Order, which fails.

    //     restOrderMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(order)))
    //         .andExpect(status().isBadRequest());

    //     List<Order> orderList = orderRepository.findAll();
    //     assertThat(orderList).hasSize(databaseSizeBeforeTest);
    // }

    // @Test
    // @Transactional
    // void getAllOrders() throws Exception {
    //     // Initialize the database
    //     orderRepository.saveAndFlush(order);

    //     // Get all the orderList
    //     restOrderMockMvc
    //         .perform(get(ENTITY_API_URL + "?sort=id,desc"))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId().intValue())))
    //         .andExpect(jsonPath("$.[*].sellerName").value(hasItem(DEFAULT_SELLER_NAME)))
    //         .andExpect(jsonPath("$.[*].buyerName").value(hasItem(DEFAULT_BUYER_NAME)))
    //         .andExpect(jsonPath("$.[*].priceSettled").value(hasItem(DEFAULT_PRICE_SETTLED.doubleValue())))
    //         .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
    //         .andExpect(jsonPath("$.[*].completed").value(hasItem(DEFAULT_COMPLETED.booleanValue())));
    // }

    // @Test
    // @Transactional
    // void getOrder() throws Exception {
    //     // Initialize the database
    //     orderRepository.saveAndFlush(order);

    //     // Get the order
    //     restOrderMockMvc
    //         .perform(get(ENTITY_API_URL_ID, order.getId()))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andExpect(jsonPath("$.id").value(order.getId().intValue()))
    //         .andExpect(jsonPath("$.sellerName").value(DEFAULT_SELLER_NAME))
    //         .andExpect(jsonPath("$.buyerName").value(DEFAULT_BUYER_NAME))
    //         .andExpect(jsonPath("$.priceSettled").value(DEFAULT_PRICE_SETTLED.doubleValue()))
    //         .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
    //         .andExpect(jsonPath("$.completed").value(DEFAULT_COMPLETED.booleanValue()));
    // }

    // @Test
    // @Transactional
    // void getNonExistingOrder() throws Exception {
    //     // Get the order
    //     restOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    // }

    // @Test
    // @Transactional
    // void putNewOrder() throws Exception {
    //     // Initialize the database
    //     orderRepository.saveAndFlush(order);

    //     int databaseSizeBeforeUpdate = orderRepository.findAll().size();

    //     // Update the order
    //     Order updatedOrder = orderRepository.findById(order.getId()).get();
    //     // Disconnect from session so that the updates on updatedOrder are not directly saved in db
    //     em.detach(updatedOrder);
    //     updatedOrder
    //         .sellerName(UPDATED_SELLER_NAME)
    //         .buyerName(UPDATED_BUYER_NAME)
    //         .priceSettled(UPDATED_PRICE_SETTLED)
    //         .status(UPDATED_STATUS)
    //         .completed(UPDATED_COMPLETED);

    //     restOrderMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, updatedOrder.getId())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(TestUtil.convertObjectToJsonBytes(updatedOrder))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the Order in the database
    //     List<Order> orderList = orderRepository.findAll();
    //     assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    //     Order testOrder = orderList.get(orderList.size() - 1);
    //     assertThat(testOrder.getSellerName()).isEqualTo(UPDATED_SELLER_NAME);
    //     assertThat(testOrder.getBuyerName()).isEqualTo(UPDATED_BUYER_NAME);
    //     assertThat(testOrder.getPriceSettled()).isEqualTo(UPDATED_PRICE_SETTLED);
    //     assertThat(testOrder.getStatus()).isEqualTo(UPDATED_STATUS);
    //     assertThat(testOrder.getCompleted()).isEqualTo(UPDATED_COMPLETED);
    // }

    // @Test
    // @Transactional
    // void putNonExistingOrder() throws Exception {
    //     int databaseSizeBeforeUpdate = orderRepository.findAll().size();
    //     order.setId(count.incrementAndGet());

    //     // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //     restOrderMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, order.getId())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(TestUtil.convertObjectToJsonBytes(order))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Order in the database
    //     List<Order> orderList = orderRepository.findAll();
    //     assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void putWithIdMismatchOrder() throws Exception {
    //     int databaseSizeBeforeUpdate = orderRepository.findAll().size();
    //     order.setId(count.incrementAndGet());

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restOrderMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, count.incrementAndGet())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(TestUtil.convertObjectToJsonBytes(order))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Order in the database
    //     List<Order> orderList = orderRepository.findAll();
    //     assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void putWithMissingIdPathParamOrder() throws Exception {
    //     int databaseSizeBeforeUpdate = orderRepository.findAll().size();
    //     order.setId(count.incrementAndGet());

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restOrderMockMvc
    //         .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(order)))
    //         .andExpect(status().isMethodNotAllowed());

    //     // Validate the Order in the database
    //     List<Order> orderList = orderRepository.findAll();
    //     assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void partialUpdateOrderWithPatch() throws Exception {
    //     // Initialize the database
    //     orderRepository.saveAndFlush(order);

    //     int databaseSizeBeforeUpdate = orderRepository.findAll().size();

    //     // Update the order using partial update
    //     Order partialUpdatedOrder = new Order();
    //     partialUpdatedOrder.setId(order.getId());

    //     partialUpdatedOrder
    //         .sellerName(UPDATED_SELLER_NAME)
    //         .priceSettled(UPDATED_PRICE_SETTLED)
    //         .status(UPDATED_STATUS)
    //         .completed(UPDATED_COMPLETED);

    //     restOrderMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, partialUpdatedOrder.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrder))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the Order in the database
    //     List<Order> orderList = orderRepository.findAll();
    //     assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    //     Order testOrder = orderList.get(orderList.size() - 1);
    //     assertThat(testOrder.getSellerName()).isEqualTo(UPDATED_SELLER_NAME);
    //     assertThat(testOrder.getBuyerName()).isEqualTo(DEFAULT_BUYER_NAME);
    //     assertThat(testOrder.getPriceSettled()).isEqualTo(UPDATED_PRICE_SETTLED);
    //     assertThat(testOrder.getStatus()).isEqualTo(UPDATED_STATUS);
    //     assertThat(testOrder.getCompleted()).isEqualTo(UPDATED_COMPLETED);
    // }

    // @Test
    // @Transactional
    // void fullUpdateOrderWithPatch() throws Exception {
    //     // Initialize the database
    //     orderRepository.saveAndFlush(order);

    //     int databaseSizeBeforeUpdate = orderRepository.findAll().size();

    //     // Update the order using partial update
    //     Order partialUpdatedOrder = new Order();
    //     partialUpdatedOrder.setId(order.getId());

    //     partialUpdatedOrder
    //         .sellerName(UPDATED_SELLER_NAME)
    //         .buyerName(UPDATED_BUYER_NAME)
    //         .priceSettled(UPDATED_PRICE_SETTLED)
    //         .status(UPDATED_STATUS)
    //         .completed(UPDATED_COMPLETED);

    //     restOrderMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, partialUpdatedOrder.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrder))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the Order in the database
    //     List<Order> orderList = orderRepository.findAll();
    //     assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    //     Order testOrder = orderList.get(orderList.size() - 1);
    //     assertThat(testOrder.getSellerName()).isEqualTo(UPDATED_SELLER_NAME);
    //     assertThat(testOrder.getBuyerName()).isEqualTo(UPDATED_BUYER_NAME);
    //     assertThat(testOrder.getPriceSettled()).isEqualTo(UPDATED_PRICE_SETTLED);
    //     assertThat(testOrder.getStatus()).isEqualTo(UPDATED_STATUS);
    //     assertThat(testOrder.getCompleted()).isEqualTo(UPDATED_COMPLETED);
    // }

    // @Test
    // @Transactional
    // void patchNonExistingOrder() throws Exception {
    //     int databaseSizeBeforeUpdate = orderRepository.findAll().size();
    //     order.setId(count.incrementAndGet());

    //     // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //     restOrderMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, order.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(TestUtil.convertObjectToJsonBytes(order))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Order in the database
    //     List<Order> orderList = orderRepository.findAll();
    //     assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void patchWithIdMismatchOrder() throws Exception {
    //     int databaseSizeBeforeUpdate = orderRepository.findAll().size();
    //     order.setId(count.incrementAndGet());

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restOrderMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, count.incrementAndGet())
    //                 .contentType("application/merge-patch+json")
    //                 .content(TestUtil.convertObjectToJsonBytes(order))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Order in the database
    //     List<Order> orderList = orderRepository.findAll();
    //     assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void patchWithMissingIdPathParamOrder() throws Exception {
    //     int databaseSizeBeforeUpdate = orderRepository.findAll().size();
    //     order.setId(count.incrementAndGet());

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restOrderMockMvc
    //         .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(order)))
    //         .andExpect(status().isMethodNotAllowed());

    //     // Validate the Order in the database
    //     List<Order> orderList = orderRepository.findAll();
    //     assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void deleteOrder() throws Exception {
    //     // Initialize the database
    //     orderRepository.saveAndFlush(order);

    //     int databaseSizeBeforeDelete = orderRepository.findAll().size();

    //     // Delete the order
    //     restOrderMockMvc
    //         .perform(delete(ENTITY_API_URL_ID, order.getId()).accept(MediaType.APPLICATION_JSON))
    //         .andExpect(status().isNoContent());

    //     // Validate the database contains one less item
    //     List<Order> orderList = orderRepository.findAll();
    //     assertThat(orderList).hasSize(databaseSizeBeforeDelete - 1);
    // }
}
