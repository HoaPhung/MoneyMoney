package com.example.be.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.be.mm.R;
import com.example.be.model.Expense;
import com.example.be.model.InCome;

import java.util.List;

/**
 * Created by Vi Van on 5/20/2018.
 */

public class CustomAdapterDetailIncome extends ArrayAdapter<InCome> {
    Context context;
    int resource;
    List<InCome> objects;
    public CustomAdapterDetailIncome(@NonNull Context context, int resource, @NonNull List<InCome> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    public void setObjects(List<InCome> objects) {
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_adapter_detail, null);
        }
        InCome temp = getItem(position);
        TextView txtIncome = (TextView) v.findViewById(R.id.txtCategory);
        TextView txtPrice = (TextView) v.findViewById(R.id.txtPrice);
        TextView txtNote = (TextView) v.findViewById(R.id.txtNote);

        txtIncome.setText(temp.getCategory());
        txtPrice.setText(String.valueOf(temp.getPrice())+"vnd");
        if(temp.getNote() != null)
            txtNote.setText(temp.getNote());
        else
            txtNote.setText("");

        return v;
    }
}
