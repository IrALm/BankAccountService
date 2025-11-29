package com.example.BanqueApp.repository;

import com.example.BanqueApp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /*@Query("SELECT c FROM Customer c WHERE c.email = :email")
    Optional<Customer> findByEmail(@Param("email") String email);*/

    @Query("SELECT c FROM Customer c WHERE c.telephone = :telephone")
    Optional<Customer> findByTelephone(@Param("telephone")String telephone);
    /* Recherche temporelle : client créer entre 2 dates */
    List<Customer> findByDateCreationBetween(LocalDateTime start , LocalDateTime end);


}
