package br.com.faj.project.donationapp;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DonationUtil {
    public JSONObject parseProductDonationToJSON(long idDonator, Long idCampaign, JSONArray items) throws JSONException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", sdf.format(new Date()));
        jsonObject.put("donator", new JSONObject().put("id", idDonator).put("type", "Person"));
        if (idCampaign != -1) {
            jsonObject.put("campaign", new JSONObject().put("id", idCampaign).put("type", "MoneyCampaign"));
        }
        jsonObject.put("items", items);
        jsonObject.put("type", "ProductDonation");

        return jsonObject;


    }

    public JSONObject parseMoneyDonationToJSON(long idDonator, Long idCampaign, float quantity) throws JSONException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", sdf.format(new Date()));
        jsonObject.put("donator", new JSONObject().put("id", idDonator).put("type", "Person"));
        if (idCampaign != -1) {
            jsonObject.put("campaign", new JSONObject().put("id", idCampaign).put("type", "MoneyCampaign"));
        }
        jsonObject.put("quantity", quantity);
        jsonObject.put("type", "MoneyDonation");

        return jsonObject;
    }


}
