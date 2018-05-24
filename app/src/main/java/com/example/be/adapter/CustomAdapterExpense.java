package com.example.be.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.be.mm.R;
import com.example.be.model.Expense;
import com.example.be.model.InCome;

import java.util.List;

public class CustomAdapterExpense  extends ArrayAdapter<Expense> {
    Context context;
    int resource;
    List<Expense> objects;
    public CustomAdapterExpense(@NonNull Context context, int resource, @NonNull List<Expense> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_adapter, null);
        }
        Expense temp = getItem(position);
        TextView txtExpense = (TextView) v.findViewById(R.id.txtCategory);
        TextView txtMoney = (TextView) v.findViewById(R.id.txtMoney);
        TextView txtPercent = (TextView) v.findViewById(R.id.txtPercent);

        String s=temp.getCategory();
        String s1=Integer.toString(temp.getPrice());
        txtExpense.setText(temp.getCategory());
        txtMoney.setText(Integer.toString(temp.getPrice()));
        txtPercent.setText(Float.toString(temp.getPrecent()));
        return v;
    }
}
