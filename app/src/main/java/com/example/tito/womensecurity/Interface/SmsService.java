package com.example.tito.womensecurity.Interface;

/**
 * Created by Tito on 01/04/2018.
 */

public interface SmsService {
    @GET("v1/sources?language=en")
    Call<Website> getSources();
}
