package com.aladin.service;

import com.aladin.domain.Employee;
import com.aladin.service.dto.EmployeeOnly;
import com.aladin.repository.EmployeeOnlyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
@Transactional
public class EmployeeOnlyService {

    private final Logger log = LoggerFactory.getLogger(EmployeeOnlyService.class);

    private final EmployeeOnlyRepository employeeOnlyRepository;

    public EmployeeOnlyService(EmployeeOnlyRepository employeeOnlyRepository) {

        this.employeeOnlyRepository = employeeOnlyRepository;
    }
    /**
     * Get all the employees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "findEmployeeonly")
    public Page<EmployeeOnly> findAll(Pageable pageable) {
        log.info("Request to get all Employees");
        return employeeOnlyRepository.searchEmployeeOnly(pageable);
    }


    @Cacheable(value = "findEmployeeonly",key = "#id")
    @Transactional(readOnly = true)
    public Optional<EmployeeOnly> findOne(Long id) {
        log.debug("Request to get  Employees id");
        return employeeOnlyRepository.searchEmployeeOnlyOne(id);
    }
    @Transactional(readOnly = true)
    public Boolean checkuseraladin (String user_id) {
        if (employeeOnlyRepository.countdepartment(user_id) >=1)
        {
            return true;
        }else
        {
            return false;
        }
    }
}
