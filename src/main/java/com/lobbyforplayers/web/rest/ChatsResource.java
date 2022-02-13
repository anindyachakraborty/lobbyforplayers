package com.lobbyforplayers.web.rest;

import com.lobbyforplayers.domain.Chats;
import com.lobbyforplayers.repository.ChatsRepository;
import com.lobbyforplayers.service.ChatsService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.lobbyforplayers.domain.Chats}.
 */
@RestController
@RequestMapping("/api")
public class ChatsResource {

    private final Logger log = LoggerFactory.getLogger(ChatsResource.class);

    private static final String ENTITY_NAME = "chats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChatsService chatsService;

    private final ChatsRepository chatsRepository;

    public ChatsResource(ChatsService chatsService, ChatsRepository chatsRepository) {
        this.chatsService = chatsService;
        this.chatsRepository = chatsRepository;
    }

    /**
     * {@code POST  /chats} : Create a new chats.
     *
     * @param chats the chats to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chats, or with status {@code 400 (Bad Request)} if the chats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chats")
    public ResponseEntity<Chats> createChats(@Valid @RequestBody Chats chats) throws URISyntaxException {
        log.debug("REST request to save Chats : {}", chats);
        if (chats.getId() != null) {
            throw new BadRequestAlertException("A new chats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Chats result = chatsService.save(chats);
        return ResponseEntity
            .created(new URI("/api/chats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /chats/:id} : Updates an existing chats.
     *
     * @param id the id of the chats to save.
     * @param chats the chats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chats,
     * or with status {@code 400 (Bad Request)} if the chats is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chats/{id}")
    public ResponseEntity<Chats> updateChats(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Chats chats
    ) throws URISyntaxException {
        log.debug("REST request to update Chats : {}, {}", id, chats);
        if (chats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Chats result = chatsService.save(chats);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chats.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /chats/:id} : Partial updates given fields of an existing chats, field will ignore if it is null
     *
     * @param id the id of the chats to save.
     * @param chats the chats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chats,
     * or with status {@code 400 (Bad Request)} if the chats is not valid,
     * or with status {@code 404 (Not Found)} if the chats is not found,
     * or with status {@code 500 (Internal Server Error)} if the chats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/chats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Chats> partialUpdateChats(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Chats chats
    ) throws URISyntaxException {
        log.debug("REST request to partial update Chats partially : {}, {}", id, chats);
        if (chats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Chats> result = chatsService.partialUpdate(chats);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chats.getId()));
    }

    /**
     * {@code GET  /chats} : get all the chats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chats in body.
     */
    @GetMapping("/chats")
    public ResponseEntity<List<Chats>> getAllChats(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Chats");
        Page<Chats> page = chatsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chats/:id} : get the "id" chats.
     *
     * @param id the id of the chats to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chats, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chats/{id}")
    public ResponseEntity<Chats> getChats(@PathVariable String id) {
        log.debug("REST request to get Chats : {}", id);
        Optional<Chats> chats = chatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chats);
    }

    /**
     * {@code DELETE  /chats/:id} : delete the "id" chats.
     *
     * @param id the id of the chats to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chats/{id}")
    public ResponseEntity<Void> deleteChats(@PathVariable String id) {
        log.debug("REST request to delete Chats : {}", id);
        chatsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
