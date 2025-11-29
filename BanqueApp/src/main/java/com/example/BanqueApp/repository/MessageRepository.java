package com.example.BanqueApp.repository;

import com.example.BanqueApp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId ORDER BY m.dateEnvoi ASC ")
    List<Message> findMessagesByConversationId(@Param("conversationId")Long conversationId);

    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId AND m.destinataire.id = :userId AND m.lu = false ")
    List<Message> findUnreadMessagesByConversationAndUser(Long conversationId, Long userId);

    @Query("""
    SELECT COUNT(m)
    FROM Message m
    WHERE m.conversation.id = :conversationId
      AND m.destinataire.id = :userId
      AND m.lu = false
    """)
    long countUnreadMessages(
            @Param("conversationId") Long conversationId,
            @Param("userId") Long userId
    );

    @Query("""
    SELECT COUNT(m)
    FROM Message m
    WHERE m.destinataire.id = :destinataireId
      AND m.lu = false
    """)
    long countUnreadMessagesByDestinataire(
            @Param("destinataireId") Long destinataireId
    );

}
