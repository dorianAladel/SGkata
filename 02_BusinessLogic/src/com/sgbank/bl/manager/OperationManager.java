package com.sgbank.bl.manager;

import com.sgbank.da.dao.OperationDao;
import com.sgbank.da.pojo.OperationPojo;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author Dorian ALADEL
 * @since 09/01/2018
 */
public class OperationManager {

    /**
     * Get account operations
     * @param identifier
     * @return Operations list
     */
    public static ArrayList<OperationPojo> selectOperations(long identifier)
    {
        OperationDao operationDAO= new OperationDao();
        ArrayList<OperationPojo> operations;

        operations = operationDAO.getAllOperations(identifier);

        return operations;
    }

    /**
     * Get account operations by period
     * @param identifier
     * @param start
     * @param end
     * @return Operations list
     */
    public static ArrayList<OperationPojo> selectOperations(long identifier, LocalDateTime start, LocalDateTime end)
    {
        OperationDao operationDAO= new OperationDao();
        ArrayList<OperationPojo> operations;

        operations = operationDAO.getAllOperationsByPeriod(identifier, start, end);

        return operations;
    }

    /**
     * Get account withdrawal operations
     * @param identifier
     * @return Operations list
     */
    public static ArrayList<OperationPojo> selectWithDrawalOperations(long identifier)
    {
        OperationDao operationDAO= new OperationDao();
        ArrayList<OperationPojo> operations;

        operations = operationDAO.getAllWithDrawalOperations(identifier);

        return operations;
    }

    /**
     * Get account deposit operations
     * @param identifier
     * @return Operations list
     */
    public static ArrayList<OperationPojo> selectDepositOperations(long identifier)
    {
        OperationDao operationDAO= new OperationDao();
        ArrayList<OperationPojo> operations;

        operations = operationDAO.getAllDepositOperations(identifier);

        return operations;
    }

    /**
     * Count bank account operations
     * @param identifier, from bank account
     * @return operation number
     * */
    public static int countOperations(long identifier)
    {
        int count_found_operations=0;
        OperationDao operationDAO = new OperationDao();
        count_found_operations = operationDAO.countOperations(identifier);
        return count_found_operations;
    }

    /**
     * Count bank account operations by period
     * @param identifier, from bank account
     * @param start
     * @param end
     * @return operation number
     * */
    public static int countOperationsByPeriod(long identifier, LocalDateTime start, LocalDateTime end)
    {
        int count_found_operations=0;
        OperationDao operationDAO = new OperationDao();
        count_found_operations = operationDAO.countOperationsByPeriod(identifier, start, end);
        return count_found_operations;
    }

    /**
     * Add an operation in database
     * @param operationPojo
     * @return created operation
     * */
    public static OperationPojo addOperation(OperationPojo operationPojo)
    {
        OperationDao operationDao = new OperationDao();

        OperationPojo operation = operationDao.create(operationPojo);

        return operation;
    }
}
