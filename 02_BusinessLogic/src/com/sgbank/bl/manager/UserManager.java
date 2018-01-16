package com.sgbank.bl.manager;

import com.sgbank.da.dao.UserDao;
import com.sgbank.da.pojo.UserPojo;
import com.sun.istack.internal.NotNull;

/**
 * @author Dorian ALADEL
 * @since 09/01/2018
 */
public class UserManager {

    /**
     * User instance
     * @param login
     * @param lastName
     * @param firstName
     * @return user
     */
    public static UserPojo instantiateUser(@NotNull String login, String lastName, String firstName) {

        UserPojo user = new UserPojo();

        user.setLogin(login);
        user.setLastName(lastName);
        user.setFirstName(firstName);

        return user;
    }

    /**
     * Look for a user
     * @param login
     * @return user
     */
    public static UserPojo FindUserByLogin(String login)
    {
        UserDao userDAO = new UserDao();
        UserPojo u = userDAO.find(login);
        return u;
    }

    /**
     * Adding a user in database
     * @param user_pojo
     * @return the user added
     */
    public static UserPojo addUser(UserPojo user_pojo)
    {
        UserDao userDao = new UserDao();

        UserPojo user = userDao.create(user_pojo);

        return user;
    }

}
