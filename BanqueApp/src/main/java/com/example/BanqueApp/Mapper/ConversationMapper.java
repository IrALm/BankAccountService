package com.example.BanqueApp.Mapper;

import com.example.BanqueApp.entity.Conversation;
import com.example.BanqueApp.model.readDTO.ConversationDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ConversationMapper {

    ConversationMapper INSTANCE = Mappers.getMapper(ConversationMapper.class);
    @Named("toDTO")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.nom", target = "clientNom")
    @Mapping(source = "conseiller.id", target = "conseillerId")
    @Mapping(source = "conseiller.nom", target = "conseillerNom")
    // messagesNonLus sera ajouté manuellement → ignore
    @Mapping(target = "messagesNonLus", ignore = true)
    ConversationDTO toDTO(Conversation conversation);

    @Named("toEntity")
    @Mapping(source = "clientId", target = "client.id")
    @Mapping(source = "conseillerId", target = "conseiller.id")
    // ces champs ne sont pas dans l’entité → ignore
    @Mapping(target = "client.nom", ignore = true)
    @Mapping(target = "conseiller.nom", ignore = true)
    @Mapping(target = "messages", ignore = true)
    Conversation toEntity(ConversationDTO dto);

    @IterableMapping(qualifiedByName = "toDTO")
    List<ConversationDTO> toDTOList(List<Conversation> conversations);

    @IterableMapping(qualifiedByName = "toEntity")
    List<Conversation> toEntityList(List<ConversationDTO> dtos);
}

