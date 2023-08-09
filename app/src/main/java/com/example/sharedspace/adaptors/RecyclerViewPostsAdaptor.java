package com.example.sharedspace.adaptors;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharedspace.ApiModels.Post;
import com.example.sharedspace.ApiModels.User;
import com.example.sharedspace.R;
import com.example.sharedspace.api.PostApi;


import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerViewPostsAdaptor extends RecyclerView.Adapter<RecyclerViewPostsAdaptor.ViewHolder> {

    Context context;
    ArrayList<Post> allposts;
    User user;

    int index=-1;
    private final String API_BASE_URL="https://sharedspace.pythonanywhere.com/api/";

    Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();
    PostApi postApi = retrofit.create(PostApi.class);
    String token;
    public RecyclerViewPostsAdaptor (Context context,ArrayList<Post> allposts,User user,String token){
        this.context =context;
        this.allposts=allposts;
        this.user=user;
        this.token=token;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.post_layout, parent,false);
        ViewHolder viewHolder =new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.author.setText(allposts.get(position).getAuthor());
        holder.title.setText(allposts.get(position).getTitle());
        holder.timestamp.setText(allposts.get(position).getTimestamp());
        holder.content.setText(allposts.get(position).getContent());
        if(!((user.getUsername()).equals(allposts.get(position).getAuthor()))) {
            holder.itemView.findViewById(R.id.delte_icon).setVisibility(View.GONE);
            holder.itemView.findViewById(R.id.edit_icon).setVisibility(View.GONE);
        }
        else {

            holder.itemView.findViewById(R.id.delte_icon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setClickable(false);
                    deleteCall(holder.itemView,position);
                }
            });
        }
        if(position>index)
        {
            index++;
            animationSetter(holder.itemView);
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.post_options);
                Button view_post=dialog.findViewById(R.id.view_post);
                Button delete=dialog.findViewById(R.id.delete_post);
                Button update_post=dialog.findViewById(R.id.update_post);
                if(!((user.getUsername()).equals(allposts.get(position).getAuthor()))) {
                    update_post.setVisibility(View.GONE);
                    delete.setVisibility(View.GONE);
                }
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        deleteCall(holder.itemView,position);
                    }
                });
                dialog.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return allposts.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        TextView author, content, timestamp, title;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.article);
            card = itemView.findViewById(R.id.card);
            timestamp = itemView.findViewById(R.id.timestamp);
            title = itemView.findViewById(R.id.title);
        }


    }
    private  void animationSetter( View view) {
        Animation slide_in= AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left );
        view.startAnimation(slide_in);
    }
    private void deleteCall(View view, int position) {
        Call<Void> call = postApi.delete(token, allposts.get(position).getPostslug());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                    Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
