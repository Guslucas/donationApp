package br.com.faj.project.donationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_main);
            }
        }, 2000);*/

        setContentView(R.layout.activity_main);
    }

    public void login(View v) {
        EditText loginET = findViewById(R.id.loginText);
        EditText passwordET = findViewById(R.id.passwordText);

        Snackbar.make(v, loginET.getText().toString() + ", você está logado!", BaseTransientBottomBar.LENGTH_SHORT)
        .show();

        // Código usado para testes
        Intent i = new Intent(this, Products.class);
        startActivity(i);

    }

    public void signIn(View view) {
        //Intent i = new Intent(this, .class);
    }
}
