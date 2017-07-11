<%--
  Created by IntelliJ IDEA.
  User: zhixiang
  Date: 2017/7/8
  Time: 下午8:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="io.huaji.sql.*" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="io.huaji.cardcase.CardCaseDao" %>
<%@ page import="java.util.List" %>
<%@ page import="io.huaji.cardcase.CardBean" %>
<%@ page import="io.huaji.util.X" %>

<html>
<head>
    <style type="text/css" rel="stylesheet"></style>
    <title>名片管理系统</title>
    <link href="https://www.belang.org/release/bella.css" rel="stylesheet">
    <link href="https://www.belang.org/release/default-theme.css" rel="stylesheet">
    <script src="https://www.belang.org/js/jquery.bella.min.js"></script>
</head>
<body style="padding-bottom: 30px">
<div class="raw-w default-subnav attach-delegate">
    <div class="raw-f">
        <h2 class="col-13-a">管理和查看您的名片</h2>
    </div>
</div>
<div class="raw-w">
    <div class="raw-f">
        <div class="col-6-10 bl-view">
            <div class="bl-view-header">功能区</div>
            <div class="bl-view-body">
                <ul class="bl-sidebar">
                    <a onclick="sel1()">欢迎</a>
                    <a onclick="sel2()">查看与编辑</a>
                    <a onclick="sel3()">新增名片</a>
                </ul>
            </div>
        </div>
        <div class="col-17-10 bl-view bl-page">
            <div class="bl-view-header">说明</div>
            <div class="bl-view-body head-view" id="welcome-view">
                <p>##欢迎，请使用左侧功能区</p>
                <p></p>
                <p>1.使用*新增名片*来创建新的名片</p>
                <p>2.在*名片视图*可修改或删除名片</p>
            </div>
            <div class="bl-view-body head-view" id="modify-view" style="display: none">
                <p>##如何编辑名片？</p>
                <p></p>
                <p>1.在名片上使用*编辑*选项卡</p>
                <p>2.编辑信息后使用*提交*，然后等待页面刷新</p>
            </div>
            <div class="bl-view-body head-view" id="new-view" style="display: none">
                <p>##新增名片</p>
                <input type="text" placeholder="姓名" name="cardName">
                <input type="text" placeholder="联系方式" name="cardTel"><br>
                <input type="text" placeholder="性别" name="cardSex">
                <input type="text" placeholder="职务" name="cardPos"><br>
                <input type="text" placeholder="公司" name="cardCorp"><br>
                <button onclick="update(this)" class="bl-btn">提交修改</button>
            </div>
        </div>
    </div>
</div>
<div class="raw-w">
    <div class="raw-f">
        <%
            CardCaseDao cardCaseDao = new CardCaseDao();
            List<CardBean> list = cardCaseDao.getCardBeanList();
            for (CardBean card : list) {
        %>
        <div class="bl-tab-view bl-page col-11-a authBody">
            <div class="bl-view-header">
                <ul>
                    <li>查看</li>
                    <li>编辑</li>
                    <li>删除</li>
                </ul>
            </div>
            <div class="bl-view-body" style="text-align: center">
                <p>##<%=card.getCardName()%>
                </p>
                <p>_<%=card.getCardPosition()%>_</p>
                <br>
                <div class="card_sex">
                    <%=card.getCardSex().equals("1") ? "男" : "女"%>
                </div>
                <br>
                <p style="font-style: italic">###<%=card.getCardTel()%>
                </p><br>
                <p>###<%=card.getCardCorp()%>
                </p>
                <br>
            </div>
            <div class="bl-view-body">
                <p>##修改</p><br>
                <input type="text" placeholder="姓名" name="cardName" value="<%=card.getCardName()%>">
                <input type="text" placeholder="联系方式" name="cardTel" value="<%=card.getCardTel()%>"><br>
                <input type="text" placeholder="性别" name="cardSex"
                       value="<%=card.getCardSex().equals("1")?"男":"女"%>">
                <input type="text" placeholder="职务" name="cardPos" value="<%=card.getCardPosition()%>"><br>
                <input type="text" placeholder="公司" name="cardCorp" value="<%=card.getCardCorp()%>"><br>
                <button onclick="update(this)" class="bl-btn">提交修改</button>
            </div>
            <div class="bl-view-body" style="text-align: center">
                <p>##注意</p>
                <p>您确定要删除这张名片吗</p><br>
                <button onclick="del(this)" class="bl-btn bl-attr-alert">确认删除</button>
            </div>
            </div>
        <%
            }
        %>
    </div>
</div>
<div class="raw-w">
    <div class="raw-f">
        <div class="col-24-a bl-view bl-page">
            <div class="bl-view-header">关于</div>
            <div class="bl-view-body">
                <p>##关于</p><br>
                <p>####1.我用了前端框架吗？</p>
                <p>是的，但这个框架我写的，该项目为*bella*，项目源代码托管在Github（详见：说明文档.pdf）</p>
                <br>
                <p>####2.后台对于JDBC的封装</p>
                <p>Servlet与Jsp逻辑封装在Dao层与JavaBean，数据库连接逻辑在io.huaji.sql包中，已被封装</p>
                <br>
                <p>####3.如果您有兴趣</p>
                <p>请移步bella的官方网站与项目主页（详见：说明文档.pdf），感谢老师</p>
            </div>
        </div>
    </div>
</div>
</body>
<style>
    .card_sex {
        margin: auto;
        width: 50px;
        height: 50px;
        display: block;
        line-height: 50px;
        border: 1px solid #ddd;
        border-radius: 50%;
    }

    .head-view {
        background-color: transparent;
    }
    input{
        width: 150px!important;

    }
</style>
<script src="https://www.belang.org/js/bella.js"></script>
<script src="js/index.js"></script>
</html>
