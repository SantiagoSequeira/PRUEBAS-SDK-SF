package com.example.pruebassdksf_j.models;

import com.salesforce.android.chat.core.model.ChatMessage;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

public class MessageWrapper implements IMessage {
        private ChatMessageWrapper message;
        private UserWrapper user;

        public MessageWrapper (ChatMessageWrapper message) {
            this.message = message;
            this.user = new UserWrapper(message.getAgentId(), message.getAgentName(), null);
        }

        @Override
        public String getId() {
            return message.getAgentId();
        }

        @Override
        public String getText() {
            return message.getText();
        }

        @Override
        public IUser getUser() {
            return user;
        }

        @Override
        public Date getCreatedAt() {
            return message.getTimestamp();
        }
}
