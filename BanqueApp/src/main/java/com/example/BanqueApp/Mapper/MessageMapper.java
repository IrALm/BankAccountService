package com.example.BanqueApp.Mapper;

import com.example.BanqueApp.entity.BankAdvisor;
import com.example.BanqueApp.entity.Customer;
import com.example.BanqueApp.entity.Message;
import com.example.BanqueApp.model.readDTO.MessageDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Named("toDTO")
    @Mapping(source = "expediteur.id", target = "expediteurId")
    @Mapping(source = "destinataire.id", target = "destinataireId")
    @Mapping(source = "conversation.id", target = "conversationId")
    @Mapping(target = "expediteurNom", ignore = true)
    @Mapping(target = "destinataireNom", ignore = true)
    MessageDTO toDTO(Message message);

    @AfterMapping
    public default void fillNames(Message message, @MappingTarget MessageDTO dto) {
        String expediteurNom = null;
        String destinataireNom = null;

        if (message.getExpediteur() instanceof Customer c) {
            expediteurNom = c.getNom();
        } else if (message.getExpediteur() instanceof BankAdvisor b) {
            expediteurNom = b.getNom();
        }

        if (message.getDestinataire() instanceof Customer c) {
            destinataireNom = c.getNom();
        } else if (message.getDestinataire() instanceof BankAdvisor b) {
            destinataireNom = b.getNom();
        }

        // Crée un nouveau DTO avec les noms remplis
        dto = new MessageDTO(
                dto.id(),
                dto.contenu(),
                dto.dateEnvoi(),
                dto.lu(),
                dto.expediteurId(),
                expediteurNom,
                dto.destinataireId(),
                destinataireNom,
                dto.conversationId()
        );
    }

    @Named("toEntity")
    @Mapping(source = "expediteurId", target = "expediteur.id")
    @Mapping(source = "destinataireId", target = "destinataire.id")
    @Mapping(source = "conversationId", target = "conversation.id")
    Message toEntity(MessageDTO dto);

    @IterableMapping(qualifiedByName = "toDTO")
    List<MessageDTO> toDTOList(List<Message> messages);

    @IterableMapping(qualifiedByName = "toEntity")
    List<Message> toEntityList(List<MessageDTO> dtos);
}

