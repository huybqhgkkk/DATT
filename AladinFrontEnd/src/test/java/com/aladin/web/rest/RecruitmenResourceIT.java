package com.aladin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aladin.IntegrationTest;
import com.aladin.domain.Recruitmen;
import com.aladin.repository.RecruitmenRepository;
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
 * Integration tests for the {@link RecruitmenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RecruitmenResourceIT {

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_REQUIRE = "AAAAAAAAAA";
    private static final String UPDATED_REQUIRE = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFIT = "AAAAAAAAAA";
    private static final String UPDATED_BENEFIT = "BBBBBBBBBB";

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final String DEFAULT_JOB = "AAAAAAAAAA";
    private static final String UPDATED_JOB = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_DEADLINE = "AAAAAAAAAA";
    private static final String UPDATED_DEADLINE = "BBBBBBBBBB";

    private static final String DEFAULT_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_LEVEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/recruitments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RecruitmenRepository recruitmenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecruitmenMockMvc;

    private Recruitmen recruitmen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recruitmen createEntity(EntityManager em) {
        Recruitmen recruitmen = new Recruitmen()
            .position(DEFAULT_POSITION)
            .description(DEFAULT_DESCRIPTION)
            .require(DEFAULT_REQUIRE)
            .benefit(DEFAULT_BENEFIT)
            .amount(DEFAULT_AMOUNT)
            .job(DEFAULT_JOB)
            .location(DEFAULT_LOCATION)
            .duration(DEFAULT_DEADLINE)
            .level(DEFAULT_LEVEL);
        return recruitmen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recruitmen createUpdatedEntity(EntityManager em) {
        Recruitmen recruitmen = new Recruitmen()
            .position(UPDATED_POSITION)
            .description(UPDATED_DESCRIPTION)
            .require(UPDATED_REQUIRE)
            .benefit(UPDATED_BENEFIT)
            .amount(UPDATED_AMOUNT)
            .job(UPDATED_JOB)
            .location(UPDATED_LOCATION)
            .duration(UPDATED_DEADLINE)
            .level(UPDATED_LEVEL);
        return recruitmen;
    }

    @BeforeEach
    public void initTest() {
        recruitmen = createEntity(em);
    }

    @Test
    @Transactional
    void createRecruitmen() throws Exception {
        int databaseSizeBeforeCreate = recruitmenRepository.findAll().size();
        // Create the Recruitmen
        restRecruitmenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recruitmen)))
            .andExpect(status().isCreated());

        // Validate the Recruitmen in the database
        List<Recruitmen> recruitmenList = recruitmenRepository.findAll();
        assertThat(recruitmenList).hasSize(databaseSizeBeforeCreate + 1);
        Recruitmen testRecruitmen = recruitmenList.get(recruitmenList.size() - 1);
        assertThat(testRecruitmen.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testRecruitmen.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRecruitmen.getRequire()).isEqualTo(DEFAULT_REQUIRE);
        assertThat(testRecruitmen.getBenefit()).isEqualTo(DEFAULT_BENEFIT);
        assertThat(testRecruitmen.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testRecruitmen.getJob()).isEqualTo(DEFAULT_JOB);
        assertThat(testRecruitmen.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testRecruitmen.getDeadline()).isEqualTo(DEFAULT_DEADLINE);
        assertThat(testRecruitmen.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    void createRecruitmenWithExistingId() throws Exception {
        // Create the Recruitmen with an existing ID
        recruitmen.setId(1L);

        int databaseSizeBeforeCreate = recruitmenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecruitmenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recruitmen)))
            .andExpect(status().isBadRequest());

        // Validate the Recruitmen in the database
        List<Recruitmen> recruitmenList = recruitmenRepository.findAll();
        assertThat(recruitmenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = recruitmenRepository.findAll().size();
        // set the field null
        recruitmen.setPosition(null);

        // Create the Recruitmen, which fails.

        restRecruitmenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recruitmen)))
            .andExpect(status().isBadRequest());

        List<Recruitmen> recruitmenList = recruitmenRepository.findAll();
        assertThat(recruitmenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = recruitmenRepository.findAll().size();
        // set the field null
        recruitmen.setDescription(null);

        // Create the Recruitmen, which fails.

        restRecruitmenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recruitmen)))
            .andExpect(status().isBadRequest());

        List<Recruitmen> recruitmenList = recruitmenRepository.findAll();
        assertThat(recruitmenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRequireIsRequired() throws Exception {
        int databaseSizeBeforeTest = recruitmenRepository.findAll().size();
        // set the field null
        recruitmen.setRequire(null);

        // Create the Recruitmen, which fails.

        restRecruitmenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recruitmen)))
            .andExpect(status().isBadRequest());

        List<Recruitmen> recruitmenList = recruitmenRepository.findAll();
        assertThat(recruitmenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBenefitIsRequired() throws Exception {
        int databaseSizeBeforeTest = recruitmenRepository.findAll().size();
        // set the field null
        recruitmen.setBenefit(null);

        // Create the Recruitmen, which fails.

        restRecruitmenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recruitmen)))
            .andExpect(status().isBadRequest());

        List<Recruitmen> recruitmenList = recruitmenRepository.findAll();
        assertThat(recruitmenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = recruitmenRepository.findAll().size();
        // set the field null
        recruitmen.setAmount(null);

        // Create the Recruitmen, which fails.

        restRecruitmenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recruitmen)))
            .andExpect(status().isBadRequest());

        List<Recruitmen> recruitmenList = recruitmenRepository.findAll();
        assertThat(recruitmenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRecruitmen() throws Exception {
        // Initialize the database
        recruitmenRepository.saveAndFlush(recruitmen);

        // Get all the recruitmenList
        restRecruitmenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recruitmen.getId().intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].require").value(hasItem(DEFAULT_REQUIRE)))
            .andExpect(jsonPath("$.[*].benefit").value(hasItem(DEFAULT_BENEFIT)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].job").value(hasItem(DEFAULT_JOB)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DEADLINE)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)));
    }

    @Test
    @Transactional
    void getRecruitmen() throws Exception {
        // Initialize the database
        recruitmenRepository.saveAndFlush(recruitmen);

        // Get the recruitmen
        restRecruitmenMockMvc
            .perform(get(ENTITY_API_URL_ID, recruitmen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recruitmen.getId().intValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.require").value(DEFAULT_REQUIRE))
            .andExpect(jsonPath("$.benefit").value(DEFAULT_BENEFIT))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.job").value(DEFAULT_JOB))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DEADLINE))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL));
    }

    @Test
    @Transactional
    void getNonExistingRecruitmen() throws Exception {
        // Get the recruitmen
        restRecruitmenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRecruitmen() throws Exception {
        // Initialize the database
        recruitmenRepository.saveAndFlush(recruitmen);

        int databaseSizeBeforeUpdate = recruitmenRepository.findAll().size();

        // Update the recruitmen
        Recruitmen updatedRecruitmen = recruitmenRepository.findById(recruitmen.getId()).get();
        // Disconnect from session so that the updates on updatedRecruitmen are not directly saved in db
        em.detach(updatedRecruitmen);
        updatedRecruitmen
            .position(UPDATED_POSITION)
            .description(UPDATED_DESCRIPTION)
            .require(UPDATED_REQUIRE)
            .benefit(UPDATED_BENEFIT)
            .amount(UPDATED_AMOUNT)
            .job(UPDATED_JOB)
            .location(UPDATED_LOCATION)
            .duration(UPDATED_DEADLINE)
            .level(UPDATED_LEVEL);

        restRecruitmenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRecruitmen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRecruitmen))
            )
            .andExpect(status().isOk());

        // Validate the Recruitmen in the database
        List<Recruitmen> recruitmenList = recruitmenRepository.findAll();
        assertThat(recruitmenList).hasSize(databaseSizeBeforeUpdate);
        Recruitmen testRecruitmen = recruitmenList.get(recruitmenList.size() - 1);
        assertThat(testRecruitmen.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testRecruitmen.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRecruitmen.getRequire()).isEqualTo(UPDATED_REQUIRE);
        assertThat(testRecruitmen.getBenefit()).isEqualTo(UPDATED_BENEFIT);
        assertThat(testRecruitmen.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRecruitmen.getJob()).isEqualTo(UPDATED_JOB);
        assertThat(testRecruitmen.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testRecruitmen.getDeadline()).isEqualTo(UPDATED_DEADLINE);
        assertThat(testRecruitmen.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    void putNonExistingRecruitmen() throws Exception {
        int databaseSizeBeforeUpdate = recruitmenRepository.findAll().size();
        recruitmen.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecruitmenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recruitmen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recruitmen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recruitmen in the database
        List<Recruitmen> recruitmenList = recruitmenRepository.findAll();
        assertThat(recruitmenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecruitmen() throws Exception {
        int databaseSizeBeforeUpdate = recruitmenRepository.findAll().size();
        recruitmen.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecruitmenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recruitmen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recruitmen in the database
        List<Recruitmen> recruitmenList = recruitmenRepository.findAll();
        assertThat(recruitmenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecruitmen() throws Exception {
        int databaseSizeBeforeUpdate = recruitmenRepository.findAll().size();
        recruitmen.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecruitmenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recruitmen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recruitmen in the database
        List<Recruitmen> recruitmenList = recruitmenRepository.findAll();
        assertThat(recruitmenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecruitmenWithPatch() throws Exception {
        // Initialize the database
        recruitmenRepository.saveAndFlush(recruitmen);

        int databaseSizeBeforeUpdate = recruitmenRepository.findAll().size();

        // Update the recruitmen using partial update
        Recruitmen partialUpdatedRecruitmen = new Recruitmen();
        partialUpdatedRecruitmen.setId(recruitmen.getId());

        partialUpdatedRecruitmen
            .position(UPDATED_POSITION)
            .require(UPDATED_REQUIRE)
            .amount(UPDATED_AMOUNT)
            .job(UPDATED_JOB)
            .location(UPDATED_LOCATION)
            .duration(UPDATED_DEADLINE);

        restRecruitmenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecruitmen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecruitmen))
            )
            .andExpect(status().isOk());

        // Validate the Recruitmen in the database
        List<Recruitmen> recruitmenList = recruitmenRepository.findAll();
        assertThat(recruitmenList).hasSize(databaseSizeBeforeUpdate);
        Recruitmen testRecruitmen = recruitmenList.get(recruitmenList.size() - 1);
        assertThat(testRecruitmen.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testRecruitmen.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRecruitmen.getRequire()).isEqualTo(UPDATED_REQUIRE);
        assertThat(testRecruitmen.getBenefit()).isEqualTo(DEFAULT_BENEFIT);
        assertThat(testRecruitmen.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRecruitmen.getJob()).isEqualTo(UPDATED_JOB);
        assertThat(testRecruitmen.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testRecruitmen.getDeadline()).isEqualTo(UPDATED_DEADLINE);
        assertThat(testRecruitmen.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    void fullUpdateRecruitmenWithPatch() throws Exception {
        // Initialize the database
        recruitmenRepository.saveAndFlush(recruitmen);

        int databaseSizeBeforeUpdate = recruitmenRepository.findAll().size();

        // Update the recruitmen using partial update
        Recruitmen partialUpdatedRecruitmen = new Recruitmen();
        partialUpdatedRecruitmen.setId(recruitmen.getId());

        partialUpdatedRecruitmen
            .position(UPDATED_POSITION)
            .description(UPDATED_DESCRIPTION)
            .require(UPDATED_REQUIRE)
            .benefit(UPDATED_BENEFIT)
            .amount(UPDATED_AMOUNT)
            .job(UPDATED_JOB)
            .location(UPDATED_LOCATION)
            .duration(UPDATED_DEADLINE)
            .level(UPDATED_LEVEL);

        restRecruitmenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecruitmen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecruitmen))
            )
            .andExpect(status().isOk());

        // Validate the Recruitmen in the database
        List<Recruitmen> recruitmenList = recruitmenRepository.findAll();
        assertThat(recruitmenList).hasSize(databaseSizeBeforeUpdate);
        Recruitmen testRecruitmen = recruitmenList.get(recruitmenList.size() - 1);
        assertThat(testRecruitmen.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testRecruitmen.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRecruitmen.getRequire()).isEqualTo(UPDATED_REQUIRE);
        assertThat(testRecruitmen.getBenefit()).isEqualTo(UPDATED_BENEFIT);
        assertThat(testRecruitmen.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRecruitmen.getJob()).isEqualTo(UPDATED_JOB);
        assertThat(testRecruitmen.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testRecruitmen.getDeadline()).isEqualTo(UPDATED_DEADLINE);
        assertThat(testRecruitmen.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    void patchNonExistingRecruitmen() throws Exception {
        int databaseSizeBeforeUpdate = recruitmenRepository.findAll().size();
        recruitmen.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecruitmenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recruitmen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recruitmen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recruitmen in the database
        List<Recruitmen> recruitmenList = recruitmenRepository.findAll();
        assertThat(recruitmenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecruitmen() throws Exception {
        int databaseSizeBeforeUpdate = recruitmenRepository.findAll().size();
        recruitmen.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecruitmenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recruitmen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recruitmen in the database
        List<Recruitmen> recruitmenList = recruitmenRepository.findAll();
        assertThat(recruitmenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecruitmen() throws Exception {
        int databaseSizeBeforeUpdate = recruitmenRepository.findAll().size();
        recruitmen.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecruitmenMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(recruitmen))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recruitmen in the database
        List<Recruitmen> recruitmenList = recruitmenRepository.findAll();
        assertThat(recruitmenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecruitmen() throws Exception {
        // Initialize the database
        recruitmenRepository.saveAndFlush(recruitmen);

        int databaseSizeBeforeDelete = recruitmenRepository.findAll().size();

        // Delete the recruitmen
        restRecruitmenMockMvc
            .perform(delete(ENTITY_API_URL_ID, recruitmen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recruitmen> recruitmenList = recruitmenRepository.findAll();
        assertThat(recruitmenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
