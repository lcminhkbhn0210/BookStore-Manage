package com.example.btl_mobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_mobile.R;
import com.example.btl_mobile.database.DB;
import com.example.btl_mobile.model.Book;
import com.example.btl_mobile.model.Cart;
import com.example.btl_mobile.model.User;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapterUser extends RecyclerView.Adapter<RecycleViewAdapterUser.UserHomeViewHolder> {
    private List<Book> mList;
    private ItemListener itemListener;
    private int userid;
    public RecycleViewAdapterUser(){
        mList = new ArrayList<>();
    }
    public void setItemListener(ItemListener itemListener){
        this.itemListener = itemListener;
    }
    public void setmList(List<Book> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }
    public void setUserid(int userid){
        this.userid = userid;
    }
    public Book getItem(int position){
        return mList.get(position);
    }
    @NonNull
    @Override
    public UserHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_user,parent,false);
        return new UserHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHomeViewHolder holder, int position) {
        if(mList==null)
            return;
        Book book = mList.get(holder.getAdapterPosition());
        holder.img.setImageResource(Integer.parseInt(book.getImg()));
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        holder.type.setText(book.getType());
        holder.creation.setText(book.getCreation());
    }

    @Override
    public int getItemCount() {
        if(mList!=null)
            return mList.size();
        return 0;
    }

    public class UserHomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title,author,creation,type;
        private ImageView img;
        private Button btnadd_cart,btnadd_favourite;
        public UserHomeViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.book_title);
            author = view.findViewById(R.id.book_author);
            creation = view.findViewById(R.id.book_creation);
            type = view.findViewById(R.id.book_type);
            img = view.findViewById(R.id.book_image);
            btnadd_cart = view.findViewById(R.id.btnAddCart);
            btnadd_favourite = view.findViewById(R.id.btnAddFavou);
            view.setOnClickListener(this);
            btnadd_favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DB db = new DB(v.getContext());
                    db.addFavourite(userid,mList.get(getAdapterPosition()).getId());
                    Toast.makeText(v.getContext(),"Ban da them thanh cong",Toast.LENGTH_SHORT).show();
                }
            });
            btnadd_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DB db = new DB(v.getContext());
                    if(db.checkCardExist(userid,0,mList.get(getAdapterPosition()).getId())==false){
                        Cart cart = new Cart();
                        cart.setSold(0);
                        cart.setUser(db.getUserById(userid));
                        cart.setAmount(1);
                        cart.setBook(mList.get(getAdapterPosition()));
                        db.addCart(cart);
                        Toast.makeText(v.getContext(),"Ban da them thanh cong vao gio hang",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Cart cart = db.getCart(userid,mList.get(getAdapterPosition()).getId(),0);
                        cart.setAmount(cart.getAmount()+1);
                        db.updateCart(cart);
                        Toast.makeText(v.getContext(),"Ban da them thanh cong vao gio hang",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            if(itemListener!=null)
                itemListener.onItemClick(v,getAdapterPosition());
        }
    }
    public interface ItemListener{
        void onItemClick(View view,int position);
    }
}
