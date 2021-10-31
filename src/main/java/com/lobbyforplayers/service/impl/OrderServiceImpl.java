package com.lobbyforplayers.service.impl;

import com.lobbyforplayers.domain.Order;
import com.lobbyforplayers.repository.OrderRepository;
import com.lobbyforplayers.service.OrderService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Order}.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        log.debug("Request to save Order : {}", order);
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> partialUpdate(Order order) {
        log.debug("Request to partially update Order : {}", order);

        return orderRepository
            .findById(order.getId())
            .map(existingOrder -> {
                if (order.getSellerName() != null) {
                    existingOrder.setSellerName(order.getSellerName());
                }
                if (order.getBuyerName() != null) {
                    existingOrder.setBuyerName(order.getBuyerName());
                }
                if (order.getPriceSettled() != null) {
                    existingOrder.setPriceSettled(order.getPriceSettled());
                }
                if (order.getStatus() != null) {
                    existingOrder.setStatus(order.getStatus());
                }
                if (order.getCompleted() != null) {
                    existingOrder.setCompleted(order.getCompleted());
                }

                return existingOrder;
            })
            .map(orderRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        log.debug("Request to get all Orders");
        return orderRepository.findAll();
    }

    /**
     *  Get all the orders where Item is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Order> findAllWhereItemIsNull() {
        log.debug("Request to get all orders where Item is null");
        return StreamSupport
            .stream(orderRepository.findAll().spliterator(), false)
            .filter(order -> order.getItem() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findOne(String id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }
}
