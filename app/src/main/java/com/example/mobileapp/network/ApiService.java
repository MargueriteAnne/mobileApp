package com.example.mobileapp.network;
import com.example.mobileapp.models.Player;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Query;
import retrofit2.http.GET;


public interface ApiService {
    @GET("players")
    Call<List<Player>> getPlayers(
            @Query("apikey") String apikey
    );

}
