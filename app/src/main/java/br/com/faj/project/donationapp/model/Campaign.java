package br.com.faj.project.donationapp.model;

import android.graphics.Bitmap;

import java.util.Date;

public abstract class Campaign {
    private long id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private float percentage;

    public Campaign(long id, String name, String description, Date startDate, Date endDate, float percentage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percentage = percentage;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public float getPercentage() {
        return percentage;
    }

}
