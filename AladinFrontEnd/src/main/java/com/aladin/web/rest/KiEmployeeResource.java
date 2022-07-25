package com.aladin.web.rest;

import com.aladin.domain.KiEmployee;
import com.aladin.repository.KiEmployeeRepository;
import com.aladin.service.KiEmployeeService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.aladin.domain.KiEmployee}.
 */
@RestController
@RequestMapping("/api")
public class KiEmployeeResource {

    private final Logger log = LoggerFactory.getLogger(KiEmployeeResource.class);

    private static final String ENTITY_NAME = "kiEmployee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KiEmployeeService kiEmployeeService;

    private final KiEmployeeRepository kiEmployeeRepository;

    public KiEmployeeResource(KiEmployeeService kiEmployeeService, KiEmployeeRepository kiEmployeeRepository) {
        this.kiEmployeeService = kiEmployeeService;
        this.kiEmployeeRepository = kiEmployeeRepository;
    }

    /**
     * {@code POST  /ki-employees} : Create a new kiEmployee.
     *
     * @param kiEmployee the kiEmployee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kiEmployee, or with status {@code 400 (Bad Request)} if the kiEmployee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ki-employees")
    public ResponseEntity<KiEmployee> createKiEmployee(@Valid @RequestBody KiEmployee kiEmployee) throws URISyntaxException {
        log.debug("REST request to save KiEmployee : {}", kiEmployee);
        if (kiEmployee.getId() != null) {
            throw new BadRequestAlertException("A new kiEmployee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KiEmployee result = kiEmployeeService.save(kiEmployee);
        return ResponseEntity
            .created(new URI("/api/ki-employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ki-employees/:id} : Updates an existing kiEmployee.
     *
     * @param id the id of the kiEmployee to save.
     * @param kiEmployee the kiEmployee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kiEmployee,
     * or with status {@code 400 (Bad Request)} if the kiEmployee is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kiEmployee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ki-employees/{id}")
    public ResponseEntity<KiEmployee> updateKiEmployee(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody KiEmployee kiEmployee
    ) throws URISyntaxException {
        log.debug("REST request to update KiEmployee : {}, {}", id, kiEmployee);
        if (kiEmployee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kiEmployee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kiEmployeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KiEmployee result = kiEmployeeService.save(kiEmployee);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kiEmployee.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ki-employees/:id} : Partial updates given fields of an existing kiEmployee, field will ignore if it is null
     *
     * @param id the id of the kiEmployee to save.
     * @param kiEmployee the kiEmployee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kiEmployee,
     * or with status {@code 400 (Bad Request)} if the kiEmployee is not valid,
     * or with status {@code 404 (Not Found)} if the kiEmployee is not found,
     * or with status {@code 500 (Internal Server Error)} if the kiEmployee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ki-employees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KiEmployee> partialUpdateKiEmployee(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody KiEmployee kiEmployee
    ) throws URISyntaxException {
        log.debug("REST request to partial update KiEmployee partially : {}, {}", id, kiEmployee);
        if (kiEmployee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kiEmployee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kiEmployeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KiEmployee> result = kiEmployeeService.partialUpdate(kiEmployee);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kiEmployee.getId().toString())
        );
    }

    /**
     * {@code GET  /ki-employees} : get all the kiEmployees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kiEmployees in body.
     */
    @GetMapping("/ki-employees")
    public ResponseEntity<List<KiEmployee>> getAllKiEmployees(Pageable pageable) {
        log.debug("REST request to get a page of KiEmployees");
        Page<KiEmployee> page = kiEmployeeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ki-employees/:id} : get the "id" kiEmployee.
     *
     * @param id the id of the kiEmployee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kiEmployee, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ki-employees/{id}")
    public ResponseEntity<KiEmployee> getKiEmployee(@PathVariable Long id) {
        log.debug("REST request to get KiEmployee : {}", id);
        Optional<KiEmployee> kiEmployee = kiEmployeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kiEmployee);
    }

    /**
     * {@code DELETE  /ki-employees/:id} : delete the "id" kiEmployee.
     *
     * @param id the id of the kiEmployee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ki-employees/{id}")
    public ResponseEntity<Void> deleteKiEmployee(@PathVariable Long id) {
        log.debug("REST request to delete KiEmployee : {}", id);
        kiEmployeeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
