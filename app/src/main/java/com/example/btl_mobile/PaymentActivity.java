package com.example.btl_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl_mobile.adapter.RecycleViewAdapterCart;
import com.example.btl_mobile.database.DB;
import com.example.btl_mobile.model.Bill;
import com.example.btl_mobile.model.Cart;
import com.example.btl_mobile.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class PaymentActivity extends AppCompatActivity {
    private List<Cart> carts;
    private User user;
    private RadioGroup group;
    private RadioButton bt1,bt2;
    private Button btnPay;
    private CheckBox checkBox1,checkBox2;
    private RecyclerView recyclerView;
    private RecycleViewAdapterCart adapterCart;
    private TextView tittle,sum;
    private DB db;
    private double sum1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        init();
        recyclerView = findViewById(R.id.recyclerView);
        tittle = findViewById(R.id.title);
        sum = findViewById(R.id.textSum);
        btnPay = findViewById(R.id.btnPay);
        adapterCart = new RecycleViewAdapterCart();
        db = new DB(this);
        user = (User) getIntent().getSerializableExtra("user");
        carts = db.getListCartByUserId(user.getId(),0);
        adapterCart.setmList(carts);
        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterCart);
        StringTokenizer tokenizer = new StringTokenizer(user.getUsername(),"@");
        String username = "";
        while (tokenizer.hasMoreTokens()){
            username = tokenizer.nextToken();
            break;
        }
        tittle.setText("GIO HANG CUA "+username.toUpperCase());
        sum.setText("Tong tien "+ caculatedPrice(carts)+"d");
        sum1 = Double.parseDouble(caculatedPrice(carts));
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sum.setText("Tong tien "+(sum1+5000)+"d");
                    sum1 = sum1 +5000;
                }
                else {
                    sum.setText("Tong tien "+(sum1-5000)+"d");
                    sum1 = sum1 -5000;
                }
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sum.setText("Tong tien "+(sum1+6000)+"d");
                    sum1 = sum1 +6000;
                }
                else {
                    sum.setText("Tong tien "+(sum1-6000)+"d");
                    sum1 = sum1 -6000;
                }
            }
        });


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Cart cart:carts){
                    if(db.getAmountByBookId(cart.getBook().getId())<=10){
                        Toast.makeText(PaymentActivity.this,"Khong",Toast.LENGTH_SHORT).show();
                    }else {
                        Bill bill = new Bill();
                        bill.setCarts(carts);
                        Date d = new Date();
                        bill.setCreation(new SimpleDateFormat("dd/MM/yyyy").format(d));
                        bill.setTotal(sum1);
                        for(Cart cartt:bill.getCarts()){
                            cartt.setSold(1);
                            db.updateCart(cartt);
                        }
                        db.addBill(bill);
                    }
                }
                finish();
            }
        });

    }
    public String caculatedPrice(List<Cart> cartList){
        double sum = 0;
        for(Cart cart:cartList){
            sum = sum + Double.parseDouble(cart.getBook().getPrice()) * cart.getAmount();
        }
        return String.valueOf(sum);
    }
    private void init(){
        carts = (List<Cart>) getIntent().getSerializableExtra("carts");
        user = (User) getIntent().getSerializableExtra("user");
        group = findViewById(R.id.radio_group);
        bt1 = findViewById(R.id.radio1);
        bt2 = findViewById(R.id.radio2);
        btnPay = findViewById(R.id.btnPay);
        checkBox1 = findViewById(R.id.check1);
        checkBox2 = findViewById(R.id.check2);
    }
}