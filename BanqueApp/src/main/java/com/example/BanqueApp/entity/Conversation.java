package com.example.BanqueApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "conversations")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Customer client;

    @ManyToOne
    @JoinColumn(name = "conseiller_id", nullable = false)
    private BankAdvisor conseiller;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime dateCreation;

    private LocalDateTime dernierMessageDate;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dateEnvoi ASC")
    @Builder.Default
    private List<Message> messages = new ArrayList<>();
}
