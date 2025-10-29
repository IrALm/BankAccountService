package com.example.BanqueApp.service.impl;

import com.example.BanqueApp.Mapper.CountMapper;
import com.example.BanqueApp.Mapper.CustomerMapper;
import com.example.BanqueApp.Mapper.PersonMapper;
import com.example.BanqueApp.entity.Count;
import com.example.BanqueApp.entity.Customer;
import com.example.BanqueApp.entity.Person;
import com.example.BanqueApp.model.createDTO.CreateCountDTO;
import com.example.BanqueApp.model.createDTO.CreatePersonDTO;
import com.example.BanqueApp.model.createDTO.CustomerWithCount;
import com.example.BanqueApp.model.readDTO.CustomerDTO;
import com.example.BanqueApp.model.readDTO.CountDTO;
import com.example.BanqueApp.repository.CustomerRepository;
import com.example.BanqueApp.repository.PersonRepository;
import com.example.BanqueApp.service.CustomerService;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Data
public class CustomerImplService implements CustomerService {

    private final CustomerRepository clientRepository;
    private final PersonRepository personRepository;

    /* Ajout d'un nouveau client */
    @Transactional /* nécessaire car on doit sauvegarder un client dans la base de données :
    si tout se passe bien la transaction est commis
    s'il y a une exception , il ya un rollback(rien n'est enregistré dans la base de données */

    @Override
    public CustomerDTO creationNouveauClient(CustomerWithCount customerWithCount){

        // Persiste d'abord la personne
        Person person = PersonMapper.INSTANCE.toEntity(customerWithCount.client());
        person = personRepository.save(person);

        // Crée le client
        Customer client = Customer.builder()
                .person(person)
                .build();

        // Crée le compte
        Count compte = Count.builder()
                .client(client)
                .solde(customerWithCount.compte().solde())
                .typeCompte(customerWithCount.compte().typeCompte())
                .numeroCompte(customerWithCount.compte().numeroCompte())
                .build();
        client.getSesComptes().add(compte);

        // Persiste le client
        Customer saved = clientRepository.save(client);

        return CustomerMapper.INSTANCE.toDTO(saved);
    }


    @Override
    public List<CustomerDTO> tousLesClients() {

        return CustomerMapper.INSTANCE.toDTOList(clientRepository.findAll());
    }
}

