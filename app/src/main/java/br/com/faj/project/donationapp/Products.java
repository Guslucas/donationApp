package br.com.faj.project.donationapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import br.com.faj.project.donationapp.model.Product;

public class Products extends AppCompatActivity implements CanDonateMoney {

    private List<Product> mProductList = new ArrayList<Product>();
    private RecyclerView mProductRecycler;
    private ProductAdapter mProductAdapter;
    private CoordinatorLayout productsLayout;
    private long idCampaign;

    private RequestQueue queue;

    private ExtendedFloatingActionButton finalizarFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);


        queue = Volley.newRequestQueue(this);

        idCampaign = getIntent().getLongExtra("ID_CAMPAIGN", -1);

        loadProducts();

        mProductRecycler = findViewById(R.id.productsRecycler);
        mProductAdapter = new ProductAdapter(mProductList, this);


        mProductRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mProductRecycler.setLayoutManager(new LinearLayoutManager(this));
        mProductRecycler.setItemAnimator(new DefaultItemAnimator());

        mProductRecycler.setAdapter(mProductAdapter);


        productsLayout = findViewById(R.id.productLayout);


        finalizarFAB = findViewById(R.id.finalizarFAB);
        finalizarFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishDonation();
            }
        });

        queue = Volley.newRequestQueue(this);
    }

    private void loadProducts() {

        String url = getResources().getString(R.string.url);

        if (idCampaign == -1) {

            url += "/product";
        } else {

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

    private void finishDonation() {
        JSONArray produtos = new JSONArray();

        for (int i = 0; i < mProductRecycler.getChildCount(); i++) {
            ProductAdapter.ProductItemHolder holder = (ProductAdapter.ProductItemHolder) mProductRecycler.findViewHolderForAdapterPosition(i);

            JSONObject produto = new JSONObject();

            int qtd = holder.mQuantity.getValue();

            if (qtd > 0) {
                try {
                    produto.put("qtd", qtd);
                    produto.put("id", mProductList.get(i).getId());
                    produtos.put(produto);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(produtos.toString());
    }

    private void listProducts(String response) throws JSONException {

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

        if (idCampaign == -1) {

            mProductList.add(new Product("Dinheiro", 0, null, "R$"));
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
