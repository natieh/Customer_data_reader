package database;

import com.mysql.cj.jdbc.Driver;
import properties.ApplicationProperties;

import java.sql.*;
import java.util.TimeZone;

public class BDConnectionProvider {
    private ApplicationProperties properties;

    public BDConnectionProvider() {
        properties = new ApplicationProperties();
    }

    public Connection getConnection() {
        String URL = "jdbc:mysql://" + properties.getDatabaseHost() + ":" + properties.getDatabasePort() +
                "/" + properties.getDatabaseName() + "?serverTimezone=" + TimeZone.getDefault().getID();

        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = DriverManager.getConnection(URL, properties.getDatabaseUser(), properties.getDatabasePassword());
            return connection;
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }

    private void createDatabase(Connection connection) throws SQLException {
        String sql = "CREATE DATABASE customers";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
    }

    private void createTables(Connection connection) throws SQLException {
        String sql = "CREATE TABLE " + properties.getDatabaseName() + ".CUSTOMERS " +
                " (ID INTEGER not NULL AUTO_INCREMENT, " +
                " NAME VARCHAR(255), " +
                " SURNAME VARCHAR(255), " +
                " AGE INTEGER, " +
                " CITY VARCHAR(100), " +
                " PRIMARY KEY ( ID ))";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);

        sql = "CREATE TABLE " + properties.getDatabaseName() + ".CONTACTS " +
                "(ID INTEGER not NULL AUTO_INCREMENT, " +
                " CUSTOMER_ID INTEGER not NULL, " +
                " TYPE INTEGER, " +
                " CONTACT VARCHAR(255), " +
                " PRIMARY KEY ( ID )," +
                " FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMERS (ID))";

        stmt.executeUpdate(sql);
    }

    public void createDatabase() {
        String URL = "jdbc:mysql://" + properties.getDatabaseHost() + ":" + properties.getDatabasePort() +
                "" + "?serverTimezone=" + TimeZone.getDefault().getID();

        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = DriverManager.getConnection(URL, properties.getDatabaseUser(), properties.getDatabasePassword());
            if (!exist(connection)) {
                createDatabase(connection);
                createTables(connection);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean exist(Connection connection) throws SQLException {
        ResultSet rs = connection.getMetaData().getCatalogs();
        while (rs.next()) {
            String catalogs = rs.getString(1);

            if (properties.getDatabaseName().equals(catalogs)) {
                return true;
            }
        }
        return false;
    }
}
