package com.aladin.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.aladin.domain.Candidate;
import com.aladin.repository.CandidateRepository;
import com.aladin.repository.search.CandidateSearchRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Candidate}.
 */
@Service
@Transactional
public class CandidateService {

    private final Logger log = LoggerFactory.getLogger(CandidateService.class);

    private final CandidateRepository candidateRepository;

    private final CandidateSearchRepository candidateSearchRepository;

    public CandidateService(CandidateRepository candidateRepository, CandidateSearchRepository candidateSearchRepository) {
        this.candidateRepository = candidateRepository;
        this.candidateSearchRepository = candidateSearchRepository;
    }

    /**
     * Save a candidate.
     *
     * @param candidate the entity to save.
     * @return the persisted entity.
     */
    public Candidate save(Candidate candidate) {
        log.debug("Request to save Candidate : {}", candidate);
        Candidate result = candidateRepository.save(candidate);
        candidateSearchRepository.save(result);
        return result;
    }

    /**
     * Partially update a candidate.
     *
     * @param candidate the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Candidate> partialUpdate(Candidate candidate) {
        log.debug("Request to partially update Candidate : {}", candidate);

        return candidateRepository
            .findById(candidate.getId())
            .map(existingCandidate -> {
                if (candidate.getPhone() != null) {
                    existingCandidate.setPhone(candidate.getPhone());
                }
                if (candidate.getEmail() != null) {
                    existingCandidate.setEmail(candidate.getEmail());
                }
                if (candidate.getPosition() != null) {
                    existingCandidate.setPosition(candidate.getPosition());
                }
                if (candidate.getLocation() != null) {
                    existingCandidate.setLocation(candidate.getLocation());
                }
                if (candidate.getPreference() != null) {
                    existingCandidate.setPreference(candidate.getPreference());
                }
                if (candidate.getEducation() != null) {
                    existingCandidate.setEducation(candidate.getEducation());
                }
                if (candidate.getExperience() != null) {
                    existingCandidate.setExperience(candidate.getExperience());
                }
                if (candidate.getTarget() != null) {
                    existingCandidate.setTarget(candidate.getTarget());
                }
                if (candidate.getFullname() != null) {
                    existingCandidate.setFullname(candidate.getFullname());
                }
                if (candidate.getSex() != null) {
                    existingCandidate.setSex(candidate.getSex());
                }
                if (candidate.getCv() != null) {
                    existingCandidate.setCv(candidate.getCv());
                }
                if (candidate.getCvContentType() != null) {
                    existingCandidate.setCvContentType(candidate.getCvContentType());
                }
                if (candidate.getBirthday() != null) {
                    existingCandidate.setBirthday(candidate.getBirthday());
                }
                if (candidate.getRelationship() != null) {
                    existingCandidate.setRelationship(candidate.getRelationship());
                }
                if (candidate.getDateRegister() != null) {
                    existingCandidate.setDateRegister(candidate.getDateRegister());
                }

                return existingCandidate;
            })
            .map(candidateRepository::save)
            .map(savedCandidate -> {
                candidateSearchRepository.save(savedCandidate);

                return savedCandidate;
            });
    }

    /**
     * Get all the candidates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Candidate> findAll(Pageable pageable) {
        log.debug("Request to get all Candidates");
        return candidateRepository.findAll(pageable);
    }

    /**
     * Get one candidate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Candidate> findOne(Long id) {
        log.debug("Request to get Candidate : {}", id);
        return candidateRepository.findById(id);
    }

    /**
     * Delete the candidate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Candidate : {}", id);
        candidateRepository.deleteById(id);
        candidateSearchRepository.deleteById(id);
    }

    /**
     * Search for the candidate corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Candidate> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Candidates for query {}", query);
        return candidateSearchRepository.search(query, pageable);
    }
}
