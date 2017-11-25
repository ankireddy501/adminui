package com.musty.admin.repository;

import com.musty.admin.domain.ImageContent;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ImageContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageContentRepository extends JpaRepository<ImageContent, Long> {

}
