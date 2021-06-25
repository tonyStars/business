<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>系统角色修改</title>
    <%@include file="/WEB-INF/common/common.jsp" %>
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <form class="layui-form">
            <div class="layui-form-item">
                <label class="layui-form-label">角色名</label>
                <div class="layui-input-inline">
                    <input type="hidden" id="roleId" name="roleId" value="${model.roleId}" class="layui-input"/>
                    <input type="text" name="roleName" value="${model.roleName}" lay-verify="required" placeholder="请输入角色名" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">描述</label>
                <div class="layui-input-block">
                    <textarea placeholder="请输入内容" class="layui-textarea" name="roleDescribe">${model.roleDescribe}</textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    layui.use(['form', 'layer'], function () {
        var form = layui.form, layer = layui.layer;
        /**监听提交*/
        form.on('submit(demo1)', function (data) {
            commit(data.field,layer);
        });
    });
    /**提交请求*/
    function commit(data,layer){
        var index = layer.load(1, {shade: [0.2, "black"]}, {time: 5 * 1000});
        $.ajax({
            type: "post",
            url: path + "/sys/role/edit.do",
            data: {"roleId":data.roleId,"roleName":data.roleName,"roleDescribe":data.roleDescribe},
            dataType: "json",
            async: false,
            cache: false,
            success: function(result){
                layer.close(index);
                if(result.msg != undefined){
                    layer.msg(result.msg);
                    return;
                }else{
                    layer.msg("保存成功!");
                    parent.layer.close(window.parent.layer.getFrameIndex(window.name));
                    return;
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                layer.close(index);
                if(XMLHttpRequest.status != 0){
                    layer.msg("保存失败！错误码（" + XMLHttpRequest.status + "）");
                    return;
                }
            }
        });
    }
</script>
</body>
</html>