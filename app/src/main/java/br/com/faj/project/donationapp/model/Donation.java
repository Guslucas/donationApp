package br.com.faj.project.donationapp.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Donation {

    private long id;
    private Date date;
    private Campaign campaign;
    private Donator donator;

    public Donation(long id, Campaign campaign, Donator donator) {
        this.id = id;
        this.date = new Date();
        this.campaign = campaign;
        this.donator = donator;
    }

    public Donation(Campaign campaign, Donator donator) {
        this.date = new Date();
        this.campaign = campaign;
        this.donator = donator;
    }

    public long getId() {
        return id;
    }
    public Date getDate() {
        return date;
    }
    public Campaign getCampaign() {
        return campaign;
    }
    public Donator getDonator() {
        return donator;
    }
}
