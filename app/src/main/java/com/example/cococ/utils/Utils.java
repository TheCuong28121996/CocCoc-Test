package com.example.cococ.utils;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String getImageUrl(String description){
        String urlImage = "";
        String regularExpression = "src=\"(.*)\" ></a>";
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(description);

        while (matcher.find()) {
            urlImage = matcher.group(1);
            Log.d("DevDebug","urlImage "+urlImage);
        }
        return urlImage;
    }

    public static String getContent(String description){
        String content = "";
        String regularExpression = "</br>(.*)";
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(description);

        while (matcher.find()) {
            content = matcher.group(1);
            Log.d("DevDebug","content "+content);
        }
        return content;
    }
}
