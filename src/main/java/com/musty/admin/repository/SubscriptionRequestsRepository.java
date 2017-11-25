package com.musty.admin.repository;

import com.musty.admin.domain.SubscriptionRequests;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SubscriptionRequests entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubscriptionRequestsRepository extends JpaRepository<SubscriptionRequests, Long> {

}
