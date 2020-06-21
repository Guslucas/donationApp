package br.com.faj.project.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard extends AppCompatActivity {

    private RecyclerView mLeaderboardRecycler;
    private LeaderboardAdapter mLeaderboardAdapter;
    private View mLeaderboardLayout;

    List<LeaderboardItem> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        itemList.add(new LeaderboardItem(66, "Google", 28));
        itemList.add(new LeaderboardItem("Marquinho", 19));
        itemList.add(new LeaderboardItem("Twitter", 17));
        itemList.add(new LeaderboardItem("Katchau", 9));

        mLeaderboardLayout = findViewById(R.id.leaderboardLayout);
        mLeaderboardRecycler = findViewById(R.id.leaderboardRecycler);

        mLeaderboardAdapter = new LeaderboardAdapter(itemList, this, 66);
        mLeaderboardRecycler.setLayoutManager(new LinearLayoutManager(this));
        mLeaderboardRecycler.setAdapter(mLeaderboardAdapter);
        mLeaderboardRecycler.setItemAnimator(new DefaultItemAnimator());


    }

    private void showError(Exception e) {
        Snackbar.make(mLeaderboardLayout, "Erro inesperado. Tente novamente.", BaseTransientBottomBar.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    private void showError(String errorMessage) {
        Snackbar.make(mLeaderboardLayout, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
    }
}
