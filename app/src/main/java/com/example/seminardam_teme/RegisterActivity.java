package com.example.seminardam_teme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seminardam_teme.model.User;
import com.example.seminardam_teme.editTextValidators.EditTextValidatorMultiple;
import com.example.seminardam_teme.editTextValidators.EditTextValidatorSingle;
import com.example.seminardam_teme.editTextValidators.QuickRegex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText ettName;
    private EditText ettPhoneOrEmail;
    private EditText ettPass;
    private EditText ettPassCfm;

    private CheckBox cbShowPass;
    private TextView tvCdErr;
    private CalendarView cdDob;
    private Spinner spCountry;

    private Date currentDob;

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
        ettPhoneOrEmail = findViewById(R.id.etNewAccountPhoneEmail);
        ettPass = findViewById(R.id.etNewAccountPassword);
        ettPassCfm = findViewById(R.id.etNewAccountPasswordCfm);

        Bundle bdl = getIntent().getBundleExtra("register_info");
        if (bdl != null) {
            ettName.setText(bdl.getString("name"));
            ettPhoneOrEmail.setText(bdl.getString("phone_or_email"));
            ettPass.setText(bdl.getString("pwd"));
        }

        cbShowPass = findViewById(R.id.cbShowNewAccPasswd);
        tvCdErr = findViewById(R.id.tvCdErr);
        cdDob = findViewById(R.id.cdDateOfBirth);
        spCountry = findViewById(R.id.countrySpinner);

        btnRegister = findViewById(R.id.btnRegisterNewAcc);

        cdDob.setDate(cdDob.getDate() - (long)(18 / MILLIS_YEAR));
        currentDob = new Date(cdDob.getDate());

        Locale[] locales = Locale.getAvailableLocales();
        ArrayAdapter<Locale> adapter = new ArrayAdapter<Locale>(this, android.R.layout.simple_spinner_item,locales) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                TextView tv = ((TextView) v);
                tv.setText(locales[position].getDisplayCountry());
                return v;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                TextView tv = ((TextView) v);
                tv.setText(locales[position].getDisplayCountry());
                return v;
            }

            @Nullable
            @Override
            public Locale getItem(int position) {
                return locales[position];
            }

            @Override
            public int getCount() {
                return locales.length;
            }
        };
        spCountry.setAdapter(adapter);

        Map<Pattern, String> theMap = new LinkedHashMap<>();
        theMap.put(Patterns.EMAIL_ADDRESS, null);
        theMap.put(Patterns.PHONE, null);

        EditTextValidatorMultiple email_v = new EditTextValidatorMultiple (
                ettPhoneOrEmail,
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
                currentDob = new GregorianCalendar(year,month,dayOfMonth,0,0,0).getTime();
                tvCdErr.setText(
                        checkAge(currentDob.getTime())?
                                null : "Age must be at least 18 years old!"
                );
        });

        passwd_v.enable();

        btnRegister.setOnClickListener((v) -> {
            if (checkAge(currentDob.getTime())){
                if (spCountry.getSelectedItem() == null){
                    Toast.makeText(this,"Please select your country!",Toast.LENGTH_LONG).show();
                    spCountry.requestFocus();
                } else {
                    if (name_v.isValid() &&
                            email_v.isValid() &&
                            passwd_v.isValid() &&
                            ettPass.getText().toString().equals(ettPassCfm.getText().toString())) {
                        User registered_user = new User();
                        registered_user.setName(ettName.getText().toString());
                        String phoneOrEmail = ettPhoneOrEmail.getText().toString();
                        if (Patterns.PHONE.matcher(phoneOrEmail).matches())
                            registered_user.setPhone(ettPhoneOrEmail.getText().toString());
                        else if (Patterns.EMAIL_ADDRESS.matcher(phoneOrEmail).matches())
                            registered_user.setEmail(ettPhoneOrEmail.getText().toString());
                        try {
                            byte[] pwd_digest = MessageDigest.getInstance("SHA-256").digest(ettPass.getText().toString().getBytes(StandardCharsets.UTF_8));
                            String pwd_hash = Base64.encodeToString(pwd_digest, Base64.NO_WRAP | Base64.NO_PADDING);
                            registered_user.setPwdHash(pwd_hash);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        registered_user.setDateOfBirth(currentDob);
                        registered_user.setCountryOrRegion(((Locale)spCountry.getSelectedItem()).toLanguageTag());
                        Intent raspuns = new Intent(RegisterActivity.this, DashboardActivity.class);
                        Bundle wrapper = new Bundle();
                        wrapper.putSerializable("utilizator", registered_user);
                        raspuns.putExtra("raspuns", wrapper);
                        setResult(RESULT_OK, raspuns);
                        finish();
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