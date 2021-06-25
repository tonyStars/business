<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WMS后台管理系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="icon" href="${ctx}/./plugin/layuimini/images/favicon.ico">
    <link rel="stylesheet" href="${ctx}/./plugin/layuimini/lib/layui-v2.5.4/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/./plugin/layuimini/css/layuimini.css" media="all">
    <link rel="stylesheet" href="${ctx}/./plugin/layuimini/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style id="layuimini-bg-color"></style>
</head>
<body class="layui-layout-body layuimini-all">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header header">
        <div class="layui-logo"></div>
        <a>
            <div class="layuimini-tool"><i title="展开" class="fa fa-outdent" data-side-fold="1"></i></div>
        </a>
        <ul class="layui-nav layui-layout-left layui-header-menu layui-header-pc-menu mobile layui-hide-xs"></ul>
        <ul class="layui-nav layui-layout-left layui-header-menu mobile layui-hide-sm">
            <li class="layui-nav-item">
                <a style="cursor: pointer"><i class="fa fa-list-ul"></i> 选择模块</a>
                <dl class="layui-nav-child layui-header-mini-menu"></dl>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <%--<li class="layui-nav-item">--%>
            <%--<a style="cursor: pointer" data-refresh="刷新"><i class="fa fa-refresh"></i></a>--%>
            <%--</li>--%>
            <%--<li class="layui-nav-item">--%>
            <%--<a style="cursor: pointer" data-clear="清理" class="layuimini-clear"><i class="fa fa-trash-o"></i></a>--%>
            <%--</li>--%>
            <li class="layui-nav-item layuimini-setting">
                <a style="cursor: pointer">
                    <c:choose>
                        <c:when test="${sessionScope.sessionUser.userCode == 'admin'}">
                            <%--                            <img src="${ctx}/plugin/layuimini/images/doubi.gif" class="layui-nav-img">--%>
                            <i class="fa fa-user"></i>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${sessionScope.sessionUser.sex == '0'}">
                                <i class="fa fa-github"></i>
                            </c:if>
                            <c:if test="${sessionScope.sessionUser.sex == '1'}">
                                <i class="fa fa-github-alt"></i>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                    ${sessionScope.sessionUser.userName}
                </a>
                <dl class="layui-nav-child">
                    <dd>
                        <a style="cursor: pointer" class="user-info" data-title="基本资料" data-icon="fa fa-gears">基本资料</a>
                    </dd>
                    <dd>
                        <a style="cursor: pointer" class="edit-password" data-title="修改密码" data-icon="fa fa-gears">修改密码</a>
                    </dd>
                    <dd>
                        <a style="cursor: pointer" href="${ctx}/exit.do" class="login-out">退出登录</a>
                    </dd>
                </dl>
            </li>
            <li class="layui-nav-item layuimini-select-bgcolor mobile layui-hide-xs">
                <a style="cursor: pointer" data-bgcolor="配色方案"><i class="fa fa-ellipsis-v" ></i></a>
            </li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll layui-left-menu"></div>
    </div>

    <div class="layui-body">
        <div class="layui-tab" lay-filter="layuiminiTab" id="top_tabs_box">
            <ul class="layui-tab-title" id="top_tabs">
                <li class="layui-this" id="layuiminiHomeTabId" lay-id=""></li>
            </ul>
            <ul class="layui-nav closeBox">
                <li class="layui-nav-item">
                    <a style="cursor: pointer"> <i class="fa fa-dot-circle-o"></i> 页面操作</a>
                    <dl class="layui-nav-child">
                        <dd><a style="cursor: pointer" data-page-close="other"><i class="fa fa-window-close"></i> 关闭其他</a></dd>
                        <dd><a style="cursor: pointer" data-page-close="all"><i class="fa fa-window-close-o"></i> 关闭全部</a></dd>
                    </dl>
                </li>
            </ul>
            <div class="layui-tab-content clildFrame">
                <div id="layuiminiHomeTabIframe" class="layui-tab-item layui-show">
                </div>
            </div>
        </div>
    </div>

</div>
<script src="${ctx}/./plugin/layuimini/lib/layui-v2.5.4/layui.js?v=1.0.4" charset="UTF-8"></script>
<script src="${ctx}/./plugin/layuimini/js/lay-config.js?v=1.0.4" charset="UTF-8"></script>
<script type="text/javascript">
    var path = '${ctx}';
    layui.use(['element','layer', 'layuimini'], function () {
        /**layer和element在init中使用*/
        var $ = layui.jquery,layer = layui.layer,element = layui.element;
        /**主界面请求加载URL*/
        layuimini.init();
        /**退出登陆提示信息*/
        $('.login-out').on("click", function () {
            /**将长连接中的userId清空*/
            userId = "";
            layuimini.msg_success("退出登录成功!");
        });
        $('.edit-password').on("click", function () {
            layer.open({
                title: "修改密码",
                type: 2,
                area: ["45%", "50%"],
                fixed: false, // 鼠标滚动时，层是否固定在可视区域
                maxmin: false, // 放大缩小按钮
                content: path + "/toPwdEdit.do",
                success: function(layero, index){ // 层弹出成功后回调参数：当前层DOM&当前层索引
                    //键盘按键监听
                    $(document).on("keydown", function(e){
                        if (e.keyCode == 13){ // 禁用回车事件 以免层重复弹出
                            return false;
                        }
                        if (e.keyCode == 27){ //ESC退出
                            layer.close(index);
                        }
                    })
                }
            });
        });
        $('.user-info').on("click", function () {
            layer.open({
                title: "基本资料",
                type: 2,
                area: ["45%", "50%"],
                fixed: false, // 鼠标滚动时，层是否固定在可视区域
                maxmin: false, // 放大缩小按钮
                content: path + "/userView.do",
                success: function(layero, index){ // 层弹出成功后回调参数：当前层DOM&当前层索引
                    //键盘按键监听
                    $(document).on("keydown", function(e){
                        if (e.keyCode == 13){ // 禁用回车事件 以免层重复弹出
                            return false;
                        }
                        if (e.keyCode == 27){ //ESC退出
                            layer.close(index);
                        }
                    })
                }
            });
        });
    });
</script>
</body>
</html>