package com.sgbank.da.pojo;

import java.io.Serializable;

/**
 * @author Dorian ALADEL
 * @since 09/01/2018
 */
public class AccountPojo implements Serializable {

    private long identifier;
    private String fk_user_login;
    private double balance;
    private double authorizedOverdraft;

    /**
     * Constructor
     * */
    public AccountPojo()
    {
    }

    /**
     * Constructor
     * @param identifier
     * @param fk_user_login
     * @param balance
     * @param authorizedOverdraft
     * */
    public AccountPojo(long identifier, String fk_user_login, double balance, double authorizedOverdraft) {
        this.identifier = identifier;
        this.fk_user_login = fk_user_login;
        this.balance = balance;
        this.authorizedOverdraft = authorizedOverdraft;
    }

    /**
     * Getters & Setters
     * */
    public long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(long identifier) {
        this.identifier = identifier;
    }

    public String getFk_user_login() {
        return fk_user_login;
    }

    public void setFk_user_login(String fk_user_login) {
        this.fk_user_login = fk_user_login;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAuthorizedOverdraft() {
        return authorizedOverdraft;
    }

    public void setAuthorizedOverdraft(double authorizedOverdraft) {
        this.authorizedOverdraft = authorizedOverdraft;
    }

    /**
     * @return object as a string
     * */
    @Override
    public String toString() {
        return "Account{" +
                "identifier='" + identifier + '\'' +
                ", fk_user_login='" + fk_user_login + '\'' +
                ", balance='" + balance + '\'' +
                ", authorizedOverdraft='" + authorizedOverdraft + '\'' +
                '}';
    }
}
