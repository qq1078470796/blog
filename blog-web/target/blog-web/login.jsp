<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="" />
    <title>登录页面</title>
    <link href="${ctx}/css/zui/css/zui.min.css" rel="stylesheet">
    <link href="${ctx}/css/zui/lib/uploader/zui.uploader.min.css" rel="stylesheet">
    <link href="css/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
    <link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
    <link href='http://fonts.googleapis.com/css?family=Oswald:400,300,700' rel='stylesheet' type='text/css'>
    <script src="js/jquery-1.11.1.min.js"></script>
    <script src="${ctx}/js/jquery.min.js"></script>
    <script src="${ctx}/css/zui/js/zui.min.js"></script>
    <!-- 默认滑动到屏幕最上方 -->
    <script type="application/x-javascript">
        addEventListener("load", function() {
            setTimeout(hideURLbar, 0);
        }, false);

        function hideURLbar() {
            window.scrollTo(0, 1);
        }
    </script>
</head>

<body>
<div class="content">
    <!-- banner -->
    <div class="banner1">
        <div class="banner-info1">
            <div class="container">
                <nav class="navbar navbar-default">
                    <!-- Brand and toggle get grouped for better mobile display -->
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <div class="logo">
                            <a class="navbar-brand" href="${ctx}/index_list">
                                <span>HAO</span>My Blog</a>
                        </div>
                    </div>
                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse nav-wil" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav cl-effect-18" id="cl-effect-18">
                            <li><a href="${ctx}/index_list">首页</a></li>
                            <li><a href="${ctx}/all_list">全部博文</a></li>
                            <li role="presentation" class="dropdown">
                                <c:if test="${empty user}">
                                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
                                        登录 <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a name="tj_login" class="lb" href="login?error=login" style="color: black">[请登录]</a></li>
                                        <li><a name="tj_login" class="lb" href="register" style="color: black">[注册]</a></li>
                                    </ul>
                                </c:if>

                                <c:if test="${not empty user}">
                                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
                                        <font color="#b22222 ">${user.nickName}，欢迎你！</font>
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a name="tj_loginp" href="javascript:void(0);"   class="lb" onclick="personal('${user.id}');" style="color: black"><font color="#9370db">${user.nickName}的个人空间</font></a></li>
                                        <li><a name="tj_login" class="lb" href="${ctx}/loginout" style="color: black">[退出]</a></li>

                                    </ul>
                                </c:if>

                            </li>
                        </ul>
                    </div><!-- /.navbar-collapse -->
                </nav>
            </div>
        </div>
    </div>
    <!-- //banner -->
    <!-- contact -->
        <div class="container">
            <div class="upcoming-events-grids">
                <div class="col-md-12">
                    <div class="gallery">
                        <div class="col-md-12 contact-grid">
                            <!--标签页，两种登录方式-->
                            <ul class="nav nav-secondary nav-justified">
                                <li id="a_login" class="active">
                                    <a data-toggle="tab" href="#account-login">账号登录</a>
                                </li>
                                <li id="p_login">
                                    <a data-toggle="tab" href="#phone-login">手机快捷登录</a>
                                </li>
                            </ul>
                            <!-- 标签页内容，两种表单 -->
                            <div class="tab-content">
                                <!--普通登录-->
                                <div class="tab-pane fade in active" id="account-login">
                                    <form id="normal_form" name="form" role="form" class="login-form" action="${ctx}/doLogin" method="post">
                                        <div class="form-group">
                                            <label for="username" class="sr-only">用户名</label>
                                            <input type="text" id="username" name="username" onblur="checkUserName();" value="${email}" class="form-control" placeholder="用户名">
                                        </div>
                                        <div class="form-group">
                                            <label for="password" class="sr-only">密码</label>
                                            <input type="password" id="password" name="password" onblur="checkPassword();" value="${password}" class="form-control" placeholder="密码">
                                        </div>
                                        <div class="form-group">
                                            <label for="code" class="sr-only">验证码</label>
                                            <input type="text" id="code"  name="code" class="form-control" placeholder="验证码" onblur="checkCode()" >
                                        </div>

                                        <div>
                                            <img id="captchaImg" style="CURSOR: pointer" onclick="changeCaptcha()"
                                                 title="看不清楚?请点击刷新验证码!" align='absmiddle' src="${ctx}/captchaServlet"
                                                 height="18" width="55">
                                            <a href="javascript:;"
                                               onClick="changeCaptcha()" style="color: #666;">看不清楚</a> <span id="code_span" style="color: red"></span>
                                        </div>
                                        <div class="form-group">
                                            <!-- 多选框 -->
                                            <div class="checkbox">
                                                <label>
                                                    <br/>

                                                    <c:if test="${error eq 'fail'}">
                                                        <span style="color: red" id="back_data">用户名或者密码错误</span>
                                                    </c:if>
                                                    <c:if test="${error eq 'active'}">
                                                        <span style="color: red" id="back_active">您的账号未激活，请先激活！</span>
                                                    </c:if>
                                                    <c:if test="${error eq 'notAdmin'}">
                                                        <span style="color: red" id="back_active">您的账号不是管理员，无法登陆管理页面</span>
                                                    </c:if>
                                                    <c:if test="${error eq 'phone_fail'}">
                                                        <span style="color: red" id="back_phone">手机验证码错误或者已失效</span>
                                                    </c:if>
                                                    <span style="color: green" id="normal_span">${success}</span>
                                                </label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <button type="button" id="btn" class="btn btn-primary btn-block" onclick="normal_login();">登录</button>
                                            <div>
                                                <div style="float: left;"><p>使用其它账号登录&nbsp;&nbsp;&nbsp;</p></div>
                                                <div style="margin-left: 20px"><a href="to_login"><img src="/images/Connect_logo_1.png"></a></div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <!--手机登录-->
                                <div class="tab-pane fade" id="phone-login">
                                    <form role="form" class="login-form form-horizontal" id="phone_form" action="${ctx}/doLogin" method="post">
                                        <div class="form-group">
                                            <label for="username" class="sr-only">用户名</label>
                                            <div class="col-xs-12">
                                                <input type="text" id="phone" name="telephone" class="form-control" placeholder="手机号" onblur="checkPhone();">
                                                <input type="hidden" id="tab" name="tab"  value="pho-login">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="password" class="sr-only">密码</label>
                                            <div class="col-xs-6">
                                                <input type="text" id="verifyCode" name="phone_code" class="form-control" placeholder="验证码" onclick="checkPhoneCode();">
                                            </div>
                                            <div class="col-xs-6">
                                                <button class="btn btn-primary btn-block" id="go">获取验证码</button>
                                            </div>
                                        </div>
                                        <span id="phone_span"></span>
                                        <div class="form-group">
                                            <div class="col-xs-12">
                                                <button type="button" id="phone_btn" class="btn btn-primary btn-block">登录</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="clearfix"> </div>
                </div>

            </div>
        </div>
    </div>
    <div class="clearfix"> </div>
