package com.example.BanqueApp.Mapper;

import com.example.BanqueApp.entity.Customer;
import com.example.BanqueApp.model.readDTO.CustomerDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CountMapper.class})
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Named("toDTO")
    @Mapping(source = "sesComptes", target ="sesComptesDTO")
    CustomerDTO toDTO(Customer customer);

    @Named("toEntity")
    @Mapping(source = "sesComptesDTO", target ="sesComptes")
    Customer toEntity(CustomerDTO dto);

    // Mapping pour les listes
    @IterableMapping(qualifiedByName = "toDTO")
    List<CustomerDTO> toDTOList(List<Customer> customers);

    @IterableMapping(qualifiedByName = "toEntity")
    List<Customer> toEntityList(List<CustomerDTO> dtos);
}
