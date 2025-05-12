package com.example.mobileapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileapp.adapters.PlayerStatAdapter;
import com.example.mobileapp.models.PlayerInfoResponse;
import com.example.mobileapp.network.ApiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayerProfileFragment extends Fragment {

    private static final String BASE_URL = "https://api.cricapi.com/v1/";
    //private static final String API_KEY = "74aa5fd7-9b7d-4ae1-bf97-f21e1b03bd03";
    private static final String API_KEY = "4c493822-a935-49eb-9d2b-10dd19c69bff";


    private TextView nameView, countryView, roleView;
    private ImageView playerImage;
    private RecyclerView statsRecyclerView;
    private ImageButton backBtn;

    private String playerId;

    public PlayerProfileFragment() {
        // Required empty public constructor
    }

    public static PlayerProfileFragment newInstance(String playerId) {
        PlayerProfileFragment fragment = new PlayerProfileFragment();
        Bundle args = new Bundle();
        args.putString("player_id", playerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            playerId = getArguments().getString("player_id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_player_profile, container, false);

        nameView = view.findViewById(R.id.nameTextView);
        countryView = view.findViewById(R.id.countryTextView);
        roleView = view.findViewById(R.id.roleTextView);
        playerImage = view.findViewById(R.id.profileImageView);
        statsRecyclerView = view.findViewById(R.id.statsRecyclerView);
        statsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        backBtn = view.findViewById(R.id.back_home);
        backBtn.setOnClickListener(v -> {
            //restore BottomNavigation
            BottomNavigationView navBar = requireActivity().findViewById(R.id.nav_bar);
            if (navBar != null) navBar.setVisibility(View.VISIBLE);

            View detailContainer = requireActivity().findViewById(R.id.detail_fragment_container);
            if (detailContainer != null) {
                // Set back to default background
                detailContainer.setBackgroundColor(Color.TRANSPARENT);
            }

            requireActivity().onBackPressed();
        });

        if (playerId == null || playerId.isEmpty()) {
            Toast.makeText(requireContext(), "Invalid Player ID", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
        } else {
            fetchPlayerData();
        }

        return view;
    }

    private void fetchPlayerData() {
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

                    Glide.with(requireContext())
                            .load(player.playerImg)
                            .into(playerImage);

                    if (player.stats != null && !player.stats.isEmpty()) {
                        PlayerStatAdapter adapter = new PlayerStatAdapter(player.stats);
                        statsRecyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(requireContext(), "No stats available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load player data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlayerInfoResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("PLAYER_PROFILE", t.getMessage(), t);
            }
        });
    }
}
