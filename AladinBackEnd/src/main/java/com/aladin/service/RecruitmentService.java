package com.aladin.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.aladin.domain.Recruitment;
import com.aladin.repository.RecruitmentRepository;
import com.aladin.repository.search.RecruitmentSearchRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Recruitment}.
 */
@Service
@Transactional
public class RecruitmentService {

    private final Logger log = LoggerFactory.getLogger(RecruitmentService.class);

    private final RecruitmentRepository recruitmentRepository;

    private final RecruitmentSearchRepository recruitmentSearchRepository;

    public RecruitmentService(RecruitmentRepository recruitmentRepository, RecruitmentSearchRepository recruitmentSearchRepository) {
        this.recruitmentRepository = recruitmentRepository;
        this.recruitmentSearchRepository = recruitmentSearchRepository;
    }

    /**
     * Save a recruitment.
     *
     * @param recruitment the entity to save.
     * @return the persisted entity.
     */
    public Recruitment save(Recruitment recruitment) {
        log.debug("Request to save Recruitment : {}", recruitment);
        Recruitment result = recruitmentRepository.save(recruitment);
        recruitmentSearchRepository.save(result);
        return result;
    }

    /**
     * Partially update a recruitment.
     *
     * @param recruitment the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Recruitment> partialUpdate(Recruitment recruitment) {
        log.debug("Request to partially update Recruitment : {}", recruitment);

        return recruitmentRepository
            .findById(recruitment.getId())
            .map(existingRecruitment -> {
                if (recruitment.getPosition() != null) {
                    existingRecruitment.setPosition(recruitment.getPosition());
                }
                if (recruitment.getDescription() != null) {
                    existingRecruitment.setDescription(recruitment.getDescription());
                }
                if (recruitment.getRequire() != null) {
                    existingRecruitment.setRequire(recruitment.getRequire());
                }
                if (recruitment.getBenefit() != null) {
                    existingRecruitment.setBenefit(recruitment.getBenefit());
                }
                if (recruitment.getAmount() != null) {
                    existingRecruitment.setAmount(recruitment.getAmount());
                }
                if (recruitment.getJob() != null) {
                    existingRecruitment.setJob(recruitment.getJob());
                }
                if (recruitment.getLocation() != null) {
                    existingRecruitment.setLocation(recruitment.getLocation());
                }
                if (recruitment.getLevel() != null) {
                    existingRecruitment.setLevel(recruitment.getLevel());
                }
                if (recruitment.getDuration() != null) {
                    existingRecruitment.setDuration(recruitment.getDuration());
                }

                return existingRecruitment;
            })
            .map(recruitmentRepository::save)
            .map(savedRecruitment -> {
                recruitmentSearchRepository.save(savedRecruitment);

                return savedRecruitment;
            });
    }

    /**
     * Get all the recruitments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Recruitment> findAll(Pageable pageable) {
        log.debug("Request to get all Recruitments");
        return recruitmentRepository.findAll(pageable);
    }

    /**
     * Get one recruitment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Recruitment> findOne(Long id) {
        log.debug("Request to get Recruitment : {}", id);
        return recruitmentRepository.findById(id);
    }

    /**
     * Delete the recruitment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Recruitment : {}", id);
        recruitmentRepository.deleteById(id);
        recruitmentSearchRepository.deleteById(id);
    }

    /**
     * Search for the recruitment corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Recruitment> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Recruitments for query {}", query);
        return recruitmentSearchRepository.search(query, pageable);
    }
}
