package com.example.joshigir.finance;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.joshigir.finance.DB.DBOperations;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionActivity extends AppCompatActivity {

    DBOperations dbOperations;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);


        dbOperations=new DBOperations(TransactionActivity.this);

        lv=(ListView)findViewById(R.id.listView);


        Cursor tablecursor = dbOperations.getTableRecords(DBOperations.TABLE_TRANSACTIONS);

        Myadapter sca = new Myadapter(TransactionActivity.this,tablecursor,true);
        lv.setAdapter(sca);



        }

    public class Myadapter extends CursorAdapter{

        public Myadapter(Context context,Cursor tablecursor, boolean autoRequery){
            super(context,tablecursor,autoRequery);}


        @Override
        public View newView(Context context, Cursor tablecursor, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.listitem, viewGroup, false);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            TextView categoryview = (TextView)view.findViewById(R.id.textViewcat);
            TextView valueview=(TextView)view.findViewById(R.id.textViewval);
            TextView finalvalview=(TextView)view.findViewById(R.id.textViewfinal);
            TextView dateview =(TextView)view.findViewById(R.id.textViewDate);



            String accname = cursor.getString(cursor.getColumnIndexOrThrow(DBOperations.COL_ACC_NAME));
            String incomeval=cursor.getString(cursor.getColumnIndex(DBOperations.COL_DEBIT));
            String expense = cursor.getString(cursor.getColumnIndex(DBOperations.COL_CAT_NAME));
            String expenseval=cursor.getString(cursor.getColumnIndex(DBOperations.COL_CREDIT));
            String finalval= cursor.getString(cursor.getColumnIndexOrThrow(DBOperations.COL_FINAL));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DBOperations.COL_DATE));







            if(expenseval.equals("-")||expense.equals("-")){

                categoryview.setText("INCOME= "+accname);
                valueview.setText("₹ "+incomeval);
                categoryview.setBackgroundColor(Color.rgb(252, 136, 4));
                valueview.setBackgroundColor(Color.rgb(252, 136, 4));
            }

            else{

                categoryview.setText("EXPENSE= "+expense);
                valueview.setText("₹ "+expenseval);
                valueview.setBackgroundColor(Color.rgb(242, 91, 91));
                categoryview.setBackgroundColor(Color.rgb(242, 91, 91));
            }

            finalvalview.setText("NET AMOUNT= ₹ "+finalval);
            finalvalview.setBackgroundColor(Color.MAGENTA);
            dateview.setText(date);
            dateview.setBackgroundColor(Color.MAGENTA);
        }

    }



    }








