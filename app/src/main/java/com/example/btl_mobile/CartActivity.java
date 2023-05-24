package com.example.btl_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl_mobile.adapter.RecycleViewAdapterCart;
import com.example.btl_mobile.database.DB;
import com.example.btl_mobile.model.Cart;
import com.example.btl_mobile.model.User;

import java.io.Serializable;
import java.util.List;
import java.util.StringTokenizer;

public class CartActivity extends AppCompatActivity implements RecycleViewAdapterCart.ItemListener {
    private RecyclerView recyclerView;
    private RecycleViewAdapterCart adapterCart;
    private TextView tittle,sum;
    private DB db;
    private User user;
    private List<Cart> carts;
    private Button btnPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.recyclerView);
        tittle = findViewById(R.id.title);
        sum = findViewById(R.id.textSum);
        btnPay = findViewById(R.id.btnPay);
        adapterCart = new RecycleViewAdapterCart();
        db = new DB(this);
        user = (User) getIntent().getSerializableExtra("user");
        carts = db.getListCartByUserId(user.getId(),0);
        adapterCart.setmList(carts);
        adapterCart.setItemListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterCart);
        registerForContextMenu(recyclerView);


        StringTokenizer tokenizer = new StringTokenizer(user.getUsername(),"@");
        String username = "";
        while (tokenizer.hasMoreTokens()){
            username = tokenizer.nextToken();
            break;
        }
        tittle.setText("GIO HANG CUA "+username.toUpperCase());
        sum.setText("Tong tien "+ caculatedPrice(carts)+"d");
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(carts.size()==0) {
                   Toast.makeText(CartActivity.this,"Khong co mat hang trong gio hang",Toast.LENGTH_SHORT).show();
               }
               else{
                   Intent intent = new Intent(CartActivity.this,PaymentActivity.class);
                   intent.putExtra("user",user);
                   intent.putExtra("carts", (Serializable) carts);
                   startActivity(intent);
               }
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

    @Override
    public void onItemClick(View view, int position) {
        Cart cart = carts.get(position);
        carts.remove(position);
        db.deleteCart(cart);
        adapterCart.setmList(carts);
        sum.setText("Tong tien "+ caculatedPrice(carts)+"d");
    }



    @Override
    protected void onResume() {
        super.onResume();
        carts = db.getListCartByUserId(user.getId(),0);
        adapterCart.setmList(carts);
        sum.setText("Tong tien "+ caculatedPrice(carts)+"d");
    }



}