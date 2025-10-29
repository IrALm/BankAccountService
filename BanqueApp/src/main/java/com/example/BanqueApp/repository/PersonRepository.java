package com.example.BanqueApp.repository;

import com.example.BanqueApp.entity.Customer;
import com.example.BanqueApp.entity.Person;
import com.example.BanqueApp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    /* Quelques méthodes personalisées qui me seront utiles :
     *
     *  1. Pour faire des recherches simples : Email ou juste numéro de téléphone
     *
     * */

    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByTelephone(String telephone);

    /* Recherche par nom ou prénom : exact et insensible à la casse */

    List<Customer> findByNomIgnoreCase(String nom);
    List<Customer> findByPrenomIgnoreCase(String prenom);

    /* Recherche par nom ou prenom partiel et insensible à la casse */

    List<Customer> findByNomContainingIgnoreCase(String nom);
    List<Customer> findByPrenomContainingIgnoreCase(String prenom);

    /* Vérification de l'existence d'un client : gràce à son mail ou son numéro de téléphone */

    boolean existsByEmail( String email);
    boolean existsByTelephone( String telephone);

    /* Suppression personalisée : avec un Email */
    void deleteByEmail( String email);

    /* Recherches combinées */

    List<Customer> findByEmailOrTelephone(String email , String telephone);
    List<Customer> findByNomContainingIgnoreCaseAndPrenomContainingIgnoreCase( String nom , String prenom);
}
