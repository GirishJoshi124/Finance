package com.example.joshigir.finance;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.joshigir.finance.DB.DBOperations;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IncomeActivity extends AppCompatActivity {

    EditText amountEditText;
    Spinner accountSpinner;
    Button submitButton;
    DBOperations dbOperations;
    ContentValues cv = new ContentValues();
    static final String INCOME_ACCOUNT = "Income Account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);


        amountEditText = (EditText) findViewById(R.id.editText2);
        submitButton = (Button) findViewById(R.id.buttonsubmit);
        submitButton.setBackgroundResource(R.mipmap.submit);

        dbOperations = new DBOperations(IncomeActivity.this);
        accountSpinner = (Spinner)findViewById(R.id.spinner2);



        final Cursor accountCursor = dbOperations.getTableRecords(DBOperations.TABLE_INCOME);
        final Cursor tablecursorr = dbOperations.getTableRecords(DBOperations.TABLE_TRANSACTIONS);





        final String[] from = {DBOperations.COL_ACC_NAME};
        int[] to = {android.R.id.text1};
        final SimpleCursorAdapter accountAdapter = new SimpleCursorAdapter(IncomeActivity.this,
                android.R.layout.simple_spinner_dropdown_item, accountCursor, from, to, Adapter.NO_SELECTION);

        accountSpinner.setAdapter(accountAdapter);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amountString = amountEditText.getText().toString();
                if (amountString == null || amountString.isEmpty()) {
                    Toast.makeText(IncomeActivity.this, "Amount cannot be empty", Toast.LENGTH_LONG).show();
                } else {
                    Double amount = Double.parseDouble(amountString);
                    View v = accountSpinner.getSelectedView();
                    TextView tv = (TextView) v.findViewById(android.R.id.text1);
                    String accountName = tv.getText().toString();

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String strDate = sdf.format(c.getTime());
                    cv.put(DBOperations.COL_FINAL, amount);
                    cv.put(DBOperations.COL_DATE,strDate);



                   if((tablecursorr!=null)&&(tablecursorr.getCount()>0)) {
                       tablecursorr.moveToLast();
                        String finalval = tablecursorr.getString(tablecursorr.getColumnIndexOrThrow(DBOperations.COL_FINAL));
                            Double finalamount = Double.parseDouble(finalval);

                            finalamount = finalamount + amount;
                            cv.put(DBOperations.COL_FINAL, finalamount);

                    }


                    cv.put(DBOperations.COL_ACC_NAME, accountName);
                    cv.put(DBOperations.COL_CAT_NAME,"-");
                    cv.put(DBOperations.COL_CREDIT, "-");
                    cv.put(DBOperations.COL_DEBIT,amount);


                    boolean wasInsertSuccessful = dbOperations.insertRecord(DBOperations.TABLE_TRANSACTIONS, cv);
                    if (wasInsertSuccessful) {
                        Toast.makeText(IncomeActivity.this, "Transaction captured successfully", Toast.LENGTH_LONG).show();
                        Intent gotointent= new Intent(IncomeActivity.this,HomeActivity.class);
                        startActivity(gotointent);

                    }
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_income, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){

            case R.id.AddIncomee: DisplayDialog(INCOME_ACCOUNT);
        }



        return super.onOptionsItemSelected(item);
    }


    public void DisplayDialog(String activity){
        AlertDialog.Builder dialog = new AlertDialog.Builder(IncomeActivity.this);

        dialog.setIcon(android.R.drawable.ic_input_add);
        dialog.setTitle("Add " + activity);

        final EditText et = new EditText(IncomeActivity.this);
        et.setHint("Enter name here");

        dialog.setView(et);

        dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                ContentValues cv =new ContentValues();
                boolean wasInsertSuccessful=false;

                String name = et.getText().toString();
                if (name == null || name.isEmpty()) {
                    Toast.makeText(IncomeActivity.this, "Name cannot be empty", Toast.LENGTH_LONG).show();

            }
                else{

                    cv.put(DBOperations.COL_ACC_NAME, name);
                     wasInsertSuccessful = dbOperations.insertRecord(DBOperations.TABLE_INCOME, cv);}

                if (wasInsertSuccessful) {
                    Toast.makeText(IncomeActivity.this, "Successfully persisted data",
                            Toast.LENGTH_LONG).show();
                }
            }


        });

    }
}
