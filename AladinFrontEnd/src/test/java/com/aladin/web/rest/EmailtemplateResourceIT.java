package com.aladin.web.rest;

import static com.aladin.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aladin.IntegrationTest;
import com.aladin.domain.Emailtemplate;
import com.aladin.repository.EmailtemplateRepository;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EmailtemplateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmailtemplateResourceIT {

    private static final String DEFAULT_TEMPLATENAME = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATENAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_HYPERLINK = "AAAAAAAAAA";
    private static final String UPDATED_HYPERLINK = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/emailtemplates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmailtemplateRepository emailtemplateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailtemplateMockMvc;

    private Emailtemplate emailtemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emailtemplate createEntity(EntityManager em) {
        Emailtemplate emailtemplate = new Emailtemplate()
            .templatename(DEFAULT_TEMPLATENAME)
            .subject(DEFAULT_SUBJECT)
            .hyperlink(DEFAULT_HYPERLINK)
            .datetime(DEFAULT_DATETIME)
            .content(DEFAULT_CONTENT);
        return emailtemplate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emailtemplate createUpdatedEntity(EntityManager em) {
        Emailtemplate emailtemplate = new Emailtemplate()
            .templatename(UPDATED_TEMPLATENAME)
            .subject(UPDATED_SUBJECT)
            .hyperlink(UPDATED_HYPERLINK)
            .datetime(UPDATED_DATETIME)
            .content(UPDATED_CONTENT);
        return emailtemplate;
    }

    @BeforeEach
    public void initTest() {
        emailtemplate = createEntity(em);
    }

    @Test
    @Transactional
    void createEmailtemplate() throws Exception {
        int databaseSizeBeforeCreate = emailtemplateRepository.findAll().size();
        // Create the Emailtemplate
        restEmailtemplateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailtemplate)))
            .andExpect(status().isCreated());

        // Validate the Emailtemplate in the database
        List<Emailtemplate> emailtemplateList = emailtemplateRepository.findAll();
        assertThat(emailtemplateList).hasSize(databaseSizeBeforeCreate + 1);
        Emailtemplate testEmailtemplate = emailtemplateList.get(emailtemplateList.size() - 1);
        assertThat(testEmailtemplate.getTemplatename()).isEqualTo(DEFAULT_TEMPLATENAME);
        assertThat(testEmailtemplate.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testEmailtemplate.getHyperlink()).isEqualTo(DEFAULT_HYPERLINK);
        assertThat(testEmailtemplate.getDatetime()).isEqualTo(DEFAULT_DATETIME);
        assertThat(testEmailtemplate.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    void createEmailtemplateWithExistingId() throws Exception {
        // Create the Emailtemplate with an existing ID
        emailtemplate.setId(1L);

        int databaseSizeBeforeCreate = emailtemplateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailtemplateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailtemplate)))
            .andExpect(status().isBadRequest());

        // Validate the Emailtemplate in the database
        List<Emailtemplate> emailtemplateList = emailtemplateRepository.findAll();
        assertThat(emailtemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTemplatenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailtemplateRepository.findAll().size();
        // set the field null
        emailtemplate.setTemplatename(null);

        // Create the Emailtemplate, which fails.

        restEmailtemplateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailtemplate)))
            .andExpect(status().isBadRequest());

        List<Emailtemplate> emailtemplateList = emailtemplateRepository.findAll();
        assertThat(emailtemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSubjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailtemplateRepository.findAll().size();
        // set the field null
        emailtemplate.setSubject(null);

        // Create the Emailtemplate, which fails.

        restEmailtemplateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailtemplate)))
            .andExpect(status().isBadRequest());

        List<Emailtemplate> emailtemplateList = emailtemplateRepository.findAll();
        assertThat(emailtemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailtemplateRepository.findAll().size();
        // set the field null
        emailtemplate.setContent(null);

        // Create the Emailtemplate, which fails.

        restEmailtemplateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailtemplate)))
            .andExpect(status().isBadRequest());

        List<Emailtemplate> emailtemplateList = emailtemplateRepository.findAll();
        assertThat(emailtemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmailtemplates() throws Exception {
        // Initialize the database
        emailtemplateRepository.saveAndFlush(emailtemplate);

        // Get all the emailtemplateList
        restEmailtemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailtemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].templatename").value(hasItem(DEFAULT_TEMPLATENAME)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].hyperlink").value(hasItem(DEFAULT_HYPERLINK)))
            .andExpect(jsonPath("$.[*].datetime").value(hasItem(sameInstant(DEFAULT_DATETIME))))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    void getEmailtemplate() throws Exception {
        // Initialize the database
        emailtemplateRepository.saveAndFlush(emailtemplate);

        // Get the emailtemplate
        restEmailtemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, emailtemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emailtemplate.getId().intValue()))
            .andExpect(jsonPath("$.templatename").value(DEFAULT_TEMPLATENAME))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.hyperlink").value(DEFAULT_HYPERLINK))
            .andExpect(jsonPath("$.datetime").value(sameInstant(DEFAULT_DATETIME)))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    void getNonExistingEmailtemplate() throws Exception {
        // Get the emailtemplate
        restEmailtemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmailtemplate() throws Exception {
        // Initialize the database
        emailtemplateRepository.saveAndFlush(emailtemplate);

        int databaseSizeBeforeUpdate = emailtemplateRepository.findAll().size();

        // Update the emailtemplate
        Emailtemplate updatedEmailtemplate = emailtemplateRepository.findById(emailtemplate.getId()).get();
        // Disconnect from session so that the updates on updatedEmailtemplate are not directly saved in db
        em.detach(updatedEmailtemplate);
        updatedEmailtemplate
            .templatename(UPDATED_TEMPLATENAME)
            .subject(UPDATED_SUBJECT)
            .hyperlink(UPDATED_HYPERLINK)
            .datetime(UPDATED_DATETIME)
            .content(UPDATED_CONTENT);

        restEmailtemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmailtemplate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmailtemplate))
            )
            .andExpect(status().isOk());

        // Validate the Emailtemplate in the database
        List<Emailtemplate> emailtemplateList = emailtemplateRepository.findAll();
        assertThat(emailtemplateList).hasSize(databaseSizeBeforeUpdate);
        Emailtemplate testEmailtemplate = emailtemplateList.get(emailtemplateList.size() - 1);
        assertThat(testEmailtemplate.getTemplatename()).isEqualTo(UPDATED_TEMPLATENAME);
        assertThat(testEmailtemplate.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEmailtemplate.getHyperlink()).isEqualTo(UPDATED_HYPERLINK);
        assertThat(testEmailtemplate.getDatetime()).isEqualTo(UPDATED_DATETIME);
        assertThat(testEmailtemplate.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void putNonExistingEmailtemplate() throws Exception {
        int databaseSizeBeforeUpdate = emailtemplateRepository.findAll().size();
        emailtemplate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailtemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emailtemplate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emailtemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emailtemplate in the database
        List<Emailtemplate> emailtemplateList = emailtemplateRepository.findAll();
        assertThat(emailtemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmailtemplate() throws Exception {
        int databaseSizeBeforeUpdate = emailtemplateRepository.findAll().size();
        emailtemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailtemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emailtemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emailtemplate in the database
        List<Emailtemplate> emailtemplateList = emailtemplateRepository.findAll();
        assertThat(emailtemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmailtemplate() throws Exception {
        int databaseSizeBeforeUpdate = emailtemplateRepository.findAll().size();
        emailtemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailtemplateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailtemplate)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emailtemplate in the database
        List<Emailtemplate> emailtemplateList = emailtemplateRepository.findAll();
        assertThat(emailtemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmailtemplateWithPatch() throws Exception {
        // Initialize the database
        emailtemplateRepository.saveAndFlush(emailtemplate);

        int databaseSizeBeforeUpdate = emailtemplateRepository.findAll().size();

        // Update the emailtemplate using partial update
        Emailtemplate partialUpdatedEmailtemplate = new Emailtemplate();
        partialUpdatedEmailtemplate.setId(emailtemplate.getId());

        partialUpdatedEmailtemplate
            .templatename(UPDATED_TEMPLATENAME)
            .subject(UPDATED_SUBJECT)
            .datetime(UPDATED_DATETIME)
            .content(UPDATED_CONTENT);

        restEmailtemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmailtemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmailtemplate))
            )
            .andExpect(status().isOk());

        // Validate the Emailtemplate in the database
        List<Emailtemplate> emailtemplateList = emailtemplateRepository.findAll();
        assertThat(emailtemplateList).hasSize(databaseSizeBeforeUpdate);
        Emailtemplate testEmailtemplate = emailtemplateList.get(emailtemplateList.size() - 1);
        assertThat(testEmailtemplate.getTemplatename()).isEqualTo(UPDATED_TEMPLATENAME);
        assertThat(testEmailtemplate.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEmailtemplate.getHyperlink()).isEqualTo(DEFAULT_HYPERLINK);
        assertThat(testEmailtemplate.getDatetime()).isEqualTo(UPDATED_DATETIME);
        assertThat(testEmailtemplate.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void fullUpdateEmailtemplateWithPatch() throws Exception {
        // Initialize the database
        emailtemplateRepository.saveAndFlush(emailtemplate);

        int databaseSizeBeforeUpdate = emailtemplateRepository.findAll().size();

        // Update the emailtemplate using partial update
        Emailtemplate partialUpdatedEmailtemplate = new Emailtemplate();
        partialUpdatedEmailtemplate.setId(emailtemplate.getId());

        partialUpdatedEmailtemplate
            .templatename(UPDATED_TEMPLATENAME)
            .subject(UPDATED_SUBJECT)
            .hyperlink(UPDATED_HYPERLINK)
            .datetime(UPDATED_DATETIME)
            .content(UPDATED_CONTENT);

        restEmailtemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmailtemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmailtemplate))
            )
            .andExpect(status().isOk());

        // Validate the Emailtemplate in the database
        List<Emailtemplate> emailtemplateList = emailtemplateRepository.findAll();
        assertThat(emailtemplateList).hasSize(databaseSizeBeforeUpdate);
        Emailtemplate testEmailtemplate = emailtemplateList.get(emailtemplateList.size() - 1);
        assertThat(testEmailtemplate.getTemplatename()).isEqualTo(UPDATED_TEMPLATENAME);
        assertThat(testEmailtemplate.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEmailtemplate.getHyperlink()).isEqualTo(UPDATED_HYPERLINK);
        assertThat(testEmailtemplate.getDatetime()).isEqualTo(UPDATED_DATETIME);
        assertThat(testEmailtemplate.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void patchNonExistingEmailtemplate() throws Exception {
        int databaseSizeBeforeUpdate = emailtemplateRepository.findAll().size();
        emailtemplate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailtemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, emailtemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emailtemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emailtemplate in the database
        List<Emailtemplate> emailtemplateList = emailtemplateRepository.findAll();
        assertThat(emailtemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmailtemplate() throws Exception {
        int databaseSizeBeforeUpdate = emailtemplateRepository.findAll().size();
        emailtemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailtemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emailtemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emailtemplate in the database
        List<Emailtemplate> emailtemplateList = emailtemplateRepository.findAll();
        assertThat(emailtemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmailtemplate() throws Exception {
        int databaseSizeBeforeUpdate = emailtemplateRepository.findAll().size();
        emailtemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailtemplateMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(emailtemplate))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emailtemplate in the database
        List<Emailtemplate> emailtemplateList = emailtemplateRepository.findAll();
        assertThat(emailtemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmailtemplate() throws Exception {
        // Initialize the database
        emailtemplateRepository.saveAndFlush(emailtemplate);

        int databaseSizeBeforeDelete = emailtemplateRepository.findAll().size();

        // Delete the emailtemplate
        restEmailtemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, emailtemplate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Emailtemplate> emailtemplateList = emailtemplateRepository.findAll();
        assertThat(emailtemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
