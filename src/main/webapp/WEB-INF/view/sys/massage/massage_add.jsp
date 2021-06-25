<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>新增消息</title>
    <%@include file="/WEB-INF/common/common.jsp" %>
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <form class="layui-form" id="customForm">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">消息类型</label>
                    <div class="layui-input-inline">
                        <select id="type" name="type">
                            <option value="1">单独推送</option>
                            <option value="2">群发</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">接收用户</label>
                    <div class="layui-input-inline">
                        <input type="hidden" id="userId" name="userId" class="layui-input"/>
                        <input type="text" id="userName" name="userName" placeholder="请选择用户" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">消息内容</label>
                    <div class="layui-input-block">
                        <textarea placeholder="请输入消息内容" class="layui-textarea" name="massage"></textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
                    <button class="layui-btn layui-btn-primary" onclick="parent.layer.close(window.parent.layer.getFrameIndex(window.name));">取消</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    layui.use(['form', 'layer','tableSelect'], function () {
        var form = layui.form, layer = layui.layer,tableSelect = layui.tableSelect;
        tableSelect.render({
            elem: '#userName',
            checkedKey: 'userId',
            table: {
                url: path + '/sys/user/searchSelect.do',
                cols: [[
                    { type: 'radio' },
                    { field: 'userId', title: '用户ID' },
                    { field: 'userName', title: '用户名称' }
                ]],
                page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                    layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                    //,curr: 5 //设定初始在第 5 页
                    ,groups: 1 //只显示 1 个连续页码
                    ,first: false //不显示首页
                    ,last: false //不显示尾页
                },
                page: true
            },
            done: function (elem, data) {
                var userName;
                var userId;
                layui.each(data.data, function (index, item) {
                    userName = item.userName;
                    userId = item.userId;
                })
                elem.val(userName);
                $("#userId").val(userId);
            }
        })
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
            url: path + "/sys/massage/add.do",
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