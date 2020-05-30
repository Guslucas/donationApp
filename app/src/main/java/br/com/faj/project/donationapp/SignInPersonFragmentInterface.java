package br.com.faj.project.donationapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.faj.project.donationapp.model.Donator;
import br.com.faj.project.donationapp.model.Person;


public class SignInPersonFragmentInterface extends Fragment implements SignInFormInterface {

    private EditText cpfET;
    private EditText nomeET;
    private EditText sobrenomeET;
    private EditText dataNascimentoET;

    private TextInputLayout cpfIL;
    private TextInputLayout nomeIL;
    private TextInputLayout sobrenomeIL;
    private TextInputLayout dataNascimentoIL;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin_person, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadUI(view);

    }

    private void loadUI(View view) {
        cpfET = view.findViewById(R.id.cpfEditText);
        nomeET = view.findViewById(R.id.nomeEditText);
        sobrenomeET = view.findViewById(R.id.sobrenomeEditText);
        dataNascimentoET = view.findViewById(R.id.dataNascimentoEditText);

        cpfIL = view.findViewById(R.id.cpfInputLayout);
        nomeIL = view.findViewById(R.id.nomeInputLayout);
        sobrenomeIL = view.findViewById(R.id.sobrenomeInputLayout);
        dataNascimentoIL = view.findViewById(R.id.dataNascimentoInputLayout);
    }

    @Override
    public boolean validate() {
        boolean isValid = true;

        if (!(ValidateEditText.validateEditText(cpfIL, cpfET) &
                ValidateEditText.validateEditText(nomeIL, nomeET) &
                ValidateEditText.validateEditText(sobrenomeIL,sobrenomeET)&
                ValidateEditText.validateEditText(dataNascimentoIL, dataNascimentoET))) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public Donator extract(Donator d) {

        Person p = (Person) d;

        String cpf = cpfET.getText().toString();
        String nome = nomeET.getText().toString();
        String sobrenome = sobrenomeET.getText().toString();

        String dataNascimentoString = dataNascimentoET.getText().toString();

        Date dataNascimento = null;
        try {
            dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(dataNascimentoString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        p.setCpf(cpf);
        p.setName(nome);
        p.setSurname(sobrenome);
        p.setBirthDate(dataNascimento);

        return p;
    }
}
