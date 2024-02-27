package com.latihan.rs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ObatService {
    @GET("obat")
    Call<List<ModelObat>> getObatList();
}
