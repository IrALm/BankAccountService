let stompClient = null;
let currentConversationId = conversationId; // From inline script

// Connexion au WebSocket
function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        // S'abonner aux messages de cette conversation
        if (currentConversationId) {
            stompClient.subscribe('/topic/conversation/' + currentConversationId, function (message) {
                const chatMessage = JSON.parse(message.body);
                displayMessage(chatMessage);

                // Marquer comme lu si c'est la conversation active
                if (chatMessage.senderId != currentUserId) {
                    markConversationAsRead(currentConversationId);
                }
            });
        }

        // S'abonner aux notifications globales pour cet utilisateur
        stompClient.subscribe('/user/' + currentUserId + '/queue/notifications', function (notification) {
            const notif = JSON.parse(notification.body);
            updateNotificationBadge(notif);
        });

    }, function (error) {
        console.error('WebSocket error: ' + error);
        setTimeout(connect, 5000);
    });
}

// Afficher un nouveau message
function displayMessage(chatMessage) {
    const messagesContainer = document.getElementById('messagesContainer');
    if (!messagesContainer) return;

    const messageDiv = document.createElement('div');

    // Supprimer le message "Aucun message" si présent
    const emptyMessages = messagesContainer.querySelector('.empty-messages');
    if (emptyMessages) {
        emptyMessages.remove();
    }

    const isSentByMe = chatMessage.senderId == currentUserId;
    messageDiv.className = isSentByMe ? 'message message-sent' : 'message message-received';

    const time = chatMessage.timestamp || new Date().toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' });

    messageDiv.innerHTML = `
        <div class="message-content">
            <p>${escapeHtml(chatMessage.content)}</p>
            <span class="message-time">${time}</span>
        </div>
    `;

    messagesContainer.appendChild(messageDiv);
    messagesContainer.scrollTop = messagesContainer.scrollHeight;
}

// Envoyer un message
function sendMessage(event) {
    event.preventDefault();

    const messageInput = document.getElementById('messageInput');
    const content = messageInput.value.trim();

    if (content && stompClient && currentConversationId) {
        const chatMessage = {
            conversationId: currentConversationId,
            senderId: currentUserId,
            senderName: currentUserName,
            content: content
        };

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
}

// Marquer une conversation comme lue
function markConversationAsRead(convId) {
    fetch(`/api/messaging/conversation/${convId}/mark-all-read/${currentUserId}`, {
        method: 'POST'
    }).then(() => {
        // Mettre à jour le badge de la conversation dans la sidebar
        updateConversationBadge(convId, 0);
        // Rafraîchir le badge global
        updateGlobalBadge();
    }).catch(err => console.error('Error marking as read:', err));
}

// Mettre à jour le badge d'une conversation spécifique
function updateConversationBadge(convId, count) {
    const convItems = document.querySelectorAll('.conversation-item');
    convItems.forEach(item => {
        const onclick = item.getAttribute('onclick');
        if (onclick && onclick.includes(`conversationId=${convId}`)) {
            let badge = item.querySelector('.unread-badge');
            if (count > 0) {
                if (!badge) {
                    badge = document.createElement('span');
                    badge.className = 'unread-badge';
                    const preview = item.querySelector('.conversation-preview');
                    if (preview) preview.appendChild(badge);
                }
                badge.textContent = count;
            } else {
                if (badge) badge.remove();
            }
        }
    });
}

// Mettre à jour le badge global (navbar)
function updateGlobalBadge() {
    fetch(`/api/messaging/unread-count/${currentUserId}`)
        .then(response => response.json())
        .then(count => {
            const badge = document.getElementById('notification-badge');
            if (badge) {
                if (count > 0) {
                    badge.textContent = count;
                    badge.style.display = 'inline-block';
                } else {
                    badge.style.display = 'none';
                }
            }
        })
        .catch(err => console.error('Error updating global badge:', err));
}

// Mettre à jour les notifications en temps réel
function updateNotificationBadge(notification) {
    // notification: { conversationId, unreadCount, totalUnread }
    if (notification.conversationId != currentConversationId) {
        // Mettre à jour le badge de la conversation
        updateConversationBadge(notification.conversationId, notification.unreadCount);
    }

    // Mettre à jour le badge global
    const badge = document.getElementById('notification-badge');
    if (badge) {
        if (notification.totalUnread > 0) {
            badge.textContent = notification.totalUnread;
            badge.style.display = 'inline-block';
        } else {
            badge.style.display = 'none';
        }
    }
}

// Échapper le HTML pour la sécurité
function escapeHtml(text) {
    if (!text) return '';
    const map = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#039;'
    };
    return text.replace(/[&<>"']/g, function (m) { return map[m]; });
}

// Initialisation
document.addEventListener('DOMContentLoaded', function () {
    connect();

    const messageForm = document.getElementById('messageForm');
    if (messageForm) {
        messageForm.addEventListener('submit', sendMessage);
    }

    // Auto-scroll vers le bas
    const messagesContainer = document.getElementById('messagesContainer');
    if (messagesContainer) {
        messagesContainer.scrollTop = messagesContainer.scrollHeight;
    }

    // Marquer la conversation actuelle comme lue au chargement
    if (currentConversationId) {
        markConversationAsRead(currentConversationId);
    }

    // Mettre à jour le badge global au chargement
    updateGlobalBadge();
});
