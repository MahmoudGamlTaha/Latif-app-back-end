package com.commerce.backend.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "report_reasons")
@Getter
@Setter
public class ReportReasons {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_reasons_id")
    @SequenceGenerator(name = "report_reasons_id", sequenceName = "report_reasons_id", allocationSize = 1)
    private Long id;

    @Column(name = "value")
    private String value;
    
    @Column(name = "value_ar")
    private String valueAr;
}
