package com.example.tito.womensecurity.Interface;

import com.example.tito.womensecurity.Modal.Response;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Url;

/**
 * Created by Tito on 01/04/2018.
 */

public interface SmsService {
    @GET("")
    Call<Response> getResponse(@Url String url);
}
