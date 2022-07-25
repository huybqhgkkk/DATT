package com.aladin.service;

import com.aladin.domain.Employee;
import com.aladin.repository.EmployeeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
@Transactional
public class EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Save a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    public Employee save(Employee employee) {
        log.debug("Request to save Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    /**
     * Partially update a employee.
     *
     * @param employee the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Employee> partialUpdate(Employee employee) {
        log.debug("Request to partially update Employee : {}", employee);

        return employeeRepository
            .findById(employee.getId())
            .map(
                existingEmployee -> {
                    if (employee.getFirst_day_work() != null) {
                        existingEmployee.setFirst_day_work(employee.getFirst_day_work());
                    }
                    if (employee.getFull_name() != null) {
                        existingEmployee.setFull_name(employee.getFull_name());
                    }
                    if (employee.getPhone_number() != null) {
                        existingEmployee.setPhone_number(employee.getPhone_number());
                    }
                    if (employee.getEmail() != null) {
                        existingEmployee.setEmail(employee.getEmail());
                    }
                    if (employee.getDate_of_birth() != null) {
                        existingEmployee.setDate_of_birth(employee.getDate_of_birth());
                    }
                    if (employee.getCountryside() != null) {
                        existingEmployee.setCountryside(employee.getCountryside());
                    }
                    if (employee.getCurrent_residence() != null) {
                        existingEmployee.setCurrent_residence(employee.getCurrent_residence());
                    }
                    if (employee.getRelative() != null) {
                        existingEmployee.setRelative(employee.getRelative());
                    }
                    if (employee.getFavourite() != null) {
                        existingEmployee.setFavourite(employee.getFavourite());
                    }
                    if (employee.getEducation() != null) {
                        existingEmployee.setEducation(employee.getEducation());
                    }
                    if (employee.getExperience() != null) {
                        existingEmployee.setExperience(employee.getExperience());
                    }
                    if (employee.getEnglish() != null) {
                        existingEmployee.setEnglish(employee.getEnglish());
                    }
                    if (employee.getObjective_in_cv() != null) {
                        existingEmployee.setObjective_in_cv(employee.getObjective_in_cv());
                    }
                    if (employee.getMarital_status() != null) {
                        existingEmployee.setMarital_status(employee.getMarital_status());
                    }
                    if (employee.getChildren() != null) {
                        existingEmployee.setChildren(employee.getChildren());
                    }
                    if (employee.getFamily() != null) {
                        existingEmployee.setFamily(employee.getFamily());
                    }
                    if (employee.getAvatar() != null) {
                        existingEmployee.setAvatar(employee.getAvatar());
                    }
                    if (employee.getAvatarContentType() != null) {
                        existingEmployee.setAvatarContentType(employee.getAvatarContentType());
                    }
                    if (employee.getGender() != null) {
                        existingEmployee.setGender(employee.getGender());
                    }
                    if (employee.getCertification() != null) {
                        existingEmployee.setCertification(employee.getCertification());
                    }
                    if (employee.getCertificationContentType() != null) {
                        existingEmployee.setCertificationContentType(employee.getCertificationContentType());
                    }

                    return existingEmployee;
                }
            )
            .map(employeeRepository::save);
    }

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Employee> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable);
    }

    /**
     * Get one employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Employee> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id);
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }
}
