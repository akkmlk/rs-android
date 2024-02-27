package com.latihan.rs;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("register")
    Call<RegisterResponse> userRegister(@Body RegisterRequest registerRequest);
}
