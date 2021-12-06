package com.example.seminardam_teme.model;

import androidx.room.TypeConverter;

import java.util.Locale;

public class LocaleConverter {
    @TypeConverter
    public String localeToLanguageTag(Locale locale){
        return locale.toLanguageTag();
    }
    @TypeConverter
    public Locale localeFromLanguageTag(String languageTag){
        return Locale.forLanguageTag(languageTag);
    }
}
