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
import com.example.btl_mobile.adapter.RecycleViewAdapterUser;
import com.example.btl_mobile.database.DB;
import com.example.btl_mobile.model.Book;
import com.example.btl_mobile.model.User;

import java.util.List;

public class Fragment_home_user extends Fragment implements RecycleViewAdapterUser.ItemListener {
    private RecyclerView recyclerView;
    private DB db;
    private RecycleViewAdapterUser adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_user,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new RecycleViewAdapterUser();
        recyclerView = view.findViewById(R.id.recyclerView);
        db = new DB(getContext());
        List<Book> list_book = db.getAllBook();
        adapter.setmList(list_book);
        User user = (User) getActivity().getIntent().getSerializableExtra("user");
        adapter.setUserid(user.getId());
        adapter.setItemListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        List<Book> list_book = db.getAllBook();
        adapter.setmList(list_book);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), ContentBookActivity.class);
        intent.putExtra("book",adapter.getItem(position));
        intent.putExtra("check",1);
        startActivity(intent);
    }
}
