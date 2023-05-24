package com.example.btl_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.btl_mobile.R;
import com.example.btl_mobile.model.Bill;

import java.util.List;
import java.util.StringTokenizer;

public class BillAdapter extends ArrayAdapter<Bill> {
    private List<Bill> mList;
    private Context context;
    public BillAdapter(@NonNull Context context, List<Bill> mList) {
        super(context, R.layout.item_bill,mList);
        this.context =context;
        this.mList = mList;
    }

    public void setmList(List<Bill> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill,null,false);
        TextView creation = view.findViewById(R.id.bill_creation);
        TextView total = view.findViewById(R.id.bill_total);
        TextView username = view.findViewById(R.id.bill_username);
        if(mList.size()!=0){
            Bill bill = mList.get(position);
            creation.setText("Ngày tạo: "+ bill.getCreation());
            total.setText("Tổng tiền: "+bill.getTotal());
            if(bill.getCarts()!=null && bill.getCarts().size()!=0){
                StringTokenizer tokenizer = new StringTokenizer(bill.getCarts().get(0).getUser().getUsername(),"@");
                while (tokenizer.hasMoreTokens()){
                    username.setText("Người tạo: "+ tokenizer.nextToken());
                    break;
                }

            }else {
                username.setText("Người tạo: "+"Khong co");
            }
        }
        return view;
    }
    public Bill getItem(int position){
        return mList.get(position);
    }
}
