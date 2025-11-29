package com.example.BanqueApp.repository;

import com.example.BanqueApp.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Optional<Conversation> findByClientIdAndConseillerId(Long clientId, Long conseillerId);

    List<Conversation> findByClientIdOrderByDernierMessageDateDesc(Long clientId);

    List<Conversation> findByConseillerIdOrderByDernierMessageDateDesc(Long conseillerId);

    @Query("SELECT c FROM Conversation c WHERE c.client.id = :userId OR c.conseiller.id = :userId ORDER BY c.dernierMessageDate DESC")
    List<Conversation> findAllByUserIdOrderByDernierMessageDateDesc(Long userId);
}
