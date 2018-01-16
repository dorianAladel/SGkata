package com.sgbank.da.dao;

import com.sgbank.da.database.ConnectionBdSgBank;
import com.sgbank.da.pojo.UserPojo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.sgbank.da.dao.DAOUtil.initPreparedRequest;

/**
 * @author Dorian ALADEL
 * @since 09/01/2018
 */
public class UserDao extends DAO_SGBank<UserPojo> {

    /*Prepared requests */
    private static final String selectUserByLogin = "SELECT * FROM t_user WHERE login = ?";
    private static final String addUser = "INSERT INTO t_user (login, last_name, first_name) values (?, ?, ?)";

    public UserDao() {
    }

    /**
     * Mapping
     * @param resultSet
     * @return user
     */
    private static UserPojo map(ResultSet resultSet) throws SQLException {

        UserPojo user = new UserPojo();

        user.setLogin(resultSet.getString("login"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));

        return user;
    }

    /**
     * Look for user
     * @param login
     * @return user found
     */
    public UserPojo find(String login) throws IllegalArgumentException, DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserPojo user = new UserPojo();

        try {
            /* we get one database connection*/
            connexion = ConnectionBdSgBank.getInstance();
            preparedStatement = initPreparedRequest(connexion, selectUserByLogin, false, login);
            resultSet = preparedStatement.executeQuery();

            /* we browse the data line of the eventual returned ResultSet  */
            if (resultSet.next())
                user = map(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        return user;
    }

    /**
     * Find User
     * @param id
     */
    @Override
    public UserPojo find(long id) {
        UserPojo user = new UserPojo();
        return user;
    }

    /**
     * Create User
     * @param obj
     * @return the user created
     */
    @Override
    public UserPojo create(UserPojo obj) {
        Connection connexion = null;
        PreparedStatement pstmt_add = null;

        try {
            connexion = ConnectionBdSgBank.getInstance();

            pstmt_add = connexion.prepareStatement(addUser);

            pstmt_add.setObject(1, obj.getLogin());
            pstmt_add.setObject(2, obj.getLastName());
            pstmt_add.setObject(3, obj.getFirstName());
            pstmt_add.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return obj;
    }

    /**
     * Update User
     * @param obj
     */
    @Override
    public void update(UserPojo obj) {
    }

    /**
     * Delete User
     * @param id
     */
    @Override
    public void delete(long id) {
    }
}