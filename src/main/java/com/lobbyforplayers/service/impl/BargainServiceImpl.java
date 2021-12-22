package com.lobbyforplayers.service.impl;

import com.lobbyforplayers.domain.Bargain;
import com.lobbyforplayers.repository.BargainRepository;
import com.lobbyforplayers.service.BargainService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Bargain}.
 */
@Service
public class BargainServiceImpl implements BargainService {

    private final Logger log = LoggerFactory.getLogger(BargainServiceImpl.class);

    private final BargainRepository bargainRepository;

    public BargainServiceImpl(BargainRepository bargainRepository) {
        this.bargainRepository = bargainRepository;
    }

    @Override
    public Bargain save(Bargain bargain) {
        log.debug("Request to save Bargain : {}", bargain);
        return bargainRepository.save(bargain);
    }

    @Override
    public Optional<Bargain> partialUpdate(Bargain bargain) {
        log.debug("Request to partially update Bargain : {}", bargain);

        return bargainRepository
            .findById(bargain.getId())
            .map(existingBargain -> {
                if (bargain.getBargainPrice() != null) {
                    existingBargain.setBargainPrice(bargain.getBargainPrice());
                }
                if (bargain.getItemId() != null) {
                    existingBargain.setItemId(bargain.getItemId());
                }
                if (bargain.getSellerApproved() != null) {
                    existingBargain.setSellerApproved(bargain.getSellerApproved());
                }
                if (bargain.getBuyerApproved() != null) {
                    existingBargain.setBuyerApproved(bargain.getBuyerApproved());
                }
                if (bargain.getSellerId() != null) {
                    existingBargain.setSellerId(bargain.getSellerId());
                }
                if (bargain.getBuyerId() != null) {
                    existingBargain.setBuyerId(bargain.getBuyerId());
                }

                return existingBargain;
            })
            .map(bargainRepository::save);
    }

    @Override
    public List<Bargain> findAll() {
        log.debug("Request to get all Bargains");
        return bargainRepository.findAll();
    }

    @Override
    public Optional<Bargain> findOne(String id) {
        log.debug("Request to get Bargain : {}", id);
        return bargainRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Bargain : {}", id);
        bargainRepository.deleteById(id);
    }
}
