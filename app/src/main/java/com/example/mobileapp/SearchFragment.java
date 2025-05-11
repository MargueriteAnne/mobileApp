package com.example.mobileapp;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.widget.Toast;


import com.example.mobileapp.adapters.CricketPlayerAdapter;
import com.example.mobileapp.models.Player;
import com.example.mobileapp.models.PlayerResponse;
import com.example.mobileapp.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {
    EditText searchInput;
    RecyclerView searchResults;
    CricketPlayerAdapter adapter;
   List<Player> allPlayers = new ArrayList<>();
    List<Player> filteredPlayers = new ArrayList<>();



    public SearchFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchInput = view.findViewById(R.id.search_input);
        searchResults = view.findViewById(R.id.search_results);

        adapter = new CricketPlayerAdapter(filteredPlayers, requireActivity().getSupportFragmentManager());
        searchResults.setLayoutManager(new LinearLayoutManager(getContext()));
        searchResults.setAdapter(adapter);

        loadPlayersFromApi();



        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPlayers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void loadPlayersFromApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.cricapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<PlayerResponse> call = apiService.getPlayers("74aa5fd7-9b7d-4ae1-bf97-f21e1b03bd03");

        call.enqueue(new Callback<PlayerResponse>() {
            @Override
            public void onResponse(Call<PlayerResponse> call, Response<PlayerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allPlayers.clear();
                    allPlayers.addAll(response.body().getData());

                    filteredPlayers.clear();
                    filteredPlayers.addAll(allPlayers);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to load players", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlayerResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterPlayers(String query) {
        filteredPlayers.clear();
        for (Player player : allPlayers) {
            if (player.getName().toLowerCase().contains(query.toLowerCase()) ||
                    player.getCountry().toLowerCase().contains(query.toLowerCase())) {
                filteredPlayers.add(player);
            }
        }
        adapter.notifyDataSetChanged();
    }
}

