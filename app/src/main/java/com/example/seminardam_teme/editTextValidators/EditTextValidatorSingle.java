package com.example.seminardam_teme.editTextValidators;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class EditTextValidatorSingle extends SimpleRegexValidator {

    private Pattern pattern;

    public EditTextValidatorSingle(EditText w_ett, String errorMsg, Pattern verif) {
        super(w_ett, errorMsg);
        this.pattern = verif;
    }

    @Override
    public TextWatcher createListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!pattern.matcher(s.toString()).matches())
                    watchedTextView.setError(errorMsg);
                else
                    watchedTextView.setError(null);
            }
        };
    }

    @Override
    public boolean isValid() {
        boolean result = false;
        if (watchedTextView != null)
            result = pattern.matcher(watchedTextView.getText().toString()).matches();
        return result;
    }

    public Pattern getPattern() {
        return this.pattern;
    }

    public void setPattern(String s) {
        this.pattern = Pattern.compile(s);
        reinitListener();
    }

    public void setPattern(Pattern p){
        this.pattern = p;
        reinitListener();
    }


}