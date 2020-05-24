package br.com.faj.project.donationapp.model;

import android.graphics.Bitmap;

public class Campaign {
    private String name;
    private String description;
    private Bitmap image;
    private int percentage;

    public Campaign(String nome, String description) {
        this.name = nome;
        this.description = description;
    }

    public Campaign(String nome, String description, Bitmap image, int porcentagemConclusao) {
        this.name = nome;
        this.description = description;
        this.image = image;
        if (porcentagemConclusao < 0 || porcentagemConclusao > 100) throw new IllegalArgumentException("Porcentagem inválida.");
        this.percentage = porcentagemConclusao;
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

    public int getPercentage() {
        return percentage;
    }
}
