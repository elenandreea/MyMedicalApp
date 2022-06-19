package com.ibm.mymedicalapp.Services;

import android.util.Log;

import com.ibm.mymedicalapp.Interfaces.DiagnoseService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServerServiceProvider {
    private final static String BASE_URL = "http://192.168.1.7:5000"; //192.168.1.7 127.0.0.1
    private static final String TAG = "Disease Prediction";

    private ServerServiceProvider(){}

    public static DiagnoseService createRetrofitService() {

        OkHttpClient okHttpClient = ServerServiceProvider.okHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(DiagnoseService.class);
    }

    public static OkHttpClient okHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
    }
}
