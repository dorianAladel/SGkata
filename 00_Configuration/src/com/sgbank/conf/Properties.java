package com.sgbank.conf;

/**
 * @author Dorian ALADEL
 * @since 09/01/2018
 */
public class Properties {

    /**
     *  DATABASE
     */
    public static final String PG_PORT_NUMBER = "5432";
    public static final String PG_HOSTNAME = "127.0.0.1";
    public static final String PG_SG_BANK_DATABASE = "db_sgbank";
    public static final String PG_SG_BANK_URL = "jdbc:postgresql://" + PG_HOSTNAME + ":" + PG_PORT_NUMBER + "/" + PG_SG_BANK_DATABASE;
    public static final String PG_SG_BANK_PASS = "toto";
    public static final String PG_SG_BANK_USER = "postgres";

    public static String PDF_DIRECTORY = "c:/Users/User/Documents/generatedPdfSG";

    private Properties() {
    }
}