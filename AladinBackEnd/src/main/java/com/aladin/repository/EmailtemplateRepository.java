package com.aladin.repository;

import com.aladin.domain.Emailtemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Emailtemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailtemplateRepository extends JpaRepository<Emailtemplate, Long> {


}
