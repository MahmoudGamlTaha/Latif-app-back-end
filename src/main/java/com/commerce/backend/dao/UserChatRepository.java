package com.commerce.backend.dao;

import org.springframework.stereotype.Repository;

import com.commerce.backend.model.entity.UserChat;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface UserChatRepository extends JpaRepository<UserChat, UUID> {
 Page<UserChat> getUserChatByReciverId(Long user_id, Pageable page);
 Page<UserChat> getUserChatBySenderId(Long user_id, Pageable page);
 
 Page<UserChat> getUserChatBySenderReciverAds(Long sender, Long reciver, Pageable page);
}
