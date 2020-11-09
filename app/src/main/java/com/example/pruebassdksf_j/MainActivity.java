package com.example.pruebassdksf_j;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.pruebassdksf_j.models.ChatMessageWrapper;
import com.example.pruebassdksf_j.models.MessageWrapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.salesforce.android.chat.core.AgentListener;
import com.salesforce.android.chat.core.ChatBotListener;
import com.salesforce.android.chat.core.ChatClient;
import com.salesforce.android.chat.core.ChatConfiguration;
import com.salesforce.android.chat.core.ChatCore;
import com.salesforce.android.chat.core.QueueListener;
import com.salesforce.android.chat.core.SessionStateListener;
import com.salesforce.android.chat.core.model.AgentInformation;
import com.salesforce.android.chat.core.model.ChatEndReason;
import com.salesforce.android.chat.core.model.ChatEntity;
import com.salesforce.android.chat.core.model.ChatEntityField;
import com.salesforce.android.chat.core.model.ChatFooterMenu;
import com.salesforce.android.chat.core.model.ChatMessage;
import com.salesforce.android.chat.core.model.ChatSessionState;
import com.salesforce.android.chat.core.model.ChatUserData;
import com.salesforce.android.chat.core.model.ChatWindowButtonMenu;
import com.salesforce.android.chat.core.model.ChatWindowMenu;
import com.salesforce.android.chat.ui.ChatUI;
import com.salesforce.android.chat.ui.ChatUIClient;
import com.salesforce.android.chat.ui.ChatUIConfiguration;
import com.salesforce.android.chat.ui.model.PreChatTextInputField;
import com.salesforce.android.service.common.utilities.control.Async;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.sql.Date;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String ORG_ID = "00D2g0000000O2w";//"00D2g0000008buj";//"00D4R0000007tWz";
    public static final String DEPLOYMENT_ID = "5722g000000CaZq";//"5722g00000000Wx";//"5724R000000c2nq";
    public static final String BUTTON_ID = "5732g000000CbV2";//"5732g00000000aS";//"5734R000000c31K";
    public static final String LIVE_AGENT_POD = "d.la3-c1cs-ph2.salesforceliveagent.com";//"c.la3-c1cs-ph2.salesforceliveagent.com"; //"d.la1-c2-ia4.salesforceliveagent.com";
    private @Nullable ChatClient mChatClient;


