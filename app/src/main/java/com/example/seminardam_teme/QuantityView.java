package com.example.seminardam_teme;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.math.BigDecimal;

public class QuantityView extends FrameLayout {
    private ImageView ivSub;
    private TextView tvQty;
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
        init();
    }

    public QuantityView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        citireAtributeStiluri(context, attrs, 0);
    }

    public QuantityView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        citireAtributeStiluri(context, attrs, defStyleAttr);
    }

    private void init() {
        inflate(getContext(), R.layout.quantity_view, this);
        ivSub = findViewById(R.id.ivSub);
        tvQty = findViewById(R.id.tvQty);
        ivAdd = findViewById(R.id.ivAdd);
        ivSub.setOnClickListener((v) -> modificareCantitate(-deltaCantitate));
        ivAdd.setOnClickListener((v) -> modificareCantitate(deltaCantitate));
        configurareView();
    }

    private void citireAtributeStiluri(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.QuantityView, defStyleAttr, 0);
            cantitateMinima = typedArray.getInteger(R.styleable.QuantityView_minQuantity, 0);
            cantitateMaxima = typedArray.getInteger(R.styleable.QuantityView_maxQuantity, 100);
            cantitateInitiala = typedArray.getInteger(R.styleable.QuantityView_startQuantity, 0);
            deltaCantitate = typedArray.getInteger(R.styleable.QuantityView_deltaQuantity, 10);
            colorRes = typedArray.getResourceId(R.styleable.QuantityView_colorOfQuantity, R.color.black);
            textColorRes = typedArray.getResourceId(R.styleable.QuantityView_colorOfText, R.color.black);
            doarContur = typedArray.getBoolean(R.styleable.QuantityView_isOutlined, true);
            return;
        }
        cantitateMinima = 0;
        cantitateMaxima = 100;
        cantitateInitiala = 0;
        deltaCantitate = 10;
        colorRes = R.color.black;
        textColorRes = R.color.black;
        doarContur = true;
    }

    private void configurareView() {
        if (!doarContur) {
            ivAdd.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_plus_button));
            ivSub.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_minus_button));
        } else {
            ivAdd.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_plus_button_outl));
            ivSub.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_minus_button_outl));
        }
        if (cantitateInitiala <= cantitateMinima) {
            cantitateCurenta = cantitateMinima;
            ivSub.setEnabled(false);
            ivAdd.setEnabled(true);
            configurareCulori(false, true);
        } else if (cantitateInitiala >= cantitateMaxima) {
            cantitateCurenta = cantitateMaxima;
            ivSub.setEnabled(true);
            ivAdd.setEnabled(false);
            configurareCulori(true, false);
        } else {
            cantitateCurenta = cantitateInitiala;
            ivSub.setEnabled(true);
            ivAdd.setEnabled(true);
            configurareCulori(true, true);
        }
        tvQty.setText(Integer.toString(cantitateCurenta));
    }

    private void configurareCulori(boolean isSubtractEnabled, boolean isAddEnabled) {
        int disabledColor = ContextCompat.getColor(getContext(), R.color.material_on_surface_disabled);
        int color = ContextCompat.getColor(getContext(), colorRes);
        if (color != ContextCompat.getColor(getContext(), R.color.black)) {
            ivAdd.getDrawable().setColorFilter(isAddEnabled ? color : disabledColor, PorterDuff.Mode.SRC_ATOP);
            tvQty.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            ivSub.getDrawable().setColorFilter(isSubtractEnabled ? color : disabledColor, PorterDuff.Mode.SRC_ATOP);
        }
        int textColor = ContextCompat.getColor(getContext(), textColorRes);
        if (textColor != ContextCompat.getColor(getContext(), R.color.black)) {
            tvQty.setTextColor(textColor);
        }
    }

    private void modificareCantitate(int delta){
        int cant_noua = cantitateCurenta + delta;
        if (cant_noua > cantitateMinima && cant_noua < cantitateMaxima){
            cantitateCurenta = cant_noua;
            if (!ivAdd.isEnabled()) modifyViewClickable(true, false);
            if (!ivSub.isEnabled()) modifyViewClickable(true, true);
        }
        else {
            if (cant_noua <= cantitateMinima){
                cantitateCurenta = cantitateMinima;
                modifyViewClickable(false, true);
            }
            else if (cant_noua >= cantitateMaxima){
                cantitateCurenta = cantitateMaxima;
                modifyViewClickable(false, false);
            }
        }
        tvQty.setText(Integer.toString(cantitateCurenta));
    }

    private void modifyViewClickable(boolean isEnabled, boolean isSubtract) {
        int defColor = isEnabled ? colorRes : R.color.material_on_surface_disabled;
        int color = ContextCompat.getColor(getContext(), defColor);
        if (isSubtract) {
            ivSub.getDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            ivSub.setEnabled(isEnabled);
            return;
        }
        ivAdd.getDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        ivAdd.setEnabled(isEnabled);
    }

    public int getCantitate() {
        return cantitateCurenta;
    }
}
