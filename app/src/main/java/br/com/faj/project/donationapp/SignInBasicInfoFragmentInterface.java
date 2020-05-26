package br.com.faj.project.donationapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import br.com.faj.project.donationapp.model.DonatorType;


public class SignInBasicInfoFragmentInterface extends Fragment implements SignInFormInterface {

    EditText emailET;
    EditText telefoneET;
    EditText senhaET;
    EditText confirmaSenhaET;
    TextInputLayout emailIL;
    TextInputLayout senhaIL;
    TextInputLayout telefoneIL;
    TextInputLayout confirmaSenhaIL;

    Spinner typeSpinner;


    private void loadUI(View view) {

        emailET = view.findViewById(R.id.emailEditText);
        telefoneET = view.findViewById(R.id.telefoneEditText);
        senhaET = view.findViewById(R.id.senhaEditText);
        confirmaSenhaET = view.findViewById(R.id.confirmaSenhaEditText);
        emailIL = view.findViewById(R.id.emailInputLayout);
        senhaIL = view.findViewById(R.id.senhaInputLayout);
        telefoneIL = view.findViewById(R.id.telefoneInputLayout);
        confirmaSenhaIL = view.findViewById(R.id.confirmaSenhaInputLayout);


        typeSpinner = view.findViewById(R.id.spinnerJuridicaFisica);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin_basic_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUI(view);
    }



    public DonatorType getType() {
        if (typeSpinner.getSelectedItemPosition() == 0) {
            return DonatorType.PERSON;
        }
        return DonatorType.COMPANY;
    }

    @Override
    public boolean validate() {
        boolean isValid = true;

        if (!(ValidateEditText.validateEditText(emailIL, emailET) &
                ValidateEditText.validateEditText(telefoneIL, telefoneET) &
                ValidateEditText.validatePassword(senhaIL, senhaET, confirmaSenhaIL, confirmaSenhaET))) {
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("Estou pausando");
    }
}
