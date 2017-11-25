package com.musty.admin.web.rest;

import com.musty.admin.AdminuiApp;

import com.musty.admin.domain.SubscriptionRequests;
import com.musty.admin.repository.SubscriptionRequestsRepository;
import com.musty.admin.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.musty.admin.domain.enumeration.SubscriptionStatus;
/**
 * Test class for the SubscriptionRequestsResource REST controller.
 *
 * @see SubscriptionRequestsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminuiApp.class)
public class SubscriptionRequestsResourceIntTest {

    private static final SubscriptionStatus DEFAULT_STATUS = SubscriptionStatus.PENDING;
    private static final SubscriptionStatus UPDATED_STATUS = SubscriptionStatus.APPROVED;

    private static final LocalDate DEFAULT_REQUESTED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUESTED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_APPROVAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPROVAL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_SUBSCRIPTION_AMOUNT = 1;
    private static final Integer UPDATED_SUBSCRIPTION_AMOUNT = 2;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SubscriptionRequestsRepository subscriptionRequestsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSubscriptionRequestsMockMvc;

    private SubscriptionRequests subscriptionRequests;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubscriptionRequestsResource subscriptionRequestsResource = new SubscriptionRequestsResource(subscriptionRequestsRepository);
        this.restSubscriptionRequestsMockMvc = MockMvcBuilders.standaloneSetup(subscriptionRequestsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubscriptionRequests createEntity(EntityManager em) {
        SubscriptionRequests subscriptionRequests = new SubscriptionRequests()
            .status(DEFAULT_STATUS)
            .requestedDate(DEFAULT_REQUESTED_DATE)
            .approvalDate(DEFAULT_APPROVAL_DATE)
            .subscriptionAmount(DEFAULT_SUBSCRIPTION_AMOUNT)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return subscriptionRequests;
    }

    @Before
    public void initTest() {
        subscriptionRequests = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubscriptionRequests() throws Exception {
        int databaseSizeBeforeCreate = subscriptionRequestsRepository.findAll().size();

        // Create the SubscriptionRequests
        restSubscriptionRequestsMockMvc.perform(post("/api/subscription-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionRequests)))
            .andExpect(status().isCreated());

        // Validate the SubscriptionRequests in the database
        List<SubscriptionRequests> subscriptionRequestsList = subscriptionRequestsRepository.findAll();
        assertThat(subscriptionRequestsList).hasSize(databaseSizeBeforeCreate + 1);
        SubscriptionRequests testSubscriptionRequests = subscriptionRequestsList.get(subscriptionRequestsList.size() - 1);
        assertThat(testSubscriptionRequests.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSubscriptionRequests.getRequestedDate()).isEqualTo(DEFAULT_REQUESTED_DATE);
        assertThat(testSubscriptionRequests.getApprovalDate()).isEqualTo(DEFAULT_APPROVAL_DATE);
        assertThat(testSubscriptionRequests.getSubscriptionAmount()).isEqualTo(DEFAULT_SUBSCRIPTION_AMOUNT);
        assertThat(testSubscriptionRequests.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSubscriptionRequests.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createSubscriptionRequestsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subscriptionRequestsRepository.findAll().size();

        // Create the SubscriptionRequests with an existing ID
        subscriptionRequests.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubscriptionRequestsMockMvc.perform(post("/api/subscription-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionRequests)))
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionRequests in the database
        List<SubscriptionRequests> subscriptionRequestsList = subscriptionRequestsRepository.findAll();
        assertThat(subscriptionRequestsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSubscriptionRequests() throws Exception {
        // Initialize the database
        subscriptionRequestsRepository.saveAndFlush(subscriptionRequests);

        // Get all the subscriptionRequestsList
        restSubscriptionRequestsMockMvc.perform(get("/api/subscription-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscriptionRequests.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].requestedDate").value(hasItem(DEFAULT_REQUESTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].approvalDate").value(hasItem(DEFAULT_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].subscriptionAmount").value(hasItem(DEFAULT_SUBSCRIPTION_AMOUNT)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSubscriptionRequests() throws Exception {
        // Initialize the database
        subscriptionRequestsRepository.saveAndFlush(subscriptionRequests);

        // Get the subscriptionRequests
        restSubscriptionRequestsMockMvc.perform(get("/api/subscription-requests/{id}", subscriptionRequests.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subscriptionRequests.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.requestedDate").value(DEFAULT_REQUESTED_DATE.toString()))
            .andExpect(jsonPath("$.approvalDate").value(DEFAULT_APPROVAL_DATE.toString()))
            .andExpect(jsonPath("$.subscriptionAmount").value(DEFAULT_SUBSCRIPTION_AMOUNT))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubscriptionRequests() throws Exception {
        // Get the subscriptionRequests
        restSubscriptionRequestsMockMvc.perform(get("/api/subscription-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubscriptionRequests() throws Exception {
        // Initialize the database
        subscriptionRequestsRepository.saveAndFlush(subscriptionRequests);
        int databaseSizeBeforeUpdate = subscriptionRequestsRepository.findAll().size();

        // Update the subscriptionRequests
        SubscriptionRequests updatedSubscriptionRequests = subscriptionRequestsRepository.findOne(subscriptionRequests.getId());
        updatedSubscriptionRequests
            .status(UPDATED_STATUS)
            .requestedDate(UPDATED_REQUESTED_DATE)
            .approvalDate(UPDATED_APPROVAL_DATE)
            .subscriptionAmount(UPDATED_SUBSCRIPTION_AMOUNT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restSubscriptionRequestsMockMvc.perform(put("/api/subscription-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubscriptionRequests)))
            .andExpect(status().isOk());

        // Validate the SubscriptionRequests in the database
        List<SubscriptionRequests> subscriptionRequestsList = subscriptionRequestsRepository.findAll();
        assertThat(subscriptionRequestsList).hasSize(databaseSizeBeforeUpdate);
        SubscriptionRequests testSubscriptionRequests = subscriptionRequestsList.get(subscriptionRequestsList.size() - 1);
        assertThat(testSubscriptionRequests.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSubscriptionRequests.getRequestedDate()).isEqualTo(UPDATED_REQUESTED_DATE);
        assertThat(testSubscriptionRequests.getApprovalDate()).isEqualTo(UPDATED_APPROVAL_DATE);
        assertThat(testSubscriptionRequests.getSubscriptionAmount()).isEqualTo(UPDATED_SUBSCRIPTION_AMOUNT);
        assertThat(testSubscriptionRequests.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSubscriptionRequests.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSubscriptionRequests() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionRequestsRepository.findAll().size();

        // Create the SubscriptionRequests

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSubscriptionRequestsMockMvc.perform(put("/api/subscription-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionRequests)))
            .andExpect(status().isCreated());

        // Validate the SubscriptionRequests in the database
        List<SubscriptionRequests> subscriptionRequestsList = subscriptionRequestsRepository.findAll();
        assertThat(subscriptionRequestsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSubscriptionRequests() throws Exception {
        // Initialize the database
        subscriptionRequestsRepository.saveAndFlush(subscriptionRequests);
        int databaseSizeBeforeDelete = subscriptionRequestsRepository.findAll().size();

        // Get the subscriptionRequests
        restSubscriptionRequestsMockMvc.perform(delete("/api/subscription-requests/{id}", subscriptionRequests.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SubscriptionRequests> subscriptionRequestsList = subscriptionRequestsRepository.findAll();
        assertThat(subscriptionRequestsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscriptionRequests.class);
        SubscriptionRequests subscriptionRequests1 = new SubscriptionRequests();
        subscriptionRequests1.setId(1L);
        SubscriptionRequests subscriptionRequests2 = new SubscriptionRequests();
        subscriptionRequests2.setId(subscriptionRequests1.getId());
        assertThat(subscriptionRequests1).isEqualTo(subscriptionRequests2);
        subscriptionRequests2.setId(2L);
        assertThat(subscriptionRequests1).isNotEqualTo(subscriptionRequests2);
        subscriptionRequests1.setId(null);
        assertThat(subscriptionRequests1).isNotEqualTo(subscriptionRequests2);
    }
}
