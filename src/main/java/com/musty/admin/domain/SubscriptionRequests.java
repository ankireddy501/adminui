package com.musty.admin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.musty.admin.domain.enumeration.SubscriptionStatus;


/**
 * A SubscriptionRequests.
 */
@Entity
@Table(name = "subscription_requests")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubscriptionRequests implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SubscriptionStatus status;

    @Column(name = "requested_date")
    private LocalDate requestedDate;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    @Column(name = "subscription_amount")
    private Integer subscriptionAmount;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public SubscriptionRequests status(SubscriptionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public LocalDate getRequestedDate() {
        return requestedDate;
    }

    public SubscriptionRequests requestedDate(LocalDate requestedDate) {
        this.requestedDate = requestedDate;
        return this;
    }

    public void setRequestedDate(LocalDate requestedDate) {
        this.requestedDate = requestedDate;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public SubscriptionRequests approvalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
        return this;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Integer getSubscriptionAmount() {
        return subscriptionAmount;
    }

    public SubscriptionRequests subscriptionAmount(Integer subscriptionAmount) {
        this.subscriptionAmount = subscriptionAmount;
        return this;
    }

    public void setSubscriptionAmount(Integer subscriptionAmount) {
        this.subscriptionAmount = subscriptionAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public SubscriptionRequests startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public SubscriptionRequests endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public SubscriptionRequests user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        SubscriptionRequests subscriptionRequests = (SubscriptionRequests) o;
        if (subscriptionRequests.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subscriptionRequests.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubscriptionRequests{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", requestedDate='" + getRequestedDate() + "'" +
            ", approvalDate='" + getApprovalDate() + "'" +
            ", subscriptionAmount=" + getSubscriptionAmount() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
