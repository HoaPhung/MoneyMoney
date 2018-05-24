package com.example.be.mm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.be.database.MMDatabase;
import com.example.be.model.Category;
import com.example.be.model.Expense;
import com.example.be.model.InCome;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddExpenseActivity extends AppCompatActivity {

    Button btnGallery, btnClose, btnSave, btnAddCategory, btnAddNote, btnCalendar;
    EditText edtPrice;
    TextView txtCategory, txtNote, txtCalendar;
    ImageView imageView;

    //String[] category={"Facebook","Google","Hoa","Van","HoaHoa"};
    ListView lvCategory=null;
    private Dialog dialogAddCategory;
    private Dialog dialogAddNote;
    Calendar cal;
    SimpleDateFormat dft=null;

    // database
    final  String DATABASE_NAME ="MoneyMoney.db";
    SQLiteDatabase database;
    ArrayList<Category> listCategory=new ArrayList<>();
    MMDatabase db=new MMDatabase(AddExpenseActivity.this,"MoneyMoney.db",null,1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        listCategory=db.readCategoryExpense(AddExpenseActivity.this);
        addControls();
        addEvents();
    }

    private void addControls() {
        btnGallery=findViewById(R.id.btnGallery);
        btnClose=findViewById(R.id.btnClose);
        btnSave=findViewById(R.id.btnSave);
        btnAddCategory=findViewById(R.id.btnAddCategory);
        btnAddNote=findViewById(R.id.btnAddNode);
        edtPrice=findViewById(R.id.editPrice);
        txtCategory=findViewById(R.id.txtCategory);
        txtNote=findViewById(R.id.txtNode);
        imageView=findViewById(R.id.imageView);
        lvCategory=new ListView(this);
        List<String> temp=new ArrayList<>();
        for (int i=0; i<listCategory.size(); i++){
            temp.add(listCategory.get(i).getName());
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,temp);
        lvCategory.setAdapter(arrayAdapter);
        btnCalendar=findViewById(R.id.btnCalendar);
        txtCalendar=findViewById(R.id.txtCalendar);
        // Calendar
        cal=Calendar.getInstance();
        // format dd//mm/yyyy
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate=dft.format(cal.getTime());
        txtCalendar.setText(strDate);

        //
        Intent intent=getIntent();
        String date=intent.getStringExtra("date");
        txtCalendar.setText(date);
    }

    private void addEvents() {
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lvCategory.getParent()!=null){
                    ((ViewGroup)lvCategory.getParent()).removeView(lvCategory);
                }
                dialogAddCategory =new Dialog(AddExpenseActivity.this);
                dialogAddCategory.setContentView(lvCategory);
                dialogAddCategory.setTitle("Add category");
                dialogAddCategory.show();
            }
        });


        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.dialog_add_note, null);

                dialogAddNote =new Dialog(AddExpenseActivity.this);
                dialogAddNote.setContentView(alertLayout);
                dialogAddNote.setTitle("Add Note");
                dialogAddNote.show();

                final Button btnSaveNote=alertLayout.findViewById(R.id.btnSaveNote);
                final Button btnCloseNote=alertLayout.findViewById(R.id.btnCloseNote);
                final EditText edtNodeDialog = alertLayout.findViewById(R.id.edtNoteDialog);

                btnCloseNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogAddNote.dismiss();
                    }
                });

                btnSaveNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String temp=edtNodeDialog.getText().toString();
                        txtNote.setText(temp);
                        dialogAddNote.dismiss();
                    }
                });


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

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateDialog();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMainActivity= new Intent(AddExpenseActivity.this, MainActivity.class);
                startActivity(intentMainActivity);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int price= Integer.parseInt(edtPrice.getText().toString());
                String time=txtCalendar.getText().toString();
                String image=" ";
                String category= txtCategory.getText().toString();
                String note=txtNote.getText().toString();
                Expense expense=new Expense( price,time,  note,image,  category);
                db.addExpense(expense);
                Intent intentMainActivity= new Intent(AddExpenseActivity.this, MainActivity.class);
                startActivity(intentMainActivity);
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
                txtCalendar.setText(dft.format(cal.getTime()));
            }
        };
        DatePickerDialog dialog=new DatePickerDialog(AddExpenseActivity.this, callback, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(AddExpenseActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(AddExpenseActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

}
