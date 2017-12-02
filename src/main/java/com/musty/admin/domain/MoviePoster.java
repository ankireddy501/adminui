package com.musty.admin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.musty.admin.domain.enumeration.ImageType;


/**
 * A MoviePoster.
 */
@Entity
@Table(name = "movie_poster")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MoviePoster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "caption")
    private Boolean caption;

    @Lob
    @Column(name = "content")
    private byte[] content;

    @Column(name = "content_content_type")
    private String contentContentType;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "poster_size")
    private ImageType posterSize;

    @ManyToOne
    private MovieContent movieContent;

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

    public MoviePoster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isCaption() {
        return caption;
    }

    public MoviePoster caption(Boolean caption) {
        this.caption = caption;
        return this;
    }

    public void setCaption(Boolean caption) {
        this.caption = caption;
    }

    public byte[] getContent() {
        return content;
    }

    public MoviePoster content(byte[] content) {
        this.content = content;
        return this;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public MoviePoster contentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
        return this;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public MoviePoster creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public MoviePoster updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public ImageType getPosterSize() {
        return posterSize;
    }

    public MoviePoster posterSize(ImageType posterSize) {
        this.posterSize = posterSize;
        return this;
    }

    public void setPosterSize(ImageType posterSize) {
        this.posterSize = posterSize;
    }

    public MovieContent getMovieContent() {
        return movieContent;
    }

    public MoviePoster movieContent(MovieContent movieContent) {
        this.movieContent = movieContent;
        return this;
    }

    public void setMovieContent(MovieContent movieContent) {
        this.movieContent = movieContent;
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
        MoviePoster moviePoster = (MoviePoster) o;
        if (moviePoster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), moviePoster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MoviePoster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", caption='" + isCaption() + "'" +
            ", content='" + getContent() + "'" +
            ", contentContentType='" + getContentContentType() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", posterSize='" + getPosterSize() + "'" +
            "}";
    }
}
