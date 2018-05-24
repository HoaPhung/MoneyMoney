package com.example.be.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.be.mm.R;
import com.example.be.model.Expense;
import com.example.be.model.InCome;
import com.example.be.model.Setting;

import java.util.List;

public class SettingAdapter extends ArrayAdapter<Setting> {
    Context context;
    int resource;
    List<Setting> objects;

    public SettingAdapter(@NonNull Context context, int resource, @NonNull List<Setting> objects) {
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
            v = vi.inflate(R.layout.setting_adapter, null);
        }
        Setting temp = getItem(position);
        ImageView imageView=v.findViewById(R.id.imageView);
        TextView txtTitle=v.findViewById(R.id.txtTitle);
        TextView txtDetail=v.findViewById(R.id.txtDetail);

        imageView.setImageResource(temp.getIcon());
        txtTitle.setText(temp.getTitle());
        txtDetail.setText(temp.getDetail());
        return v;
    }
}

