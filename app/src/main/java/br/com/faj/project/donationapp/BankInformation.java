package br.com.faj.project.donationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BankInformation extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_information);
    }

    public void finalizar (View v){
        Intent i = new Intent(this, ProductQtd.class);
        startActivity(i);
    }
}
