package com.commerce.backend.dao;

import com.commerce.backend.model.entity.SubscriptionTypes;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.entity.UserSubscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    Page<UserSubscription> findUserSubscriptionByUserId(User userId, Pageable pageable);
    Page<UserSubscription> findUserSubscriptionBySubscriptionId(SubscriptionTypes sub, Pageable pageable);
}
