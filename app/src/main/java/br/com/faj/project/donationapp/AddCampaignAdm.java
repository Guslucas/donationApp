package br.com.faj.project.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCampaignAdm extends AppCompatActivity {

    private ConstraintLayout addCampaignLayout;

    SwitchMaterial campaignTypeSwitch;

    RequestQueue queue;

    boolean isMonetary =  false;

    EditText nomeET;
    EditText descricaoET;
    EditText inicioET;
    EditText fimET;
    EditText objetivoET;
    TextInputLayout nomeIL;
    TextInputLayout descricaoIL;
    TextInputLayout inicioIL;
    TextInputLayout fimIL;
    TextInputLayout objetivoIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_campaign_adm);

        addCampaignLayout = findViewById(R.id.addCampaignLayout);

        queue = Volley.newRequestQueue(this);

        nomeET = findViewById(R.id.nomeEditText);
        descricaoET = findViewById(R.id.descricaoEditText);
        inicioET = findViewById(R.id.inicioEditText);
        fimET = findViewById(R.id.fimEditText);
        objetivoET = findViewById(R.id.objetivoEditText);
        nomeIL = findViewById(R.id.nomeInputLayout);
        descricaoIL = findViewById(R.id.descricaoInputLayout);
        inicioIL = findViewById(R.id.inicioInputLayout);
        fimIL = findViewById(R.id.fimInputLayout);
        objetivoIL = findViewById(R.id.objetivoInputLayout);

        objetivoET.setEnabled(false);
        objetivoIL.setEnabled(false);

        campaignTypeSwitch = findViewById(R.id.switchTypeCampaign);
        campaignTypeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isMonetary = isChecked;
                if (isChecked){

                    objetivoET.setEnabled(true);
                    objetivoIL.setEnabled(true);

                }else{

                    showError("Campanha não monetária, campo objetivo desabiltiado");
                    objetivoET.setEnabled(false);
                    objetivoIL.setEnabled(false);
                }

            }
        });

    }

    public void addCampaign() throws JSONException, ParseException {

        String url = getResources().getString(R.string.url);
        url += "/campaign" ;
        Log.i("URL sendo usada", url);

        final JSONObject jsonObject = new JSONObject();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataInicio = sdf.parse(inicioET.getText().toString());
        Date dataFIm = sdf.parse(fimET.getText().toString());

        SimpleDateFormat sdfServer = new SimpleDateFormat("yyyy-MM-dd");
        String dataInicioSv = sdfServer.format(dataInicio);
        String dataFimSv = sdfServer.format(dataFIm);


        jsonObject.put("nome", nomeET.getText().toString());
        jsonObject.put("descricao", descricaoET.getText().toString());
        jsonObject.put("inicio", dataInicioSv);
        jsonObject.put("fim", dataFimSv);

        if(isMonetary){

            jsonObject.put("objetivo", objetivoET.getText().toString());
        }
        else{

            //todo fazer doação produtária
        }

        final String campaign = jsonObject.toString();

        final StringRequest campaignRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                nomeET.setText("");
                descricaoET.setText("");
                inicioET.setText("");
                fimET.setText("");
                objetivoET.setText("");

                Snackbar.make(addCampaignLayout, "Campanha adicionado com sucesso!", BaseTransientBottomBar.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError(error);
            }
        }){

            @Override
            public byte[] getBody() {
                return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        queue.add(campaignRequest);
    }


    private void showError(Exception e) {
        Snackbar.make(addCampaignLayout, "Erro inesperado. Tente novamente.", BaseTransientBottomBar.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    private void showError(String errorMessage) {
        Snackbar.make(addCampaignLayout, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
    }

}
