package com.musty.admin.repository;

import com.musty.admin.domain.ContentViewSummary;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContentViewSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentViewSummaryRepository extends JpaRepository<ContentViewSummary, Long> {

}
