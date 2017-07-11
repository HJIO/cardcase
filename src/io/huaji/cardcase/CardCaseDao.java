package io.huaji.cardcase;

import io.huaji.sql.DBCommand;
import io.huaji.sql.DBDatasource;
import io.huaji.sql.DBManager;
import io.huaji.util.X;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhixiang on 2017/7/10.
 */
public class CardCaseDao extends HttpServlet {
    private DBManager dbManager;
    private DBCommand test1;
    private ResultSet rs;
    private List<CardBean> list;

    public CardCaseDao() {
        DBDatasource dbDatasource = new DBDatasource();
        dbDatasource.quickConnect("cardcase", "cardcase", "Zzxcardcase");
        dbManager = new DBManager(dbDatasource);
        test1 = new DBCommand("select * from card order by card_id desc");
        dbManager.send(test1);
        rs = test1.getResultSet();
    }

    public List<CardBean> getCardBeanList() {
        list = new ArrayList<CardBean>();
        try {
            rs.first();
            String cardTel = rs.getString(2);
            String cardName = rs.getString(3);
            String cardSex = rs.getString(4);
            String cardPosition = rs.getString(5);
            String cardCorp = rs.getString(6);
            CardBean cardBean = new CardBean(cardTel, cardName, cardSex, cardPosition, cardCorp);
            list.add(cardBean);
            while (rs.next()) {
                cardTel = rs.getString(2);
                cardName = rs.getString(3);
                cardSex = rs.getString(4);
                cardPosition = rs.getString(5);
                cardCorp = rs.getString(6);
                cardBean = new CardBean(cardTel, cardName, cardSex, cardPosition, cardCorp);
                list.add(cardBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void Update(CardBean cardBean) {
        String cardTel = cardBean.getCardTel();
        String cardName = cardBean.getCardName();
        String cardSex = cardBean.getCardSex();
        String cardPosition = cardBean.getCardPosition();
        String cardCorp = cardBean.getCardCorp();
        X.print(cardPosition);
        DBCommand cmd1 = new DBCommand("select * from card where card_name = ?", cardName);
        dbManager.send(cmd1);
        if (cmd1.getRowCount() == 0) {
            DBCommand cmd2 = new DBCommand("insert into card(card_tel, card_name, card_sex, card_position, card_corp) values(?,?,?,?,?)", cardTel, cardName, cardSex, cardPosition, cardCorp);
            dbManager.send(cmd2);
        } else {
            DBCommand cmd2 = new DBCommand("Update card set card_tel =?,card_sex=?,card_position=?,card_corp=? where card_name =?", cardTel, cardSex, cardPosition, cardCorp, cardName);
            dbManager.send(cmd2);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String type = request.getParameter("requestType");
        switch (type){
            case "update":
                String cardTel = request.getParameter("cardTel");
                String cardName = request.getParameter("cardName");
                String cardSex = request.getParameter("cardSex");
                String cardPosition = request.getParameter("cardPos");
                String cardCorp = request.getParameter("cardCorp");
                CardBean cardBean = new CardBean(cardTel,cardName,cardSex,cardPosition,cardCorp);
                Update(cardBean);
                break;
            case "del":
                del(request.getParameter("cardName"));

        }

    }
    void del(String cardName){
        DBCommand cmd = new DBCommand("delete from card where card_name = ?",cardName);
        dbManager.send(cmd);
    }
}
