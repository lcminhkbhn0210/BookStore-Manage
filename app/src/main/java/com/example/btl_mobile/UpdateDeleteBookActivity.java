package com.example.btl_mobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

public class UpdateDeleteBookActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText eTitle,ePrice,eCreation,eContent,eAuthor,eImg;
    private Spinner spinner;
    private Button btnUpdate,btnBack,btnDelete;
    private Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_book);
        init();
    }
    public void init(){
        eTitle = findViewById(R.id.eTitle);
        ePrice = findViewById(R.id.ePrice);
        eCreation = findViewById(R.id.eCreation);
        eContent = findViewById(R.id.eContent);
        eAuthor = findViewById(R.id.eAuthor);
        eImg = findViewById(R.id.eImg);
        spinner = findViewById(R.id.spType);
        btnUpdate = findViewById(R.id.btUpdate);
        btnBack = findViewById(R.id.btBack);
        btnDelete = findViewById(R.id.btDelete);
        book = (Book) getIntent().getSerializableExtra("book");
        eTitle.setText(book.getTitle());
        ePrice.setText(book.getPrice());
        eContent.setText(book.getContent());
        eAuthor.setText(book.getAuthor());
        eCreation.setText(book.getCreation());
        eImg.setText(book.getImg());
        spinner.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,getResources().getStringArray(R.array.type)));
        int p = 0;
        for(int i=0;i<spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).equals(book.getType())) {
                p=i;
                break;
            }
        }
        spinner.setSelection(p);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        DB db = new DB(this);
        switch (view.getId()){
            case R.id.eCreation:
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteBookActivity.this, new DatePickerDialog.OnDateSetListener() {
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
            case R.id.btDelete:
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Thong Bao xoa");
                builder.setMessage("Ban co chac muon xoa "+ book.getTitle()+" nay khong?");
                builder.setIcon(R.drawable.ic_remove);
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteBook(book.getId());
                        db.getAllBill();
                        finish();
                    }
                });
                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog1 = builder.create();
                dialog1.show();
                break;
            case R.id.btBack:
                finish();
            case R.id.btUpdate:
                if(!eTitle.getText().toString().isEmpty() && !eContent.getText().toString().isEmpty()){
                    Book book1 = new Book();
                    book1.setId(book.getId());
                    book1.setTitle(eTitle.getText().toString());
                    book1.setPrice(ePrice.getText().toString());
                    book1.setContent(eContent.getText().toString());
                    book1.setAuthor(eAuthor.getText().toString());
                    book1.setType(spinner.getSelectedItem().toString());
                    book1.setImg(eImg.getText().toString());
                    book1.setCreation(eCreation.getText().toString());
                    db.updateBook(book1);
                }
                finish();
        }

    }
}