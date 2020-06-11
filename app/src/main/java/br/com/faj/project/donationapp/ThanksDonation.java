package br.com.faj.project.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class ThanksDonation extends AppCompatActivity {

    SharedPreferences loginInfoSP;
    SharedPreferences campaignInfo;
    String donatorName;
    String nomeCampanha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_qtd);

        loginInfoSP = getSharedPreferences("loginInfo", MODE_PRIVATE);
        donatorName = loginInfoSP.getString("NAME_DONATOR", null);

        campaignInfo = getSharedPreferences("campaignInfo", MODE_PRIVATE);
        nomeCampanha = campaignInfo.getString("NAME_CAMPAIGN", null);

    }

    public void inicio(View v){

        Intent i = new Intent(this, Campaigns.class);
        startActivity(i);
        ActivityCompat.finishAffinity(ThanksDonation.this);
    }

    public void share(View view) {
        String link = "https://play.google.com/store/search?q=donation%20app";

        String shareText;
        if (nomeCampanha == null) {
            shareText = donatorName + " acabou de fazer uma doação para a gente!!";
        } else {
            shareText = donatorName + " acabou de doar para a campanha " + nomeCampanha +"!!";
        }
        shareText += " Ajude você também:\n" + link;

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        startActivity(Intent.createChooser(sendIntent, null));
    }
}
