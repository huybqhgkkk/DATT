package com.aladin.repository;

import com.aladin.domain.Recruitmen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Recruitmen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecruitmenRepository extends JpaRepository<Recruitmen, Long> {}
