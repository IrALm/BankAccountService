package com.example.BanqueApp.Mapper;

import com.example.BanqueApp.entity.Count;
import com.example.BanqueApp.model.readDTO.CountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CountMapper {

    CountMapper INSTANCE = Mappers.getMapper(CountMapper.class);

    CountDTO toDTO( Count count);
    Count toEntity( CountDTO dto);

    // Mapping pour les listes
    List<CountDTO> toDTOList(List<Count> users);
    List<Count> toEntityList(List<CountDTO> dtos);
}
