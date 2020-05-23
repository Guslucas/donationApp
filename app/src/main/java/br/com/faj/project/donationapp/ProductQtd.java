package br.com.faj.project.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ProductQtd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_qtd);
    }

    public void inicio(View v){

        Intent i = new Intent(this, Campaigns.class);
        startActivity(i);
        ActivityCompat.finishAffinity(ProductQtd.this);
    }
}
