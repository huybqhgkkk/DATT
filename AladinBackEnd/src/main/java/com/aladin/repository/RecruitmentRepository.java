package com.aladin.repository;

import com.aladin.domain.Recruitment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Recruitment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {}
