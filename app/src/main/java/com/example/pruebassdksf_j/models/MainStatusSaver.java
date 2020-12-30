package com.example.pruebassdksf_j.models;

import android.util.Log;

import androidx.annotation.Nullable;

import com.salesforce.android.chat.core.ChatClient;
import com.salesforce.android.chat.core.ChatConfiguration;
import com.salesforce.android.chat.core.ChatCore;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

public class MainStatusSaver {
    private static MainStatusSaver instance = null;


    public ChatCore chatCore;
    public ChatClient chatClient;
    public MessagesListAdapter<MessageWrapper> adapter;
    public String SELECTEDORG;

    protected MainStatusSaver() {
        // Exists only to defeat instantiation.
        recompileCore();
    }
    public static MainStatusSaver getInstance() {
        if(instance == null) {
            instance = new MainStatusSaver();
        }
        return instance;
    }

    public void recompileCore() {
        String ORG_ID = (SELECTEDORG == "Santy" ? "00D4R0000007tWz" :
                (SELECTEDORG == "UALAUAT" ? "00D2g0000008buj" : "00D2g0000000O2w" ) );
        String DEPLOYMENT_ID = (SELECTEDORG == "Santy" ?  "5724R000000c2nq" :
                (SELECTEDORG == "UALAUAT" ? "5722g00000000Wx" : "5722g000000CaZq" ) );
        String BUTTON_ID = (SELECTEDORG == "Santy" ?  "5734R000000c3JX" :
                (SELECTEDORG == "UALAUAT" ? "5732g00000000aS" : "5732g000000CbV2" ) );
        String LIVE_AGENT_POD = (SELECTEDORG == "Santy" ? "d.la1-c2-ia4.salesforceliveagent.com" :
                (SELECTEDORG == "UALAUAT" ? "d.la3-c1cs-ph2.salesforceliveagent.com" :
                        "d.la3-c1cs-ph2.salesforceliveagent.com" ) );
        chatCore = ChatCore.configure(new ChatConfiguration.Builder(ORG_ID, BUTTON_ID, DEPLOYMENT_ID, LIVE_AGENT_POD).build());
    }

    public void setChatCore(ChatCore chatCore) {
        this.chatCore = chatCore;
    }

    public void setChatClient(ChatClient mChatClient) {
        this.chatClient = mChatClient;
    }

    public void setAdapter(MessagesListAdapter<MessageWrapper> adapter) {
        this.adapter = adapter;
    }

    public void setSELECTEDORG(String SELECTEDORG) {
        this.SELECTEDORG = SELECTEDORG;
    }
}