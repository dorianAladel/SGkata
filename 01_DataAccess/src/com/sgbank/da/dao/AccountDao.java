package com.sgbank.da.dao;

import com.sgbank.da.database.ConnectionBdSgBank;
import com.sgbank.da.pojo.AccountPojo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.sgbank.da.dao.DAOUtil.initPreparedRequest;

/**
 * @author Dorian ALADEL
 * @since 09/01/2018
 */
public class AccountDao extends DAO_SGBank<AccountPojo> {

    /*Prepared requests */
    private static final String selectAccountById = "SELECT * FROM t_account WHERE identifier = ?";
    private static final String selectAccounts = "SELECT * FROM t_account WHERE fk_user_login = ? ORDER BY identifier";
    private static final String countAccounts = "SELECT COUNT(*) FROM t_account WHERE fk_user_login = ?";
    private static final String addAccount = "INSERT INTO t_account (identifier, fk_user_login, balance, overdraft_authorized) values (nextval('t_account_identifier_seq'::regclass), ?, ?, ?)";
    private static final String updateAccount = "UPDATE t_account SET balance=?, overdraft_authorized = ? WHERE identifier=?";

    public AccountDao() {
    }

    /**
     * Mapping
     * @param resultSet
     * @return account pojo
     */
    private static AccountPojo map(ResultSet resultSet) throws SQLException {

        AccountPojo account = new AccountPojo();

        account.setIdentifier(resultSet.getLong("identifier"));
        account.setFk_user_login(resultSet.getString("fk_user_login"));
        account.setBalance(resultSet.getDouble("balance"));
        account.setAuthorizedOverdraft(resultSet.getDouble("overdraft_authorized"));

        return account;
    }

    /**
     * Find account by identifier
     * @param identifier
     * @return account pojo
     */
    @Override
    public AccountPojo find(long identifier) {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        AccountPojo accountPojo = new AccountPojo();

        try
        {
            /* we get one database connection*/
            connexion = ConnectionBdSgBank.getInstance();

            preparedStatement = initPreparedRequest(connexion, selectAccountById, false, identifier);
            resultSet = preparedStatement.executeQuery();

            /* we browse the data line of the eventual returned ResultSet  */
            if (resultSet.next())
                accountPojo = map( resultSet );
        }
        catch ( SQLException e )
        {
            throw new DAOException( e );
        }
        catch (ClassNotFoundException e1)
        {
            e1.printStackTrace();
        }
        return accountPojo;
    }

    /**
     * Create a new account
     * @param obj
     * @return account pojo
     */
    @Override
    public AccountPojo create(AccountPojo obj) {

        Connection connexion = null;
        PreparedStatement pstmt_add = null;

        try
        {
            /* we get one database connection*/
            connexion = ConnectionBdSgBank.getInstance();

            pstmt_add = connexion.prepareStatement(addAccount);

            pstmt_add.setObject(1, obj.getFk_user_login());
            pstmt_add.setObject(2, obj.getBalance());
            pstmt_add.setObject(3, obj.getAuthorizedOverdraft());

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
     * Update of account balance
     * @param obj
     */
    @Override
    public void update(AccountPojo obj) {
        Connection connection = null;
        PreparedStatement pstmt_add = null;
        try
        {
            connection = ConnectionBdSgBank.getInstance();

            pstmt_add = connection.prepareStatement(updateAccount);

            pstmt_add.setObject(1, obj.getBalance());
            pstmt_add.setObject(2, obj.getAuthorizedOverdraft());
            pstmt_add.setObject(3, obj.getIdentifier());

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
    }

    /**
     * Inutile ici
     * @param id
     */
    @Override
    public void delete(long id) {
    }

    /**
     * Get  all user accounts
     * @param login
     * @return withdrawal operation list
     */
    public ArrayList<AccountPojo> selectAccounts(String login) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<AccountPojo> accountPojos = new ArrayList<AccountPojo>();

        try {
            /* we get one database connection*/
            connexion = ConnectionBdSgBank.getInstance();
            preparedStatement = initPreparedRequest(connexion, selectAccounts, false, login);
            resultSet = preparedStatement.executeQuery();

            /* we browse the data line of the eventual returned ResultSet  */
            while (resultSet.next())
                accountPojos.add(map(resultSet));
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return accountPojos;
    }

    /**
     * Count all accounts
     * @param login
     * @return account number
     */
    public int countAccounts(String login) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int operations_nb = -1;

        try {
            /* we get one database connection*/
            connexion = ConnectionBdSgBank.getInstance();
            preparedStatement = initPreparedRequest(connexion, countAccounts, false, login);
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
}