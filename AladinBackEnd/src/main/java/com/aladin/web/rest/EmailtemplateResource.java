package com.aladin.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.aladin.domain.Emailtemplate;
import com.aladin.repository.EmailtemplateRepository;
import com.aladin.service.EmailtemplateService;
import com.aladin.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.aladin.domain.Emailtemplate}.
 */
@RestController
@RequestMapping("/api")
public class EmailtemplateResource {

    private final Logger log = LoggerFactory.getLogger(EmailtemplateResource.class);

    private static final String ENTITY_NAME = "emailtemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailtemplateService emailtemplateService;

    private final EmailtemplateRepository emailtemplateRepository;

    public EmailtemplateResource(EmailtemplateService emailtemplateService, EmailtemplateRepository emailtemplateRepository) {
        this.emailtemplateService = emailtemplateService;
        this.emailtemplateRepository = emailtemplateRepository;
    }

    /**
     * {@code POST  /emailtemplates} : Create a new emailtemplate.
     *
     * @param emailtemplate the emailtemplate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emailtemplate, or with status {@code 400 (Bad Request)} if the emailtemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emailtemplates")
    public ResponseEntity<Emailtemplate> createEmailtemplate(@Valid @RequestBody Emailtemplate emailtemplate) throws URISyntaxException {
        log.debug("REST request to save Emailtemplate : {}", emailtemplate);
        if (emailtemplate.getId() != null) {
            throw new BadRequestAlertException("A new emailtemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Emailtemplate result = emailtemplateService.save(emailtemplate);
        return ResponseEntity
            .created(new URI("/api/emailtemplates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emailtemplates/:id} : Updates an existing emailtemplate.
     *
     * @param id the id of the emailtemplate to save.
     * @param emailtemplate the emailtemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailtemplate,
     * or with status {@code 400 (Bad Request)} if the emailtemplate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emailtemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emailtemplates/{id}")
    public ResponseEntity<Emailtemplate> updateEmailtemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Emailtemplate emailtemplate
    ) throws URISyntaxException {
        log.debug("REST request to update Emailtemplate : {}, {}", id, emailtemplate);
        if (emailtemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emailtemplate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emailtemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Emailtemplate result = emailtemplateService.save(emailtemplate);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emailtemplate.getId().toString()))
            .body(result);
    }
    /**
     * {@code GET  /emailtemplates} : get all the emailtemplates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emailtemplates in body.
     */
    @GetMapping("/emailtemplates")
    public ResponseEntity<List<Emailtemplate>> getAllEmailtemplates(Pageable pageable) {
        log.debug("REST request to get a page of Emailtemplates");
        Page<Emailtemplate> page = emailtemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emailtemplates/:id} : get the "id" emailtemplate.
     *
     * @param id the id of the emailtemplate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emailtemplate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emailtemplates/{id}")
    public ResponseEntity<Emailtemplate> getEmailtemplate(@PathVariable Long id) {
        log.debug("REST request to get Emailtemplate : {}", id);
        Optional<Emailtemplate> emailtemplate = emailtemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailtemplate);
    }

    /**
     * {@code SEARCH  /_search/emailtemplates?query=:query} : search for the emailtemplate corresponding
     * to the query.
     *
     * @param query the query of the emailtemplate search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/emailtemplates")
    public ResponseEntity<List<Emailtemplate>> searchEmailtemplates(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Emailtemplates for query {}", query);
        Page<Emailtemplate> page = emailtemplateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
