package com.commerce.backend.dao;

import org.springframework.stereotype.Repository;

import com.commerce.backend.model.entity.UserChat;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
@Repository
public interface UserChatRepository extends JpaRepository<UserChat, UUID> {
 
	Page<UserChat> getUserChatByReciverId(Long user_id, Pageable page);
 
   Page<UserChat> getUserChatBySenderId(Long user_id, Pageable page);
 
   @Query(value= "SELECT uc FROM UserChat uc WHERE uc.senderId = ?1 AND uc.reciverId = ?2  and ad_id = ?3 order by createAt desc", countQuery = "SELECT count(*) FROM UserChat uc  WHERE uc.senderId = ?1 AND uc.reciverId = ?2")
   Page<UserChat> getUserChatBySenderIdAndReciverIdAndItemId(Long sender, Long reciver,Long ads, Pageable page);
 
   @Query(value = "SELECT * FROM  user_chats uc WHERE (uc.sender_id = ?1 AND uc.receiver_id = ?2  and uc.ad_id = ?3) OR (uc.sender_id = ?2 AND uc.receiver_id = ?1  and uc.ad_id = ?3) AND uc.room NOTNULL LIMIT 1", nativeQuery = true)
   UserChat getChatRoom(Long sender, Long reciver,Long ads);
   @Query(value= "SELECT uc FROM UserChat uc WHERE uc.room = ?1 ORDER BY createAt desc", countQuery="SELECT COUNT(*) from UserChat uc WHERE uc.room = ?1")
   Page<UserChat> findChatByRoom(String room ,Pageable page);
   
   @Query(value = "SELECT distinct on( uc.receiver_id) receiver_id ,uc.id, uc.device_model, uc.sender_id , uc.created_at, uc.message_text, uc.ad_id,uc.room FROM user_chats uc WHERE uc.room = ?1 AND uc.receiver_id != ?2", nativeQuery = true)
   List<UserChat> findReciverByChatRoom(String room, Long sender);
   
   @Query(value = "SELECT distinct on( uc.receiver_id) receiver_id ,uc.id, uc.device_model, uc.sender_id , uc.created_at, uc.message_text, uc.ad_id,uc.room FROM user_chats uc WHERE uc.room = ?1", nativeQuery = true)
   List<UserChat> findReciverByChatRoom(String room);
}
