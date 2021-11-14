package com.example.seminardam_teme;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class QuantityView extends ConstraintLayout {
    private ImageView ivSub;
    private EditText etQty;
    private ImageView ivAdd;

    // Atribute
    private int cantitateMinima;
    private int cantitateMaxima;
    private int cantitateInitiala;
    private int cantitateCurenta;
    private int deltaCantitate;
    private int textColorRes;
    private int colorRes;
    private boolean doarContur;

    public QuantityView(@NonNull Context context) {
        super(context);
        initizalizare();
    }

    public QuantityView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        citireAtributeStiluri(context, attrs, 0);
        initizalizare();
    }

    public QuantityView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        citireAtributeStiluri(context, attrs, defStyleAttr);
        initizalizare();
    }

    private void initizalizare() {
        LayoutInflater l_infl = LayoutInflater.from(getContext());
        l_infl.inflate(R.layout.quantity_view, this, true);
        ivSub = findViewById(R.id.ivSub);
        etQty = findViewById(R.id.etQty);
        ivAdd = findViewById(R.id.ivAdd);
        ivSub.setOnClickListener((v) -> modificareCantitate(-deltaCantitate));
        ivAdd.setOnClickListener((v) -> modificareCantitate(deltaCantitate));
        etQty.addTextChangedListener(new TextWatcher() {

            private boolean isEditing = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!isEditing) {
                    isEditing = true;
                    int qte;
                    try {
                        qte = Integer.parseInt(s.toString());
                    } catch (NumberFormatException nfe) {
                        qte = cantitateMinima;
                    }
                    setCantitate(qte);
                    String newQteString = String.valueOf(getCantitate());
                    if (!s.toString().equals(newQteString))
                        s.replace(0, s.length(), newQteString);
                    isEditing = false;
                }
            }
        });
        etQty.setTextColor(ContextCompat.getColor(getContext(), textColorRes));
        setDoarContur(doarContur);
        setCantitate(cantitateInitiala);
    }

    private void citireAtributeStiluri(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs == null) {
            cantitateMinima = 0;
            cantitateMaxima = 100;
            cantitateInitiala = 0;
            deltaCantitate = 10;
            colorRes = R.color.aquamarine_700;
            textColorRes = R.color.black;
            doarContur = false;
        }
        else {
            TypedArray typedArray = null;
            try {
                typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.QuantityView, defStyleAttr, 0);
                cantitateMinima = typedArray.getInteger(R.styleable.QuantityView_minQuantity, 0);
                cantitateMaxima = typedArray.getInteger(R.styleable.QuantityView_maxQuantity, 100);
                cantitateInitiala = typedArray.getInteger(R.styleable.QuantityView_startQuantity, 0);
                deltaCantitate = typedArray.getInteger(R.styleable.QuantityView_deltaQuantity, 10);
                colorRes = typedArray.getResourceId(R.styleable.QuantityView_colorOfQuantity, R.color.aquamarine_700);
                textColorRes = typedArray.getResourceId(R.styleable.QuantityView_colorOfText, R.color.black);
                doarContur = typedArray.getBoolean(R.styleable.QuantityView_isOutlined, true);
            } catch (Exception e) {
                Log.e("QuantityView", e.getMessage());
            } finally {
                if (typedArray != null)
                    typedArray.recycle();
            }
        }
    }

    public void setDoarContur(boolean doar_contur) {
        doarContur = doar_contur;
        if (doarContur) {
            ivAdd.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_plus_button_outl));
            ivSub.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_minus_button_outl));
        } else {
            ivAdd.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_plus_button));
            ivSub.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_minus_button));
        }
    }

    private boolean cantitateValida(int qte) {
        return cantitateMinima <= qte && qte <= cantitateMaxima;
    }

    public void setButtonsEnabled(boolean enableSubtract, boolean enableAdd) {

        int enabledColor = ContextCompat.getColor(getContext(), colorRes);
        int disabledColor = ContextCompat.getColor(getContext(), R.color.material_on_surface_disabled);

        int subColor = enableSubtract? enabledColor : disabledColor;
        ivSub.getDrawable().setColorFilter(subColor, PorterDuff.Mode.SRC_ATOP);
        ivSub.setEnabled(enableSubtract);

        int addColor = enableAdd? enabledColor : disabledColor;
        ivAdd.getDrawable().setColorFilter(addColor, PorterDuff.Mode.SRC_ATOP);
        ivAdd.setEnabled(enableAdd);

    }

    public void setCantitate(int cantitateNoua) {
        if (cantitateValida(cantitateNoua)) {
            cantitateCurenta = cantitateNoua;
        } else {
            if (cantitateNoua < cantitateMinima)
                cantitateCurenta = cantitateMinima;
            else if (cantitateNoua > cantitateMaxima)
                cantitateCurenta = cantitateMaxima;
        }
        setButtonsEnabled(
                cantitateCurenta > cantitateMinima,
                cantitateCurenta < cantitateMaxima
        );
        etQty.setText(Integer.toString(cantitateCurenta));
        Log.v("qtyView","qtyChanged");
    }

    public void modificareCantitate(int delta) {
        int cant_noua = cantitateCurenta + delta;
        setCantitate(cant_noua);
    }

    public int getCantitate() {
        return cantitateCurenta;
    }
}
