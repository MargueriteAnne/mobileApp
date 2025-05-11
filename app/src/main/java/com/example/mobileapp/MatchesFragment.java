package com.example.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class MatchesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MatchAdapter matchAdapter;
    private static final String BASE_URL = "https://api.cricapi.com/v1/";
    private static final String API_KEY = "74aa5fd7-9b7d-4ae1-bf97-f21e1b03bd03";

    public MatchesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matches, container, false);

        recyclerView = view.findViewById(R.id.RecyclerViewMatches);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        ImageButton backBtn = view.findViewById(R.id.back_home);
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

        fetchMatches();

        return view;
    }

    private void fetchMatches() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        //Make the API call
        ApiService apiService = retrofit.create(ApiService.class);
        Call<MatchResponse> call = apiService.getMatches(API_KEY);
        call.enqueue(new Callback<MatchResponse>() {
            @Override
            public void onResponse(Call<MatchResponse> call, Response<MatchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Match> matchList = response.body().data;
                    matchAdapter = new MatchAdapter(getContext(), matchList);
                    recyclerView.setAdapter(matchAdapter);
                } else {
                    Toast.makeText(getContext(), "Failed to load matches", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MatchResponse> call, Throwable t) {
                Log.e("Matches", "API call failed", t);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
