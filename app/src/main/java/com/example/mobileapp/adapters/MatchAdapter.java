package com.example.mobileapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileapp.R;
import com.example.mobileapp.models.Match;

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {
    private Context context;
    private List<Match> matchList;

    public MatchAdapter(Context context, List<Match> matchList) {
        this.context = context;
        this.matchList = matchList;
    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.match_item, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MatchViewHolder holder, int position) {
        Match match = matchList.get(position);
        holder.matchName.setText(match.name);
        holder.matchStatus.setText(match.status);
        holder.matchDateTime.setText(match.dateTimeGMT.replace("T", " ")); // Format for readability

        if (match.teamInfo != null && match.teamInfo.size() >= 2) {
            Glide.with(context).load(match.teamInfo.get(0).img).into(holder.team1Logo);
            Glide.with(context).load(match.teamInfo.get(1).img).into(holder.team2Logo);
        }
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView matchName, matchStatus, matchDateTime;
        ImageView team1Logo, team2Logo;

        public MatchViewHolder(View itemView) {
            super(itemView);
            matchName = itemView.findViewById(R.id.matchName);
            matchStatus = itemView.findViewById(R.id.matchStatus);
            matchDateTime = itemView.findViewById(R.id.matchDateTime);
            team1Logo = itemView.findViewById(R.id.team1Logo);
            team2Logo = itemView.findViewById(R.id.team2Logo);
        }
    }
}
