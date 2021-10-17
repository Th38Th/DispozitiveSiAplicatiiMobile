package com.example.seminardam_teme;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DashboardActivity extends CustomActionBarActivity {

    private TextView tvEmail;
    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvEmail = findViewById(R.id.tViewEmail);
        tvName = findViewById(R.id.tViewName);

        Bundle bdl = getIntent().getExtras();
        if (bdl != null) {
            tvEmail.setText(bdl.getString("email"));
            String name = bdl.getString("name");
            if (name == null)
                tvName.setVisibility(View.GONE);
            else
                tvName.setText(name);
        }

    }
}