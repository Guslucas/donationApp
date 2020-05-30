package br.com.faj.project.donationapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Timer;
import java.util.TimerTask;

import br.com.faj.project.donationapp.model.Address;
import br.com.faj.project.donationapp.model.Donator;

public class SignInAddressFragmentInterface extends Fragment implements SignInFormInterface {

    private TextInputLayout cepIL;
    private TextInputLayout bairroIL;
    private TextInputLayout ruaAvenidaIL;
    private TextInputLayout numeroIL;
    private TextInputLayout complementoIL;
    private TextInputLayout cidadeIL;
    private TextInputLayout estadoIL;

    private EditText cepET;
    private EditText bairroET;
    private EditText ruaAvenidaET;
    private EditText numeroET;
    private EditText complementoET;
    private EditText cidadeET;
    private EditText estadoET;



    private RequestQueue requestQueue;

    private Timer timer = new Timer();
    private final long DELAY = 500;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestQueue = Volley.newRequestQueue(this.getContext());
        loadUI(view);

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


    private void loadUI(View view) {

        cepET = view.findViewById(R.id.cepEditText);
        bairroET = view.findViewById(R.id.bairroEditText);
        ruaAvenidaET = view.findViewById(R.id.ruaAvenidaEditText);
        numeroET = view.findViewById(R.id.numeroEditText);
        complementoET = view.findViewById(R.id.complementoEditText);
        cidadeET = view.findViewById(R.id.cidadeEditText);
        estadoET = view.findViewById(R.id.estadoEditText);

        cepIL = view.findViewById(R.id.cepInputLayout);
        bairroIL = view.findViewById(R.id.bairroInputLayout);
        ruaAvenidaIL = view.findViewById(R.id.ruaAvenidaInputLayout);
        numeroIL = view.findViewById(R.id.numeroInputLayout);
        complementoIL = view.findViewById(R.id.complementoInputLayout);
        cidadeIL = view.findViewById(R.id.cidadeInputLayout);
        estadoIL = view.findViewById(R.id.estadoInputLayout);


    }

    private void getInfoCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        StringRequest cepRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject info;
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

    @Override
    public boolean validate() {
        boolean isValid = true;

        if (!(ValidateEditText.validateEditText(ruaAvenidaIL, ruaAvenidaET) &
                ValidateEditText.validateNumber(numeroIL, numeroET) &
                ValidateEditText.validateEditText(complementoIL,complementoET)&
                ValidateEditText.validateEditText(bairroIL, bairroET) &
                ValidateEditText.validateEditText(cidadeIL, cidadeET) &
                ValidateEditText.validateEditText(cepIL, cepET) &
                ValidateEditText.validateEditText(estadoIL, estadoET))) {
            isValid = false;
        }

        return isValid;
    }

    @Override
    public Donator extract(Donator d) {

        String ruaAvenida = ruaAvenidaET.getText().toString();
        String numero = numeroET.getText().toString();
        String complemento = complementoET.getText().toString();
        String bairro = bairroET.getText().toString();
        String cidade = cidadeET.getText().toString();
        String cep = cepET.getText().toString();
        String estado = estadoET.getText().toString();

        Address a = new Address(ruaAvenida, numero, complemento, bairro, cidade, cep, estado);

        d.setAddress(a);

        return d;
    }
}
