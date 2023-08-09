package com.example.sharedspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sharedspace.NavigationToolbar.NavigationToolbar;
import com.example.sharedspace.adaptors.ViewPagerFragmentManagerProfileAdaptor;
import com.example.sharedspace.api.AuthApi;
import com.example.sharedspace.api.PostApi;
import com.example.sharedspace.entities.AuthTokenTable;
import com.example.sharedspace.repository.AuthTokenHelper;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.function.IntToDoubleFunction;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileLayout extends AppCompatActivity {

    private final String API_BASE_URL="https://sharedspace.pythonanywhere.com/api/";
    AuthTokenHelper authTokenHelper=AuthTokenHelper.getInstance(ProfileLayout.this);
    Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();
    AuthApi authApi = retrofit.create(AuthApi.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_layout);
        TabLayout tabs= findViewById(R.id.tabOption);
        ViewPager pages=findViewById(R.id.page);
        ViewPagerFragmentManagerProfileAdaptor pagerAdaptor = new ViewPagerFragmentManagerProfileAdaptor(getSupportFragmentManager(),this);
        pages.setAdapter(pagerAdaptor);
        tabs.setupWithViewPager(pages);
        Toolbar Nav= findViewById(R.id.Nav);
        setSupportActionBar(Nav);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sharedspace");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.top_navigation_menu_profile_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int Itemid= item.getItemId();
        if (Itemid==R.id.option_change_password) {
            Toast.makeText(getApplicationContext(),"password",2000).show();
        }
        else if (Itemid==R.id.option_logout) {
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);

            AuthTokenHelper authTokenHelper=AuthTokenHelper.getInstance(ProfileLayout.this);
            ArrayList<AuthTokenTable> authToken= (ArrayList<AuthTokenTable>)authTokenHelper.authTokenTableDao().getAuthToken();
            authTokenHelper.authTokenTableDao().deleteAuthToken(authToken.get(0));
            finishAffinity();
            startActivity(intent);
        }
        else if (Itemid==R.id.option_new_post) {
            startActivity(new Intent(getApplicationContext(),NewPostActivity.class));
        }
        else {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}