package com.example.seminardam_teme.misc;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.TextViewCompat;

import com.example.seminardam_teme.R;

import org.w3c.dom.Text;

public class LoadingIndicator extends ConstraintLayout {

    private TextView tvMessage;
    private TextView tvStatus;
    private ProgressBar pbProgr;
    private ConstraintLayout clProgressBarBox;

    public enum StatusDisplayStyle {
        DEFAULT,
        PERCENT,
        NONE
    }

    private StatusDisplayStyle statusDisplayStyle;

    public LoadingIndicator(@NonNull Context context) {
        super(context);
        initializare();
    }

    public LoadingIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializare();
        citireAtributeStiluri(context, attrs, 0, 0);
    }

    public LoadingIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializare();
        citireAtributeStiluri(context, attrs, defStyleAttr, 0);
    }

    public LoadingIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializare();
        citireAtributeStiluri(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setSquarePBar(boolean squarePBar) {
        LayoutParams layoutParams = (LayoutParams) this.clProgressBarBox.getLayoutParams();
        layoutParams.dimensionRatio = squarePBar? "1:1" : layoutParams.dimensionRatio.equals("1:1")? "":layoutParams.dimensionRatio;
        this.clProgressBarBox.setLayoutParams(layoutParams);
    }

    public boolean isSquarePBar() {
        return ((LayoutParams)this.clProgressBarBox.getLayoutParams()).dimensionRatio.equals("1:1");
    }

    public void setProgressDrawable(Drawable dr){
        pbProgr.setProgressDrawable(dr);
    }

    public Drawable getProgressDrawable(){
        return pbProgr.getProgressDrawable();
    }

    public void setProgressTintList(ColorStateList cls){
        pbProgr.setProgressTintList(cls);
    }

    public ColorStateList getProgressTintList(){
        return pbProgr.getProgressTintList();
    }

    public void setStatusDisplayStyle(StatusDisplayStyle st){
        statusDisplayStyle = st;
        updateStatus();
    }

    public StatusDisplayStyle getStatusDisplayStyle(){
        return statusDisplayStyle;
    }

    public CharSequence genStatusText(StatusDisplayStyle sds){
        CharSequence seq;
        switch (sds) {
            case PERCENT:
                seq = Html.fromHtml(getResources().getString(R.string.fmt_status_li_percent, (int)((double)getProgress()/(double)getMax()*100)));
                break;
            case NONE:
                seq = null;
                break;
            case DEFAULT:
            default:
                seq = Html.fromHtml(getResources().getString(R.string.fmt_status_li_default, getProgress(), getMax()));
                break;
        }
        return seq;
    }

    private void setStatusText(CharSequence text){
        tvStatus.setText(text);
    }

    public CharSequence getStatusText(){
        return tvStatus.getText();
    }

    private void updateStatus() {
        setStatusText(genStatusText(statusDisplayStyle));
    }

    public void setCurrentTaskName(CharSequence taskName){
        tvMessage.setText(taskName);
    }

    public CharSequence getCurrentTaskName(){
        return tvMessage.getText();
    }


    public void setProgress(int progress) {
        pbProgr.setProgress(progress);
        updateStatus();
    }

    public int getProgress(){
        return pbProgr.getProgress();
    }

    public void setMax(int max) {
        pbProgr.setMax(max);
        updateStatus();
    }

    public int getMax() {
        return pbProgr.getMax();
    }

    private void citireAtributeStiluri(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray = null;
        try {
            typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingIndicator, defStyleAttr, defStyleRes);
            TextViewCompat.setTextAppearance(tvMessage, typedArray.getResourceId(R.styleable.LoadingIndicator_currentTaskNameTextAppearance, R.style.TextAppearance_AppCompat));
            setCurrentTaskName(typedArray.getString(R.styleable.LoadingIndicator_currentTaskName));
            TextViewCompat.setTextAppearance(tvStatus, typedArray.getResourceId(R.styleable.LoadingIndicator_statusTextAppearance, R.style.TextAppearance_AppCompat_Small));
            setProgressDrawable(typedArray.getDrawable(R.styleable.LoadingIndicator_progressDrawable));
            setProgressTintList(typedArray.getColorStateList(R.styleable.LoadingIndicator_progressTint));
            setStatusDisplayStyle(StatusDisplayStyle.values()[typedArray.getInteger(R.styleable.LoadingIndicator_statusDisplayStyle,0)]);
            setProgress(typedArray.getInteger(R.styleable.LoadingIndicator_progress, 0));
            setMax(typedArray.getInteger(R.styleable.LoadingIndicator_progressMax, 0));
            setSquarePBar(typedArray.getBoolean(R.styleable.LoadingIndicator_squareProgressIndicator, true));
        } catch (Exception e) {
            e.printStackTrace();
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
