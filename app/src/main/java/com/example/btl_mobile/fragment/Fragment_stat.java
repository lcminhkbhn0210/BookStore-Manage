package com.example.btl_mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.btl_mobile.R;
import com.example.btl_mobile.WatchDeleteBillActivity;
import com.example.btl_mobile.adapter.BillAdapter;
import com.example.btl_mobile.database.DB;
import com.example.btl_mobile.model.Bill;

import java.util.List;
import java.util.StringTokenizer;

public class Fragment_stat extends Fragment {
    private TextView sum;
    private ListView listView;
    private BillAdapter adapter;
    private Spinner spinner;
    private DB db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.lvRevenueList);
        spinner = view.findViewById(R.id.spnMonth);
        sum = view.findViewById(R.id.tvTotalRevenue);
        db = new DB(getContext());
        List<Bill> list = db.getAllBill();
        adapter = new BillAdapter(getContext(),list);
        listView.setAdapter(adapter);
        double tong = 0;
        for(Bill bill:list){
            tong = tong + bill.getTotal();
        }
        sum.setText(String.valueOf(tong));
        String arr1[] = getResources().getStringArray(R.array.month);
        spinner.setAdapter(new ArrayAdapter<String>(view.getContext(),R.layout.item_spinner,arr1));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String typer = (String) spinner.getItemAtPosition(position);
                if(typer.equals("All")){
                    List<Bill> bills = db.getAllBill();
                    adapter = new BillAdapter(getContext(),bills);
                    listView.setAdapter(adapter);
                    double tong = 0;
                    for(Bill bill:bills){
                        tong = tong + bill.getTotal();
                    }
                    sum.setText(String.valueOf(tong));
                }
                else {
                    StringTokenizer tokenizer = new StringTokenizer(typer);
                    String month = "";
                    while (tokenizer.hasMoreTokens()){
                        month = tokenizer.nextToken();
                    }
                    List<Bill> bills = db.getBillByMonth(Integer.parseInt(month));
                    adapter = new BillAdapter(getContext(),bills);
                    listView.setAdapter(adapter);
                    double tong = 0;
                    for(Bill bill:bills){
                        tong = tong + bill.getTotal();
                    }
                    sum.setText(String.valueOf(tong));

                }
                adapter.notifyDataSetChanged();
                listView.invalidateViews();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WatchDeleteBillActivity.class);
                intent.putExtra("bill",adapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Bill> list = db.getAllBill();
        adapter = new BillAdapter(getContext(),list);
        listView.setAdapter(adapter);
        double tong = 0;
        for(Bill bill:list){
            tong = tong + bill.getTotal();
        }
        sum.setText(String.valueOf(tong));
    }
}
