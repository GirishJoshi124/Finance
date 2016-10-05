package com.example.joshigir.finance;

import android.app.Application;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joshigir.finance.DB.DBOperations;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;



public class ExpenseActivity extends AppCompatActivity {

    Spinner spinnerIncome, spinnerExpense;
    EditText amount;
    Button submit;
    DBOperations dbOperations;
    ContentValues con = new ContentValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);


        spinnerExpense=(Spinner)findViewById(R.id.spinner);
        submit =(Button)findViewById(R.id.buttonSubmit);
        submit.setBackgroundResource(R.mipmap.submit);
        amount=(EditText)findViewById(R.id.editText3);
        dbOperations= new DBOperations(ExpenseActivity.this);


        final Cursor tablecursor = dbOperations.getTableRecords(DBOperations.TABLE_TRANSACTIONS);


        Cursor expenseCursor = dbOperations.getTableRecords(DBOperations.TABLE_EXPENSE);
        final String[] fromm = {DBOperations.COL_CAT_NAME};
        int[] too = {android.R.id.text1};
        final SimpleCursorAdapter accountAdapter2 = new SimpleCursorAdapter(ExpenseActivity.this,
                android.R.layout.simple_spinner_dropdown_item, expenseCursor, fromm, too, Adapter.NO_SELECTION);

        spinnerExpense.setAdapter(accountAdapter2);

            submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountstringg = amount.getText().toString();
                if (amountstringg.isEmpty() || amountstringg == null) {
                    Toast.makeText(ExpenseActivity.this, "amount cannot be empty", Toast.LENGTH_LONG).show();
                } else {

                    if(tablecursor.getCount()<=0){

                        Toast.makeText(ExpenseActivity.this,"Kindly begin with entering income :)",Toast.LENGTH_LONG).show();
                    }
                    Double amountt = Double.parseDouble(amountstringg);



                    View y = spinnerExpense.getSelectedView();
                    TextView expensetv = (TextView) y.findViewById(android.R.id.text1);
                    String expense = expensetv.getText().toString();




                    if((tablecursor!=null)&&(tablecursor.getCount()>0)) {
                        tablecursor.moveToLast();
                       String finalval = tablecursor.getString(tablecursor.getColumnIndexOrThrow(DBOperations.COL_FINAL));

                        Double finalamount = Double.parseDouble(finalval);


                        finalamount = finalamount - amountt;

                        if((finalamount==null)||(finalamount<0))
                        {
                            Toast.makeText(ExpenseActivity.this,"No sufficient balance!!",Toast.LENGTH_LONG).show();
                        }


                        else {

                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            String strDate = sdf.format(c.getTime());

                            con.put(DBOperations.COL_DATE,strDate);

                            con.put(DBOperations.COL_FINAL, finalamount);
                            con.put(DBOperations.COL_CAT_NAME, expense);
                            con.put(DBOperations.COL_CREDIT, amountt);
                            con.put(DBOperations.COL_DEBIT,"-");
                            boolean wasInsertSuccessful = dbOperations.insertRecord(DBOperations.TABLE_TRANSACTIONS, con);
                            if (wasInsertSuccessful) {
                                Toast.makeText(ExpenseActivity.this, "Transaction captured successfully", Toast.LENGTH_LONG).show();
                                Intent gotointent= new Intent(ExpenseActivity.this,HomeActivity.class);
                                startActivity(gotointent);
                        }



                    }




                    }
                }
            }
        });

    }


}
