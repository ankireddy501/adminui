package com.musty.admin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.musty.admin.domain.enumeration.SubscriptionType;

/**
 * A ImageContent.
 */
@Entity
@Table(name = "image_content")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ImageContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "caption")
    private Boolean caption;

    @Column(name = "content_path")
    private String contentPath;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_type")
    private SubscriptionType subscriptionType;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ImageContent name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isCaption() {
        return caption;
    }

    public ImageContent caption(Boolean caption) {
        this.caption = caption;
        return this;
    }

    public void setCaption(Boolean caption) {
        this.caption = caption;
    }

    public String getContentPath() {
        return contentPath;
    }

    public ImageContent contentPath(String contentPath) {
        this.contentPath = contentPath;
        return this;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public ImageContent subscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
        return this;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public ImageContent creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public ImageContent updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
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
        ImageContent imageContent = (ImageContent) o;
        if (imageContent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), imageContent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImageContent{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", caption='" + isCaption() + "'" +
            ", contentPath='" + getContentPath() + "'" +
            ", subscriptionType='" + getSubscriptionType() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
