package br.com.faj.project.donationapp.model;

import android.graphics.Bitmap;

public class Product {
    private String name;
    private long id;
    private Bitmap image;
    private String type;

    public Product(String name, long id, Bitmap image, String type) {
        this.name = name;
        this.id = id;
        this.image = image;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getType() {
        return type;
    }
}
