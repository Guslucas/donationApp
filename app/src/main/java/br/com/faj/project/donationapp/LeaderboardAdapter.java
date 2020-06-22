package br.com.faj.project.donationapp;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardItemHolder> {

    private List<LeaderboardItem> itemList;
    private Leaderboard callingActivity;
    private Integer donatorId;

    public LeaderboardAdapter(List<LeaderboardItem> itemList, Leaderboard callingActivity, Integer donatorId) {
        this.itemList = itemList;
        this.callingActivity = callingActivity;
        this.donatorId = donatorId;
    }

    @NonNull
    @Override
    public LeaderboardItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false);
        return new LeaderboardItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardItemHolder holder, int position) {
        LeaderboardItem item = itemList.get(position);
        if (position == 0) {
            holder.mCrownIV.setVisibility(View.VISIBLE);

            int color;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                color = callingActivity.getResources().getColor(android.R.color.holo_orange_light, null);
                holder.mCrownIV.getDrawable().setTint(color);
            }
        } else if (position == 1) {
            holder.mCrownIV.setVisibility(View.VISIBLE);
            int color;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                color = callingActivity.getResources().getColor(android.R.color.holo_orange_dark, null);
                holder.mCrownIV.getDrawable().setTint(color);
            }
        } else if (position == 2) {
            holder.mCrownIV.setVisibility(View.VISIBLE);
            int color;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                color = callingActivity.getResources().getColor(android.R.color.darker_gray, null);
                holder.mCrownIV.getDrawable().setTint(color);
            }
        }
        else {
            holder.mCrownIV.setVisibility(View.INVISIBLE);
        }

        holder.mNameTV.setText(item.getName());
        holder.mQuantity.setText(String.valueOf(item.getQuantitydDonation()));
        if (item.getDonatorId() == donatorId) {
            View itemView = holder.itemView;
            CardView viewById = itemView.findViewById(R.id.card);
            //viewById.setCardBackgroundColor(Color.argb(100, 100, 25, 100));

                int color = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                color = callingActivity.getResources().getColor(R.color.colorPrimary, null);

                color = Color.argb(Color.alpha(color) - 100, Color.red(color), Color.green(color), Color.blue(color));
            }
            viewById.setElevation(0);
            viewById.getBackground().setTint(color);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class LeaderboardItemHolder extends RecyclerView.ViewHolder {
        TextView mNameTV;
        TextView mQuantity;
        ImageView mCrownIV;

        public LeaderboardItemHolder(@NonNull View itemView) {
            super(itemView);
            mNameTV = itemView.findViewById(R.id.nomeTV);
            mQuantity = itemView.findViewById(R.id.qtdDonations);
            mCrownIV = itemView.findViewById(R.id.crownIV);
        }
    }
}
