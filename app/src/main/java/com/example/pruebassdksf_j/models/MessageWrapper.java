package com.example.pruebassdksf_j.models;

import android.media.Image;

import androidx.annotation.Nullable;

import com.salesforce.android.chat.core.model.ChatMessage;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.util.Date;

public class MessageWrapper implements IMessage,  MessageContentType.Image {
        private ChatMessageWrapper message;
        private UserWrapper user;
        private Image image;

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

        @Override
        public String getImageUrl() {
            return image == null ? null : image.url;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public static class Image {

            private String url;

            public Image(String url) {
                this.url = url;
            }
        }

}
