package com.lobbyforplayers.web.rest;

import com.lobbyforplayers.domain.Bargain;
import com.lobbyforplayers.repository.BargainRepository;
import com.lobbyforplayers.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.lobbyforplayers.domain.Bargain}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BargainResource {

    private final Logger log = LoggerFactory.getLogger(BargainResource.class);

    private static final String ENTITY_NAME = "bargain";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BargainRepository bargainRepository;

    public BargainResource(BargainRepository bargainRepository) {
        this.bargainRepository = bargainRepository;
    }

    /**
     * {@code POST  /bargains} : Create a new bargain.
     *
     * @param bargain the bargain to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bargain, or with status {@code 400 (Bad Request)} if the bargain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bargains")
    public ResponseEntity<Bargain> createBargain(@Valid @RequestBody Bargain bargain) throws URISyntaxException {
        log.debug("REST request to save Bargain : {}", bargain);
        if (bargain.getId() != null) {
            throw new BadRequestAlertException("A new bargain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bargain result = bargainRepository.save(bargain);
        return ResponseEntity
            .created(new URI("/api/bargains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bargains/:id} : Updates an existing bargain.
     *
     * @param id the id of the bargain to save.
     * @param bargain the bargain to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bargain,
     * or with status {@code 400 (Bad Request)} if the bargain is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bargain couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bargains/{id}")
    public ResponseEntity<Bargain> updateBargain(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Bargain bargain
    ) throws URISyntaxException {
        log.debug("REST request to update Bargain : {}, {}", id, bargain);
        if (bargain.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bargain.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bargainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Bargain result = bargainRepository.save(bargain);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bargain.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bargains/:id} : Partial updates given fields of an existing bargain, field will ignore if it is null
     *
     * @param id the id of the bargain to save.
     * @param bargain the bargain to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bargain,
     * or with status {@code 400 (Bad Request)} if the bargain is not valid,
     * or with status {@code 404 (Not Found)} if the bargain is not found,
     * or with status {@code 500 (Internal Server Error)} if the bargain couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bargains/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bargain> partialUpdateBargain(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Bargain bargain
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bargain partially : {}, {}", id, bargain);
        if (bargain.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bargain.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bargainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bargain> result = bargainRepository
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

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bargain.getId().toString())
        );
    }

    /**
     * {@code GET  /bargains} : get all the bargains.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bargains in body.
     */
    @GetMapping("/bargains")
    public List<Bargain> getAllBargains() {
        log.debug("REST request to get all Bargains");
        return bargainRepository.findAll();
    }

    /**
     * {@code GET  /bargains/:id} : get the "id" bargain.
     *
     * @param id the id of the bargain to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bargain, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bargains/{id}")
    public ResponseEntity<Bargain> getBargain(@PathVariable String id) {
        log.debug("REST request to get Bargain : {}", id);
        Optional<Bargain> bargain = bargainRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bargain);
    }

    /**
     * {@code DELETE  /bargains/:id} : delete the "id" bargain.
     *
     * @param id the id of the bargain to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bargains/{id}")
    public ResponseEntity<Void> deleteBargain(@PathVariable String id) {
        log.debug("REST request to delete Bargain : {}", id);
        bargainRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
