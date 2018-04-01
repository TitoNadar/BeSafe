package com.example.tito.womensecurity.Common;

import com.example.tito.womensecurity.Interface.SmsService;
import com.example.tito.womensecurity.Remote.RetrofitClient;

/**
 * Created by Tito on 01/04/2018.
 */

public class Helper {
    private static String Base_Url="https://newsapi.org/";
    public static final String API_KEY="Your API key";

    public static SmsService getNewsService()
    {
        return RetrofitClient.getclient(Base_Url).create(SmsService.class);
    }
}
