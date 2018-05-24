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
import com.example.be.model.InCome;

import java.util.List;

public class CustomAdapterIncome  extends ArrayAdapter<InCome> {
    Context context;
    int resource;
    List<InCome> objects;

    public CustomAdapterIncome(@NonNull Context context, int resource, @NonNull List<InCome> objects) {
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
        InCome temp = getItem(position);
        TextView txtIncome = (TextView) v.findViewById(R.id.txtCategory);
        TextView txtMoney = (TextView) v.findViewById(R.id.txtMoney);
        TextView txtPercent = (TextView) v.findViewById(R.id.txtPercent);

        txtIncome.setText(temp.getCategory());
        txtMoney.setText(Integer.toString(temp.getPrice()));
        txtPercent.setText(Float.toString(temp.getPercent()));
        return v;
    }
}
