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

public class MenuAdm extends AppCompatActivity implements View.OnClickListener {

    CardView adcionarCampanhaMenuButton;
    CardView adicionarProdutoMenuButton;
    CardView logoutMenuButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_adm);

        adcionarCampanhaMenuButton = findViewById(R.id.adcionarCampanhaMenuButton);
        adicionarProdutoMenuButton = findViewById(R.id.adicionarProdutoMenuButton);
        logoutMenuButton = findViewById(R.id.logoutMenuButton);

        adcionarCampanhaMenuButton.setOnClickListener(this);
        adicionarProdutoMenuButton.setOnClickListener(this);
        logoutMenuButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent i = new Intent();

        switch (v.getId()) {
            case R.id.adcionarCampanhaMenuButton:
                i.setClass(this, AddCampaignAdm.class);
                break;
            case R.id.adicionarProdutoMenuButton:
                i.setClass(this, AddProductAdm.class);
                break;
            case R.id.logoutMenuButton:
                //Remove user logado
                finish();
                return;
        }
        startActivity(i);
    }
}
