package br.com.faj.project.donationapp.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Item {

    private long id;
    private Date shelfLife;
    private Product product;
    private String barCode;
    private String brand;

    public Item(Product product) {
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public Date getShelfLife() {
        return shelfLife;
    }

    public Product getProduct() {
        return product;
    }


    public String getBarCode() {
        return barCode;
    }

    public String getBrand() {
        return brand;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("product", new JSONObject().put("id", product.getId()));
        return jsonObject;
    }
}
