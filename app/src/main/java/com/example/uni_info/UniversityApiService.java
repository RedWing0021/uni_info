package com.example.uni_info;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UniversityApiService {
    @GET("search")
    Call<List<University>> getUniversities();
}

