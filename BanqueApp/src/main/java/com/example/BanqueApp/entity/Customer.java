package com.example.BanqueApp.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.List;

@Entity //entité JPA
@Data // importer avec Lombock : pour les méthode toString , equals , getters et setters , etc
@NoArgsConstructor // Constructeur sans atribut en paramètre
@AllArgsConstructor // constructeur avec tous les attributs
@Builder // Lombok: pour la méthode build()
@Table( name = "clients")
@Schema( description = " Répresente un client dans le système ")
public class Customer {

    @Id
    private Long id;

    @OneToOne
    @MapsId // clé partagée avec Person
    @JoinColumn(name = "id")
    private Person person;

    @CreationTimestamp
    private java.time.LocalDateTime dateCreation;

    /* Rélation 1 client Plusieurs comptes*/

    @OneToMany(mappedBy = "client" , cascade = CascadeType.ALL , orphanRemoval = true)
    @Builder.Default // pour initialiser la liste avec le même builder
    private List<Count> sesComptes = new ArrayList<>();
}
