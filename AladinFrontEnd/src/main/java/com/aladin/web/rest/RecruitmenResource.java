package com.aladin.web.rest;

import com.aladin.domain.Recruitmen;
import com.aladin.repository.RecruitmenRepository;
import com.aladin.web.rest.errors.BadRequestAlertException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.aladin.domain.Recruitmen}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RecruitmenResource {

    private final Logger log = LoggerFactory.getLogger(RecruitmenResource.class);

    private static final String ENTITY_NAME = "recruitmen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecruitmenRepository recruitmenRepository;

    public RecruitmenResource(RecruitmenRepository recruitmenRepository) {
        this.recruitmenRepository = recruitmenRepository;
    }

    /**
     * {@code POST  /recruitmen} : Create a new recruitmen.
     *
     * @param recruitmen the recruitmen to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recruitmen, or with status {@code 400 (Bad Request)} if the recruitmen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recruitmen")
    public ResponseEntity<Recruitmen> createRecruitmen(@Valid @RequestBody Recruitmen recruitmen) throws URISyntaxException {
        log.debug("REST request to save Recruitmen : {}", recruitmen);
        if (recruitmen.getId() != null) {
            throw new BadRequestAlertException("A new recruitmen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Recruitmen result = recruitmenRepository.save(recruitmen);
        return ResponseEntity
            .created(new URI("/api/recruitmen/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recruitmen/:id} : Updates an existing recruitmen.
     *
     * @param id the id of the recruitmen to save.
     * @param recruitmen the recruitmen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recruitmen,
     * or with status {@code 400 (Bad Request)} if the recruitmen is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recruitmen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recruitmen/{id}")
    public ResponseEntity<Recruitmen> updateRecruitmen(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Recruitmen recruitmen
    ) throws URISyntaxException {
        log.debug("REST request to update Recruitmen : {}, {}", id, recruitmen);
        if (recruitmen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recruitmen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recruitmenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Recruitmen result = recruitmenRepository.save(recruitmen);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recruitmen.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /recruitmen/:id} : Partial updates given fields of an existing recruitmen, field will ignore if it is null
     *
     * @param id the id of the recruitmen to save.
     * @param recruitmen the recruitmen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recruitmen,
     * or with status {@code 400 (Bad Request)} if the recruitmen is not valid,
     * or with status {@code 404 (Not Found)} if the recruitmen is not found,
     * or with status {@code 500 (Internal Server Error)} if the recruitmen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/recruitmen/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Recruitmen> partialUpdateRecruitmen(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Recruitmen recruitmen
    ) throws URISyntaxException {
        log.debug("REST request to partial update Recruitmen partially : {}, {}", id, recruitmen);
        if (recruitmen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recruitmen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recruitmenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Recruitmen> result = recruitmenRepository
            .findById(recruitmen.getId())
            .map(
                existingRecruitmen -> {
                    if (recruitmen.getPosition() != null) {
                        existingRecruitmen.setPosition(recruitmen.getPosition());
                    }
                    if (recruitmen.getDescription() != null) {
                        existingRecruitmen.setDescription(recruitmen.getDescription());
                    }
                    if (recruitmen.getRequire() != null) {
                        existingRecruitmen.setRequire(recruitmen.getRequire());
                    }
                    if (recruitmen.getBenefit() != null) {
                        existingRecruitmen.setBenefit(recruitmen.getBenefit());
                    }
                    if (recruitmen.getAmount() != null) {
                        existingRecruitmen.setAmount(recruitmen.getAmount());
                    }
                    if (recruitmen.getJob() != null) {
                        existingRecruitmen.setJob(recruitmen.getJob());
                    }
                    if (recruitmen.getLocation() != null) {
                        existingRecruitmen.setLocation(recruitmen.getLocation());
                    }
                    if (recruitmen.getDuration() != null) {
                        existingRecruitmen.setDuration(recruitmen.getDuration());
                    }
                    if (recruitmen.getLevel() != null) {
                        existingRecruitmen.setLevel(recruitmen.getLevel());
                    }

                    return existingRecruitmen;
                }
            )
            .map(recruitmenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recruitmen.getId().toString())
        );
    }

    /**
     * {@code GET  /recruitmen} : get all the recruitmen.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recruitmen in body.
     */
    @GetMapping("/recruitmen")
    public ResponseEntity<List<Recruitmen>> getAllRecruitmen(Pageable pageable) {
        log.debug("REST request to get a page of Recruitmen");
        Page<Recruitmen> page = recruitmenRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recruitmen/:id} : get the "id" recruitmen.
     *
     * @param id the id of the recruitmen to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recruitmen, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recruitmen/{id}")
    public ResponseEntity<Recruitmen> getRecruitmen(@PathVariable Long id) {
        log.debug("REST request to get Recruitmen : {}", id);
        Optional<Recruitmen> recruitmen = recruitmenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recruitmen);
    }

    /**
     * {@code DELETE  /recruitmen/:id} : delete the "id" recruitmen.
     *
     * @param id the id of the recruitmen to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recruitmen/{id}")
    public ResponseEntity<Void> deleteRecruitmen(@PathVariable Long id) {
        log.debug("REST request to delete Recruitmen : {}", id);
        recruitmenRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
