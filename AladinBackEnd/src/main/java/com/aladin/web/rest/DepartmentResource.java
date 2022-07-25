package com.aladin.web.rest;

import com.aladin.domain.Contact;
import com.aladin.domain.Department;
import com.aladin.repository.DepartmentRepository;
import com.aladin.service.DepartmentService;
import com.aladin.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.aladin.domain.Department}.
 */
@RestController
@RequestMapping("/api")
public class DepartmentResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentResource.class);

    private static final String ENTITY_NAME = "department";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartmentService departmentService;

    private final UserService userService;

    private final DepartmentRepository departmentRepository;

    public DepartmentResource(DepartmentService departmentService, UserService userService, DepartmentRepository departmentRepository) {
        this.departmentService = departmentService;
        this.userService = userService;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getOneDepartments(Principal principal, Pageable pageable) {
        log.debug("REST request to get a page of principal Departments.");
        String user_id = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal).getId();
        user_id= user_id.replaceAll("[^0-9.]", "");
        if (user_id.length()>17) {
            user_id = user_id.substring(0,16);
        }
        Page<Department> page = departmentService.finddepartment(user_id,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    @GetMapping("/departmentsname")
    public ResponseEntity<List<Department>> getDepartmentsName(Pageable pageable) {
        log.debug("REST request to get a page of Departments.");
        Page<Department> page = departmentService.finddepartmentname(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    @GetMapping("/departmentsname/{name}")
    public ResponseEntity<List<Department>> getdepartmentsuser(@PathVariable String name,Pageable pageable) {
        log.debug("REST request to get a page of Departments : {}", name);
        Page<Department> page = departmentService.getdepartmentsuser(name,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


}
