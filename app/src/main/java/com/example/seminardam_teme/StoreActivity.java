package com.example.seminardam_teme;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends CustomActionBarActivity {

    private TextView tvStore;
    private ListView lvProduse;
    private MagazinAdapter storeAdapter;
    private List<Produs> produsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        tvStore = findViewById(R.id.tvStore);

        produsList = getProduse();
        tvStore.setText(getResources().getString(R.string.str_store_msg,((produsList.size()-1)/10)*10));

        storeAdapter = new MagazinAdapter(produsList);
        lvProduse = findViewById(R.id.lvProduse);
        lvProduse.setAdapter(storeAdapter);

    }

    private List<Produs> getProduse() {
        List<Produs> lst = new ArrayList<>();
        lst.add(new Produs("Prod1",5.64f,"Produsul 1"));
        lst.add(new Produs("Prod2",7.43f,"Produsul doi"));
        lst.add(new Produs("Prod3",2.32f,"Produsul III"));
        lst.add(new Produs("Prod4",9.12f,"Produsul 2x2"));
        lst.add(new Produs("Prod5",3.41f,"Produsul %"));
        return lst;
    }
}