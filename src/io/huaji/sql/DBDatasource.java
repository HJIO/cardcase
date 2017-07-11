package io.huaji.sql;

import io.huaji.util.X;

import java.sql.*;

public class DBDatasource {

    private Connection connection;
    private String databaseName;
    private String userName;
    private String userPasswords;
    private String dbName;
    private String hostName = "localhost";
    private String dbType = "mysql";
    private int port = 3306;
    private String characterEncoding = "utf-8";
    private Boolean userSSL = false;
    private Boolean useUnicode = true;
    private Statement statement;

    boolean isProcessing = false;

    public DBDatasource() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ioE) {
            X.print("JDBC DRIVER ERROR");
        }
    }

    public boolean quickConnect(String dbName, String userName, String userPasswords) {

        String completeUrl = "jdbc:" + dbType + "://" + hostName + ":" + port + "/" + dbName
                + "?useSSL=" + (userSSL ? "true" : "false") + "&characterEncoding="
                + characterEncoding + "&useUnicode=" + (useUnicode ? "true" : "false");
        try {
            connection = DriverManager.getConnection(completeUrl, userName, userPasswords);
            statement = connection.createStatement();
            return true;
        } catch (SQLException sqlE) {
            X.print("DB CONNECT FAILED");
            X.print(completeUrl);
            return false;
        }
    }

    private int rowCount = 0;
    private int columnCount = 0;
    private ResultSet resultSet = null;
    private Boolean hasUpdated = false;

    final int UPDATE = 0;
    final int EXEC = 1;

    void process(String sql, int processID) {

        while (isProcessing) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException iE) {
                X.print("Thread ERROR");
            }
            process(sql, processID);
        }
        isProcessing = true;
        try {
            switch (processID) {
                case UPDATE:
                    update(sql);
                    break;
                case EXEC:
                    exec(sql);
                    break;
                default:
                    X.print("processID not found.");
                    break;
            }
            hasUpdated = true;
        } catch (SQLException sqlE) {
            X.print("An error occurred while processing.");
            isProcessing = false;
            hasUpdated = false;
        }
    }

    private void update(String sql) throws SQLException {
        rowCount = statement.executeUpdate(sql);
        X.print(rowCount +" " + (rowCount == 1 || rowCount == 0 ? "row affected." : "rows affected."));
    }

    private void exec(String sql) throws SQLException {
        resultSet = statement.executeQuery(sql);
        int currRowCount = 0;
        while(resultSet.next())
            currRowCount++;
        resultSet.first();
        rowCount = currRowCount;
        ResultSetMetaData rsmd = resultSet.getMetaData();
        columnCount = rsmd.getColumnCount();
        X.print(rowCount +" " + ((rowCount == 1 || rowCount == 0 ? "row" : "rows")+" found in "+columnCount+" columns."));

    }

    int getRowCount() {
        int t_rowCount = rowCount;
        rowCount = 0;
        return t_rowCount;
    }
    int getColumnCount() {
        int t_columnCount = columnCount;
        columnCount = 0;
        return t_columnCount;
    }

    Boolean getHasUpdated() {
        Boolean t_hasUpdated = hasUpdated;
        hasUpdated = false;
        return t_hasUpdated;
    }


    ResultSet getResultSet() {
        return resultSet;
    }

    public boolean close() {
        try {
            statement.close();
            connection.close();
            return true;
        } catch (SQLException sqlE) {
            X.print("CONNECTION CLOSE FAILED");
            return false;
        }
    }

    public static void main(String args[]) {
        DBDatasource dbDatasource = new DBDatasource();
        dbDatasource.quickConnect("cardcase", "cardcase", "Zzxcardcase");
        DBManager dbManager = new DBManager(dbDatasource);
        DBCommand test1 = new DBCommand("Update card set card_tel = \"7234561\", card_sex= \"?\", card_position= \"?\", card_corp= \"CEwwO\" where card_name = \"test\"");
        dbManager.send(test1);
        dbManager.free();
    }
}
