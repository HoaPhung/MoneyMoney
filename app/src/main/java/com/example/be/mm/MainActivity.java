package com.example.be.mm;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.be.adapter.CustomAdapterExpense;
import com.example.be.adapter.CustomAdapterIncome;
import com.example.be.database.MMDatabase;
import com.example.be.model.Category;
import com.example.be.model.Expense;
import com.example.be.model.InCome;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    // tab balance
    TabHost tabHost;
    TextView txtMonthYear, txtIncome, txtBalance, txtExpense;
    CalendarView calendarView;
    private Dialog dialogChooseTransaction;
    // tab income
    ListView lvIncomeDetail;
    ListView lvIncome;
    //TextView txtDate;
    //CustomAdapterIncome customAdapterDetail;
    TextView txtDateIncome;
    TextView txtNumberTransactionIncome;
    ArrayList<InCome> test=new ArrayList<>();

    //tab expense
    ListView lvExpenseDetail;
    //TextView txtDate;
    //CustomAdapterIncome customAdapterDetail;
    TextView txtDateExpense;
    TextView txtNumberTransactionExpence;
    ArrayList<Expense> test1=new ArrayList<>();

    ArrayList<Category> listIncomeCategory=new ArrayList<>();
    ArrayList<Category> listExpenseCategory= new ArrayList<>();
    HorizontalBarChart mChart;

    int totalIncome=0;
    int totalExpense=0;

    TextView txtTotalExpence, txtTotalIncome;

    //CustomAdapterIncome adapterIncome2;
    ArrayList<InCome> listIncome;
    //private Dialog dialogEditDelTrans;
    //int pos;
    //PopupMenu popup;
    FloatingActionButton btnSetting;

    // database
    final  String DATABASE_NAME ="MoneyMoney.db";
    SQLiteDatabase database;
    MMDatabase db=new MMDatabase(MainActivity.this,"MoneyMoney.db",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvent();
        setData(1);
    }
    public void setData(int count){ //count là số lượng row cần vễ. ở đây là 1
        //vẽ chart
        mChart = (HorizontalBarChart) findViewById(R.id.chart1);
        ArrayList<BarEntry> yValues = new ArrayList<>();
        float BarWidth=9f;
        float val1 = totalIncome; //Gía trị Income
        float val2 = totalExpense; //Gía trị Expense
        yValues.add(new BarEntry(0,new float[]{val1,val2}));
        BarDataSet set1 = new BarDataSet(yValues,"title");
        set1.setDrawIcons(false);
        set1.setStackLabels(new  String[]{"Income","Expense"});
        set1.setColors(ColorTemplate.JOYFUL_COLORS);
        BarData data = new BarData(set1);
        data.setBarWidth(BarWidth);
        mChart.setData(data);
        mChart.setFitBars(true);
        mChart.invalidate();
        mChart.getDescription().setEnabled(false);
    }
    // function init
    private void addControl() {
        listIncomeCategory=db.readCategoryIncome(MainActivity.this);
        listExpenseCategory=db.readCategoryExpense(MainActivity.this);
        // tab balance
        tabHost =findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec spec;
        spec=tabHost.newTabSpec("t1");
        spec.setContent(R.id.tab1);
        //spec.setIndicator("", getResources().getDrawable(R.drawable.music));
        spec.setIndicator("Income");
        tabHost.addTab(spec);
        spec=tabHost.newTabSpec("t2");
        spec.setContent(R.id.tab2);
        //spec.setIndicator("", getResources().getDrawable(R.drawable.lovemusic));
        spec.setIndicator("Balance");
        tabHost.addTab(spec);
        spec=tabHost.newTabSpec("t3");
        spec.setContent(R.id.tab3);
        //spec.setIndicator("", getResources().getDrawable(R.drawable.lovemusic));
        spec.setIndicator("Expence");
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);
        txtBalance=findViewById(R.id.txtBalance);
        txtExpense=findViewById(R.id.txtExpense);
        txtIncome=findViewById(R.id.txtIncome);
        txtMonthYear=findViewById(R.id.txtMonthYear);
        calendarView=findViewById(R.id.calendarView);
        //get current month and year
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        String currentTime=Integer.toString(month)+" "+ Integer.toString(year);
        txtMonthYear.setText(currentTime);

        listIncome=new ArrayList<>();
        listIncome=db.readTransactionInCome(MainActivity.this);
        lvIncomeDetail = findViewById(R.id.lvIncomeDetail);
        ArrayList<InCome> listIncomeLV= new ArrayList<>();
        listIncomeLV=handleIncomeListview(listIncome);
        final CustomAdapterIncome adapterIncome = new CustomAdapterIncome(this,R.layout.custom_adapter, listIncomeLV);
        lvIncomeDetail.setAdapter(adapterIncome);

        //lvIncome = findViewById(R.id.lvIncome);
        //adapterIncome2 = new CustomAdapterIncome(this,R.layout.custom_adapter, listIncome);
        //lvIncome.setAdapter(adapterIncome2);

        ArrayList<Expense> listExpense=new ArrayList<>();
        listExpense=db.readTransactionExpense(MainActivity.this);
        lvExpenseDetail=findViewById(R.id.lvExpenseDetail);
        ArrayList<Expense> listExpenseLV= new ArrayList<>();
        listExpenseLV=handleExpenseListView(listExpense);
        final CustomAdapterExpense adapterExpense = new CustomAdapterExpense(this,R.layout.custom_adapter, listExpenseLV);
        lvExpenseDetail.setAdapter(adapterExpense);

        txtDateIncome= findViewById(R.id.txtDateIncome);
        txtDateIncome.setText(currentTime);
        txtDateExpense=findViewById(R.id.txtDateExpence);
        txtDateExpense.setText(currentTime);

        //int
        txtNumberTransactionIncome= findViewById(R.id.txtNumberTransactionIncome);
        String numberTransactionIncome=Integer.toString(listIncome.size());
        txtNumberTransactionIncome.setText(numberTransactionIncome);
        txtNumberTransactionExpence=findViewById(R.id.txtNumberTransactionExpence);
        String numberTransactionExpense= Integer.toString(listExpense.size());
        txtNumberTransactionExpence.setText(numberTransactionExpense);

        txtIncome.setText(Integer.toString(totalIncome));
        txtExpense.setText(Integer.toString(totalExpense));
        int balance= Math.abs(totalIncome-totalExpense);
        txtBalance.setText(Integer.toString(balance));
        txtTotalExpence=findViewById(R.id.txtTotalExpnce);
        txtTotalExpence.setText(Integer.toString(totalExpense));
        txtTotalIncome=findViewById(R.id.txtTotalIncome);
        txtTotalIncome.setText(Integer.toString(totalIncome));

        btnSetting = (FloatingActionButton) findViewById(R.id.fab);
        //popup = new PopupMenu(MainActivity.this, lvIncome);
        //Inflating the Popup using xml file
        //popup.getMenuInflater().inflate(R.menu.popup_edit_delete_transaction, popup.getMenu());
    }

    // event
    private void addEvent() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
               // Toast.makeText(getApplicationContext(), "Selected Date:\n" + "Day = " + i2 + "\n" + "Month = " + i1 + "\n" + "Year = " + i, Toast.LENGTH_LONG).show();
                String date=i2+"/"+i1+"/"+i;

                final Intent intentIncomeActivity= new Intent(MainActivity.this, AddIncomeActivity.class);
                intentIncomeActivity.putExtra("date",date);

                final Intent intentExpenseActivity= new Intent(MainActivity.this, AddExpenseActivity.class);
                intentExpenseActivity.putExtra("date",date);


                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.dialog_choose_transaction, null);

                dialogChooseTransaction =new Dialog(MainActivity.this);
                dialogChooseTransaction.setContentView(alertLayout);
                dialogChooseTransaction.setTitle("Choose transaction!");
                dialogChooseTransaction.show();

                final Button btnInCome=alertLayout.findViewById(R.id.btnIncome);
                final Button btnExpense=alertLayout.findViewById(R.id.btnExpense);

                btnInCome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //dialogChooseTransaction.dismiss();
                        startActivity(intentIncomeActivity);
                    }
                });

                btnExpense.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(intentExpenseActivity);
                    }
                });
            }
        });
        /*
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.menuEdit){

                }
                else if(item.getItemId() == R.id.menuDelete){
                    db.deleteIncome(listIncome.get(pos).getId());
                    listIncome.remove(pos);
                    adapterIncome2.notifyDataSetChanged();
                }
                return false;
            }
        });

        lvIncome.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.dialog_edit_delete_transaction, null);
                pos = position;

                dialogEditDelTrans =new Dialog(MainActivity.this);
                dialogEditDelTrans.setContentView(alertLayout);
                dialogEditDelTrans.setTitle("Choose Action!");
                dialogEditDelTrans.show();

                final Button btnEdit=alertLayout.findViewById(R.id.btnEdit);
                final Button btnDelete=alertLayout.findViewById(R.id.btnDelete);

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteIncome(listIncome.get(pos).getId());
                        listIncome.remove(pos);
                        adapterIncome2.notifyDataSetChanged();
                        dialogEditDelTrans.dismiss();
                    }
                //});
                pos = position;
                popup.show(); //showing popup menu
                return false;
            }
        });*/

        Button btnSeeDetailIncome = (Button) findViewById(R.id.btnSeeDetailIncome);
        btnSeeDetailIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,IncomeDetailActivity.class);
                startActivity(intent);
            }
        });

        Button btnSeeDetailExpense = (Button) findViewById(R.id.btnSeeDetailExpense);
        btnSeeDetailExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ExpenseDetailActivity.class);
                startActivity(intent);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<InCome> handleIncomeListview(ArrayList<InCome> inComes){
        int totalPrice=0;
        for (int i=0; i<inComes.size(); i++){
            totalPrice+=inComes.get(i).getPrice();
        }
        totalIncome=totalPrice;
        ArrayList<InCome> rs=new ArrayList<>();
        for (int i=0; i<listIncomeCategory.size(); i++){
            int total=0;
            InCome temp=new InCome(listIncomeCategory.get(i).getName(),0);
            for (int k=0; k<inComes.size(); k++){
                String s1= temp.getCategory();
                String s2=inComes.get(k).getCategory();
                if (temp.getCategory().equals(inComes.get(k).getCategory())){
                    total+=inComes.get(k).getPrice();
                }
            }
            if (total!=0){
                float precent=(float) total/totalPrice*100;
                NumberFormat format= NumberFormat.getNumberInstance();
                format.setMinimumFractionDigits(2);
                format.setMaximumFractionDigits(2);
                //float x= Float.parseFloat(format.format(precent));
                temp.setPercent(precent);
                temp.setPrice(total);
                rs.add(temp);
            }
        }
        return  rs;
    }

    private ArrayList<Expense> handleExpenseListView(ArrayList<Expense> expenses){
        int totalPrice=0;
        for (int i=0; i<expenses.size(); i++){
            totalPrice+=expenses.get(i).getPrice();
        }
        totalExpense=totalPrice;
        ArrayList<Expense> rs=new ArrayList<>();
        for (int i=0; i<listExpenseCategory.size(); i++){
            int total=0;
            Expense temp=new Expense(listExpenseCategory.get(i).getName(),0);
            for (int k=0; k<expenses.size(); k++){
                String s1= temp.getCategory();
                String s2=expenses.get(k).getCategory();
                if (temp.getCategory().equals(expenses.get(k).getCategory())){
                    int tem= expenses.get(k).getPrice();
                    total+=expenses.get(k).getPrice();
                }
            }
            if (total!=0){
                float precent=(float) total/totalPrice*100;
                NumberFormat format= NumberFormat.getNumberInstance();
                format.setMinimumFractionDigits(2);
                format.setMaximumFractionDigits(2);
                //float x= Float.parseFloat(format.format(precent));
                temp.setPrecent(precent);
                temp.setPrice(total);
                rs.add(temp);
            }
        }
        return  rs;
    }

}
