package com.aladin.repository;

import com.aladin.domain.Kafkaproducer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Kafkaproducer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KafkaproducerRepository extends JpaRepository<Kafkaproducer, Long> {}
