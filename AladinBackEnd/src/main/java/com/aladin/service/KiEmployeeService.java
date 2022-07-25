package com.aladin.service;

import com.aladin.domain.KiEmployee;
import com.aladin.repository.KiEmployeeRepository;
import com.aladin.repository.search.KiEmployeeSearchRepository;
import java.util.Optional;

import com.aladin.service.dto.KiEmployeeOnly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KiEmployee}.
 */
@Service
@Transactional
public class KiEmployeeService {

    private final Logger log = LoggerFactory.getLogger(KiEmployeeService.class);

    private final KiEmployeeRepository kiEmployeeRepository;

    private final EmailtemplateService emailtemplateService;

    private final KiEmployeeSearchRepository kiEmployeeSearchRepository;

    public KiEmployeeService(KiEmployeeRepository kiEmployeeRepository, EmailtemplateService emailtemplateService, KiEmployeeSearchRepository kiEmployeeSearchRepository) {
        this.kiEmployeeRepository = kiEmployeeRepository;
        this.emailtemplateService = emailtemplateService;
        this.kiEmployeeSearchRepository = kiEmployeeSearchRepository;
    }

    /**
     * Save a kiEmployee.
     *
     * @param kiEmployee the entity to save.
     * @return the persisted entity.
     */
    @Caching(evict = {
        @CacheEvict(value = "kiBoss",allEntries = true),
        @CacheEvict(value = "kiLeader",allEntries = true),
        @CacheEvict(value = "kiUser",allEntries = true)
    })
    public KiEmployee save(KiEmployee kiEmployee) {
        log.debug("Request to save KiEmployee : {}", kiEmployee);
        KiEmployee result = kiEmployeeRepository.save(kiEmployee);
        kiEmployeeSearchRepository.save(result);
        return result;
    }

