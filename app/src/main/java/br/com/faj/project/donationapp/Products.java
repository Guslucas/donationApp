package br.com.faj.project.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import br.com.faj.project.donationapp.model.Campaign;
import br.com.faj.project.donationapp.model.MoneyCampaign;
import br.com.faj.project.donationapp.model.Product;
import br.com.faj.project.donationapp.model.ProductCampaign;

public class Products extends AppCompatActivity implements DonateMoney {

    List<Product> mProductList = new ArrayList<Product>();
    RecyclerView mProductRecycler;
    ProductAdapter mProductAdapter;
    private ConstraintLayout productsLayout;
    int idCampaign;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        queue = Volley.newRequestQueue(this);

        idCampaign = getIntent().getIntExtra("ID_CAMPAIGN", -1);

        campaigProducts(idCampaign);


        mProductRecycler = findViewById(R.id.productsRecycler);
        mProductAdapter = new ProductAdapter(mProductList, this);

        mProductRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mProductRecycler.setLayoutManager(new LinearLayoutManager(this));
        mProductRecycler.setItemAnimator(new DefaultItemAnimator());

        mProductRecycler.setAdapter(mProductAdapter);


        productsLayout = findViewById(R.id.productLayout);
    }

    private void campaigProducts(int idCampaign){

        String url = getResources().getString(R.string.url);

        if(idCampaign == -1){

            url += "/product";
        }

        else{

            url += "/campaign/" + idCampaign + "/products";
        }

        Log.i("URL sendo usada:", url);

        StringRequest requestProducts = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    listProducts(new String(response.getBytes("ISO-8859-1"), "UTF-8"));
                } catch (Exception e) {
                    showError(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError(error);
                error.printStackTrace();
            }
        });

        queue.add(requestProducts);
    }


//    private void loadMoney(){
//
//        Bitmap night = BitmapFactory.decodeResource(this.getResources(), R.drawable.night);
//        mProductList.add(new Product("Dinheiro", "Estamos precisando", night,"R$"));
//    }

//    private void loadProducts() {
//        Bitmap night = BitmapFactory.decodeResource(this.getResources(), R.drawable.night);
//        mProductList.add(new Product("Money", "We need it.", night, ""));
//        mProductList.add(new Product("Milk", "We accept.", night, "1L"));
//
//        /*String url = getResources().getString(R.string.url);
//        url += "/campaigns"; //TODO alterar para In
//        Log.i("URL sendo usada", url);
//
//        StringRequest requestCampaigns = new StringRequest(Request.Method.GET, url, null, null);
//
//        queue.add(requestCampaigns);*/
//    }

    private void listProducts(String response)  throws JSONException {

        JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
        if (!jsonObject.getString("status").equalsIgnoreCase("OK")) {
            showError(jsonObject.getString("errorMessage"));
            return;
        }

        List<Product> products = new ArrayList<>();

        JSONArray productsArray = jsonObject.getJSONArray("object");
        for (int i = 0; i < productsArray.length(); i++) {

            JSONObject jsonProduct = productsArray.getJSONObject(i);

            long id = jsonProduct.getLong("id");
            String type = jsonProduct.getString("type");
            String name = jsonProduct.getString("name");
            Product p = new Product(name, id, null, type);
            products.add(p);
        }

        mProductList.clear();

        if(idCampaign == -1){

            mProductList.add(new Product("Dinheiro", 0, null ,"R$"));
        }

        mProductList.addAll(products);
        mProductAdapter.notifyDataSetChanged();
    }

    public void checkoutMoney() {
        Intent i = new Intent(this, MoneyDonation.class);
        startActivity(i);
    }

    private void showError(Exception e) {
        Snackbar.make(productsLayout, "Erro inesperado. Tente novamente.", BaseTransientBottomBar.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    private void showError(String errorMessage) {
        Snackbar.make(productsLayout, errorMessage, BaseTransientBottomBar.LENGTH_SHORT).show();
    }

}
