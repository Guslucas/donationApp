package br.com.faj.project.donationapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class SignInBasicInfoFragment extends Fragment {

    Spinner typeSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cadastro_basic_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        typeSpinner = view.findViewById(R.id.spinnerJuridicaFisica);
    }

    public DonatorType getType() {
        if (typeSpinner.getSelectedItemPosition() == 0) {
            return DonatorType.PERSON;
        }
        return DonatorType.COMPANY;
    }
}
