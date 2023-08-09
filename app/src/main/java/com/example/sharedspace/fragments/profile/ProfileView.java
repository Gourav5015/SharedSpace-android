package com.example.sharedspace.fragments.profile;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.sharedspace.ApiModels.User;
import com.example.sharedspace.ProfileLayout;
import com.example.sharedspace.R;
import com.example.sharedspace.api.AuthApi;
import com.example.sharedspace.entities.AuthTokenTable;
import com.example.sharedspace.repository.AuthTokenHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileView extends Fragment {

    private final String API_BASE_URL="https://sharedspace.pythonanywhere.com/api/";
    private final String IMAGE_BASE_URL="https://sharedspace.pythonanywhere.com/";


    Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();
    AuthApi authApi = retrofit.create(AuthApi.class);

    Context context;
    AuthTokenHelper authTokenHelper;
    public ProfileView(Context context) {
        // Required empty public constructor
        this.authTokenHelper=AuthTokenHelper.getInstance(context);
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_profile_view, container, false);
        TextView name= view.findViewById(R.id.Name);
        TextView username=view.findViewById(R.id.username);
        TextView about=view.findViewById(R.id.About);
        TextView email=view.findViewById(R.id.email);
        ImageView profilepicture=view.findViewById(R.id.profilepicture);
        ProgressBar progressBar =view.findViewById(R.id.progres);
        ArrayList<AuthTokenTable> authToken= (ArrayList<AuthTokenTable>)authTokenHelper.authTokenTableDao().getAuthToken();
        String token= authToken.get(0).getAuthToken();
        //String token= "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImFkbWluIn0.6j__e_1UoUYdZCIA4rCkw1gafh9jqiw52snk11oalqQ";

        Call<User> call= authApi.getuser(token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.VISIBLE);
                    User user=response.body();
                    name.setText(user.getFirstname()+" "+user.getLastname());
                    username.setText(user.getUsername());
                    about.setText(user.getAbout());
                    email.setText(user.getEmail());
                    Glide.with(context).load(IMAGE_BASE_URL+user.getImage()).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    }).override(100,100).into(profilepicture);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        return view;
    }
}