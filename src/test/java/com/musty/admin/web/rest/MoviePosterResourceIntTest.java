package com.musty.admin.web.rest;

import com.musty.admin.AdminuiApp;

import com.musty.admin.domain.MoviePoster;
import com.musty.admin.repository.MoviePosterRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.musty.admin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.musty.admin.domain.enumeration.ImageType;
/**
 * Test class for the MoviePosterResource REST controller.
 *
 * @see MoviePosterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminuiApp.class)
public class MoviePosterResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CAPTION = false;
    private static final Boolean UPDATED_CAPTION = true;

    private static final byte[] DEFAULT_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ImageType DEFAULT_POSTER_SIZE = ImageType.THUMBNAIL;
    private static final ImageType UPDATED_POSTER_SIZE = ImageType.RAW;

    @Autowired
    private MoviePosterRepository moviePosterRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMoviePosterMockMvc;

    private MoviePoster moviePoster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MoviePosterResource moviePosterResource = new MoviePosterResource(moviePosterRepository);
        this.restMoviePosterMockMvc = MockMvcBuilders.standaloneSetup(moviePosterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MoviePoster createEntity(EntityManager em) {
        MoviePoster moviePoster = new MoviePoster()
            .name(DEFAULT_NAME)
            .caption(DEFAULT_CAPTION)
            .content(DEFAULT_CONTENT)
            .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE)
            .creationDate(DEFAULT_CREATION_DATE)
            .updateDate(DEFAULT_UPDATE_DATE)
            .posterSize(DEFAULT_POSTER_SIZE);
        return moviePoster;
    }

    @Before
    public void initTest() {
        moviePoster = createEntity(em);
    }

    @Test
    @Transactional
    public void createMoviePoster() throws Exception {
        int databaseSizeBeforeCreate = moviePosterRepository.findAll().size();

        // Create the MoviePoster
        restMoviePosterMockMvc.perform(post("/api/movie-posters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moviePoster)))
            .andExpect(status().isCreated());

        // Validate the MoviePoster in the database
        List<MoviePoster> moviePosterList = moviePosterRepository.findAll();
        assertThat(moviePosterList).hasSize(databaseSizeBeforeCreate + 1);
        MoviePoster testMoviePoster = moviePosterList.get(moviePosterList.size() - 1);
        assertThat(testMoviePoster.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMoviePoster.isCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testMoviePoster.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testMoviePoster.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
        assertThat(testMoviePoster.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testMoviePoster.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testMoviePoster.getPosterSize()).isEqualTo(DEFAULT_POSTER_SIZE);
    }

    @Test
    @Transactional
    public void createMoviePosterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moviePosterRepository.findAll().size();

        // Create the MoviePoster with an existing ID
        moviePoster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoviePosterMockMvc.perform(post("/api/movie-posters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moviePoster)))
            .andExpect(status().isBadRequest());

        // Validate the MoviePoster in the database
        List<MoviePoster> moviePosterList = moviePosterRepository.findAll();
        assertThat(moviePosterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMoviePosters() throws Exception {
        // Initialize the database
        moviePosterRepository.saveAndFlush(moviePoster);

        // Get all the moviePosterList
        restMoviePosterMockMvc.perform(get("/api/movie-posters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moviePoster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].posterSize").value(hasItem(DEFAULT_POSTER_SIZE.toString())));
    }

    @Test
    @Transactional
    public void getMoviePoster() throws Exception {
        // Initialize the database
        moviePosterRepository.saveAndFlush(moviePoster);

        // Get the moviePoster
        restMoviePosterMockMvc.perform(get("/api/movie-posters/{id}", moviePoster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(moviePoster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.caption").value(DEFAULT_CAPTION.booleanValue()))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.posterSize").value(DEFAULT_POSTER_SIZE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMoviePoster() throws Exception {
        // Get the moviePoster
        restMoviePosterMockMvc.perform(get("/api/movie-posters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMoviePoster() throws Exception {
        // Initialize the database
        moviePosterRepository.saveAndFlush(moviePoster);
        int databaseSizeBeforeUpdate = moviePosterRepository.findAll().size();

        // Update the moviePoster
        MoviePoster updatedMoviePoster = moviePosterRepository.findOne(moviePoster.getId());
        updatedMoviePoster
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .creationDate(UPDATED_CREATION_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .posterSize(UPDATED_POSTER_SIZE);

        restMoviePosterMockMvc.perform(put("/api/movie-posters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMoviePoster)))
            .andExpect(status().isOk());

        // Validate the MoviePoster in the database
        List<MoviePoster> moviePosterList = moviePosterRepository.findAll();
        assertThat(moviePosterList).hasSize(databaseSizeBeforeUpdate);
        MoviePoster testMoviePoster = moviePosterList.get(moviePosterList.size() - 1);
        assertThat(testMoviePoster.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMoviePoster.isCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testMoviePoster.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testMoviePoster.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testMoviePoster.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testMoviePoster.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testMoviePoster.getPosterSize()).isEqualTo(UPDATED_POSTER_SIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingMoviePoster() throws Exception {
        int databaseSizeBeforeUpdate = moviePosterRepository.findAll().size();

        // Create the MoviePoster

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMoviePosterMockMvc.perform(put("/api/movie-posters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moviePoster)))
            .andExpect(status().isCreated());

        // Validate the MoviePoster in the database
        List<MoviePoster> moviePosterList = moviePosterRepository.findAll();
        assertThat(moviePosterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMoviePoster() throws Exception {
        // Initialize the database
        moviePosterRepository.saveAndFlush(moviePoster);
        int databaseSizeBeforeDelete = moviePosterRepository.findAll().size();

        // Get the moviePoster
        restMoviePosterMockMvc.perform(delete("/api/movie-posters/{id}", moviePoster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MoviePoster> moviePosterList = moviePosterRepository.findAll();
        assertThat(moviePosterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoviePoster.class);
        MoviePoster moviePoster1 = new MoviePoster();
        moviePoster1.setId(1L);
        MoviePoster moviePoster2 = new MoviePoster();
        moviePoster2.setId(moviePoster1.getId());
        assertThat(moviePoster1).isEqualTo(moviePoster2);
        moviePoster2.setId(2L);
        assertThat(moviePoster1).isNotEqualTo(moviePoster2);
        moviePoster1.setId(null);
        assertThat(moviePoster1).isNotEqualTo(moviePoster2);
    }
}
