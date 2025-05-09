package com.example.mobileapp.network;

import com.example.mobileapp.models.PlayerResponse;
import com.example.mobileapp.models.PlayerInfoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("players")
    Call<PlayerResponse> getPlayers(
            @Query("apikey") String apikey
    );

    @GET("players_info")
    Call<PlayerInfoResponse> getPlayerInfo(
            @Query("apikey") String apikey,
            @Query("id") String playerId
    );
}
