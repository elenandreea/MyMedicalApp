package com.ibm.mymedicalapp.Interfaces;

import java.util.List;

import javax.xml.transform.Result;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DiagnoseService {
    @GET("/findDisease")
    Call<String> getDiagnose(@Query("array")int[] ids); //@Query("array")List<Integer> ids
}
