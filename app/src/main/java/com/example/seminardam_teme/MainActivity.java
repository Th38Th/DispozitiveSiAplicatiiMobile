package com.example.seminardam_teme;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import com.example.seminardam_teme.editTextValidators.*;

public class MainActivity extends AppCompatActivity {

    // Formularul pentru inregistrare + butonul radio corespunzator
    private RadioButton rbRegister;
    private ConstraintLayout frmRegister;
    private EditText tbRegisterName;
    private EditText tbRegisterEmailPhone;
    private EditText tbRegisterPass;
    private AppCompatButton btnRegisterContinue;
    private CheckBox cbShowPass;

    // Formularul pentru logare + butonul radio corespunzator
    private RadioButton rbSignIn;
    private ConstraintLayout frmSignIn;
    private EditText tbSignInEmailPhone;
    private AppCompatButton btnSignInContinue;
    private CheckBox cbHelp;
    private LinearLayout extraOptList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actBar = getSupportActionBar();
        if (actBar != null)
            actBar.hide();

        TextView[] lst = new TextView[] {
                findViewById(R.id.tViewTnC),
                findViewById(R.id.tViewTnC2),
                findViewById(R.id.tvCoULnk),
                findViewById(R.id.tvPNLnk),
                findViewById(R.id.tvHLnk)
        };

        for (TextView t : lst) t.setMovementMethod(LinkMovementMethod.getInstance());

        rbRegister = findViewById(R.id.rbRegister);
        frmRegister = findViewById(R.id.frmCreateAcc);
        tbRegisterName = findViewById(R.id.etRegisterName);
        tbRegisterEmailPhone = findViewById(R.id.etRegisterPhoneEmail);
        tbRegisterPass = findViewById(R.id.etRegisterPassword);
        btnRegisterContinue = findViewById(R.id.btnContinueRegister);
        cbShowPass = findViewById(R.id.cbShowPassword);

        rbSignIn = findViewById(R.id.rbSignIn);
        frmSignIn = findViewById(R.id.frmSignIn);
        tbSignInEmailPhone = findViewById(R.id.etSignInEmailAddress);
        btnSignInContinue = findViewById(R.id.btnContinueSignIn);
        extraOptList = findViewById(R.id.lstHelps);
        cbHelp = findViewById(R.id.tvHelp);

        cbHelp.setOnCheckedChangeListener((b, c) -> {
            extraOptList.setVisibility(c? View.VISIBLE : View.GONE);
        });

        Map<Pattern, String> theMap = new LinkedHashMap<>();
        theMap.put(QuickRegex.email, null);
        theMap.put(QuickRegex.phone, null);

        EditTextValidatorMultiple emailVerifRegister = new EditTextValidatorMultiple (
                tbRegisterEmailPhone,
                "Please input a valid email address or phone number.",
                theMap, EditTextValidatorMultiple.RegexChainingMode.XOR
        );

        emailVerifRegister.enable();

        EditTextValidatorMultiple emailVerifSignIn = new EditTextValidatorMultiple (
                tbSignInEmailPhone,
                "Please input a valid email address or phone number.",
                theMap, EditTextValidatorMultiple.RegexChainingMode.XOR
        );

        emailVerifSignIn.enable();

        EditTextValidatorSingle name_evs = new EditTextValidatorSingle(
                tbRegisterName,
                "Minimum 3 characters required!",
                QuickRegex.atLeast(3)
        );

        name_evs.enable();

        EditTextValidatorSingle passwd_evs = new EditTextValidatorSingle(
                tbRegisterPass,
                "Minimum 6 characters required!",
                QuickRegex.atLeast(6)
        );

        passwd_evs.enable();

        rbRegister.setOnCheckedChangeListener((b,c) -> {
            rbSignIn.setChecked(!c);
            frmRegister.setVisibility(c? View.VISIBLE : View.GONE);
        });

        rbSignIn.setOnCheckedChangeListener((b,c) -> {
            rbRegister.setChecked(!c);
            frmSignIn.setVisibility(c? View.VISIBLE : View.GONE);
        });

        cbShowPass.setOnCheckedChangeListener((b,c) -> {
            int s = tbRegisterPass.getSelectionStart(),
                    e = tbRegisterPass.getSelectionEnd();
            tbRegisterPass.setTransformationMethod(c?
                    HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
            tbRegisterPass.setSelection(s,e);
        });

        btnRegisterContinue.setOnClickListener((v) -> {
            if (name_evs.isValid() && emailVerifRegister.isValid() && passwd_evs.isValid()) {
                Intent registerAcc = new Intent(MainActivity.this, RegisterActivity.class);
                registerAcc.putExtra("name", tbRegisterName.getText().toString());
                registerAcc.putExtra("email", tbRegisterEmailPhone.getText().toString());
                registerAcc.putExtra("pwd", tbRegisterPass.getText().toString());
                startActivity(registerAcc);
            }
        });

        btnSignInContinue.setOnClickListener((v) -> {
            if (emailVerifSignIn.isValid()) {
                Intent loginStep2 = new Intent(MainActivity.this, LoginStep2.class);
                loginStep2.putExtra("email", tbSignInEmailPhone.getText().toString());
                startActivity(loginStep2);
            }
        });

        rbSignIn.setChecked(true);
        cbHelp.setChecked(false);
    }
}