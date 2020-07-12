package br.com.faj.project.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.nio.charset.StandardCharsets;

public class BankBill extends AppCompatActivity {

    private SharedPreferences loginInfoSP;
    private long idDonator;
    private SharedPreferences campaignInfo;
    private long idCampaign;
    private RequestQueue queue;
    private ConstraintLayout layout;
    private float valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_bill);

        loginInfoSP = getSharedPreferences("loginInfo", MODE_PRIVATE);

        campaignInfo = getSharedPreferences("campaignInfo", MODE_PRIVATE);
        idCampaign = campaignInfo.getLong("ID_CAMPAIGN", -1);

        layout = findViewById(R.id.bank_info_layout);

        valor = getIntent().getFloatExtra("valor", -1);

        queue = Volley.newRequestQueue(this);
    }

    public void finalizar (View v){

        if (loginInfoSP.contains("ID_DONATOR")) {
            idDonator = loginInfoSP.getLong("ID_DONATOR", -1);
        } else {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String url = getResources().getString(R.string.url);
        url += "/donate";
        Log.i("URL sendo usada:", url);

        JSONObject json = null;
        try {
            json = new DonationUtil().parseMoneyDonationToJSON(idDonator, idCampaign, valor);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("JSON sendo usado:", json.toString());

        final JSONObject finalJson = json;
        StringRequest requestProducts = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    donationResponse(new String(response.getBytes("ISO-8859-1"), "UTF-8"));
                } catch (Exception e) {
                    showError(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError(error);
                error.printStackTrace();
            }
        }) {
            @Override
            public byte[] getBody() {
                return finalJson.toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        queue.add(requestProducts);

    }

    private void donationResponse(String response) throws JSONException {

        JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
        if (!jsonObject.getString("status").equalsIgnoreCase("OK")) {
            showError(jsonObject.getString("errorMessage"));
            return;
        }

        Intent i = new Intent(this, ThanksDonation.class);
        startActivity(i);
        finishAffinity();
    }

    private void showError(Exception e) {
        Snackbar.make(layout, "Erro inesperado. Tente novamente.", BaseTransientBottomBar.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    private void showError(String errorMessage) {
        Snackbar.make(layout, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
    }
}
