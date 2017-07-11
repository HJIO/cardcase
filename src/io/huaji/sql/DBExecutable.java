package io.huaji.sql;

import java.sql.ResultSet;

public interface DBExecutable {

    public Boolean update();

    public ResultSet exec();

    public int getRowCount();
    public int getColumnCount();

    public ResultSet getResultSet();

}
