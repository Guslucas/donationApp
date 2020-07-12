package br.com.faj.project.donationapp.model;


import java.util.Date;

public class MoneyCampaign extends Campaign {

    //private float goal; comentado pelo momento

    public MoneyCampaign(long id, String name, String description, Date startDate, Date endDate, float percentage) {
        super(id, name, description, startDate, endDate, percentage);
    }

}
