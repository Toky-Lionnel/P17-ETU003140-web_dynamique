package Connexion;

import java.sql.*;
import java.util.*;

public class UtilDB
{
    private String url, user, pw;
    private Connection connection;
    private Statement statement;

    public UtilDB() {
        this.url = "jdbc:mysql://172.80.237.54:3306/db_s2_ETU003140";
        this.user = "ETU003140";
        this.pw = "Lgx3KCEP";
    }

    public Connection connect() 
    {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, pw);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void close() 
    {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}