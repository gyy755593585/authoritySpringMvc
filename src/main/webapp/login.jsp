<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
 <script type="text/javascript" src="${path}/static/js/login.js" charset="utf-8"></script>
</head>
<body>
<div class="top_div"></div>
<div style="background: rgb(255, 255, 255); margin: -100px auto auto; border: 1px solid rgb(231, 231, 231);border-image:none;width:400px;text-align: center;">
    <form method="post" id="loginform">
        <div style="width: 165px; height: 96px; position: absolute;">
            <div class="tou"></div>
            <div class="initial_left_hand" id="left_hand"></div>
            <div class="initial_right_hand" id="right_hand"></div>
        </div>
        <P style="padding: 30px 0px 10px; position: relative;">
            <span class="u_logo"></span>
            <input class="ipt easyui-validatebox"  data-options="prompt:'请输入您的用户名.',required:true,validType:'length[3,10]'"type="text" name="userName" placeholder="请输入登录名"/>
        </P>
        <P style="position: relative;">
            <span class="p_logo"></span>
            <input class="ipt easyui-validatebox" id="password" data-options="prompt:'请输入一个有效的密码.',required:true,validType:'password'" type="password" name="password" placeholder="请输入密码"/>
        </P>
       <%--  <P style="padding: 10px 0px 10px; position: relative;">
            <input class=s"captcha" type="text" name="captcha" placeholder="请输入验证码"/>
            <img id="captcha" alt="验证码" src="${path }/captcha.jpg" data-src="${path }/captcha.jpg?t=" style="vertical-align:middle;border-radius:4px;width:94.5px;height:35px;cursor:pointer;">
        </P> --%>
        <P style="position: relative;text-align: left;">
            <!--
            <input class="rememberMe" type="checkbox" name="rememberMe" value="1" checked style="vertical-align:middle;margin-left:40px;height:20px;"/> 记住密码
              -->
        </P>
        <div style="height: 50px; line-height: 50px; margin-top: 10px;border-top-color: rgb(231, 231, 231); border-top-width: 1px; border-top-style: solid;">
            <P style="margin: 0px 35px 20px 45px;">
                <span style="float: left;">
                </span>
                <span style="float: right;">
                    <a style="background: rgb(0, 142, 173); padding: 7px 10px; border-radius: 4px; border: 1px solid rgb(26, 117, 152); border-image: none; color: rgb(255, 255, 255); font-weight: bold;" href="javascript:;" onclick="submitForm()">登录</a>
                </span>
            </P>
        </div>
    </form>
</div>
</body>
</html>