package br.com.faj.project.donationapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.faj.project.donationapp.model.Campaign;

class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.CampaignItemHolder> {

    private List<Campaign> campaignList;
    private Campaigns callingActivity;

    CampaignAdapter(List<Campaign> campaignList, Campaigns callingActivity) {
        this.campaignList = campaignList;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public CampaignItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.campaign_item, parent, false);
        return new CampaignItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CampaignItemHolder holder, int position) {
        Campaign campaign = campaignList.get(position);
        holder.mName.setText(campaign.getName());
        holder.mDescription.setText(campaign.getDescription());
        //holder.mImage.setImageBitmap(campaign.getImage());
        holder.mProgressBar.setProgress(Math.round(campaign.getPercentage()));
    }

    @Override
    public int getItemCount() {
        return campaignList.size();
    }

    public class CampaignItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImage;
        TextView mName;
        TextView mDescription;
        ProgressBar mProgressBar;

        CampaignItemHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.imageView);
            mName = itemView.findViewById(R.id.nameTV);
            mDescription = itemView.findViewById(R.id.descriptionTV);
            mProgressBar = itemView.findViewById(R.id.progressBar);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            callingActivity.
                    selectCampaign(getAdapterPosition());
        }
    }
}
