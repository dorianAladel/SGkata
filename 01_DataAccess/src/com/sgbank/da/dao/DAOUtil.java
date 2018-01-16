package com.sgbank.da.dao;

import java.sql.*;

/**
 * @author Dorian ALADEL
 * @since 09/01/2018
 */
public class DAOUtil {
    /**
     * Hidden constructor by default
     * because it's a final class utility,
     * containing only methods called statically
     */
    private DAOUtil() { }

    /**
     * Silent closing of the resultset
     *  */
    public static void silentClosing( ResultSet resultSet )
    {
        if (resultSet != null)
            try { resultSet.close(); }
            catch ( SQLException e ) { System.out.println( "Échec de la fermeture du ResultSet : " + e.getMessage() ); }
    }

    /**
     * Silent closing of the statement
     *  */
    public static void silentClosing( Statement statement )
    {
        if ( statement != null )
            try { statement.close(); }
            catch ( SQLException e ) { System.out.println( "Échec de la fermeture du Statement : " + e.getMessage() ); }
    }

    /**
     * Silent closing of the connection
     *  */
    public static void silentClosing( Connection connexion )
    {
        if ( connexion != null )
            try { connexion.close(); }
            catch ( SQLException e ) { System.out.println( "Échec de la fermeture de la connexion : " + e.getMessage() ); }
    }

    /**
     * Silent closing of the resulset, statement and connection
     *  */
    public static void silentClosings( ResultSet resultSet, Statement statement, Connection connexion )
    {
        silentClosing( resultSet );
        silentClosing( statement );
        silentClosing( connexion );
    }

    /**
     * Initialize the prepared request based on the connection argument
     * with the SQL request
     */
    public static PreparedStatement initPreparedRequest( Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException
    {
        PreparedStatement preparedStatement = connexion.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
        for ( int i = 0; i < objets.length; i++ )
            preparedStatement.setObject( i + 1, objets[i] );

        return preparedStatement;
    }
}