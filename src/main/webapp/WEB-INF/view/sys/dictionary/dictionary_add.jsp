<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>数据字典新增</title>
    <%@include file="/WEB-INF/common/common.jsp" %>
</head>
<style>
    .layui-table td, .layui-table th {
        position: relative;
        padding: 9px 15px;
        min-height: 20px;
        line-height: 20px;
        font-size: 10px;
    }
</style>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <table class="layui-table" id="table" lay-filter="table">
            <thead>
                <tr>
                    <td>类型名称</td>
                    <td>子类型名称</td>
                    <td>值</td>
                    <td>排序</td>
                    <td>备注</td>
                    <td>操作</td>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><input type="text" class="layui-input" name="typeName"></td>
                    <td><input type="text" class="layui-input" name="itemName"></td>
                    <td><input type="text" class="layui-input" name="value"></td>
                    <td><input type="text" class="layui-input" name="orderId"></td>
                    <td><input type="text" class="layui-input" name="note"></td>
<%--                    <td>--%>
<%--                        <select>--%>
<%--                            <option value=""></option>--%>
<%--                            <option value="0">女</option>--%>
<%--                            <option value="1">男</option>--%>
<%--                        </select>--%>
<%--                    </td>--%>
                    <td>
                        <a class="layui-btn layui-btn-xs add">添加</a>
                        <a class="layui-btn layui-btn-danger layui-btn-xs del">删除</a>
                    </td>
                </tr>
            </tbody>
        </table>
        <div class="layui-input-inline">
            <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
        </div>
    </div>
</div>
<script>
    layui.use(['form', 'layer'], function () {
        var form = layui.form, layer = layui.layer;
        /**因为动态添加的元素class属性是无效的，所以不能用$('.add').click(function(){});*/
        $('body').on('click', '.add', function() {
            /**要添加的html*/
            var html = '<tr>'+
                '<td><input type="text" class="layui-input" name="typeName"></td>'+
                '<td><input type="text" class="layui-input" name="itemName"></td>'+
                '<td><input type="text" class="layui-input" name="value"></td>'+
                '<td><input type="text" class="layui-input" name="orderId"></td>'+
                '<td><input type="text" class="layui-input" name="note"></td>'+
                // '<td>'+
                // '<select>'+
                // '<option value=""></option>'+
                // '<option value="0">女</option>'+
                // '<option value="1">男</option>'+
                // '</select>'+
                // '</td>'+
                '<td>'+
                '<a class="layui-btn layui-btn-xs add">添加</a>'+
                '<a class="layui-btn layui-btn-danger layui-btn-xs del">删除</a>'+
                '</td>'+
                '</tr>';
            /**添加到表格最后*/
            $(html).appendTo($('#table tbody:last'));
            form.render();//如果有select元素，所有添加后要重新渲染一次
        });
        /**要删除的行*/
        $('body').on('click', '.del', function() {
            if ($('#table tbody tr').length === 1) {
                layer.msg('只有一条不允许删除。', {
                    time : 2000
                });
            } else {
                /**删除当前按钮所在的tr*/
                $(this).closest('tr').remove();
            }
        });
        /**监听提交*/
        form.on('submit(demo1)', function () {
            var datas = [];
            $('#table tr').each(function(i){
                var typeName = $(this).find('td:eq(0) input').val();//取表格的第i行第1列的input标签的值
                var itemName = $(this).find('td:eq(1) input').val();//取表格的第i行第2列的input标签的值
                var value = $(this).find('td:eq(2) input').val();//取表格的第i行第3列的input标签的值
                var orderId = $(this).find('td:eq(3) input').val();//取表格的第i行第4列的input标签的值
                var note = $(this).find('td:eq(4) input').val();//取表格的第i行第5列的input标签的值
                if(typeName != null && typeName != "" && itemName != null && itemName != "" && value != null && value != "" && orderId != null && orderId != ""){
                    var data = {"typeName":typeName,"itemName":itemName,"value":value,"orderId":orderId,"note":note};
                    datas.push(data);
                }
            });
            commit(datas,layer);
        });
    });
    /**提交请求*/
    function commit(data,layer){
        var index = layer.load(1, {shade: [0.2, "black"]}, {time: 5 * 1000});
        $.ajax({
            type: "post",
            url: path + "/sys/dictionary/add.do",
            data: {"data":JSON.stringify(data)},
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