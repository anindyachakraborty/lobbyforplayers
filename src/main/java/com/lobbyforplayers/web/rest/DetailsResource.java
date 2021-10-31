package com.lobbyforplayers.web.rest;

import com.lobbyforplayers.domain.Details;
import com.lobbyforplayers.repository.DetailsRepository;
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
 * REST controller for managing {@link com.lobbyforplayers.domain.Details}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DetailsResource {

    private final Logger log = LoggerFactory.getLogger(DetailsResource.class);

    private static final String ENTITY_NAME = "details";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailsRepository detailsRepository;

    public DetailsResource(DetailsRepository detailsRepository) {
        this.detailsRepository = detailsRepository;
    }

    /**
     * {@code POST  /details} : Create a new details.
     *
     * @param details the details to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new details, or with status {@code 400 (Bad Request)} if the details has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/details")
    public ResponseEntity<Details> createDetails(@Valid @RequestBody Details details) throws URISyntaxException {
        log.debug("REST request to save Details : {}", details);
        if (details.getId() != null) {
            throw new BadRequestAlertException("A new details cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Details result = detailsRepository.save(details);
        return ResponseEntity
            .created(new URI("/api/details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /details/:id} : Updates an existing details.
     *
     * @param id the id of the details to save.
     * @param details the details to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated details,
     * or with status {@code 400 (Bad Request)} if the details is not valid,
     * or with status {@code 500 (Internal Server Error)} if the details couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/details/{id}")
    public ResponseEntity<Details> updateDetails(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Details details
    ) throws URISyntaxException {
        log.debug("REST request to update Details : {}, {}", id, details);
        if (details.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, details.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Details result = detailsRepository.save(details);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, details.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /details/:id} : Partial updates given fields of an existing details, field will ignore if it is null
     *
     * @param id the id of the details to save.
     * @param details the details to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated details,
     * or with status {@code 400 (Bad Request)} if the details is not valid,
     * or with status {@code 404 (Not Found)} if the details is not found,
     * or with status {@code 500 (Internal Server Error)} if the details couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Details> partialUpdateDetails(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Details details
    ) throws URISyntaxException {
        log.debug("REST request to partial update Details partially : {}, {}", id, details);
        if (details.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, details.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Details> result = detailsRepository
            .findById(details.getId())
            .map(existingDetails -> {
                if (details.getLoginName() != null) {
                    existingDetails.setLoginName(details.getLoginName());
                }
                if (details.getPassword() != null) {
                    existingDetails.setPassword(details.getPassword());
                }
                if (details.getLastName() != null) {
                    existingDetails.setLastName(details.getLastName());
                }
                if (details.getFirstName() != null) {
                    existingDetails.setFirstName(details.getFirstName());
                }
                if (details.getSecurtiyQuestion() != null) {
                    existingDetails.setSecurtiyQuestion(details.getSecurtiyQuestion());
                }
                if (details.getSecurityAnswer() != null) {
                    existingDetails.setSecurityAnswer(details.getSecurityAnswer());
                }
                if (details.getParentalPassword() != null) {
                    existingDetails.setParentalPassword(details.getParentalPassword());
                }
                if (details.getFirstCdKey() != null) {
                    existingDetails.setFirstCdKey(details.getFirstCdKey());
                }
                if (details.getOtherInformation() != null) {
                    existingDetails.setOtherInformation(details.getOtherInformation());
                }
                if (details.getEnteredDate() != null) {
                    existingDetails.setEnteredDate(details.getEnteredDate());
                }
                if (details.getOrderDate() != null) {
                    existingDetails.setOrderDate(details.getOrderDate());
                }

                return existingDetails;
            })
            .map(detailsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, details.getId().toString())
        );
    }

    /**
     * {@code GET  /details} : get all the details.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of details in body.
     */
    @GetMapping("/details")
    public List<Details> getAllDetails() {
        log.debug("REST request to get all Details");
        return detailsRepository.findAll();
    }

    /**
     * {@code GET  /details/:id} : get the "id" details.
     *
     * @param id the id of the details to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the details, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/details/{id}")
    public ResponseEntity<Details> getDetails(@PathVariable String id) {
        log.debug("REST request to get Details : {}", id);
        Optional<Details> details = detailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(details);
    }

    /**
     * {@code DELETE  /details/:id} : delete the "id" details.
     *
     * @param id the id of the details to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/details/{id}")
    public ResponseEntity<Void> deleteDetails(@PathVariable String id) {
        log.debug("REST request to delete Details : {}", id);
        detailsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
