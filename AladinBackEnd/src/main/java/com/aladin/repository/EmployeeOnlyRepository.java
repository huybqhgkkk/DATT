package com.aladin.repository;

import com.aladin.domain.Employee;
import com.aladin.service.dto.EmployeeOnly;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeOnlyRepository extends JpaRepository<Employee, Long> {
    @Query(value = "select new com.aladin.service.dto.EmployeeOnly(p.id,p.full_name,p.email,p.date_of_birth,p.first_day_work,p.phone_number,p.avatar) from Employee p")
    Page<EmployeeOnly> searchEmployeeOnly(Pageable pageable);

    @Query(value = "select new com.aladin.service.dto.EmployeeOnly(p.id,p.full_name,p.email,p.date_of_birth,p.first_day_work,p.phone_number,p.avatar) from Employee p where p.id =?1")
    Optional<EmployeeOnly> searchEmployeeOnlyOne(Long user_id);

    @Query(value = "select count(*) from department where user_id = ?1", nativeQuery = true)
    int countdepartment(String user_id);
}
