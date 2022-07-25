package com.aladin.web.rest;

import com.aladin.domain.Employee;
import com.aladin.domain.KiEmployee;
import com.aladin.repository.KiEmployeeRepository;
import com.aladin.service.KiEmployeeService;
import com.aladin.service.NotificationKi;
import com.aladin.service.UserService;
import com.aladin.service.dto.KiEmployeeOnly;
import com.aladin.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
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

    private final UserService userService;

    private final NotificationKi notificationKi;

    private final KiEmployeeRepository kiEmployeeRepository;

    public KiEmployeeResource(KiEmployeeService kiEmployeeService, UserService userService, NotificationKi notificationKi, KiEmployeeRepository kiEmployeeRepository) {
        this.kiEmployeeService = kiEmployeeService;
        this.userService = userService;
        this.notificationKi = notificationKi;
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
    public ResponseEntity<KiEmployee> createKiEmployee(Principal principal,@Valid @RequestBody KiEmployee kiEmployee) throws URISyntaxException {
        log.debug("REST request to save KiEmployee : {}", kiEmployee);
        String userid = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal).getId();
        userid= userid.replaceAll("[^0-9.]", "");
        if (userid.length()>17) {
            userid = userid.substring(0,16);
        }
        if (kiEmployee.getId() != null) {
            throw new BadRequestAlertException("A new kiEmployee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (kiEmployeeService.checkKiMonth(kiEmployee.getEmployee().getId()))
        {
            throw new BadRequestAlertException("You can do only one KI per month", ENTITY_NAME, "onlyKi");
        }
            notificationKi.alertkiuser(kiEmployee);
            KiEmployee result = kiEmployeeService.save(kiEmployee);
            kiEmployeeService.updatestatus(0,kiEmployee.getId());
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
    public ResponseEntity<KiEmployee> updateKiEmployee(Principal principal,
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
        String userid = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal).getId();
        userid= userid.replaceAll("[^0-9.]", "");
        if (userid.length()>17) {
            userid = userid.substring(0,16);
        }
        KiEmployee result = null;
        if (userService.checkboss(userid)){
            result=kiEmployeeService.save(kiEmployee);
            kiEmployeeService.updatestatus(2,kiEmployee.getId());
            notificationKi.alertkiboss(kiEmployee);
        } else
        if (userService.checkLeader(userid)){
            if (kiEmployeeService.getstatus(kiEmployee.getId())>1 )
            {
                throw new BadRequestAlertException("Boss đã đánh giá không được sửa", ENTITY_NAME, "bossUpdateKI");
            }
            kiEmployee.setBoss_ki_point(kiEmployee.getLeader_ki_point());
            result =kiEmployeeService.save(kiEmployee);
            kiEmployeeService.updatestatus(1,kiEmployee.getId());
            notificationKi.alertkileader(kiEmployee);
        } else
        if (kiEmployeeService.getstatus(kiEmployee.getId())>0 )
        {
            throw new BadRequestAlertException("Leader đã đánh giá không được sửa", ENTITY_NAME, "leaderUpdateKi");
        }
          else
        {
            result =kiEmployeeService.save(kiEmployee);
            kiEmployeeService.updatestatus(0,kiEmployee.getId());
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kiEmployee.getId().toString()))
            .body(result);
    }
    /**
     * {@code GET  /ki-employees} : get all the kiEmployees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kiEmployees in body.
     */
    @GetMapping("/ki-employees")
    public ResponseEntity<List<KiEmployeeOnly>> getAllKiEmployees(Pageable pageable, Principal principal) {
        log.debug("REST request to get a page of KiEmployees");
        Page<KiEmployeeOnly> page = null;
        String userid = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal).getId();
        userid= userid.replaceAll("[^0-9.]", "");
        if (userid.length()>17) {
            userid = userid.substring(0,16);
        }
        if ((userService.checkboss(userid)) || (userService.checkmoney(userid)))
        {
            page = kiEmployeeService.findAll(pageable);
        } else
            if (userService.checkLeader(userid)){
                page = kiEmployeeService.findLeader(userid,pageable);
            }
            else
            {
              page =kiEmployeeService.findUser(userid,pageable);
            }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/ki-employees/{id}")
    public ResponseEntity<KiEmployee> getKiEmployee(@PathVariable Long id, Principal principal) {
        log.debug("REST request to get KiEmployee : {}", id);
        Optional<KiEmployee> kiEmployee = null;
        String userid = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal).getId();
        userid= userid.replaceAll("[^0-9.]", "");
        if (userid.length()>17) {
            userid = userid.substring(0,16);
        }
        if ((userService.checkboss(userid)) || (userService.checkLeader(userid)) || (kiEmployeeService.getUserId(id)==Long.parseLong(userid))){
            kiEmployee = kiEmployeeService.findOne(id);
        }
        else
        {
            throw new BadRequestAlertException("Không được xem KI của người khác", ENTITY_NAME, "idinvalid");
        }
        return ResponseUtil.wrapOrNotFound(kiEmployee);
    }

    /**
     * {@code SEARCH  /_search/ki-employees?query=:query} : search for the kiEmployee corresponding
     * to the query.
     *
     * @param query the query of the kiEmployee search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ki-employees")
    public ResponseEntity<List<KiEmployee>> searchKiEmployees(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of KiEmployees for query {}", query);
        Page<KiEmployee> page = kiEmployeeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
