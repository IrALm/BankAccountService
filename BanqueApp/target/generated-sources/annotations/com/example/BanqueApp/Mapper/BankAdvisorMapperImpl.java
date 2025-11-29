package com.example.BanqueApp.Mapper;

import com.example.BanqueApp.entity.BankAdvisor;
import com.example.BanqueApp.model.readDTO.BankAdvisorDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T04:10:09+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class BankAdvisorMapperImpl implements BankAdvisorMapper {

    @Override
    public BankAdvisorDTO toDTO(BankAdvisor bankAdvisor) {
        if ( bankAdvisor == null ) {
            return null;
        }

        Long id = null;
        String nom = null;
        String prenom = null;
        String telephone = null;
        String adresse = null;
        LocalDate dateNaissance = null;
        String email = null;
        String matricule = null;

        id = bankAdvisor.getId();
        nom = bankAdvisor.getNom();
        prenom = bankAdvisor.getPrenom();
        telephone = bankAdvisor.getTelephone();
        adresse = bankAdvisor.getAdresse();
        dateNaissance = bankAdvisor.getDateNaissance();
        email = bankAdvisor.getEmail();
        matricule = bankAdvisor.getMatricule();

        BankAdvisorDTO bankAdvisorDTO = new BankAdvisorDTO( id, nom, prenom, telephone, adresse, dateNaissance, email, matricule );

        return bankAdvisorDTO;
    }

    @Override
    public BankAdvisor toEntity(BankAdvisorDTO bankAdvisorDTO) {
        if ( bankAdvisorDTO == null ) {
            return null;
        }

        BankAdvisor.BankAdvisorBuilder<?, ?> bankAdvisor = BankAdvisor.builder();

        bankAdvisor.id( bankAdvisorDTO.id() );
        bankAdvisor.email( bankAdvisorDTO.email() );
        bankAdvisor.nom( bankAdvisorDTO.nom() );
        bankAdvisor.prenom( bankAdvisorDTO.prenom() );
        bankAdvisor.telephone( bankAdvisorDTO.telephone() );
        bankAdvisor.adresse( bankAdvisorDTO.adresse() );
        bankAdvisor.dateNaissance( bankAdvisorDTO.dateNaissance() );
        bankAdvisor.matricule( bankAdvisorDTO.matricule() );

        return bankAdvisor.build();
    }

    @Override
    public List<BankAdvisorDTO> toDTOList(List<BankAdvisor> bankAdvisors) {
        if ( bankAdvisors == null ) {
            return null;
        }

        List<BankAdvisorDTO> list = new ArrayList<BankAdvisorDTO>( bankAdvisors.size() );
        for ( BankAdvisor bankAdvisor : bankAdvisors ) {
            list.add( toDTO( bankAdvisor ) );
        }

        return list;
    }

    @Override
    public List<BankAdvisor> toEntityList(List<BankAdvisorDTO> bankAdvisorDTOs) {
        if ( bankAdvisorDTOs == null ) {
            return null;
        }

        List<BankAdvisor> list = new ArrayList<BankAdvisor>( bankAdvisorDTOs.size() );
        for ( BankAdvisorDTO bankAdvisorDTO : bankAdvisorDTOs ) {
            list.add( toEntity( bankAdvisorDTO ) );
        }

        return list;
    }
}
