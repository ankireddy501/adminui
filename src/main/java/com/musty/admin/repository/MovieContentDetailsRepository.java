package com.musty.admin.repository;

import com.musty.admin.domain.MovieContentDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MovieContentDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieContentDetailsRepository extends JpaRepository<MovieContentDetails, Long> {

}
