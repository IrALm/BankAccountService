package com.example.BanqueApp.service;

import com.example.BanqueApp.entity.ClientEntity;
import com.example.BanqueApp.entity.CompteEntity;
import com.example.BanqueApp.model.ClientDTO;
import com.example.BanqueApp.model.CompteDTO;
import com.example.BanqueApp.model.CreateClientDTO;
import com.example.BanqueApp.model.CreateCompteDTO;
import com.example.BanqueApp.repository.ClientRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class ClientService {

    private final ClientRepository clientRepository;

    /* Ajout d'un nouveau client */
    @Transactional /* nécessaire car on doit sauvegarder un client dans la base de données :
    si tout se passe bien la transaction est commis
    s'il y a une exception , il ya un rollback(rien n'est enregistré dans la base de données */

    public ClientDTO creationNouveauClient(CreateClientDTO nouveauClient , CreateCompteDTO compteClient){

        /* Grâce à Lombok, on utilise build au lieu d'utiliser le constructeur tranditionnel pour
             recupéere les information du nouveau client
         */

        ClientEntity  client = clientRepository.findByEmail(compteClient.emailClient())
                .orElse(null);
        if( client == null) {
            client = ClientEntity.builder()
                    .nom(nouveauClient.nom())
                    .prenom(nouveauClient.prenom())
                    .email(nouveauClient.email())
                    .adresse(nouveauClient.adresse())
                    .date_naissance(nouveauClient.date_naissance())
                    .telephone(nouveauClient.telephone())
                    .build();
        }
        /* Création du compte */

        CompteEntity compte = CompteEntity.builder()
                .client(client)
                .solde(compteClient.solde())
                .typeCompte(compteClient.typeCompte())
                .numeroCompte(compteClient.numeroCompte())
                .build();
        client.getSesComptes().add(compte);
        ClientEntity saved= clientRepository.save(client);

        /* conversion au DTO de lecture à renvoyer */

        List<CompteDTO> comptes = saved.getSesComptes().stream()
                .map( c -> new CompteDTO(c.getNumeroCompte() , c.getTypeCompte() , c.getSolde()) )
                .collect(Collectors.toList());
        return new ClientDTO(saved.getNom(), saved.getPrenom(), saved.getEmail(), saved.getTelephone(), saved.getAdresse(),
                saved.getDate_naissance() , comptes);

    }

    @Transactional( readOnly = true)
    public List<ClientDTO> tousLesClients(){

        return clientRepository.findAll().stream()
                .map( c -> new ClientDTO(c.getNom(),
                        c.getPrenom(),
                        c.getEmail(),
                        c.getTelephone(),
                        c.getAdresse(),
                        c.getDate_naissance(),
                        c.getSesComptes().stream()
                                .map(a -> new CompteDTO(a.getNumeroCompte(),
                                        a.getTypeCompte(),
                                        a.getSolde()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}

