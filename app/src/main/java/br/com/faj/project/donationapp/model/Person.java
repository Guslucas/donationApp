package br.com.faj.project.donationapp.model;

import androidx.annotation.NonNull;

import java.util.Date;

public class Person extends Donator {

    String cpf;
    String name;
    String surname;
    Date birthDate;

    public Person(long id, String email, String password, String bio, Address address, String cpf, String name, String surname, Date birthDate) {
        super(id, email, password, bio, address);
        this.cpf = cpf;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    public Person(String email, String password, String bio) {
        super(email, password, bio);
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

}
