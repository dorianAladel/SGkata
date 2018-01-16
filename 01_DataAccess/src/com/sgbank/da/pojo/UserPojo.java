package com.sgbank.da.pojo;

import java.io.Serializable;

/**
 * @author Dorian ALADEL
 * @since 09/01/2018
 */
public class UserPojo implements Serializable {

    private String login;
    private String lastName;
    private String firstName;

    /**
     * Constructor
     * */
    public UserPojo()
    {
    }

    /**
     * Constructor
     * @param login
     * @param lastName
     * @param firstName
     * */
    public UserPojo(String login, String lastName, String firstName) {
        this.login = login;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    /**
     * Getters & Setters
     * */
    public String getLogin() { return login; }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return object as a string
     * */
    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", nom='" + lastName + '\'' +
                ", prenom='" + firstName + '\'' +
                '}';
    }
}

