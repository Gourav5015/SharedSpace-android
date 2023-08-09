package com.example.sharedspace.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sharedspace.entities.AuthTokenTable;

import java.util.List;

@Dao
public interface AuthTokenTableDao {

    @Query("Select * from  AuthTokenTable")
    List<AuthTokenTable> getAuthToken();

    @Insert
    void insertAuthToken (AuthTokenTable authTokenTable);

    @Delete
    void deleteAuthToken(AuthTokenTable authTokenTable);
}
