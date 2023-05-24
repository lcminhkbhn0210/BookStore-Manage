package com.example.btl_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.btl_mobile.adapter.ViewPagerUserAdapter;
import com.example.btl_mobile.model.Book;
import com.example.btl_mobile.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class UserActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerUserAdapter adapter;
    private FloatingActionButton floatingActionButton;
    private List<Book> Cart;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        init();
        adapter = new ViewPagerUserAdapter(getSupportFragmentManager(),3);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_search);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_statistic);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this,CartActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }
    private void init(){

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab);
        floatingActionButton = findViewById(R.id.fab);
        user = (User) getIntent().getSerializableExtra("user");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mHome:
                viewPager.setCurrentItem(0);
                break;
            case R.id.mFavourite:
                viewPager.setCurrentItem(2);
                break;
            case R.id.mLogout:
                Intent intent = new Intent(UserActivity.this,LoginActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}