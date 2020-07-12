package br.com.faj.project.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    RecyclerView optionsRecycler;

    CardView doarMenuButton;
    CardView mensagemMenuButton;
    CardView historicoMenuButton;
    CardView leaderboardMenuButton;
    CardView cnpjMenuButton;
    CardView logoutMenuButton;

    SharedPreferences loginInfoSP;
    SharedPreferences.Editor loginInfoEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        doarMenuButton = findViewById(R.id.doarMenuButton);
        mensagemMenuButton = findViewById(R.id.mensagemMenuButton);
        historicoMenuButton = findViewById(R.id.historicoMenuButton);
        leaderboardMenuButton = findViewById(R.id.leaderboardMenuButton);
        cnpjMenuButton = findViewById(R.id.cnpjMenuButton);
        logoutMenuButton = findViewById(R.id.logoutMenuButton);

        doarMenuButton.setOnClickListener(this);
        mensagemMenuButton.setOnClickListener(this);
        historicoMenuButton.setOnClickListener(this);
        leaderboardMenuButton.setOnClickListener(this);
        cnpjMenuButton.setOnClickListener(this);
        logoutMenuButton.setOnClickListener(this);

        loginInfoSP = getSharedPreferences("loginInfo", MODE_PRIVATE);
        loginInfoEditor = loginInfoSP.edit();
    }

    public void test(View view) {
        System.out.println("a");
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent();

        switch (v.getId()) {
            case R.id.doarMenuButton:
                i.setClass(this, Campaigns.class);
                break;
            case R.id.mensagemMenuButton:
                i.setClass(this, Messages.class);
                break;
            case R.id.historicoMenuButton:
                i.setClass(this, History.class);
                break;
            case R.id.leaderboardMenuButton:
                i.setClass(this, Leaderboard.class);
                break;
            case R.id.cnpjMenuButton:
                i.setClass(this, BarCode.class);
                break;
            case R.id.logoutMenuButton:
                //Remove user logado
                loginInfoEditor.clear();
                loginInfoEditor.apply();
                finish();
                return;
        }
        startActivity(i);
    }
}
