package com.example.btl_mobile.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.btl_mobile.fragment.Fragment_listbook;
import com.example.btl_mobile.fragment.Fragment_stat;
import com.example.btl_mobile.fragment.Frament_search;

public class ViewPagerAdminAdapter extends FragmentPagerAdapter {
    private int page_number;
    public ViewPagerAdminAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.page_number = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Fragment_listbook();
            case 1:
                return new Fragment_stat();
            case 2:
                return new Frament_search();
            default:
                return new Fragment_listbook();
        }
    }

    @Override
    public int getCount() {
        return page_number;
    }
}
