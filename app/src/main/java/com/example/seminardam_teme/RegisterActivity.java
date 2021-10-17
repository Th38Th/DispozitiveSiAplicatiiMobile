package com.example.seminardam_teme;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seminardam_teme.editTextValidators.EditTextValidatorMultiple;
import com.example.seminardam_teme.editTextValidators.EditTextValidatorSingle;
import com.example.seminardam_teme.editTextValidators.QuickRegex;

import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText ettName;
    private EditText ettEmail;
    private EditText ettPass;
    private EditText ettPassCfm;

    private CheckBox cbShowPass;
    private TextView tvCdErr;
    private CalendarView cdDob;
    private Spinner spCountry;

    private long currentDob;

    private final double MILLIS_YEAR = 1 / 31556926000.0;

    private AppCompatButton btnRegister;

    private boolean checkAge(long date){
        long today = new Date().getTime();
        long age = (long)(Math.floor(today - date) * MILLIS_YEAR);
        return (age >= 18);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actBar = getSupportActionBar();
        if (actBar != null)
            actBar.hide();

        ettName = findViewById(R.id.etNewAccountName);
        ettEmail = findViewById(R.id.etNewAccountPhoneEmail);
        ettPass = findViewById(R.id.etNewAccountPassword);
        ettPassCfm = findViewById(R.id.etNewAccountPasswordCfm);

        Bundle bdl = getIntent().getExtras();
        if (bdl != null){
            ettName.setText(bdl.getString("name"));
            ettEmail.setText(bdl.getString("email"));
            ettPass.setText(bdl.getString("pwd"));
        }

        cbShowPass = findViewById(R.id.cbShowNewAccPasswd);
        tvCdErr = findViewById(R.id.tvCdErr);
        cdDob = findViewById(R.id.cdDateOfBirth);
        spCountry = findViewById(R.id.countrySpinner);

        btnRegister = findViewById(R.id.btnRegisterNewAcc);

        cdDob.setDate(cdDob.getDate() - (long)(18 / MILLIS_YEAR));
        currentDob = cdDob.getDate();

        Locale[] locales = Locale.getAvailableLocales();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, locales) {
            public Object getItem(int position)
            {
                return locales[position].getDisplayCountry();
            }

            public int getCount()
            {
                return locales.length;
            }
        };
        spCountry.setAdapter(adapter);

        Map<Pattern, String> theMap = new LinkedHashMap<>();
        theMap.put(QuickRegex.email, null);
        theMap.put(QuickRegex.phone, null);

        EditTextValidatorMultiple email_v = new EditTextValidatorMultiple (
                ettEmail,
                "Please input a valid email address or phone number.",
                theMap, EditTextValidatorMultiple.RegexChainingMode.XOR
        );
        email_v.enable();

        EditTextValidatorSingle name_v = new EditTextValidatorSingle(
                ettName,
                "Minimum 3 characters required!",
                QuickRegex.atLeast(3)
        );
        name_v.enable();

        EditTextValidatorSingle passwd_v = new EditTextValidatorSingle(
                ettPass,
                "Minimum 6 characters required!",
                QuickRegex.atLeast(6)
        );

        cbShowPass.setOnCheckedChangeListener((b,c) -> {
            int s = ettPass.getSelectionStart(),
                    e = ettPass.getSelectionEnd();
            ettPass.setTransformationMethod(c?
                    HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
            ettPass.setSelection(s,e);
        });

        ettPassCfm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean k = s.toString().equals(ettPass.getText().toString());
                ettPassCfm.setError(k? null : "Passwords do not match!");
            }
        });

        ettPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean k = s.toString().equals(ettPassCfm.getText().toString());
                ettPassCfm.setError(k? null : "Passwords do not match!");
            }
        });

        cdDob.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
                currentDob = new GregorianCalendar(year,month,dayOfMonth,0,0,0).getTime().getTime();
                tvCdErr.setText(
                        checkAge(currentDob)?
                                null : "Age must be at least 18 years old!"
                );
        });

        passwd_v.enable();

        btnRegister.setOnClickListener((v) -> {
            if (checkAge(currentDob)){
                if (spCountry.getSelectedItem() == null){
                    Toast.makeText(this,"Please select your country!",Toast.LENGTH_LONG).show();
                    spCountry.requestFocus();
                } else {
                    if (name_v.isValid() &&
                            email_v.isValid() &&
                            passwd_v.isValid() &&
                            ettPass.getText().toString().equals(ettPassCfm.getText().toString())) {
                        Intent dashbd = new Intent(RegisterActivity.this, DashboardActivity.class);
                        dashbd.putExtra("name", ettName.getText().toString());
                        dashbd.putExtra("email", ettEmail.getText().toString());
                        startActivity(dashbd);
                    }
                    else {
                        Toast.makeText(this,"Please check your personal info!",Toast.LENGTH_LONG).show();
                        ettName.requestFocus();
                    }
                }
            } else {
                Toast.makeText(this,"You must be at least 18 years old to create an account!",Toast.LENGTH_LONG).show();
                cdDob.requestFocus();
            }
        });
    }
}