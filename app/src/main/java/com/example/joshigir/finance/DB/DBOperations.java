package com.example.joshigir.finance.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by joshigir on 1/30/2016.
 */
public class DBOperations {

    DBHelper dbHelper;
    SQLiteDatabase db;
    public static final String COL_ID = "_id";
    public static final String COL_ACC_NAME = "account_name";
    public static final String COL_CAT_NAME = "category_name";
    public static final String COL_DEBIT ="debit";
    public static final String COL_CREDIT="credit";
    public static final String COL_FINAL="amount";
    public static final String COL_DATE="date";




    public static final String TABLE_INCOME = "income_accounts";
    public static final String TABLE_EXPENSE = "expense_categories";
    public static final String TABLE_TRANSACTIONS = "transactions";

    public DBOperations(Context context) {
        String dbName = "Db";
        int dbVersion = 4;

        dbHelper = new DBHelper(context, dbName, null, dbVersion);
    }

    public boolean insertRecord(String tableName, ContentValues cv) {
        db = dbHelper.getWritableDatabase();

        long id = db.insert(tableName, null, cv);

        db.close();
        if (id > -1) {
            return true;
        }
        else
        return false;
    }

    public Cursor getTableRecords(String tableName) {
        db = dbHelper.getReadableDatabase();

        return db.query(tableName, null, null, null, null, null, null);


    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE income_accounts (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "account_name VARCHAR2(100))");
            db.execSQL("INSERT INTO income_accounts (account_name) VALUES ('Salary')");
            db.execSQL("INSERT INTO income_accounts (account_name) VALUES ('Business')");
            db.execSQL("INSERT INTO income_accounts (account_name) VALUES ('Part-Time')");
            db.execSQL("INSERT INTO income_accounts (account_name) VALUES ('Others')");

            db.execSQL("CREATE TABLE expense_categories (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "category_name VARCHAR2(100))");
            db.execSQL("INSERT INTO expense_categories (category_name) VALUES ('Rent')");
            db.execSQL("INSERT INTO expense_categories (category_name) VALUES ('Fuel')");
            db.execSQL("INSERT INTO expense_categories (category_name) VALUES ('Food/Groceries')");
            db.execSQL("INSERT INTO expense_categories (category_name) VALUES ('Others')");

            db.execSQL("CREATE TABLE transactions (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "  account_name VARCHAR2(50), category_name VARCHAR2(50), debit REAL , credit REAL , amount REAL, date TEXT)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {





        }

    }
}
