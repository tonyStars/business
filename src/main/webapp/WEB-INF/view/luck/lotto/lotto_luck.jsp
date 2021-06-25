<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>大乐透下期幸运号码</title>
    <%@include file="/WEB-INF/common/common.jsp" %>
</head>
<style>
    .layui-table td, .layui-table th {
        position: relative;
        padding: 9px 15px;
        min-height: 20px;
        line-height: 20px;
    }
</style>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <table class="layui-table" id="table" lay-filter="table">
            <thead>
            <tr>
                <td>红号码1</td>
                <td>红号码2</td>
                <td>红号码3</td>
                <td>红号码4</td>
                <td>红号码5</td>
                <td>蓝号码6</td>
                <td>蓝号码7</td>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><input type="text" class="layui-input" id="redNumber1" readonly></td>
                <td><input type="text" class="layui-input" id="redNumber2" readonly></td>
                <td><input type="text" class="layui-input" id="redNumber3" readonly></td>
                <td><input type="text" class="layui-input" id="redNumber4" readonly></td>
                <td><input type="text" class="layui-input" id="redNumber5" readonly></td>
                <td><input type="text" class="layui-input" id="blueNumber6" readonly></td>
                <td><input type="text" class="layui-input" id="blueNumber7" readonly></td>
            </tr>
            </tbody>
        </table>
        <div class="layui-input-inline" >
            <button class="layui-btn" lay-submit="" lay-filter="demo1">抽一组</button>
        </div>
    </div>
</div>
<script>
    layui.use(['form', 'layer'], function () {
        var form = layui.form, layer = layui.layer;
        /**监听提交*/
        form.on('submit(demo1)', function (data) {
            commit(layer);
        });
    });
    /**提交请求*/
    function commit(layer){
        var index = layer.load(1, {shade: [0.2, "black"]}, {time: 5 * 1000});
        $.ajax({
            type: "get",
            url: path + "/luck/lotto/luck.do",
            dataType: "json",
            async: false,
            cache: false,
            success: function(result){
                layer.close(index);
                if(result.msg != undefined){
                    layer.msg(result.msg);
                }else{
                    var data = result.data;
                    $('#redNumber1').val(data.redNumber1);
                    $('#redNumber2').val(data.redNumber2);
                    $('#redNumber3').val(data.redNumber3);
                    $('#redNumber4').val(data.redNumber4);
                    $('#redNumber5').val(data.redNumber5);
                    $('#blueNumber6').val(data.blueNumber6);
                    $('#blueNumber7').val(data.blueNumber7);
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