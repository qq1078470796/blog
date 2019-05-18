<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE HTML>
<html>
<head>
    <title>all</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="" />
    <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
    <!-- Custom Theme files -->
    <link href="css/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
    <link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
    <!-- js -->
    <script src="js/jquery-1.11.1.min.js"></script>
    <!-- //js -->
    <link href='http://fonts.googleapis.com/css?family=Oswald:400,300,700' rel='stylesheet' type='text/css'>
</head>

<body>
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
                        <li><a href="${ctx}/index_list" >首页</a></li>
                        <li class="act">
                            <a href="${ctx}/all_list" class="effect1 active">全部博文</a>
                        </li>
                        <li><a href="${ctx}/list?id=${user.id}">个人空间</a></li>
                        <li role="presentation" class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
                                分类 <span class="caret"></span>
                            </a>

                            <ul class="dropdown-menu">
                                <c:forEach var="cate" items="${cpage.result}" varStatus="i">
                                    <li><a href="byCate?category=${cate.name}">${cate.name}</a></li>
                                </c:forEach>

                            </ul>

                        </li>
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
<!-- banner -->
<!-- banner-bottom -->
<div class="banner-bottom">
        <!-- news-and-events -->
        <div class="news">
            <div class="news-grids">
                <div class="col-md-8 news-grid-left">
                    <h3>最新博文</h3>
                    <ul>
                        <c:forEach var="cont" items="${page.result}" varStatus="i">
                            <li>
                                <div class="news-grid-left1">
                                    <img src="${cont.imgUrl}" alt=" " class="img-responsive" />
                                </div>
                                <div class="news-grid-right1">
                                    <h4><a href="watch?cid=${cont.id}">${cont.title}</a></h4>
                                    <h5>By <a href="#">${cont.nickName}</a> <label>|</label> <i><fmt:formatDate value="${cont.rptTime}" pattern="yyyy-MM-dd HH:mm:ss"/></i></h5>
                                    <p>${cont.content}</p>
                                </div>
                                <div class="news-grid-right1">
                                    <a href="watch?cid=${cont.id}">文章详情</a>
                                </div>
                                <div class="clearfix"> </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <div class="col-md-4 news-grid-right">
                    <div class="news-grid-rght1">
                        <!-- Nav tabs -->
                        <ul class="nav nav-tabs" role="tablist">
                            <li role="presentation" class="active"><a class="high" href="#home" aria-controls="home" role="tab" data-toggle="tab">查看本地天气</a></li>
                            <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">更换城市</a></li>
                        </ul>

                        <!-- Tab panes -->
                        <div class="tab-content">
                            <iframe name="weather_inc" src="http://i.tianqi.com/index.php?c=code&id=82" width="300" height="370" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
                        </div>
                    </div>
                </div>
            </div>
            <div class="clearfix"> </div>
            <!-- //news-and-events -->
            <div id="page-info" style="left: 0px;">
                <ul class="pager pager-loose">
                    <c:if test="${page.pageNum <= 1}">
                        <li><a href="javascript:void(0);">« 上一页</a></li>
                    </c:if>
                    <c:if test="${page.pageNum > 1}">
                        <li class="previous"><a href="${ctx}/${type}pageNum=${page.pageNum-1}&&id=${user.id}">« 上一页</a></li>
                    </c:if>
                    <c:forEach begin="1" end="${page.pages}" var="pn">
                        <c:if test="${page.pageNum==pn}">
                            <li class="active" style="background: #990033"><a href="javascript:void(0);">${pn}</a></li>
                        </c:if>
                        <c:if test="${page.pageNum!=pn}">
                            <li ><a href="${ctx}/${type}pageNum=${pn}&&id=${user.id}">${pn}</a></li>
                        </c:if>
                    </c:forEach>

                    <c:if test="${page.pageNum>=page.pages}">
                        <li><a href="javascript:void(0);">下一页 »</a></li>
                    </c:if>
                    <c:if test="${page.pageNum<page.pages}">
                        <li><a href="${ctx}/${type}pageNum=${page.pageNum+1}&&id=${user.id}">下一页 »</a></li>
                    </c:if>

                </ul>
            </div>
        </div>
    </div>

</div>
</div>
<!-- //banner-bottom -->
<!-- for bootstrap working -->
<script src="js/bootstrap.js"></script>
<!-- //for bootstrap working -->
</body>
</html>