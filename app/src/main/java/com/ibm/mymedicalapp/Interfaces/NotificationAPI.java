package com.ibm.mymedicalapp.Interfaces;

import com.ibm.mymedicalapp.Models.Constants;
import com.ibm.mymedicalapp.Models.PushNotification;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificationAPI {
    @Headers({"Authorization: key=" + Constants.SERVER_KEY, "Content-Type:" + Constants.CONTENT_TYPE})
    @POST("fcm/send")
    Call<Response> postNotification(@Body PushNotification data);
}
