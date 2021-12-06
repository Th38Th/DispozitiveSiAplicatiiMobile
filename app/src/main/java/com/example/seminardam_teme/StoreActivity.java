package com.example.seminardam_teme;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seminardam_teme.model.Database;
import com.example.seminardam_teme.model.Produs;
import com.example.seminardam_teme.json.IResponse;
import com.example.seminardam_teme.json.JsonReader;
import com.example.seminardam_teme.model.ProdusDAO;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends CustomActionBarActivity {

    private TextView tvStore;
    private ListView lvProduse;
    private MagazinAdapter storeAdapter;
    private ProdusDAO produseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Database dbProduse = Database.getInstance(this);
        produseDao = dbProduse.getDataBase().produsDAO();

        tvStore = findViewById(R.id.tvStore);
        lvProduse = findViewById(R.id.lvProduse);

        storeAdapter = new MagazinAdapter(null, () -> StoreActivity.this,0, R.string.fmt_pret, R.string.fmt_desc);

        loadProductListFromUrl("https://fakestoreapi.com/products");

    }

    private void setProductList(List<Produs> produsList){
        storeAdapter.setLista(produsList);
        if (lvProduse.getAdapter()==null)
            lvProduse.setAdapter(storeAdapter);
        tvStore.setText(getResources().getString(R.string.str_store_msg, UtilityFormulae.prettyLowerBound((produsList.size()))));
    }

    private void loadProductListFromUrl(String url){
        JsonReader reader = new JsonReader();
        Thread thread = new Thread(() -> reader.read(url, new IResponse<List<Produs>>(){

            @Override
            public void onSuccess(List<Produs> lst) {
                produseDao.insert(lst);
                runOnUiThread(()-> setProductList(lst));
            }

            @Override
            public void onError(String mesaj) {
                produseDao.insert(getProduse());
                runOnUiThread(()-> {
                    Toast.makeText(StoreActivity.this, R.string.str_api_fail_err, Toast.LENGTH_LONG).show();
                    setProductList(getProduse());
                });
            }
        }));
        thread.start();
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