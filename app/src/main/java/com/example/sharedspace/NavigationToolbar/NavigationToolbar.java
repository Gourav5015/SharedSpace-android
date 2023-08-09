package com.example.sharedspace.NavigationToolbar;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.sharedspace.HomeLayout;
import com.example.sharedspace.ProfileLayout;
import com.example.sharedspace.R;

public class NavigationToolbar {
    HomeLayout context;
    Toolbar Nav;
    public  NavigationToolbar(HomeLayout context , Toolbar Nav) {
        this.context=context;
        this.Nav=Nav;
    }
    public   void setNav () {
        context.setSupportActionBar(Nav);
        context.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        context.getSupportActionBar().setTitle("Sharedspace");
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        new MenuInflater(context).inflate(R.menu.top_navigation_options,menu);
//        return HomeLayout.super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        int Itemid= item.getItemId();
//        if (Itemid==R.id.option_new_post) {
//            Toast.makeText(getApplicationContext(),"new",2000).show();
//        }
//        else if (Itemid==R.id.option_profile) {
//            startActivity(new Intent(getApplicationContext(), ProfileLayout.class));
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
