package br.com.faj.project.donationapp.model;

import android.graphics.Bitmap;

public class Product {
    private String name;
    private String description;
    private Bitmap image;
    private String type;

    public Product(String name, String description, Bitmap image, String type) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getType() {
        return type;
    }
}
