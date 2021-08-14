package com.example.SalesforceChatTester.UserItemDataFolder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import com.example.SalesforceChatTester.DatabaseFolder.OrgEntity;
import com.example.SalesforceChatTester.DatabaseFolder.RoomDB;
import com.example.SalesforceChatTester.R;
import com.example.SalesforceChatTester.DatabaseFolder.UserItemData;
import com.google.android.material.textfield.TextInputEditText;

public class UserItemDataEdit extends AppCompatActivity {
    OrgEntity orgEntity;
    UserItemData userItemData;
    RoomDB dataBase;
    TextInputEditText objectFieldNameTIET;
    TextInputEditText userItemDataValueTIET;
    TextInputEditText transcriptFieldNameTIET;
    TextInputEditText agentLabelTIET;
    Switch doFindSw;
    Switch showToAgentSw;
    Switch isExactMatchSw;
    Switch doCreateSw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_item_data_edit);
        Bundle extras = getIntent().getExtras();
        dataBase = RoomDB.getInstance(this);
        orgEntity = (OrgEntity) extras.getSerializable("orgEntity");

        objectFieldNameTIET = findViewById(R.id.objectFieldNameTIET);
        userItemDataValueTIET = findViewById(R.id.userItemDataValueTIET);
        transcriptFieldNameTIET = findViewById(R.id.transcriptFieldNameTIET);
        agentLabelTIET = findViewById(R.id.agentLabelTIET);
        doFindSw = findViewById(R.id.doFindSw);
        showToAgentSw = findViewById(R.id.showToAgentSw);
        isExactMatchSw = findViewById(R.id.isExactMatchSw);
        doCreateSw = findViewById(R.id.doCreateSw);

        if (extras.containsKey("userItemData")) {
            userItemData = (UserItemData) extras.getSerializable("userItemData");
        } else {
            userItemData = new UserItemData(
                    "",
                    "",
                    "",
                    false,
                    orgEntity.getId(),
                    "",
                    false,
                    false,
                    false
            );
        }

        objectFieldNameTIET.setText(userItemData.getObjectFieldName());
        userItemDataValueTIET.setText(userItemData.getValue());
        transcriptFieldNameTIET.setText(userItemData.getTranscriptFieldName());
        agentLabelTIET.setText(userItemData.getAgentLabel());
        doFindSw.setChecked(userItemData.getDoFind());
        showToAgentSw.setChecked(userItemData.getShowToAgent());
        isExactMatchSw.setChecked(userItemData.getIsExactMatch());
        doCreateSw.setChecked(userItemData.getDoCreate());

        Button saveUserItemDataButton = findViewById(R.id.saveUserItemDataButton);
        saveUserItemDataButton.setOnClickListener(v -> {
            userItemData.setObjectFieldName(objectFieldNameTIET.getText().toString());
            userItemData.setValue(userItemDataValueTIET.getText().toString());
            userItemData.setTranscriptFieldName(transcriptFieldNameTIET.getText().toString());
            userItemData.setAgentLabel(agentLabelTIET.getText().toString());
            userItemData.setDoFind(doFindSw.isChecked());
            userItemData.setShowToAgent(showToAgentSw.isChecked());
            userItemData.setIsExactMatch(isExactMatchSw.isChecked());
            userItemData.setDoCreate(doCreateSw.isChecked());

            if(userItemData.getId() != 0) {
                dataBase.mainDao().updateUserItemData(
                        userItemData
                );
            } else {
                dataBase.mainDao().insertUserItemData(
                        userItemData
                );
            }
            finish();
        });
    }

}