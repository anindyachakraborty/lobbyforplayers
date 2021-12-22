package com.lobbyforplayers.web.rest;

import com.lobbyforplayers.domain.Questionary;
import com.lobbyforplayers.repository.QuestionaryRepository;
import com.lobbyforplayers.service.QuestionaryService;
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
 * REST controller for managing {@link com.lobbyforplayers.domain.Questionary}.
 */
@RestController
@RequestMapping("/api")
public class QuestionaryResource {

    private final Logger log = LoggerFactory.getLogger(QuestionaryResource.class);

    private static final String ENTITY_NAME = "questionary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionaryService questionaryService;

    private final QuestionaryRepository questionaryRepository;

    public QuestionaryResource(QuestionaryService questionaryService, QuestionaryRepository questionaryRepository) {
        this.questionaryService = questionaryService;
        this.questionaryRepository = questionaryRepository;
    }

    /**
     * {@code POST  /questionaries} : Create a new questionary.
     *
     * @param questionary the questionary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionary, or with status {@code 400 (Bad Request)} if the questionary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/questionaries")
    public ResponseEntity<Questionary> createQuestionary(@Valid @RequestBody Questionary questionary) throws URISyntaxException {
        log.debug("REST request to save Questionary : {}", questionary);
        if (questionary.getId() != null) {
            throw new BadRequestAlertException("A new questionary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Questionary result = questionaryService.save(questionary);
        return ResponseEntity
            .created(new URI("/api/questionaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /questionaries/:id} : Updates an existing questionary.
     *
     * @param id the id of the questionary to save.
     * @param questionary the questionary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionary,
     * or with status {@code 400 (Bad Request)} if the questionary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/questionaries/{id}")
    public ResponseEntity<Questionary> updateQuestionary(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Questionary questionary
    ) throws URISyntaxException {
        log.debug("REST request to update Questionary : {}, {}", id, questionary);
        if (questionary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, questionary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!questionaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Questionary result = questionaryService.save(questionary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, questionary.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /questionaries/:id} : Partial updates given fields of an existing questionary, field will ignore if it is null
     *
     * @param id the id of the questionary to save.
     * @param questionary the questionary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionary,
     * or with status {@code 400 (Bad Request)} if the questionary is not valid,
     * or with status {@code 404 (Not Found)} if the questionary is not found,
     * or with status {@code 500 (Internal Server Error)} if the questionary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/questionaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Questionary> partialUpdateQuestionary(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Questionary questionary
    ) throws URISyntaxException {
        log.debug("REST request to partial update Questionary partially : {}, {}", id, questionary);
        if (questionary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, questionary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!questionaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Questionary> result = questionaryService.partialUpdate(questionary);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, questionary.getId())
        );
    }

    /**
     * {@code GET  /questionaries} : get all the questionaries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionaries in body.
     */
    @GetMapping("/questionaries")
    public List<Questionary> getAllQuestionaries() {
        log.debug("REST request to get all Questionaries");
        return questionaryService.findAll();
    }

    /**
     * {@code GET  /questionaries/:id} : get the "id" questionary.
     *
     * @param id the id of the questionary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/questionaries/{id}")
    public ResponseEntity<Questionary> getQuestionary(@PathVariable String id) {
        log.debug("REST request to get Questionary : {}", id);
        Optional<Questionary> questionary = questionaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionary);
    }

    /**
     * {@code DELETE  /questionaries/:id} : delete the "id" questionary.
     *
     * @param id the id of the questionary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/questionaries/{id}")
    public ResponseEntity<Void> deleteQuestionary(@PathVariable String id) {
        log.debug("REST request to delete Questionary : {}", id);
        questionaryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
