package com.commerce.backend.dao;

import org.springframework.stereotype.Repository;

import com.commerce.backend.model.entity.UserChat;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
@Repository
public interface UserChatRepository extends JpaRepository<UserChat, UUID> {
 Page<UserChat> getUserChatByReciverId(Long user_id, Pageable page);
 Page<UserChat> getUserChatBySenderId(Long user_id, Pageable page);
 @Query(value="SELECT uc FROM UserChat uc WHERE uc.senderId = ?1 AND uc.reciverId = ?2", countQuery = "SELECT count(*) FROM UserChat uc  WHERE uc.senderId = ?1 AND uc.reciverId = ?2")
 Page<UserChat> getUserChatBySenderIdAndReciverIdAndItemId(Long sender, Long reciver, Pageable page);
}
