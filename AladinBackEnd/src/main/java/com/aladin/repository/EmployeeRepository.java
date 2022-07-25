package com.aladin.repository;

import com.aladin.domain.Employee;
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
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "SELECT * FROM EMPLOYEE where user_id = ?1", nativeQuery = true)
    Optional<Employee> searchEmployeeUser(String user_id);
    @Query(value = "select * from employee where user_id=?1", nativeQuery = true)
    Page<Employee> searchEmployeeOne(String user_id, Pageable pageable);

}
