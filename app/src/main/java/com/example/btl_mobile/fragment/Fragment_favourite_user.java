package com.example.btl_mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_mobile.ContentBookActivity;
import com.example.btl_mobile.R;
import com.example.btl_mobile.adapter.RecycleViewAdapterAdmin;
import com.example.btl_mobile.database.DB;
import com.example.btl_mobile.model.Book;
import com.example.btl_mobile.model.Favourite;
import com.example.btl_mobile.model.User;

import java.util.ArrayList;
import java.util.List;

public class Fragment_favourite_user extends Fragment implements RecycleViewAdapterAdmin.ItemListener {
    private RecyclerView recyclerView;
    private DB db;
    private RecycleViewAdapterAdmin adapterAdmin;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favouritebook_user,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        db = new DB(getContext());
        adapterAdmin = new RecycleViewAdapterAdmin();
        user = (User)getActivity().getIntent().getSerializableExtra("user");
        List<Book> list_book = new ArrayList<>();
        List<Favourite> favourites = db.getFavouriteByUserId(user.getId());
        for(Favourite favourite:favourites){
            Book book = db.getBookByKey(favourite.getBookid());
            list_book.add(book);
        }
        adapterAdmin.setmList(list_book);
        adapterAdmin.setItemListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterAdmin);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Book> list_book = new ArrayList<>();
        User user = (User)getActivity().getIntent().getSerializableExtra("user");
        List<Favourite> favourites = db.getFavouriteByUserId(user.getId());
        for(Favourite favourite:favourites){
            Book book = db.getBookByKey(favourite.getBookid());
            list_book.add(book);
        }
        adapterAdmin.setmList(list_book);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), ContentBookActivity.class);
        intent.putExtra("book",adapterAdmin.getItem(position));
        intent.putExtra("user",user);
        intent.putExtra("check",0);
        startActivity(intent);
    }
}
