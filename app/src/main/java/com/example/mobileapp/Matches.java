/*package com.example.mobileapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.adapters.MatchAdapter;
import com.example.mobileapp.models.Match;
import com.example.mobileapp.models.MatchResponse;
import com.example.mobileapp.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Matches extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MatchAdapter matchAdapter;
    private static final String BASE_URL = "https://api.cricapi.com/v1/";
    private static final String API_KEY = "74aa5fd7-9b7d-4ae1-bf97-f21e1b03bd03";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        recyclerView = findViewById(R.id.RecyclerViewMatches); // Make sure this ID exists
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageButton backBtn = findViewById(R.id.back_home);
        backBtn.setOnClickListener(v -> finish());

        fetchMatches();
    }

    private void fetchMatches() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<MatchResponse> call = apiService.getMatches(API_KEY);

        call.enqueue(new Callback<MatchResponse>() {
            @Override
            public void onResponse(Call<MatchResponse> call, Response<MatchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Match> matchList = response.body().data;
                    matchAdapter = new MatchAdapter(Matches.this,matchList);
                    recyclerView.setAdapter(matchAdapter);
                } else {
                    Toast.makeText(Matches.this, "Failed to load matches", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MatchResponse> call, Throwable t) {
                Log.e("Matches", "API call failed", t);
                Toast.makeText(Matches.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}*/
