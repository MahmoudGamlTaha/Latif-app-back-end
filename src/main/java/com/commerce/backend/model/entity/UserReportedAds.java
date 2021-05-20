package com.commerce.backend.model.entity;

import javax.persistence.*;

import com.commerce.backend.constants.ReportType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "user_reported_ads")
@Data
//@DiscriminatorColumn(name ="type", discriminatorType = DiscriminatorType.STRING)
public class UserReportedAds {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reported_ads_id")
    @SequenceGenerator(name = "reported_ads_id", sequenceName = "reported_ads_id", allocationSize = 1)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "type")
	private ReportType reportType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ads_id")
    private UserAds ads;

    @Column(name = "reason")
    private String reason;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
