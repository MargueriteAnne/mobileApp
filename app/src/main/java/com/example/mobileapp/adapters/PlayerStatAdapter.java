package com.example.mobileapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.models.PlayerStat;

import java.util.List;

public class PlayerStatAdapter extends RecyclerView.Adapter<PlayerStatAdapter.StatViewHolder> {
    private List<PlayerStat> statList;

    public PlayerStatAdapter(List<PlayerStat> statList) {
        this.statList = statList;
    }

    @NonNull
    @Override
    public StatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stat, parent, false);
        return new StatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatViewHolder holder, int position) {
        PlayerStat stat = statList.get(position);


        holder.statTypeTextView.setText(stat.fn);
        holder.statValueTextView.setText(stat.value);
        holder.matchTypeTextView.setText(stat.matchtype);
    }

    @Override
    public int getItemCount() {
        return statList.size();  // Return the total count of stats
    }

    // ViewHolder class for binding the stat data
    static class StatViewHolder extends RecyclerView.ViewHolder {
        TextView statTypeTextView, statValueTextView, matchTypeTextView;

        public StatViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initializing item_stat.xml
            statTypeTextView = itemView.findViewById(R.id.statTypeTextView);
            statValueTextView = itemView.findViewById(R.id.statValueTextView);
            matchTypeTextView = itemView.findViewById(R.id.matchTypeTextView);
        }
    }
}
