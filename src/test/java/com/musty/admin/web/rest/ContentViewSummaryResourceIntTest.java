package com.musty.admin.web.rest;

import com.musty.admin.AdminuiApp;

import com.musty.admin.domain.ContentViewSummary;
import com.musty.admin.repository.ContentViewSummaryRepository;
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

import com.musty.admin.domain.enumeration.ContentType;
/**
 * Test class for the ContentViewSummaryResource REST controller.
 *
 * @see ContentViewSummaryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminuiApp.class)
public class ContentViewSummaryResourceIntTest {

    private static final LocalDate DEFAULT_VIEW_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VIEW_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ContentType DEFAULT_CONTENT_TYPE = ContentType.IMAGE;
    private static final ContentType UPDATED_CONTENT_TYPE = ContentType.VIDEO;

    private static final String DEFAULT_CONTENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_ID = "BBBBBBBBBB";

    @Autowired
    private ContentViewSummaryRepository contentViewSummaryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContentViewSummaryMockMvc;

    private ContentViewSummary contentViewSummary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContentViewSummaryResource contentViewSummaryResource = new ContentViewSummaryResource(contentViewSummaryRepository);
        this.restContentViewSummaryMockMvc = MockMvcBuilders.standaloneSetup(contentViewSummaryResource)
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
    public static ContentViewSummary createEntity(EntityManager em) {
        ContentViewSummary contentViewSummary = new ContentViewSummary()
            .viewDate(DEFAULT_VIEW_DATE)
            .contentType(DEFAULT_CONTENT_TYPE)
            .contentId(DEFAULT_CONTENT_ID);
        return contentViewSummary;
    }

    @Before
    public void initTest() {
        contentViewSummary = createEntity(em);
    }

    @Test
    @Transactional
    public void createContentViewSummary() throws Exception {
        int databaseSizeBeforeCreate = contentViewSummaryRepository.findAll().size();

        // Create the ContentViewSummary
        restContentViewSummaryMockMvc.perform(post("/api/content-view-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentViewSummary)))
            .andExpect(status().isCreated());

        // Validate the ContentViewSummary in the database
        List<ContentViewSummary> contentViewSummaryList = contentViewSummaryRepository.findAll();
        assertThat(contentViewSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        ContentViewSummary testContentViewSummary = contentViewSummaryList.get(contentViewSummaryList.size() - 1);
        assertThat(testContentViewSummary.getViewDate()).isEqualTo(DEFAULT_VIEW_DATE);
        assertThat(testContentViewSummary.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testContentViewSummary.getContentId()).isEqualTo(DEFAULT_CONTENT_ID);
    }

    @Test
    @Transactional
    public void createContentViewSummaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentViewSummaryRepository.findAll().size();

        // Create the ContentViewSummary with an existing ID
        contentViewSummary.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentViewSummaryMockMvc.perform(post("/api/content-view-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentViewSummary)))
            .andExpect(status().isBadRequest());

        // Validate the ContentViewSummary in the database
        List<ContentViewSummary> contentViewSummaryList = contentViewSummaryRepository.findAll();
        assertThat(contentViewSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContentViewSummaries() throws Exception {
        // Initialize the database
        contentViewSummaryRepository.saveAndFlush(contentViewSummary);

        // Get all the contentViewSummaryList
        restContentViewSummaryMockMvc.perform(get("/api/content-view-summaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentViewSummary.getId().intValue())))
            .andExpect(jsonPath("$.[*].viewDate").value(hasItem(DEFAULT_VIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].contentId").value(hasItem(DEFAULT_CONTENT_ID.toString())));
    }

    @Test
    @Transactional
    public void getContentViewSummary() throws Exception {
        // Initialize the database
        contentViewSummaryRepository.saveAndFlush(contentViewSummary);

        // Get the contentViewSummary
        restContentViewSummaryMockMvc.perform(get("/api/content-view-summaries/{id}", contentViewSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contentViewSummary.getId().intValue()))
            .andExpect(jsonPath("$.viewDate").value(DEFAULT_VIEW_DATE.toString()))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.contentId").value(DEFAULT_CONTENT_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContentViewSummary() throws Exception {
        // Get the contentViewSummary
        restContentViewSummaryMockMvc.perform(get("/api/content-view-summaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentViewSummary() throws Exception {
        // Initialize the database
        contentViewSummaryRepository.saveAndFlush(contentViewSummary);
        int databaseSizeBeforeUpdate = contentViewSummaryRepository.findAll().size();

        // Update the contentViewSummary
        ContentViewSummary updatedContentViewSummary = contentViewSummaryRepository.findOne(contentViewSummary.getId());
        updatedContentViewSummary
            .viewDate(UPDATED_VIEW_DATE)
            .contentType(UPDATED_CONTENT_TYPE)
            .contentId(UPDATED_CONTENT_ID);

        restContentViewSummaryMockMvc.perform(put("/api/content-view-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContentViewSummary)))
            .andExpect(status().isOk());

        // Validate the ContentViewSummary in the database
        List<ContentViewSummary> contentViewSummaryList = contentViewSummaryRepository.findAll();
        assertThat(contentViewSummaryList).hasSize(databaseSizeBeforeUpdate);
        ContentViewSummary testContentViewSummary = contentViewSummaryList.get(contentViewSummaryList.size() - 1);
        assertThat(testContentViewSummary.getViewDate()).isEqualTo(UPDATED_VIEW_DATE);
        assertThat(testContentViewSummary.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testContentViewSummary.getContentId()).isEqualTo(UPDATED_CONTENT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingContentViewSummary() throws Exception {
        int databaseSizeBeforeUpdate = contentViewSummaryRepository.findAll().size();

        // Create the ContentViewSummary

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContentViewSummaryMockMvc.perform(put("/api/content-view-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentViewSummary)))
            .andExpect(status().isCreated());

        // Validate the ContentViewSummary in the database
        List<ContentViewSummary> contentViewSummaryList = contentViewSummaryRepository.findAll();
        assertThat(contentViewSummaryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContentViewSummary() throws Exception {
        // Initialize the database
        contentViewSummaryRepository.saveAndFlush(contentViewSummary);
        int databaseSizeBeforeDelete = contentViewSummaryRepository.findAll().size();

        // Get the contentViewSummary
        restContentViewSummaryMockMvc.perform(delete("/api/content-view-summaries/{id}", contentViewSummary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContentViewSummary> contentViewSummaryList = contentViewSummaryRepository.findAll();
        assertThat(contentViewSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentViewSummary.class);
        ContentViewSummary contentViewSummary1 = new ContentViewSummary();
        contentViewSummary1.setId(1L);
        ContentViewSummary contentViewSummary2 = new ContentViewSummary();
        contentViewSummary2.setId(contentViewSummary1.getId());
        assertThat(contentViewSummary1).isEqualTo(contentViewSummary2);
        contentViewSummary2.setId(2L);
        assertThat(contentViewSummary1).isNotEqualTo(contentViewSummary2);
        contentViewSummary1.setId(null);
        assertThat(contentViewSummary1).isNotEqualTo(contentViewSummary2);
    }
}
