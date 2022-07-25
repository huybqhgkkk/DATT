package com.aladin.repository;

import com.aladin.domain.KiEmployee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the KiEmployee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KiEmployeeRepository extends JpaRepository<KiEmployee, Long> {}
