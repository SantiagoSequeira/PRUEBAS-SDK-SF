package com.example.SalesforceChatTester.DatabaseFolder;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Organization.class, UserItemData.class, OrgEntity.class}, version = 11, exportSchema = true)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB database;
    private static String DATABASE_NAME = "MainData";

    public synchronized static RoomDB getInstance(final Context context) {
        if(database == null) {
            database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RoomDB.class, DATABASE_NAME
            ).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return database;
    }

    public abstract MainDao mainDao();
}
