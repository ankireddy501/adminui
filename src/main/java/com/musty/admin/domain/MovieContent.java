package com.musty.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.musty.admin.domain.enumeration.SubscriptionType;


/**
 * A MovieContent.
 */
@Entity
@Table(name = "movie_content")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MovieContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "content_path")
    private String contentPath;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_type")
    private SubscriptionType subscriptionType;

    @Column(name = "creation_time")
    private LocalDate creationTime;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @OneToOne
    @JoinColumn(unique = true)
    private MovieContentDetails details;

    @OneToMany(mappedBy = "movieContent")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MoviePoster> moviePosters = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MovieContent name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public MovieContent description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContentPath() {
        return contentPath;
    }

    public MovieContent contentPath(String contentPath) {
        this.contentPath = contentPath;
        return this;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public MovieContent subscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
        return this;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public LocalDate getCreationTime() {
        return creationTime;
    }

    public MovieContent creationTime(LocalDate creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public void setCreationTime(LocalDate creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public MovieContent updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public MovieContentDetails getDetails() {
        return details;
    }

    public MovieContent details(MovieContentDetails movieContentDetails) {
        this.details = movieContentDetails;
        return this;
    }

    public void setDetails(MovieContentDetails movieContentDetails) {
        this.details = movieContentDetails;
    }

    public Set<MoviePoster> getMoviePosters() {
        return moviePosters;
    }

    public MovieContent moviePosters(Set<MoviePoster> moviePosters) {
        this.moviePosters = moviePosters;
        return this;
    }

    public MovieContent addMoviePoster(MoviePoster moviePoster) {
        this.moviePosters.add(moviePoster);
        moviePoster.setMovieContent(this);
        return this;
    }

    public MovieContent removeMoviePoster(MoviePoster moviePoster) {
        this.moviePosters.remove(moviePoster);
        moviePoster.setMovieContent(null);
        return this;
    }

    public void setMoviePosters(Set<MoviePoster> moviePosters) {
        this.moviePosters = moviePosters;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MovieContent movieContent = (MovieContent) o;
        if (movieContent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movieContent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovieContent{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", contentPath='" + getContentPath() + "'" +
            ", subscriptionType='" + getSubscriptionType() + "'" +
            ", creationTime='" + getCreationTime() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
