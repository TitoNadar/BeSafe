package com.example.tito.womensecurity.Remote;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Tito on 01/04/2018.
 */

public class RetrofitClient {
    private static Retrofit retrofit=null;
    public static Retrofit getclient(String baseUrl)
    {
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
