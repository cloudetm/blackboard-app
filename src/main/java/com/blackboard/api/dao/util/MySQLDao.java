package com.blackboard.api.dao.util;

import com.mysql.jdbc.Driver;
import com.sun.media.jfxmedia.logging.Logger;

import java.sql.*;
import java.util.Calendar;
import java.util.Optional;

/**
 * This is the MySQL Utility Class, which will help prepare secure MySQL Queries and execute them.
 *
 * @author Timothy Kim <timothyk@gwu.edu>
 * @author ChristopherLicata <cmlicata@gwmail.gwu.edu>
 */
public class MySQLDao
{
    private Connection connection = null;

    private Driver driver;

    /**
     * This data is being retrieved via the external configuration .yml file
     */
    private String db;



    /**
     * Constructs and prepares the SQL Statement with the given parameters.
     * <p/>
     * As compared to executing SQL statements directly, prepared statements offer two main advantages: <ol>
     * <li>The overhead of compiling and optimizing the statement is incurred only once, although the
     * statement is executed multiple times. Not all optimization can be performed at the time the prepared
     * statement is compiled, for two reasons: the best plan may depend on the specific values of the
     * parameters, and the best plan may change as tables and indexes change over time. </li> <li>Prepare
     * statements are resilient against SQL injection, because parameter values, which are transmitted later
     * using a different protocol, need not be correctly escaped. If the original statement template is not
     * derived from external input, SQL injection cannot occur.</li> </ol>
     *
     * @param connection The connection to the database being utilized.
     * @param sql        The MySQL statement being prepared
     * @param args       The arguments being passed to the parameterized query.
     */
    public PreparedStatement prepareStatement(Connection connection, String sql, Object[] args)
            throws SQLException
    {
        // Return the id of the data row we created, deleted, or update in the DB
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < args.length; i++)
        {
            Object arg = args[i];
            if (arg instanceof String)
            {
                statement.setString(i + 1, (String) arg);
            }
            else if (arg instanceof Integer)
            {
                statement.setInt(i + 1, (Integer) arg);
            }
            else
            {
                statement.setObject(i + 1, arg);
            }
        }
        return statement;
    }


    /**
     * Prepares and executes parameterized create, delete, and update queries with variadic variables.
     *
     * @param sql  The MySQL statement being prepared
     * @param args The variadic variables being passed into the parameters of the query.
     */
    public Optional<ResultSet> update(String sql, Object... args)
    {
        try
        {
            PreparedStatement statement = prepareStatement(connection, sql, args);
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate();
            return Optional.of(statement.getGeneratedKeys());
        }
        catch (SQLException e)
        {
            // Print organized SQL error message
            printSQLException(e);
            return Optional.empty();
        }
    }


    /**
     * Prepares and executes parameterized SELECT queries with variadic variables.
     *
     * @param sql  The MySQL statement being prepared
     * @param args The variadic variables being passed into the parameters of the query.
     */
    public Optional<ResultSet> query(String sql, Object... args)
    {
        try
        {
            PreparedStatement statement = prepareStatement(connection, sql, args);
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            return Optional.of(statement.executeQuery());
        }
        catch (SQLException e)
        {
            // Print organized SQL error message
            printSQLException(e);
            return Optional.empty();
        }
    }


    /**
     * The SQLException instance contains the following information that can help determine the cause of the
     * error:
     * <p/>
     * A description of the error. Retrieve the String object that contains this description by calling the
     * method SQLException.getMessage.
     * <p/>
     * A SQLState code. These codes and their respective meanings have been standardized by ISO/ANSI and Open
     * Group (X/Open), although some codes have been reserved for database vendors to define for themselves.
     * This String object consists of five alphanumeric characters. Retrieve this code by calling the method
     * SQLException.getSQLState.
     * <p/>
     * An error code. This is an integer value identifying the error that caused the SQLException instance to
     * be thrown. Its value and meaning are implementation-specific and might be the actual error code
     * returned by the underlying data source. Retrieve the error by calling the method
     * SQLException.getErrorCode.
     * <p/>
     * A cause. A SQLException instance might have a causal relationship, which consists of one or more
     * Throwable objects that caused the SQLException instance to be thrown. To navigate this chain of causes,
     * recursively call the method SQLException.getCause until a null value is returned.
     * <p/>
     * A reference to any chained exceptions. If more than one error occurs, the exceptions are referenced
     * through this chain. Retrieve these exceptions by calling the method SQLException.getNextException on
     * the exception that was thrown.
     * <p/>
     * Retrieving Exceptions
     * <p/>
     * The following method, JDBCTutorialUtilities.printSQLException outputs the SQLState, error code, error
     * description, and cause (if there is one) contained in the SQLException as well as any other exception
     * chained to it:
     *
     * @param ex The SQLException thrown by the function in question.
     */
    public static void printSQLException(SQLException ex)
    {

        for (Throwable e : ex)
        {
            if (e instanceof SQLException && !ignoreSQLException(((SQLException) e).getSQLState()))
            {

                e.printStackTrace(System.err);
                Logger.logMsg(1, "SQLState: " +
                        ((SQLException) e).getSQLState());

                Logger.logMsg(1, "Error Code: " +
                        ((SQLException) e).getErrorCode());

                Logger.logMsg(1, "Message: " + e.getMessage());

                Throwable t = ex.getCause();
                while (t != null)
                {
                    Logger.logMsg(1, "Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }


    /**
     * Instead of outputting SQLException information, you could instead first retrieve the SQLState then
     * process the SQLException accordingly. For example, the method JDBCTutorialUtilities.ignoreSQLException
     * returns true if the SQLState is equal to code 42Y55 (and you are using Java DB as your DBMS), which
     * causes JDBCTutorialUtilities.printSQLException to ignore the SQLException:
     *
     * @param sqlState The specific "error" message that is being returned by the SQL handlers in Java
     *
     * @return whether or not there is a valid sqlState involving preexisting Jars or Tables.
     */
    public static boolean ignoreSQLException(String sqlState)
    {

        if (sqlState == null)
        {
            System.out.println("The SQL state is not defined!");
            return false;
        }

        // X0Y32: Jar file already exists in schema
        if (sqlState.equalsIgnoreCase("X0Y32"))
        {
            return true;
        }

        // 42Y55: Table already exists in schema
        return sqlState.equalsIgnoreCase("42Y55");

    }


    /**
     * Creates a persistent connection to the Database
     *
     * @param dburl The URI to the database.
     */
    public void setDb(String dburl)
    {
        db = dburl;
        try
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
            }
            catch (ClassNotFoundException e)
            {
                System.out.println(e);
            }

            connection = DriverManager.getConnection(db);
        }
        catch (SQLException e)
        {
            printSQLException(e);
            exitApplication();
        }
    }


    public Timestamp generateTimeStamp()
    {
        // 1) create a java calendar instance
        Calendar calendar = Calendar.getInstance();

        // 2) get a java.util.Date from the calendar instance.
        //    this date will represent the current instant, or "now".
        java.util.Date now = calendar.getTime();

        // 3) a java current time (now) instance
        return new java.sql.Timestamp(now.getTime());
    }

    private static void exitApplication()
    {
        Runtime.getRuntime().exit(0);
    }
}
