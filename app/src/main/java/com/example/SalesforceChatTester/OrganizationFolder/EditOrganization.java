package com.example.SalesforceChatTester.OrganizationFolder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.SalesforceChatTester.DatabaseFolder.Organization;
import com.example.SalesforceChatTester.EntitiesFolder.EntitiesList;
import com.example.SalesforceChatTester.R;
import com.example.SalesforceChatTester.DatabaseFolder.RoomDB;
import com.google.android.material.textfield.TextInputEditText;

public class EditOrganization extends AppCompatActivity {
    Organization organization;
    RoomDB dataBase;
    Button orgButton;
    TextInputEditText orgId;
    TextInputEditText agentPOD;
    TextInputEditText orgDeployId;
    TextInputEditText orgButtonId;
    TextInputEditText orgTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_organization);
        Bundle extras = getIntent().getExtras();
        dataBase = RoomDB.getInstance(this);
        if (extras != null) {
            organization = (Organization) extras.getSerializable("organization");
            //The key argument here must match that used in the other activity
        } else {
            organization = new Organization("","","","","");
        }
        orgButton = findViewById(R.id.orgButton);
        setTitle("Organizacion: " + organization.getTitle());
        Button entitiesButton = findViewById(R.id.entitiesEditButton);
        if(organization.getId() == 0) {
            entitiesButton.setEnabled(false);
        } else {
            entitiesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), EntitiesList.class);
                    intent.putExtra("organization", organization);
                    startActivity(intent);
                }
            });
        }


        orgTitle = findViewById(R.id.orgTitle);
        orgTitle.setText(organization.getTitle());

        orgId = findViewById(R.id.orgId);
        orgId.setText(organization.getORG_ID());

        agentPOD = findViewById(R.id.orgAgentPOD);
        agentPOD.setText(organization.getLIVE_AGENT_POD());

        orgDeployId = findViewById(R.id.orgDeploymentId);
        orgDeployId.setText(organization.getDEPLOYMENT_ID());

        orgButtonId = findViewById(R.id.orgEditButtonId);
        orgButtonId.setText(organization.getBUTTON_ID());

        orgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Organization org = new Organization(
                        orgTitle.getText().toString(),
                        orgDeployId.getText().toString(),
                        orgId.getText().toString(),
                        agentPOD.getText().toString(),
                        orgButtonId.getText().toString()
                );
                if(organization.getId()!= 0) {
                    org.setId(organization.getId());
                    dataBase.mainDao().updateOrganization(org);
                } else {
                    dataBase.mainDao().insertOrganization(org);
                }
                finish();
            }
        });
    }


}