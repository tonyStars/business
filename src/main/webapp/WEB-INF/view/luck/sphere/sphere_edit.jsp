<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>双色球号码编辑</title>
    <%@include file="/WEB-INF/common/common.jsp" %>
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <form class="layui-form" id="customForm">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">期号</label>
                    <div class="layui-input-inline">
                        <input type="hidden" id="id" name="id" value="${model.id}" class="layui-input"/>
                        <input type="text" name="issueNumber" value="${model.issueNumber}" lay-verify="required" placeholder="请输入期号" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">开奖时间</label>
                    <div class="layui-input-inline">
                        <input type="text" name="openingTime" id="openingTime" value="${model.openingTime}" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">红号码1</label>
                    <div class="layui-input-inline">
                        <input type="text" name="redNumber1" value="${model.redNumber1}" lay-verify="required" placeholder="请输入红号码1" autocomplete="off" class="layui-input" onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')" maxlength='2'>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">红号码2</label>
                    <div class="layui-input-inline">
                        <input type="text" name="redNumber2" value="${model.redNumber2}" lay-verify="required" placeholder="请输入红号码2" autocomplete="off" class="layui-input" onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')" maxlength='2'>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">红号码3</label>
                    <div class="layui-input-inline">
                        <input type="text" name="redNumber3" value="${model.redNumber3}" lay-verify="required" placeholder="请输入红号码3" autocomplete="off" class="layui-input" onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')" maxlength='2'>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">红号码4</label>
                    <div class="layui-input-inline">
                        <input type="text" name="redNumber4" value="${model.redNumber4}" lay-verify="required" placeholder="请输入红号码4" autocomplete="off" class="layui-input" onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')" maxlength='2'>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">红号码5</label>
                    <div class="layui-input-inline">
                        <input type="text" name="redNumber5" value="${model.redNumber5}" lay-verify="required" placeholder="请输入红号码5" autocomplete="off" class="layui-input" onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')" maxlength='2'>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">红号码6</label>
                    <div class="layui-input-inline">
                        <input type="text" name="blueNumber6" value="${model.redNumber6}" lay-verify="required" placeholder="请输入红号码6" autocomplete="off" class="layui-input" onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')" maxlength='2'>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">蓝号码7</label>
                    <div class="layui-input-inline">
                        <input type="text" name="blueNumber7" value="${model.blueNumber7}" lay-verify="required" placeholder="请输入蓝号码7" autocomplete="off" class="layui-input" onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')" maxlength='2'>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    layui.use(['form', 'layer','laydate'], function () {
        var form = layui.form,laydate = layui.laydate, layer = layui.layer;
        laydate.render({
            elem: '#openingTime'
        });
        /**监听提交*/
        form.on('submit(demo1)', function (data) {
            commit(layer);
        });
    });
    /**提交请求*/
    function commit(layer){
        var index = layer.load(1, {shade: [0.2, "black"]}, {time: 5 * 1000});
        $.ajax({
            type: "post",
            url: path + "/luck/sphere/edit.do",
            data: $("#customForm").serializeArray(),
            dataType: "json",
            async: false,
            cache: false,
            success: function(result){
                layer.close(index);
                if(result.msg != undefined){
                    layer.msg(result.msg);
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