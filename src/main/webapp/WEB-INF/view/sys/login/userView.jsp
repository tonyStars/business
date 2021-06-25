<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>基本资料</title>
    <%@include file="/WEB-INF/common/common.jsp" %>
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <div class="layui-form layuimini-form">
            <div class="layui-form-item">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-block">
                    <input type="text" name="account" value="${user.userCode}" class="layui-input" readonly>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">用户姓名</label>
                <div class="layui-input-block">
                    <input type="text" name="userName" value="${user.userName}" class="layui-input" readonly>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">手机号码</label>
                <div class="layui-input-block">
                    <input type="text" name="phone" value="${user.phone}" class="layui-input" readonly>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-primary" onclick="parent.layer.close(window.parent.layer.getFrameIndex(window.name));">取消</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    /**键盘按键监听*/
    document.onkeydown = function (e) {
        if (e.keyCode == 27) {
            var index = window.parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    };
</script>
</body>
</html>