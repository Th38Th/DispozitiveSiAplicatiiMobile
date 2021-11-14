package com.example.seminardam_teme;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seminardam_teme.json.IResponse;
import com.example.seminardam_teme.json.JsonReader;

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
        lvProduse = findViewById(R.id.lvProduse);

        produsList = new ArrayList<>();
        storeAdapter = new MagazinAdapter(produsList, 0, R.string.fmt_pret, R.string.fmt_desc);
        lvProduse.setAdapter(storeAdapter);

        loadProductListFromUrl("https://fakestoreapi.com/products");
    }

    private void loadProductListFromUrl(String url){
        JsonReader reader = new JsonReader();
        Thread thread = new Thread(() -> reader.read(url, new IResponse<List<Produs>>(){

            @Override
            public void onSuccess(List<Produs> lst) {
                runOnUiThread(()-> setProductList(lst));
            }

            @Override
            public void onError(String mesaj) {
                runOnUiThread(()-> {
                    Toast.makeText(StoreActivity.this, "Loading from API failed... Populating list with default values", Toast.LENGTH_LONG).show();
                    setProductList(getProduse());
                });
            }
        }));
        thread.start();
    }

    private void setProductList(List<Produs> newProduse) {
        produsList.clear();
        produsList.addAll(newProduse);
        tvStore.setText(getResources().getString(R.string.str_store_msg,
                UtilityFormulae.prettyLowerBound(produsList.size())));
        storeAdapter.notifyDataSetChanged();
    }

    private List<Produs> getProduse() {
        List<Produs> lst = new ArrayList<>();
        lst.add(new Produs("Prod1",5.64f,"Produsul 1"));
        lst.add(new Produs("Prod2",7.43f,"Produsul doi"));
        lst.add(new Produs("Prod3",2.32f,"Produsul III", BitmapFactory.decodeResource(getResources(), R.drawable.icn)));
        lst.add(new Produs("Prod4",9.12f,"Produsul 2x2"));
        lst.add(new Produs("Prod5",3.41f,"Produsul %"));
        return lst;
    }
}