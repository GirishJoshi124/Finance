package com.example.joshigir.finance;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joshigir.finance.DB.DBOperations;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button incomeButton, expenseButton, transactionsButton;
    DBOperations dbOperations;

    static final String INCOME_ACCOUNT = "Income Account";
    static final String EXPENSE_CATEGORY = "Expense Category";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        incomeButton = (Button) findViewById(R.id.buttonIncome);
        expenseButton = (Button) findViewById(R.id.buttonExpense);
        transactionsButton = (Button) findViewById(R.id.buttonTransaction);
        dbOperations = new DBOperations(HomeActivity.this);

        incomeButton.setOnClickListener(this);
        expenseButton.setOnClickListener(this);
        transactionsButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent gotoIntent = null;

        switch (v.getId()) {
            case R.id.buttonIncome:
                // goto Income Screen
                gotoIntent = new Intent(HomeActivity.this, IncomeActivity.class);
                break;

            case R.id.buttonExpense:
                // goto Expense Screen
                gotoIntent = new Intent(HomeActivity.this, ExpenseActivity.class);
                break;

            case R.id.buttonTransaction:
                // goto Transactions Screen
                gotoIntent = new Intent(HomeActivity.this, TransactionActivity.class);
                break;

        }
        startActivity(gotoIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {





            switch (item.getItemId()) {
                case R.id.AddIncome:
                    displayDialog(INCOME_ACCOUNT);
                    break;

                case R.id.AddExpense:
                    displayDialog(EXPENSE_CATEGORY);
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

    public void displayDialog(final String activity){
        AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);

        dialog.setIcon(android.R.drawable.ic_input_add);
        dialog.setTitle("Add " + activity);

        final EditText et = new EditText(HomeActivity.this);
        et.setHint("Enter name here");

        dialog.setView(et);
        dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = et.getText().toString();
                if (name == null || name.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Name cannot be empty", Toast.LENGTH_LONG).show();
                } else {
                    // insert into respective DB Table
                    ContentValues cv = new ContentValues();
                    boolean wasInsertSuccessful = false;
                    if (activity.equals(INCOME_ACCOUNT)) {
                        cv.put(DBOperations.COL_ACC_NAME, name);
                        wasInsertSuccessful = dbOperations.insertRecord(DBOperations.TABLE_INCOME, cv);
                    } else {
                        cv.put(DBOperations.COL_CAT_NAME, name);
                        wasInsertSuccessful = dbOperations.insertRecord(DBOperations.TABLE_EXPENSE, cv);
                    }

                    if (wasInsertSuccessful) {
                        Toast.makeText(HomeActivity.this, "Successfully persisted data",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        dialog.show();
    }
    }
