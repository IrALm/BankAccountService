package com.example.BanqueApp.repository;

import com.example.BanqueApp.entity.Conversation;
import com.example.BanqueApp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByConversationIdOrderByDateEnvoiAsc(Long conversationId);

    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId AND m.destinataire.id = :userId AND m.lu = false")
    List<Message> findUnreadMessagesByConversationAndUser(Long conversationId, Long userId);

    long countByConversationIdAndDestinataire_IdAndLuFalse(Long conversationId, Long userId);
}
