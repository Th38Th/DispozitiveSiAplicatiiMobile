package com.example.seminardam_teme;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.example.seminardam_teme.model.Produs;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MagazinAdapter extends BaseAdapter {

    public List<Produs> getLista() {
        return lista;
    }

    public void setLista(List<Produs> lista) {
        if (lista != this.lista) {
            this.lista = lista;
            notifyDataSetChanged();
        }
    }

    private List<Produs> lista;
    private int fmtDenumire;
    private int fmtPret;
    private int fmtDescriere;

    private ContextProvider ctxpvdr;

    private static final String FMT_DENUMIRE_DEFAULT = "%s";
    private static final String FMT_PRET_DEFAULT = "Pret: %.2f";
    private static final String FMT_DESCRIERE_DEFAULT = "\"%s\"";

    public MagazinAdapter(List<Produs> lista, ContextProvider ctxpvdr, @StringRes int fmtDenumire, @StringRes int fmtPret, @StringRes int fmtDescriere) {
        this.lista = lista;
        this.ctxpvdr = ctxpvdr;
        this.fmtDenumire = fmtDenumire;
        this.fmtPret = fmtPret;
        this.fmtDescriere = fmtDescriere;
    }

    public MagazinAdapter(List<Produs> lista, ContextProvider ctxpvdr) {
        this(lista, ctxpvdr, 0, 0, 0);
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Produs getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder {
        final TextView tvNume;
        final TextView tvPret;
        final TextView tvDesc;
        final ImageView ivImg;

        public ViewHolder(TextView tvNume, TextView tvPret, TextView tvDesc, ImageView ivImg) {
            this.tvNume = tvNume;
            this.tvPret = tvPret;
            this.tvDesc = tvDesc;
            this.ivImg = ivImg;
        }

        static ViewHolder from(View v){
            TextView tvNume = v.findViewById(R.id.tvDenProd);
            TextView tvPret = v.findViewById(R.id.tvPretProd);
            TextView tvDesc = v.findViewById(R.id.tvDescProd);
            ImageView ivImg = v.findViewById(R.id.ivProdus);
            return new ViewHolder(tvNume,tvPret,tvDesc,
                    ivImg);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater infl = LayoutInflater.from(parent.getContext());
            convertView = infl.inflate(R.layout.product_item, parent, false);
            holder = ViewHolder.from(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Produs current = lista.get(position);
        Context ctx = ctxpvdr.getContext();
        holder.tvNume.setText(fmtDenumire==0? String.format(FMT_DENUMIRE_DEFAULT,current.getDenumire()) : Html.fromHtml(ctx.getResources().getString(fmtDenumire, current.getDenumire())));
        holder.tvPret.setText(fmtPret==0? String.format(ctx.getResources().getConfiguration().locale,FMT_PRET_DEFAULT,current.getPret()) : Html.fromHtml(ctx.getResources().getString(fmtPret, current.getPret())));
        holder.tvDesc.setText(fmtDescriere==0? String.format(FMT_DESCRIERE_DEFAULT,current.getDescriere()) : Html.fromHtml(ctx.getResources().getString(fmtDescriere, current.getDescriere())));
        new Thread(()-> {
            try (InputStream istr = new URL(current.getUrlImagine()).openStream()) {
                Bitmap bmp = BitmapFactory.decodeStream(istr);
                ((Activity)ctx).runOnUiThread(()->holder.ivImg.setImageBitmap(bmp));
            } catch (MalformedURLException mfe) {
                Log.e("loadImage", "Failed to load image from url \"" + current.getUrlImagine() + "\"");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        return convertView;
    }
}
