package com.example.SalesforceChatTester.EntitiesFolder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.SalesforceChatTester.DatabaseFolder.OrgEntity;
import com.example.SalesforceChatTester.DatabaseFolder.Organization;
import com.example.SalesforceChatTester.DatabaseFolder.RoomDB;
import com.example.SalesforceChatTester.R;

public class EntitiesList extends AppCompatActivity {
    RoomDB dataBase;
    Organization organization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entities);
        dataBase = RoomDB.getInstance(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            organization = (Organization) extras.getSerializable("organization");
            //The key argument here must match that used in the other activity
        }
        setTitle("Organizacion: " + organization.getTitle());
        EntitiesAdapter organizationsAdapter = new EntitiesAdapter(
                this,
                dataBase.mainDao().getAllEntitiesByOrgId(organization.getId())
        );
        ListView ll = findViewById(R.id.EntitiesList);
        ll.setAdapter(organizationsAdapter);
    }

    public void openEntity(View view) {
        Intent intent = new Intent(getApplicationContext(), EditEntity.class);
        intent.putExtra("organization", organization);

        TextView tx = view.findViewById(R.id.entityItemId);
        Integer idOfItem = Integer.valueOf(tx.getText().toString());
        OrgEntity orgEntity = dataBase.mainDao().getEntityById(idOfItem);
        intent.putExtra("orgEntity", orgEntity);
        startActivity(intent);
    }

    public void newEntity(View view) {
        Intent intent = new Intent(getApplicationContext(), EditEntity.class);
        intent.putExtra("organization", organization);
        startActivity(intent);
    }
}