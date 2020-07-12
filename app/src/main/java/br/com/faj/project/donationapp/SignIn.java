package br.com.faj.project.donationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import br.com.faj.project.donationapp.model.Company;
import br.com.faj.project.donationapp.model.Donator;
import br.com.faj.project.donationapp.model.DonatorType;
import br.com.faj.project.donationapp.model.Person;

public class SignIn extends AppCompatActivity {

    TextView titleTV;

    List<String> titulos = new ArrayList<>();
    List<Fragment> telas = new ArrayList<>();
    DonatorType type;

    int telaAtual = 0;

    RequestQueue queue;

    ConstraintLayout constraintLayout;

    SharedPreferences loginInfoSP;
    SharedPreferences.Editor loginInfoEditor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        SignInBasicInfoFragment basicInfo = new SignInBasicInfoFragment();
        SignInPersonFragment person = new SignInPersonFragment();
        SignInCompanyFragment company = new SignInCompanyFragment();
        SignInAddressFragment address = new SignInAddressFragment();

        titulos.add("Informações Básicas");
        telas.add(basicInfo);
        titulos.add("Pessoa Física");
        telas.add(person);
        titulos.add("Pessoa Jurídica");
        telas.add(company);
        titulos.add("Endereço");
        telas.add(address);

        titleTV = findViewById(R.id.titleTV);
        titleTV.setText(titulos.get(telaAtual));

        constraintLayout = findViewById(R.id.constraintLayout);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, telas.get(telaAtual));
        ft.commit();

        queue = Volley.newRequestQueue(this);

        loginInfoSP = getSharedPreferences("loginInfo", MODE_PRIVATE);
        loginInfoEditor = loginInfoSP.edit();

    }

    public void back(View view) {
        int proximaTela = 0;
        if (telaAtual == 0) {
            finish();
            return;
        } else if (telaAtual == telas.size() - 1) {
            if (type.equals(DonatorType.PERSON)) {
                proximaTela = 1;
            } else if (type.equals(DonatorType.COMPANY)) {
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

        SignInForm telaAtual = (SignInForm) telas.get(this.telaAtual);

        if (!telaAtual.validate()) {
            return;
        }

        int proximaTela = 0;
        if (this.telaAtual == 0) {
            SignInBasicInfoFragment basicInfo = (SignInBasicInfoFragment) telas.get(0);
            type = basicInfo.getType();
            if (type.equals(DonatorType.PERSON)) {
                proximaTela = 1;
            } else if (type.equals(DonatorType.COMPANY)) {
                proximaTela = 2;
            }
        } else if (this.telaAtual == telas.size() - 1) {

            Donator d = null;

            SignInForm basicInfo = (SignInForm) telas.get(0);
            d = basicInfo.extract(d);

            if (d instanceof Person) {
                SignInForm personInfo = (SignInForm) telas.get(1);
                d = personInfo.extract(d);
            } else if (d instanceof Company) {
                SignInForm companyInfo = (SignInForm) telas.get(2);
                d = companyInfo.extract(d);
            }

            SignInForm addressInfo = (SignInForm) telas.get(3);
            d = addressInfo.extract(d);

            signIn(d);

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

    private void signIn(Donator d) {

        String url = getResources().getString(R.string.url);
        url += "/signin"; //TODO alterar para In
        Log.i("URL sendo usada", url);

        String donator = null;
        try {
            donator = d.toJSON().toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String finalDonator = donator;
        StringRequest signInRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                responseSignIn(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError(error);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return finalDonator.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        queue.add(signInRequest);
    }

    private void responseSignIn(String response) {
        try {
            System.out.println(response);
            JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
            if (!jsonObject.getString("status").equalsIgnoreCase("OK")) {
                showError(jsonObject.getString("errorMessage"));
                return;
            }

            JSONObject object = jsonObject.getJSONObject("object");

            long id = object.getLong("id");
            loginInfoEditor.putLong("ID_DONATOR", id);

            String name;
            if (object.has("cpf")) {
                name = object.getString("name");
            } else {
                name = object.getString("companyName");
            }
            loginInfoEditor.putString("NAME_DONATOR", name);

            loginInfoEditor.apply();

            goToMenu();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void goToMenu() {
        Intent i = new Intent(this, Menu.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        ActivityCompat.finishAffinity(SignIn.this);
    }

    private void showError(Exception e) {
        Snackbar.make(constraintLayout, "Erro inesperado. Tente novamente.", BaseTransientBottomBar.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    private void showError(String e) {
        Snackbar.make(constraintLayout, e, BaseTransientBottomBar.LENGTH_SHORT).show();
    }
}

