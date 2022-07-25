package com.aladin.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.aladin.domain.Services;
import com.aladin.repository.ServicesRepository;
import com.aladin.repository.search.ServicesSearchRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Services}.
 */
@Service
@Transactional
public class ServicesService {

    private final Logger log = LoggerFactory.getLogger(ServicesService.class);

    private final ServicesRepository servicesRepository;

    private final ServicesSearchRepository servicesSearchRepository;

    public ServicesService(ServicesRepository servicesRepository, ServicesSearchRepository servicesSearchRepository) {
        this.servicesRepository = servicesRepository;
        this.servicesSearchRepository = servicesSearchRepository;
    }

    /**
     * Save a services.
     *
     * @param services the entity to save.
     * @return the persisted entity.
     */
    public Services save(Services services) {
        log.debug("Request to save Services : {}", services);
        Services result = servicesRepository.save(services);
        servicesSearchRepository.save(result);
        return result;
    }

    /**
     * Partially update a services.
     *
     * @param services the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Services> partialUpdate(Services services) {
        log.debug("Request to partially update Services : {}", services);

        return servicesRepository
            .findById(services.getId())
            .map(existingServices -> {
                if (services.getType() != null) {
                    existingServices.setType(services.getType());
                }
                if (services.getDescription() != null) {
                    existingServices.setDescription(services.getDescription());
                }
                if (services.getReason() != null) {
                    existingServices.setReason(services.getReason());
                }

                return existingServices;
            })
            .map(servicesRepository::save)
            .map(savedServices -> {
                servicesSearchRepository.save(savedServices);

                return savedServices;
            });
    }

    /**
     * Get all the services.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Services> findAll(Pageable pageable) {
        log.debug("Request to get all Services");
        return servicesRepository.findAll(pageable);
    }

    /**
     * Get one services by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Services> findOne(Long id) {
        log.debug("Request to get Services : {}", id);
        return servicesRepository.findById(id);
    }

    /**
     * Delete the services by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Services : {}", id);
        servicesRepository.deleteById(id);
        servicesSearchRepository.deleteById(id);
    }

    /**
     * Search for the services corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Services> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Services for query {}", query);
        return servicesSearchRepository.search(query, pageable);
    }
}
