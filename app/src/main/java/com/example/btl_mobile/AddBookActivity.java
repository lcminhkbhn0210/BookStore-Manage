package com.example.btl_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.btl_mobile.database.DB;
import com.example.btl_mobile.model.Book;

import java.util.Calendar;

public class AddBookActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText eTitle,ePrice,eCreation,eContent,eAuthor,eImg;
    private Spinner spinner;
    private Button btnAdd,btnCancel;
    private DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        init();
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        eCreation.setOnClickListener(this);
    }
    public void init(){
        eTitle = findViewById(R.id.eTitle);
        ePrice = findViewById(R.id.ePrice);
        eCreation = findViewById(R.id.eCreation);
        eContent = findViewById(R.id.eContent);
        eAuthor = findViewById(R.id.eAuthor);
        eImg = findViewById(R.id.eImg);
        spinner = findViewById(R.id.spType);
        btnAdd = findViewById(R.id.btAdd);
        db = new DB(this);
        System.out.println(R.drawable.book_giaitich);
        System.out.println(R.drawable.book_gdcd);
        System.out.println(R.drawable.book_gd1);
        System.out.println(R.drawable.book_kh1);
        System.out.println(R.drawable.book_tt1);
        System.out.println(R.drawable.book_tt2);
        System.out.println(R.drawable.book_truyentranh1);
        System.out.println(R.drawable.book_kh2);
        btnCancel = findViewById(R.id.btCancel);
        spinner.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,getResources().getStringArray(R.array.type)));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.eCreation:
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(AddBookActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String date1 = "";
                        if(m>8){
                            date1 = d+"/"+(m+1)+"/"+y;
                        }else {
                            date1 = d+"/0"+(m+1)+"/"+y;
                        }
                        eCreation.setText(date1);
                    }
                },year,month,day);
                dialog.show();
                break;
            case R.id.btAdd:
                if(!eTitle.getText().toString().isEmpty() ){
                    Book book = new Book();
                    book.setTitle(eTitle.getText().toString());
                    book.setPrice(ePrice.getText().toString());
                    book.setAuthor(eAuthor.getText().toString());
                    book.setCreation(eCreation.getText().toString());
                    book.setContent(eContent.getText().toString());
                    book.setImg(eImg.getText().toString());
                    book.setType(spinner.getSelectedItem().toString());
                    db.addBook(book);
                    finish();
                }
            case R.id.btCancel:
                finish();
        }
    }
}