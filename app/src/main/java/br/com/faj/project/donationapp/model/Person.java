package br.com.faj.project.donationapp.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Person extends Donator {

    private String cpf;
    private String name;
    private String surname;
    private Date birthDate;

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

    //TODO REMOVER DEPOIS DE TESTADO
    public Person(long id) {
        super(id);
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

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put("cpf", cpf);
        jsonObject.put("name", name);
        jsonObject.put("surname", surname);
        jsonObject.put("birthDate", new SimpleDateFormat("yyyy-MM-dd").format(birthDate));

        jsonObject.put("type", "Person");

        return jsonObject;
    }
}
