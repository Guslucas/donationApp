package br.com.faj.project.donationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MoneyDonation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_donation);
    }

    public void transferir(View v){

        Intent i = new Intent(this, BankInformation.class);
        startActivity(i);

    }

    public void gerarBoleto(View v){

        Intent i = new Intent(this, BankBill.class);
        startActivity(i);
    }
}
