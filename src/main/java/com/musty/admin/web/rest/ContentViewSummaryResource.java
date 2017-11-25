package com.musty.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.musty.admin.domain.ContentViewSummary;

import com.musty.admin.repository.ContentViewSummaryRepository;
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
 * REST controller for managing ContentViewSummary.
 */
@RestController
@RequestMapping("/api")
public class ContentViewSummaryResource {

    private final Logger log = LoggerFactory.getLogger(ContentViewSummaryResource.class);

    private static final String ENTITY_NAME = "contentViewSummary";

    private final ContentViewSummaryRepository contentViewSummaryRepository;

    public ContentViewSummaryResource(ContentViewSummaryRepository contentViewSummaryRepository) {
        this.contentViewSummaryRepository = contentViewSummaryRepository;
    }

    /**
     * POST  /content-view-summaries : Create a new contentViewSummary.
     *
     * @param contentViewSummary the contentViewSummary to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contentViewSummary, or with status 400 (Bad Request) if the contentViewSummary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/content-view-summaries")
    @Timed
    public ResponseEntity<ContentViewSummary> createContentViewSummary(@RequestBody ContentViewSummary contentViewSummary) throws URISyntaxException {
        log.debug("REST request to save ContentViewSummary : {}", contentViewSummary);
        if (contentViewSummary.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contentViewSummary cannot already have an ID")).body(null);
        }
        ContentViewSummary result = contentViewSummaryRepository.save(contentViewSummary);
        return ResponseEntity.created(new URI("/api/content-view-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /content-view-summaries : Updates an existing contentViewSummary.
     *
     * @param contentViewSummary the contentViewSummary to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contentViewSummary,
     * or with status 400 (Bad Request) if the contentViewSummary is not valid,
     * or with status 500 (Internal Server Error) if the contentViewSummary couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/content-view-summaries")
    @Timed
    public ResponseEntity<ContentViewSummary> updateContentViewSummary(@RequestBody ContentViewSummary contentViewSummary) throws URISyntaxException {
        log.debug("REST request to update ContentViewSummary : {}", contentViewSummary);
        if (contentViewSummary.getId() == null) {
            return createContentViewSummary(contentViewSummary);
        }
        ContentViewSummary result = contentViewSummaryRepository.save(contentViewSummary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contentViewSummary.getId().toString()))
            .body(result);
    }

    /**
     * GET  /content-view-summaries : get all the contentViewSummaries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contentViewSummaries in body
     */
    @GetMapping("/content-view-summaries")
    @Timed
    public List<ContentViewSummary> getAllContentViewSummaries() {
        log.debug("REST request to get all ContentViewSummaries");
        return contentViewSummaryRepository.findAll();
        }

    /**
     * GET  /content-view-summaries/:id : get the "id" contentViewSummary.
     *
     * @param id the id of the contentViewSummary to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contentViewSummary, or with status 404 (Not Found)
     */
    @GetMapping("/content-view-summaries/{id}")
    @Timed
    public ResponseEntity<ContentViewSummary> getContentViewSummary(@PathVariable Long id) {
        log.debug("REST request to get ContentViewSummary : {}", id);
        ContentViewSummary contentViewSummary = contentViewSummaryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contentViewSummary));
    }

    /**
     * DELETE  /content-view-summaries/:id : delete the "id" contentViewSummary.
     *
     * @param id the id of the contentViewSummary to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/content-view-summaries/{id}")
    @Timed
    public ResponseEntity<Void> deleteContentViewSummary(@PathVariable Long id) {
        log.debug("REST request to delete ContentViewSummary : {}", id);
        contentViewSummaryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