// CHAT CORE y LISTENERS
    ChatCore core = ChatCore.configure(new ChatConfiguration.Builder(ORG_ID, BUTTON_ID,
            DEPLOYMENT_ID, LIVE_AGENT_POD)
            .build());
    SessionStateListener myStateListener = new SessionStateListener() {
        @Override
        public void onSessionStateChange(ChatSessionState chatSessionState) {
        }

        @Override
        public void onSessionEnded(ChatEndReason chatEndReason) {
            endChater();
        }
    };
    AgentListener myAgentListener = new AgentListener() {
        @Override
        public void onAgentJoined(AgentInformation agentInformation) {
            startChater();
        }

        @Override
        public void onChatTransferred(AgentInformation agentInformation) {

        }

        @Override
        public void onChatMessageReceived(ChatMessage chatMessage) {
            adapter.addToStart(new MessageWrapper(
                    new ChatMessageWrapper(chatMessage.getAgentId(), chatMessage.getAgentName(), chatMessage.getText(), chatMessage.getTimestamp())
            ), true);
        }

        @Override
        public void onAgentIsTyping(boolean b) {
            TextView tx = findViewById(R.id.writtingTextbox);
            if(b) {
                tx.setText("El agente esta escribiendo...");
            } else {
                tx.setText("");
            }
        }

        @Override
        public void onTransferToButtonInitiated() {

        }

        @Override
        public void onAgentJoinedConference(String s) {

        }

        @Override
        public void onAgentLeftConference(String s) {

        }
    };
    QueueListener myQueueListener = new QueueListener() {
        @Override
        public void onQueuePositionUpdate(int i) {

        }

        @Override
        public void onQueueEstimatedWaitTimeUpdate(int i, int i1) {

        }
    };
    ChatBotListener myEinsteinBotListener = new ChatBotListener() {
        @Override
        public void onChatMenuReceived(ChatWindowMenu chatWindowMenu) {

        }

        @Override
        public void onChatFooterMenuReceived(ChatFooterMenu chatFooterMenu) {

        }

        @Override
        public void onChatButtonMenuReceived(ChatWindowButtonMenu chatWindowButtonMenu) {

        }
    };


    //DATOS DEL PRE-CHAT PARA UI ESTANDAR
    ChatUserData userData = new ChatUserData(
            "Usuario Ualá", "testuser10@uala.com.ar", true);
    ChatUserData lastName = new ChatUserData(
            "LastName", "test", false);
    ChatUserData country = new ChatUserData(
            "Country", "484", false);
    ChatUserData recordType = new ChatUserData(
            "RecordType", "0122g000000A293AAC", false);
    ChatUserData dType = new ChatUserData(
            "Tipo de documento", "CURP", false);
    ChatUserData gender = new ChatUserData(
            "Genero", "M", false);
    ChatUserData dId = new ChatUserData(
            "Numero de documento", "FMFORPLVTDICCQXVHU", false);
    ChatUserData firstName = new ChatUserData(
            "Nombre", "Santiago", false);
    ChatUserData phone = new ChatUserData(
            "Telefono", "1102255550", false);
    ChatUserData fNacimiento = new ChatUserData(
            "Fecha Nacimiento", "2020-10-08T00:00:00.000Z", false);
    // Map FirstName, LastName, and Email to fields in a Contact record
    ChatEntity contactEntity = new ChatEntity.Builder()
            .showOnCreate(true)
            .linkToTranscriptField("Account")
            .addChatEntityField(
                    new ChatEntityField.Builder()
                            .doFind(true)
                            .isExactMatch(true)
                            .doCreate(true)
                            .build("PersonEmail", userData))
            .addChatEntityField(
                    new ChatEntityField.Builder()
                            .doFind(false)
                            .doCreate(true)
                            .build("LastName", lastName))
            .addChatEntityField(
                    new ChatEntityField.Builder()
                            .doFind(true)
                            .isExactMatch(true)
                            .doCreate(true)
                            .build("RecordTypeId", recordType))
            .addChatEntityField(
                    new ChatEntityField.Builder()
                            .doFind(true)
                            .isExactMatch(true)
                            .doCreate(true)
                            .build("Country__c", country))
            .addChatEntityField(
                    new ChatEntityField.Builder()
                            .doFind(false)
                            .isExactMatch(false)
                            .doCreate(true)
                            .build("DocumentType__c", dType))
            .addChatEntityField(
                    new ChatEntityField.Builder()
                            .doFind(false)
                            .isExactMatch(false)
                            .doCreate(true)
                            .build("Gender__c", gender))
            .addChatEntityField(
                    new ChatEntityField.Builder()
                            .doFind(false)
                            .isExactMatch(false)
                            .doCreate(true)
                            .build("DocumentId__c", dId)
            )
            .addChatEntityField(
                    new ChatEntityField.Builder()
                            .doFind(false)
                            .isExactMatch(false)
                            .doCreate(true)
                            .build("FirstName", firstName)
            )
            .addChatEntityField(
                    new ChatEntityField.Builder()
                            .doFind(false)
                            .isExactMatch(false)
                            .doCreate(true)
                            .build("PersonMobilePhone", phone)
            )
            .addChatEntityField(
                    new ChatEntityField.Builder()
                            .doCreate(true)
                            .doFind(true)
                            .isExactMatch(true)
                            .build("AWSCreatedDate__c", fNacimiento)
            )
            .build("Account");

    // CONFIGURACION DE CHAT
    ChatConfiguration chatConfiguration =
            new ChatConfiguration.Builder(ORG_ID, BUTTON_ID,
                    DEPLOYMENT_ID, LIVE_AGENT_POD)
                    .chatUserData(
                            userData,
                            lastName,
                            country,
                            recordType,
                            dType,
                            dId,
                            gender,
                            firstName,
                            phone,
                            fNacimiento
                    )
                    .chatEntities(contactEntity)
                    .build();

    public MessagesListAdapter<MessageWrapper> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        /* ESTANDAR
        fab.setOnClickListener(click -> {
            chat();
            return;
        });
        */
        //CORE + UI CUSTOM
        fab.setOnClickListener(click -> {
                startChat();
                return;
            });
    }

    public void chat () {
        //UI ESTANDAR
        
        ChatUI.configure(ChatUIConfiguration.create(chatConfiguration))
                .createClient(getApplicationContext())
                .onResult(new Async.ResultHandler<ChatUIClient>() {
                    @Override public void handleResult (Async<?> operation,
                                                        ChatUIClient chatUIClient) {
                        chatUIClient.startChatSession(MainActivity.this);
                    }
                });
    }


    public void startChater() {
        // UI CUSTOM
        setContentView(R.layout.messager);

        MessageInput inputView = findViewById(R.id.input);
        MessagesList messagesList = findViewById(R.id.messagesList);
        String senderId = "xd";
        adapter = new MessagesListAdapter<>(senderId, null);
        messagesList.setAdapter(adapter);

        inputView.setTypingListener(new MessageInput.TypingListener() {
            @Override
            public void onStartTyping() {
                mChatClient.setIsUserTyping(true);
            }

            @Override
            public void onStopTyping() {
                mChatClient.setIsUserTyping(false);
            }
        });
        inputView.setInputListener( input -> {
            mChatClient.sendChatMessage(input.toString());
            adapter.addToStart(new MessageWrapper(new ChatMessageWrapper("xd", "Visitor", input.toString(), Calendar.getInstance().getTime())), true);
            return true;
        });

        Button btnClose = findViewById(R.id.closeChat);
        btnClose.setOnClickListener(click -> {
            mChatClient.endChatSession();
            return;
        });
    }

    public void endChater () {
        setContentView(R.layout.activity_main);
    }


    public void startChat() {
        // UI CUSTOM
        setContentView(R.layout.loading_screen);
        core.createClient(this)
                .onResult(new Async.ResultHandler<ChatClient>() {
                    @Override
                    public void handleResult(Async<?> operation,
                                             @NonNull ChatClient chatClient) {
                        mChatClient = chatClient
                                .addSessionStateListener(myStateListener)
                                .addAgentListener(myAgentListener)
                                .addQueueListener(myQueueListener)
                                .addChatBotListener(myEinsteinBotListener);
                    }
                });
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}