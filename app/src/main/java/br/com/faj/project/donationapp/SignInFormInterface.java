package br.com.faj.project.donationapp;


import br.com.faj.project.donationapp.model.Donator;

public interface SignInFormInterface {
    boolean validate();

    Donator extract(Donator d);
}
