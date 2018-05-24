package com.example.be.mm;

import android.content.Intent;
import android.location.SettingInjectorService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.be.adapter.SettingAdapter;
import com.example.be.model.Setting;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    ListView lvGeneralSetting, lvSecurity, lvAppreciateApp;
    ArrayList<Setting> generalSetting= new ArrayList<>();
    ArrayList<Setting> securitySetting= new ArrayList<>();
    ArrayList<Setting> appreciateApp= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        addControls();
        addEvents();
    }

    private void addControls() {
        lvGeneralSetting= findViewById(R.id.lvGeneralSetting);
        lvAppreciateApp=findViewById(R.id.lvAppreciateApp);
        lvSecurity=findViewById(R.id.lvSecurity);

        // general setting
        int iconIncome= R.drawable.income;
        String title1= "Income Categories";
        String detail1="Add or edit income category";
        Setting test1=new Setting(iconIncome, title1, detail1);
        generalSetting.add(test1);

        int iconExpense= R.drawable.expense;
        String title2= "Expense Categories";
        String detail2="Add or edit expense category";
        Setting test2=new Setting(iconExpense, title2, detail2);
        generalSetting.add(test2);

        int iconRemiders= R.drawable.reminder;
        String title3= "Reminders";
        String detail3="Set up or edit reminders";
        Setting test3=new Setting(iconRemiders, title3, detail3);
        generalSetting.add(test3);
        final SettingAdapter adapterGeneralSetting = new SettingAdapter(this,R.layout.setting_adapter, generalSetting);
        lvGeneralSetting.setAdapter(adapterGeneralSetting);

        // Security setting
        int iconPassword= R.drawable.password;
        String title4= "Password";
        String detail4="Set up or edit password";
        Setting test4=new Setting(iconPassword, title4, detail4);
        securitySetting.add(test4);

        int iconResetAll= R.drawable.reset;
        String title5= "Reset all data";
        String detail5="Delete all data and reset application";
        Setting test5=new Setting(iconResetAll, title5, detail5);
        securitySetting.add(test5);

        final SettingAdapter adapterSecuritySetting = new SettingAdapter(this,R.layout.setting_adapter, securitySetting);
        lvSecurity.setAdapter(adapterSecuritySetting);

        //rate app
        int iconRate= R.drawable.rate;
        String title6= "Rate app";
        String detail6="Give this app a nice review";
        Setting test6=new Setting(iconRate, title6, detail6);
        appreciateApp.add(test6);

        final SettingAdapter adapterRateApp = new SettingAdapter(this,R.layout.setting_adapter, appreciateApp);
        lvAppreciateApp.setAdapter(adapterRateApp);

    }

    private void addEvents() {

        lvGeneralSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    Intent intent=new Intent(SettingActivity.this, AddIncomeCategory.class);
                    startActivity(intent);
                }
                else if (i==1){
                    Intent intent=new Intent(SettingActivity.this, AddExpenseCategory.class);
                    startActivity(intent);
                }
                else if (i==2){
                    Intent intent= new Intent(SettingActivity.this, TestRemider.class);
                    startActivity(intent);
                }
            }
        });



    }
}
