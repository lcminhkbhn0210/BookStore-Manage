package com.example.btl_mobile.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_mobile.R;
import com.example.btl_mobile.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapterAdmin extends RecyclerView.Adapter<RecycleViewAdapterAdmin.HomeViewHolder> {
    private List<Book> mList;
    private ItemListener itemListener;
    public void setmList(List<Book> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }
    public Book getItem(int position){
        return mList.get(position);
    }
    public RecycleViewAdapterAdmin(){
        this.mList = new ArrayList<>();
    }
    public void setItemListener(ItemListener itemListener){
        this.itemListener = itemListener;
    }
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
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

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title,author,creation,type;
        private ImageView img;
        public HomeViewHolder(@NonNull View item) {
            super(item);
            title = item.findViewById(R.id.book_title);
            author = item.findViewById(R.id.book_author);
            creation = item.findViewById(R.id.book_creation);
            type = item.findViewById(R.id.book_type);
            img = item.findViewById(R.id.book_image);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener!=null)
                itemListener.onItemClick(view,getAdapterPosition());
        }
    }
    public interface ItemListener{
        void onItemClick(View view,int position);
    }
}
