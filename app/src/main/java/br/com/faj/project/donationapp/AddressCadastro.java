package br.com.faj.project.donationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Timer;
import java.util.TimerTask;

public class AddressCadastro extends AppCompatActivity {

    EditText cepET;
    EditText cidadeET;
    EditText estadoET;

    RequestQueue requestQueue;

    private Timer timer = new Timer();
    private final long DELAY = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_address);

        requestQueue = Volley.newRequestQueue(this);
        cepET = findViewById(R.id.cepEditText);
        cidadeET = findViewById(R.id.cidadeEditText);
        estadoET = findViewById(R.id.estadoEditText);

        cepET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null) timer.cancel();
            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s.length() == 8) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getInfoCep(s.toString());
                        }
                    }, DELAY);
                } else {
                    cidadeET.setEnabled(true);
                    estadoET.setEnabled(true);
                    cidadeET.setText("");
                    estadoET.setText("");
                    //TODO mensagem erro
                }
            }
        });
    }

    public void getInfoCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        StringRequest cepRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject info = null;
                try {
                    info = (JSONObject) new JSONTokener(response).nextValue();
                    if (!info.has("erro")) {
                        cidadeET.setText(info.getString("localidade"));
                        estadoET.setText(info.getString("uf"));
                        cidadeET.setEnabled(false);
                        estadoET.setEnabled(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null);
        requestQueue.add(cepRequest);
    }
}
