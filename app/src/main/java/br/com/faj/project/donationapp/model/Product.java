package br.com.faj.project.donationapp.model;

import android.graphics.Bitmap;

public class Product {
    private long id;
    private String name;
    private String type;
    private Bitmap image;

    public Product(String name, long id, Bitmap image, String type) {
        this.name = name;
        this.id = id;
        this.image = image;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Bitmap getImage() {
        return image;
    }
}
