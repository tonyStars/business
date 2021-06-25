<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WMS后台管理系统</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${ctx}/./plugin/layuimini/lib/layui-v2.5.4/css/layui.css" media="all">
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        html, body {
            width: 100%;
            height: 100%;
            overflow: hidden
        }

        body {
            background: #009688;
        }

        body:after {
            content: '';
            background-repeat: no-repeat;
            background-size: cover;
            -webkit-filter: blur(3px);
            -moz-filter: blur(3px);
            -o-filter: blur(3px);
            -ms-filter: blur(3px);
            filter: blur(3px);
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            z-index: -1;
        }

        .layui-container {
            width: 100%;
            height: 100%;
            overflow: hidden
        }

        .admin-login-background {
            width: 360px;
            height: 300px;
            position: absolute;
            left: 50%;
            top: 40%;
            margin-left: -180px;
            margin-top: -100px;
        }

        .logo-title {
            text-align: center;
            letter-spacing: 2px;
            padding: 14px 0;
        }

        .logo-title h1 {
            color: #009688;
            font-size: 25px;
            font-weight: bold;
        }

        .login-form {
            background-color: #fff;
            border: 1px solid #fff;
            border-radius: 3px;
            padding: 14px 20px;
            box-shadow: 0 0 8px #eeeeee;
        }

        .login-form .layui-form-item {
            position: relative;
        }

        .login-form .layui-form-item label {
            position: absolute;
            left: 1px;
            top: 1px;
            width: 38px;
            line-height: 36px;
            text-align: center;
            color: #d2d2d2;
        }

        .login-form .layui-form-item input {
            padding-left: 36px;
            height: 40px;
        }

        .captcha-img img {
            height: 34px;
            border: 1px solid #e6e6e6;
            height: 36px;
            width: 100%;
        }

        .layui-btn-fluid {
            width: 100%;
        }
    </style>
</head>
<body>
<div class="layui-container">
    <div class="admin-login-background">
        <div class="layui-form login-form">
            <form class="layui-form" id="customForm" onsubmit="return false" autocomplete="off">
                <div class="layui-form-item logo-title">
                    <h1>财务管理系统</h1>
                </div>
                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-username"></label>
                    <input class="layui-input" type="text" name="account" id="account" lay-verify="required|account" placeholder="用户名" autocomplete="off">
                </div>
                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-password"></label>
                    <input class="layui-input" type="password" name="password" id="password" lay-verify="required|password" placeholder="密码" autocomplete="off">
                </div>
                <div class="layui-form-item">
                    <button class="layui-btn layui-btn-fluid" lay-submit="" lay-filter="login">登 入</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="${ctx}/./plugin/layuimini/lib/jquery-3.4.1/jquery-3.4.1.min.js" charset="UTF-8"></script>
<script src="${ctx}/./plugin/layuimini/lib/layui-v2.5.4/layui.js" charset="UTF-8"></script>
<script src="${ctx}/./plugin/layuimini/lib/jq-module/jquery.particleground.min.js" charset="UTF-8"></script>
<script src="${ctx}/./js/md5.js" charset="UTF-8"></script>
<script type="text/javascript">
    var path = "${ctx}";
    layui.use(['form', 'layer'], function () {
        var form = layui.form, layer = layui.layer;
        /**登录过期的时候，跳出ifram框架*/
        if (top.location != self.location) top.location = self.location;
        /**粒子线条背景*/
        $(document).ready(function () {
            $('.layui-container').particleground({
                dotColor: '#5cbdaa',
                lineColor: '#5cbdaa'
            });
        });
        /**进行登录操作*/
        form.on('submit(login)', function (data) {
            data = data.field;
            if (data.account == '') {
                layer.msg('用户名不能为空!');
                return false;
            }
            if (data.password == '') {
                layer.msg('密码不能为空!');
                return false;
            }
            login(layer);
        });
    });

    /**登陆操作*/
    function login(layer) {
        var storage = window.sessionStorage;
        storage.clear();
        /**loading风格&透明度&背景色&最长等待时间10秒*/
        var index = layer.load(1, {shade: [0.2, "black"]}, {time: 10 * 1000});
        $("#password").val(hex_md5($("#account").val() + $("#password").val()));
        /**请求的password参数通过MD5_SALT加密传入后台*/
        $.ajax({
            type: "post",
            url: path + "/login.do",
            data: $("#customForm").serializeArray(),
            dataType: "json",
            async: false,
            cache: false,
            success: function (result) {
                /**请求成功关闭layer*/
                layer.close(index);
                if (result.msg != undefined) {
                    layer.msg(result.msg, {icon: 5});
                    $("#account").focus();
                    return;
                } else {
                    /**登录跳转*/
                    window.location.href = path + "/index.do";
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                layer.close(index);
                layer.msg('登录失败！错误码（' + XMLHttpRequest.status + '）', {icon: 2});
                if (XMLHttpRequest.status != 0) {
                    layer.msg('登录失败！错误码（' + XMLHttpRequest.status + '）', {icon: 2});
                }
                return;
            }
        });
    }
</script>
</body>
</html>