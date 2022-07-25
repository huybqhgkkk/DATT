package com.aladin.repository;

import com.aladin.domain.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Department entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query(value = "select * from department WHERE user_id=?1", nativeQuery = true)
    Page<Department> finddepartment(String user_id,Pageable pageable);

    @Query(value = "select * from department where isleader =1", nativeQuery = true)
    Page<Department> finddepartmentname(Pageable pageable);

    @Query(value = "select * from department WHERE department_name=?1", nativeQuery = true)
    Page<Department> finddepartmentsuser(String name,Pageable pageable);

}
