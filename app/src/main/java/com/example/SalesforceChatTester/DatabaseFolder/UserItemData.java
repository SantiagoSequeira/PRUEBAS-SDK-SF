package com.example.SalesforceChatTester.DatabaseFolder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "UserItemData")
public class UserItemData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    @ColumnInfo(name = "DoFind")
    private Boolean doFind;

    @ColumnInfo(name = "IsExactMatch")
    private Boolean isExactMatch;

    @ColumnInfo(name = "DoCreate")
    private Boolean doCreate;

    @ColumnInfo(name = "EntityId")
    private int entityId;

    @ColumnInfo(name = "ShowToAgent")
    private Boolean showToAgent;

    @ColumnInfo(name = "ObjectFieldName")
    private String objectFieldName;

    @ColumnInfo(name = "AgentLabel")
    private String agentLabel;

    @ColumnInfo(name = "TranscriptFieldName")
    private String transcriptFieldName;

    @ColumnInfo(name = "Value")
    private String value;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int id) {
        this.entityId = id;
    }

    public Boolean getDoFind() {
        return doFind;
    }

    public void setDoFind(Boolean doFind) {
        this.doFind = doFind;
    }

    public Boolean getShowToAgent() {
        return showToAgent;
    }

    public void setShowToAgent(Boolean showToAgent) {
        this.showToAgent = showToAgent;
    }

    public String getObjectFieldName() {
        return objectFieldName;
    }

    public void setObjectFieldName(String objectFieldName) {
        this.objectFieldName = objectFieldName;
    }

    public String getAgentLabel() {
        return agentLabel;
    }

    public void setAgentLabel(String agentLabel) {
        this.agentLabel = agentLabel;
    }

    public String getTranscriptFieldName() {
        return transcriptFieldName;
    }

    public void setTranscriptFieldName(String transcriptFieldName) {
        this.transcriptFieldName = transcriptFieldName;
    }

    public String getValue() {
        return value;
    }

    public Boolean getIsExactMatch() {
        return isExactMatch;
    }

    public void setIsExactMatch(Boolean exactMatch) {
        isExactMatch = exactMatch;
    }

    public Boolean getDoCreate() {
        return doCreate;
    }

    public void setDoCreate(Boolean doCreate) {
        this.doCreate = doCreate;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public UserItemData (
            String objectFieldName,
            String value,
            String agentLabel,
            Boolean doFind,
            int entityId,
            String transcriptFieldName,
            Boolean showToAgent,
            Boolean doCreate,
            Boolean isExactMatch) {
        this.objectFieldName = objectFieldName;
        this.value = value;
        this.entityId = entityId;
        this.agentLabel = agentLabel;
        this.doFind = doFind;
        this.transcriptFieldName = transcriptFieldName;
        this.showToAgent = showToAgent;
        this.doCreate = doCreate;
        this.isExactMatch = isExactMatch;
    }
}
