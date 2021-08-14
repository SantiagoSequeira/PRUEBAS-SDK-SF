package com.example.SalesforceChatTester;

import android.content.Intent;
import android.os.Bundle;

import com.example.SalesforceChatTester.DatabaseFolder.RoomDB;
import com.example.SalesforceChatTester.EntitiesFolder.EditEntity;
import com.example.SalesforceChatTester.OrganizationFolder.EditOrganization;
import com.example.SalesforceChatTester.OrganizationFolder.OrganizationItem;
import com.example.SalesforceChatTester.DatabaseFolder.Organization;
import com.example.SalesforceChatTester.OrganizationFolder.OrganizationsAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    RoomDB dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dataBase = RoomDB.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("R.string.app_name");
        ListView ll = findViewById(R.id.List1);
        OrganizationsAdapter organizationsAdapter = new OrganizationsAdapter(
                this,
                dataBase.mainDao().getAllOrganizations()
        );
        ll.setAdapter(organizationsAdapter);
    }

    public void openOrg(View view) {
        Intent intent = new Intent(getApplicationContext(), OrganizationItem.class);
        TextView tx = view.findViewById(R.id.orgItemId);
        Integer idOfItem = Integer.valueOf(tx.getText().toString());
        Organization organization = dataBase.mainDao().getOrganization(idOfItem);
        intent.putExtra("organization", organization);
        startActivity(intent);
    }

    public void newOrg(View view) {
        Intent intent = new Intent(getApplicationContext(), EditOrganization.class);
        startActivity(intent);
    }

    public void newEntity(View view) {
        Intent intent = new Intent(getApplicationContext(), EditEntity.class);
        startActivity(intent);
    }

}