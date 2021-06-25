<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WMS后台管理系统</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link rel="stylesheet" href="${ctx}/plugin/layuimini/css/login/default.css" media="all">
    <link rel="stylesheet" href="${ctx}/plugin/layuimini/css/login/styles.css" media="all">
    <link rel="stylesheet" href="${ctx}/plugin/layuimini/css/login/demo.css" media="all">
    <link rel="stylesheet" href="${ctx}/plugin/layuimini/css/login/loaders.css" media="all">
    <link rel="stylesheet" href="${ctx}/plugin/layuimini/lib/layui-v2.5.4/css/layui.css" media="all">
    <script>
    /**登陆判断是否是子页面跳转到登录页,如果是子页面,则定位到顶层父级页面登录*/
    if (window.top != null && window.top.document.URL != document.URL){
        window.top.location= document.URL;
    }
    </script>
</head>
<body>
<div class='login'>
    <div class='login_title'>
        <span>WMS后台管理系统 - 登录</span>
    </div>
    <div class='login_fields'>
        <div class='login_fields_user'>
            <div class='icon'>
                <img alt="" src='${ctx}/plugin/layuimini/images/login/user_icon_copy.png'>
            </div>
            <input name="login" placeholder='用户名' maxlength="16" type='text' autocomplete="off"/>
            <div class='validation'>
                <img alt="" src='${ctx}/plugin/layuimini/images/login/tick.png'>
            </div>
        </div>
        <div class='login_fields_password'>
            <div class='icon'>
                <img alt="" src='${ctx}/plugin/layuimini/images/login/lock_icon_copy.png'>
            </div>
            <input name="pwd" placeholder='密码' maxlength="16" type='text' autocomplete="off">
            <div class='validation'>
                <img alt="" src='${ctx}/plugin/layuimini/images/login/tick.png'>
            </div>
        </div>
        <div class='login_fields_password'>
            <div class='icon'>
                <img alt="" src='${ctx}/plugin/layuimini/images/login/key.png'>
            </div>
            <input name="code" placeholder='验证码' maxlength="4" type='text' name="ValidateNum" autocomplete="off">
            <div class='validation' style="opacity: 1; right: -5px;top: -3px;">
                <canvas class="J_codeimg" id="myCanvas" onclick="Code();">对不起，您的浏览器不支持canvas，请下载最新版浏览器!</canvas>
            </div>
        </div>
        <div class='login_fields__submit'>
            <input type='button' value='登录'>
        </div>
    </div>
    <div class='success'>
    </div>
    <div class='disclaimer'>
        <p>欢迎登录后台管理系统</p>
    </div>
</div>
<div class='authent'>
    <div class="loader" style="height: 44px;width: 44px;margin-left: 28px;">
        <div class="loader-inner ball-clip-rotate-multiple">
            <div></div>
            <div></div>
            <div></div>
        </div>
    </div>
    <p>认证中...</p>
