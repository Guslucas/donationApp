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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.faj.project.donationapp.model.Company;
import br.com.faj.project.donationapp.model.Donator;


public class SignInCompanyFragment extends Fragment implements SignInForm {

    private EditText cnpjET;
    private EditText nomeET;
    private EditText razaoSocialET;
    private EditText dataFundacaoET;

    private TextInputLayout cnpjIL;
    private TextInputLayout nomeIL;
    private TextInputLayout razaoSocialIL;
    private TextInputLayout dataFundacaoIL;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin_company, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadUI(view);
    }

    private void loadUI(View view) {
        cnpjET = view.findViewById(R.id.cnpjEditText);
        nomeET = view.findViewById(R.id.nomeEditText);
        razaoSocialET = view.findViewById(R.id.razaoSocialEditText);
        dataFundacaoET = view.findViewById(R.id.dataFundacaoEditText);

        cnpjIL = view.findViewById(R.id.cnpjInputLayout);
        nomeIL = view.findViewById(R.id.nomeInputLayout);
        razaoSocialIL = view.findViewById(R.id.razaoSocialInputLayout);
        dataFundacaoIL = view.findViewById(R.id.dataFundacaoInputLayout);

    }

    @Override
    public boolean validate() {
        boolean isValid = true;

        if (!(EditTextUtil.validateEditText(cnpjIL, cnpjET) &
                EditTextUtil.validateEditText(nomeIL, nomeET) &
                EditTextUtil.validateEditText(razaoSocialIL,razaoSocialET)&
                EditTextUtil.validateEditText(dataFundacaoIL, dataFundacaoET))){
            isValid = false;
        }


        return isValid;
    }

    @Override
    public Donator extract(Donator d) {

        Company c = (Company) d;

        String cnpj = cnpjET.getText().toString();
        String nome = nomeET.getText().toString();
        String razaoSocial = razaoSocialET.getText().toString();
        String dataFundacaoString = dataFundacaoET.getText().toString();

        Date dataFundacao = null;
        try {
            dataFundacao = new SimpleDateFormat("dd/MM/yyyy").parse(dataFundacaoString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.setCnpj(cnpj);
        c.setTradingName(nome);
        c.setCompanyName(razaoSocial);
        c.setFoundationDate(dataFundacao);

        return c;
    }
}
