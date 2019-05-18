<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>个人空间</title>
    <link href="${ctx}/css/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet"/>

    <link href="${ctx}/css/zui/css/zui.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/zui/css/zui-theme.min.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/css/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/css/zui/js/zui.min.js"></script>
    <script src="${ctx}/css/zui/lib/kindeditor/kindeditor.min.js"></script>
    <style>
        body,html{
            background-color: #EBEBEB;
            padding: 0;
            margin: 0;
            height:100%;
        }
        body {
            font: 14px/1.5 "PingFang SC","Lantinghei SC","Microsoft YaHei","HanHei SC","Helvetica Neue","Open Sans",Arial,"Hiragino Sans GB","微软雅黑",STHeiti,"WenQuanYi Micro Hei",SimSun,sans-serif;
        }
        table,table tr ,table th, table tr td{
            border:2px solid #0b0b0b;
            text-align: center;
        }
    </style>
    <!--[if lt IE 9]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
<body>
<nav class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
        <!-- 导航头部 -->
        <div class="navbar-header">
            <!-- 移动设备上的导航切换按钮 -->
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse-example">
                <span class="sr-only">切换导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <!-- 导航项目 -->
        <div class="collapse navbar-collapse navbar-collapse-example">
            <!-- 一般导航项目 -->
            <ul class="nav navbar-nav">
                <li><a href="${ctx}/index_list">首页</a></li>
            </ul>
            <ul class="nav navbar-nav" style="float: right">
                <li><a href="${ctx}/list?id=${user.id}">${user.nickName}

                </a></li>
            </ul>
            <img src="images/q.png" width="30" style="margin-top: 4px;float: right"/>
        </div>
    </div>
</nav>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-2" >
            <ul class="nav nav-pills nav-stacked">
                <c:if test="${moxing.equals('user')}">
                <li class="active">
                    <a href="${ctx}/admin?type=user" class="active">用户</a>
                </li>
                 <li>
                    <a href="${ctx}/admin?type=cate">分类</a>
                 </li>
                </c:if>
                <c:if test="${moxing.equals('cate')}">
                 <li>
                    <a href="${ctx}/admin?type=user">用户</a>
                 </li>
                <li class="active">
                    <a href="${ctx}/admin?type=cate" class="active">分类</a>
                </li>
                </c:if>
                <li class="disabled">
                        <a href="#">文章</a>
                </li>
            </ul>
        </div>
        <div class="col-md-10 column">

            <c:if test="${moxing.equals('user')}">
            <table class="table">
                <thead>
                <tr>
                    <th>用户昵称</th>
                    <th>密码</th>
                    <th>邮箱</th>
                    <th>手机号</th>
                    <th>权限</th>
                    <th>状态</th>
                    <th>修改</th>
                    <th>删除</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="page" items="${page.result}" varStatus="i">
                    <tr>
                        <form action="${ctx}/updateUserInfo" method="get" id="userForm_${page.id}">
                        <td><input type="text" id="nickName_${page.id}" name="nickName" value="${page.nickName}"
                                   class="input_auto">

                            <input type="text" id="id_${page.id}" name="id" value="${page.id}" hidden="hidden">
                                </td>
                        <td><input type="text" id="password_${page.id}" name="password" value="${page.password}"
                                    class="input_auto">
                                </td>
                        <td><input type="text" id="email_${page.id}" name="email" value="${page.email}"
                                   class="input_auto">
                                </td>
                        <td><input type="text" id="phone_${page.id}" name="phone" value="${page.phone}"
                                    class="input_auto">
                                </td>
                        <td>
                            <select id="state_${page.id}" name="state" class="state" disabled="disabled">
                                <option value ="${page.state}">
                                    <c:if test="${page.state.equals('1')}">
                                    普通用户
                                    </c:if>
                                    <c:if test="${page.state.equals('3')}">
                                        类别管理员
                                    </c:if>
                                    <c:if test="${page.state.equals('5')}">
                                        超级管理员
                                    </c:if>
                                </option>
                            </select>
                                </td>
                        <td>
                            <select id="enable_${page.id}" name="enable" class="enable" disabled="disabled">
                                <option value ="${page.enable}">
                                    <c:if test="${page.enable.equals('0')}">
                                        禁止使用
                                    </c:if>
                                    <c:if test="${page.enable.equals('1')}">
                                        正常
                                    </c:if>
                                </option>
                            </select>
                                </td>
                            <span  class="spanw"></span>
                        <td>
                            <a onclick="showUpdate(${page.id})" class="a1">点击修改</a>
                            <a onclick="goUpdate(${page.id},'user')" style="display: none" class="b1">提交修改</a>
                        </td>
                        <td>
                            <a onclick="showDelete()" class="c1">点击删除</a>
                            <a onclick="goDelete(${page.id},'user') "style="display: none" class="d1">确认删除</a>
                        </td>

                        </form>
                    </tr>

                    </c:forEach>
                </tbody>
            </table>
            </c:if>

            <c:if test="${moxing.equals('cate')}">
                <table class="table" id="oldTable">
                    <thead>
                    <tr>
                        <th><button onclick="openNew()" style="width:100%">新增分类</button></th>

                    </tr>
                    <tr>
                        <th>分类名称</th>
                        <th>分类图片</th>
                        <th>修改</th>
                        <th>删除</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="page" items="${page.result}" varStatus="i">
                        <tr>
                            <form action="${ctx}/updateCateInfo" method="get" id="cateForm_${page.id}">
                                <td><input type="text" id="cName" name="cName" value="${page.name}"
                                           class="input_auto">

                                    <input type="text" id="cid" name="cid" value="${page.id}" hidden="hidden">
                                </td>
                                <td>
                                    <img  src="images/${page.name}.jpg"
                                          width="150" height="100">
                                    </a>

                                </td>
                                <span  class="spanw"></span>
                                <td>
                                    <a onclick="showUpdate()" class="a1">点击修改</a>
                                    <a onclick="goUpdate(${page.id},'cate')" style="display: none" class="b1">提交修改</a>
                                </td>
                                <td>
                                    <a onclick="showDelete()" class="c1">点击删除</a>
                                    <a onclick="goDelete(${page.id},'cate') "style="display: none" class="d1">确认删除</a>
                                </td>

                            </form>
                        </tr>

                    </c:forEach>
                    </tbody>
                </table>

                <table class="table" id="newTable" hidden="hidden">
                    <thead>
                    <tr>
                        <th>分类名称</th>
                        <th>分类图片(点击图片上传)</th>
                        <th>操作栏</th>
                        <th>操作栏</th>
                    </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <form action="${ctx}/insertCateInfo" method="get" id="cateForm">
                                <td><input type="text" id="ncName" name="ncName" placeholder="分类名称">
                                </td>
                                <td>
                                    <img id="img-change"  src="images/10.jpg" onclick="selectImg();" width="150" height="100"></a>
                                </td>
                                <span  class="spanw"></span>
                                <td>
                                    <a onclick="goInsert()" class="a1">点击新增</a>
                                </td>
                                <td>
                                    <a onclick="stopInsert()" class="c1">取消新增</a>
                                </td>

                            </form>

                            <form id="upload-form"   style="width:auto;" >
                                <input type="file"  id="change-img" name="uploadImg" onchange="changeImg();" style="display:none;">
                            </form>

                        </tr>
                    </tbody>
                </table>
            </c:if>

        </div>
    </div>
