package com.aladin.repository;

import com.aladin.domain.ImageForNews;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ImageForNews entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageForNewsRepository extends JpaRepository<ImageForNews, Long> {}
