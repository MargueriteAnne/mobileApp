package com.example.mobileapp.adapters;
import com.example.mobileapp.PlayerProfileFragment;
import com.example.mobileapp.R;
import  com.example.mobileapp.models.Player;

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
    public CricketPlayerAdapter(List<Player> playerList) {
        this.playerList = playerList;
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

        /*holder.itemView.setOnClickListener(v -> {
            android.content.Context context = holder.itemView.getContext();
            android.content.Intent intent = new android.content.Intent(context, PlayerProfileFragment.class);
            intent.putExtra("player_name", player.getName());
            intent.putExtra("player_country", player.getCountry());
            context.startActivity(intent);
        });*/

        holder.nextArrow.setOnClickListener(v -> {
            android.content.Context context = holder.itemView.getContext();
            android.content.Intent intent = new android.content.Intent(context, PlayerProfileFragment.class);
            intent.putExtra("player_id", player.getId());
            context.startActivity(intent);
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