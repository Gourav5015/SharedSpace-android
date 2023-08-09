package com.example.sharedspace;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.sharedspace.ApiModels.Post;
import com.example.sharedspace.ApiModels.User;
import com.example.sharedspace.NavigationToolbar.NavigationToolbar;
import com.example.sharedspace.adaptors.RecyclerViewPostsAdaptor;
import com.example.sharedspace.api.AuthApi;
import com.example.sharedspace.api.PostApi;
import com.example.sharedspace.entities.AuthTokenTable;
import com.example.sharedspace.repository.AuthTokenHelper;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeLayout extends AppCompatActivity {

    private final String API_BASE_URL="https://sharedspace.pythonanywhere.com/api/";
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
        // Navigation bar
        //Toolbar Nav= NavigationToolbar.getNavToolbar(getApplicationContext());
        Toolbar Nav= findViewById(R.id.Navigation);
//        setSupportActionBar(Nav);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setTitle("Sharedspace");
        NavigationToolbar nav = new NavigationToolbar(this, Nav);
        nav.setNav();

        // rcycler view
        ArrayList<Post>  allposts = new ArrayList<>();
        RecyclerView recyclerView= findViewById(R.id.Posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        RecyclerViewPostsAdaptor adaptor= new RecyclerViewPostsAdaptor(this,allposts);
//        recyclerView.setAdapter(adaptor);
        //Retrofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        PostApi postApi = retrofit.create(PostApi.class);
        AuthApi authApi=retrofit.create(AuthApi.class);
        AuthTokenHelper authTokenHelper=AuthTokenHelper.getInstance(this);
        ArrayList<AuthTokenTable> authToken= (ArrayList<AuthTokenTable>)authTokenHelper.authTokenTableDao().getAuthToken();
        String token= authToken.get(0).getAuthToken();

        Call<User> usercall=authApi.getuser(token);
        usercall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        Call<List<com.example.sharedspace.ApiModels.Post>> call =postApi.getAllPost();


        call.enqueue(new Callback<List<com.example.sharedspace.ApiModels.Post>>() {
            @Override
            public void onResponse(Call<List<com.example.sharedspace.ApiModels.Post>> call, Response<List<com.example.sharedspace.ApiModels.Post>> response) {
                if(response.isSuccessful()){
                    List<Post>  posts =response.body();
                    for (Post p:posts){
                        allposts.add(p);
                    }
                    RecyclerViewPostsAdaptor adaptor= new RecyclerViewPostsAdaptor(HomeLayout.this,allposts,user,token);
                    recyclerView.setAdapter(adaptor);
                }
            }
            @Override
            public void onFailure(Call<List<com.example.sharedspace.ApiModels.Post>> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.top_navigation_options,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int Itemid= item.getItemId();
        if (Itemid==R.id.option_new_post) {
            startActivity(new Intent(getApplicationContext(),NewPostActivity.class));
        }
        else if (Itemid==R.id.option_profile) {
            startActivity(new Intent(getApplicationContext(),ProfileLayout.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder exitDialog = new AlertDialog.Builder(HomeLayout.this);
        exitDialog.setTitle("Exit");
        exitDialog.setMessage("Are you  sure");
        exitDialog.setIcon(R.drawable.baseline_question_mark_24);
        exitDialog.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                HomeLayout.super.onBackPressed();
            }
        });
        exitDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        exitDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        exitDialog.show();
;    }

}