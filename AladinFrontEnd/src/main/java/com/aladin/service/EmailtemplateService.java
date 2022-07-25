package com.aladin.service;

import com.aladin.domain.Emailtemplate;
import com.aladin.repository.EmailtemplateRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Emailtemplate}.
 */
@Service
@Transactional
public class EmailtemplateService {

    private final Logger log = LoggerFactory.getLogger(EmailtemplateService.class);

    private final EmailtemplateRepository emailtemplateRepository;

    public EmailtemplateService(EmailtemplateRepository emailtemplateRepository) {
        this.emailtemplateRepository = emailtemplateRepository;
    }

    /**
     * Save a emailtemplate.
     *
     * @param emailtemplate the entity to save.
     * @return the persisted entity.
     */
    public Emailtemplate save(Emailtemplate emailtemplate) {
        log.debug("Request to save Emailtemplate : {}", emailtemplate);
        return emailtemplateRepository.save(emailtemplate);
    }

    /**
     * Partially update a emailtemplate.
     *
     * @param emailtemplate the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Emailtemplate> partialUpdate(Emailtemplate emailtemplate) {
        log.debug("Request to partially update Emailtemplate : {}", emailtemplate);

        return emailtemplateRepository
            .findById(emailtemplate.getId())
            .map(
                existingEmailtemplate -> {
                    if (emailtemplate.getTemplatename() != null) {
                        existingEmailtemplate.setTemplatename(emailtemplate.getTemplatename());
                    }
                    if (emailtemplate.getSubject() != null) {
                        existingEmailtemplate.setSubject(emailtemplate.getSubject());
                    }
                    if (emailtemplate.getHyperlink() != null) {
                        existingEmailtemplate.setHyperlink(emailtemplate.getHyperlink());
                    }
                    if (emailtemplate.getDatetime() != null) {
                        existingEmailtemplate.setDatetime(emailtemplate.getDatetime());
                    }
                    if (emailtemplate.getContent() != null) {
                        existingEmailtemplate.setContent(emailtemplate.getContent());
                    }

                    return existingEmailtemplate;
                }
            )
            .map(emailtemplateRepository::save);
    }

    /**
     * Get all the emailtemplates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Emailtemplate> findAll(Pageable pageable) {
        log.debug("Request to get all Emailtemplates");
        return emailtemplateRepository.findAll(pageable);
    }

    /**
     * Get one emailtemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Emailtemplate> findOne(Long id) {
        log.debug("Request to get Emailtemplate : {}", id);
        return emailtemplateRepository.findById(id);
    }

    /**
     * Delete the emailtemplate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Emailtemplate : {}", id);
        emailtemplateRepository.deleteById(id);
    }
}
