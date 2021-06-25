<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.buttonAuthor.com/taglibs" prefix="wms" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <!-- --------css------- -->
    <!--浏览器打开标签图标-->
    <link rel="icon" href="${ctx}/plugin/layuimini/images/favicon.ico">
    <!--浏览器打开标签图标-->
    <link rel="stylesheet" href="${ctx}/plugin/layuimini/lib/layui-v2.5.4/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/plugin/layuimini/css/layuimini.css" media="all">
    <link rel="stylesheet" href="${ctx}/plugin/layuimini/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="${ctx}/plugin/layuimini/css/public.css" media="all">
    <link rel="stylesheet" href="${ctx}/plugin/layuimini/css/myself.css" media="all">
    <!--消息通知必要样式-->
    <link rel="stylesheet" href="${ctx}/./plugin/topic-msg/css/naranja.min.css">
    <!-- --------js-------- -->
    <script src="${ctx}/plugin/layuimini/lib/jquery-3.4.1/jquery-3.4.1.min.js" charset="UTF-8"></script>
    <script src="${ctx}/plugin/layuimini/lib/layui-v2.5.4/layui.js" charset="UTF-8"></script>
    <script src="${ctx}/plugin/layuimini/lib/jq-module/jquery.particleground.min.js" charset="UTF-8"></script>
    <script src="${ctx}/plugin/layuimini/js/lay-config.js?v=1.0.4" charset="UTF-8"></script>
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<%--    <script src="${ctx}/view/common/common.js" charset="utf-8"></script>--%>
    <!-- 有个别页面使用了 -->
    <script src="${ctx}/js/md5.js" charset="UTF-8"></script>
    <script src="${ctx}/view/common/common.js" charset="UTF-8"></script>
    <!--消息通知必要js-->
    <script type="text/javascript" src="${ctx}/./plugin/topic-msg/js/naranja.js"></script>
</head>
<body>
<input type="hidden" id="msgId" name="msgId" value="0"/>
<script>
    /**登录成功之后进行长连接消息提示*/
    // nettyConnect();
    var socket;
    var userId = ${sessionScope.sessionUser.userId};
    function nettyConnect() {
        // 判断当前浏览器是否支持webSocket
        if(window.WebSocket){
            socket = new WebSocket("ws://localhost:58080/webSocket")
            // 相当于channel的read事件，ev 收到服务器回送的消息
            socket.onmessage = function (ev) {
                if(ev.data != null || ev.data != ""){
                    var data = ev.data;
                    var m = data.split(",")[0];
                    data.substring(data.indexOf(',') + 1, data.length);
                    narn ('log','通知信息',data,5000);
                    /**取出的数据不是数字,则不赋值*/
                    if(!isNaN(m)){
                        $('#msgId').val(m);
                    }
                }
            }
            // 相当于连接开启
            socket.onopen = function (ev) {
                socket.send(
                    JSON.stringify({
                        // 连接成功将，用户ID传给服务端
                        uid: userId
                    })
                );
            }
            // 相当于连接关闭
            socket.onclose = function (ev) {
                narn ('log','通知信息','连接关闭了...',5000);
            }
        }else{
            alert("当前浏览器不支持webSocket");
        }

        // 如果前端需要保持连接,则需要定时往服务器针对自己发送请求,返回的参数和发送参数一致则证明时间段内有交互,服务端则不进行连接断开操作
        var int = setInterval(function clock() {
            socket.send(
                JSON.stringify({
                    // 连接成功将，用户ID传给服务端
                    uid: userId
                })
            );
        },30000);//1500000为25分钟,定时循环,后台设置30的心跳检测时间

    }

    /**
     * 消息通知右下角提示
     * @param type 提示样式(默认:log 成功:success 警告:warn 危险:error)
     * @param title 消息提示标题
     * @param msg 消息内容
     * @param time 持续时间('keep'为永久持续,5000为5秒显示)
     */
    function narn (type,title,msg,time) {
        naranja()[type]({
            title: title,
            text: msg,
            timeout: time,
            buttons: [{
                text: '接受',
                click: function (e) {
                    naranja().success({
                        title: '通知',
                        text: '通知被接受'
                    });
                }
            },{
                text: '取消',
                click: function (e) {
                    e.closeNotification();
                }
            }]
        })
    }

    /**
     * 消息回调 - 告知服务器已经收到消息
     * @param msgId 消息id
     */
    function callBack(msgId) {
        if(msgId != null && msgId != ""){
            $.ajax({
                type: "post",
                url: path + "/sys/massage/callBack.do",
                data: {"userId":userId,"msgId":msgId},
                dataType: "json",
                async: true,
                cache: false,
                success: function(result){
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                }
            });
        }
    }

    /**
     * 获取离线消息接口
     * @param uid 用户id
     */
    function outLineMsg(uid) {
        $.ajax({
            type: "post",
            url: path + "/sys/massage/outLineMsg.do",
            data: {"userId":uid},
            dataType: "json",
            async: true,
            cache: false,
            success: function(result){
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
</script>
</body>
<script>
    var path = "${ctx}";
    /**默认引入外部js插件,后期有外部插件可以存放在此路径下,默认引入*/
    layui.config({
    	base: path + '/js/app/modules/'
    });
    var sessionUser = '${sessionScope.sessionUser}';
    /**监听消息id发送改变则使用回调函数,代表有新消息*/
    // $('#msgId').bind('input oninput', function() {
    //     callBack(userId,$('#msgId').val());
    // });
    /**建立连接页面开启后调后台接口将该用户未接收到的消息进行获取*/
    outLineMsg(userId);
    var mId = 0;
    var bac = setInterval(function back() {
        if(mId != $("#msgId").val()){
            mId = $("#msgId").val();
            callBack($("#msgId").val());
        }
    },5000);
</script>
</html>