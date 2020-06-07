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

    public void share(View view) {

        String donatorName = "Gustavo";
        String nomeCampanha = "Noites Tranquilas";

        String shareText;
        if (nomeCampanha == null) {
            shareText = donatorName + " acabou de fazer uma doação para a gente!!";
        } else {
            shareText = donatorName + " acabou de doar para a campanha " + nomeCampanha +"!!";
        }
        shareText += " Ajude você também:\nhttps://play.google.com/store/search?q=donation%20app";

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        startActivity(Intent.createChooser(sendIntent, null));
    }
}
