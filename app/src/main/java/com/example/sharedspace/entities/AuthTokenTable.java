package com.example.sharedspace.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "AuthTokenTable")
public class AuthTokenTable {
    @PrimaryKey
    @NonNull
    private String AuthToken;

    public String getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(String authToken) {
        AuthToken = authToken;
    }

    public  AuthTokenTable(){}
    @Ignore
    public AuthTokenTable(String authToken) {
        AuthToken = authToken;
    }
}
