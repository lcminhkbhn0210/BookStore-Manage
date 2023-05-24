package com.example.btl_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.btl_mobile.database.DB;

public class AdminActivity extends AppCompatActivity {
    private Button btnmanage_client,btn_statistic,btnmanage_book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnmanage_client = findViewById(R.id.btnmanage_client);
        btn_statistic = findViewById(R.id.btnstat);
        btnmanage_book = findViewById(R.id.btnmanage_book);
        btnmanage_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this,ManageBookActivity.class);
                startActivity(intent);
            }
        });

    }
}