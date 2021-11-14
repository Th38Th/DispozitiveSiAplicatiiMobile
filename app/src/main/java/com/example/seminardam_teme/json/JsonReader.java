package com.example.seminardam_teme.json;

import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.seminardam_teme.Produs;

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
        Map<Produs, String> urlS = new HashMap<>();
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
                String numeProd = currentObject.getString("title");
                double pretProd = currentObject.getDouble("price");
                String descProd = currentObject.getString("description");
                String urlPath = currentObject.getString("image");
                Produs produs = new Produs(numeProd, (float)pretProd, descProd);
                urlS.put(produs, urlPath);
                lista.add(produs);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        new Thread() {
            public void run() {
                for (Map.Entry<Produs, String> p: urlS.entrySet()) {
                    try(InputStream istr = new URL(p.getValue()).openStream()){
                        p.getKey().setImagine(BitmapFactory.decodeStream(istr));
                    } catch (MalformedURLException mfe){
                        Log.e("loadImage","Failed to load image from url \""+p.getValue()+"\"");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        return lista;
    }
}
