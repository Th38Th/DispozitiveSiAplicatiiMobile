package com.example.seminardam_teme;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class LoginStep2 extends AppCompatActivity {

    private EditText ettEmail;
    private EditText ettPasswd;
    private AppCompatButton signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_step2);

        ActionBar actBar = getSupportActionBar();
        if (actBar != null)
            actBar.hide();

        ettEmail = findViewById(R.id.etLoginPhoneEmail);
        ettPasswd = findViewById(R.id.etLoginPassword);

        Bundle bdl = getIntent().getExtras();
        if (bdl != null){
            ettEmail.setText(bdl.getString("email"));
        }

        signIn = findViewById(R.id.btnSignIn);

        signIn.setOnClickListener((v) -> {
            String eml = ettEmail.getText().toString(), pwd = ettPasswd.getText().toString();
            if (eml.equals("")) {
                Toast.makeText(LoginStep2.this, "Please input your email address!", Toast.LENGTH_LONG).show();
                return;
            }
            if (pwd.equals("")) {
                Toast.makeText(LoginStep2.this, "Please input your password!", Toast.LENGTH_LONG).show();
                return;
            }
            Intent dashBd = new Intent(LoginStep2.this, DashboardActivity.class);
            dashBd.putExtra("email", ettEmail.getText().toString());
            startActivity(dashBd);
        });

    }
}