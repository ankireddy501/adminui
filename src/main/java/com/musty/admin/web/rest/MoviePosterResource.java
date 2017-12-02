package com.musty.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.musty.admin.domain.MoviePoster;

import com.musty.admin.repository.MoviePosterRepository;
import com.musty.admin.web.rest.errors.BadRequestAlertException;
import com.musty.admin.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MoviePoster.
 */
@RestController
@RequestMapping("/api")
public class MoviePosterResource {

    private final Logger log = LoggerFactory.getLogger(MoviePosterResource.class);

    private static final String ENTITY_NAME = "moviePoster";

    private final MoviePosterRepository moviePosterRepository;

    public MoviePosterResource(MoviePosterRepository moviePosterRepository) {
        this.moviePosterRepository = moviePosterRepository;
    }

    /**
     * POST  /movie-posters : Create a new moviePoster.
     *
     * @param moviePoster the moviePoster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new moviePoster, or with status 400 (Bad Request) if the moviePoster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movie-posters")
    @Timed
    public ResponseEntity<MoviePoster> createMoviePoster(@RequestBody MoviePoster moviePoster) throws URISyntaxException {
        log.debug("REST request to save MoviePoster : {}", moviePoster);
        if (moviePoster.getId() != null) {
            throw new BadRequestAlertException("A new moviePoster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoviePoster result = moviePosterRepository.save(moviePoster);
        return ResponseEntity.created(new URI("/api/movie-posters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movie-posters : Updates an existing moviePoster.
     *
     * @param moviePoster the moviePoster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated moviePoster,
     * or with status 400 (Bad Request) if the moviePoster is not valid,
     * or with status 500 (Internal Server Error) if the moviePoster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movie-posters")
    @Timed
    public ResponseEntity<MoviePoster> updateMoviePoster(@RequestBody MoviePoster moviePoster) throws URISyntaxException {
        log.debug("REST request to update MoviePoster : {}", moviePoster);
        if (moviePoster.getId() == null) {
            return createMoviePoster(moviePoster);
        }
        MoviePoster result = moviePosterRepository.save(moviePoster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, moviePoster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movie-posters : get all the moviePosters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of moviePosters in body
     */
    @GetMapping("/movie-posters")
    @Timed
    public List<MoviePoster> getAllMoviePosters() {
        log.debug("REST request to get all MoviePosters");
        return moviePosterRepository.findAll();
        }

    /**
     * GET  /movie-posters/:id : get the "id" moviePoster.
     *
     * @param id the id of the moviePoster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the moviePoster, or with status 404 (Not Found)
     */
    @GetMapping("/movie-posters/{id}")
    @Timed
    public ResponseEntity<MoviePoster> getMoviePoster(@PathVariable Long id) {
        log.debug("REST request to get MoviePoster : {}", id);
        MoviePoster moviePoster = moviePosterRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(moviePoster));
    }

    /**
     * DELETE  /movie-posters/:id : delete the "id" moviePoster.
     *
     * @param id the id of the moviePoster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movie-posters/{id}")
    @Timed
    public ResponseEntity<Void> deleteMoviePoster(@PathVariable Long id) {
        log.debug("REST request to delete MoviePoster : {}", id);
        moviePosterRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