</div>
<div class="OverWindows"></div>
<script src="${ctx}/plugin/layuimini/lib/jquery-3.4.1/jquery-3.4.1.min.js" charset="UTF-8"></script>
<script src="${ctx}/plugin/layuimini/lib/layui-v2.5.4/layui.js" charset="UTF-8"></script>
<script src="${ctx}/plugin/layuimini/lib/login/jquery-ui.min.js" charset="UTF-8"></script>
<script src="${ctx}/plugin/layuimini/lib/login/stopExecutionOnTimeout.js?t=1" charset="UTF-8"></script>
<script src="${ctx}/plugin/layuimini/lib/login/Particleground.js" charset="UTF-8"></script>
<script src="${ctx}/plugin/layuimini/lib/login/Treatment.js" charset="UTF-8"></script>
<script src="${ctx}/plugin/layuimini/lib/login/jquery.mockjax.js" charset="UTF-8"></script>
<script src="${ctx}/js/md5.js" charset="UTF-8"></script>
<script type="text/javascript">
    var path = "${ctx}";
    var canGetCookie = 0;//是否支持存储Cookie 0 不支持 1 支持
    var CodeVal = 0;
    Code();
    /**监听键盘回车事件,点击回车则触发button按钮*/
    $(document).keypress(function (e) {
        if (e.which == 13) {
            $('input[type="button"]').click();
        }
    });
    /**生成验证码*/
    function Code() {
        if(canGetCookie == 1){
            createCode("AdminCode");
            var AdminCode = getCookieValue("AdminCode");
            showCheck(AdminCode);
        }else{
            showCheck(createCode(""));
        }
    }
    function showCheck(a) {
        CodeVal = a;
        var c = document.getElementById("myCanvas");
        var ctx = c.getContext("2d");
        ctx.clearRect(0, 0, 1000, 1000);
        ctx.font = "80px 'Hiragino Sans GB'";
        ctx.fillStyle = "#E8DFE8";
        ctx.fillText(a, 0, 100);
    }
    /**生成验证码*/
    /**粒子背景特效**/
    $('body').particleground({
        dotColor: '#E8DFE8',
        lineColor: '#133b88'
    });
    $('input[name="pwd"]').focus(function () {
        $(this).attr('type', 'password');
    });
    $('input[type="text"]').focus(function () {
        $(this).prev().animate({ 'opacity': '1' }, 200);
    });
    $('input[type="text"],input[type="password"]').blur(function () {
        $(this).prev().animate({ 'opacity': '.5' }, 200);
    });
    $('input[name="login"],input[name="pwd"]').keyup(function () {
        var Len = $(this).val().length;
        if (!$(this).val() == '' && Len >= 5) {
            $(this).next().animate({
                'opacity': '1',
                'right': '30'
            }, 200);
        } else {
            $(this).next().animate({
                'opacity': '0',
                'right': '20'
            }, 200);
        }
    });
    /**粒子背景特效**/
    layui.use('layer', function () {
        /**非空验证*/
        $('input[type="button"]').click(function () {
            var login = $('input[name="login"]').val();
            var pwd = $('input[name="pwd"]').val();
            var code = $('input[name="code"]').val();
            if (login == '') {
                ErroAlert('请输入您的账号');
            } else if (pwd == '') {
                ErroAlert('请输入您的密码');
            } else if (code == '') {
                ErroAlert('请输入验证码');
            }else if(code.length != 4){
                ErroAlert('请输入4位数验证码');
            } else {
                /**认证中..*/
                // fullscreen();
                /**验证参数结束,进行特效渲染*/
                $('.login').addClass('test'); //倾斜特效
                setTimeout(function () {
                    $('.login').addClass('testtwo'); //平移特效
                }, 300);
                setTimeout(function () {
                    $('.authent').show().animate({ right: -320 }, {
                        easing: 'easeOutQuint',
                        duration: 600,
                        queue: false
                    });
                    $('.authent').animate({ opacity: 1 }, {
                        duration: 200,
                        queue: false
                    }).addClass('visible');
                }, 500);
                /**验证参数结束,进行特效渲染*/
                /**配置登录参数*/
                var JsonData = {account: login, password: hex_md5(login + pwd), code: code};
                /**ajax内部判断验证码*/
                var url = "";
                /**校验输入的验证码是否正确*/
                if(JsonData.code.toUpperCase() == CodeVal.toUpperCase()){
                    url = path + "/login.do";
                }else{
                    /**输入的验证码不正确则执行下列方法*/
                    setTimeout(function () {
                        $('.authent').show().animate({ right: 90 }, {
                            easing: 'easeOutQuint',
                            duration: 600,
                            queue: false
                        });
                        $('.authent').animate({ opacity: 0 }, {
                            duration: 200,
                            queue: false
                        }).addClass('visible');
                        $('.login').removeClass('testtwo'); //平移特效
                    }, 2000);
                    setTimeout(function () {
                        $('.authent').hide();
                        $('.login').removeClass('test');
                        ErroAlert('验证码不正确');
                    }, 2400);
                    return;
                    /**输入的验证码不正确则执行下列方法*/
                }
                /**输入的验证码正确*/
                AjaxPost(url, JsonData,
                    function () {
                        //ajax加载中
                    },
                    /**ajax返回*/
                    function (data) {
                        /**认证完成*/
                        setTimeout(function () {
                            $('.authent').show().animate({ right: 90 }, {
                                easing: 'easeOutQuint',
                                duration: 600,
                                queue: false
                            });
                            $('.authent').animate({ opacity: 0 }, {
                                duration: 200,
                                queue: false
                            }).addClass('visible');
                            $('.login').removeClass('testtwo'); //平移特效
                        }, 2000);
                        setTimeout(function () {
                            $('.authent').hide();
                            $('.login').removeClass('test');
                            if (data.msg != undefined) {
                                ErroAlert(data.msg);
                            } else {
                                /**登录成功*/
                                $('.login div').fadeOut(100);
                                $('.success').fadeIn(1000);
                                $('.success').html(data.Text);
                                /**跳转首页操作*/
                                window.location.href = path + "/index.do";
                            }
                        }, 2400);
                    })
            }
        })
    })
    var fullscreen = function () {
        elem = document.body;
        if (elem.webkitRequestFullScreen) {
            elem.webkitRequestFullScreen();
        } else if (elem.mozRequestFullScreen) {
            elem.mozRequestFullScreen();
        } else if (elem.requestFullScreen) {
            elem.requestFullscreen();
        } else {
            //浏览器不支持全屏API或已被禁用
        }
    }
</script>
</body>
</html>