package com.example.sharedspace.adaptors;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sharedspace.fragments.profile.MyPostFragment;
import com.example.sharedspace.fragments.profile.ProfileView;

public class ViewPagerFragmentManagerProfileAdaptor  extends FragmentPagerAdapter {
    Context context;
    public ViewPagerFragmentManagerProfileAdaptor(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0) {
             return new ProfileView(context);
        }
        else {
            return new MyPostFragment(context);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return "Profile";
        }
        else {
            return "My Post";
        }
    }
}
