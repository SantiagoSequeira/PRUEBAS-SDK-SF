package com.example.SalesforceChatTester.DatabaseFolder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Organization")
public class Organization implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    @ColumnInfo(name = "Title")
    private String title;

    @ColumnInfo(name = "DEPLOYMENT_ID")
    private String DEPLOYMENT_ID;

    @ColumnInfo(name = "ORG_ID")
    private String ORG_ID;

    @ColumnInfo(name = "LIVE_AGENT_POD")
    private String LIVE_AGENT_POD;

    @ColumnInfo(name = "BUTTON_ID")
    private String BUTTON_ID;

    public int getId() {
        return Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDEPLOYMENT_ID() {
        return DEPLOYMENT_ID;
    }

    public void setDEPLOYMENT_ID(String DEPLOYMENT_ID) {
        this.DEPLOYMENT_ID = DEPLOYMENT_ID;
    }

    public String getORG_ID() {
        return ORG_ID;
    }

    public void setORG_ID(String ORG_ID) {
        this.ORG_ID = ORG_ID;
    }

    public String getLIVE_AGENT_POD() {
        return LIVE_AGENT_POD;
    }

    public void setLIVE_AGENT_POD(String LIVE_AGENT_POD) {
        this.LIVE_AGENT_POD = LIVE_AGENT_POD;
    }

    public String getBUTTON_ID() {
        return BUTTON_ID;
    }

    public void setBUTTON_ID(String BUTTON_ID) {
        this.BUTTON_ID = BUTTON_ID;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public Organization(String title, String DEPLOYMENT_ID, String ORG_ID, String LIVE_AGENT_POD, String BUTTON_ID) {
        this.title = title;
        this.DEPLOYMENT_ID = DEPLOYMENT_ID;
        this.LIVE_AGENT_POD = LIVE_AGENT_POD;
        this.BUTTON_ID = BUTTON_ID;
        this.ORG_ID = ORG_ID;
    }
}
