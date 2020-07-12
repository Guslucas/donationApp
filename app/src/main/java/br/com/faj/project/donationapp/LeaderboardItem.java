package br.com.faj.project.donationapp;

class LeaderboardItem {
    private long donatorId;
    private String name;
    private int quantitydDonation;

    public LeaderboardItem(long donatorId, String name, int quantitydDonation) {
        this.donatorId = donatorId;
        this.name = name;
        this.quantitydDonation = quantitydDonation;
    }

    public LeaderboardItem(String name, int quantitydDonation) {
        this.name = name;
        this.quantitydDonation = quantitydDonation;
    }

    public long getDonatorId() {
        return donatorId;
    }

    public String getName() {
        return name;
    }

    public int getQuantitydDonation() {
        return quantitydDonation;
    }
}
