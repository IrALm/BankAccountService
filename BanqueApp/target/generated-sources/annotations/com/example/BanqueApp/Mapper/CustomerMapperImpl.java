package com.example.BanqueApp.Mapper;

import com.example.BanqueApp.entity.Customer;
import com.example.BanqueApp.entity.Person;
import com.example.BanqueApp.model.readDTO.CountDTO;
import com.example.BanqueApp.model.readDTO.CustomerDTO;
import com.example.BanqueApp.model.readDTO.PersonDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-29T19:20:11+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
public class CustomerMapperImpl implements CustomerMapper {

    private final PersonMapper personMapper = PersonMapper.INSTANCE;
    private final CountMapper countMapper = CountMapper.INSTANCE;

    @Override
    public CustomerDTO toDTO(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        PersonDTO personDTO = null;
        List<CountDTO> sesComptesDTO = null;

        personDTO = personMapper.toDTO( customer.getPerson() );
        sesComptesDTO = countMapper.toDTOList( customer.getSesComptes() );

        CustomerDTO customerDTO = new CustomerDTO( personDTO, sesComptesDTO );

        return customerDTO;
    }

    @Override
    public Customer toEntity(CustomerDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.person( personDTOToPerson( dto.personDTO() ) );
        customer.sesComptes( countMapper.toEntityList( dto.sesComptesDTO() ) );

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

    protected Person personDTOToPerson(PersonDTO personDTO) {
        if ( personDTO == null ) {
            return null;
        }

        Person.PersonBuilder person = Person.builder();

        person.nom( personDTO.nom() );
        person.prenom( personDTO.prenom() );
        person.email( personDTO.email() );
        person.telephone( personDTO.telephone() );
        person.adresse( personDTO.adresse() );
        person.date_naissance( personDTO.date_naissance() );

        return person.build();
    }
}
