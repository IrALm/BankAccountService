package com.example.BanqueApp.Mapper;

import com.example.BanqueApp.entity.Customer;
import com.example.BanqueApp.model.readDTO.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CountMapper.class})
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(source = "sesComptes", target ="sesComptesDTO")
    CustomerDTO toDTO(Customer customer);

    @Mapping(source = "sesComptesDTO", target ="sesComptes")
    Customer toEntity(CustomerDTO dto);

    // Mapping pour les listes
    List<CustomerDTO> toDTOList(List<Customer> users);
    List<Customer> toEntityList(List<CustomerDTO> dtos);
}
