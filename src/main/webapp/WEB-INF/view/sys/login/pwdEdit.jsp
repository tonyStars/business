<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>修改密码</title>
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
                <label class="layui-form-label required">旧密码</label>
                <div class="layui-input-block">
                    <input type="password" name="orgpass" lay-verify="required" lay-reqtext="旧密码不能为空" placeholder="请输入旧的密码" class="layui-input">
                    <tip>请填写自己账号的旧密码!</tip>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label required">新密码</label>
                <div class="layui-input-block">
                    <input type="password" name="password" lay-verify="required" lay-reqtext="新密码不能为空" placeholder="请输入新密码" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label required">新密码确认</label>
                <div class="layui-input-block">
                    <input type="password" name="pass" lay-verify="required" lay-reqtext="新密码不能为空" placeholder="请输入新密码" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="saveBtn">确认保存</button>
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
    layui.use('form', function () {
        var form = layui.form;
        form.on('submit(saveBtn)', function (data) {
            commit(data.field);
            return false;
        });
    })
    /**确认保存*/
    function commit(data) {
        if(data.password != data.pass){
            layer.msg("新密码两次输入不一致！");
            return;
        }
        var layerIndex = layer.load(1, {shade: [0.3, '#000']});
        data.password = hex_md5(data.account + data.password);
        data.pass = hex_md5(data.account + data.pass);
        data.orgpass = hex_md5(data.account + data.orgpass);
        $.ajax({
            type: "post",
            url: path + "/updatePwd.do",
            data: {"orgpass":data.orgpass,"password":data.password},
            dataType: "json",
            async: false,
            cache: false,
            success: function (result) {
                layer.close(layerIndex);
                if (result.msg != undefined) {
                    layer.msg("保存失败！" + result.msg);
                    return;
                } else {
                    layer.msg("保存成功！");
                    parent.layer.close(window.parent.layer.getFrameIndex(window.name));
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                layer.close(layerIndex);
                if (XMLHttpRequest.status != 0) {
                    layer.msg("保存失败！错误码（" + XMLHttpRequest.status + "）");
                }
                return;
            }
        });
    }
</script>
</body>
</html>