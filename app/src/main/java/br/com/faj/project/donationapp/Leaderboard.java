package br.com.faj.project.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private SnackbarUtil snackbarUtil;

    private RequestQueue queue;

    private List<LeaderboardItem> mLeaderboardItems = new ArrayList<>();

    private SharedPreferences loginInfoSP;
    private long donatorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        queue = Volley.newRequestQueue(this);

        loginInfoSP = getSharedPreferences("loginInfo", MODE_PRIVATE);
        donatorId = loginInfoSP.getLong("ID_DONATOR", -1);

        Log.i("ID_USUARIO", String.valueOf(donatorId));

        if (donatorId == -1) try {
            throw new Exception("Donator invalido");
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*mLeaderboardItems.add(new LeaderboardItem(66, "Google", 28));
        mLeaderboardItems.add(new LeaderboardItem("Marquinho", 19));
        mLeaderboardItems.add(new LeaderboardItem("Twitter", 17));
        mLeaderboardItems.add(new LeaderboardItem("Katchau", 9));*/

        mLeaderboardLayout = findViewById(R.id.historyLayout);
        snackbarUtil = new SnackbarUtil(mLeaderboardLayout);
        mLeaderboardRecycler = findViewById(R.id.historyRecycler);

        mLeaderboardAdapter = new LeaderboardAdapter(mLeaderboardItems, this, donatorId);
        mLeaderboardRecycler.setLayoutManager(new LinearLayoutManager(this));
        mLeaderboardRecycler.setAdapter(mLeaderboardAdapter);
        mLeaderboardRecycler.setItemAnimator(new DefaultItemAnimator());
        loadItems();

    }

    private void loadItems() {
        String url = getResources().getString(R.string.url);
        url += "/leaderboard";
        Log.i("URL sendo usada", url);

        StringRequest leaderboardRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    responseLeaderboard(new String(response.getBytes("ISO-8859-1"), "UTF-8"));
                } catch (Exception e) {
                    snackbarUtil.showError(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                snackbarUtil.showError(error);
            }
        });

        queue.add(leaderboardRequest);
    }

    private void responseLeaderboard(String response) throws JSONException {
        System.out.println(response);

        JSONObject jsonResponse = (JSONObject) (new JSONTokener(response)).nextValue();

        if (!jsonResponse.getString("status").equalsIgnoreCase("OK")) {
            snackbarUtil.showError(jsonResponse.getString("errorMessage"));
            return;
        }

        List<LeaderboardItem> itemList = new ArrayList<>();

        JSONArray itemListJson = jsonResponse.getJSONArray("object");
        for (int i = 0; i < itemListJson.length(); i++) {
            JSONObject item = itemListJson.getJSONObject(i);
            long idDonator = item.getLong("donatorId");
            String name = item.getString("name");
            int quantitydDonation = item.getInt("quantitydDonation");

            LeaderboardItem leaderboardItem = new LeaderboardItem(idDonator, name, quantitydDonation);
            itemList.add(leaderboardItem);
        }

        mLeaderboardItems.clear();
        mLeaderboardItems.addAll(itemList);
        mLeaderboardAdapter.notifyDataSetChanged();
    }
}
