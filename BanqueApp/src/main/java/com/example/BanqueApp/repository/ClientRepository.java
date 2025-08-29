package com.example.BanqueApp.repository;

import com.example.BanqueApp.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity , Long> {

    /* Quelques méthodes personalisées qui me seront utiles :
    *
    *  1. Pour faire des recherches simples : Email ou juste numéro de téléphone
    *
    * */

    Optional<ClientEntity> findByEmail(String email);
    Optional<ClientEntity> findByTelephone(String telephone);

    /* Recherche par nom ou prénom : exact et insensible à la casse */

    List<ClientEntity> findByNomIgnoreCase(String nom);
    List<ClientEntity> findByPrenomIgnoreCase(String prenom);

    /* Recherche par nom ou prenom partiel et insensible à la casse */

    List<ClientEntity> findByNomContainingIgnoreCase(String nom);
    List<ClientEntity> findByPrenomContainingIgnoreCase(String prenom);

    /* Recherches combinées */

    List<ClientEntity> findByEmailOrTelephone( String email , String telephone);
    List<ClientEntity> findByNomContainingIgnoreCaseAndPrenomContainingIgnoreCase( String nom , String prenom);

    /* Recherche temporelle : client créer entre 2 dates */
    List<ClientEntity> findByDateCreationBetween(LocalDateTime start , LocalDateTime end);

    /* Vérification de l'existence d'un client : gràce à son mail ou son numéro de téléphone */

    boolean existsByEmail( String email);
    boolean existsByTelephone( String telephone);

    /* Suppression personalisée : avec un Email */

    void deleteByEmail( String email);

}
