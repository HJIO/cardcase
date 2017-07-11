package io.huaji.sql;

import io.huaji.util.X;

import java.sql.ResultSet;

public class DBCommand implements DBExecutable {

    private DBDatasource dbDatasource = null;
    private ResultSet resultSet = null;
    private int rowCount = 0;
    private int columnCount = 0;
    private String sql;
    private Boolean hasSent = false;

    void setDataSource(DBDatasource dbDatasource) {
        this.dbDatasource = dbDatasource;
    }

    public DBCommand(String sql) {
        this.sql = sql;
        X.print("mysql > " + sql);
    }

    public DBCommand(Object... args) {

        String sql = (String) args[0];
        for (int i = 1; i < args.length; i++) {
            try {
                Class o = args[i].getClass();
                if (o == String.class) sql = sql.replaceFirst("[?]", "\"" + args[i] + "\"");
                else sql = sql.replaceFirst("[?]", args[i].toString());
            } catch (Exception e) {
                X.print("DBCommand ERROR: cannot resolve argument " + i);
            }
        }
        this.sql = sql;
        X.print("mysql > " + sql);

    }

    public void reset(String sql) {
        this.sql = sql;
        DBDatasource dbDatasource;
        ResultSet resultSet = null;
        int rowCount = 0;
        hasSent = false;
    }

    public void reset(Object... args) {
        String sql = (String) args[0];
        for (int i = 1; i < args.length; i++) {
            Class o = args[i].getClass();
            if (o == String.class) sql = sql.replaceFirst("[?]", "\"" + args[i] + "\"");
            else sql = sql.replaceFirst("[?]", args[i].toString());
        }
        this.sql = sql;
        DBDatasource dbDatasource = null;
        ResultSet resultSet = null;
        int rowCount = 0;
        hasSent = false;
    }

    public String getSql() {
        return sql;
    }

    @Override
    public Boolean update() {
        dbDatasource.process(sql, dbDatasource.UPDATE);
        rowCount = dbDatasource.getRowCount();
        Boolean t = dbDatasource.getHasUpdated();
        dbDatasource.isProcessing = false;
        hasSent = true;
        return t;
    }

    @Override
    public ResultSet exec() {
        dbDatasource.process(sql, dbDatasource.EXEC);
        resultSet = dbDatasource.getResultSet();
        dbDatasource.isProcessing = false;
        hasSent = true;
        return resultSet;
    }

    public Boolean hasSent() {
        return hasSent;
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    @Override
    public ResultSet getResultSet() {
        return resultSet;
    }

}
