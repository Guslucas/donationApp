package br.com.faj.project.donationapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDonation extends Donation {
    private List<Item> items = new ArrayList<>();

    public ProductDonation(long id, Campaign campaign, Donator donator, List<Item> items) {
        super(id, campaign, donator);
        this.items = items;
    }

    public ProductDonation(Campaign campaign, Donator donator, List<Item> items) {
        super(campaign, donator);
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

}
