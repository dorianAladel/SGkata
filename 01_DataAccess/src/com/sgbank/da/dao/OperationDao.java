package com.sgbank.da.dao;

import com.sgbank.da.database.ConnectionBdSgBank;
import com.sgbank.da.pojo.OperationPojo;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.sgbank.da.dao.DAOUtil.initPreparedRequest;

/**
 * @author Dorian ALADEL
 * @since 09/01/2018
 */
public class OperationDao extends DAO_SGBank<OperationPojo> {

    /*Prepared requests */
    private static final String selectOperationById = "SELECT * FROM t_operation WHERE identifier = ?";
    private static final String selectOperations = "SELECT * FROM t_operation WHERE fk_account_identifier = ? ORDER BY date DESC";
    private static final String selectOperationsByPeriod = "SELECT * FROM t_operation WHERE date >= ? AND date <= ? AND fk_account_identifier = ? ORDER BY date";
    private static final String countOperations = "SELECT COUNT(*) FROM t_operation WHERE fk_account_identifier = ?";
    private static final String countOperationsByPeriod = "SELECT COUNT (*) FROM t_operation WHERE fk_account_identifier = ? AND date >= ? AND date <= ? ORDER BY date";
    private static final String selectWithDrawalOperations = "SELECT * FROM t_operation WHERE fk_account_identifier = ? AND type = ? ORDER BY date DESC";
    private static final String selectDepositOperations = "SELECT * FROM t_operation WHERE fk_account_identifier = ? AND type = ? ORDER BY date DESC";
    private static final String addOperation = "INSERT INTO t_operation (identifier, entitled, type, date, amount, fk_account_identifier) values (nextval('t_operation_identifier_seq'::regclass), ?, ?, ?, ?, ?)";

    public OperationDao() {
    }

    /**
     * Mapping
     * @param resultSet
     * @return operation pojo
     */
    private static OperationPojo map(ResultSet resultSet) throws SQLException {

        OperationPojo operation = new OperationPojo();

        operation.setIdentifier(resultSet.getLong("identifier"));
        operation.setEntitled(resultSet.getString("entitled"));
        operation.setType(resultSet.getString("type"));
        operation.setDate(resultSet.getTimestamp("date").toLocalDateTime());
        operation.setAmount(resultSet.getDouble("amount"));
        operation.setFkAccountIdentifier(resultSet.getLong("fk_account_identifier"));

        return operation;
    }

    /**
     * Find operation by identifier
     * @param identifier
     * @return operation pojo
     */
    @Override
    public OperationPojo find(long identifier) {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        OperationPojo operationPojo = new OperationPojo();

        try
        {
            /* we get one database connection*/
            connexion = ConnectionBdSgBank.getInstance();

            preparedStatement = initPreparedRequest(connexion, selectOperationById, false, identifier);
            resultSet = preparedStatement.executeQuery();

            /* we browse the data line of the eventual returned ResultSet  */
            if (resultSet.next())
                operationPojo = map( resultSet );
        }
        catch ( SQLException e )
        {
            throw new DAOException( e );
        }
        catch (ClassNotFoundException e1)
        {
            e1.printStackTrace();
        }
        return operationPojo;
    }

