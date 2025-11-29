package com.example.BanqueApp.model.createDTO;

import lombok.Data;

@Data
public class ChatMessageDTO {
    private Long conversationId;
    private Long senderId;
    private String senderName;
    private String content;
    private Long messageId;
    private String timestamp;
}
