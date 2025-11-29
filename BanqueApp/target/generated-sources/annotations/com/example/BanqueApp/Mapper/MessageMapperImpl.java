package com.example.BanqueApp.Mapper;

import com.example.BanqueApp.entity.Conversation;
import com.example.BanqueApp.entity.Message;
import com.example.BanqueApp.entity.User;
import com.example.BanqueApp.model.readDTO.MessageDTO;
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
public class MessageMapperImpl implements MessageMapper {

    @Override
    public MessageDTO toDTO(Message message) {
        if ( message == null ) {
            return null;
        }

        Long expediteurId = null;
        Long destinataireId = null;
        Long conversationId = null;
        Long id = null;
        String contenu = null;
        LocalDateTime dateEnvoi = null;
        boolean lu = false;

        expediteurId = messageExpediteurId( message );
        destinataireId = messageDestinataireId( message );
        conversationId = messageConversationId( message );
        id = message.getId();
        contenu = message.getContenu();
        dateEnvoi = message.getDateEnvoi();
        lu = message.isLu();

        String expediteurNom = null;
        String destinataireNom = null;

        MessageDTO messageDTO = new MessageDTO( id, contenu, dateEnvoi, lu, expediteurId, expediteurNom, destinataireId, destinataireNom, conversationId );

        fillNames( message, messageDTO );

        return messageDTO;
    }

    @Override
    public Message toEntity(MessageDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Message.MessageBuilder message = Message.builder();

        message.expediteur( messageDTOToUser( dto ) );
        message.destinataire( messageDTOToUser1( dto ) );
        message.conversation( messageDTOToConversation( dto ) );
        message.id( dto.id() );
        message.contenu( dto.contenu() );
        message.dateEnvoi( dto.dateEnvoi() );
        message.lu( dto.lu() );

        return message.build();
    }

    @Override
    public List<MessageDTO> toDTOList(List<Message> messages) {
        if ( messages == null ) {
            return null;
        }

        List<MessageDTO> list = new ArrayList<MessageDTO>( messages.size() );
        for ( Message message : messages ) {
            list.add( toDTO( message ) );
        }

        return list;
    }

    @Override
    public List<Message> toEntityList(List<MessageDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Message> list = new ArrayList<Message>( dtos.size() );
        for ( MessageDTO messageDTO : dtos ) {
            list.add( toEntity( messageDTO ) );
        }

        return list;
    }

    private Long messageExpediteurId(Message message) {
        if ( message == null ) {
            return null;
        }
        User expediteur = message.getExpediteur();
        if ( expediteur == null ) {
            return null;
        }
        Long id = expediteur.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long messageDestinataireId(Message message) {
        if ( message == null ) {
            return null;
        }
        User destinataire = message.getDestinataire();
        if ( destinataire == null ) {
            return null;
        }
        Long id = destinataire.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long messageConversationId(Message message) {
        if ( message == null ) {
            return null;
        }
        Conversation conversation = message.getConversation();
        if ( conversation == null ) {
            return null;
        }
        Long id = conversation.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected User messageDTOToUser(MessageDTO messageDTO) {
        if ( messageDTO == null ) {
            return null;
        }

        User.UserBuilder<?, ?> user = User.builder();

        user.id( messageDTO.expediteurId() );

        return user.build();
    }

    protected User messageDTOToUser1(MessageDTO messageDTO) {
        if ( messageDTO == null ) {
            return null;
        }

        User.UserBuilder<?, ?> user = User.builder();

        user.id( messageDTO.destinataireId() );

        return user.build();
    }

    protected Conversation messageDTOToConversation(MessageDTO messageDTO) {
        if ( messageDTO == null ) {
            return null;
        }

        Conversation.ConversationBuilder conversation = Conversation.builder();

        conversation.id( messageDTO.conversationId() );

        return conversation.build();
    }
}
