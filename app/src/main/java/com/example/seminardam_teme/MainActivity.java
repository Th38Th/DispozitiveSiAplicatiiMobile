package com.example.seminardam_teme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import com.example.seminardam_teme.editTextValidators.*;

public class MainActivity extends AppCompatActivity {

    public static final double MILLIS_YEAR = 1 / 31556926000.0;

    private List<User> listaUtilizatori;

    // Cele doua request code-uri
    private final int REGISTER_REQUEST_CODE = 0x73FB;
    private final int LOGIN_REQUEST_CODE = 0x023AA;

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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("utilizatori", listaUtilizatori.toArray(new User[0]));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        listaUtilizatori = Arrays.asList((User[])savedInstanceState.getSerializable("utilizatori"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (listaUtilizatori == null)
            listaUtilizatori = new ArrayList<>();

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
        theMap.put(Patterns.EMAIL_ADDRESS, null);
        theMap.put(Patterns.PHONE, null);

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
            if (name_evs.isValid()
                    && emailVerifRegister.isValid()
                    && passwd_evs.isValid()) {
                Intent registerAcc = new Intent(MainActivity.this, RegisterActivity.class);
                Bundle initial_info = new Bundle();
                initial_info.putString("name", tbRegisterName.getText().toString());
                initial_info.putString("phone_or_email", tbRegisterEmailPhone.getText().toString());
                initial_info.putString("pwd", tbRegisterPass.getText().toString());
                registerAcc.putExtra("register_info", initial_info);
                startActivityForResult(registerAcc, REGISTER_REQUEST_CODE);
            }
        });

        btnSignInContinue.setOnClickListener((v) -> {
            if (emailVerifSignIn.isValid()) {
                Intent loginAct = new Intent(MainActivity.this, LoginActivity.class);
                loginAct.putExtra("phone_or_email", tbSignInEmailPhone.getText().toString());
                startActivityForResult(loginAct, LOGIN_REQUEST_CODE);
            }
        });

        rbSignIn.setChecked(true);
        cbHelp.setChecked(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REGISTER_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Bundle raspuns = data.getBundleExtra("raspuns");
                        User user = (User) raspuns.getSerializable("utilizator");
                        listaUtilizatori.add(user);
                        tbRegisterName.getText().clear();
                        tbRegisterEmailPhone.getText().clear();
                        tbRegisterPass.getText().clear();
                        rbSignIn.setChecked(true);
                        String ph_eml = null;
                        if (user.getEmail() != null)
                            ph_eml = user.getEmail();
                        else if (user.getPhone() != null)
                            ph_eml = user.getPhone();
                        tbSignInEmailPhone.setText(ph_eml);
                        Toast.makeText(MainActivity.this, "Registration Successful!", Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.this, "Registered " + user.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case LOGIN_REQUEST_CODE:
                if (resultCode == RESULT_OK){
                    if (data != null) {
                        User login_usr = null;
                        String user_id = data.getStringExtra("phone_or_email");
                        byte[] pwd_hash = data.getByteArrayExtra("hash_parola");
                        for (User u : listaUtilizatori) {
                            if (u.logIn(user_id, user_id, pwd_hash)) {
                               login_usr = u;
                               break;
                            }
                        }
                        if (login_usr != null) {
                            Intent dashBd = new Intent(MainActivity.this, DashboardActivity.class);
                            Bundle login_info = new Bundle();
                            login_info.putSerializable("utilizator", login_usr);
                            dashBd.putExtra("user_info",login_info);
                            startActivity(dashBd);
                            Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                            Toast.makeText(MainActivity.this, "Logged in "+login_usr.toString(), Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(MainActivity.this, "ERROR: Invalid credentials!", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                break;
        }
    }
}