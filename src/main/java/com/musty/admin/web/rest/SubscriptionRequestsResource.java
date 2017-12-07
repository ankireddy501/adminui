package com.musty.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.musty.admin.domain.SubscriptionRequests;

import com.musty.admin.repository.SubscriptionRequestsRepository;
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
 * REST controller for managing SubscriptionRequests.
 */
@RestController
@RequestMapping("/api")
public class SubscriptionRequestsResource {

    private final Logger log = LoggerFactory.getLogger(SubscriptionRequestsResource.class);

    private static final String ENTITY_NAME = "subscriptionRequests";

    private final SubscriptionRequestsRepository subscriptionRequestsRepository;

    public SubscriptionRequestsResource(SubscriptionRequestsRepository subscriptionRequestsRepository) {
        this.subscriptionRequestsRepository = subscriptionRequestsRepository;
    }

    /**
     * POST  /subscription-requests : Create a new subscriptionRequests.
     *
     * @param subscriptionRequests the subscriptionRequests to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subscriptionRequests, or with status 400 (Bad Request) if the subscriptionRequests has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/subscription-requests")
    @Timed
    public ResponseEntity<SubscriptionRequests> createSubscriptionRequests(@RequestBody SubscriptionRequests subscriptionRequests) throws URISyntaxException {
        log.debug("REST request to save SubscriptionRequests : {}", subscriptionRequests);
        if (subscriptionRequests.getId() != null) {
            throw new BadRequestAlertException("A new subscriptionRequests cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubscriptionRequests result = subscriptionRequestsRepository.save(subscriptionRequests);
        return ResponseEntity.created(new URI("/api/subscription-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subscription-requests : Updates an existing subscriptionRequests.
     *
     * @param subscriptionRequests the subscriptionRequests to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subscriptionRequests,
     * or with status 400 (Bad Request) if the subscriptionRequests is not valid,
     * or with status 500 (Internal Server Error) if the subscriptionRequests couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/subscription-requests")
    @Timed
    public ResponseEntity<SubscriptionRequests> updateSubscriptionRequests(@RequestBody SubscriptionRequests subscriptionRequests) throws URISyntaxException {
        log.debug("REST request to update SubscriptionRequests : {}", subscriptionRequests);
        if (subscriptionRequests.getId() == null) {
            return createSubscriptionRequests(subscriptionRequests);
        }
        SubscriptionRequests result = subscriptionRequestsRepository.save(subscriptionRequests);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subscriptionRequests.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subscription-requests : get all the subscriptionRequests.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of subscriptionRequests in body
     */
    @GetMapping("/subscription-requests")
    @Timed
    public List<SubscriptionRequests> getAllSubscriptionRequests() {
        log.debug("REST request to get all SubscriptionRequests");
        return subscriptionRequestsRepository.findAll();
        }

    /**
     * GET  /subscription-requests/:id : get the "id" subscriptionRequests.
     *
     * @param id the id of the subscriptionRequests to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subscriptionRequests, or with status 404 (Not Found)
     */
    @GetMapping("/subscription-requests/{id}")
    @Timed
    public ResponseEntity<SubscriptionRequests> getSubscriptionRequests(@PathVariable Long id) {
        log.debug("REST request to get SubscriptionRequests : {}", id);
        SubscriptionRequests subscriptionRequests = subscriptionRequestsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(subscriptionRequests));
    }

    /**
     * DELETE  /subscription-requests/:id : delete the "id" subscriptionRequests.
     *
     * @param id the id of the subscriptionRequests to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/subscription-requests/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubscriptionRequests(@PathVariable Long id) {
        log.debug("REST request to delete SubscriptionRequests : {}", id);
        subscriptionRequestsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
