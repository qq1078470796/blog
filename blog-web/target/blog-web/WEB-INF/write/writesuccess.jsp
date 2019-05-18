<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>写帖子</title>
    <link href="${ctx}/css/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet"/>

    <link href="${ctx}/css/zui/css/zui.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/zui/css/zui-theme.min.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/css/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/css/zui/js/zui.min.js"></script>
    <style>
        .content {
            position:absolute;
            top:50%;
            left:50%;
            margin:-150px 0 0 -300px;
            width:600px;
            height:300px;

        }
    </style>
</head>
<body>
<div class="content" style="text-align: center">
   <i class="icon-1x icon-check-circle-o" style="color: green"></i> <font size="4">帖子发布成功</font>
    <div style="margin-left: 60px">
        <button class="btn btn-block " id="new-blog" type="button" style="width: 140px;text-align: center;float: left;margin-top: 40px;background-color: #affbb2">写新贴</button>
        <button class="btn btn-block " id="manage-blog" type="button" style="width: 140px;text-align: center;margin-left: 10px;float: left;margin-top: 40px;background-color: #affbb2">管理帖子</button>
        <button class="btn btn-block " id="watch-blog" type="button" style="width: 140px;text-align: center;margin-left: 10px;float: left;margin-top: 40px;background-color: #affbb2">查看帖子</button>
    </div>
</div>
</body>
<script>
    //写新梦
    $("#new-blog").click(function () {
        location.href ="${ctx}/writePage"
    });

    //管理帖子
    $("#manage-blog").click(function () {
        location.href ="${ctx}/list?manage=manage"
    });

    //查看帖子
    $("#watch-blog").click(function () {
        location.href ="${ctx}/watch?cid=${content.id}"
    });
</script>
</html>