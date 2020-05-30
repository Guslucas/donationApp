package br.com.faj.project.donationapp.model;

import java.util.Date;

public class Company extends Donator {

    String cnpj;
    String tradingName;
    String companyName;
    Date foundationDate;

    public Company(long id, String email, String password, String bio, Address address, String cnpj, String tradingName, String companyName, Date foundationDate) {
        super(id, email, password, bio, address);
        this.cnpj = cnpj;
        this.tradingName = tradingName;
        this.companyName = companyName;
        this.foundationDate = foundationDate;
    }

    public Company(String email, String password, String bio) {
        super(email, password, bio);
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getTradingName() {
        return tradingName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Date getFoundationDate() {
        return foundationDate;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setFoundationDate(Date foundationDate) {
        this.foundationDate = foundationDate;
    }
}
