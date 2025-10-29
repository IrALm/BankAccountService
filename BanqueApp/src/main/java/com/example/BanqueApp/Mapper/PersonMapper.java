package com.example.BanqueApp.Mapper;


import com.example.BanqueApp.entity.Person;
import com.example.BanqueApp.model.createDTO.CreatePersonDTO;
import com.example.BanqueApp.model.readDTO.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDTO toDTO(Person person);
    Person toEntity(CreatePersonDTO dto);
}
