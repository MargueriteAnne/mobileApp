package com.example.mobileapp.network;
import com.example.mobileapp.models.PlayerResponse;

import retrofit2.Call;
import retrofit2.http.Query;
import retrofit2.http.GET;


public interface ApiService {
    @GET("players")
    Call<PlayerResponse> getPlayers(
            @Query("apikey") String apikey
    );

}
