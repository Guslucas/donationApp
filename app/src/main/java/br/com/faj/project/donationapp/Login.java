package br.com.faj.project.donationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.login);
            }
        }, 2000);*/

        setContentView(R.layout.login);
    }

    public void login(View v) {
        EditText loginET = findViewById(R.id.loginText);
        EditText passwordET = findViewById(R.id.passwordText);

        Snackbar.make(v, loginET.getText().toString() + ", você está logado!", BaseTransientBottomBar.LENGTH_SHORT)
        .show();

        // Código usado para testes
        Intent i = new Intent(this, Campaigns.class);
        startActivity(i);

    }

    public void signIn(View view) {
        Intent i = new Intent(this, Cadastro.class);
        startActivity(i);
    }
}
