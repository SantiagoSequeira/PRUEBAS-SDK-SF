package com.example.pruebassdksf_j.models;

import com.salesforce.android.chat.core.model.ChatMessage;

import java.util.Date;

public class ChatMessageWrapper implements ChatMessage {
    private String agentId;
    private String agentName;
    private String text;
    private Date timeStamp;

    public ChatMessageWrapper (String agentId, String agentName, String text, Date time ) {
        this.agentId = agentId;
        this.agentName = agentName;
        this.text = text;
        this.timeStamp = time;
    }


    @Override
    public String getAgentId() {
        return agentId;
    }

    @Override
    public String getAgentName() {
        return agentName;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Date getTimestamp() {
        return timeStamp;
    }
}
