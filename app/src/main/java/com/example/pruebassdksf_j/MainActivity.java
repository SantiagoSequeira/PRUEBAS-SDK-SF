package com.example.pruebassdksf_j;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.pruebassdksf_j.models.ChatMessageWrapper;
import com.example.pruebassdksf_j.models.MainStatusSaver;
import com.example.pruebassdksf_j.models.MessageWrapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {


    public MainStatusSaver instance = MainStatusSaver.getInstance();

    // CHAT CORE y LISTENERS
    public String sOrg = "Santy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RadioGroup rG = findViewById(R.id.selectedEnviroment);
        rG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rItem = findViewById(checkedId);
                sOrg = rItem.getText().toString();
                instance.setSELECTEDORG(String.valueOf(rItem.getText()));
                instance.recompileCore();
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);


        // ESTANDAR
        fab.setOnClickListener(click -> {
            chat(sOrg);
            return;
        });

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        //CORE + UI CUSTOM
        fab2.setOnClickListener(click -> {
            Intent intent = new Intent(this, Chatting.class);
            startActivity(intent);
            return;
        });

        // */
    }





    //DATOS DEL PRE-CHAT PARA UI ESTANDAR
    ChatUserData email = new ChatUserData("Email", "testuser11@uala.com.ar", true);
    ChatUserData username = new ChatUserData("UalaUsername", "virtual802_1600178162", true);
    ChatUserData country = new ChatUserData("Country", "032", false);
    ChatUserData appVer = new ChatUserData("APPVersion", "22.012", false);
    ChatUserData lastName = new ChatUserData("LastName", "test MX", false);
    ChatUserData recordType = new ChatUserData("RecordType", "0122g000000A293AAC", false);
    ChatUserData dType = new ChatUserData("Tipo de documento", "CURP", false);
    ChatUserData gender = new ChatUserData("Genero", "M", false);
    ChatUserData dId = new ChatUserData("Numero de documento", "AMFORPLVTDICCQXVHU", false);
    ChatUserData firstName = new ChatUserData("Nombre", "Santiago", false);
    ChatUserData phone = new ChatUserData("Telefono", "1102255550", false);
    ChatUserData fNacimiento = new ChatUserData("Fecha Nacimiento", "2020-10-08T00:00:00.000Z", false);

    //PRECHAT
    //  Map FirstName, LastName, and Email to fields in a Contact record
    ChatEntity accountEntity = new ChatEntity.Builder()
            .showOnCreate(true)
            .linkToTranscriptField("Account")
            .addChatEntityField(new ChatEntityField.Builder()
                    .doFind(true)
                    .isExactMatch(true)
                    .doCreate(true)
                    .build("UalaUsername__c", username)
            )
            .addChatEntityField(
                    new ChatEntityField.Builder()
                            .doFind(false)
                            .isExactMatch(false)
                            .doCreate(true)
                            .build("Country__c", country)
            )
            .build("Account");

//
    public void chat (String sOrg) {
        //UI ESTANDAR
        Log.e("UALADEV", String.valueOf(sOrg == "UALADEV"));
        String DEPLOYMENT_ID = "";
        String ORG_ID = "";
        String BUTTON_ID = "";
        String LIVE_AGENT_POD = "";

        switch (sOrg) {
            case "Santy":
                ORG_ID = "00D4R0000007tWz";
                DEPLOYMENT_ID = "5724R000000c2nq";
                BUTTON_ID = "5734R000000c3JX";
                LIVE_AGENT_POD = "d.la1-c2-ia4.salesforceliveagent.com";
                break;
            case "UALAUAT":
                ORG_ID = "00D2g0000008buj";
                DEPLOYMENT_ID = "5722g00000000Ws";
                BUTTON_ID = "5732g00000000ZU";
                LIVE_AGENT_POD = "d.la3-c1cs-ph2.salesforceliveagent.com";
                break;
            case "UALADEV":
                ORG_ID = "00D2g0000000O2w";
                DEPLOYMENT_ID = "5722g000000CaZq";
                BUTTON_ID = "5732g000000CbRK";
                LIVE_AGENT_POD = "d.la3-c1cs-ph2.salesforceliveagent.com";
                break;
        }
        Log.e("Santy", String.valueOf(sOrg == "Santy"));
        Log.e("SelectedOrg2", sOrg );
        Log.e("ORG_ID", ORG_ID);
        Log.e("DEPLOYMENT_ID", DEPLOYMENT_ID);
        Log.e("BUTTON_ID", BUTTON_ID);
        Log.e("LIVE_AGENT_POD", LIVE_AGENT_POD);

        //CONFIGURACION DE CHAT
        ChatConfiguration chatConfiguration =
                new ChatConfiguration.Builder(ORG_ID, BUTTON_ID,
                        DEPLOYMENT_ID, LIVE_AGENT_POD)
                        .chatUserData(country, username)
                        .chatEntities(accountEntity)
                        .build();
        ChatUIConfiguration uiConfig = new ChatUIConfiguration.Builder()
                .chatConfiguration(chatConfiguration)// Use estimated wait time
                .defaultToMinimized(false)                // Start in full-screen mode
                .build();
        ChatUI.configure(uiConfig)

            .createClient(getApplicationContext())

            .onResult(new Async.ResultHandler<ChatUIClient>() {
                @Override public void handleResult (Async<?> operation,
                                                    ChatUIClient chatUIClient) {
                    chatUIClient.startChatSession(MainActivity.this);
                }
        });
    }
}