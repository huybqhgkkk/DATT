package com.aladin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aladin.IntegrationTest;
import com.aladin.domain.Candidate;
import com.aladin.repository.CandidateRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link CandidateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CandidateResourceIT {

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTHDAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_PREFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_PREFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_EDUCATION = "AAAAAAAAAA";
    private static final String UPDATED_EDUCATION = "BBBBBBBBBB";

    private static final String DEFAULT_EXPERIENCE = "AAAAAAAAAA";
    private static final String UPDATED_EXPERIENCE = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET = "AAAAAAAAAA";
    private static final String UPDATED_TARGET = "BBBBBBBBBB";

    private static final String DEFAULT_RALATIONSHIP = "AAAAAAAAAA";
    private static final String UPDATED_RALATIONSHIP = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIMEREGISTER = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMEREGISTER = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FULLNAME = "AAAAAAAAAA";
    private static final String UPDATED_FULLNAME = "BBBBBBBBBB";

    private static final String DEFAULT_SEX = "AAAAAAAAAA";
    private static final String UPDATED_SEX = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CV = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CV = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CV_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CV_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/candidates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCandidateMockMvc;

    private Candidate candidate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidate createEntity(EntityManager em) {
        Candidate candidate = new Candidate()
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .position(DEFAULT_POSITION)
            .birthday(DEFAULT_BIRTHDAY)
            .location(DEFAULT_LOCATION)
            .preference(DEFAULT_PREFERENCE)
            .education(DEFAULT_EDUCATION)
            .experience(DEFAULT_EXPERIENCE)
            .target(DEFAULT_TARGET)
            .ralationship(DEFAULT_RALATIONSHIP)
            .timeregister(DEFAULT_TIMEREGISTER)
            .fullname(DEFAULT_FULLNAME)
            .sex(DEFAULT_SEX)
            .cv(DEFAULT_CV)
            .cvContentType(DEFAULT_CV_CONTENT_TYPE);
        return candidate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidate createUpdatedEntity(EntityManager em) {
        Candidate candidate = new Candidate()
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .position(UPDATED_POSITION)
            .birthday(UPDATED_BIRTHDAY)
            .location(UPDATED_LOCATION)
            .preference(UPDATED_PREFERENCE)
            .education(UPDATED_EDUCATION)
            .experience(UPDATED_EXPERIENCE)
            .target(UPDATED_TARGET)
            .ralationship(UPDATED_RALATIONSHIP)
            .timeregister(UPDATED_TIMEREGISTER)
            .fullname(UPDATED_FULLNAME)
            .sex(UPDATED_SEX)
            .cv(UPDATED_CV)
            .cvContentType(UPDATED_CV_CONTENT_TYPE);
        return candidate;
    }

    @BeforeEach
    public void initTest() {
        candidate = createEntity(em);
    }

    @Test
    @Transactional
    void createCandidate() throws Exception {
        int databaseSizeBeforeCreate = candidateRepository.findAll().size();
        // Create the Candidate
        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidate)))
            .andExpect(status().isCreated());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeCreate + 1);
        Candidate testCandidate = candidateList.get(candidateList.size() - 1);
        assertThat(testCandidate.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCandidate.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCandidate.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testCandidate.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testCandidate.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testCandidate.getPreference()).isEqualTo(DEFAULT_PREFERENCE);
        assertThat(testCandidate.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testCandidate.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
        assertThat(testCandidate.getTarget()).isEqualTo(DEFAULT_TARGET);
        assertThat(testCandidate.getRalationship()).isEqualTo(DEFAULT_RALATIONSHIP);
        assertThat(testCandidate.getTimeregister()).isEqualTo(DEFAULT_TIMEREGISTER);
        assertThat(testCandidate.getFullname()).isEqualTo(DEFAULT_FULLNAME);
        assertThat(testCandidate.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testCandidate.getCv()).isEqualTo(DEFAULT_CV);
        assertThat(testCandidate.getCvContentType()).isEqualTo(DEFAULT_CV_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createCandidateWithExistingId() throws Exception {
        // Create the Candidate with an existing ID
        candidate.setId(1L);

        int databaseSizeBeforeCreate = candidateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidate)))
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setPhone(null);

        // Create the Candidate, which fails.

        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidate)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setEmail(null);

        // Create the Candidate, which fails.

        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidate)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setPosition(null);

        // Create the Candidate, which fails.

        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidate)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBirthdayIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setBirthday(null);

        // Create the Candidate, which fails.

        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidate)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setLocation(null);

        // Create the Candidate, which fails.

        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidate)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEducationIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setEducation(null);

        // Create the Candidate, which fails.

        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidate)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExperienceIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setExperience(null);

        // Create the Candidate, which fails.

        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidate)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCandidates() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList
        restCandidateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidate.getId().intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].preference").value(hasItem(DEFAULT_PREFERENCE)))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION)))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].target").value(hasItem(DEFAULT_TARGET)))
            .andExpect(jsonPath("$.[*].ralationship").value(hasItem(DEFAULT_RALATIONSHIP)))
            .andExpect(jsonPath("$.[*].timeregister").value(hasItem(DEFAULT_TIMEREGISTER.toString())))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX)))
            .andExpect(jsonPath("$.[*].cvContentType").value(hasItem(DEFAULT_CV_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cv").value(hasItem(Base64Utils.encodeToString(DEFAULT_CV))));
    }

    @Test
    @Transactional
    void getCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get the candidate
        restCandidateMockMvc
            .perform(get(ENTITY_API_URL_ID, candidate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(candidate.getId().intValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.preference").value(DEFAULT_PREFERENCE))
            .andExpect(jsonPath("$.education").value(DEFAULT_EDUCATION))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE))
            .andExpect(jsonPath("$.target").value(DEFAULT_TARGET))
            .andExpect(jsonPath("$.ralationship").value(DEFAULT_RALATIONSHIP))
            .andExpect(jsonPath("$.timeregister").value(DEFAULT_TIMEREGISTER.toString()))
            .andExpect(jsonPath("$.fullname").value(DEFAULT_FULLNAME))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX))
            .andExpect(jsonPath("$.cvContentType").value(DEFAULT_CV_CONTENT_TYPE))
            .andExpect(jsonPath("$.cv").value(Base64Utils.encodeToString(DEFAULT_CV)));
    }

    @Test
    @Transactional
    void getNonExistingCandidate() throws Exception {
        // Get the candidate
        restCandidateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

        // Update the candidate
        Candidate updatedCandidate = candidateRepository.findById(candidate.getId()).get();
        // Disconnect from session so that the updates on updatedCandidate are not directly saved in db
        em.detach(updatedCandidate);
        updatedCandidate
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .position(UPDATED_POSITION)
            .birthday(UPDATED_BIRTHDAY)
            .location(UPDATED_LOCATION)
            .preference(UPDATED_PREFERENCE)
            .education(UPDATED_EDUCATION)
            .experience(UPDATED_EXPERIENCE)
            .target(UPDATED_TARGET)
            .ralationship(UPDATED_RALATIONSHIP)
            .timeregister(UPDATED_TIMEREGISTER)
            .fullname(UPDATED_FULLNAME)
            .sex(UPDATED_SEX)
            .cv(UPDATED_CV)
            .cvContentType(UPDATED_CV_CONTENT_TYPE);

        restCandidateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCandidate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCandidate))
            )
            .andExpect(status().isOk());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
        Candidate testCandidate = candidateList.get(candidateList.size() - 1);
        assertThat(testCandidate.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCandidate.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCandidate.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testCandidate.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testCandidate.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCandidate.getPreference()).isEqualTo(UPDATED_PREFERENCE);
        assertThat(testCandidate.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testCandidate.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testCandidate.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testCandidate.getRalationship()).isEqualTo(UPDATED_RALATIONSHIP);
        assertThat(testCandidate.getTimeregister()).isEqualTo(UPDATED_TIMEREGISTER);
        assertThat(testCandidate.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testCandidate.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testCandidate.getCv()).isEqualTo(UPDATED_CV);
        assertThat(testCandidate.getCvContentType()).isEqualTo(UPDATED_CV_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, candidate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidate)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCandidateWithPatch() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

        // Update the candidate using partial update
        Candidate partialUpdatedCandidate = new Candidate();
        partialUpdatedCandidate.setId(candidate.getId());

        partialUpdatedCandidate
            .phone(UPDATED_PHONE)
            .birthday(UPDATED_BIRTHDAY)
            .location(UPDATED_LOCATION)
            .preference(UPDATED_PREFERENCE)
            .experience(UPDATED_EXPERIENCE)
            .fullname(UPDATED_FULLNAME)
            .sex(UPDATED_SEX);

        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCandidate))
            )
            .andExpect(status().isOk());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
        Candidate testCandidate = candidateList.get(candidateList.size() - 1);
        assertThat(testCandidate.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCandidate.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCandidate.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testCandidate.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testCandidate.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCandidate.getPreference()).isEqualTo(UPDATED_PREFERENCE);
        assertThat(testCandidate.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testCandidate.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testCandidate.getTarget()).isEqualTo(DEFAULT_TARGET);
        assertThat(testCandidate.getRalationship()).isEqualTo(DEFAULT_RALATIONSHIP);
        assertThat(testCandidate.getTimeregister()).isEqualTo(DEFAULT_TIMEREGISTER);
        assertThat(testCandidate.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testCandidate.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testCandidate.getCv()).isEqualTo(DEFAULT_CV);
        assertThat(testCandidate.getCvContentType()).isEqualTo(DEFAULT_CV_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateCandidateWithPatch() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

        // Update the candidate using partial update
        Candidate partialUpdatedCandidate = new Candidate();
        partialUpdatedCandidate.setId(candidate.getId());

        partialUpdatedCandidate
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .position(UPDATED_POSITION)
            .birthday(UPDATED_BIRTHDAY)
            .location(UPDATED_LOCATION)
            .preference(UPDATED_PREFERENCE)
            .education(UPDATED_EDUCATION)
            .experience(UPDATED_EXPERIENCE)
            .target(UPDATED_TARGET)
            .ralationship(UPDATED_RALATIONSHIP)
            .timeregister(UPDATED_TIMEREGISTER)
            .fullname(UPDATED_FULLNAME)
            .sex(UPDATED_SEX)
            .cv(UPDATED_CV)
            .cvContentType(UPDATED_CV_CONTENT_TYPE);

        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCandidate))
            )
            .andExpect(status().isOk());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
        Candidate testCandidate = candidateList.get(candidateList.size() - 1);
        assertThat(testCandidate.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCandidate.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCandidate.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testCandidate.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testCandidate.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCandidate.getPreference()).isEqualTo(UPDATED_PREFERENCE);
        assertThat(testCandidate.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testCandidate.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testCandidate.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testCandidate.getRalationship()).isEqualTo(UPDATED_RALATIONSHIP);
        assertThat(testCandidate.getTimeregister()).isEqualTo(UPDATED_TIMEREGISTER);
        assertThat(testCandidate.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testCandidate.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testCandidate.getCv()).isEqualTo(UPDATED_CV);
        assertThat(testCandidate.getCvContentType()).isEqualTo(UPDATED_CV_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, candidate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(candidate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(candidate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(candidate))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        int databaseSizeBeforeDelete = candidateRepository.findAll().size();

        // Delete the candidate
        restCandidateMockMvc
            .perform(delete(ENTITY_API_URL_ID, candidate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
