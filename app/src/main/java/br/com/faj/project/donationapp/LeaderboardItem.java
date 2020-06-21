package br.com.faj.project.donationapp;

class LeaderboardItem {
    private int id;
    private String name;
    private int quantitydDonation;

    public LeaderboardItem(int id, String name, int quantitydDonation) {
        this.id = id;
        this.name = name;
        this.quantitydDonation = quantitydDonation;
    }

    public LeaderboardItem(String name, int quantitydDonation) {
        this.name = name;
        this.quantitydDonation = quantitydDonation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantitydDonation() {
        return quantitydDonation;
    }
}
