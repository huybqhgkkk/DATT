package com.aladin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aladin.IntegrationTest;
import com.aladin.domain.Department;
import com.aladin.domain.Employee;
import com.aladin.domain.User;
import com.aladin.repository.EmployeeRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeResourceIT {

    private static final LocalDate DEFAULT_FIRST_DAY_WORK = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_DAY_WORK = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COUNTRYSIDE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRYSIDE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_RESIDENCE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_RESIDENCE = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIVE = "AAAAAAAAAA";
    private static final String UPDATED_RELATIVE = "BBBBBBBBBB";

    private static final String DEFAULT_FAVOURITE = "AAAAAAAAAA";
    private static final String UPDATED_FAVOURITE = "BBBBBBBBBB";

    private static final String DEFAULT_EDUCATION = "AAAAAAAAAA";
    private static final String UPDATED_EDUCATION = "BBBBBBBBBB";

    private static final String DEFAULT_EXPERIENCE = "AAAAAAAAAA";
    private static final String UPDATED_EXPERIENCE = "BBBBBBBBBB";

    private static final String DEFAULT_ENGLISH = "AAAAAAAAAA";
    private static final String UPDATED_ENGLISH = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECTIVE_IN_CV = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIVE_IN_CV = "BBBBBBBBBB";

    private static final String DEFAULT_MARITAL_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_MARITAL_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CHILDREN = "AAAAAAAAAA";
    private static final String UPDATED_CHILDREN = "BBBBBBBBBB";

    private static final String DEFAULT_FAMILY = "AAAAAAAAAA";
    private static final String UPDATED_FAMILY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_AVATAR = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_AVATAR = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_AVATAR_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_AVATAR_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .first_day_work(DEFAULT_FIRST_DAY_WORK)
            .full_name(DEFAULT_FULL_NAME)
            .phone_number(DEFAULT_PHONE_NUMBER)
            .email(DEFAULT_EMAIL)
            .date_of_birth(DEFAULT_DATE_OF_BIRTH)
            .countryside(DEFAULT_COUNTRYSIDE)
            .current_residence(DEFAULT_CURRENT_RESIDENCE)
            .relative(DEFAULT_RELATIVE)
            .favourite(DEFAULT_FAVOURITE)
            .education(DEFAULT_EDUCATION)
            .experience(DEFAULT_EXPERIENCE)
            .english(DEFAULT_ENGLISH)
            .objective_in_cv(DEFAULT_OBJECTIVE_IN_CV)
            .marital_status(DEFAULT_MARITAL_STATUS)
            .children(DEFAULT_CHILDREN)
            .family(DEFAULT_FAMILY)
            .avatar(DEFAULT_AVATAR)
            .avatarContentType(DEFAULT_AVATAR_CONTENT_TYPE)
            .gender(DEFAULT_GENDER);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        employee.setUser(user);
        // Add required entity
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            department = DepartmentResourceIT.createEntity(em);
            em.persist(department);
            em.flush();
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
        employee.setDepartment(department);
        return employee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee()
            .first_day_work(UPDATED_FIRST_DAY_WORK)
            .full_name(UPDATED_FULL_NAME)
            .phone_number(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .date_of_birth(UPDATED_DATE_OF_BIRTH)
            .countryside(UPDATED_COUNTRYSIDE)
            .current_residence(UPDATED_CURRENT_RESIDENCE)
            .relative(UPDATED_RELATIVE)
            .favourite(UPDATED_FAVOURITE)
            .education(UPDATED_EDUCATION)
            .experience(UPDATED_EXPERIENCE)
            .english(UPDATED_ENGLISH)
            .objective_in_cv(UPDATED_OBJECTIVE_IN_CV)
            .marital_status(UPDATED_MARITAL_STATUS)
            .children(UPDATED_CHILDREN)
            .family(UPDATED_FAMILY)
            .avatar(UPDATED_AVATAR)
            .avatarContentType(UPDATED_AVATAR_CONTENT_TYPE)
            .gender(UPDATED_GENDER);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        employee.setUser(user);
        // Add required entity
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            department = DepartmentResourceIT.createUpdatedEntity(em);
            em.persist(department);
            em.flush();
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
        employee.setDepartment(department);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();
        // Create the Employee
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirst_day_work()).isEqualTo(DEFAULT_FIRST_DAY_WORK);
        assertThat(testEmployee.getFull_name()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testEmployee.getPhone_number()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployee.getDate_of_birth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testEmployee.getCountryside()).isEqualTo(DEFAULT_COUNTRYSIDE);
        assertThat(testEmployee.getCurrent_residence()).isEqualTo(DEFAULT_CURRENT_RESIDENCE);
        assertThat(testEmployee.getRelative()).isEqualTo(DEFAULT_RELATIVE);
        assertThat(testEmployee.getFavourite()).isEqualTo(DEFAULT_FAVOURITE);
        assertThat(testEmployee.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testEmployee.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
        assertThat(testEmployee.getEnglish()).isEqualTo(DEFAULT_ENGLISH);
        assertThat(testEmployee.getObjective_in_cv()).isEqualTo(DEFAULT_OBJECTIVE_IN_CV);
        assertThat(testEmployee.getMarital_status()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testEmployee.getChildren()).isEqualTo(DEFAULT_CHILDREN);
        assertThat(testEmployee.getFamily()).isEqualTo(DEFAULT_FAMILY);
        assertThat(testEmployee.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testEmployee.getAvatarContentType()).isEqualTo(DEFAULT_AVATAR_CONTENT_TYPE);
        assertThat(testEmployee.getGender()).isEqualTo(DEFAULT_GENDER);
    }

    @Test
    @Transactional
    void createEmployeeWithExistingId() throws Exception {
        // Create the Employee with an existing ID
        employee.setId(1L);

        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirst_day_workIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setFirst_day_work(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFull_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setFull_name(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhone_numberIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setPhone_number(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setEmail(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDate_of_birthIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setDate_of_birth(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountrysideIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setCountryside(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCurrent_residenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setCurrent_residence(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRelativeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setRelative(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFavouriteIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setFavourite(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEducationIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setEducation(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExperienceIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setExperience(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEnglishIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setEnglish(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkObjective_in_cvIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setObjective_in_cv(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMarital_statusIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setMarital_status(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChildrenIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setChildren(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFamilyIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setFamily(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setGender(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].first_day_work").value(hasItem(DEFAULT_FIRST_DAY_WORK.toString())))
            .andExpect(jsonPath("$.[*].full_name").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].phone_number").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].date_of_birth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].countryside").value(hasItem(DEFAULT_COUNTRYSIDE)))
            .andExpect(jsonPath("$.[*].current_residence").value(hasItem(DEFAULT_CURRENT_RESIDENCE)))
            .andExpect(jsonPath("$.[*].relative").value(hasItem(DEFAULT_RELATIVE)))
            .andExpect(jsonPath("$.[*].favourite").value(hasItem(DEFAULT_FAVOURITE)))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION)))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].english").value(hasItem(DEFAULT_ENGLISH)))
            .andExpect(jsonPath("$.[*].objective_in_cv").value(hasItem(DEFAULT_OBJECTIVE_IN_CV)))
            .andExpect(jsonPath("$.[*].marital_status").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].children").value(hasItem(DEFAULT_CHILDREN)))
            .andExpect(jsonPath("$.[*].family").value(hasItem(DEFAULT_FAMILY)))
            .andExpect(jsonPath("$.[*].avatarContentType").value(hasItem(DEFAULT_AVATAR_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(Base64Utils.encodeToString(DEFAULT_AVATAR))))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)));
    }

    @Test
    @Transactional
    void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL_ID, employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.first_day_work").value(DEFAULT_FIRST_DAY_WORK.toString()))
            .andExpect(jsonPath("$.full_name").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.phone_number").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.date_of_birth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.countryside").value(DEFAULT_COUNTRYSIDE))
            .andExpect(jsonPath("$.current_residence").value(DEFAULT_CURRENT_RESIDENCE))
            .andExpect(jsonPath("$.relative").value(DEFAULT_RELATIVE))
            .andExpect(jsonPath("$.favourite").value(DEFAULT_FAVOURITE))
            .andExpect(jsonPath("$.education").value(DEFAULT_EDUCATION))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE))
            .andExpect(jsonPath("$.english").value(DEFAULT_ENGLISH))
            .andExpect(jsonPath("$.objective_in_cv").value(DEFAULT_OBJECTIVE_IN_CV))
            .andExpect(jsonPath("$.marital_status").value(DEFAULT_MARITAL_STATUS))
            .andExpect(jsonPath("$.children").value(DEFAULT_CHILDREN))
            .andExpect(jsonPath("$.family").value(DEFAULT_FAMILY))
            .andExpect(jsonPath("$.avatarContentType").value(DEFAULT_AVATAR_CONTENT_TYPE))
            .andExpect(jsonPath("$.avatar").value(Base64Utils.encodeToString(DEFAULT_AVATAR)))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER));
    }

    @Test
    @Transactional
    void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .first_day_work(UPDATED_FIRST_DAY_WORK)
            .full_name(UPDATED_FULL_NAME)
            .phone_number(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .date_of_birth(UPDATED_DATE_OF_BIRTH)
            .countryside(UPDATED_COUNTRYSIDE)
            .current_residence(UPDATED_CURRENT_RESIDENCE)
            .relative(UPDATED_RELATIVE)
            .favourite(UPDATED_FAVOURITE)
            .education(UPDATED_EDUCATION)
            .experience(UPDATED_EXPERIENCE)
            .english(UPDATED_ENGLISH)
            .objective_in_cv(UPDATED_OBJECTIVE_IN_CV)
            .marital_status(UPDATED_MARITAL_STATUS)
            .children(UPDATED_CHILDREN)
            .family(UPDATED_FAMILY)
            .avatar(UPDATED_AVATAR)
            .avatarContentType(UPDATED_AVATAR_CONTENT_TYPE)
            .gender(UPDATED_GENDER);

        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirst_day_work()).isEqualTo(UPDATED_FIRST_DAY_WORK);
        assertThat(testEmployee.getFull_name()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testEmployee.getPhone_number()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getDate_of_birth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testEmployee.getCountryside()).isEqualTo(UPDATED_COUNTRYSIDE);
        assertThat(testEmployee.getCurrent_residence()).isEqualTo(UPDATED_CURRENT_RESIDENCE);
        assertThat(testEmployee.getRelative()).isEqualTo(UPDATED_RELATIVE);
        assertThat(testEmployee.getFavourite()).isEqualTo(UPDATED_FAVOURITE);
        assertThat(testEmployee.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testEmployee.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testEmployee.getEnglish()).isEqualTo(UPDATED_ENGLISH);
        assertThat(testEmployee.getObjective_in_cv()).isEqualTo(UPDATED_OBJECTIVE_IN_CV);
        assertThat(testEmployee.getMarital_status()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testEmployee.getChildren()).isEqualTo(UPDATED_CHILDREN);
        assertThat(testEmployee.getFamily()).isEqualTo(UPDATED_FAMILY);
        assertThat(testEmployee.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testEmployee.getAvatarContentType()).isEqualTo(UPDATED_AVATAR_CONTENT_TYPE);
        assertThat(testEmployee.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    @Transactional
    void putNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee
            .full_name(UPDATED_FULL_NAME)
            .phone_number(UPDATED_PHONE_NUMBER)
            .current_residence(UPDATED_CURRENT_RESIDENCE)
            .relative(UPDATED_RELATIVE)
            .favourite(UPDATED_FAVOURITE)
            .education(UPDATED_EDUCATION)
            .marital_status(UPDATED_MARITAL_STATUS);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirst_day_work()).isEqualTo(DEFAULT_FIRST_DAY_WORK);
        assertThat(testEmployee.getFull_name()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testEmployee.getPhone_number()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployee.getDate_of_birth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testEmployee.getCountryside()).isEqualTo(DEFAULT_COUNTRYSIDE);
        assertThat(testEmployee.getCurrent_residence()).isEqualTo(UPDATED_CURRENT_RESIDENCE);
        assertThat(testEmployee.getRelative()).isEqualTo(UPDATED_RELATIVE);
        assertThat(testEmployee.getFavourite()).isEqualTo(UPDATED_FAVOURITE);
        assertThat(testEmployee.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testEmployee.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
        assertThat(testEmployee.getEnglish()).isEqualTo(DEFAULT_ENGLISH);
        assertThat(testEmployee.getObjective_in_cv()).isEqualTo(DEFAULT_OBJECTIVE_IN_CV);
        assertThat(testEmployee.getMarital_status()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testEmployee.getChildren()).isEqualTo(DEFAULT_CHILDREN);
        assertThat(testEmployee.getFamily()).isEqualTo(DEFAULT_FAMILY);
        assertThat(testEmployee.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testEmployee.getAvatarContentType()).isEqualTo(DEFAULT_AVATAR_CONTENT_TYPE);
        assertThat(testEmployee.getGender()).isEqualTo(DEFAULT_GENDER);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee
            .first_day_work(UPDATED_FIRST_DAY_WORK)
            .full_name(UPDATED_FULL_NAME)
            .phone_number(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .date_of_birth(UPDATED_DATE_OF_BIRTH)
            .countryside(UPDATED_COUNTRYSIDE)
            .current_residence(UPDATED_CURRENT_RESIDENCE)
            .relative(UPDATED_RELATIVE)
            .favourite(UPDATED_FAVOURITE)
            .education(UPDATED_EDUCATION)
            .experience(UPDATED_EXPERIENCE)
            .english(UPDATED_ENGLISH)
            .objective_in_cv(UPDATED_OBJECTIVE_IN_CV)
            .marital_status(UPDATED_MARITAL_STATUS)
            .children(UPDATED_CHILDREN)
            .family(UPDATED_FAMILY)
            .avatar(UPDATED_AVATAR)
            .avatarContentType(UPDATED_AVATAR_CONTENT_TYPE)
            .gender(UPDATED_GENDER);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirst_day_work()).isEqualTo(UPDATED_FIRST_DAY_WORK);
        assertThat(testEmployee.getFull_name()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testEmployee.getPhone_number()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getDate_of_birth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testEmployee.getCountryside()).isEqualTo(UPDATED_COUNTRYSIDE);
        assertThat(testEmployee.getCurrent_residence()).isEqualTo(UPDATED_CURRENT_RESIDENCE);
        assertThat(testEmployee.getRelative()).isEqualTo(UPDATED_RELATIVE);
        assertThat(testEmployee.getFavourite()).isEqualTo(UPDATED_FAVOURITE);
        assertThat(testEmployee.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testEmployee.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testEmployee.getEnglish()).isEqualTo(UPDATED_ENGLISH);
        assertThat(testEmployee.getObjective_in_cv()).isEqualTo(UPDATED_OBJECTIVE_IN_CV);
        assertThat(testEmployee.getMarital_status()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testEmployee.getChildren()).isEqualTo(UPDATED_CHILDREN);
        assertThat(testEmployee.getFamily()).isEqualTo(UPDATED_FAMILY);
        assertThat(testEmployee.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testEmployee.getAvatarContentType()).isEqualTo(UPDATED_AVATAR_CONTENT_TYPE);
        assertThat(testEmployee.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    @Transactional
    void patchNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, employee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
