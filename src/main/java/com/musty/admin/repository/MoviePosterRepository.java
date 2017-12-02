package com.musty.admin.repository;

import com.musty.admin.domain.MoviePoster;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MoviePoster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoviePosterRepository extends JpaRepository<MoviePoster, Long> {

}
