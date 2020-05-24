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
                setContentView(R.layout.login);
            }
        }, 2000);*/

        setContentView(R.layout.login);

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

    private void verifyPreviousLogin() {

        String login = loginInfoSP.getString("login", null);
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
                    JSONObject pessoa = null;
                try {
                    pessoa = (JSONObject) (new JSONTokener(response)).nextValue();
                } catch (JSONException e) {
                    showError(e);
                }
                if (pessoa != null && pessoa.has("id")) {
                    loginSuccess();
                } else {
                    showError("Login ou senha inválidos");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(loginLayout, "Ocorreu um erro inseperado. Tente novamente.", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                JSONObject loginESenha = new JSONObject();;
                try {
                    loginESenha.put("email", login);
                    loginESenha.put("password", password);
                } catch (JSONException e) {
                    showError(e);
                }

                return loginESenha.toString().getBytes(StandardCharsets.UTF_8);
            }
        };

        queue.add(loginRequest);
    }

    private void loginSuccess() {
        // Código usado para testes
        Intent i = new Intent(this, Campaigns.class);
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

    public void login(View v) {

        boolean error = false;

        String login = loginET.getText().toString();
        String password = passwordET.getText().toString();

        if (login.trim().equals("")) {
            loginIL.setError("O campo não pode ser vazio.");
            error = true;
        }

        if (password.trim().equals("")) {
            passwordIL.setError("O campo não pode ser vazio.");
            error = true;
        }

        if (error) {
            return;
        }

        loginServer(login, password);
    }

    public void signIn(View view) {
        //Intent i = new Intent(this, Cadastro.class);
        Intent i = new Intent(this, SignIn.class);
        startActivity(i);
    }
}
