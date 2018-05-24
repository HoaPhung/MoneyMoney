package com.example.be.mm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.be.adapter.CustomAdapterDetailExpense;
import com.example.be.database.MMDatabase;
import com.example.be.model.Category;
import com.example.be.model.Expense;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ExpenseDetailActivity extends AppCompatActivity {
    ListView lvExpense;
    TextView txtSeeAll, txtSeeOneDay;
    ArrayList<Expense>  listExpenses;
    MMDatabase db=new MMDatabase(ExpenseDetailActivity.this,"MoneyMoney.db",null,1);
    CustomAdapterDetailExpense customAdapterDetail;
    Calendar cal;
    SimpleDateFormat dft=null;
    int pos;
    PopupMenu popup;
    private Dialog dialogEdit;
    View alertLayout;
    TextView txtCategory;
    EditText edtNote;
    TextView txtChooseCategory;
    ListView lvCategory=null;
    ArrayList<Category> listCategory=new ArrayList<>();
    private Dialog dialogAddCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);
        addControl();
        addEvent();
    }

    private void addControl(){
        txtSeeAll = (TextView) findViewById(R.id.chooseSeeAll);
        txtSeeAll.setBackgroundColor(getResources().getColor(R.color.choose));
        txtSeeOneDay = (TextView) findViewById(R.id.chooseSeeDay);

        lvExpense = (ListView)findViewById(R.id.lvExpense);
        listExpenses = db.readTransactionExpense(ExpenseDetailActivity.this);
        customAdapterDetail = new CustomAdapterDetailExpense(this,R.layout.custom_adapter_detail,listExpenses);
        lvExpense.setAdapter(customAdapterDetail);


        cal=Calendar.getInstance();
        // format dd//mm/yyyy
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        popup = new PopupMenu(ExpenseDetailActivity.this, lvExpense);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.popup_edit_delete_transaction, popup.getMenu());

        LayoutInflater inflater = getLayoutInflater();
        alertLayout = inflater.inflate(R.layout.dialog_edit_delete_transaction, null);

        txtCategory = (TextView) alertLayout.findViewById(R.id.txtCategory);
        edtNote = (EditText) alertLayout.findViewById(R.id.edtNote);
        txtChooseCategory = (TextView) alertLayout.findViewById(R.id.txtChooseCategory);
        dialogEdit =new Dialog(ExpenseDetailActivity.this);
        dialogEdit.setContentView(alertLayout);
        dialogEdit.setTitle("Edit expense!");

        listCategory = db.readCategoryExpense(ExpenseDetailActivity.this);
        lvCategory=new ListView(this);
        List<String> temp=new ArrayList<>();
        for (int i=0; i<listCategory.size(); i++){
            temp.add(listCategory.get(i).getName());
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,temp);
        lvCategory.setAdapter(arrayAdapter);
    }

    private void addEvent(){
        txtSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSeeAll.setBackgroundColor(getResources().getColor(R.color.choose));
                txtSeeOneDay.setBackgroundColor(Color.argb(0, 0, 0, 0));
                txtSeeOneDay.setText("Xem theo ngày");

                listExpenses = db.readTransactionExpense(ExpenseDetailActivity.this);
                customAdapterDetail = new CustomAdapterDetailExpense(ExpenseDetailActivity.this,R.layout.custom_adapter_detail,listExpenses);
                lvExpense.setAdapter(customAdapterDetail);
            }
        });

        txtSeeOneDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSeeOneDay.setBackgroundColor(getResources().getColor(R.color.choose));
                txtSeeAll.setBackgroundColor(Color.argb(0, 0, 0, 0));

                handleDateDialog();
            }
        });

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.menuEdit){
                    dialogEdit.show();
                }
                else if(item.getItemId() == R.id.menuDelete){
                    db.deleteExpense(listExpenses.get(pos).getId());
                    listExpenses.remove(pos);
                    customAdapterDetail.notifyDataSetChanged();
                }
                return false;
            }
        });

        lvExpense.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                popup.show(); //showing popup menu
                return false;
                }
            });

        final Button btnClose=alertLayout.findViewById(R.id.btnClose);
        final Button btnSave=alertLayout.findViewById(R.id.btnSave);

        txtChooseCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvCategory.getParent()!=null){
                    ((ViewGroup)lvCategory.getParent()).removeView(lvCategory);
                }
                dialogAddCategory =new Dialog(ExpenseDetailActivity.this);
                dialogAddCategory.setContentView(lvCategory);
                dialogAddCategory.setTitle("Add category");
                dialogAddCategory.show();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEdit.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtCategory.getText().toString() == ""){
                    txtCategory.setText("Bạn phải chọn Category");
                }
                else {
                    listExpenses.get(pos).setCategory(txtCategory.getText().toString());
                    listExpenses.get(pos).setNote(edtNote.getText().toString());
                    db.updateExpense(listExpenses.get(pos));
                    customAdapterDetail.notifyDataSetChanged();
                    dialogEdit.dismiss();
                }
            }
        });

        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String temp=lvCategory.getItemAtPosition(i).toString();
                txtCategory.setText(temp);
                dialogAddCategory.dismiss();
            }
        });
    }

    private void handleDateDialog() {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                cal.set(Calendar.YEAR,i);
                cal.set(Calendar.MONTH,i1);
                cal.set(Calendar.DAY_OF_MONTH,i2);

                if(i1 < 10 && i2 < 10){
                    dft = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());
                }
                txtSeeOneDay.setText(dft.format(cal.getTime()));

                String date = txtSeeOneDay.getText().toString();
                listExpenses = db.readTransactionExpenseDate(ExpenseDetailActivity.this,date);
                customAdapterDetail = new CustomAdapterDetailExpense(ExpenseDetailActivity.this,R.layout.custom_adapter_detail,listExpenses);
                customAdapterDetail.setObjects(listExpenses);

                lvExpense.setAdapter(customAdapterDetail);
            }
        };
        DatePickerDialog dialog=new DatePickerDialog(ExpenseDetailActivity.this, callback, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
}
