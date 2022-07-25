package com.aladin.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.aladin.domain.Kafkaproducer;
import com.aladin.repository.KafkaproducerRepository;
import com.aladin.repository.search.KafkaproducerSearchRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Kafkaproducer}.
 */
@Service
@Transactional
public class KafkaproducerService {

    private final Logger log = LoggerFactory.getLogger(KafkaproducerService.class);

    private final KafkaproducerRepository kafkaproducerRepository;

    private final KafkaproducerSearchRepository kafkaproducerSearchRepository;

    public KafkaproducerService(
        KafkaproducerRepository kafkaproducerRepository,
        KafkaproducerSearchRepository kafkaproducerSearchRepository
    ) {
        this.kafkaproducerRepository = kafkaproducerRepository;
        this.kafkaproducerSearchRepository = kafkaproducerSearchRepository;
    }

    /**
     * Save a kafkaproducer.
     *
     * @param kafkaproducer the entity to save.
     * @return the persisted entity.
     */
    public Kafkaproducer save(Kafkaproducer kafkaproducer) {
        log.debug("Request to save Kafkaproducer : {}", kafkaproducer);
        Kafkaproducer result = kafkaproducerRepository.save(kafkaproducer);
        kafkaproducerSearchRepository.save(result);
        return result;
    }

    /**
     * Partially update a kafkaproducer.
     *
     * @param kafkaproducer the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Kafkaproducer> partialUpdate(Kafkaproducer kafkaproducer) {
        log.debug("Request to partially update Kafkaproducer : {}", kafkaproducer);

        return kafkaproducerRepository
            .findById(kafkaproducer.getId())
            .map(existingKafkaproducer -> {
                if (kafkaproducer.getSubject() != null) {
                    existingKafkaproducer.setSubject(kafkaproducer.getSubject());
                }
                if (kafkaproducer.getRecipients() != null) {
                    existingKafkaproducer.setRecipients(kafkaproducer.getRecipients());
                }
                if (kafkaproducer.getRecipientsname() != null) {
                    existingKafkaproducer.setRecipientsname(kafkaproducer.getRecipientsname());
                }
                if (kafkaproducer.getCc() != null) {
                    existingKafkaproducer.setCc(kafkaproducer.getCc());
                }
                if (kafkaproducer.getDatetime() != null) {
                    existingKafkaproducer.setDatetime(kafkaproducer.getDatetime());
                }
                if (kafkaproducer.getContent() != null) {
                    existingKafkaproducer.setContent(kafkaproducer.getContent());
                }

                return existingKafkaproducer;
            })
            .map(kafkaproducerRepository::save)
            .map(savedKafkaproducer -> {
                kafkaproducerSearchRepository.save(savedKafkaproducer);

                return savedKafkaproducer;
            });
    }

    /**
     * Get all the kafkaproducers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Kafkaproducer> findAll() {
        log.debug("Request to get all Kafkaproducers");
        return kafkaproducerRepository.findAll();
    }

    /**
     * Get one kafkaproducer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Kafkaproducer> findOne(Long id) {
        log.debug("Request to get Kafkaproducer : {}", id);
        return kafkaproducerRepository.findById(id);
    }

    /**
     * Delete the kafkaproducer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Kafkaproducer : {}", id);
        kafkaproducerRepository.deleteById(id);
        kafkaproducerSearchRepository.deleteById(id);
    }

    /**
     * Search for the kafkaproducer corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Kafkaproducer> search(String query) {
        log.debug("Request to search Kafkaproducers for query {}", query);
        return StreamSupport.stream(kafkaproducerSearchRepository.search(query).spliterator(), false).collect(Collectors.toList());
    }
}
