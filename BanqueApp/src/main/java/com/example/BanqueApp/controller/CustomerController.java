package com.example.BanqueApp.controller;

import com.example.BanqueApp.model.createDTO.CustomerWithCount;
import com.example.BanqueApp.model.readDTO.CustomerDTO;
import com.example.BanqueApp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor // génère un constructeur avec le champ final
// au lieu d'utiliser @Data car il généera des setters et getters inutiles
@RestController // indique que cette classe est un controller Rest et que les méthodes retournent directement des objects JSON( et non des vues HTML)
@RequestMapping("/api/clients") // définit le chemin de base pour toutes les routes de ce controller
public class CustomerController {

    private final CustomerService clientService; // final pour montrer que la dépendance ne change pas

    @PostMapping("/nouveau")
    /* @RequestBody CreateClientDTO dto : spring convertit automatiquemnt le JSON recu en objet CreateClientDTO mais il faut que
    * les noms de champ du JSON soit identique à celui du DTO sinon erreur
    * ResponseEntity.ok : réponse HTTP 200 OK
    * */
    public ResponseEntity<CustomerDTO> ajoutNouveauClient(@RequestBody CustomerWithCount dto){

        return ResponseEntity.ok( clientService.creationNouveauClient(dto));

    }

    @GetMapping("/tousLesClients")
    public List<CustomerDTO> tousLesClients(){
        return clientService.tousLesClients();
    }

}
