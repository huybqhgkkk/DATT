package com.aladin.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.aladin.domain.Recruitment;
import com.aladin.repository.RecruitmentRepository;
import com.aladin.service.RecruitmentService;
import com.aladin.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.aladin.domain.Recruitment}.
 */
@RestController
@RequestMapping("/api")
public class RecruitmentResource {

    private final Logger log = LoggerFactory.getLogger(RecruitmentResource.class);

    private static final String ENTITY_NAME = "recruitment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecruitmentService recruitmentService;

    private final RecruitmentRepository recruitmentRepository;

    public RecruitmentResource(RecruitmentService recruitmentService, RecruitmentRepository recruitmentRepository) {
        this.recruitmentService = recruitmentService;
        this.recruitmentRepository = recruitmentRepository;
    }

    /**
     * {@code POST  /recruitments} : Create a new recruitment.
     *
     * @param recruitment the recruitment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recruitment, or with status {@code 400 (Bad Request)} if the recruitment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recruitments")
    public ResponseEntity<Recruitment> createRecruitment(@RequestBody Recruitment recruitment) throws URISyntaxException {
        log.debug("REST request to save Recruitment : {}", recruitment);
        if (recruitment.getId() != null) {
            throw new BadRequestAlertException("A new recruitment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Recruitment result = recruitmentService.save(recruitment);
        return ResponseEntity
            .created(new URI("/api/recruitments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recruitments/:id} : Updates an existing recruitment.
     *
     * @param id the id of the recruitment to save.
     * @param recruitment the recruitment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recruitment,
     * or with status {@code 400 (Bad Request)} if the recruitment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recruitment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recruitments/{id}")
    public ResponseEntity<Recruitment> updateRecruitment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Recruitment recruitment
    ) throws URISyntaxException {
        log.debug("REST request to update Recruitment : {}, {}", id, recruitment);
        if (recruitment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recruitment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recruitmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Recruitment result = recruitmentService.save(recruitment);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recruitment.getId().toString()))
            .body(result);
    }
    /**
     * {@code GET  /recruitments} : get all the recruitments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recruitments in body.
     */
    @GetMapping("/recruitments")
    public ResponseEntity<List<Recruitment>> getAllRecruitments(Pageable pageable) {
        log.debug("REST request to get a page of Recruitments");
        Page<Recruitment> page = recruitmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recruitments/:id} : get the "id" recruitment.
     *
     * @param id the id of the recruitment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recruitment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recruitments/{id}")
    public ResponseEntity<Recruitment> getRecruitment(@PathVariable Long id) {
        log.debug("REST request to get Recruitment : {}", id);
        Optional<Recruitment> recruitment = recruitmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recruitment);
    }

    /**
     * {@code DELETE  /recruitments/:id} : delete the "id" recruitment.
     *
     * @param id the id of the recruitment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recruitments/{id}")
    public ResponseEntity<Void> deleteRecruitment(@PathVariable Long id) {
        log.debug("REST request to delete Recruitment : {}", id);
        recruitmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/recruitments?query=:query} : search for the recruitment corresponding
     * to the query.
     *
     * @param query the query of the recruitment search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/recruitments")
    public ResponseEntity<List<Recruitment>> searchRecruitments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Recruitments for query {}", query);
        Page<Recruitment> page = recruitmentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