    /**
     * Create a new operation
     * @param obj
     * @return operation pojo
     */
    @Override
    public OperationPojo create(OperationPojo obj) {

        Connection connexion = null;
        PreparedStatement pstmt_add = null;

        try
        {
            /* we get one database connection*/
            connexion = ConnectionBdSgBank.getInstance();

            pstmt_add = connexion.prepareStatement(addOperation);

            pstmt_add.setObject(1, obj.getEntitled());
            pstmt_add.setObject(2, obj.getType());
            pstmt_add.setObject(3, obj.getDate());
            pstmt_add.setObject(4, obj.getAmount());
            pstmt_add.setObject(5, obj.getFkAccountIdentifier());

            pstmt_add.executeUpdate();
        }
        catch ( SQLException e )
        {
            throw new DAOException( e );
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * Inutile ici
     * @param obj
     */
    @Override
    public void update(OperationPojo obj) {
    }

    /**
     * Inutile ici
     * @param id
     */
    @Override
    public void delete(long id) {
    }

    /**
     * Get  all operations from the bank account
     * @param identifier
     * @return operation list
     */
    public ArrayList<OperationPojo> getAllOperations(long identifier) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<OperationPojo> operations = new ArrayList<OperationPojo>();

        try {
            /* we get one database connection*/
            connexion = ConnectionBdSgBank.getInstance();
            preparedStatement = initPreparedRequest(connexion, selectOperations, false, identifier);
            resultSet = preparedStatement.executeQuery();

            /* we browse the data line of the eventual returned ResultSet  */
            while (resultSet.next())
                operations.add(map(resultSet));
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return operations;
    }

    /**
     * Get  all operations by period from the bank account
     * @param fk_account_identifier
     * @param start
     * @param end
     * @return operation list
     */
    public ArrayList<OperationPojo> getAllOperationsByPeriod(long fk_account_identifier, LocalDateTime start, LocalDateTime end) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<OperationPojo> operations = new ArrayList<OperationPojo>();

        try {
            /* we get one database connection*/
            connexion = ConnectionBdSgBank.getInstance();

            preparedStatement = connexion.prepareStatement(selectOperationsByPeriod);
            preparedStatement.setObject(1, start, Types.DATE);
            preparedStatement.setObject(2, end, Types.DATE);
            preparedStatement.setObject(3, fk_account_identifier);

            resultSet = preparedStatement.executeQuery();

            /* we browse the data line of the eventual returned ResultSet  */
            while (resultSet.next())
                operations.add(map(resultSet));
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return operations;
    }

    /**
     * Count all operations
     * @param fk_account_identifier
     * @return operation number
     */
    public int countOperations(long fk_account_identifier) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int operations_nb = -1;

        try {
            /* we get one database connection*/
            connexion = ConnectionBdSgBank.getInstance();
            preparedStatement = initPreparedRequest(connexion, countOperations, false, fk_account_identifier);
            resultSet = preparedStatement.executeQuery();

            /* we browse the data line of the eventual ResultSet returned */
            if (resultSet.next())
                operations_nb = resultSet.getInt(1);

        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        return operations_nb;
    }

    /**
     * Count all operations by period
     * @param fk_account_identifier
     * @param start
     * @param end
     * @return operation number
     */
    public int countOperationsByPeriod(long fk_account_identifier, LocalDateTime start, LocalDateTime end) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int operations_nb = -1;

        try {
            /* we get one database connection*/
            connexion = ConnectionBdSgBank.getInstance();

            preparedStatement = connexion.prepareStatement(countOperationsByPeriod);
            preparedStatement.setObject(1, fk_account_identifier, Types.BIGINT);
            preparedStatement.setObject(2, start, Types.DATE);
            preparedStatement.setObject(3, end, Types.DATE);
            resultSet = preparedStatement.executeQuery();

            /* we browse the data line of the eventual returned ResultSet  */
            if (resultSet.next())
                operations_nb = resultSet.getInt(1);

        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        return operations_nb;
    }

    /**
     * Get  all withdrawal operations from the bank account
     * @param fk_account_identifier
     * @return withdrawal operation list
     */
    public ArrayList<OperationPojo> getAllWithDrawalOperations(long fk_account_identifier) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<OperationPojo> operations = new ArrayList<OperationPojo>();

        try {
            /* we get one database connection*/
            connexion = ConnectionBdSgBank.getInstance();
            preparedStatement = initPreparedRequest(connexion, selectWithDrawalOperations, false, fk_account_identifier, "retrait");
            resultSet = preparedStatement.executeQuery();

            /* we browse the data line of the eventual returned ResultSet  */
            while (resultSet.next())
                operations.add(map(resultSet));
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return operations;
    }

    /**
     * Get  all deposit operations from the bank account
     * @param fk_account_identifier
     * @return deposit operation list
     */
    public ArrayList<OperationPojo> getAllDepositOperations(long fk_account_identifier) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<OperationPojo> operations = new ArrayList<OperationPojo>();

        try {
            /* we get one database connection*/
            connexion = ConnectionBdSgBank.getInstance();
            preparedStatement = initPreparedRequest(connexion, selectDepositOperations, false, fk_account_identifier, "deposit");
            resultSet = preparedStatement.executeQuery();

            /* we browse the data line of the eventual returned ResultSet  */
            while (resultSet.next())
                operations.add(map(resultSet));
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return operations;
    }
}