package br.com.faj.project.donationapp.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Donator {
    private long id;
    private String email;
    private String password;
    private String bio;
    private Address address;

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
    }

    //TODO testes, pode tirar dps
    public Donator(long id) {
        this.id = id;
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

    public void setAddress(Address address) {
        this.address = address;
    }

    public JSONObject toJSON() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        jsonObject.put("bio", bio);
        jsonObject.put("address", address.toJSON());

        return jsonObject;
    }
}
