package com.example.SalesforceChatTester.DatabaseFolder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "OrgEntity")
public class OrgEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    @ColumnInfo(name = "objectName")
    private String objectName;

    @ColumnInfo(name = "OrgId")
    private int orgId;

    @ColumnInfo(name = "LinkToTranscriptField")
    private Boolean linkToTranscriptField;

    public int getId() {
        return Id;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public Boolean getLinkToTranscriptField() {
        return linkToTranscriptField;
    }

    public void setLinkToTranscriptField(Boolean linkToTranscriptField) {
        this.linkToTranscriptField = linkToTranscriptField;
    }

    public OrgEntity(String objectName, int orgId, Boolean linkToTranscriptField) {
        this.objectName = objectName;
        this.orgId = orgId;
        this.linkToTranscriptField = linkToTranscriptField;
    }
}