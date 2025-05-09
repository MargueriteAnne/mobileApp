package com.example.mobileapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapp.network.ApiService;
import com.bumptech.glide.Glide;
import com.example.mobileapp.models.PlayerInfoResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayerProfileFragment extends AppCompatActivity {

    TextView nameView, countryView, roleView;
    ImageView playerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_player_profile);

        nameView = findViewById(R.id.nameTextView);
        countryView = findViewById(R.id.countryTextView);
        roleView = findViewById(R.id.roleTextView);
        playerImage = findViewById(R.id.profileImageView);

        String playerId = getIntent().getStringExtra("player_id");

        if (playerId != null) {
            fetchPlayerInfo(playerId);
        }
    }

    private void fetchPlayerInfo(String playerId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.cricapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<PlayerInfoResponse> call = apiService.getPlayerInfo("74aa5fd7-9b7d-4ae1-bf97-f21e1b03bd03", playerId);

        call.enqueue(new Callback<PlayerInfoResponse>() {
            @Override
            public void onResponse(Call<PlayerInfoResponse> call, Response<PlayerInfoResponse> response) {
                if (response.isSuccessful()) {
                    var player = response.body().data;
                    nameView.setText(player.name);
                    countryView.setText(player.country);
                    roleView.setText(player.role);


                    Glide.with(PlayerProfileFragment.this)
                            .load(player.playerImg)
                            .into(playerImage);
                }
            }

            @Override
            public void onFailure(Call<PlayerInfoResponse> call, Throwable t) {
                Toast.makeText(PlayerProfileFragment.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
