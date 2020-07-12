package br.com.faj.project.donationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import br.com.faj.project.donationapp.model.Campaign;
import br.com.faj.project.donationapp.model.MoneyCampaign;
import br.com.faj.project.donationapp.model.ProductCampaign;

public class Campaigns extends AppCompatActivity {

    List<Campaign> mCampaignList = new ArrayList<Campaign>();
    RecyclerView mCampaignRecycler;
    CampaignAdapter mCampaignAdapter;

    RequestQueue queue;
    private ConstraintLayout campaignsLayout;

    private SharedPreferences campaignInfo;
    private SharedPreferences.Editor campaignInfoEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaigns);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        queue = Volley.newRequestQueue(this);



        //getCampaigns
        mCampaignRecycler = findViewById(R.id.campaignsRecycler);
        mCampaignAdapter = new CampaignAdapter(mCampaignList, this);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(getApplicationContext());
        mCampaignRecycler.setLayoutManager(layoutManager);
        mCampaignRecycler.setAdapter(mCampaignAdapter);
        mCampaignRecycler.setItemAnimator(new DefaultItemAnimator());
        mCampaignRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        loadCampaigns();

        final FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (getApplicationContext(), Products.class);
                startActivity(i);
            }
        });

        campaignsLayout = findViewById(R.id.campaignsLayout);

        campaignInfo = getSharedPreferences("campaignInfo", MODE_PRIVATE);
        campaignInfoEditor = campaignInfo.edit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCampaigns();
        campaignInfoEditor.clear();
        campaignInfoEditor.apply();
    }

    private void loadCampaigns() {
       /* Bitmap night = BitmapFactory.decodeResource(this.getResources(), R.drawable.night);
       mCampaignList.add(new MoneyCampaign("Calm Nights", "Help us donation this.", new Date(2020, 01, 01), new Date(2020, 04, 01)));
       mCampaignList.add(new ProductCampaign("DARK Nights", "Help us donation this.", new Date(2020, 01, 01), new Date(2020, 04, 01)));*/

        String url = getResources().getString(R.string.url);
        url += "/campaign";
        Log.i("URL sendo usada", url);

        StringRequest requestCampaigns = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    listCampaigns(new String(response.getBytes("ISO-8859-1"), "UTF-8"));
                } catch (Exception e) {
                    showError(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError(error);
            }
        });

        queue.add(requestCampaigns);

        //TODO código apenas de teste
        /* String jsonArray = "{\"status\":\"OK\", \"errorMessage\":null, \"object\":[{\"type\":\"ProductCampaign\",\"id\":1,\"name\":\"Prod Tranquilas\",\"description\":\"Ajude doando.\",\"startDate\":\"2020-04-25\",\"endDate\":\"2020-06-15\",\"percentage\":0.0},{\"type\":\"MoneyCampaign\",\"id\":2,\"name\":\"Money Tranquilas\",\"description\":\"Ajude doando.\",\"startDate\":\"2020-04-25\",\"endDate\":\"2020-06-15\",\"percentage\":0.0}]}";
        try {
            listCampaigns(jsonArray);
        } catch (JSONException e) {
            showError(e);
            e.printStackTrace();
        } */

    }

    public void listCampaigns(String response) throws JSONException {

        JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
        if (!jsonObject.getString("status").equalsIgnoreCase("OK")) {
            showError(jsonObject.getString("errorMessage"));
            return;
        }

        List<Campaign> campaigns = new ArrayList<>();

        JSONArray campaignsArray = jsonObject.getJSONArray("object");

        for (int i = 0; i < campaignsArray.length(); i++) {
            Campaign c = null;
            JSONObject jsonCampaign = campaignsArray.getJSONObject(i);
            String type = jsonCampaign.getString("type");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            long id = jsonCampaign.getLong("id");
            String name = jsonCampaign.getString("name");
            String description = jsonCampaign.getString("description");
            String startDateString = jsonCampaign.getString("startDate");
            String endDateString = jsonCampaign.getString("endDate");
            Date startDate;
            Date endDate;
            try {
                startDate = sdf.parse(startDateString);
                endDate = sdf.parse(endDateString);
            } catch (ParseException e) {
                e.printStackTrace();
                // Se deu erro em alguma campanha, apenas ignora ela
                continue;
            }

            float percentage = (float) jsonCampaign.getDouble("percentage");


            if (type.equalsIgnoreCase("ProductCampaign")) {
                c = new ProductCampaign(id, name, description, startDate, endDate, percentage);
            } else if (type.equalsIgnoreCase("MoneyCampaign")) {
                c = new MoneyCampaign(id, name, description, startDate, endDate, percentage);
            }
            campaigns.add(c);

        }
        mCampaignList.clear();
        mCampaignList.addAll(campaigns);
        mCampaignAdapter.notifyDataSetChanged();
    }



    public void selectCampaign(int adapterPosition) {
        Campaign c = mCampaignList.get(adapterPosition);

        //Toast.makeText(getApplicationContext(), "A campanha que você vai doar é " + c.getName(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, Products.class);

        Intent md = new Intent (this, MoneyDonation.class);

        campaignInfoEditor.putString("NAME_CAMPAIGN", c.getName());
        campaignInfoEditor.putLong("ID_CAMPAIGN", c.getId());
        campaignInfoEditor.apply();

        if(c instanceof MoneyCampaign){

            md.putExtra("ID_CAMPAIGN", c.getId());
            startActivity(md);
        }
        else if(c instanceof ProductCampaign){

            i.putExtra("ID_CAMPAIGN", c.getId());
            startActivity(i);
        }

    }


    private void showError(Exception e) {
        Snackbar.make(campaignsLayout, "Erro inesperado. Tente novamente.", BaseTransientBottomBar.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    private void showError(String errorMessage) {
        Snackbar.make(campaignsLayout, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
    }
}
