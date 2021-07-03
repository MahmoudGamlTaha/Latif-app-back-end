package com.commerce.backend.model.entity;

import com.commerce.backend.constants.PeriodUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subscription_types")
public class SubscriptionTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscrip_type_id")
    @SequenceGenerator(name="subscrip_type_id", sequenceName = "subscrip_type_id", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ads_number")
    private Integer adsNumber;

    @Column(name = "period_in_days")
    private Integer periodInDays;

    @Column(name = "price")
    private Float price;

    @Column(name = "number_user")
    private Integer numberUser;

    @Column(name = "description_en", length = 250)
    private String descriptionEn;
    
    @Column(name = "description_ar", length = 250)
    private String descriptionAr;
    
    @Column(name = "period_unit")
    @Enumerated(EnumType.STRING)
    private PeriodUnit periodUnit;

    @ManyToMany(mappedBy = "subscriptions", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

}
