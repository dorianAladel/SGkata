package com.sgbank.da.dao;

/**
 * @author Dorian ALADEL
 * @since 09/01/2018
 */
public class DAOException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /* Constructors */
    public DAOException(String message) { super( message ); }
    public DAOException(String message, Throwable cause) { super( message, cause ); }
    public DAOException(Throwable cause) { super( cause ); }

}
