package com.example.SalesforceChatTester.EntitiesFolder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.SalesforceChatTester.DatabaseFolder.OrgEntity;
import com.example.SalesforceChatTester.DatabaseFolder.Organization;
import com.example.SalesforceChatTester.DatabaseFolder.RoomDB;
import com.example.SalesforceChatTester.R;
import com.example.SalesforceChatTester.DatabaseFolder.UserItemData;
import com.example.SalesforceChatTester.UserItemDataFolder.UserItemDataAdapter;
import com.example.SalesforceChatTester.UserItemDataFolder.UserItemDataEdit;
import com.google.android.material.textfield.TextInputEditText;

public class EditEntity extends AppCompatActivity {
    OrgEntity orgEntity;
    Organization organization;
    RoomDB dataBase;

    Switch linkToChatTranscriptSw;
    TextInputEditText sObjectName;
    Button saveEntityButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entities);
        dataBase = RoomDB.getInstance(this);
        Bundle extras = getIntent().getExtras();
        organization = (Organization) extras.getSerializable("organization");
        if (extras.containsKey("orgEntity")) {
            orgEntity = (OrgEntity) extras.getSerializable("orgEntity");
        } else {
            orgEntity = new OrgEntity("", organization.getId(), false);
        }
        setTitle("Entity: " + orgEntity.getObjectName());
        linkToChatTranscriptSw = findViewById(R.id.linkToChatTranscriptSw);
        sObjectName = findViewById(R.id.sObjectName);
        saveEntityButton = findViewById(R.id.saveEntityButton);
        linkToChatTranscriptSw.setChecked(orgEntity.getLinkToTranscriptField());
        sObjectName.setText(orgEntity.getObjectName());
        saveEntityButton.setOnClickListener(v -> {
            orgEntity.setLinkToTranscriptField(linkToChatTranscriptSw.isChecked());
            orgEntity.setObjectName(sObjectName.getText().toString());
                if(orgEntity.getId() != 0) {
                    dataBase.mainDao().updateEntity(
                            orgEntity
                    );
                } else {
                    dataBase.mainDao().insertEntity(
                            orgEntity
                    );
                }
                finish();
        });
        if(orgEntity.getId() != 0) {
            UserItemDataAdapter userItemDataAdapter = new UserItemDataAdapter(
                    this,
                    dataBase.mainDao().getAllUserItemDataByEntityId(orgEntity.getId())
            );
            ListView ll = findViewById(R.id.userItemDataList);
            ll.setAdapter(userItemDataAdapter);
        } else {
            Button newUserItemDataButton = findViewById(R.id.newUserItemDataButton);
            newUserItemDataButton.setEnabled(false);
        }
    }

    public void reloadData() {

    }

    public void newUserData(View view) {
        Intent intent = new Intent(getApplicationContext(), UserItemDataEdit.class);
        intent.putExtra("orgEntity", orgEntity);
        startActivity(intent);
    }

    public void updateUserData(View view) {
        Intent intent = new Intent(getApplicationContext(), UserItemDataEdit.class);

        TextView tx = view.findViewById(R.id.userDataItemId);
        Integer idOfItem = Integer.valueOf(tx.getText().toString());
        UserItemData userItemData = dataBase.mainDao().getUserItemDataById(idOfItem);
        intent.putExtra("userItemData", userItemData);
        intent.putExtra("orgEntity", orgEntity);
        startActivity(intent);
    }

}