<%--
  Created by IntelliJ IDEA.
  User: zhixiang
  Date: 2017/6/20
  Time: 上午9:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style type="text/css" rel="stylesheet"></style>
    <title>测试</title>
    <link href="https://www.belang.org/release/bella.css" rel="stylesheet">
    <link href="https://www.belang.org/release/default-theme.css" rel="stylesheet">
    <script src="https://www.belang.org/js/jquery.bella.min.js"></script>

</head>
<body>
<div class="raw-w default-subnav">
    <div class="raw-f">
        <h2>名片管理系统</h2>
    </div>
</div>
<div class="raw-w">
    <div class="raw-f">
        <div class="bl-tab-view bl-page col-24-a authBody">
            <div class="bl-view-header">
                <ul>
                    <li>注册</li>
                    <li>登录</li>
                    <li>关于</li>
                </ul>
            </div>

            <div class="bl-view-body">
                <p>##没有账号?</p><br>
                <input type="text" placeholder="Account ID" name="regAccountId"><br>
                <input type="password" placeholder="Passwords" name="regPasswords"><br>
                <input type="password" placeholder="Confirm" name="regPasswordsC"><br>
                <button class="bl-btn" onclick="register()">马上注册</button>
            </div>
            <div class="bl-view-body">
                <p>##登录</p><br>
                <input type="text" placeholder="Account ID" name="accountId"><br>
                <input type="password" placeholder="Passwords" name="passwords"><br>
                <button class="bl-btn" onclick="login()">登录</button>
            </div>
            <div class="bl-view-body">
                <p>##关于</p>
            </div>
        </div>
    </div>
</div>

</body>
<script src="https://www.belang.org/js/bella.js"></script>
<script src="js/index.js"></script>
</html>
