
package com.example.sharedspace;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.sharedspace.entities.AuthTokenTable;
import com.example.sharedspace.repository.AuthTokenHelper;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView splash_title= findViewById(R.id.splash_title);
        Animation blink= AnimationUtils.loadAnimation(this,R.anim.splash_animation);
        splash_title.setAnimation(blink);
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 AuthTokenHelper authTokenHelper=AuthTokenHelper.getInstance(SplashActivity.this);
                 ArrayList<AuthTokenTable> authToken= (ArrayList<AuthTokenTable>)authTokenHelper.authTokenTableDao().getAuthToken();
                 if (authToken.size()>0){
                     startActivity(new Intent(getApplicationContext(),HomeLayout.class));
                     finish();
                 }
                 else {
                     startActivity(intent);
                     finish();
                 }

             }
         },5000);
    }
}