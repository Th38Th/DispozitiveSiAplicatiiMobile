package com.example.seminardam_teme.misc;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.TextViewCompat;

import com.example.seminardam_teme.R;

public class LoadingIndicator extends ConstraintLayout {

    private TextView tvMessage;
    private TextView tvStatus;
    private ProgressBar pbProgr;
    private ConstraintLayout clProgressBarBox;

    private boolean squarePBar;

    public LoadingIndicator(@NonNull Context context) {
        super(context);
        initializare();
    }

    public LoadingIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializare();
    }

    public LoadingIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializare();
    }

    public LoadingIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializare();
    }

    public void setSquarePBar(boolean squarePBar) {
        LayoutParams layoutParams = (LayoutParams) this.clProgressBarBox.getLayoutParams();
        layoutParams.dimensionRatio = squarePBar? "1:1" : layoutParams.dimensionRatio.equals("1:1")? "":layoutParams.dimensionRatio;
        this.clProgressBarBox.setLayoutParams(layoutParams);
    }

    public boolean isSquarePBar() {
        return ((LayoutParams)this.clProgressBarBox.getLayoutParams()).dimensionRatio.equals("1:1");
    }

    private void citireAtributeStiluri(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = null;
        try {
            typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingIndicator, defStyleAttr, 0);
            TextViewCompat.setTextAppearance(tvMessage, typedArray.getResourceId(R.styleable.LoadingIndicator_messageTextAppearance, R.style.TextAppearance_AppCompat));
            TextViewCompat.setTextAppearance(tvStatus, typedArray.getResourceId(R.styleable.LoadingIndicator_statusTextAppearance, R.style.TextAppearance_AppCompat_Small));
            setSquarePBar(typedArray.getBoolean(R.styleable.LoadingIndicator_squareProgressIndicator, true));
        } catch (Exception e) {
            Log.e("QuantityView", e.getMessage());
        } finally {
            if (typedArray != null)
                typedArray.recycle();
        }
    }

    private void initializare() {
        LayoutInflater l_infl = LayoutInflater.from(getContext());
        l_infl.inflate(R.layout.loading_indicator, this, true);
        tvMessage = findViewById(R.id.tvLoadingMsg);
        tvStatus = findViewById(R.id.tvProgressStatus);
        pbProgr = findViewById(R.id.pbProgressIndicator);
        clProgressBarBox = findViewById(R.id.clPbLayoutBox);
    }

}
