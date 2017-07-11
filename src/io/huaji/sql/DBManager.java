package io.huaji.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.regex.*;

import io.huaji.util.X;

public class DBManager {

    private DBDatasource dbDatasource;

    public DBManager(DBDatasource dbDatasource) {
        this.dbDatasource = dbDatasource;
    }

    public void switchDBDataSource(DBDatasource dbDatasource) {
        this.dbDatasource = dbDatasource;
    }

    public Boolean send(DBCommand dbCommand) {
        dbCommand.setDataSource(dbDatasource);
        String s = dbCommand.getSql();
        String patternStr = "(\\S+)(.*)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            switch (matcher.group(1).toUpperCase()) {
                case "CREATE":
                case "UPDATE":
                case "DROP":
                case "DELETE":
                case "ALTER":
                case "INSERT":
                case "GRANT": {
                    dbCommand.update();
                    dbCommand.setRowCount(dbDatasource.getRowCount());
                    dbCommand.setColumnCount(dbDatasource.getColumnCount());
                    break;
                }
                case "SELECT":
                    dbCommand.exec();
                    dbCommand.setRowCount(dbDatasource.getRowCount());
                    dbCommand.setColumnCount(dbDatasource.getColumnCount());
            }

            return true;
        } else {
            X.print("SQL Syntax ERROR");
            return false;
        }

    }

    public void print(DBCommand dbCommand) {
        while (!dbCommand.hasSent()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException inE) {
                X.print("Thread ERROR");
            }
        }
        try {
            ResultSet t_rs = dbCommand.getResultSet();
            ResultSetMetaData t_rsmd = t_rs.getMetaData();
            for (int i = 1; i <= t_rsmd.getColumnCount(); i++) {
                X.printConstant(t_rs.getObject(i));
                X.printConstant(" - ");
            }
            while (t_rs.next()) {
                for (int i = 1; i <= t_rsmd.getColumnCount(); i++) {
                    X.printConstant(t_rs.getObject(i));
                    X.printConstant(" - ");
                }
                X.print("");
            }
        } catch (SQLException E) {
            E.printStackTrace();
        }

    }

    public void free() {
        dbDatasource.close();
        dbDatasource = null;
    }
}
