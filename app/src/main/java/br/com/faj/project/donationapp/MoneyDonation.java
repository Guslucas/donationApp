package br.com.faj.project.donationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import javax.xml.validation.Validator;

public class MoneyDonation extends AppCompatActivity {

    EditText valorET;
    TextInputLayout valorIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_donation);

        valorET = findViewById(R.id.valorEditText);
        valorIL = findViewById(R.id.valorInputLayout);
    }

    public void transferir(View v){
        Intent i = new Intent(this, BankInformation.class);

        if (!EditTextUtil.validateEditText(valorIL, valorET)) return;
        float valor = Float.parseFloat(valorET.getText().toString());
        i.putExtra("valor", valor);

        startActivity(i);

    }

    public void gerarBoleto(View v){
        Intent i = new Intent(this, BankBill.class);

        if (!EditTextUtil.validateEditText(valorIL, valorET)) return;
        float valor = Float.parseFloat(valorET.getText().toString());
        i.putExtra("valor", valor);

        startActivity(i);
    }
}
