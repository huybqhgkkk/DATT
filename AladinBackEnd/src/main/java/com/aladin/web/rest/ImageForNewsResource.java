package com.aladin.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.aladin.domain.ImageForNews;
import com.aladin.repository.ImageForNewsRepository;
import com.aladin.service.ImageForNewsService;
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
 * REST controller for managing {@link com.aladin.domain.ImageForNews}.
 */
@RestController
@RequestMapping("/api")
public class ImageForNewsResource {

    private final Logger log = LoggerFactory.getLogger(ImageForNewsResource.class);

    private static final String ENTITY_NAME = "imageForNews";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImageForNewsService imageForNewsService;

    private final ImageForNewsRepository imageForNewsRepository;

    public ImageForNewsResource(ImageForNewsService imageForNewsService, ImageForNewsRepository imageForNewsRepository) {
        this.imageForNewsService = imageForNewsService;
        this.imageForNewsRepository = imageForNewsRepository;
    }

    /**
     * {@code POST  /image-for-news} : Create a new imageForNews.
     *
     * @param imageForNews the imageForNews to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imageForNews, or with status {@code 400 (Bad Request)} if the imageForNews has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/image-for-news")
    public ResponseEntity<ImageForNews> createImageForNews(@RequestBody ImageForNews imageForNews) throws URISyntaxException {
        log.debug("REST request to save ImageForNews : {}", imageForNews);
        if (imageForNews.getId() != null) {
            throw new BadRequestAlertException("A new imageForNews cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImageForNews result = imageForNewsService.save(imageForNews);
        return ResponseEntity
            .created(new URI("/api/image-for-news/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /image-for-news/:id} : Updates an existing imageForNews.
     *
     * @param id the id of the imageForNews to save.
     * @param imageForNews the imageForNews to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imageForNews,
     * or with status {@code 400 (Bad Request)} if the imageForNews is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imageForNews couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/image-for-news/{id}")
    public ResponseEntity<ImageForNews> updateImageForNews(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ImageForNews imageForNews
    ) throws URISyntaxException {
        log.debug("REST request to update ImageForNews : {}, {}", id, imageForNews);
        if (imageForNews.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, imageForNews.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!imageForNewsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ImageForNews result = imageForNewsService.save(imageForNews);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imageForNews.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /image-for-news} : get all the imageForNews.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of imageForNews in body.
     */
    @GetMapping("/image-for-news")
    public ResponseEntity<List<ImageForNews>> getAllImageForNews(Pageable pageable) {
        log.debug("REST request to get a page of ImageForNews");
        Page<ImageForNews> page = imageForNewsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /image-for-news/:id} : get the "id" imageForNews.
     *
     * @param id the id of the imageForNews to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imageForNews, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/image-for-news/{id}")
    public ResponseEntity<ImageForNews> getImageForNews(@PathVariable Long id) {
        log.debug("REST request to get ImageForNews : {}", id);
        Optional<ImageForNews> imageForNews = imageForNewsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imageForNews);
    }

    /**
     * {@code DELETE  /image-for-news/:id} : delete the "id" imageForNews.
     *
     * @param id the id of the imageForNews to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/image-for-news/{id}")
    public ResponseEntity<Void> deleteImageForNews(@PathVariable Long id) {
        log.debug("REST request to delete ImageForNews : {}", id);
        imageForNewsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/image-for-news?query=:query} : search for the imageForNews corresponding
     * to the query.
     *
     * @param query the query of the imageForNews search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/image-for-news")
    public ResponseEntity<List<ImageForNews>> searchImageForNews(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ImageForNews for query {}", query);
        Page<ImageForNews> page = imageForNewsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
