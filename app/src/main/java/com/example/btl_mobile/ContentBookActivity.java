package com.example.btl_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btl_mobile.database.DB;
import com.example.btl_mobile.model.Book;
import com.example.btl_mobile.model.User;

public class ContentBookActivity extends AppCompatActivity {
    private TextView content,title;
    private ImageView img;
    private Book book;
    private Button btnDelete;
    private User user;
    private DB db;
    private int check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_book);
        content = findViewById(R.id.tv_book_summary);
        title = findViewById(R.id.tv_book_title);
        btnDelete = findViewById(R.id.btndelete);
        check = getIntent().getIntExtra("check",1);
        db = new DB(this);
        user  = (User) getIntent().getSerializableExtra("user");
        img = findViewById(R.id.iv_book_cover);
        book = (Book) getIntent().getSerializableExtra("book");
        content.setText(book.getContent());
        title.setText(book.getTitle());
        img.setImageResource(Integer.parseInt(book.getImg()));
        if(check==0){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteFavouriteByUserIdAndBookId(user.getId(),book.getId());
                finish();
            }
        });}else {
            btnDelete.setVisibility(View.GONE);
        }
    }
}