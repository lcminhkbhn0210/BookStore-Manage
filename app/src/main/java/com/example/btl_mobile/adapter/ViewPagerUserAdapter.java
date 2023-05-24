package com.example.btl_mobile.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.btl_mobile.fragment.Fragment_favourite_user;
import com.example.btl_mobile.fragment.Fragment_home_user;
import com.example.btl_mobile.fragment.Fragment_search_user;


public class ViewPagerUserAdapter extends FragmentPagerAdapter {
    private int page_number;
    public ViewPagerUserAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.page_number = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Fragment_home_user();
            case 1:
                return new Fragment_search_user();
            case 2:
                return new Fragment_favourite_user();
            default:
                return new Fragment_home_user();
        }
    }

    @Override
    public int getCount() {
        return page_number;
    }
}
