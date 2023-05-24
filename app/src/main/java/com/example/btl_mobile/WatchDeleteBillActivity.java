package com.example.btl_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.btl_mobile.adapter.RecycleViewAdapterCart;
import com.example.btl_mobile.database.DB;
import com.example.btl_mobile.model.Bill;
import com.example.btl_mobile.model.Cart;
import com.example.btl_mobile.model.User;

import java.util.List;
import java.util.StringTokenizer;

public class WatchDeleteBillActivity extends AppCompatActivity {
    private List<Cart> carts;
    private User user;
    private Button btnDelete;
    private RecyclerView recyclerView;
    private RecycleViewAdapterCart adapterCart;
    private TextView tittle,sum;
    private DB db;
    private Bill bill;
    private double sum1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_delete_bill);
        bill = (Bill) getIntent().getSerializableExtra("bill");
        recyclerView = findViewById(R.id.recyclerView);
        tittle = findViewById(R.id.title);
        sum = findViewById(R.id.textSum);
        btnDelete = findViewById(R.id.btndelete);
        adapterCart = new RecycleViewAdapterCart();
        db = new DB(this);
        user = db.getUserById(bill.getCarts().get(0).getUser().getId());
        carts = db.getListCartByBillId(bill.getId());
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
        tittle.setText("HOA DON CUA "+username.toUpperCase());
        sum.setText("Tong tien "+ bill.getTotal()+"d");
        sum1 = Double.parseDouble(caculatedPrice(carts));
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteBill(bill);
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
}