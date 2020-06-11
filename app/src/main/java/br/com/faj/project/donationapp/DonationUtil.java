package br.com.faj.project.donationapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DonationUtil {
    public JSONObject parseToJSON(long idDonator, Long idCampaign, JSONArray items) throws JSONException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", sdf.format(new Date()));
        jsonObject.put("donator", new JSONObject().put("id", idDonator));
        if (idCampaign != -1) {
            jsonObject.put("campaign", new JSONObject().put("id", idCampaign));
        }
        jsonObject.put("items", items);

        return jsonObject;


    }
}
