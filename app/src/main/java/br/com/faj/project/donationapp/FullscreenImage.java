package br.com.faj.project.donationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class FullscreenImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_image);

        ImageView imageView = findViewById(R.id.fullScreenImageView);


        Intent intent = getIntent();
        String image = intent.getStringExtra("IMAGE");

        if (image.equalsIgnoreCase("BAR")) {

            Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.barcode);

            Matrix matrix = new Matrix();
            matrix.postRotate(90f);

            Bitmap rotated = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);

            imageView.setImageBitmap(rotated);
        } else if (image.equalsIgnoreCase("QR")) {
            imageView.setImageResource(R.drawable.qrcode);
        }


    }
}
