package com.example.BanqueApp.Mapper;

import com.example.BanqueApp.entity.BankAdvisor;
import com.example.BanqueApp.entity.Conversation;
import com.example.BanqueApp.entity.Customer;
import com.example.BanqueApp.model.readDTO.ConversationDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T21:31:24+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class ConversationMapperImpl implements ConversationMapper {

    @Override
    public ConversationDTO toDTO(Conversation conversation) {
        if ( conversation == null ) {
            return null;
        }

        Long clientId = null;
        String clientNom = null;
        Long conseillerId = null;
        String conseillerNom = null;
        Long id = null;
        LocalDateTime dateCreation = null;
        LocalDateTime dernierMessageDate = null;

        clientId = conversationClientId( conversation );
        clientNom = conversationClientNom( conversation );
        conseillerId = conversationConseillerId( conversation );
        conseillerNom = conversationConseillerNom( conversation );
        id = conversation.getId();
        dateCreation = conversation.getDateCreation();
        dernierMessageDate = conversation.getDernierMessageDate();

        long messagesNonLus = 0L;

        ConversationDTO conversationDTO = new ConversationDTO( id, clientId, clientNom, conseillerId, conseillerNom, dateCreation, dernierMessageDate, messagesNonLus );

        return conversationDTO;
    }

    @Override
    public Conversation toEntity(ConversationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Conversation.ConversationBuilder conversation = Conversation.builder();

        conversation.client( conversationDTOToCustomer( dto ) );
        conversation.conseiller( conversationDTOToBankAdvisor( dto ) );
        conversation.id( dto.id() );
        conversation.dateCreation( dto.dateCreation() );
        conversation.dernierMessageDate( dto.dernierMessageDate() );

        return conversation.build();
    }

    @Override
    public List<ConversationDTO> toDTOList(List<Conversation> conversations) {
        if ( conversations == null ) {
            return null;
        }

        List<ConversationDTO> list = new ArrayList<ConversationDTO>( conversations.size() );
        for ( Conversation conversation : conversations ) {
            list.add( toDTO( conversation ) );
        }

        return list;
    }

    @Override
    public List<Conversation> toEntityList(List<ConversationDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Conversation> list = new ArrayList<Conversation>( dtos.size() );
        for ( ConversationDTO conversationDTO : dtos ) {
            list.add( toEntity( conversationDTO ) );
        }

        return list;
    }

    private Long conversationClientId(Conversation conversation) {
        if ( conversation == null ) {
            return null;
        }
        Customer client = conversation.getClient();
        if ( client == null ) {
            return null;
        }
        Long id = client.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String conversationClientNom(Conversation conversation) {
        if ( conversation == null ) {
            return null;
        }
        Customer client = conversation.getClient();
        if ( client == null ) {
            return null;
        }
        String nom = client.getNom();
        if ( nom == null ) {
            return null;
        }
        return nom;
    }

    private Long conversationConseillerId(Conversation conversation) {
        if ( conversation == null ) {
            return null;
        }
        BankAdvisor conseiller = conversation.getConseiller();
        if ( conseiller == null ) {
            return null;
        }
        Long id = conseiller.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String conversationConseillerNom(Conversation conversation) {
        if ( conversation == null ) {
            return null;
        }
        BankAdvisor conseiller = conversation.getConseiller();
        if ( conseiller == null ) {
            return null;
        }
        String nom = conseiller.getNom();
        if ( nom == null ) {
            return null;
        }
        return nom;
    }

    protected Customer conversationDTOToCustomer(ConversationDTO conversationDTO) {
        if ( conversationDTO == null ) {
            return null;
        }

        Customer.CustomerBuilder<?, ?> customer = Customer.builder();

        customer.id( conversationDTO.clientId() );

        return customer.build();
    }

    protected BankAdvisor conversationDTOToBankAdvisor(ConversationDTO conversationDTO) {
        if ( conversationDTO == null ) {
            return null;
        }

        BankAdvisor.BankAdvisorBuilder<?, ?> bankAdvisor = BankAdvisor.builder();

        bankAdvisor.id( conversationDTO.conseillerId() );

        return bankAdvisor.build();
    }
}