<!-- //contact -->
<script>
    //校验用户名
    function checkUserName() {
        $("#back_data").text("");
        $("#back_active").text("");
        $("#back_phone").text("");
        var username = $("#username").val();
        username = username.replace(/^\s+|\s+$/g,"");
        if(username == ""){
            $("#normal_span").text("请输入用户名或密码！").css("color","red");
            return false;
        }else{
            $("#normal_span").text("").css("color","green");
            return true;
        }
    }
    //校验密码
    function checkPassword() {
        $("#back_data").text("");
        $("#back_active").text("");
        $("#back_phone").text("");
        var password = $("#password").val();
        password = password.replace(/^\s+|\s+$/g, "");
        if (password == "") {
            $("#normal_span").text("请输入密码！").css("color", "red");
            return false;
        }
        if (password.length < 6) {
            $("#normal_span").text("密码长度少于6位，请重新输入！").css("color", "red");
            return false;
        }

        $("#normal_span").text("").css("color", "green");
        return true;
    }
    //更换验证码
    function changeCaptcha() {
        $("#captchaImg").attr('src', '${ctx}/captchaServlet?t=' + (new Date().getTime()));
    }

    //验证码校验
    var flag_c = false;
    function checkCode() {
        $("#back_data").text("");
        $("#back_active").text("");
        $("#back_phone").text("");
        var code = $("#code").val();
        code = code.replace(/^\s+|\s+$/g,"");
        if(code == ""){
            $("#code_span").text("请输入验证码！").css("color","red");
            flag_c = false;
        }else{
            $.ajax({
                type: 'post',
                url: '/checkCode',
                data: {"code": code},
                dataType: 'json',
                success: function (data) {
                    var val = data['message'];
                    if (val == "success") {
                        $("#code_span").text("√").css("color","green");
                        $("#reg_span").text("");
                        flag_c = true;
                    }else {
                        $("#code_span").text("验证码错误！").css("color","red");
                        flag_c = false;
                    }
                }
            });

        }
        return flag_c;
    }

    //密码框回车事件
    $("#password").bind('keypress',function(event){

        if(event.keyCode == 13)

        {
            normal_login();
        }
    });

    //验证码框回车事件
    $("#code").bind('keypress',function(event){


        if(event.keyCode == 13)

        {
            normal_login();
        }

    });
    function normal_login() {
        if(checkUserName() && checkPassword() && checkCode()) {
            $("#normal_form").submit();
        }
    }
    //校验手机号
    var flag2 = false
    function checkPhone(){
        var phone = $("#phone").val();
        phone = phone.replace(/^\s+|\s+$/g,"");
        if(!(/^1[3|4|5|8|7][0-9]\d{8}$/.test(phone))){
            $("#phone_span").text("手机号码非法，请重新输入！").css("color","red");
            flag2 = false;
        }else{
            $.ajax({
                type:'post',
                url:'/checkPhone',
                data: {"phone":phone},
                dataType:'json',
                success:function(data){
                    var val = data['message'];
                    if(val=="success"){
                        //未注册
                        $("#phone_span").text("该手机号还未注册！");
                        flag2 =  false;
                    }else{
                        //注册
                        $("#phone_span").text("");

                        flag2 =  true;
                    }
                }
            });
        }

        return flag2;
    }

    function countDown(s){
        if(s <= 0){
            $("#go").text("重新获取");
            $("#go").removeAttr("disabled");
            return;
        }
        /* $("#go").val(s + "秒后重新获取");*/
        $("#go").text(s + "秒后重新获取");
        setTimeout("countDown("+(s-1)+")",1000);
    }

    //获取验证码
    $(function () {
        var go = document.getElementById('go');

        go.onclick = function (ev){

            if(!flag2){
                $("#phone_span").text("手机号码非法或者未注册！").css("color","red");
            }else {

                //  发送短信给用户手机..
                // 1 发送一个HTTP请求，通知服务器 发送短信给目标用户
                var telephone =$("input[name='telephone']").val();// 用户输入的手机号
                // 用户输入手机号校验通过
                $("#go").attr("disabled", "disabled");
                countDown(60);
                $.ajax({
                    method: 'POST',
                    url: '${ctx}/sendSms',// 发送验证码给ActiveQM, 同时保存验证码到redis数据库
                    data : {
                        telephone : telephone
                    },
                    success:function(data) {
                        var tt = data["msg"];
                        if(tt){
                            alert("发送短信成功!");

                        }else{
                            alert("发送短信出错，请联系管理员");
                        }
                    }
                });
            }
            var oEvent = ev || event;
            //js阻止链接默认行为，没有停止冒泡
            oEvent.preventDefault();
            return false;

        }
    });

    var p_flag = false;
    //手机验证码检查
    function checkPhoneCode(){
        var reg = /^\d{6}\b/;
        var code = $("#verifyCode").val();
        if(reg.test(code)){
            p_flag =  true;
        }else {
            p_flag =  false;
        }

        return p_flag;
    }
    //登录
    $("#phone_btn").click(function () {

        if(checkPhone()&& checkPhoneCode()){
            // 校验用户名和密码
            $("#phone_span").text("").css("color","red");
            $("#phone_form").submit();
        }else {
            alert("请输入手机号和6位验证码!");
        }

    });

</script>
</body>

</html>