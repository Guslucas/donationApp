package br.com.faj.project.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard extends AppCompatActivity {

    private RecyclerView mLeaderboardRecycler;
    private LeaderboardAdapter mLeaderboardAdapter;
    private View mLeaderboardLayout;

    private RequestQueue queue;

    private List<LeaderboardItem> mLeaderboardItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        queue = Volley.newRequestQueue(this);

        /*mLeaderboardItems.add(new LeaderboardItem(66, "Google", 28));
        mLeaderboardItems.add(new LeaderboardItem("Marquinho", 19));
        mLeaderboardItems.add(new LeaderboardItem("Twitter", 17));
        mLeaderboardItems.add(new LeaderboardItem("Katchau", 9));*/

        mLeaderboardLayout = findViewById(R.id.leaderboardLayout);
        mLeaderboardRecycler = findViewById(R.id.leaderboardRecycler);

        mLeaderboardAdapter = new LeaderboardAdapter(mLeaderboardItems, this, 66);
        mLeaderboardRecycler.setLayoutManager(new LinearLayoutManager(this));
        mLeaderboardRecycler.setAdapter(mLeaderboardAdapter);
        mLeaderboardRecycler.setItemAnimator(new DefaultItemAnimator());
        loadItems();

    }

    private void loadItems() {
        String url = getResources().getString(R.string.url);
        //url += "/leaderboard";
        url += "/leaderboard.json";
        Log.i("URL sendo usada", url);

        StringRequest leaderboardRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    responseLeaderboard(new String(response.getBytes("ISO-8859-1"), "UTF-8"));
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

        queue.add(leaderboardRequest);
    }

    private void responseLeaderboard(String response) throws JSONException {
        System.out.println(response);

        JSONObject jsonResponse = (JSONObject) (new JSONTokener(response)).nextValue();

        if (!jsonResponse.getString("status").equalsIgnoreCase("OK")) {
            showError(jsonResponse.getString("errorMessage"));
            return;
        }

        List<LeaderboardItem> itemList = new ArrayList<>();

        JSONArray itemListJson = jsonResponse.getJSONArray("object");
        for (int i = 0; i < itemListJson.length(); i++) {
            JSONObject item = itemListJson.getJSONObject(i);
            long idDonator = item.getLong("idDonator");
            String name = item.getString("name");
            int quantitydDonation = item.getInt("quantityDonation");

            LeaderboardItem leaderboardItem = new LeaderboardItem(idDonator, name, quantitydDonation);
            itemList.add(leaderboardItem);
        }

        mLeaderboardItems.clear();
        mLeaderboardItems.addAll(itemList);
        mLeaderboardAdapter.notifyDataSetChanged();
    }

    private void showError(Exception e) {
        Snackbar.make(mLeaderboardLayout, "Erro inesperado. Tente novamente.", BaseTransientBottomBar.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    private void showError(String errorMessage) {
        Snackbar.make(mLeaderboardLayout, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
    }
}