    /**
     * Partially update a kiEmployee.
     *
     * @param kiEmployee the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<KiEmployee> partialUpdate(KiEmployee kiEmployee) {
        log.debug("Request to partially update KiEmployee : {}", kiEmployee);
        return kiEmployeeRepository
            .findById(kiEmployee.getId())
            .map(existingKiEmployee -> {
                if (kiEmployee.getDate_time() != null) {
                    existingKiEmployee.setDate_time(kiEmployee.getDate_time());
                }
                if (kiEmployee.getWork_quantity() != null) {
                    existingKiEmployee.setWork_quantity(kiEmployee.getWork_quantity());
                }
                if (kiEmployee.getWork_quantity_comment() != null) {
                    existingKiEmployee.setWork_quantity_comment(kiEmployee.getWork_quantity_comment());
                }
                if (kiEmployee.getWork_quality() != null) {
                    existingKiEmployee.setWork_quality(kiEmployee.getWork_quality());
                }
                if (kiEmployee.getWork_quality_comment() != null) {
                    existingKiEmployee.setWork_quality_comment(kiEmployee.getWork_quality_comment());
                }
                if (kiEmployee.getWork_progress() != null) {
                    existingKiEmployee.setWork_progress(kiEmployee.getWork_progress());
                }
                if (kiEmployee.getWork_progress_comment() != null) {
                    existingKiEmployee.setWork_progress_comment(kiEmployee.getWork_progress_comment());
                }
                if (kiEmployee.getWork_attitude() != null) {
                    existingKiEmployee.setWork_attitude(kiEmployee.getWork_attitude());
                }
                if (kiEmployee.getWork_attitude_comment() != null) {
                    existingKiEmployee.setWork_attitude_comment(kiEmployee.getWork_attitude_comment());
                }
                if (kiEmployee.getWork_discipline() != null) {
                    existingKiEmployee.setWork_discipline(kiEmployee.getWork_discipline());
                }
                if (kiEmployee.getWork_discipline_comment() != null) {
                    existingKiEmployee.setWork_discipline_comment(kiEmployee.getWork_discipline_comment());
                }
                if (kiEmployee.getAssigned_work() != null) {
                    existingKiEmployee.setAssigned_work(kiEmployee.getAssigned_work());
                }
                if (kiEmployee.getOther_work() != null) {
                    existingKiEmployee.setOther_work(kiEmployee.getOther_work());
                }
                if (kiEmployee.getCompleted_work() != null) {
                    existingKiEmployee.setCompleted_work(kiEmployee.getCompleted_work());
                }
                if (kiEmployee.getUncompleted_work() != null) {
                    existingKiEmployee.setUncompleted_work(kiEmployee.getUncompleted_work());
                }
                if (kiEmployee.getFavourite_work() != null) {
                    existingKiEmployee.setFavourite_work(kiEmployee.getFavourite_work());
                }
                if (kiEmployee.getUnfavourite_work() != null) {
                    existingKiEmployee.setUnfavourite_work(kiEmployee.getUnfavourite_work());
                }
                if (kiEmployee.getEmployee_ki_point() != null) {
                    existingKiEmployee.setEmployee_ki_point(kiEmployee.getEmployee_ki_point());
                }
                if (kiEmployee.getLeader_ki_point() != null) {
                    existingKiEmployee.setLeader_ki_point(kiEmployee.getLeader_ki_point());
                }
                if (kiEmployee.getLeader_comment() != null) {
                    existingKiEmployee.setLeader_comment(kiEmployee.getLeader_comment());
                }
                if (kiEmployee.getBoss_ki_point() != null) {
                    existingKiEmployee.setBoss_ki_point(kiEmployee.getBoss_ki_point());
                }
                if (kiEmployee.getBoss_comment() != null) {
                    existingKiEmployee.setBoss_comment(kiEmployee.getBoss_comment());
                }

                return existingKiEmployee;
            })
            .map(kiEmployeeRepository::save)
            .map(savedKiEmployee -> {
                kiEmployeeSearchRepository.save(savedKiEmployee);

                return savedKiEmployee;
            });
    }

    /**
     * Get all the kiEmployees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Cacheable(value = "kiBoss")
    @Transactional(readOnly = true)
    public Page<KiEmployeeOnly> findAll(Pageable pageable) {
        log.debug("Request to get all KiEmployees");
        return kiEmployeeRepository.findBoss(pageable);
    }
    public boolean checkKiMonth(Long employee_id)
    {
        if (kiEmployeeRepository.checkkimonth(employee_id)>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    @Cacheable(value = "kiLeader", key="#user_id")
    @Transactional(readOnly = true)
    public Page<KiEmployeeOnly> findLeader(String user_id,Pageable pageable) {
        log.debug("Request find leader KiEmployees");
        return kiEmployeeRepository.findLeader(user_id,pageable);
    }
    @Cacheable(value = "kiUser", key="#user_id")
    @Transactional(readOnly = true)
    public Page<KiEmployeeOnly> findUser(String user_id,Pageable pageable) {
        log.debug("Request find user KiEmployees");
        return kiEmployeeRepository.findUser(user_id,pageable);
    }
    @Transactional(readOnly = true)
    public void updatestatus(int status, Long ki_id) {
        log.debug("Request update status from KiEmployees");
        kiEmployeeRepository.updatestatus(status,ki_id);
    }
    @Transactional(readOnly = true)
    public Long getstatus(Long ki_id) {
        log.debug("Request to get status from KiEmployees");
        return kiEmployeeRepository.getstatus(ki_id);
    }

    @Transactional(readOnly = true)
    public Long getUserId(Long ki_id) {
        log.debug("Request to get userid from KiEmployees");
        return kiEmployeeRepository.getUserId(ki_id);
    }
    /**
     * Get one kiEmployee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<KiEmployee> findOne(Long id) {
        log.debug("Request to get KiEmployee : {}", id);
        return kiEmployeeRepository.findById(id);
    }

    /**
     * Delete the kiEmployee by id.
     *
     * @param id the id of the entity.
     */
    @Caching(evict = {
        @CacheEvict(value = "kiBoss",allEntries = true),
        @CacheEvict(value = "kiLeader",allEntries = true),
        @CacheEvict(value = "kiUser",allEntries = true)
    })
    public void delete(Long id) {
        log.debug("Request to delete KiEmployee : {}", id);
        kiEmployeeRepository.deleteById(id);
        kiEmployeeSearchRepository.deleteById(id);
    }

    /**
     * Search for the kiEmployee corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<KiEmployee> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of KiEmployees for query {}", query);
        return kiEmployeeSearchRepository.search(query, pageable);
    }
}
