package com.shuvam.recsnd;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("json")
    Call<QueryResponse> getHospitals(@Query("location")String location,@Query("radius") String radius,@Query("type") String type,@Query("key") String key);
 
    /*@GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);*/
}