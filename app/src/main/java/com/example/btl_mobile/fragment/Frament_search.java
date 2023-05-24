package com.example.btl_mobile.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;

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

import java.util.Calendar;
import java.util.List;

public class Frament_search extends Fragment implements View.OnClickListener, RecycleViewAdapterAdmin.ItemListener {
    private Spinner spinner;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private EditText eTo,eFrom;
    private Button btSearch;
    private DB db;
    private RecycleViewAdapterAdmin adapter = new RecycleViewAdapterAdmin();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchbook,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = view.findViewById(R.id.spinner_type);
        recyclerView = view.findViewById(R.id.recyclerView);
        eTo = view.findViewById(R.id.eTo);
        eFrom = view.findViewById(R.id.eFrom);
        btSearch = view.findViewById(R.id.btSearch);
        searchView = view.findViewById(R.id.search);
        db = new DB(getContext());
        String[] arr = getResources().getStringArray(R.array.type);
        String[] arr1 = new String[arr.length+1];
        arr1[0] = "All";
        for(int i=0;i<arr.length;i++){
            arr1[i+1] = arr[i];
        }
        spinner.setAdapter(new ArrayAdapter<String>(view.getContext(),R.layout.item_spinner,arr1));
        List<Book> list = db.getAllBook();
        adapter.setmList(list);
        adapter.setItemListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        btSearch.setOnClickListener(this);
        eTo.setOnClickListener(this);
        eFrom.setOnClickListener(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String typer = (String) spinner.getItemAtPosition(position);
                if(typer.equals("All")){
                    List<Book> list1 = db.getAllBook();
                    adapter.setmList(list1);
                }else {
                    List<Book> list1 = db.getBookByType(typer);
                    adapter.setmList(list1);
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
                adapter.setmList(listbook);
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        switch (view.getId()){
            case R.id.btSearch:
                String from = eFrom.getText().toString();
                String to = eTo.getText().toString();
                if(!from.isEmpty() && !to.isEmpty()){
                    List<Book> itemList = db.getListBookByDateFromTo(from,to);
                    adapter.setmList(itemList);
                }
                break;
            case R.id.eFrom:
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String date1 = "";
                        if(m>8){
                            date1 = d+"/"+(m+1)+"/"+y;
                        }else {
                            date1 = d+"/0"+(m+1)+"/"+y;
                        }
                        eFrom.setText(date1);
                    }
                },year,month,day);
                dialog.show();
                break;
            case R.id.eTo:
                DatePickerDialog dialog1 = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String date1 = "";
                        if(m>8){
                            date1 = d+"/"+(m+1)+"/"+y;
                        }else {
                            date1 = d+"/0"+(m+1)+"/"+y;
                        }
                        eTo.setText(date1);
                    }
                },year,month,day);
                dialog1.show();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Book book = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteBookActivity.class);
        intent.putExtra("book", book);
        startActivity(intent);
    }
}
