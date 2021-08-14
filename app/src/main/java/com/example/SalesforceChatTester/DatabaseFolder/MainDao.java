package com.example.SalesforceChatTester.DatabaseFolder;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {
    //ORGANIZATIONS
    @Insert(onConflict = REPLACE)
    void insertOrganization(Organization organization);

    @Query("SELECT * FROM Organization")
    List<Organization> getAllOrganizations();

    @Query("SELECT * FROM Organization WHERE Id = :id LIMIT 1")
    Organization getOrganization(Integer id);

    @Update
    void updateOrganization(Organization organization);

    //USER ITEM DATA
    @Insert(onConflict = REPLACE)
    void insertUserItemData(UserItemData userItemData);

    @Query("SELECT * FROM UserItemData WHERE entityId = :orgId")
    List<UserItemData> getAllUserItemDataByEntityId(Integer orgId);

    @Query("SELECT * FROM UserItemData WHERE id = :userItemDataId")
    UserItemData getUserItemDataById(Integer userItemDataId);

    @Update
    void updateUserItemData(UserItemData userItemData);

    //ENTITIES
    @Insert(onConflict = REPLACE)
    void insertEntity(OrgEntity orgEntity);

    @Query("SELECT * FROM OrgEntity WHERE orgId = :orgId")
    List<OrgEntity> getAllEntitiesByOrgId(Integer orgId);

    @Query("SELECT * FROM OrgEntity WHERE id = :entityId")
    OrgEntity getEntityById(Integer entityId);

    @Update
    void updateEntity(OrgEntity orgEntity);
}
