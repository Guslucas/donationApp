package br.com.faj.project.donationapp.model;

import java.util.Date;

public class MoneyDonation extends Donation {

    private float quantity;

    public MoneyDonation(long id, Campaign campaign, Donator donator, float quantity) {
        super(id, campaign, donator);
        this.quantity = quantity;
    }

    public MoneyDonation(Campaign campaign, Donator donator, float quantity) {
        super(campaign, donator);
        this.quantity = quantity;
    }

    public float getQuantity() {
        return quantity;
    }
}
