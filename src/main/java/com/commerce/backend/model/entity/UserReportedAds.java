package com.commerce.backend.model.entity;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

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
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "type")
	private ReportType reportType;
    
    @JsonBackReference 
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "ads_id")
    private UserAds reportedAds;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "reason", referencedColumnName = "id")
    private ReportReasons reason;
    
    @Column(name = "other")
    private String otherReason;
    
    @Column(name = "created_at")
    @Type(type = "timestamp")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
