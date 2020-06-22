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

import java.text.SimpleDateFormat;
import java.util.List;

class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryItemHolder> {

    private List<HistoryItem> itemList;
    private History callingActivity;
    private Integer donatorId;

    public HistoryAdapter(List<HistoryItem> itemList, History callingActivity, Integer donatorId) {
        this.itemList = itemList;
        this.callingActivity = callingActivity;
        this.donatorId = donatorId;
    }

    @NonNull
    @Override
    public HistoryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemHolder holder, int position) {
        HistoryItem item = itemList.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        holder.mDate.setText(sdf.format(item.getDate()));
        holder.mType.setText(item.getType());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class HistoryItemHolder extends RecyclerView.ViewHolder {
        TextView mType;
        TextView mDate;

        public HistoryItemHolder(@NonNull View itemView) {
            super(itemView);
            mType = itemView.findViewById(R.id.tipoDonation);
            mDate = itemView.findViewById(R.id.dataDonation);

        }
    }
}
