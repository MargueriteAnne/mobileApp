package com.example.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileapp.adapters.PlayerStatAdapter;
import com.example.mobileapp.network.ApiService;
//import com.bumptech.glide.Glide;
import com.example.mobileapp.models.PlayerInfoResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayerProfileActivity extends AppCompatActivity {

    String BASE_URL = "https://api.cricapi.com/v1/";
    String API_KEY = "74aa5fd7-9b7d-4ae1-bf97-f21e1b03bd03";

    RecyclerView statsRecyclerView;

    TextView nameView, countryView, roleView;
    ImageView playerImage;
    PlayerStatAdapter statsAdapter;
    ImageButton backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);

        nameView = findViewById(R.id.nameTextView);
        countryView = findViewById(R.id.countryTextView);
        roleView = findViewById(R.id.roleTextView);
        playerImage = findViewById(R.id.profileImageView);

        statsRecyclerView = findViewById(R.id.statsRecyclerView);
        statsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        backBtn = findViewById(R.id.back_home);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayerProfileActivity.this, CricketPlayers.class);
                startActivity(intent);
            }
        });

        String playerId = getIntent().getStringExtra("player_id");

        if (playerId == null || playerId.isEmpty()) {
            Toast.makeText(this, "Invalid Player ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();





        ApiService apiService = retrofit.create(ApiService.class);
        Call<PlayerInfoResponse> call = apiService.getPlayerInfo(API_KEY, playerId);

        call.enqueue(new Callback<PlayerInfoResponse>() {
            @Override
            public void onResponse(Call<PlayerInfoResponse> call, Response<PlayerInfoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    var player = response.body().data;
                    nameView.setText(player.name);
                    countryView.setText(player.country);
                    roleView.setText(player.role);


                  Glide.with(PlayerProfileActivity.this)
                            .load(player.playerImg)
                            .into(playerImage);


                  //PlayerStatAdapter into RecyclerView
                  if (player.stats != null && !player.stats.isEmpty()) {
                      PlayerStatAdapter adapter = new PlayerStatAdapter(player.stats);
                      statsRecyclerView.setAdapter(adapter);
                  } else {
                      Toast.makeText(PlayerProfileActivity.this, "No stats available", Toast.LENGTH_SHORT).show();
                  }
                } else {
                    Toast.makeText(PlayerProfileActivity.this, "Failed to load player data", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PlayerInfoResponse> call, Throwable t) {
                Toast.makeText(PlayerProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("PLAYER_PROFILE", t.getMessage(), t);
            }
        });
    }
}
