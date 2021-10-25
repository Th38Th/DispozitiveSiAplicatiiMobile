package com.example.seminardam_teme;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DashboardActivity extends CustomActionBarActivity {

    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvName;
    private TextView tvAgeAndCountry;

    private User usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvEmail = findViewById(R.id.tViewEmail);
        tvPhone = findViewById(R.id.tViewPhone);
        tvName = findViewById(R.id.tViewName);
        tvAgeAndCountry = findViewById(R.id.tViewAgeAndCountry);

        Bundle bdl = getIntent().getBundleExtra("user_info");
        if (bdl != null) {
            usr = (User) bdl.getSerializable("utilizator");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Resources res = getResources();
        if (usr != null) {
            String u_eml = usr.getEmail(), u_phn = usr.getPhone();
            if (u_eml != null) {
                tvEmail.setVisibility(View.VISIBLE);
                tvEmail.setText(res.getString(R.string.str_fmt_email, u_eml));
            } else
                tvEmail.setVisibility(View.GONE);
            if (u_phn != null) {
                tvPhone.setVisibility(View.VISIBLE);
                tvPhone.setText(res.getString(R.string.str_fmt_phone, u_phn));
            } else
                tvPhone.setVisibility(View.GONE);
            tvName.setText(usr.getName());
            tvAgeAndCountry.setText(res.getString(R.string.str_age_and_locale_display, usr.getAge(),
                    usr.getCountryOrRegion().getDisplayCountry()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (usr != null)
            usr.logOut();
    }
}