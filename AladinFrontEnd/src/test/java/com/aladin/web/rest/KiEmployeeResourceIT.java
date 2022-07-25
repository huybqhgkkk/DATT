package com.aladin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aladin.IntegrationTest;
import com.aladin.domain.Employee;
import com.aladin.domain.KiEmployee;
import com.aladin.repository.KiEmployeeRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link KiEmployeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KiEmployeeResourceIT {

    private static final LocalDate DEFAULT_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_WORK_QUANTITY = 0.1F;
    private static final Float UPDATED_WORK_QUANTITY = 1F;

    private static final String DEFAULT_WORK_QUANTITY_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_WORK_QUANTITY_COMMENT = "BBBBBBBBBB";

    private static final Float DEFAULT_WORK_QUALITY = 0.1F;
    private static final Float UPDATED_WORK_QUALITY = 1F;

    private static final String DEFAULT_WORK_QUALITY_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_WORK_QUALITY_COMMENT = "BBBBBBBBBB";

    private static final Float DEFAULT_WORK_PROGRESS = 0.1F;
    private static final Float UPDATED_WORK_PROGRESS = 1F;

    private static final String DEFAULT_WORK_PROGRESS_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_WORK_PROGRESS_COMMENT = "BBBBBBBBBB";

    private static final Float DEFAULT_WORK_ATTITUDE = 0.1F;
    private static final Float UPDATED_WORK_ATTITUDE = 1F;

    private static final String DEFAULT_WORK_ATTITUDE_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_WORK_ATTITUDE_COMMENT = "BBBBBBBBBB";

    private static final Float DEFAULT_WORK_DISCIPLINE = 0.1F;
    private static final Float UPDATED_WORK_DISCIPLINE = 1F;

    private static final String DEFAULT_WORK_DISCIPLINE_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_WORK_DISCIPLINE_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_ASSIGNED_WORK = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNED_WORK = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_WORK = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_WORK = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLETED_WORK = "AAAAAAAAAA";
    private static final String UPDATED_COMPLETED_WORK = "BBBBBBBBBB";

    private static final String DEFAULT_UNCOMPLETED_WORK = "AAAAAAAAAA";
    private static final String UPDATED_UNCOMPLETED_WORK = "BBBBBBBBBB";

    private static final String DEFAULT_FAVOURITE_WORK = "AAAAAAAAAA";
    private static final String UPDATED_FAVOURITE_WORK = "BBBBBBBBBB";

    private static final String DEFAULT_UNFAVOURITE_WORK = "AAAAAAAAAA";
    private static final String UPDATED_UNFAVOURITE_WORK = "BBBBBBBBBB";

    private static final Float DEFAULT_EMPLOYEE_KI_POINT = 0.1F;
    private static final Float UPDATED_EMPLOYEE_KI_POINT = 1F;

    private static final Float DEFAULT_LEADER_KI_POINT = 0.1F;
    private static final Float UPDATED_LEADER_KI_POINT = 1F;

    private static final String DEFAULT_LEADER_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_LEADER_COMMENT = "BBBBBBBBBB";

    private static final Float DEFAULT_BOSS_KI_POINT = 0.1F;
    private static final Float UPDATED_BOSS_KI_POINT = 1F;

    private static final String DEFAULT_BOSS_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_BOSS_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ki-employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KiEmployeeRepository kiEmployeeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKiEmployeeMockMvc;

    private KiEmployee kiEmployee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KiEmployee createEntity(EntityManager em) {
        KiEmployee kiEmployee = new KiEmployee()
            .date_time(DEFAULT_DATE_TIME)
            .work_quantity(DEFAULT_WORK_QUANTITY)
            .work_quantity_comment(DEFAULT_WORK_QUANTITY_COMMENT)
            .work_quality(DEFAULT_WORK_QUALITY)
            .work_quality_comment(DEFAULT_WORK_QUALITY_COMMENT)
            .work_progress(DEFAULT_WORK_PROGRESS)
            .work_progress_comment(DEFAULT_WORK_PROGRESS_COMMENT)
            .work_attitude(DEFAULT_WORK_ATTITUDE)
            .work_attitude_comment(DEFAULT_WORK_ATTITUDE_COMMENT)
            .work_discipline(DEFAULT_WORK_DISCIPLINE)
            .work_discipline_comment(DEFAULT_WORK_DISCIPLINE_COMMENT)
            .assigned_work(DEFAULT_ASSIGNED_WORK)
            .other_work(DEFAULT_OTHER_WORK)
            .completed_work(DEFAULT_COMPLETED_WORK)
            .uncompleted_work(DEFAULT_UNCOMPLETED_WORK)
            .favourite_work(DEFAULT_FAVOURITE_WORK)
            .unfavourite_work(DEFAULT_UNFAVOURITE_WORK)
            .employee_ki_point(DEFAULT_EMPLOYEE_KI_POINT)
            .leader_ki_point(DEFAULT_LEADER_KI_POINT)
            .leader_comment(DEFAULT_LEADER_COMMENT)
            .boss_ki_point(DEFAULT_BOSS_KI_POINT)
            .boss_comment(DEFAULT_BOSS_COMMENT)
            .status(DEFAULT_STATUS);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        kiEmployee.setEmployee(employee);
        return kiEmployee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KiEmployee createUpdatedEntity(EntityManager em) {
        KiEmployee kiEmployee = new KiEmployee()
            .date_time(UPDATED_DATE_TIME)
            .work_quantity(UPDATED_WORK_QUANTITY)
            .work_quantity_comment(UPDATED_WORK_QUANTITY_COMMENT)
            .work_quality(UPDATED_WORK_QUALITY)
            .work_quality_comment(UPDATED_WORK_QUALITY_COMMENT)
            .work_progress(UPDATED_WORK_PROGRESS)
            .work_progress_comment(UPDATED_WORK_PROGRESS_COMMENT)
            .work_attitude(UPDATED_WORK_ATTITUDE)
            .work_attitude_comment(UPDATED_WORK_ATTITUDE_COMMENT)
            .work_discipline(UPDATED_WORK_DISCIPLINE)
            .work_discipline_comment(UPDATED_WORK_DISCIPLINE_COMMENT)
            .assigned_work(UPDATED_ASSIGNED_WORK)
            .other_work(UPDATED_OTHER_WORK)
            .completed_work(UPDATED_COMPLETED_WORK)
            .uncompleted_work(UPDATED_UNCOMPLETED_WORK)
            .favourite_work(UPDATED_FAVOURITE_WORK)
            .unfavourite_work(UPDATED_UNFAVOURITE_WORK)
            .employee_ki_point(UPDATED_EMPLOYEE_KI_POINT)
            .leader_ki_point(UPDATED_LEADER_KI_POINT)
            .leader_comment(UPDATED_LEADER_COMMENT)
            .boss_ki_point(UPDATED_BOSS_KI_POINT)
            .boss_comment(UPDATED_BOSS_COMMENT)
            .status(UPDATED_STATUS);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createUpdatedEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        kiEmployee.setEmployee(employee);
        return kiEmployee;
    }

    @BeforeEach
    public void initTest() {
        kiEmployee = createEntity(em);
    }

    @Test
    @Transactional
    void createKiEmployee() throws Exception {
        int databaseSizeBeforeCreate = kiEmployeeRepository.findAll().size();
        // Create the KiEmployee
        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isCreated());

        // Validate the KiEmployee in the database
        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeCreate + 1);
        KiEmployee testKiEmployee = kiEmployeeList.get(kiEmployeeList.size() - 1);
        assertThat(testKiEmployee.getDate_time()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testKiEmployee.getWork_quantity()).isEqualTo(DEFAULT_WORK_QUANTITY);
        assertThat(testKiEmployee.getWork_quantity_comment()).isEqualTo(DEFAULT_WORK_QUANTITY_COMMENT);
        assertThat(testKiEmployee.getWork_quality()).isEqualTo(DEFAULT_WORK_QUALITY);
        assertThat(testKiEmployee.getWork_quality_comment()).isEqualTo(DEFAULT_WORK_QUALITY_COMMENT);
        assertThat(testKiEmployee.getWork_progress()).isEqualTo(DEFAULT_WORK_PROGRESS);
        assertThat(testKiEmployee.getWork_progress_comment()).isEqualTo(DEFAULT_WORK_PROGRESS_COMMENT);
        assertThat(testKiEmployee.getWork_attitude()).isEqualTo(DEFAULT_WORK_ATTITUDE);
        assertThat(testKiEmployee.getWork_attitude_comment()).isEqualTo(DEFAULT_WORK_ATTITUDE_COMMENT);
        assertThat(testKiEmployee.getWork_discipline()).isEqualTo(DEFAULT_WORK_DISCIPLINE);
        assertThat(testKiEmployee.getWork_discipline_comment()).isEqualTo(DEFAULT_WORK_DISCIPLINE_COMMENT);
        assertThat(testKiEmployee.getAssigned_work()).isEqualTo(DEFAULT_ASSIGNED_WORK);
        assertThat(testKiEmployee.getOther_work()).isEqualTo(DEFAULT_OTHER_WORK);
        assertThat(testKiEmployee.getCompleted_work()).isEqualTo(DEFAULT_COMPLETED_WORK);
        assertThat(testKiEmployee.getUncompleted_work()).isEqualTo(DEFAULT_UNCOMPLETED_WORK);
        assertThat(testKiEmployee.getFavourite_work()).isEqualTo(DEFAULT_FAVOURITE_WORK);
        assertThat(testKiEmployee.getUnfavourite_work()).isEqualTo(DEFAULT_UNFAVOURITE_WORK);
        assertThat(testKiEmployee.getEmployee_ki_point()).isEqualTo(DEFAULT_EMPLOYEE_KI_POINT);
        assertThat(testKiEmployee.getLeader_ki_point()).isEqualTo(DEFAULT_LEADER_KI_POINT);
        assertThat(testKiEmployee.getLeader_comment()).isEqualTo(DEFAULT_LEADER_COMMENT);
        assertThat(testKiEmployee.getBoss_ki_point()).isEqualTo(DEFAULT_BOSS_KI_POINT);
        assertThat(testKiEmployee.getBoss_comment()).isEqualTo(DEFAULT_BOSS_COMMENT);
        assertThat(testKiEmployee.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createKiEmployeeWithExistingId() throws Exception {
        // Create the KiEmployee with an existing ID
        kiEmployee.setId(1L);

        int databaseSizeBeforeCreate = kiEmployeeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        // Validate the KiEmployee in the database
        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDate_timeIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setDate_time(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWork_quantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setWork_quantity(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWork_quantity_commentIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setWork_quantity_comment(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWork_qualityIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setWork_quality(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWork_quality_commentIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setWork_quality_comment(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWork_progressIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setWork_progress(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWork_progress_commentIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setWork_progress_comment(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWork_attitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setWork_attitude(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWork_attitude_commentIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setWork_attitude_comment(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWork_disciplineIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setWork_discipline(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWork_discipline_commentIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setWork_discipline_comment(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAssigned_workIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setAssigned_work(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOther_workIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setOther_work(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompleted_workIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setCompleted_work(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFavourite_workIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setFavourite_work(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmployee_ki_pointIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setEmployee_ki_point(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLeader_ki_pointIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setLeader_ki_point(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLeader_commentIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setLeader_comment(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBoss_ki_pointIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setBoss_ki_point(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBoss_commentIsRequired() throws Exception {
        int databaseSizeBeforeTest = kiEmployeeRepository.findAll().size();
        // set the field null
        kiEmployee.setBoss_comment(null);

        // Create the KiEmployee, which fails.

        restKiEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isBadRequest());

        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllKiEmployees() throws Exception {
        // Initialize the database
        kiEmployeeRepository.saveAndFlush(kiEmployee);

        // Get all the kiEmployeeList
        restKiEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kiEmployee.getId().intValue())))
            .andExpect(jsonPath("$.[*].date_time").value(hasItem(DEFAULT_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].work_quantity").value(hasItem(DEFAULT_WORK_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].work_quantity_comment").value(hasItem(DEFAULT_WORK_QUANTITY_COMMENT)))
            .andExpect(jsonPath("$.[*].work_quality").value(hasItem(DEFAULT_WORK_QUALITY.doubleValue())))
            .andExpect(jsonPath("$.[*].work_quality_comment").value(hasItem(DEFAULT_WORK_QUALITY_COMMENT)))
            .andExpect(jsonPath("$.[*].work_progress").value(hasItem(DEFAULT_WORK_PROGRESS.doubleValue())))
            .andExpect(jsonPath("$.[*].work_progress_comment").value(hasItem(DEFAULT_WORK_PROGRESS_COMMENT)))
            .andExpect(jsonPath("$.[*].work_attitude").value(hasItem(DEFAULT_WORK_ATTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].work_attitude_comment").value(hasItem(DEFAULT_WORK_ATTITUDE_COMMENT)))
            .andExpect(jsonPath("$.[*].work_discipline").value(hasItem(DEFAULT_WORK_DISCIPLINE.doubleValue())))
            .andExpect(jsonPath("$.[*].work_discipline_comment").value(hasItem(DEFAULT_WORK_DISCIPLINE_COMMENT)))
            .andExpect(jsonPath("$.[*].assigned_work").value(hasItem(DEFAULT_ASSIGNED_WORK)))
            .andExpect(jsonPath("$.[*].other_work").value(hasItem(DEFAULT_OTHER_WORK)))
            .andExpect(jsonPath("$.[*].completed_work").value(hasItem(DEFAULT_COMPLETED_WORK)))
            .andExpect(jsonPath("$.[*].uncompleted_work").value(hasItem(DEFAULT_UNCOMPLETED_WORK)))
            .andExpect(jsonPath("$.[*].favourite_work").value(hasItem(DEFAULT_FAVOURITE_WORK)))
            .andExpect(jsonPath("$.[*].unfavourite_work").value(hasItem(DEFAULT_UNFAVOURITE_WORK)))
            .andExpect(jsonPath("$.[*].employee_ki_point").value(hasItem(DEFAULT_EMPLOYEE_KI_POINT.doubleValue())))
            .andExpect(jsonPath("$.[*].leader_ki_point").value(hasItem(DEFAULT_LEADER_KI_POINT.doubleValue())))
            .andExpect(jsonPath("$.[*].leader_comment").value(hasItem(DEFAULT_LEADER_COMMENT)))
            .andExpect(jsonPath("$.[*].boss_ki_point").value(hasItem(DEFAULT_BOSS_KI_POINT.doubleValue())))
            .andExpect(jsonPath("$.[*].boss_comment").value(hasItem(DEFAULT_BOSS_COMMENT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getKiEmployee() throws Exception {
        // Initialize the database
        kiEmployeeRepository.saveAndFlush(kiEmployee);

        // Get the kiEmployee
        restKiEmployeeMockMvc
            .perform(get(ENTITY_API_URL_ID, kiEmployee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kiEmployee.getId().intValue()))
            .andExpect(jsonPath("$.date_time").value(DEFAULT_DATE_TIME.toString()))
            .andExpect(jsonPath("$.work_quantity").value(DEFAULT_WORK_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.work_quantity_comment").value(DEFAULT_WORK_QUANTITY_COMMENT))
            .andExpect(jsonPath("$.work_quality").value(DEFAULT_WORK_QUALITY.doubleValue()))
            .andExpect(jsonPath("$.work_quality_comment").value(DEFAULT_WORK_QUALITY_COMMENT))
            .andExpect(jsonPath("$.work_progress").value(DEFAULT_WORK_PROGRESS.doubleValue()))
            .andExpect(jsonPath("$.work_progress_comment").value(DEFAULT_WORK_PROGRESS_COMMENT))
            .andExpect(jsonPath("$.work_attitude").value(DEFAULT_WORK_ATTITUDE.doubleValue()))
            .andExpect(jsonPath("$.work_attitude_comment").value(DEFAULT_WORK_ATTITUDE_COMMENT))
            .andExpect(jsonPath("$.work_discipline").value(DEFAULT_WORK_DISCIPLINE.doubleValue()))
            .andExpect(jsonPath("$.work_discipline_comment").value(DEFAULT_WORK_DISCIPLINE_COMMENT))
            .andExpect(jsonPath("$.assigned_work").value(DEFAULT_ASSIGNED_WORK))
            .andExpect(jsonPath("$.other_work").value(DEFAULT_OTHER_WORK))
            .andExpect(jsonPath("$.completed_work").value(DEFAULT_COMPLETED_WORK))
            .andExpect(jsonPath("$.uncompleted_work").value(DEFAULT_UNCOMPLETED_WORK))
            .andExpect(jsonPath("$.favourite_work").value(DEFAULT_FAVOURITE_WORK))
            .andExpect(jsonPath("$.unfavourite_work").value(DEFAULT_UNFAVOURITE_WORK))
            .andExpect(jsonPath("$.employee_ki_point").value(DEFAULT_EMPLOYEE_KI_POINT.doubleValue()))
            .andExpect(jsonPath("$.leader_ki_point").value(DEFAULT_LEADER_KI_POINT.doubleValue()))
            .andExpect(jsonPath("$.leader_comment").value(DEFAULT_LEADER_COMMENT))
            .andExpect(jsonPath("$.boss_ki_point").value(DEFAULT_BOSS_KI_POINT.doubleValue()))
            .andExpect(jsonPath("$.boss_comment").value(DEFAULT_BOSS_COMMENT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingKiEmployee() throws Exception {
        // Get the kiEmployee
        restKiEmployeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewKiEmployee() throws Exception {
        // Initialize the database
        kiEmployeeRepository.saveAndFlush(kiEmployee);

        int databaseSizeBeforeUpdate = kiEmployeeRepository.findAll().size();

        // Update the kiEmployee
        KiEmployee updatedKiEmployee = kiEmployeeRepository.findById(kiEmployee.getId()).get();
        // Disconnect from session so that the updates on updatedKiEmployee are not directly saved in db
        em.detach(updatedKiEmployee);
        updatedKiEmployee
            .date_time(UPDATED_DATE_TIME)
            .work_quantity(UPDATED_WORK_QUANTITY)
            .work_quantity_comment(UPDATED_WORK_QUANTITY_COMMENT)
            .work_quality(UPDATED_WORK_QUALITY)
            .work_quality_comment(UPDATED_WORK_QUALITY_COMMENT)
            .work_progress(UPDATED_WORK_PROGRESS)
            .work_progress_comment(UPDATED_WORK_PROGRESS_COMMENT)
            .work_attitude(UPDATED_WORK_ATTITUDE)
            .work_attitude_comment(UPDATED_WORK_ATTITUDE_COMMENT)
            .work_discipline(UPDATED_WORK_DISCIPLINE)
            .work_discipline_comment(UPDATED_WORK_DISCIPLINE_COMMENT)
            .assigned_work(UPDATED_ASSIGNED_WORK)
            .other_work(UPDATED_OTHER_WORK)
            .completed_work(UPDATED_COMPLETED_WORK)
            .uncompleted_work(UPDATED_UNCOMPLETED_WORK)
            .favourite_work(UPDATED_FAVOURITE_WORK)
            .unfavourite_work(UPDATED_UNFAVOURITE_WORK)
            .employee_ki_point(UPDATED_EMPLOYEE_KI_POINT)
            .leader_ki_point(UPDATED_LEADER_KI_POINT)
            .leader_comment(UPDATED_LEADER_COMMENT)
            .boss_ki_point(UPDATED_BOSS_KI_POINT)
            .boss_comment(UPDATED_BOSS_COMMENT)
            .status(UPDATED_STATUS);

        restKiEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKiEmployee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKiEmployee))
            )
            .andExpect(status().isOk());

        // Validate the KiEmployee in the database
        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeUpdate);
        KiEmployee testKiEmployee = kiEmployeeList.get(kiEmployeeList.size() - 1);
        assertThat(testKiEmployee.getDate_time()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testKiEmployee.getWork_quantity()).isEqualTo(UPDATED_WORK_QUANTITY);
        assertThat(testKiEmployee.getWork_quantity_comment()).isEqualTo(UPDATED_WORK_QUANTITY_COMMENT);
        assertThat(testKiEmployee.getWork_quality()).isEqualTo(UPDATED_WORK_QUALITY);
        assertThat(testKiEmployee.getWork_quality_comment()).isEqualTo(UPDATED_WORK_QUALITY_COMMENT);
        assertThat(testKiEmployee.getWork_progress()).isEqualTo(UPDATED_WORK_PROGRESS);
        assertThat(testKiEmployee.getWork_progress_comment()).isEqualTo(UPDATED_WORK_PROGRESS_COMMENT);
        assertThat(testKiEmployee.getWork_attitude()).isEqualTo(UPDATED_WORK_ATTITUDE);
        assertThat(testKiEmployee.getWork_attitude_comment()).isEqualTo(UPDATED_WORK_ATTITUDE_COMMENT);
        assertThat(testKiEmployee.getWork_discipline()).isEqualTo(UPDATED_WORK_DISCIPLINE);
        assertThat(testKiEmployee.getWork_discipline_comment()).isEqualTo(UPDATED_WORK_DISCIPLINE_COMMENT);
        assertThat(testKiEmployee.getAssigned_work()).isEqualTo(UPDATED_ASSIGNED_WORK);
        assertThat(testKiEmployee.getOther_work()).isEqualTo(UPDATED_OTHER_WORK);
        assertThat(testKiEmployee.getCompleted_work()).isEqualTo(UPDATED_COMPLETED_WORK);
        assertThat(testKiEmployee.getUncompleted_work()).isEqualTo(UPDATED_UNCOMPLETED_WORK);
        assertThat(testKiEmployee.getFavourite_work()).isEqualTo(UPDATED_FAVOURITE_WORK);
        assertThat(testKiEmployee.getUnfavourite_work()).isEqualTo(UPDATED_UNFAVOURITE_WORK);
        assertThat(testKiEmployee.getEmployee_ki_point()).isEqualTo(UPDATED_EMPLOYEE_KI_POINT);
        assertThat(testKiEmployee.getLeader_ki_point()).isEqualTo(UPDATED_LEADER_KI_POINT);
        assertThat(testKiEmployee.getLeader_comment()).isEqualTo(UPDATED_LEADER_COMMENT);
        assertThat(testKiEmployee.getBoss_ki_point()).isEqualTo(UPDATED_BOSS_KI_POINT);
        assertThat(testKiEmployee.getBoss_comment()).isEqualTo(UPDATED_BOSS_COMMENT);
        assertThat(testKiEmployee.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingKiEmployee() throws Exception {
        int databaseSizeBeforeUpdate = kiEmployeeRepository.findAll().size();
        kiEmployee.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKiEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kiEmployee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kiEmployee))
            )
            .andExpect(status().isBadRequest());

        // Validate the KiEmployee in the database
        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKiEmployee() throws Exception {
        int databaseSizeBeforeUpdate = kiEmployeeRepository.findAll().size();
        kiEmployee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKiEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kiEmployee))
            )
            .andExpect(status().isBadRequest());

        // Validate the KiEmployee in the database
        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKiEmployee() throws Exception {
        int databaseSizeBeforeUpdate = kiEmployeeRepository.findAll().size();
        kiEmployee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKiEmployeeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kiEmployee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KiEmployee in the database
        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKiEmployeeWithPatch() throws Exception {
        // Initialize the database
        kiEmployeeRepository.saveAndFlush(kiEmployee);

        int databaseSizeBeforeUpdate = kiEmployeeRepository.findAll().size();

        // Update the kiEmployee using partial update
        KiEmployee partialUpdatedKiEmployee = new KiEmployee();
        partialUpdatedKiEmployee.setId(kiEmployee.getId());

        partialUpdatedKiEmployee
            .date_time(UPDATED_DATE_TIME)
            .work_quantity(UPDATED_WORK_QUANTITY)
            .work_quantity_comment(UPDATED_WORK_QUANTITY_COMMENT)
            .work_quality(UPDATED_WORK_QUALITY)
            .work_attitude(UPDATED_WORK_ATTITUDE)
            .work_attitude_comment(UPDATED_WORK_ATTITUDE_COMMENT)
            .completed_work(UPDATED_COMPLETED_WORK)
            .favourite_work(UPDATED_FAVOURITE_WORK)
            .unfavourite_work(UPDATED_UNFAVOURITE_WORK)
            .boss_ki_point(UPDATED_BOSS_KI_POINT);

        restKiEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKiEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKiEmployee))
            )
            .andExpect(status().isOk());

        // Validate the KiEmployee in the database
        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeUpdate);
        KiEmployee testKiEmployee = kiEmployeeList.get(kiEmployeeList.size() - 1);
        assertThat(testKiEmployee.getDate_time()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testKiEmployee.getWork_quantity()).isEqualTo(UPDATED_WORK_QUANTITY);
        assertThat(testKiEmployee.getWork_quantity_comment()).isEqualTo(UPDATED_WORK_QUANTITY_COMMENT);
        assertThat(testKiEmployee.getWork_quality()).isEqualTo(UPDATED_WORK_QUALITY);
        assertThat(testKiEmployee.getWork_quality_comment()).isEqualTo(DEFAULT_WORK_QUALITY_COMMENT);
        assertThat(testKiEmployee.getWork_progress()).isEqualTo(DEFAULT_WORK_PROGRESS);
        assertThat(testKiEmployee.getWork_progress_comment()).isEqualTo(DEFAULT_WORK_PROGRESS_COMMENT);
        assertThat(testKiEmployee.getWork_attitude()).isEqualTo(UPDATED_WORK_ATTITUDE);
        assertThat(testKiEmployee.getWork_attitude_comment()).isEqualTo(UPDATED_WORK_ATTITUDE_COMMENT);
        assertThat(testKiEmployee.getWork_discipline()).isEqualTo(DEFAULT_WORK_DISCIPLINE);
        assertThat(testKiEmployee.getWork_discipline_comment()).isEqualTo(DEFAULT_WORK_DISCIPLINE_COMMENT);
        assertThat(testKiEmployee.getAssigned_work()).isEqualTo(DEFAULT_ASSIGNED_WORK);
        assertThat(testKiEmployee.getOther_work()).isEqualTo(DEFAULT_OTHER_WORK);
        assertThat(testKiEmployee.getCompleted_work()).isEqualTo(UPDATED_COMPLETED_WORK);
        assertThat(testKiEmployee.getUncompleted_work()).isEqualTo(DEFAULT_UNCOMPLETED_WORK);
        assertThat(testKiEmployee.getFavourite_work()).isEqualTo(UPDATED_FAVOURITE_WORK);
        assertThat(testKiEmployee.getUnfavourite_work()).isEqualTo(UPDATED_UNFAVOURITE_WORK);
        assertThat(testKiEmployee.getEmployee_ki_point()).isEqualTo(DEFAULT_EMPLOYEE_KI_POINT);
        assertThat(testKiEmployee.getLeader_ki_point()).isEqualTo(DEFAULT_LEADER_KI_POINT);
        assertThat(testKiEmployee.getLeader_comment()).isEqualTo(DEFAULT_LEADER_COMMENT);
        assertThat(testKiEmployee.getBoss_ki_point()).isEqualTo(UPDATED_BOSS_KI_POINT);
        assertThat(testKiEmployee.getBoss_comment()).isEqualTo(DEFAULT_BOSS_COMMENT);
        assertThat(testKiEmployee.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateKiEmployeeWithPatch() throws Exception {
        // Initialize the database
        kiEmployeeRepository.saveAndFlush(kiEmployee);

        int databaseSizeBeforeUpdate = kiEmployeeRepository.findAll().size();

        // Update the kiEmployee using partial update
        KiEmployee partialUpdatedKiEmployee = new KiEmployee();
        partialUpdatedKiEmployee.setId(kiEmployee.getId());

        partialUpdatedKiEmployee
            .date_time(UPDATED_DATE_TIME)
            .work_quantity(UPDATED_WORK_QUANTITY)
            .work_quantity_comment(UPDATED_WORK_QUANTITY_COMMENT)
            .work_quality(UPDATED_WORK_QUALITY)
            .work_quality_comment(UPDATED_WORK_QUALITY_COMMENT)
            .work_progress(UPDATED_WORK_PROGRESS)
            .work_progress_comment(UPDATED_WORK_PROGRESS_COMMENT)
            .work_attitude(UPDATED_WORK_ATTITUDE)
            .work_attitude_comment(UPDATED_WORK_ATTITUDE_COMMENT)
            .work_discipline(UPDATED_WORK_DISCIPLINE)
            .work_discipline_comment(UPDATED_WORK_DISCIPLINE_COMMENT)
            .assigned_work(UPDATED_ASSIGNED_WORK)
            .other_work(UPDATED_OTHER_WORK)
            .completed_work(UPDATED_COMPLETED_WORK)
            .uncompleted_work(UPDATED_UNCOMPLETED_WORK)
            .favourite_work(UPDATED_FAVOURITE_WORK)
            .unfavourite_work(UPDATED_UNFAVOURITE_WORK)
            .employee_ki_point(UPDATED_EMPLOYEE_KI_POINT)
            .leader_ki_point(UPDATED_LEADER_KI_POINT)
            .leader_comment(UPDATED_LEADER_COMMENT)
            .boss_ki_point(UPDATED_BOSS_KI_POINT)
            .boss_comment(UPDATED_BOSS_COMMENT)
            .status(UPDATED_STATUS);

        restKiEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKiEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKiEmployee))
            )
            .andExpect(status().isOk());

        // Validate the KiEmployee in the database
        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeUpdate);
        KiEmployee testKiEmployee = kiEmployeeList.get(kiEmployeeList.size() - 1);
        assertThat(testKiEmployee.getDate_time()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testKiEmployee.getWork_quantity()).isEqualTo(UPDATED_WORK_QUANTITY);
        assertThat(testKiEmployee.getWork_quantity_comment()).isEqualTo(UPDATED_WORK_QUANTITY_COMMENT);
        assertThat(testKiEmployee.getWork_quality()).isEqualTo(UPDATED_WORK_QUALITY);
        assertThat(testKiEmployee.getWork_quality_comment()).isEqualTo(UPDATED_WORK_QUALITY_COMMENT);
        assertThat(testKiEmployee.getWork_progress()).isEqualTo(UPDATED_WORK_PROGRESS);
        assertThat(testKiEmployee.getWork_progress_comment()).isEqualTo(UPDATED_WORK_PROGRESS_COMMENT);
        assertThat(testKiEmployee.getWork_attitude()).isEqualTo(UPDATED_WORK_ATTITUDE);
        assertThat(testKiEmployee.getWork_attitude_comment()).isEqualTo(UPDATED_WORK_ATTITUDE_COMMENT);
        assertThat(testKiEmployee.getWork_discipline()).isEqualTo(UPDATED_WORK_DISCIPLINE);
        assertThat(testKiEmployee.getWork_discipline_comment()).isEqualTo(UPDATED_WORK_DISCIPLINE_COMMENT);
        assertThat(testKiEmployee.getAssigned_work()).isEqualTo(UPDATED_ASSIGNED_WORK);
        assertThat(testKiEmployee.getOther_work()).isEqualTo(UPDATED_OTHER_WORK);
        assertThat(testKiEmployee.getCompleted_work()).isEqualTo(UPDATED_COMPLETED_WORK);
        assertThat(testKiEmployee.getUncompleted_work()).isEqualTo(UPDATED_UNCOMPLETED_WORK);
        assertThat(testKiEmployee.getFavourite_work()).isEqualTo(UPDATED_FAVOURITE_WORK);
        assertThat(testKiEmployee.getUnfavourite_work()).isEqualTo(UPDATED_UNFAVOURITE_WORK);
        assertThat(testKiEmployee.getEmployee_ki_point()).isEqualTo(UPDATED_EMPLOYEE_KI_POINT);
        assertThat(testKiEmployee.getLeader_ki_point()).isEqualTo(UPDATED_LEADER_KI_POINT);
        assertThat(testKiEmployee.getLeader_comment()).isEqualTo(UPDATED_LEADER_COMMENT);
        assertThat(testKiEmployee.getBoss_ki_point()).isEqualTo(UPDATED_BOSS_KI_POINT);
        assertThat(testKiEmployee.getBoss_comment()).isEqualTo(UPDATED_BOSS_COMMENT);
        assertThat(testKiEmployee.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingKiEmployee() throws Exception {
        int databaseSizeBeforeUpdate = kiEmployeeRepository.findAll().size();
        kiEmployee.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKiEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kiEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kiEmployee))
            )
            .andExpect(status().isBadRequest());

        // Validate the KiEmployee in the database
        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKiEmployee() throws Exception {
        int databaseSizeBeforeUpdate = kiEmployeeRepository.findAll().size();
        kiEmployee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKiEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kiEmployee))
            )
            .andExpect(status().isBadRequest());

        // Validate the KiEmployee in the database
        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKiEmployee() throws Exception {
        int databaseSizeBeforeUpdate = kiEmployeeRepository.findAll().size();
        kiEmployee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKiEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kiEmployee))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KiEmployee in the database
        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKiEmployee() throws Exception {
        // Initialize the database
        kiEmployeeRepository.saveAndFlush(kiEmployee);

        int databaseSizeBeforeDelete = kiEmployeeRepository.findAll().size();

        // Delete the kiEmployee
        restKiEmployeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, kiEmployee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KiEmployee> kiEmployeeList = kiEmployeeRepository.findAll();
        assertThat(kiEmployeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
