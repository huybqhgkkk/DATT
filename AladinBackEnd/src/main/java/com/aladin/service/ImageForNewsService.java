package com.aladin.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.aladin.domain.ImageForNews;
import com.aladin.repository.ImageForNewsRepository;
import com.aladin.repository.search.ImageForNewsSearchRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ImageForNews}.
 */
@Service
@Transactional
public class ImageForNewsService {

    private final Logger log = LoggerFactory.getLogger(ImageForNewsService.class);

    private final ImageForNewsRepository imageForNewsRepository;

    private final ImageForNewsSearchRepository imageForNewsSearchRepository;

    public ImageForNewsService(ImageForNewsRepository imageForNewsRepository, ImageForNewsSearchRepository imageForNewsSearchRepository) {
        this.imageForNewsRepository = imageForNewsRepository;
        this.imageForNewsSearchRepository = imageForNewsSearchRepository;
    }

    /**
     * Save a imageForNews.
     *
     * @param imageForNews the entity to save.
     * @return the persisted entity.
     */
    public ImageForNews save(ImageForNews imageForNews) {
        log.debug("Request to save ImageForNews : {}", imageForNews);
        ImageForNews result = imageForNewsRepository.save(imageForNews);
        imageForNewsSearchRepository.save(result);
        return result;
    }

    /**
     * Partially update a imageForNews.
     *
     * @param imageForNews the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ImageForNews> partialUpdate(ImageForNews imageForNews) {
        log.debug("Request to partially update ImageForNews : {}", imageForNews);

        return imageForNewsRepository
            .findById(imageForNews.getId())
            .map(existingImageForNews -> {
                if (imageForNews.getImage() != null) {
                    existingImageForNews.setImage(imageForNews.getImage());
                }
                if (imageForNews.getImageContentType() != null) {
                    existingImageForNews.setImageContentType(imageForNews.getImageContentType());
                }

                return existingImageForNews;
            })
            .map(imageForNewsRepository::save)
            .map(savedImageForNews -> {
                imageForNewsSearchRepository.save(savedImageForNews);

                return savedImageForNews;
            });
    }

    /**
     * Get all the imageForNews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ImageForNews> findAll(Pageable pageable) {
        log.debug("Request to get all ImageForNews");
        return imageForNewsRepository.findAll(pageable);
    }

    /**
     * Get one imageForNews by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ImageForNews> findOne(Long id) {
        log.debug("Request to get ImageForNews : {}", id);
        return imageForNewsRepository.findById(id);
    }

    /**
     * Delete the imageForNews by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ImageForNews : {}", id);
        imageForNewsRepository.deleteById(id);
        imageForNewsSearchRepository.deleteById(id);
    }

    /**
     * Search for the imageForNews corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ImageForNews> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ImageForNews for query {}", query);
        return imageForNewsSearchRepository.search(query, pageable);
    }
}
