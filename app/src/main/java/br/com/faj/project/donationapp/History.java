package br.com.faj.project.donationapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class History extends AppCompatActivity {

    private RecyclerView mHistoryRecycler;
    private HistoryAdapter mHistoryAdapter;
    private View mHistoryLayout;
    private SnackbarUtil snackbarUtil;

    private RequestQueue queue;

    private List<HistoryItem> mHistoryItems = new ArrayList<>();

    private SharedPreferences loginInfoSP;
    private long donatorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        queue = Volley.newRequestQueue(this);

        mHistoryLayout = findViewById(R.id.historyLayout);
        snackbarUtil = new SnackbarUtil(mHistoryLayout);
        mHistoryRecycler = findViewById(R.id.historyRecycler);

        mHistoryAdapter = new HistoryAdapter(mHistoryItems, this, 66);
        mHistoryRecycler.setLayoutManager(new LinearLayoutManager(this));
        mHistoryRecycler.setAdapter(mHistoryAdapter);
        mHistoryRecycler.setItemAnimator(new DefaultItemAnimator());

        loginInfoSP = getSharedPreferences("loginInfo", MODE_PRIVATE);
        donatorId = loginInfoSP.getLong("ID_DONATOR", -1);

        loadItems();

        Log.i("ID_USUARIO", String.valueOf(donatorId));

        if (donatorId == -1) try {
            throw new Exception("Donator invalido");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadItems() {
        String url = getResources().getString(R.string.url);
        url += "/donator/" + donatorId + "/donation";
        Log.i("URL sendo usada", url);

        StringRequest leaderboardRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    responseHistory(new String(response.getBytes("ISO-8859-1"), "UTF-8"));
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

    private void responseHistory(String response) throws JSONException {
        System.out.println(response);

        JSONObject jsonResponse = (JSONObject) (new JSONTokener(response)).nextValue();

        if (!jsonResponse.getString("status").equalsIgnoreCase("OK")) {
            snackbarUtil.showError(jsonResponse.getString("errorMessage"));
            return;
        }

        List<HistoryItem> itemList = new ArrayList<>();

        JSONArray itemListJson = jsonResponse.getJSONArray("object");
        for (int i = 0; i < itemListJson.length(); i++) {
            JSONObject item = itemListJson.getJSONObject(i);
            String dateString = item.getString("date");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

            Date date;
            try {
                date = sdf.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
                continue;
            }

            String type = item.getString("type");

            HistoryItem historyItem = new HistoryItem(date, type);
            itemList.add(historyItem);
        }

        mHistoryItems.clear();
        mHistoryItems.addAll(itemList);
        mHistoryAdapter.notifyDataSetChanged();
    }
}
