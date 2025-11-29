let stompClient = null;
let currentConversationId = null;
const userId = document.getElementById('user-id').value;
const userRole = document.getElementById('user-role').value;

// Connexion WebSocket au chargement de la page
document.addEventListener('DOMContentLoaded', function() {
    connectWebSocket();
    loadConversations();
});

function connectWebSocket() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        
        // S'abonner aux messages pour cet utilisateur
        stompClient.subscribe('/queue/messages/' + userId, function (message) {
            const messageData = JSON.parse(message.body);
            displayMessage(messageData);
        });
    }, function(error) {
        console.error('WebSocket error:', error);
        setTimeout(connectWebSocket, 5000); // Reconnexion après 5 secondes
    });
}

function loadConversations() {
    fetch('/api/messaging/conversations/user/' + userId)
        .then(response => response.json())
        .then(conversations => {
            if (userRole === 'CONSEILLER_BANCAIRE') {
                displayConversations(conversations);
            } else {
                // Pour les clients, charger directement la conversation
                if (conversations.length > 0) {
                    loadConversation(conversations[0].id);
                }
            }
        })
        .catch(error => console.error('Error loading conversations:', error));
}

function displayConversations(conversations) {
    const listElement = document.getElementById('conversations-list');
    listElement.innerHTML = '';
    
    conversations.forEach(conv => {
        const convElement = document.createElement('div');
        convElement.className = 'conversation-item';
        if (conv.messagesNonLus > 0) {
            convElement.classList.add('unread');
        }
        
        convElement.innerHTML = `
            <div class="conversation-name">${conv.clientNom}</div>
            <div class="conversation-info">
                ${conv.messagesNonLus > 0 ? `<span class="unread-badge">${conv.messagesNonLus}</span>` : ''}
            </div>
        `;
        
        convElement.onclick = () => loadConversation(conv.id);
        listElement.appendChild(convElement);
    });
}

function loadConversation(conversationId) {
    currentConversationId = conversationId;
    
    fetch('/api/messaging/conversation/' + conversationId + '/messages')
        .then(response => response.json())
        .then(messages => {
            displayMessages(messages);
            // Marquer tous les messages comme lus
            markAllAsRead(conversationId);
        })
        .catch(error => console.error('Error loading messages:', error));
}

function displayMessages(messages) {
    const container = document.getElementById('messages-container');
    container.innerHTML = '';
    
    messages.forEach(message => {
        displayMessage(message);
    });
    
    // Scroll vers le bas
    container.scrollTop = container.scrollHeight;
}

function displayMessage(message) {
    const container = document.getElementById('messages-container');
    const messageElement = document.createElement('div');
    
    const isOwnMessage = message.expediteurId == userId;
    messageElement.className = 'message ' + (isOwnMessage ? 'message-sent' : 'message-received');
    
    const date = new Date(message.dateEnvoi);
    const timeString = date.toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' });
    
    messageElement.innerHTML = `
        <div class="message-bubble">
            <div class="message-content">${escapeHtml(message.contenu)}</div>
            <div class="message-time">${timeString}</div>
        </div>
    `;
    
    container.appendChild(messageElement);
    container.scrollTop = container.scrollHeight;
}

function markAllAsRead(conversationId) {
    fetch('/api/messaging/conversation/' + conversationId + '/mark-all-read/' + userId, {
        method: 'POST'
    }).catch(error => console.error('Error marking messages as read:', error));
}

// Envoi de message
document.getElementById('message-form').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const input = document.getElementById('message-input');
    const content = input.value.trim();
    
    if (content && currentConversationId && stompClient) {
        // Récupérer l'ID du destinataire en fonction du rôle
        getDestinataire(currentConversationId).then(destinataireId => {
            const messageDTO = {
                conversationId: currentConversationId,
                contenu: content,
                expediteurId: userId,
                destinataireId: destinataireId
            };
            
            stompClient.send("/app/chat", {}, JSON.stringify(messageDTO));
            input.value = '';
        });
    }
});

function getDestinataire(conversationId) {
    return fetch('/api/messaging/conversation/' + conversationId + '/user/' + userId)
        .then(response => response.json())
        .then(conv => {
            if (userRole === 'CLIENT') {
                return conv.conseillerId;
            } else {
                return conv.clientId;
            }
        });
}

function escapeHtml(text) {
    const map = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#039;'
    };
    return text.replace(/[&<>"']/g, m => map[m]);
}
