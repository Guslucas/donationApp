package br.com.faj.project.donationapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.faj.project.donationapp.model.Product;

class ObjectiveAdapter extends RecyclerView.Adapter<ObjectiveAdapter.ObjectiveItemHolder> {

    ArrayAdapter<String> spinnerAdapter;
    AddCampaignAdm callingActivity;
    List<String> productList;

    public ObjectiveAdapter(List<String> productList, AddCampaignAdm callingActivity) {
        this.productList = productList;
        this.callingActivity = callingActivity;
        spinnerAdapter = new ArrayAdapter<>(callingActivity.getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, productList);
    }

    @NonNull
    @Override
    public ObjectiveItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.objective_item, parent, false);
        return new ObjectiveItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObjectiveItemHolder holder, int position) {

        holder.mSpinner.setAdapter(spinnerAdapter);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ObjectiveItemHolder extends RecyclerView.ViewHolder {
        Spinner mSpinner;
        NumberPicker mQuantity;

        public ObjectiveItemHolder(@NonNull View itemView) {
            super(itemView);
            mSpinner = itemView.findViewById(R.id.spinner);
            mQuantity = itemView.findViewById(R.id.quantity);
            mQuantity.setMaxValue(10);
            mQuantity.setMinValue(0);
            mQuantity.setValue(0);
        }
    }
}
