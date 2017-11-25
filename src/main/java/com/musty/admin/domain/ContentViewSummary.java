package com.musty.admin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.musty.admin.domain.enumeration.ContentType;

/**
 * A ContentViewSummary.
 */
@Entity
@Table(name = "content_view_summary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContentViewSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "view_date")
    private LocalDate viewDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type")
    private ContentType contentType;

    @Column(name = "content_id")
    private String contentId;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getViewDate() {
        return viewDate;
    }

    public ContentViewSummary viewDate(LocalDate viewDate) {
        this.viewDate = viewDate;
        return this;
    }

    public void setViewDate(LocalDate viewDate) {
        this.viewDate = viewDate;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public ContentViewSummary contentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getContentId() {
        return contentId;
    }

    public ContentViewSummary contentId(String contentId) {
        this.contentId = contentId;
        return this;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContentViewSummary contentViewSummary = (ContentViewSummary) o;
        if (contentViewSummary.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contentViewSummary.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContentViewSummary{" +
            "id=" + getId() +
            ", viewDate='" + getViewDate() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", contentId='" + getContentId() + "'" +
            "}";
    }
}
