package com.example.BanqueApp.Mapper;

import com.example.BanqueApp.entity.BankAdvisor;
import com.example.BanqueApp.model.readDTO.BankAdvisorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankAdvisorMapper {

    BankAdvisorMapper  INSTANCE = Mappers.getMapper(BankAdvisorMapper.class);

    BankAdvisorDTO toDTO(BankAdvisor bankAdvisor);
    BankAdvisor toEntity(BankAdvisorDTO bankAdvisorDTO);
    List<BankAdvisorDTO> toDTOList(List<BankAdvisor> bankAdvisors);
    List<BankAdvisor> toEntityList(List<BankAdvisorDTO> bankAdvisorDTOs);
}
