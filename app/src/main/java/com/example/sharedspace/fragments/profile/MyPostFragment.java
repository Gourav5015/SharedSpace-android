package com.example.sharedspace.fragments.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sharedspace.ApiModels.Post;
import com.example.sharedspace.ApiModels.User;
import com.example.sharedspace.HomeLayout;
import com.example.sharedspace.R;
import com.example.sharedspace.adaptors.RecyclerViewPostsAdaptor;
import com.example.sharedspace.api.AuthApi;
import com.example.sharedspace.api.PostApi;
import com.example.sharedspace.entities.AuthTokenTable;
import com.example.sharedspace.repository.AuthTokenHelper;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyPostFragment extends Fragment {
    Context context;
    private final String API_BASE_URL="https://sharedspace.pythonanywhere.com/api/";

    Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();
    PostApi postApi = retrofit.create(PostApi.class);
    AuthApi authApi=retrofit.create(AuthApi.class);
    User user;

    AuthTokenHelper authTokenHelper;

    public MyPostFragment(Context context) {
        // Required empty public constructor
        this.context=context;
        this.authTokenHelper=AuthTokenHelper.getInstance(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_post, container, false);
        RecyclerView recyclerView= view.findViewById(R.id.MyPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
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
        Call<List<Post>> call = postApi.getUserPost(token);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<com.example.sharedspace.ApiModels.Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){
                    ArrayList<Post>allposts =(ArrayList<Post>) response.body();
                    RecyclerViewPostsAdaptor adaptor= new RecyclerViewPostsAdaptor(context,allposts,user,token);
                    recyclerView.setAdapter(adaptor);
                }
            }
            @Override
            public void onFailure(Call<List<com.example.sharedspace.ApiModels.Post>> call, Throwable t) {

            }
        });
        return  view;
    }
}