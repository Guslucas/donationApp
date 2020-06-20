package br.com.faj.project.donationapp;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
        holder.mProgressBar.setProgress(Math.round(campaign.getPercentage()));
        holder.mProgressTV.setText(String.valueOf(Math.round(campaign.getPercentage())) + "%");

        Date endDate = campaign.getEndDate();
        endDate = new Date(new Date().getTime() + (2 * 24 * 60 * 60 * 1000));


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));


        long diff = endDate.getTime() - (new Date().getTime());
        int diffDays = (int) diff / (24 * 60 * 60 * 1000);

        int argb = 0;
        if (diffDays <= 7) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                argb = callingActivity.getResources().getColor(R.color.design_default_color_error, null);
            }
            diffDays = (diffDays == 0) ? 1 : diffDays;
            holder.mDate.setText(String.valueOf("Termina em " + diffDays + " dia" + ((diffDays > 1) ? "s" : "") + "!"));
        } else {
            holder.mDate.setText("Termina em " + sdf.format(endDate));
            argb = Color.BLACK;
        }

        holder.mDateIcon.setColorFilter(argb);
        holder.mDate.setTextColor(argb);



    }

    @Override
    public int getItemCount() {
        return campaignList.size();
    }

    public class CampaignItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mName;
        TextView mDescription;
        ProgressBar mProgressBar;
        TextView mProgressTV;
        TextView mDate;
        ImageView mDateIcon;


        CampaignItemHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.nameTV);
            mDescription = itemView.findViewById(R.id.descriptionTV);
            mProgressBar = itemView.findViewById(R.id.progressBar);
            mDate = itemView.findViewById(R.id.dateTV);
            mDateIcon = itemView.findViewById(R.id.dateIcon);
            mProgressTV = itemView.findViewById(R.id.progressTV);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            callingActivity.selectCampaign(getAdapterPosition());
        }
    }
}
