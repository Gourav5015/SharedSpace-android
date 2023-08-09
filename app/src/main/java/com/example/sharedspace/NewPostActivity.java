package com.example.sharedspace;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharedspace.ApiModels.Post;
import com.example.sharedspace.NavigationToolbar.NavigationToolbar;
import com.example.sharedspace.api.PostApi;
import com.example.sharedspace.entities.AuthTokenTable;
import com.example.sharedspace.repository.AuthTokenHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewPostActivity extends AppCompatActivity {

    private final String API_BASE_URL="https://sharedspace.pythonanywhere.com/api/";

    Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();
    PostApi postApi = retrofit.create(PostApi.class);

    AuthTokenHelper authTokenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        Toolbar Nav= findViewById(R.id.Navigation);
        setSupportActionBar(Nav);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sharedspace");
//        NavigationToolbar navigationToolbar=new NavigationToolbar(NewPostActivity.class,(Toolbar)findViewById(R.id.Navigation));
        authTokenHelper=AuthTokenHelper.getInstance(this);;
        ArrayList<AuthTokenTable> authToken= (ArrayList<AuthTokenTable>)authTokenHelper.authTokenTableDao().getAuthToken();
        String token= authToken.get(0).getAuthToken();
        Button submit= findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit.setEnabled(false);
                EditText editTextTitle=findViewById(R.id.posttitle);
                EditText editTextContent=findViewById(R.id.postcontent);
                String title=editTextTitle.getText().toString();
                String content= editTextContent.getText().toString();
                if ((title.length()>0) && (content.length()>0)){
                    Call<Post> call=postApi.create(token,title,content);
                    call.enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            if(response.isSuccessful()){
                                startActivity( new Intent(getApplicationContext(),HomeLayout.class));
                                finishAffinity();
                            }else{
                                submit.setEnabled(true);
                            }
                        }
                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {
                            submit.setEnabled(true);
                        }
                    });
                }
                else {
                    submit.setEnabled(true);
                    Toast.makeText(getApplicationContext(),"Title or content cant be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {

        EditText editTextTitle=findViewById(R.id.posttitle);
        EditText editTextContent=findViewById(R.id.postcontent);
        String title=editTextTitle.getText().toString();
        String content= editTextContent.getText().toString();
        if ((title.length()>0) || (content.length()>0)) {

            AlertDialog.Builder exitDialog = new AlertDialog.Builder(NewPostActivity.this);

            exitDialog.setTitle("Discard Post");
            exitDialog.setMessage("Are you  sure");
            exitDialog.setIcon(R.drawable.baseline_question_mark_24);
            exitDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    NewPostActivity.super.onBackPressed();
                }
            });
            exitDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            exitDialog.show();
        }
        else {
            NewPostActivity.super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemid =item.getItemId();
        if(itemid==android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}