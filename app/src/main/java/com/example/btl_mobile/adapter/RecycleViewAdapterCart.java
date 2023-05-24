package com.example.btl_mobile.adapter;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_mobile.R;
import com.example.btl_mobile.model.Book;
import com.example.btl_mobile.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapterCart extends RecyclerView.Adapter<RecycleViewAdapterCart.HomeCardViewHolder> {
    private List<Cart> mList;
    private ItemListener itemListener;

    private OnItemContextMenuClickListener mContextMenuListener;

    public void setOnItemContextMenuClickListener(OnItemContextMenuClickListener listener) {
        mContextMenuListener = listener;
    }
    public void setmList(List<Cart> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }
    public Cart getItem(int position){
        return mList.get(position);
    }
    public RecycleViewAdapterCart(){
        this.mList = new ArrayList<>();
    }
    public void setItemListener(ItemListener itemListener){
        this.itemListener = itemListener;
    }
    @NonNull
    @Override
    public HomeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        HomeCardViewHolder holder = new HomeCardViewHolder(view);
        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(Menu.NONE,R.id.mAdd,Menu.NONE,"Add");
                menu.add(Menu.NONE,R.id.mSubtract,Menu.NONE,"Sub");
                menu.add(Menu.NONE,R.id.mDelete,Menu.NONE,"Delete");
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCardViewHolder holder, int position) {
        if(mList==null)
            return;
        Cart cart = mList.get(holder.getAdapterPosition());
        holder.img.setImageResource(Integer.parseInt(cart.getBook().getImg()));
        holder.title.setText(cart.getBook().getTitle());
        holder.author.setText(cart.getBook().getAuthor());
        holder.type.setText(cart.getBook().getType());
        holder.creation.setText(cart.getBook().getCreation());
        holder.amount.setText(String.valueOf(cart.getAmount()));


    }

    @Override
    public int getItemCount() {
        if(mList!=null)
            return mList.size();
        return 0;
    }

    public class HomeCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title,author,creation,type,amount;
        private ImageView img;
        public HomeCardViewHolder(@NonNull View item)   {
            super(item);
            title = item.findViewById(R.id.book_title);
            author = item.findViewById(R.id.book_author);
            creation = item.findViewById(R.id.book_creation);
            type = item.findViewById(R.id.book_type);
            img = item.findViewById(R.id.book_image);
            amount = item.findViewById(R.id.book_amount);
            item.setOnClickListener(this);

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
    public interface OnItemContextMenuClickListener {
        void onItemContextMenuClick(int position, MenuItem item);
    }

}
