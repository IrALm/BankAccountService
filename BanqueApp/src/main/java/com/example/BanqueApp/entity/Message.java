package com.example.BanqueApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le contenu du message ne peut pas être vide")
    @Column(columnDefinition = "TEXT")
    private String contenu;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime dateEnvoi;

    @Builder.Default
    private boolean lu = false;

    @ManyToOne
    @JoinColumn(name = "expediteur_id", nullable = false)
    @NotNull
    private User expediteur;

    @ManyToOne
    @JoinColumn(name = "destinataire_id", nullable = false)
    @NotNull
    private User destinataire;

    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    @NotNull
    private Conversation conversation;
}
