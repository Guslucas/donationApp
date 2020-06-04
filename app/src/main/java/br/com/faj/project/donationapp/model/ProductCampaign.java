package br.com.faj.project.donationapp.model;

import java.sql.Date;

public class ProductCampaign extends Campaign {


    public ProductCampaign(long id, String name, String description, java.util.Date startDate, java.util.Date endDate, float percentage) {
        super(id, name, description, startDate, endDate, percentage);
    }

    public ProductCampaign(String name, String description, Date startDate, Date endDate) {
        super(name, description, startDate, endDate);
    }
}
