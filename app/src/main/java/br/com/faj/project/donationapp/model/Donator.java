package br.com.faj.project.donationapp.model;

public abstract class Donator {
    long id;
    String email;
    String password;
    String bio;
    Address address;

    public Donator(long id, String email, String password, String bio, Address address) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.address = address;
    }

    public Donator(String email, String password, String bio) {
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getBio() {
        return bio;
    }

    public Address getAddress() {
        return address;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
