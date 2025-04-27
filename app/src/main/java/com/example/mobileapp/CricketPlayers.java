package com.example.mobileapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mobileapp.adapters.CricketPlayerAdapter;
import com.example.mobileapp.models.Player;
import com.example.mobileapp.models.PlayerResponse;
import com.example.mobileapp.network.ApiService;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CricketPlayers extends AppCompatActivity {

    private RecyclerView recyclerViewPlayers;
    private CricketPlayerAdapter adapter;
    private static final String BASE_URL = "https://api.cricapi.com/v1/";
    private static final String API_KEY = "74aa5fd7-9b7d-4ae1-bf97-f21e1b03bd03";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cricket_players);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerViewPlayers = findViewById(R.id.recyclerViewPlayers);
        recyclerViewPlayers.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        //Make the API call

        Call<PlayerResponse> call = apiService.getPlayers(API_KEY);
        call.enqueue(new Callback<PlayerResponse>() {
            @Override
            public void onResponse(Call<PlayerResponse> call, Response<PlayerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Player> playerList = response.body().getData();
                    adapter = new CricketPlayerAdapter(playerList);
                    recyclerViewPlayers.setAdapter(adapter);
                } else {
                    Toast.makeText(CricketPlayers.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlayerResponse> call, Throwable t) {
                Toast.makeText(CricketPlayers.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API_ERROR", t.getMessage(), t);
            }
        });
    }
}