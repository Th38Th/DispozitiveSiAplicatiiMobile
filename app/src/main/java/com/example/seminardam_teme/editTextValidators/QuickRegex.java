package com.example.seminardam_teme.editTextValidators;

import java.util.regex.Pattern;

public final class QuickRegex {
    /*
    public static final Pattern email = Pattern.compile("^[a-zA-Z0-9._+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z0-9-.]+$");
    public static final Pattern phone = Pattern.compile("^\\(?\\+?[0-9]{1,3}\\)?([ .-]?)+[0-9]{3}([ .-]?)+[0-9]{3}([ .-]?)+[0-9]{3}$");
    */
    public static Pattern atLeast(int n) {
        return Pattern.compile("^." + "{" + n + ",}");
    }
}
