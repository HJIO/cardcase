package io.huaji.cardcase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import io.huaji.sql.*;
import io.huaji.util.X;

public class AccountPlatform extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String requestType = request.getParameter("requestType");
        switch (requestType) {
            case "reg":
                String uid = request.getParameter("regAccountId");
                String pws = request.getParameter("regPws");
                PrintWriter writer = response.getWriter();
                if (register(uid, pws)) {
                    writer.append("success");
                } else {
                    writer.append("error");
                }
                writer.flush();
                writer.close();
                break;

            case "login":
                uid = request.getParameter("accountId");
                pws = request.getParameter("pws");
                writer = response.getWriter();
                if (login(uid, pws)) {
                    writer.append("success");
                    writer.flush();
                    writer.close();
                    request.getRequestDispatcher("main.jsp").forward(request,response);
                } else {
                    writer.append("error");
                    writer.flush();
                    writer.close();
                }


                break;

            default:
                X.print("Request Type Unknow.");
        }
    }

    private static Boolean register(String uid, String pws) {
        DBDatasource dbDatasource = new DBDatasource();
        dbDatasource.quickConnect("cardcase", "cardcase", "Zzxcardcase");
        DBManager dbManager = new DBManager(dbDatasource);
        DBCommand test1 = new DBCommand("select * from user where user_name = ?", uid);
        dbManager.send(test1);
        if (test1.getRowCount() == 0) {
            DBCommand test2 = new DBCommand("insert into user(user_name,user_passwords) values(?,?)", uid, pws);
            dbManager.send(test2);
            dbManager.free();
            return true;
        } else {
            dbManager.free();
            return false;
        }
    }

    private static Boolean login(String uid, String pws) {
        DBDatasource dbDatasource = new DBDatasource();
        dbDatasource.quickConnect("cardcase", "cardcase", "Zzxcardcase");
        DBManager dbManager = new DBManager(dbDatasource);
        DBCommand test1 = new DBCommand("select user_passwords from user where user_name = ?", uid);
        dbManager.send(test1);
        ResultSet rs = test1.getResultSet();
        String pwsC = "";
        try {
            rs.first();
            pwsC = rs.getString(1);
            if (test1.getRowCount() == 0) {
                dbManager.free();
                return false;
            } else if (pwsC.equals(pws)) {
                dbManager.free();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            dbManager.free();
            return false;
        }
    }
}