</div>


</body>
<script type="text/javascript">
    jQuery(document).ready(function($){
        // 第一次加载设置input宽度
        $('.input_auto').each(function(index, el) {
            textWidth($(this))
        });
        // 改变文本内容时重置宽度
        $('.input_auto').bind('input oninput', function() {
            textWidth($(this))
        });

        function textWidth($this){
            // 获取当前input的value值和字体大小
            var inputVal=$this.val();
            var font=$this.css('font-size');
            //获取容器的宽度
            $(".spanw").text(inputVal).css('font-size',font);;
            var width = $(".spanw").width();
            // 清空容器
            $(".spanw").text('');
            // 设置input宽度
            $this.css('width', width+4);
        };
        $("input.input_auto").attr("disabled","disabled");
    });
        function showUpdate(ids) {

            $("#nickName_"+ids).removeAttr("disabled","disabled");
            $("#id_"+ids).removeAttr("disabled","disabled");
            $("#password_"+ids).removeAttr("disabled","disabled");
            $("#email_"+ids).removeAttr("disabled","disabled");
            $("#phone_"+ids).removeAttr("disabled","disabled");
            $("#state_"+ids).removeAttr("disabled","disabled");
            $("#enable_"+ids).removeAttr("disabled","disabled");


            var state=$("#state_"+ids).val();

            if(state==1){
                $("#state_"+ids).append("<option value='1'>普通用户</option>");
                $("#state_"+ids).append("<option value='3'>类别管理员</option>");
                $("#state_"+ids).append("<option value='5'>超级管理员</option>");
            }
            else if(state==3){
                $("#state_"+ids).append("<option value='3'>类别管理员</option>");
                $("#state_"+ids).append("<option value='1'>普通用户</option>");
                $("#state_"+ids).append("<option value='5'>超级管理员</option>");
            }else{
                $("#state_"+ids).append("<option value='5'>超级管理员</option>");
                $("#state_"+ids).append("<option value='3'>类别管理员</option>");
                $("#state_"+ids).append("<option value='1'>普通用户</option>");
            }

            $("#enable_"+ids).append("<option value='1'>正常</option>");
            $("#enable_"+ids).append("<option value='0'>禁止使用</option>");

            $(".a1").hide();
            $(".b1").show();
            $(".picture").attr("onclick","selectImg1()");

        }

        function goUpdate(pageId,type) {
            if(type=="user"){
                $("#userForm_"+pageId).submit();
            }
            else if(type=="cate"){
                $("#cateForm_"+pageId).submit();
            }else{

            }
        }
        function showDelete() {
            $(".c1").hide();
            $(".d1").show();
        }
        function goDelete(pageId,type) {
            if(type=="user"){
                $("input.input_auto").removeAttr("disabled","disabled");
                $("#userForm_"+pageId).attr("action","${ctx}/deleteUserInfo");
                $("#userForm_"+pageId).submit();
            }
            else if(type=="cate"){
                $("#cateForm_"+pageId).attr("action","${ctx}/deleteCateInfo");
                $("#cateForm_"+pageId).submit();
            }
        }
        function goInsert() {
            $("#cateForm").submit();
        }
        function openNew() {
            $("#newTable").show();
            $("#oldTable").hide();
        }
        function stopInsert() {
            $("#newTable").hide();
            $("#oldTable").show();
        }

    //点击图片事件
    function selectImg() {
        var newName=$("#ncName").val();
        if(newName==""){
            alert("请填写分类名称");
        }
        else{
            document.getElementById("change-img").click();
        }

    }

    //图片选择后事件
    function changeImg() {
        var formData = new FormData($( "#upload-form" )[0]);
        var newname=$("#ncName").val();
        formData.append("ncname",newname);
        alert(newname);
        $.ajax({
            url: '/uploadf' ,
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                var msg = data["error"];
                if(msg==0){
                    //上传成功
                    var url = data["url"];
                    document.getElementById("img-change").src = url;
                    alert(url);
                }

            }
        });
    }

</script>
</html>