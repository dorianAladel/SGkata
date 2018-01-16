package com.sgbank.da.database;

import com.sgbank.conf.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Dorian ALADEL
 * @since 09/01/2018
 */
public class ConnectionBdSgBank {

    private static Connection connect;

    /**
     * Method returning database instance
     * */
    public static Connection getInstance() throws ClassNotFoundException {
        if(connect == null)
        {
            try {
                connect = DriverManager.getConnection(
                        Properties.PG_SG_BANK_URL,
                        Properties.PG_SG_BANK_USER,
                        Properties.PG_SG_BANK_PASS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connect;
    }
}