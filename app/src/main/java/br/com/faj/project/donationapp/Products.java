package br.com.faj.project.donationapp;

import androidx.appcompat.app.AppCompatActivity;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import br.com.faj.project.donationapp.model.Product;

public class Products extends AppCompatActivity implements DonateMoney {

    List<Product> mProductList = new ArrayList<Product>();
    RecyclerView mProductRecycler;
    ProductAdapter mProductAdapter;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        loadProducts();

        mProductRecycler = findViewById(R.id.productsRecycler);
        mProductAdapter = new ProductAdapter(mProductList, this);

        mProductRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mProductRecycler.setLayoutManager(new LinearLayoutManager(this));
        mProductRecycler.setItemAnimator(new DefaultItemAnimator());

        mProductRecycler.setAdapter(mProductAdapter);

        queue = Volley.newRequestQueue(this);
    }

    private void loadProducts() {
        Bitmap night = BitmapFactory.decodeResource(this.getResources(), R.drawable.night);
        mProductList.add(new Product("Money", "We need it.", night, ""));
        mProductList.add(new Product("Milk", "We accept.", night, "1L"));

        /*String url = getResources().getString(R.string.url);
        url += "/campaigns"; //TODO alterar para In
        Log.i("URL sendo usada", url);

        StringRequest requestCampaigns = new StringRequest(Request.Method.GET, url, null, null);

        queue.add(requestCampaigns);*/


    }

    public void checkoutMoney() {
        Intent i = new Intent(this, MoneyDonation.class);
        startActivity(i);
    }

}
