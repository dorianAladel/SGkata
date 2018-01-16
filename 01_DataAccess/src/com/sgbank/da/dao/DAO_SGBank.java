package com.sgbank.da.dao;

/**
 * @author Dorian ALADEL
 * @since 09/01/2018
 */
import com.sgbank.da.database.ConnectionBdSgBank;

/**
 * Abstract class for DAO classes
 */
public abstract class DAO_SGBank<T> extends ConnectionBdSgBank {

    /**
     * Return object by id
     * @param id
     * @return the object
     */
    public abstract T find(long id);

    /**
     * Create a new object in database from Pojo
     * @param obj
     * @return the new object
     */
    public abstract T create(T obj);

    /**
     * Update an object d
     * @param obj
     */
    public abstract void update(T obj);

    /**
     * Delete an object from database
     * @param id
     */
    public abstract void delete(long id);


}
