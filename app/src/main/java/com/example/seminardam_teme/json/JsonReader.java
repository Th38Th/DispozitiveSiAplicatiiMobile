package com.example.seminardam_teme.json;

import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.seminardam_teme.model.Produs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonReader {
    public void read(String urlPath, IResponse<List<Produs>> rsp) {
        try {
            URL path = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) path.openConnection();

            conn.connect();

            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader rdr = new BufferedReader(isr);

            StringBuilder res = new StringBuilder();
            String line = "";
            while ((line = rdr.readLine()) != null)
                res.append(line);

            rdr.close();
            isr.close();
            is.close();

            conn.disconnect();

            List<Produs> list = parse(res.toString(), null, rsp);

            rsp.onSuccess(list);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            rsp.onError(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            rsp.onError(e.getMessage());
        }
    }


    private List<Produs> parse(String jsonString, String root, IResponse<List<Produs>> rsp) {
        List<Produs> lista = new ArrayList<>();
        try
        {
            JSONArray arrayJson;
            if (root == null)
                arrayJson = new JSONArray(jsonString);
            else {
                JSONObject obiectJson = new JSONObject(jsonString);
                arrayJson = obiectJson.getJSONArray(root);
            }
            for (int i = 0; i < arrayJson.length(); i++) {
                JSONObject currentObject = arrayJson.getJSONObject(i);
                int idProd = currentObject.getInt("id");
                String numeProd = currentObject.getString("title");
                double pretProd = currentObject.getDouble("price");
                String descProd = currentObject.getString("description");
                String urlPath = currentObject.getString("image");
                Produs produs = new Produs(idProd, numeProd, (float)pretProd, descProd, urlPath);
                lista.add(produs);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
