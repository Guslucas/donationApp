package br.com.faj.project.donationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import br.com.faj.project.donationapp.model.DonatorType;

public class SignIn extends AppCompatActivity {

    TextView titleTV;

    List<String> titulos = new ArrayList<>();
    List<Fragment> telas = new ArrayList<>();
    DonatorType type;

    int telaAtual = 0;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        SignInBasicInfoFragmentInterface basicInfo = new SignInBasicInfoFragmentInterface();
        SignInPersonFragmentInterface person = new SignInPersonFragmentInterface();
        SignInCompanyFragmentInterface company = new SignInCompanyFragmentInterface();
        SignInAddressFragmentInterface address = new SignInAddressFragmentInterface();

        titulos.add("Informações Básicas");
        telas.add(basicInfo);
        titulos.add("Pessoa Física");
        telas.add(person);
        titulos.add("Pessoa Jurídica");
        telas.add(company);
        titulos.add("Endereço");
        telas.add(address);

        titleTV = findViewById(R.id.titleTV);
        titleTV.setText((String) titulos.get(telaAtual));

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, telas.get(telaAtual));
        ft.commit();

    }

    public void back(View view) {
        int proximaTela = 0;
        if (telaAtual == 0) {
            finish();
            return;
        } else if (telaAtual == telas.size() - 1) {
            if (type.equals(DonatorType.PERSON)) {
                proximaTela = 1;
            }else if (type.equals(DonatorType.COMPANY)) {
                proximaTela = 2;
            }
        } else {
            proximaTela = 0;
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, telas.get(proximaTela));
        ft.commit();
        titleTV.setText(titulos.get(proximaTela));
        telaAtual = proximaTela;
    }

    public void next(View view) {

        SignInFormInterface telaAtual = (SignInFormInterface) telas.get(this.telaAtual);

        if (!telaAtual.validate()) {
            return;
        }

        int proximaTela = 0;
        if (this.telaAtual == 0) {
            SignInBasicInfoFragmentInterface basicInfo = (SignInBasicInfoFragmentInterface) telas.get(0);
            type = basicInfo.getType();
            if (type.equals(DonatorType.PERSON)) {
                proximaTela = 1;
            }else if (type.equals(DonatorType.COMPANY)) {
                proximaTela = 2;
            }
        } else if (this.telaAtual == telas.size() - 1) {
            goToCampaigns();
            return;
        } else {
            proximaTela = 3;
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, telas.get(proximaTela));
        ft.commit();

        titleTV.setText(titulos.get(proximaTela));
        this.telaAtual = proximaTela;

    }

    private void goToCampaigns() {
        Intent i = new Intent(this, Campaigns.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        ActivityCompat.finishAffinity(SignIn.this);
    }
}

