package com.example.btl_mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

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

import java.util.List;

public class Fragment_search_user extends Fragment implements View.OnClickListener,RecycleViewAdapterAdmin.ItemListener {
    private RecyclerView recyclerView;
    private DB db;
    private RecycleViewAdapterAdmin adapterAdmin;
    private Spinner spinner;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_user,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        spinner = view.findViewById(R.id.spinner_type);
        searchView = view.findViewById(R.id.search);
        db = new DB(getContext());
        adapterAdmin = new RecycleViewAdapterAdmin();
        List<Book> list_book = db.getAllBook();
        adapterAdmin.setmList(list_book);
        adapterAdmin.setItemListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterAdmin);
        String[] arr = getResources().getStringArray(R.array.type);
        String[] arr1 = new String[arr.length+1];
        arr1[0] = "All";
        for(int i=0;i<arr.length;i++){
            arr1[i+1] = arr[i];
        }
        spinner.setAdapter(new ArrayAdapter<String>(view.getContext(),R.layout.item_spinner,arr1));


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String typer = (String) spinner.getItemAtPosition(position);
                if(typer.equals("All")){
                    List<Book> list1 = db.getAllBook();
                    adapterAdmin.setmList(list1);
                }else {
                    List<Book> list1 = db.getBookByType(typer);
                    adapterAdmin.setmList(list1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Book> listbook = db.getListBookByTitle(s);
                adapterAdmin.setmList(listbook);
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Book> list = db.getAllBook();
        adapterAdmin.setmList(list);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), ContentBookActivity.class);
        intent.putExtra("book",adapterAdmin.getItem(position));
        intent.putExtra("check",1);
        startActivity(intent);
    }
}
