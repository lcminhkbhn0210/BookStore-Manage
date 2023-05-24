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

import com.example.btl_mobile.R;
import com.example.btl_mobile.UpdateDeleteBookActivity;
import com.example.btl_mobile.adapter.RecycleViewAdapterAdmin;
import com.example.btl_mobile.database.DB;
import com.example.btl_mobile.model.Book;

import java.util.List;

public class Fragment_listbook extends Fragment implements RecycleViewAdapterAdmin.ItemListener {
    private RecyclerView recyclerView;
    private DB db;
    private RecycleViewAdapterAdmin adapterAdmin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listbook,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        db = new DB(getContext());
        adapterAdmin = new RecycleViewAdapterAdmin();
        List<Book> list_book = db.getAllBook();
        adapterAdmin.setmList(list_book);
        adapterAdmin.setItemListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterAdmin);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Book> list = db.getAllBook();
        adapterAdmin.setmList(list);
    }

    @Override
    public void onItemClick(View view, int position) {
        Book book = adapterAdmin.getItem(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteBookActivity.class);
        intent.putExtra("book", book);
        startActivity(intent);
    }
}
