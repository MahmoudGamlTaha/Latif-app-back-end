package com.commerce.backend.chatsys.repository;

import com.commerce.backend.chatsys.model.ChatMessage;
import com.commerce.backend.constants.MessageStatus;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
long countBySenderIdAndRecipientIdAndStatus(
    String senderId, String recipientId, MessageStatus status);

     List<ChatMessage> findByChatId(String chatId);
}