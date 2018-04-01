package com.example.tito.womensecurity.Common;

import com.example.tito.womensecurity.Interface.SmsService;
import com.example.tito.womensecurity.Remote.RetrofitClient;

/**
 * Created by Tito on 01/04/2018.
 */

public class Helper {
    private static String Base_Url="https://instantalerts.co/api/web/send/";
    public static final String KEY="64200da0029b2it409pl98r58wotqy6v";

    public static SmsService getSmsService()
    {
        return RetrofitClient.getclient(Base_Url).create(SmsService.class);
    }
    public static String getApiUrl()
    {
        StringBuilder stringBuilder=new StringBuilder("https://instantalerts.co/api/web/send/?apikey=");
        stringBuilder.append(KEY).append("&sender=").append("SEDEMO").append("&to=").append("9004462645").append("&message=Test+message").append("&format=json");
        return stringBuilder.toString();
    }
}
