package br.com.faj.project.donationapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.faj.project.donationapp.model.Campaign;

public class Campaigns extends AppCompatActivity {

    List<Campaign> mCampaignList = new ArrayList<Campaign>();
    RecyclerView mCampaignRecycler;
    CampaignAdapter mCampaignAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaigns);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadCampaigns();

        //getCampaigns
        mCampaignRecycler = findViewById(R.id.campaignsRecycler);
        mCampaignAdapter = new CampaignAdapter(mCampaignList, this);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(getApplicationContext());
        mCampaignRecycler.setLayoutManager(layoutManager);
        mCampaignRecycler.setAdapter(mCampaignAdapter);
        mCampaignRecycler.setItemAnimator(new DefaultItemAnimator());
        mCampaignRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void loadCampaigns() {
        Bitmap night = BitmapFactory.decodeResource(this.getResources(), R.drawable.night);
        mCampaignList.add(new Campaign("Calm Nights", "Help us donation this.", night, 30));
        mCampaignList.add(new Campaign("DARK Nights", "Help us donation this.", night, 30));
    }

    public void selectCampaign(int adapterPosition) {
        Campaign c = mCampaignList.get(adapterPosition);
        //Toast.makeText(getApplicationContext(), "A campanha que você vai doar é " + c.getName(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, Products.class);
        i.putExtra("CAMPAIGN_ID", c.getName());
        startActivity(i);
    }
}
