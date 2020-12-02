package com.example.cococ.utils;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class Constants {

    @StringDef({Mode.NIGHT_MODE, Mode.LIGHT_MODE})
    @Retention(SOURCE)
    public @interface Mode {
        String NIGHT_MODE = "NIGHT";
        String LIGHT_MODE = "LIGHT";
    }
}
