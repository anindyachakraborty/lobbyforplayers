package com.lobbyforplayers.service.impl;

import com.lobbyforplayers.domain.Payments;
import com.lobbyforplayers.repository.PaymentsRepository;
import com.lobbyforplayers.service.PaymentsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Payments}.
 */
@Service
public class PaymentsServiceImpl implements PaymentsService {

    private final Logger log = LoggerFactory.getLogger(PaymentsServiceImpl.class);

    private final PaymentsRepository paymentsRepository;

    public PaymentsServiceImpl(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    @Override
    public Payments save(Payments payments) {
        log.debug("Request to save Payments : {}", payments);
        return paymentsRepository.save(payments);
    }

    @Override
    public Optional<Payments> partialUpdate(Payments payments) {
        log.debug("Request to partially update Payments : {}", payments);

        return paymentsRepository
            .findById(payments.getId())
            .map(existingPayments -> {
                if (payments.getBuyerUserId() != null) {
                    existingPayments.setBuyerUserId(payments.getBuyerUserId());
                }
                if (payments.getSellerUserId() != null) {
                    existingPayments.setSellerUserId(payments.getSellerUserId());
                }
                if (payments.getSellerRecieved() != null) {
                    existingPayments.setSellerRecieved(payments.getSellerRecieved());
                }
                if (payments.getBuyerTransactionId() != null) {
                    existingPayments.setBuyerTransactionId(payments.getBuyerTransactionId());
                }
                if (payments.getSellerTransactionId() != null) {
                    existingPayments.setSellerTransactionId(payments.getSellerTransactionId());
                }
                if (payments.getOrderId() != null) {
                    existingPayments.setOrderId(payments.getOrderId());
                }
                if (payments.getPackageId() != null) {
                    existingPayments.setPackageId(payments.getPackageId());
                }
                if (payments.getAmount() != null) {
                    existingPayments.setAmount(payments.getAmount());
                }
                if (payments.getGame() != null) {
                    existingPayments.setGame(payments.getGame());
                }
                if (payments.getStatus() != null) {
                    existingPayments.setStatus(payments.getStatus());
                }

                return existingPayments;
            })
            .map(paymentsRepository::save);
    }

    @Override
    public List<Payments> findAll() {
        log.debug("Request to get all Payments");
        return paymentsRepository.findAll();
    }

    @Override
    public Optional<Payments> findOne(String id) {
        log.debug("Request to get Payments : {}", id);
        return paymentsRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Payments : {}", id);
        paymentsRepository.deleteById(id);
    }
}
