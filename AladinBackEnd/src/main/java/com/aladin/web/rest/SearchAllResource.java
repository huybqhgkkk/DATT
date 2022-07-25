package com.aladin.web.rest;

import com.aladin.domain.Employee;
import com.aladin.service.SearchAllService;
import com.aladin.service.UserService;
import com.aladin.service.dto.SearchAllResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * REST controller for managing {@link Employee}.
 */
@RestController
@RequestMapping("/api")
public class SearchAllResource {


    private final Logger log = LoggerFactory.getLogger(SearchAllResource.class);
    private final SearchAllService searchAllService;
    private final UserService userService;


    public SearchAllResource(SearchAllService searchAllService, UserService userService) {
        this.searchAllService = searchAllService;
        this.userService = userService;
    }

    @GetMapping("/_search-all")
    public ResponseEntity<List<SearchAllResponse>> searchAllByRole(@RequestParam("query") String query, Pageable pageable, Principal principal) {
        if (principal != null) {
            Collection<GrantedAuthority> authorities = ((AbstractAuthenticationToken) principal).getAuthorities();
            AtomicReference<Boolean> checkRole = new AtomicReference<>(false);
            authorities.forEach(role -> {
                if (role.toString().equals("ROLE_USER")) {
                    checkRole.set(true);
                }
            });
            if (checkRole.get()) {
                log.debug("REST user id: {} request to search all : {}", query);
                Page<SearchAllResponse> page = searchAllService.searchAllByRole(query, pageable);
                HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
                return ResponseEntity.ok().headers(headers).body(page.getContent());
            } else {
                log.debug("REST request to search all : {}", query);
                Page<SearchAllResponse> page = searchAllService.searchAllPublic(query, pageable);
                HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
                return ResponseEntity.ok().headers(headers).body(page.getContent());
            }
        } else {
            log.debug("REST request to search all : {}", query);
            Page<SearchAllResponse> page = searchAllService.searchAllPublic(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
    }

    @GetMapping("/_search-employee")
    public ResponseEntity<List<SearchAllResponse>> searchEmployeeOnly(@RequestParam("query") String query, Pageable pageable, Principal principal) {
        if (principal != null) {
            Collection<GrantedAuthority> authorities = ((AbstractAuthenticationToken) principal).getAuthorities();
            AtomicReference<Boolean> checkRole = new AtomicReference<>(false);
            authorities.forEach(role -> {
                if (role.toString().equals("ROLE_USER")) {
                    checkRole.set(true);
                }
            });
            if (checkRole.get()) {
                log.debug("REST user id: {} request to search employee : {}", query);
                Page<SearchAllResponse> page = searchAllService.searchEmployeeOnly(query, pageable);
                HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
                return ResponseEntity.ok().headers(headers).body(page.getContent());
            }
        }
        return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

}
