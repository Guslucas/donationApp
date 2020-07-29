package br.com.faj.project.donationapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.faj.project.donationapp.model.Product;

public class AddCampaignAdm extends AppCompatActivity {

    private ConstraintLayout addCampaignLayout;
    private SnackbarUtil snackbarUtil;

    SwitchMaterial campaignTypeSwitch;

    RequestQueue queue;

    boolean isMonetary = false;

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

    LinearLayout objectivesLayout;
    LinearLayout objectivesLayoutParent;
    Button addObjectiveBtn;
    List<String> mProductsNames = new ArrayList<>();
    ArrayAdapter<String> spinnerAdapter;
    private List<Product> mProductList = new ArrayList<>();
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_campaign_adm);

        addCampaignLayout = findViewById(R.id.addCampaignLayout);
        snackbarUtil = new SnackbarUtil(addCampaignLayout);

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
        scrollView = findViewById(R.id.scrollView);
        loadProducts();

        spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, mProductsNames);
        objectivesLayout = findViewById(R.id.objectivesLayout);
        objectivesLayoutParent = findViewById(R.id.objectivesLayoutParent);
        addObjectiveBtn = findViewById(R.id.addObjectiveBtn);
        addObjectiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View newView = inflater.inflate(R.layout.objective_item, objectivesLayout, false);
                Spinner spinner = newView.findViewById(R.id.spinner);
                spinner.setAdapter(spinnerAdapter);
                ImageView closeBtn = newView.findViewById(R.id.closeBtn);
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LinearLayout parent = (LinearLayout) newView.getParent();
                        parent.removeView(newView);
                    }
                });
                objectivesLayout.addView(newView);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });

        //objetivoET.setEnabled(false);
        //objetivoIL.setEnabled(false);
        objetivoIL.setVisibility(View.GONE);

        campaignTypeSwitch = findViewById(R.id.switchTypeCampaign);
        campaignTypeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isMonetary = isChecked;
                if (isChecked) {

                    //objetivoIL.setEnabled(true);
                    objetivoIL.setVisibility(View.VISIBLE);
                    objectivesLayoutParent.setVisibility(View.GONE);

                } else {
                    //objetivoIL.setEnabled(false);
                    objetivoIL.setVisibility(View.GONE);
                    objectivesLayoutParent.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    public void addCampaign(final JSONObject jsonObject) throws JSONException, ParseException {
        String url = getResources().getString(R.string.url);
        url += "/campaign";
        Log.i("URL sendo usada", url);

        final StringRequest campaignRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = (JSONObject) (new JSONTokener(response)).nextValue();

                    if (!jsonResponse.getString("status").equalsIgnoreCase("OK")) {
                        snackbarUtil.showError(jsonResponse.getString("errorMessage"));
                        return;
                    }

                    nomeET.setText("");
                    descricaoET.setText("");
                    inicioET.setText("");
                    fimET.setText("");
                    objetivoET.setText("");
                    objectivesLayout.removeAllViews();

                    Snackbar.make(addCampaignLayout, "Campanha adicionada com sucesso!", BaseTransientBottomBar.LENGTH_LONG).show();
                } catch (Exception e) {
                    snackbarUtil.showError(e);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                snackbarUtil.showError(error);
            }
        }) {

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

    public void adicionar(View view) {

        if (!EditTextUtil.validateEditText(nomeIL, nomeET) | !EditTextUtil.validateEditText(descricaoIL, descricaoET)
                | !EditTextUtil.validateEditText(inicioIL, inicioET) | !EditTextUtil.validateEditText(fimIL, fimET)) {
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dataInicio;
            try {
                dataInicio = sdf.parse(inicioET.getText().toString());
            } catch (ParseException e) {
                inicioIL.setError("Formato inválido");
                return;
            }
            Date dataFIm;
            try {
                dataFIm = sdf.parse(fimET.getText().toString());
            } catch (ParseException e) {
                fimIL.setError("Formato inválido");
                return;
            }
            SimpleDateFormat sdfServer = new SimpleDateFormat("yyyy-MM-dd");

            String dataInicioSv = sdfServer.format(dataInicio);
            String dataFimSv = sdfServer.format(dataFIm);

            jsonObject.put("name", nomeET.getText().toString());
            jsonObject.put("description", descricaoET.getText().toString());
            jsonObject.put("startDate", dataInicioSv);
            jsonObject.put("endDate", dataFimSv);
        } catch (JSONException e) {
            e.printStackTrace();
            snackbarUtil.showError("Erro inesperado.");
            return;
        }

        if (isMonetary) {
            if (!EditTextUtil.validateEditText(objetivoIL, objetivoET)) {
                return;
            }
            try {
                float goal = Float.parseFloat(objetivoET.getText().toString());
                jsonObject.put("type", "MoneyCampaign");
                jsonObject.put("goal", goal);
            } catch (JSONException e) {
                e.printStackTrace();
                snackbarUtil.showError("Erro inesperado.");
                return;
            }
        } else {
            JSONArray objectives = new JSONArray();
            int qtdObjetivos = objectivesLayout.getChildCount();
            if (qtdObjetivos == 0) {
                snackbarUtil.showError("Nenhum produto inserido.");
                return;
            }
            for (int i = 0; i < qtdObjetivos; i++) {
                View v = objectivesLayout.getChildAt(i);
                if (v instanceof ConstraintLayout) {
                    Spinner s = v.findViewById(R.id.spinner);
                    TextInputLayout qtdIL = v.findViewById(R.id.qtdInputLayout);
                    EditText qtdET = v.findViewById(R.id.qtdEditText);
                    if (!EditTextUtil.validateEditText(qtdIL, qtdET)) {
                        return;
                    }

                    JSONObject objective = new JSONObject();
                    int quantity = 0;
                    try {
                        quantity = Integer.parseInt(qtdET.getText().toString());
                    } catch (IllegalStateException e) {
                        qtdIL.setError("Número inválido.");
                        return;
                    }

                    try {
                        objective.put("quantity", quantity);
                        objective.put("product", new JSONObject().put("id", mProductList.get(s.getSelectedItemPosition()).getId()));
                        objectives.put(objective);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
            try {
                jsonObject.put("type", "ProductCampaign");
                jsonObject.put("objectives", objectives);
            } catch (JSONException e) {
                e.printStackTrace();
                snackbarUtil.showError("Erro inesperado.");
                return;
            }
        }
        System.out.println(jsonObject.toString());
        try {
            addCampaign(jsonObject);
        } catch (Exception e) {
            snackbarUtil.showError("Erro inesperado.");
            e.printStackTrace();
            return;
        }
    }

    private void loadProducts() {
        String url = getResources().getString(R.string.url);
        url += "/product";
        Log.i("URL sendo usada:", url);

        StringRequest requestProducts = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    listProducts(new String(response.getBytes("ISO-8859-1"), "UTF-8"));
                } catch (Exception e) {
                    snackbarUtil.showError(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                snackbarUtil.showError(error);
                error.printStackTrace();
            }
        });

        queue.add(requestProducts);
    }

    private void listProducts(String response) throws JSONException {

        JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
        if (!jsonObject.getString("status").equalsIgnoreCase("OK")) {
            snackbarUtil.showError(jsonObject.getString("errorMessage"));
            return;
        }

        mProductList.clear();
        mProductsNames.clear();

        JSONArray productsArray = jsonObject.getJSONArray("object");
        for (int i = 0; i < productsArray.length(); i++) {

            JSONObject jsonProduct = productsArray.getJSONObject(i);

            long id = jsonProduct.getLong("id");
            String type = jsonProduct.getString("type");
            String name = jsonProduct.getString("name");
            Product p = new Product(name, id, type);
            mProductList.add(p);
            mProductsNames.add(p.getName() + "(" + p.getType() + ")");
        }

    }
}
