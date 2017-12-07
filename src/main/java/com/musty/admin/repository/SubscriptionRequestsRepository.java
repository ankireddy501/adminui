package com.musty.admin.repository;

import com.musty.admin.domain.SubscriptionRequests;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the SubscriptionRequests entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubscriptionRequestsRepository extends JpaRepository<SubscriptionRequests, Long> {

    @Query("select subscription_requests from SubscriptionRequests subscription_requests where subscription_requests.user.login = ?#{principal.username}")
    List<SubscriptionRequests> findByUserIsCurrentUser();

}
