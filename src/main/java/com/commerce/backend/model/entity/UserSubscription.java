package com.commerce.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
//@AllArgsConstructor
@Table(name = "user_subscription")
public class UserSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_subscription_id")
    @SequenceGenerator(name = "user_subscription_id", sequenceName = "user_subscription_id", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    private SubscriptionTypes subscriptionId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "updated_at")
    private Date updatedAt;
}
