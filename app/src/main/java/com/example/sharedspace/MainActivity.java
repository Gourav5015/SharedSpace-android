package com.example.sharedspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharedspace.ApiModels.Authentication;
import com.example.sharedspace.api.AuthApi;
import com.example.sharedspace.api.PostApi;
import com.example.sharedspace.entities.AuthTokenTable;
import com.example.sharedspace.repository.AuthTokenHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String API_BASE_URL="https://sharedspace.pythonanywhere.com/api/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton =findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                EditText username=findViewById(R.id.username);
                TextView password=(TextView)(findViewById(R.id.password));
                String user=username.getText().toString();
                String Password= password.getText().toString();

                // api
                Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create()).build();
                AuthApi authApi = retrofit.create(AuthApi.class);

                Call<Authentication> call = authApi.login(user,Password);

                call.enqueue(new Callback<Authentication>() {
                    @Override
                    public void onResponse(Call<Authentication> call, Response<Authentication> response) {
                        if(response.isSuccessful()){
                            Authentication authentication= response.body();;
                            if (authentication.getAuthToken().length()>0){
                                AuthTokenHelper authTokenHelper=AuthTokenHelper.getInstance(MainActivity.this);
                                authTokenHelper.authTokenTableDao().insertAuthToken(new AuthTokenTable(authentication.getAuthToken()));
                                Intent intent=new Intent(getApplicationContext(),HomeLayout.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Wrong username or password",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Authentication> call, Throwable t) {

                        Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }


}