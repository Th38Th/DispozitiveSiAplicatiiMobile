package com.example.seminardam_teme;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private EditText ettPhoneOrEmail;
    private EditText ettPasswd;
    private AppCompatButton signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actBar = getSupportActionBar();
        if (actBar != null)
            actBar.hide();

        ettPhoneOrEmail = findViewById(R.id.etLoginPhoneEmail);
        ettPasswd = findViewById(R.id.etLoginPassword);

        Bundle bdl = getIntent().getExtras();
        if (bdl != null) {
            ettPhoneOrEmail.setText(bdl.getString("phone_or_email"));
        }

        signIn = findViewById(R.id.btnSignIn);

        signIn.setOnClickListener((v) -> {
            String eml = ettPhoneOrEmail.getText().toString(), pwd = ettPasswd.getText().toString();
            if (eml.equals("")) {
                Toast.makeText(LoginActivity.this, R.string.str_phone_email_prompt, Toast.LENGTH_LONG).show();
                return;
            }
            if (pwd.equals("")) {
                Toast.makeText(LoginActivity.this, R.string.str_pwd_prompt, Toast.LENGTH_LONG).show();
                return;
            }
            Intent raspuns = new Intent(LoginActivity.this, DashboardActivity.class);
            raspuns.putExtra("phone_or_email", eml);
            try {
                raspuns.putExtra("hash_parola", MessageDigest.getInstance("SHA-256").digest(pwd.getBytes(StandardCharsets.UTF_8)));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            setResult(RESULT_OK, raspuns);
            finish();
        });

    }
}