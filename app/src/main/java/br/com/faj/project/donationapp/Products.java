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
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.faj.project.donationapp.model.Product;

public class Products extends AppCompatActivity implements DonateMoney {

    List<Product> mProductList = new ArrayList<Product>();
    RecyclerView mProductRecycler;
    ProductAdapter mProductAdapter;

    RequestQueue queue;

    ExtendedFloatingActionButton finalizarFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);


        mProductRecycler = findViewById(R.id.productsRecycler);
        mProductAdapter = new ProductAdapter(mProductList, this);



        mProductRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mProductRecycler.setLayoutManager(new LinearLayoutManager(this));
        mProductRecycler.setItemAnimator(new DefaultItemAnimator());

        mProductRecycler.setAdapter(mProductAdapter);

        loadProducts();


        finalizarFAB = findViewById(R.id.finalizarFAB);
        finalizarFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishDonation();
            }
        });

        queue = Volley.newRequestQueue(this);
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
                    //produto.put("id", mProductList.get(i).getId());
                    produtos.put(produto);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(produtos.toString());
    }

    private void loadProducts() {
        Bitmap night = BitmapFactory.decodeResource(this.getResources(), R.drawable.night);
        mProductList.add(new Product("Money", "We need it.", night, ""));
        mProductList.add(new Product("Milk", "We accept.", night, "1L"));
        mProductList.add(new Product("Coffee", "We accept.", night, "3kg"));
        mProductList.add(new Product("Chocolate", "We accept.", night, "200g"));

        /*String url = getResources().getString(R.string.url);
        url += "/campaigns"; //TODO alterar para In
        Log.i("URL sendo usada", url);

        StringRequest requestCampaigns = new StringRequest(Request.Method.GET, url, null, null);

        queue.add(requestCampaigns);*/
        mProductAdapter.notifyDataSetChanged();

    }

    public void checkoutMoney() {
        Intent i = new Intent(this, MoneyDonation.class);
        startActivity(i);
    }

}
