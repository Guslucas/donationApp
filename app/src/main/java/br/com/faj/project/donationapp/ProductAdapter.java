package br.com.faj.project.donationapp;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductItemHolder> {

    DonateMoney callingActivity;
    List<Product> productList;

    public ProductAdapter(List<Product> productList, DonateMoney callingActivity) {
        this.productList = productList;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public ProductItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemHolder holder, int position) {
        Product product = productList.get(position);

        holder.mName.setText(product.getName());
        holder.mDescription.setText(product.getDescription());
        holder.mImage.setImageBitmap(product.getImage());
        holder.mType.setText("(" + product.getType() + ")");

        if (holder.mName.getText().toString().equals("Money")) {
            holder.mQuantity.setVisibility(View.GONE);
            holder.mType.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImage;
        TextView mName;
        TextView mType;
        TextView mDescription;
        NumberPicker mQuantity;

        public ProductItemHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.imageView);
            mName = itemView.findViewById(R.id.nameTV);
            mDescription = itemView.findViewById(R.id.descriptionTV);
            mQuantity = itemView.findViewById(R.id.quantity);
            mQuantity.setMaxValue(10);
            mQuantity.setMinValue(0);
            mQuantity.setValue(0);
            mType = itemView.findViewById(R.id.typeTV);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mName.getText().toString().equals("Money")) {
                callingActivity.checkoutMoney();
            }
        }
    }
}