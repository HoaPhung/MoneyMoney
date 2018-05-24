package com.example.be.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.be.mm.AddIncomeActivity;
import com.example.be.model.Category;
import com.example.be.model.Expense;
import com.example.be.model.InCome;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MMDatabase  extends SQLiteOpenHelper{
    /*private static final String TAG = "MMDatabase";
    private static final String DATABASE_NAME = "MoneyMoney.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_CATEGORY_INCOME = "CategoryIncome";
    private static final String TABLE_CATEGORY_EXPENSE = "CategoryExpense";

    // Post Table Columns
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";*/

    final  String DATABASE_NAME ="MoneyMoney.db";
    SQLiteDatabase database;

    public MMDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MMDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /*public List<Category> getCategoryInCome(){
        List<Category> res= new ArrayList<>();
        String CATEGORY_SELECT_QUERY=String.format("SELECT * FROM %s", TABLE_CATEGORY_INCOME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(CATEGORY_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Category category = new Category();
                    res.add(category);
                    category.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    category.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return res;
    }

    public  Category getCategoryById(int id){
        String CATEGORY_SELECT_QUERY = String.format("SELECT * FROM %s WHERE id = %s", TABLE_CATEGORY_INCOME, id);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(CATEGORY_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                Category category = new Category();
                category.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                category.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                return category;
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return null;
    }*/

    public static SQLiteDatabase initDatabase(Activity activity, String databaseName){
        try {
            String outFileName = activity.getApplicationInfo().dataDir + "/databases/" + databaseName;
            File f = new File(outFileName);
            if(!f.exists()) {
                InputStream e = activity.getAssets().open(databaseName);
                File folder = new File(activity.getApplicationInfo().dataDir + "/databases/");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                FileOutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];

                int length;
                while ((length = e.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                e.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return activity.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
    }

    public  ArrayList<Category> readCategoryExpense(Activity activity){
        ArrayList<Category> rs= new ArrayList<>();
        database = MMDatabase.initDatabase(activity, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM CategoryExpense",null);
        rs.clear();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            rs.add(new Category(id, name));
        }
        return rs;
    }

    public ArrayList<Category> readCategoryIncome(Activity activity){
        ArrayList<Category> rs= new ArrayList<>();
        database = MMDatabase.initDatabase(activity, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM CategoryIncome",null);
        rs.clear();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            rs.add(new Category(id, name));
        }
        return rs;
    }

    public ArrayList<InCome> readTransactionInCome (Activity activity){
        ArrayList<InCome> rs= new ArrayList<>();
        database = MMDatabase.initDatabase(activity, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM TransactionInCome",null);
        rs.clear();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id=cursor.getInt(0);
            int price=cursor.getInt(1);
            String time=cursor.getString(2);
            String image=cursor.getString(3);
            String category=cursor.getString(4);
            String note=cursor.getString(5);

            rs.add(new InCome(id, price,  time,  note, image,  category));
        }
        return rs;
    }

    public ArrayList<InCome> readTransactionInComeDate (Activity activity, String date){
        ArrayList<InCome> rs= new ArrayList<>();
        database = MMDatabase.initDatabase(activity, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM TransactionInCome WHERE time = '"+date+"'",null);
        rs.clear();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id=cursor.getInt(0);
            int price=cursor.getInt(1);
            String time=cursor.getString(2);
            String image=cursor.getString(3);
            String category=cursor.getString(4);
            String note=cursor.getString(5);

            rs.add(new InCome(id, price,  time,  note, image,  category));
        }
        return rs;
    }

    public ArrayList<Expense> readTransactionExpense (Activity activity){
        ArrayList<Expense> rs= new ArrayList<>();
        database = MMDatabase.initDatabase(activity, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM TransactionExpense",null);
        rs.clear();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id=cursor.getInt(0);
            int price=cursor.getInt(1);
            String time=cursor.getString(2);
            String image=cursor.getString(3);
            String category=cursor.getString(4);
            String note=cursor.getString(5);

            rs.add(new Expense( id, price,  time,  note, image,  category));
        }
        return rs;
    }

    public ArrayList<Expense> readTransactionExpenseDate (Activity activity, String date){
        ArrayList<Expense> rs= new ArrayList<>();
        database = MMDatabase.initDatabase(activity, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM TransactionExpense WHERE time = '"+date+"'",null);
        rs.clear();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id=cursor.getInt(0);
            int price=cursor.getInt(1);
            String time=cursor.getString(2);
            String image=cursor.getString(3);
            String category=cursor.getString(4);
            String note=cursor.getString(5);

            rs.add(new Expense( id, price,  time,  note, image,  category));
        }
        return rs;
    }

    public void addCategory(Category category, int type){
        database=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("name", category.getName());

        if (type==0){
            database.insert("CategoryIncome",null, values);
        }
        else if (type==1){
            database.insert("CategoryExpense", null,values);
        }
    }

    public boolean updateCategory(Category category, int type){
        database=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("name", category.getName());
        boolean ret = false;
        if (type==0){
            ret = database.update("CategoryIncome", values,"id="+category.getId(),null)>0;
        }
        else if (type==1){
            ret = database.update("CategoryExpense", values,"id="+category.getId(),null)>0;
        }
        return ret;
    }

    public boolean deleteCategory(Category category, int type){
        database=this.getWritableDatabase();

        boolean ret = false;
        if (type==0){
            ret = database.delete("CategoryIncome", "id="+category.getId(),null)>0;
        }
        else if (type==1){
            ret = database.delete("CategoryExpense", "id="+category.getId(),null)>0;
        }
        return ret;
    }

    public void addIncome(InCome income){
        database=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("price", income.getPrice());
        values.put("time", income.getTime());
        values.put("image", income.getPathImage());
        values.put("category", income.getCategory());
        values.put("note", income.getNote());

        database.insert("TransactionInCome", null, values);
    }

    public void addExpense(Expense expense){
        database=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("price", expense.getPrice());
        values.put("time", expense.getTime());
        values.put("image", expense.getPathImage());
        values.put("category", expense.getCategory());
        values.put("note", expense.getNote());

        database.insert("TransactionExpense", null, values);
    }

    public boolean updateIncome(InCome income){
        database=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("price", income.getPrice());
        values.put("time", income.getTime());
        values.put("image", income.getPathImage());
        values.put("category", income.getCategory());
        values.put("note", income.getNote());

        return database.update("TransactionInCome", values,"id="+income.getId(),null)>0;
    }

    public boolean updateExpense(Expense expense){
        database=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("price", expense.getPrice());
        values.put("time", expense.getTime());
        values.put("image", expense.getPathImage());
        values.put("category", expense.getCategory());
        values.put("note", expense.getNote());

        return database.update("TransactionExpense", values,"id="+expense.getId(),null)>0;
    }

    public boolean deleteIncome(int id){
        database= this.getWritableDatabase();
        return database.delete("TransactionInCome","id="+id,null)>0;
    }

    public boolean deleteExpense(int id){
        database= this.getWritableDatabase();
        return database.delete("TransactionExpense","id="+id,null)>0;
    }

    public boolean deleteExpense(String categoryName){
        database= this.getWritableDatabase();
        return database.delete("TransactionExpense","category='"+categoryName+"'",null)>0;
    }

    public boolean deleteIncome(String categoryName){
        database= this.getWritableDatabase();
        return database.delete("TransactionInCome","category='"+categoryName+"'",null)>0;
    }

    public boolean resetExpense(){
        database = this.getWritableDatabase();
        return database.delete("TransactionExpense","1",null) > 0;
    }

    public boolean resetIncome(){
        database = this.getWritableDatabase();
        return database.delete("TransactionInCome","1",null) > 0;
    }
}
