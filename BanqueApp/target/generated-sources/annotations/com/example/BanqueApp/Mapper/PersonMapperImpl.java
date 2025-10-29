package com.example.BanqueApp.Mapper;

import com.example.BanqueApp.entity.Person;
import com.example.BanqueApp.model.createDTO.CreatePersonDTO;
import com.example.BanqueApp.model.readDTO.PersonDTO;
import java.time.LocalDate;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-29T15:01:32+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
public class PersonMapperImpl implements PersonMapper {

    @Override
    public PersonDTO toDTO(Person person) {
        if ( person == null ) {
            return null;
        }

        String nom = null;
        String prenom = null;
        String email = null;
        String telephone = null;
        String adresse = null;
        LocalDate date_naissance = null;

        nom = person.getNom();
        prenom = person.getPrenom();
        email = person.getEmail();
        telephone = person.getTelephone();
        adresse = person.getAdresse();
        date_naissance = person.getDate_naissance();

        PersonDTO personDTO = new PersonDTO( nom, prenom, email, telephone, adresse, date_naissance );

        return personDTO;
    }

    @Override
    public Person toEntity(CreatePersonDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Person.PersonBuilder person = Person.builder();

        person.nom( dto.nom() );
        person.prenom( dto.prenom() );
        person.email( dto.email() );
        person.telephone( dto.telephone() );
        person.adresse( dto.adresse() );
        person.date_naissance( dto.date_naissance() );

        return person.build();
    }
}
