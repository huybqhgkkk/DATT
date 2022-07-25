package com.aladin.web.rest;

import com.aladin.domain.Employee;
import com.aladin.service.dto.EmployeeOnly;
import com.aladin.repository.EmployeeRepository;
import com.aladin.service.EmployeeOnlyService;
import com.aladin.service.EmployeeService;
import com.aladin.service.UserService;
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
 * REST controller for managing {@link com.aladin.domain.Employee}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    private static final String ENTITY_NAME = "employee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeService employeeService;

    private final EmployeeOnlyService employeeOnlyService;

    private final UserService userService;

    private final EmployeeRepository employeeRepository;

    public EmployeeResource(EmployeeService employeeService, EmployeeOnlyService employeeOnlyService, UserService userService, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.employeeOnlyService = employeeOnlyService;
        this.userService = userService;
        this.employeeRepository = employeeRepository;
    }

    /**
     * {@code POST  /employees} : Create a new employee.
     *
     * @param employee the employee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employee, or with status {@code 400 (Bad Request)} if the employee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", employee);
        if (employee.getId() != null) {
            throw new BadRequestAlertException("A new employee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Employee result = employeeService.save(employee);
        employeeService.saveEmployeeSearch(result);
        return ResponseEntity
            .created(new URI("/api/employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employees/:id} : Updates an existing employee.
     *
     * @param id the id of the employee to save.
     * @param employee the employee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employee,
     * or with status {@code 400 (Bad Request)} if the employee is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Employee employee,Principal principal
    ) throws URISyntaxException {
        log.debug("REST request to update Employee : {}, {}", id, employee);
        String user_id = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal).getId();
        user_id= user_id.replaceAll("[^0-9.]", "");
        if (user_id.length()>17) {
            user_id = user_id.substring(0,16);
        }
        if (!employee.getUser().getId().equals(user_id))
        {
            throw new BadRequestAlertException("not yours", ENTITY_NAME, "idnull");
        }
        if (employee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Employee result = employeeService.save(employee);
        employeeService.saveEmployeeSearch(employee);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employee.getId().toString()))
            .body(result);
    }

    /**
     * {@code DELETE  /employees/:id} : delete the "id" employee.
     *
     * @param id the id of the employee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.debug("REST request to delete Employee : {}", id);
        employeeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/employees?query=:query} : search for the employee corresponding
     * to the query.
     *
     * @param query the query of the employee search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/employees")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Employees for query {}", query);
        Page<Employee> page = employeeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    /**
     * {@code GET  /employees} : get all the employees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employees in body.
     */
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeOnly>> getAllEmployees(Pageable pageable,Principal principal) {
        log.debug("REST request to get a page of Employees");
        String user_id = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal).getId();
        user_id= user_id.replaceAll("[^0-9.]", "");
        if (user_id.length()>17) {
            user_id = user_id.substring(0,16);
        }
        if (!employeeOnlyService.checkuseraladin(user_id))
        {
            throw new BadRequestAlertException("only Aladin", ENTITY_NAME, "only Aladin");
        }
        Page<EmployeeOnly> page = employeeOnlyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    /**
     * {@code GET  /employees/:id} : get the "id" employee.
     *
     * @param id the id of the employee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employee, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employees/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id, Principal principal) {
        log.debug("REST request to get Employee : {}", id);
        String user_id = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal).getId();
        user_id= user_id.replaceAll("[^0-9.]", "");
        if (user_id.length()>17) {
            user_id = user_id.substring(0,16);
        }
        Optional<Employee> employee = employeeService.findOne(id);
        if ((employee.get().getUser().getId().equals(user_id))|| (userService.checkboss(user_id)) ) {

             return ResponseUtil.wrapOrNotFound(employee);
        }
        else
        {
            Optional<EmployeeOnly> employeeOnly = employeeOnlyService.findOne(id);
            return ResponseUtil.wrapOrNotFound(employeeOnly);
        }
    }
    @GetMapping("/employees1")
    public ResponseEntity<List<Employee>> getOneEmployees(Pageable pageable,Principal principal) {
        log.debug("REST request to get a page of Employees");
        String user_id = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal).getId();
        user_id= user_id.replaceAll("[^0-9.]", "");
        if (user_id.length()>17) {
            user_id = user_id.substring(0,16);
        }
        Page<Employee> page = employeeService.findOneEmployee(user_id,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
