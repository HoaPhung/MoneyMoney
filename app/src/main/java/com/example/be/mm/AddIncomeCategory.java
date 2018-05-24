package com.example.be.mm;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.be.adapter.CategoryAdapter;

import com.example.be.database.MMDatabase;
import com.example.be.model.Category;

import java.util.ArrayList;

public class AddIncomeCategory extends AppCompatActivity {
    ImageButton btnBack, btnAdd;
    ListView lvIncomeCategory;
    MMDatabase db=new MMDatabase(AddIncomeCategory.this,"MoneyMoney.db",null,1);
    ArrayList<Category> listCategory= new ArrayList<>();
    CategoryAdapter adapter;
    private Dialog dialodAddCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income_category);

        addControls();
        addEvents();
    }

    private void addControls() {
        btnAdd=findViewById(R.id.btnAdd);
        btnBack=findViewById(R.id.btnBack);
        lvIncomeCategory=findViewById(R.id.lvIncomeCategory);
        listCategory=db.readCategoryIncome(AddIncomeCategory.this);
        adapter= new CategoryAdapter(this, R.layout.category_expense_adapter,listCategory);
        lvIncomeCategory.setAdapter(adapter);
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AddIncomeCategory.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.dialog_add_income_category, null);

                dialodAddCategory =new Dialog(AddIncomeCategory.this);
                dialodAddCategory.setContentView(alertLayout);
                dialodAddCategory.setTitle("Add new category!");
                dialodAddCategory.show();

                final Button btnClose=alertLayout.findViewById(R.id.btnClose);
                final Button btnSave=alertLayout.findViewById(R.id.btnSave);
                final EditText edtCategory= alertLayout.findViewById(R.id.edtAddIncCategory);

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialodAddCategory.dismiss();
                    }
                });

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //nhớ bắt lỗi nếu chuỗi rông

                        String str= edtCategory.getText().toString();
                        if (!str.equals("")){
                            dialodAddCategory.dismiss();
                            Category temp= new Category(str);
                            db.addCategory(temp,0);
                            temp.setId(listCategory.get(listCategory.size()-1).getId()+1);
                            listCategory.add(temp);
                            adapter.notifyDataSetChanged();
                        }

                    }
                });

            }
        });
    }
}
