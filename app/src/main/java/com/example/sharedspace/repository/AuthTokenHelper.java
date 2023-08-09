package com.example.sharedspace.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.sharedspace.dao.AuthTokenTableDao;
import com.example.sharedspace.entities.AuthTokenTable;

@Database(entities = AuthTokenTable.class, exportSchema = false,version = 1)
public  abstract class AuthTokenHelper extends RoomDatabase {

    private static  final String DB_NAME="Database";
    public abstract AuthTokenTableDao  authTokenTableDao();
    private static  AuthTokenHelper instance ;

    public static  synchronized  AuthTokenHelper getInstance (Context context){
        if (instance==null){
            instance= Room.databaseBuilder(context,AuthTokenHelper.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
            return instance;
        }
        return instance;
    }
}
