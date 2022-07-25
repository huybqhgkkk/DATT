package com.aladin.service;

import com.aladin.domain.KiEmployee;
import com.aladin.repository.KiEmployeeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public KiEmployeeService(KiEmployeeRepository kiEmployeeRepository) {
        this.kiEmployeeRepository = kiEmployeeRepository;
    }

    /**
     * Save a kiEmployee.
     *
     * @param kiEmployee the entity to save.
     * @return the persisted entity.
     */
    public KiEmployee save(KiEmployee kiEmployee) {
        log.debug("Request to save KiEmployee : {}", kiEmployee);
        return kiEmployeeRepository.save(kiEmployee);
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
            .map(
                existingKiEmployee -> {
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
                    if (kiEmployee.getStatus() != null) {
                        existingKiEmployee.setStatus(kiEmployee.getStatus());
                    }

                    return existingKiEmployee;
                }
            )
            .map(kiEmployeeRepository::save);
    }

    /**
     * Get all the kiEmployees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<KiEmployee> findAll(Pageable pageable) {
        log.debug("Request to get all KiEmployees");
        return kiEmployeeRepository.findAll(pageable);
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
    public void delete(Long id) {
        log.debug("Request to delete KiEmployee : {}", id);
        kiEmployeeRepository.deleteById(id);
    }
}
