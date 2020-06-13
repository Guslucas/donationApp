package br.com.faj.project.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

public class AddProductAdm extends AppCompatActivity {

    private EditText productNameET;
    private  EditText typeET;
    private ConstraintLayout productLayout;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        queue = Volley.newRequestQueue(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_adm);
        productLayout = findViewById(R.id.productLayout);
    }

    public void sendProductServer(View view) throws JSONException {

        productNameET = findViewById(R.id.productNameET);
        typeET = findViewById(R.id.typeET);

        String url = getResources().getString(R.string.url);
        url += "/product" ;
        Log.i("URL sendo usada", url);

        JSONObject jsonObject =  new JSONObject();

        jsonObject.put("name", productNameET.getText().toString());
        jsonObject.put("type", typeET.getText().toString());

        final String product = jsonObject.toString();

        final StringRequest productRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                productNameET.setText("");
                typeET.setText("");
                Snackbar.make(productLayout, "Produto adicionado com sucesso!", BaseTransientBottomBar.LENGTH_LONG);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError(error);
            }
        }){
        };

        queue.add(productRequest);
    }


    private void showError(Exception e) {
        Snackbar.make(productLayout, "Erro inesperado. Tente novamente.", BaseTransientBottomBar.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    private void showError(String errorMessage) {
        Snackbar.make(productLayout, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
    }

}
