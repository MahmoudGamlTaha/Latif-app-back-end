package com.commerce.backend.dao;

import com.commerce.backend.model.entity.ReportReasons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportReasonsRepository extends JpaRepository<ReportReasons, Long> {
}
