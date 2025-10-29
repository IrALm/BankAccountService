package com.example.BanqueApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity //entité JPA
@Data // importer avec Lombock : pour les méthode toString , equals , getters et setters , etc
@NoArgsConstructor // Constructeur sans atribut en paramètre
@AllArgsConstructor // constructeur avec tous les attributs
@Table( name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le type de transaction est obligatoire")
    @Enumerated(EnumType.STRING)
    private TypeTransaction typetransaction;

    @NotNull
    private BigDecimal montant;

    @CreationTimestamp
    private LocalDateTime dateTansaction;

    /* compte source de la transaction */
    @ManyToOne
    @JoinColumn( name ="compte_id" , nullable = false)
    private Count compteSource;

    /* Compte destinataire : nullable dans le cadre du dépot ou retrait */

    @ManyToOne
    @JoinColumn( name ="compte_dest_id")
    private Count compteDest;
}
