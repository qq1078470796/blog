<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE HTML>
<html>
<head>
    <title>Home</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="" />
    <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
    <link href="css/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
    <link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
    <script src="js/jquery-1.11.1.min.js"></script>
    <link href='http://fonts.googleapis.com/css?family=Oswald:400,300,700' rel='stylesheet' type='text/css'>
</head>

<body>
<!-- banner -->
<div class="banner">
    <div class="banner-info">
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
                        <li class="act"><a href="${ctx}/index_list" class="effect1 active">首页</a></li>
                        <li >
                            <a href="${ctx}/all_list" >全部博文</a>
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
            <!--banner-Slider-->
            <script src="js/responsiveslides.min.js"></script>
            <script>
                // You can also use "$(window).load(function() {"
                $(function () {
                    // Slideshow 4
                    $("#slider3").responsiveSlides({
                        auto: true,
                        pager: true,
                        nav:true,
                        speed: 500,
                        namespace: "callbacks",
                        before: function () {
                            $('.events').append("<li>before event fired.</li>");
                        },
                        after: function () {
                            $('.events').append("<li>after event fired.</li>");
                        }
                    });

                });
            </script>
            <div  id="top" class="callbacks_container">
                <ul class="rslides" id="slider3">
                    <!--
                        作者：offline
                        时间：2019-01-31
                        描述：首页滚动条文章
                    -->

                    <li>
                        <div class="banner-info-slider">
                            <ul>
                                <li><a>${upvote.nickName}</a></li>
                                <li>
                                    <fmt:formatDate value="${upvote.rptTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </li>
                            </ul>
                            <h3>${upvote.title}</h3>
                            <p><span>${upvote.content}</span></p>
                            <div class="more">
                                <a href="watch?cid=${upvote.id}" class="type-1">
                                    <span>阅读全文</span>
                                    <span>阅读全文</span>
                                </a>
                            </div>
                        </div>
                    </li>

                </ul>
            </div>
        </div>
    </div>
</div>
<!--search-->
<form method="post" action="${ctx}/all_list"  id="SearchForm"  role="search" style="width: 25%;float:right;">
    <div class="form-inline">
        <input type="text" id="keyword" name="keyword" value="${keyword}" class="form-control" placeholder="搜索">
        <input type="button" class="btn btn-default" value="搜索一下" onclick="searchForm();">
    </div>
</form>
<!-- banner -->
<!-- banner-bottom -->
<div class="banner-bottom" style="padding:5em 0 0" >
    <div class="container">
        <div class="move-text">
            <div class="breaking_news">
                <h2>站长消息</h2>
            </div>
            <div class="marquee">
                <div class="marquee1"><a class="breaking" href="#">左右滑动可以前进后退页面，善用。
                    站长提示您，请在PC环境下登陆个人主页，否则页面混乱看着闹心别怪我</a></div>
                <div class="marquee2"><a class="breaking" href="#">可以自行注册账号，手机，qq，普通账号注册均可，
                    我会在这里定期发布一些个人动态和推荐音乐，欢迎各位使用</a></div>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
            <script type="text/javascript" src="js/jquery.marquee.js"></script>
            <script>
                $('.marquee').marquee({ pauseOnHover: true });
                //@ sourceURL=pen.js
            </script>
        </div>
        &nbsp;
        <!-- -bottom-grids -->
        <div class="category-bottom-grids">
            <div class="category-bottom-grids1">
                <c:forEach var="blist" items="${blist.result}" varStatus="i">
                <div class="col-md-3 category-bottom-grid">
                    <div class="category-bottom-grid1">
                        <div class="category-bottom-grid1-before before4">
                            <img src="images/${blist.category}.jpg" alt=" " class="img-responsive"
                            />
                            <div class="category-bottom-grid1-pos">
                                <p>${blist.category} </p>
                            </div>
                        </div>
                        <ul class="list2">
                            <li><a href="watch?cid=${blist.id}">${blist.title}</a></li>
                        </ul>
                        <div class="read-more res3">
                            <a href="byCate?category=${blist.category}">查看分类下的所有文章</a>
                        </div>
                    </div>
                    <c:if test="${i.count mod 4==0}">
                        <div class="clearfix"> </div>
                    </c:if>
                </div>
                </c:forEach>
                <div class="clearfix"> </div>
            </div>
        </div>
        <!-- //category-bottom-grids -->
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
            </div>
        </div>
        <!-- //news-and-events -->
    </div>
</div>
<!-- //banner-bottom -->
<!-- for bootstrap working -->
<script src="js/bootstrap.js"></script>
<!-- //for bootstrap working -->
<script>
    //搜索
    function searchForm(){
        var keyword =  $("#keyword").val();
        if(keyword!=null && keyword.trim()!=""){
            $("#SearchForm").submit();
        }
    }

</script>
</body>
</html>