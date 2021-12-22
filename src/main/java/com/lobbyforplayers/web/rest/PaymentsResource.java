package com.lobbyforplayers.web.rest;

import com.lobbyforplayers.domain.Payments;
import com.lobbyforplayers.repository.PaymentsRepository;
import com.lobbyforplayers.service.PaymentsService;
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
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.lobbyforplayers.domain.Payments}.
 */
@RestController
@RequestMapping("/api")
public class PaymentsResource {

    private final Logger log = LoggerFactory.getLogger(PaymentsResource.class);

    private static final String ENTITY_NAME = "payments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentsService paymentsService;

    private final PaymentsRepository paymentsRepository;

    public PaymentsResource(PaymentsService paymentsService, PaymentsRepository paymentsRepository) {
        this.paymentsService = paymentsService;
        this.paymentsRepository = paymentsRepository;
    }

    /**
     * {@code POST  /payments} : Create a new payments.
     *
     * @param payments the payments to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payments, or with status {@code 400 (Bad Request)} if the payments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payments")
    public ResponseEntity<Payments> createPayments(@Valid @RequestBody Payments payments) throws URISyntaxException {
        log.debug("REST request to save Payments : {}", payments);
        if (payments.getId() != null) {
            throw new BadRequestAlertException("A new payments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Payments result = paymentsService.save(payments);
        return ResponseEntity
            .created(new URI("/api/payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /payments/:id} : Updates an existing payments.
     *
     * @param id the id of the payments to save.
     * @param payments the payments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payments,
     * or with status {@code 400 (Bad Request)} if the payments is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payments/{id}")
    public ResponseEntity<Payments> updatePayments(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Payments payments
    ) throws URISyntaxException {
        log.debug("REST request to update Payments : {}, {}", id, payments);
        if (payments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payments.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Payments result = paymentsService.save(payments);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payments.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /payments/:id} : Partial updates given fields of an existing payments, field will ignore if it is null
     *
     * @param id the id of the payments to save.
     * @param payments the payments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payments,
     * or with status {@code 400 (Bad Request)} if the payments is not valid,
     * or with status {@code 404 (Not Found)} if the payments is not found,
     * or with status {@code 500 (Internal Server Error)} if the payments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Payments> partialUpdatePayments(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Payments payments
    ) throws URISyntaxException {
        log.debug("REST request to partial update Payments partially : {}, {}", id, payments);
        if (payments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payments.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Payments> result = paymentsService.partialUpdate(payments);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payments.getId())
        );
    }

    /**
     * {@code GET  /payments} : get all the payments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payments in body.
     */
    @GetMapping("/payments")
    public List<Payments> getAllPayments() {
        log.debug("REST request to get all Payments");
        return paymentsService.findAll();
    }

    /**
     * {@code GET  /payments/:id} : get the "id" payments.
     *
     * @param id the id of the payments to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payments, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payments/{id}")
    public ResponseEntity<Payments> getPayments(@PathVariable String id) {
        log.debug("REST request to get Payments : {}", id);
        Optional<Payments> payments = paymentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(payments);
    }

    /**
     * {@code DELETE  /payments/:id} : delete the "id" payments.
     *
     * @param id the id of the payments to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Void> deletePayments(@PathVariable String id) {
        log.debug("REST request to delete Payments : {}", id);
        paymentsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
