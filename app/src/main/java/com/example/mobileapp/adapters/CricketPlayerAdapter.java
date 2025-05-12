package com.example.mobileapp.adapters;

import com.example.mobileapp.PlayerProfileFragment;
import com.example.mobileapp.R;
import  com.example.mobileapp.models.Player;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CricketPlayerAdapter extends RecyclerView.Adapter<CricketPlayerAdapter.ViewHolder> {

    private List<Player> playerList;
    FragmentManager fragmentManager;
    public CricketPlayerAdapter(List<Player> playerList, FragmentManager fragmentManager) {
        this.playerList = playerList;
        this.fragmentManager = fragmentManager;
    }

    public void setPlayers(List<Player> playerList){
        this.playerList =playerList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public CricketPlayerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_player, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CricketPlayerAdapter.ViewHolder holder, int position) {
        Player player = playerList.get(position);
        holder.name.setText(player.getName());
        holder.country.setText(player.getCountry());


        //handle the nextArrow
        holder.nextArrow.setOnClickListener(v -> {
            PlayerProfileFragment fragment = new PlayerProfileFragment();

            //pass data via bundle
            Bundle bundle = new Bundle();
            bundle.putString("player_id", player.getId());
            fragment.setArguments(bundle);

            // Hide BottomNavigationView
            BottomNavigationView navBar = ((Activity) v.getContext()).findViewById(R.id.nav_bar);
            if (navBar != null) navBar.setVisibility(View.GONE);

            View container = ((Activity) v.getContext()).findViewById(R.id.detail_fragment_container);
            if (container != null) {
                container.setBackgroundColor(Color.WHITE);
            }

            fragmentManager.beginTransaction()
                    .replace(R.id.detail_fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, country;
        ImageView nextArrow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.player_name);
            country = itemView.findViewById(R.id.player_country);
            nextArrow = itemView.findViewById(R.id.right_nextArrow);
        }
    }
}