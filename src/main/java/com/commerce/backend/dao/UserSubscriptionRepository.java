package com.commerce.backend.dao;

import com.commerce.backend.model.entity.SubscriptionTypes;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.entity.UserSubscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    @Query(value = "SELECT us FROM UserSubscription us WHERE us.endDate > :now and us.userId = :user")
    UserSubscription findByUserId(User user, Date now);
    Page<UserSubscription> findUserSubscriptionByUserId(User userId, Pageable pageable);
    Page<UserSubscription> findUserSubscriptionBySubscriptionId(SubscriptionTypes sub, Pageable pageable);
    Long countByEndDateGreaterThan(Date now);
}
