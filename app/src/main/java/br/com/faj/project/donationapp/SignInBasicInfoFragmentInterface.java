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

import br.com.faj.project.donationapp.model.Company;
import br.com.faj.project.donationapp.model.Donator;
import br.com.faj.project.donationapp.model.DonatorType;
import br.com.faj.project.donationapp.model.Person;


public class SignInBasicInfoFragmentInterface extends Fragment implements SignInFormInterface {

    private EditText emailET;
    private EditText bioET;
    private EditText senhaET;
    private EditText confirmaSenhaET;
    private TextInputLayout emailIL;
    private TextInputLayout senhaIL;
    private TextInputLayout bioIL;
    private TextInputLayout confirmaSenhaIL;

    private Spinner typeSpinner;


    private void loadUI(View view) {

        emailET = view.findViewById(R.id.emailEditText);
        bioET = view.findViewById(R.id.bioEditText);
        senhaET = view.findViewById(R.id.senhaEditText);
        confirmaSenhaET = view.findViewById(R.id.confirmaSenhaEditText);
        emailIL = view.findViewById(R.id.emailInputLayout);
        senhaIL = view.findViewById(R.id.senhaInputLayout);
        bioIL = view.findViewById(R.id.bioInputLayout);
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
                ValidateEditText.validateEditText(bioIL, bioET) &
                ValidateEditText.validatePassword(senhaIL, senhaET, confirmaSenhaIL, confirmaSenhaET))) {
            isValid = false;
        }

        return isValid;
    }

    @Override
    public Donator extract(Donator d) {

        String email = emailET.getText().toString();
        String bio = bioET.getText().toString();
        String senha = senhaET.getText().toString();

        DonatorType tipo = getType();

        if (tipo.equals(DonatorType.PERSON)) {
            d = new Person(email, senha, bio);
        } else if (tipo.equals(DonatorType.COMPANY)) {
            d = new Company(email, senha, bio);
        }
        return d;
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("Estou pausando");
    }
}
