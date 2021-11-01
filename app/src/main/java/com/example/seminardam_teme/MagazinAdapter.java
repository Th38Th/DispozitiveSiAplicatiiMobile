package com.example.seminardam_teme;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MagazinAdapter extends BaseAdapter {

    private List<Produs> lista;
    private String fmtDenumire;
    private String fmtPret;
    private String fmtDescriere;

    private static final String FMT_DENUMIRE_DEFAULT = "%s";
    private static final String FMT_PRET_DEFAULT = "Pret: %.2f";
    private static final String FMT_DESCRIERE_DEFAULT = "\"%s\"";

    public MagazinAdapter(List<Produs> lista, String fmtDenumire, String fmtPret, String fmtDescriere) {
        this.lista = lista;
        this.fmtDenumire = (fmtDenumire!=null && fmtDenumire.contains("%"))? fmtDenumire : FMT_DENUMIRE_DEFAULT;
        this.fmtPret = (fmtPret!=null && fmtPret.contains("%"))? fmtPret : FMT_PRET_DEFAULT;
        this.fmtDescriere = (fmtDescriere!=null && fmtDescriere.contains("%"))? fmtDenumire : FMT_DESCRIERE_DEFAULT;
    }

    public MagazinAdapter(List<Produs> lista) {
        this(lista, FMT_DENUMIRE_DEFAULT, FMT_PRET_DEFAULT, FMT_DESCRIERE_DEFAULT);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context ctx = parent.getContext();
        String pkg = ctx.getPackageName();
        LayoutInflater infl = LayoutInflater.from(parent.getContext());
        View itemView = infl.inflate(R.layout.product_item, parent, false);
        //ImageView i_img = itemView.findViewById(R.id.ivProdus);
        TextView t_nume = itemView.findViewById(R.id.tvDenProd);
        TextView t_pret = itemView.findViewById(R.id.tvPretProd);
        TextView t_desc = itemView.findViewById(R.id.tvDescProd);
        Produs current = lista.get(position);
        //i_img.setImageDrawable(current.getImagine());
        int fmtDen_id = ctx.getResources().getIdentifier(fmtDenumire, "string", pkg);
        t_nume.setText(fmtDen_id==0? String.format(fmtDenumire,current.getDenumire()) : ctx.getResources().getString(fmtDen_id, current.getDenumire()));
        int fmtPret_id = ctx.getResources().getIdentifier(fmtPret, "string", pkg);
        t_pret.setText(fmtPret_id==0? String.format(fmtPret,current.getPret()) : ctx.getResources().getString(fmtPret_id, current.getPret()));
        int fmtDesc_id = ctx.getResources().getIdentifier(fmtDescriere, "string", pkg);
        t_desc.setText(fmtDesc_id==0? String.format(fmtDescriere,current.getDescriere()) : ctx.getResources().getString(fmtDesc_id, current.getDescriere()));
        return itemView;
    }
}
