package br.com.faj.project.donationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.nio.charset.StandardCharsets;

public class Login extends AppCompatActivity {

    EditText loginET;
    EditText passwordET;

    TextInputLayout loginIL;
    TextInputLayout passwordIL;

    RequestQueue queue;

    SharedPreferences loginInfoSP;
    SharedPreferences.Editor loginInfoEditor;

    ConstraintLayout loginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_login);
            }
        }, 2000);*/

        setContentView(R.layout.activity_login);

        loginIL = findViewById(R.id.loginInputLayout);
        passwordIL = findViewById(R.id.passwordInputLayout);
        loginET = findViewById(R.id.loginEditText);
        passwordET = findViewById(R.id.passwordEditText);

        loginLayout = findViewById(R.id.loginLayout);

        loginET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    validate(s.toString());
                    loginIL.setError(null);
                } catch (IllegalArgumentException e) {
                    loginIL.setError(e.getMessage());
                }
            }
        });
        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    validate(s.toString());
                    passwordIL.setError(null);
                } catch (IllegalArgumentException e) {
                    passwordIL.setError(e.getMessage());
                }
            }
        });

        queue = Volley.newRequestQueue(this);

        loginInfoSP = getSharedPreferences("loginInfo", MODE_PRIVATE);
        loginInfoEditor = loginInfoSP.edit();

        verifyPreviousLogin();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loginInfoEditor.clear();
        loginInfoEditor.apply();
    }

    private void verifyPreviousLogin() {

        String login = loginInfoSP.getString("activity_login", null);
        String password = loginInfoSP.getString("password", null);

        if (login != null && password != null) {
            loginServer(login, password);
        }
    }

    private void loginServer(final String login, final String password) {
        String url = getResources().getString(R.string.url);
        url += "/login";
        Log.i("URL sendo usada", url);

        StringRequest loginRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    responseLogIn(new String(response.getBytes("ISO-8859-1"), "UTF-8"));
                } catch (Exception e) {
                    showError(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError(error);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                JSONObject loginESenha = new JSONObject();
                try {
                    loginESenha.put("email", login);
                    loginESenha.put("password", password);
                    loginESenha.put("type", "Person");
                } catch (JSONException e) {
                    showError(e);
                }

                return loginESenha.toString().getBytes(StandardCharsets.UTF_8);
            }
        };

        queue.add(loginRequest);
    }

    private void responseLogIn(String response) throws JSONException {
        System.out.println(response);

        JSONObject jsonResponse = (JSONObject) (new JSONTokener(response)).nextValue();

        if (!jsonResponse.getString("status").equalsIgnoreCase("OK")) {
            showError(jsonResponse.getString("errorMessage"));
            return;
        }


        JSONObject object = jsonResponse.getJSONObject("object");
        long id = object.getLong("id");


        //TODO talvez separar em um método diferente de uma outra classe
        String name;
        if (object.has("cpf")) {
            name = object.getString("name");
        } else {
            name = object.getString("companyName");
        }

        loginInfoEditor.putLong("ID_DONATOR", id);
        loginInfoEditor.putString("NAME_DONATOR", name);


        loginInfoEditor.apply();

        loginSuccess();

    }

    private void testeProduct(){
        Intent intent =  new Intent(this, AddProductAdm.class);
        startActivity(intent);
    }

    private void loginSuccess() {
        // Código usado para testes
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

    private void showError(Exception e) {
        Snackbar.make(loginLayout, "Erro inesperado. Tente novamente.", BaseTransientBottomBar.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    private void showError(String e) {
        Snackbar.make(loginLayout, e, BaseTransientBottomBar.LENGTH_SHORT).show();
    }

    private void validate(String s) throws IllegalArgumentException {
        if (s.trim().equals("")) {
            throw new IllegalArgumentException("O campo não pode ser vazio.");
        }
    }

    public void goToMessages() {
        Intent i = new Intent(this, Messages.class);
        startActivity(i);
    }

    public void goToBarCode() {
        Intent i = new Intent(this, BarCode.class);
        startActivity(i);
    }

    public void goToBankInformation() {
        Intent i = new Intent(this, BankInformation.class);
        startActivity(i);
    }

    public void goToProducts() {
        Intent i = new Intent(this, Products.class);
        startActivity(i);
    }

    private void goToMenu() {
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

    public void login(View v) {

        //TODO TRAVA APENAS PARA TESTES
        //testeProduct();
        //loginSuccess();
        //goToMessages();
        //goToBarCode();
        //goToBankInformation();
        //goToProducts();
        //goToMenu();
        //finish();


        boolean error = false;

        String login = loginET.getText().toString();
        String password = passwordET.getText().toString();

        if (login.trim().isEmpty()) {
            loginIL.setError("O campo não pode ser vazio.");
            error = true;
        }

        if (password.trim().isEmpty()) {
            passwordIL.setError("O campo não pode ser vazio.");
            error = true;
        }

        if (error) {
            return;
        }

        loginServer(login, password);
    }

    public void signIn(View view) {
        Intent i = new Intent(this, SignIn.class);
        startActivity(i);
    }
}
