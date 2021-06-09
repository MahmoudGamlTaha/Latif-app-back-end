package com.commerce.backend.chatsys.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.commerce.backend.chatsys.model.ChatRoom;

import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);
}