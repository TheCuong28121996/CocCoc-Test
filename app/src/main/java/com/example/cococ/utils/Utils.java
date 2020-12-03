package com.example.cococ.utils;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    // <description><![CDATA[<a href="https://vnexpress.net/mau-thuan-tien-bac-trong-vu-bat-coc-chet-nguoi-4200833.html"><img src="https://i1-vnexpress.vnecdn.net/2020/12/03/HIENTRUONGVINHLONG-1606968037-6284-1606968348.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=DriWJOG09dzeSCqYhKfZ3g" >
    // </a></br>Võ Thị Kim Chi, 53 tuổi, thuê người từ Vũng Tàu xuống "bắt cóc" con gái về giải quyết mâu thuẫn tiền bạc khi bán nhà đất nhưng sau đó xảy ra án mạng.]]></description>

    /**
     * Get url image from description
     * @param description
     * @return
     */
    public static String getImageUrl(String description) {
        String urlImage = "";
        String regularExpression = "src=\"(.*)\" ></a>";
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(description);

        while (matcher.find()) {
            urlImage = matcher.group(1);
            Log.d("DevDebug", "urlImage " + urlImage);
        }
        return urlImage;
    }

    /**
     *  Get url content from description
     * @param description
     * @return
     */
    public static String getContent(String description) {
        String content = "";
        String regularExpression = "</br>(.*)";
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(description);

        while (matcher.find()) {
            content = matcher.group(1);
            Log.d("DevDebug", "content " + content);
        }
        return content;
    }
}
