package com.example.seminardam_teme.editTextValidators;

import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

// Clasa de baza, abstracta, pentru validatorii Regex

abstract class SimpleRegexValidator {

    protected TextView watchedTextView;
    protected TextWatcher listener;
    protected CharSequence errorMsg;
    private boolean listening;

    public boolean isListening() {
        return listening;
    }

    public SimpleRegexValidator(EditText w_ett, CharSequence errorMsg) {
        this.watchedTextView = w_ett;
        this.errorMsg = errorMsg;
        this.listening = false;
    }

    public SimpleRegexValidator(EditText w_ett) {
        this(w_ett, null);
    }

    public SimpleRegexValidator(){
        this(null, null);
    }

    public void disable() {
        if (watchedTextView != null && listener != null && listening) {
            watchedTextView.removeTextChangedListener(listener);
            listening = false;
        }
    }

    public void enable() {
        if (!listening) {
            if (listener == null)
                this.listener = this.createListener();
            if (watchedTextView != null){
                watchedTextView.addTextChangedListener(listener);
                listening = true;
            }
        }
    }

    protected abstract TextWatcher createListener();

    protected void setListener(TextWatcher tw){
        this.listener = tw;
    }

    protected void reinitListener() {
        disable();
        this.listener = this.createListener();
    }

    public void setTextView(TextView v) {
        this.disable();
        this.watchedTextView = v;
        this.reinitListener();
    }

    public TextView getTextView(){
        return this.watchedTextView;
    }

    public abstract boolean isValid();
}




