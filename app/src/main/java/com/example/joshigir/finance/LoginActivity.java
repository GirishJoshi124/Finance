package com.example.joshigir.finance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button loginButton, registerButton;
    EditText usernameEditText, passwordeEditText,confirmPasswordEditText;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.buttonLogin);
        registerButton = (Button) findViewById(R.id.buttonRegister);
        usernameEditText = (EditText) findViewById(R.id.editTextUsername);
        passwordeEditText = (EditText) findViewById(R.id.editTextPassword);
        confirmPasswordEditText=(EditText)findViewById(R.id.editTextConfirmPassword);
        pref = getSharedPreferences("mypref",MODE_PRIVATE);
        editor = pref.edit();

        final String prefUsername = pref.getString("username", "");
        final String prefPassword = pref.getString("password", "");


        if("".equals(prefUsername)){
            loginButton.setVisibility(View.GONE);
            registerButton.setVisibility(View.VISIBLE);
        }
        else{
            registerButton.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
            confirmPasswordEditText.setVisibility(View.GONE);

        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordeEditText.getText().toString();


                if(username == null || username.trim().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Username is required",
                            Toast.LENGTH_LONG).show();
                    usernameEditText.setError("Username is required");
                } else if(password == null || password.trim().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Password is required",
                            Toast.LENGTH_LONG).show();
                    passwordeEditText.setError("Password is required");
                }


                else {
                    if(username.equals(prefUsername) && password.equals(prefPassword)){
                        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid credentials",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordeEditText.getText().toString();
                String confirmpassword=confirmPasswordEditText.getText().toString();

                if(username == null || username.trim().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Username is required",
                            Toast.LENGTH_LONG).show();
                    usernameEditText.setError("Username is required");
                } else if(password == null || password.trim().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Password is required",
                            Toast.LENGTH_LONG).show();
                    passwordeEditText.setError("Password is required");
                }

                else if(password.length()<9){

                    Toast.makeText(LoginActivity.this,"Password should be greater than 8 characters",
                            Toast.LENGTH_LONG).show();

                    passwordeEditText.setError("Password should be greater than 8 characters");
                    passwordeEditText.setText("");

                }

                else if(password.equals(confirmpassword)){

                    Toast.makeText(LoginActivity.this,"Successfully Registered",Toast.LENGTH_LONG).show();

                    editor.putString("username", username);
                    editor.putString("password", password);

                    editor.commit();
                    Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    finish();


                }

                else {

                    Toast.makeText(LoginActivity.this,"Password doesnt match.Try Again!",Toast.LENGTH_LONG).show();

                }
            }
        });

    }



}
