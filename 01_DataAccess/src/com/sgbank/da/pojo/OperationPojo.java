package com.sgbank.da.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Dorian ALADEL
 * @since 09/01/2018
 */
public class OperationPojo implements Serializable {

    private long identifier;
    private String entitled;
    private String type;
    private LocalDateTime date;
    private double amount;
    private long fk_account_identifier;

    /**
     * Constructor
     * */
    public OperationPojo()
    {
    }

    /**
     * Constructor
     * @param identifier
     * @param entitled
     * @param type
     * @param date
     * @param amount
     * @param fk_account_identifier
     * */
    public OperationPojo(long identifier, String entitled, String type, LocalDateTime date, double amount, long fk_account_identifier) {
        this.identifier = identifier;
        this.entitled = entitled;
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.fk_account_identifier = fk_account_identifier;
    }

    /**
     * Getters & Setters
     * */
    public long getIdentifier() {
        return identifier;
    }

    public String getEntitled() {
        return entitled;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public double getFkAccountIdentifier() {
        return fk_account_identifier;
    }

    public void setIdentifier(long identifier) {
        this.identifier = identifier;
    }

    public void setEntitled(String entitled) {
        this.entitled = entitled;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setFkAccountIdentifier(long fk_account_identifier) {
        this.fk_account_identifier = fk_account_identifier;
    }

    /**
     * @return object as a string
     * */
    @Override
    public String toString() {
        return "Operation{" +
                "identifier='" + identifier + '\'' +
                ", entitled='" + entitled + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", amount='" + amount + '\'' +
                ", fk_account_identifier='" + fk_account_identifier +'\''+
                '}';
    }
}