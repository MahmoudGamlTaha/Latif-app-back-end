package com.commerce.backend.dao;

import com.commerce.backend.model.entity.SubscriptionTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionTypesRepository extends JpaRepository<SubscriptionTypes, Long> {
}
