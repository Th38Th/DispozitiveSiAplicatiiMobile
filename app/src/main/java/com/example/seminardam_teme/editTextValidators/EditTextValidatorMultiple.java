package com.example.seminardam_teme.editTextValidators;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class EditTextValidatorMultiple extends SimpleRegexValidator {

    // Modul in care sunt 'agregate' rezultatele testarii cu fiecare expresie regulata
    public enum RegexChainingMode {
        /// Toate Regex-urile trebuie indeplinite impreuna (Primul regex care esueaza -> mesaj de eroare aferent)
        AND,
        // Cel putin un Regex trebuie indeplinit (Se afiseaza mesajul de eroare daca nu se indeplineste nici un regex)
        OR,
        // Un singur Regex trebuie indeplinit (Se afiseaza mesajul de eroare daca sunt indeplinite mai multe regex-uri, sau nici unul)
        XOR,
        // Cel putin un Regex sa nu fie indeplinit (Se afiseaza mesajul de eroare daca sunt toate regex-urile indeplinite)
        NOR,
        // Exact un Regex sa nu fie indeplinit (Se afiseaza mesajul de eroare daca esueaza mai mult de 1 regex)
        XNOR,
        // Nici un Regex sa nu fie indeplinit (Primul Regex care se potriveste -> mesajul de eroare aferent)
        NAND,
    }

    private Map<Pattern, String> patterns;
    private RegexChainingMode regexChainingMode;

    public EditTextValidatorMultiple(EditText w_ett, String defaultErrorMsg, Map<Pattern, String> verifs, RegexChainingMode rcm) {
        super(w_ett, defaultErrorMsg);
        this.patterns = new LinkedHashMap<>();
        if (verifs != null)
            this.patterns.putAll(verifs);
        this.regexChainingMode = rcm;
    }

    public EditTextValidatorMultiple(EditText w_ett, String defaultErrorMsg, Map<Pattern, String> verifs) {
        this(w_ett, defaultErrorMsg, verifs, RegexChainingMode.AND);
    }

    public EditTextValidatorMultiple(EditText w_ett, String defaultErrorMsg) {
        this(w_ett, defaultErrorMsg, null, RegexChainingMode.AND);
    }

    public EditTextValidatorMultiple(EditText w_ett) {
        this(w_ett, null, null, RegexChainingMode.AND);
    }

    public EditTextValidatorMultiple() {
        this(null, null, null, RegexChainingMode.AND);
    }




    @Override
    public TextWatcher createListener() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            public void afterTextChanged(Editable s) {
                Pair<Boolean,String> p = checkValid(s.toString());
                watchedTextView.setError(p.first? null : (p.second == null ? errorMsg : p.second));
            }
        };
    }

    public Map<Pattern, String> getPatterns() {
        return this.patterns;
    }

    public void setPatterns(Map<Pattern, String> verifs) {
        this.patterns = new LinkedHashMap<>();
        this.patterns.putAll(verifs);
        if (this.listener != null)
            reinitListener();
    }

    public String getPatternMessage(Pattern pt){
        return this.patterns.get(pt);
    }

    public void addPattern(Pattern pt, String msg){
        this.patterns.put(pt, msg);
    }

    public void addPattern(String pt_str, String msg){
        this.patterns.put(Pattern.compile(pt_str), msg);
    }

    public void setRegexChainingMode(RegexChainingMode n_rcm) {
        this.regexChainingMode = n_rcm;
    }

    public void setPatternMessage(Pattern pt, String msg){
        this.patterns.put(pt, msg);
    }

    public void removePattern(Pattern pt) {
        this.patterns.remove(pt);
    }

    private Pair<Boolean, String> checkValid(String str) {
        boolean passed = true;
        boolean firstDone = false;
        boolean k;
        String error = null;
        for (Map.Entry<Pattern,String> p: patterns.entrySet())
            switch(regexChainingMode) {
                case AND:
                    if (!p.getKey().matcher(str).matches()) {
                        error = p.getValue();
                        break;
                    }
                    break;
                case OR:
                    k = p.getKey().matcher(str).matches();
                    if (!firstDone) {
                        passed = k;
                        firstDone = true;
                    } else {
                        passed |= k;
                    }
                    break;
                case XOR:
                    k = p.getKey().matcher(str).matches();
                    if (!firstDone) {
                        passed = k;
                        firstDone = true;
                    } else {
                        passed ^= k;
                    }
                    break;
                case NOR:
                    k = !p.getKey().matcher(str).matches();
                    if (!firstDone) {
                        passed = k;
                        firstDone = true;
                    } else {
                        passed |= k;
                    }
                    break;
                case NAND:
                    if (p.getKey().matcher(str).matches()) {
                        error = p.getValue();
                        passed = false;
                    }
                    break;
                case XNOR:
                    k = !p.getKey().matcher(str).matches();
                    if (!firstDone) {
                        passed = k;
                        firstDone = true;
                    } else {
                        passed ^= k;
                    }
                    break;
                default:
                    break;
            }
        return new Pair<>(passed,error);
    }

    @Override
    public boolean isValid() {
        boolean result = false;
        if (watchedTextView != null)
            result = checkValid(watchedTextView.getText().toString()).first;
        return result;
    }
}