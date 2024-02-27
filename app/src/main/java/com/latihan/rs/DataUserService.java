package com.latihan.rs;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataUserService {
    @GET("user")
    Call<List<UserData>>  getAllUser();
//    Call<JsonObject>  getAllUser();
}
