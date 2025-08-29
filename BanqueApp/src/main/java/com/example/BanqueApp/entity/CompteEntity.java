package com.example.BanqueApp.entity;


import com.example.BanqueApp.model.TypeCompte;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Entity //entité JPA
@Data // importer avec Lombock : pour les méthode toString , equals , getters et setters , etc
@NoArgsConstructor // Constructeur sans atribut en paramètre
@AllArgsConstructor // constructeur avec tous les attributs
@Table( name = "comptes" , uniqueConstraints
        = { @UniqueConstraint(columnNames = "numeroCompte")
        /* Pour la contrainte d'unicité : numeroCompte doit étre unique*/
})
public class CompteEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String numeroCompte;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private TypeCompte typeCompte  ;

    @NotNull
    private BigDecimal solde = BigDecimal.ZERO;

    @CreationTimestamp
    private LocalDateTime dateCreation;

    /* Relation N compte -> 1 client */
    @ManyToOne
    @JoinColumn( name = "client_id" , nullable = false)
    private ClientEntity client;

    /* Relation 1 compte -> Plusieurs transactions
    *  mappedBy indique quelle propriété de l'entité
    *  transaction est responsable de la rélation
    *  cascade = CascadeType.ALL : les opérations persist , merge , remove , refresh , detach
    *  faite sur le compte seront propagés automatiquement aux transactions associées../
    *  orphanRemoval = true: si une transaction est rétirée , Hibernate la considère comme
    *  orpheline et la supprime automatiquement de la base...
    * */
    @OneToMany( mappedBy = "compteSource" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Transaction> transactionSource;

    @OneToMany( mappedBy = "compteDest" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Transaction> transactionDest;

}
