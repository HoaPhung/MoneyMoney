package com.example.be.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.be.database.MMDatabase;
import com.example.be.mm.AddExpenseCategory;
import com.example.be.mm.AddIncomeCategory;
import com.example.be.mm.R;
import com.example.be.model.Category;
import com.example.be.model.Expense;

import java.util.List;

import static com.example.be.mm.R.id.btnEdit;

public class CategoryAdapter extends ArrayAdapter<Category> {
    Context context;
    int resource;
    List<Category> objects;
    Dialog dialogEditCategory;
    Dialog dialogDelete;
    String s;
    MMDatabase db=new MMDatabase(getContext(),"MoneyMoney.db",null,1);
    int mode = -1;
    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
        if(this.getContext() instanceof AddExpenseCategory){
            mode = 1;
        }
        else if(this.getContext() instanceof AddIncomeCategory){
            mode = 0;
        }
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final RecyclerView.ViewHolder viewHolder;
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.category_expense_adapter, null);

        }
        final Category temp = getItem(position);
        TextView txtCategory = (TextView) v.findViewById(R.id.txtCategory);
        ImageButton btnEdit= v.findViewById(R.id.btnEdit);
        txtCategory.setText(temp.getName());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater lif = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                dialogEditCategory =new Dialog(context);
                view = lif.inflate(R.layout.dialog_edit_category, null);
                ImageView imgTrash= view.findViewById(R.id.imgTrash);
                Button btnClose= view.findViewById(R.id.btnClose);
                Button btnSave= view.findViewById(R.id.btnSave);
                final EditText edtCategory= view.findViewById(R.id.edtCategory);
                edtCategory.setText(temp.getName());

                dialogEditCategory.setContentView(view);
                dialogEditCategory.setTitle("Edit expense category!");
                dialogEditCategory.show();

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogEditCategory.dismiss();
                    }
                });

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        temp.setName(edtCategory.getText().toString());
                        boolean ret = db.updateCategory(temp,mode);
                        dialogEditCategory.dismiss();
                        notifyDataSetChanged();
                    }
                });


                imgTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater lif = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        dialogDelete= new Dialog(context);
                        view=lif.inflate(R.layout.dialog_delete_category, null);
                        Button btnClose, btnDelete;
                        btnClose=view.findViewById(R.id.btnClose);
                        btnDelete=view.findViewById(R.id.btnDelete);

                        btnClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogDelete.dismiss();
                            }
                        });
                        btnDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                for(int i =objects.size()-1;i>=0;i--){
                                    if(objects.get(i).getId() == temp.getId()){
                                        if(mode == 1)
                                            db.deleteExpense(temp.getName());
                                        else if(mode == 0)
                                            db.deleteIncome(temp.getName());
                                        objects.remove(i);
                                    }
                                }
                                db.deleteCategory(temp,mode);
                                dialogDelete.dismiss();
                                dialogEditCategory.dismiss();
                                notifyDataSetChanged();
                            }
                        });
                        dialogDelete.setContentView(view);
                        dialogDelete.setTitle("Delete expense category!");
                        dialogDelete.show();
                    }
                });
            }
        });
        return v;
    }
}
