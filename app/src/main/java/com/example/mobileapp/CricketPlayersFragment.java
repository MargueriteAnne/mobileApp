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

import com.example.mobileapp.adapters.CricketPlayerAdapter;
import com.example.mobileapp.models.Player;
import com.example.mobileapp.models.PlayerResponse;
import com.example.mobileapp.network.ApiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CricketPlayersFragment extends Fragment {

    private RecyclerView recyclerViewPlayers;
    private CricketPlayerAdapter adapter;
    private static final String BASE_URL = "https://api.cricapi.com/v1/";
    private static final String API_KEY = "74aa5fd7-9b7d-4ae1-bf97-f21e1b03bd03";

    public CricketPlayersFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cricket_player, container, false);

        recyclerViewPlayers = view.findViewById(R.id.recyclerViewPlayers);
        recyclerViewPlayers.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CricketPlayerAdapter(new ArrayList<>(), requireActivity().getSupportFragmentManager());
        recyclerViewPlayers.setAdapter(adapter);

        ImageButton backBtn = view.findViewById(R.id.back_home);
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });


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
                    adapter.setPlayers(playerList);
                } else {
                    Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlayerResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API_ERROR", t.getMessage(), t);
            }
        });

        BottomNavigationView navBar = view.findViewById(R.id.nav_bar);
        navBar.setOnItemSelectedListener(item -> {

            // Hide RecyclerView
            view.findViewById(R.id.recyclerViewPlayers).setVisibility(View.GONE);

            // Show Fragment container
            view.findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);

            if (item.getItemId() == R.id.nav_search) {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new SearchFragment())
                        .commit();
                return true;
            }
            return false;
        });

        return view;
    }
}
