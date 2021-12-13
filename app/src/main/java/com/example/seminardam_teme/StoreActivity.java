package com.example.seminardam_teme;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.seminardam_teme.model.Database;
import com.example.seminardam_teme.model.Produs;
import com.example.seminardam_teme.json.IResponse;
import com.example.seminardam_teme.json.JsonReader;
import com.example.seminardam_teme.model.ProdusDAO;
import com.example.seminardam_teme.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        syncWithDatabase();

    }

    private void syncWithDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refProducts = database.getReference("products");
        refProducts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChildren()) loadProductListFromUrl("https://fakestoreapi.com/products");
                else {
                    new Thread(()->{
                        List<Produs> prod = produseDao.getProduse();
                        runOnUiThread(()->setProductList(prod));
                    }).start();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("PRODUCT_ERR", error.toString());
            }
        });
        refProducts.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Produs loadedProd = snapshot.getValue(Produs.class);
                new Thread(()->produseDao.insert(loadedProd)).start();
                Log.v("PRODUCT_CREATED", loadedProd.toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Produs loadedProd = snapshot.getValue(Produs.class);
                new Thread(()->produseDao.update(loadedProd)).start();
                Log.v("PRODUCT_CHANGED", loadedProd.toString());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Produs loadedProd = snapshot.getValue(Produs.class);
                new Thread(()->produseDao.delete(loadedProd)).start();
                Log.v("PRODUCT_DELETED", loadedProd.toString());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Produs loadedProd = snapshot.getValue(Produs.class);
                new Thread(()->produseDao.update(loadedProd)).start();
                Log.v("PRODUCT_MOVED", loadedProd.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("PRODUCT_ERR", error.toString());
            }
        });
    }

    private void writeToDatabase(Produs prod) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refProducts = database.getReference("products");
        refProducts.push().setValue(prod);
    }

    private void setProductList(List<Produs> produsList){
        storeAdapter.setLista(produsList);
        if (lvProduse.getAdapter()==null)
            lvProduse.setAdapter(storeAdapter);
        tvStore.setText(getResources().getString(R.string.str_store_msg, UtilityFormulae.prettyLowerBound((produsList.size()))));
    }

    private void loadProductListFromUrl(String url) {
        JsonReader reader = new JsonReader();
        Thread thread = new Thread(() -> reader.read(url, new IResponse<List<Produs>>(){

            @Override
            public void onSuccess(List<Produs> lst) {
                for(Produs p: lst) writeToDatabase(p);
            }

            @Override
            public void onError(String mesaj) {
                for(Produs p: getProduse()) writeToDatabase(p);
                runOnUiThread(()-> {
                    Toast.makeText(StoreActivity.this, R.string.str_api_fail_err, Toast.LENGTH_LONG).show();
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