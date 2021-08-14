package com.example.SalesforceChatTester.OrganizationFolder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.SalesforceChatTester.DatabaseFolder.OrgEntity;
import com.example.SalesforceChatTester.DatabaseFolder.Organization;
import com.example.SalesforceChatTester.R;
import com.example.SalesforceChatTester.DatabaseFolder.RoomDB;
import com.example.SalesforceChatTester.DatabaseFolder.UserItemData;
import com.salesforce.android.chat.core.ChatConfiguration;
import com.salesforce.android.chat.core.model.ChatEntity;
import com.salesforce.android.chat.core.model.ChatEntityField;
import com.salesforce.android.chat.core.model.ChatUserData;
import com.salesforce.android.chat.ui.ChatUI;
import com.salesforce.android.chat.ui.ChatUIClient;
import com.salesforce.android.chat.ui.ChatUIConfiguration;
import com.salesforce.android.service.common.utilities.control.Async;

import java.util.ArrayList;
import java.util.List;

public class OrganizationItem  extends AppCompatActivity {
    Organization organization;
    RoomDB dataBase;
    ArrayList<ChatEntity> chatEntities;
    ArrayList<ChatUserData> chatUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        dataBase = RoomDB.getInstance(this);
        if (extras != null) {
            organization = (Organization) extras.getSerializable("organization");
            //The key argument here must match that used in the other activity
        }
        setTitle("Organizacion: " + organization.getTitle());
        setContentView(R.layout.organization_details);

        TextView orgId = findViewById(R.id.orgDetailsId);
        orgId.setText(organization.getORG_ID());
        TextView agentPOD = findViewById(R.id.orgAgentPod);

        agentPOD.setText(organization.getLIVE_AGENT_POD());
        TextView orgDeployId = findViewById(R.id.orgDeployId);
        orgDeployId.setText(organization.getDEPLOYMENT_ID());
        TextView orgButtonId = findViewById(R.id.orgButtonId);
        orgButtonId.setText(organization.getBUTTON_ID());

    }

    public void generateChatEntity() {
        chatEntities =new ArrayList<ChatEntity>();
        chatUserData =new ArrayList<ChatUserData>();
        List<OrgEntity> entities = dataBase.mainDao().getAllEntitiesByOrgId(organization.getId());
        for(Integer i=0; i<entities.size(); i++) {
            OrgEntity entity = entities.get(i);
            ChatEntity.Builder objectEntity = new ChatEntity.Builder()
                    .showOnCreate(true);
            if(entity.getLinkToTranscriptField()) {
                objectEntity.linkToTranscriptField(entity.getObjectName());
            }
            List<UserItemData> userItemData = dataBase.mainDao().getAllUserItemDataByEntityId(entity.getId());
            for(Integer u =0; u<userItemData.size(); u++) {
                UserItemData item = userItemData.get(u);
                ChatUserData cUD = new ChatUserData(
                        item.getAgentLabel(),
                        item.getValue(),
                        item.getShowToAgent(),
                        item.getObjectFieldName()
                );
                objectEntity.addChatEntityField(
                        new ChatEntityField.Builder()
                                .doFind(
                                        item.getDoFind()
                                )
                                .isExactMatch(
                                        item.getIsExactMatch()
                                )
                                .doCreate(
                                        item.getDoCreate()
                                )
                                .build(
                                        item.getObjectFieldName(),
                                        cUD
                                )
                );
                chatUserData.add(cUD);
            }
            chatEntities.add(objectEntity.build(entity.getObjectName()));
        }

    }

    public void startChat(View view) {
        generateChatEntity();
        ChatConfiguration chatConfiguration =
                new ChatConfiguration.Builder(organization.getORG_ID(), organization.getBUTTON_ID(),
                        organization.getDEPLOYMENT_ID(), organization.getLIVE_AGENT_POD())
                        .chatUserData(chatUserData)
                        .chatEntities(chatEntities)
                        .build();
        ChatUIConfiguration uiConfig = new ChatUIConfiguration.Builder()
                .chatConfiguration(chatConfiguration)// Use estimated wait time
                .defaultToMinimized(false)
                .disablePreChatView(true)
                .build();
        ChatUI.configure(uiConfig)
                .createClient(getApplicationContext())
                .onResult(new Async.ResultHandler<ChatUIClient>() {
                    @Override public void handleResult (Async<?> operation,
                                                        ChatUIClient chatUIClient) {

                        chatUIClient.startChatSession(OrganizationItem.this);
                    }
                });
    }

    public void editOrg(View view) {
        Intent intent = new Intent(getApplicationContext(), EditOrganization.class);
        intent.putExtra("organization", organization);
        startActivity(intent);
    }

}
