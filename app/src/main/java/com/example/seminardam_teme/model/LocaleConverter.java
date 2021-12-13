package com.example.seminardam_teme.model;

import androidx.room.TypeConverter;

import java.util.Locale;

public class LocaleConverter {
    @TypeConverter
    public static String localeToLanguageTag(Locale locale){
        return locale.toLanguageTag();
    }
    @TypeConverter
    public static Locale localeFromLanguageTag(String languageTag){
        return Locale.forLanguageTag(languageTag);
    }
}
