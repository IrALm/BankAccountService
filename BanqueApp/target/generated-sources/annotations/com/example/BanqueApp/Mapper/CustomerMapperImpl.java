package com.example.BanqueApp.Mapper;

import com.example.BanqueApp.entity.Customer;
import com.example.BanqueApp.model.readDTO.CountDTO;
import com.example.BanqueApp.model.readDTO.CustomerDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T01:24:33+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Autowired
    private CountMapper countMapper;

    @Override
    public CustomerDTO toDTO(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        List<CountDTO> sesComptesDTO = null;
        Long id = null;
        String nom = null;
        String prenom = null;
        String telephone = null;
        String adresse = null;
        LocalDate dateNaissance = null;
        String email = null;

        sesComptesDTO = countMapper.toDTOList( customer.getSesComptes() );
        id = customer.getId();
        nom = customer.getNom();
        prenom = customer.getPrenom();
        telephone = customer.getTelephone();
        adresse = customer.getAdresse();
        dateNaissance = customer.getDateNaissance();
        email = customer.getEmail();

        CustomerDTO customerDTO = new CustomerDTO( id, nom, prenom, telephone, adresse, dateNaissance, email, sesComptesDTO );

        return customerDTO;
    }

    @Override
    public Customer toEntity(CustomerDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Customer.CustomerBuilder<?, ?> customer = Customer.builder();

        customer.sesComptes( countMapper.toEntityList( dto.sesComptesDTO() ) );
        customer.id( dto.id() );
        customer.email( dto.email() );
        customer.nom( dto.nom() );
        customer.prenom( dto.prenom() );
        customer.telephone( dto.telephone() );
        customer.adresse( dto.adresse() );
        customer.dateNaissance( dto.dateNaissance() );

        return customer.build();
    }

    @Override
    public List<CustomerDTO> toDTOList(List<Customer> users) {
        if ( users == null ) {
            return null;
        }

        List<CustomerDTO> list = new ArrayList<CustomerDTO>( users.size() );
        for ( Customer customer : users ) {
            list.add( toDTO( customer ) );
        }

        return list;
    }

    @Override
    public List<Customer> toEntityList(List<CustomerDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Customer> list = new ArrayList<Customer>( dtos.size() );
        for ( CustomerDTO customerDTO : dtos ) {
            list.add( toEntity( customerDTO ) );
        }

        return list;
    }
}
