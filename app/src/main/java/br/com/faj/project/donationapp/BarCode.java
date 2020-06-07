package br.com.faj.project.donationapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class BarCode extends AppCompatActivity implements View.OnClickListener {
    private ImageView barCode;
    private ImageView qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);

        barCode = findViewById(R.id.barCode);
        qrCode = findViewById(R.id.qrCode);

        barCode.setOnClickListener(this);
        qrCode.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.barCode:

                break;
            case R.id.qrCode:
                zoomQrCode();
                break;
        }
    }

    public void zoomQrCode() {

    }
}
