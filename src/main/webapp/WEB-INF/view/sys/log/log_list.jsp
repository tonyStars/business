<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>系统日志管理</title>
    <%@include file="/WEB-INF/common/common.jsp" %>
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <fieldset class="layui-elem-field layuimini-search">
            <div style="margin: 10px 10px 10px 10px">
                <form class="layui-form layui-form-pane">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">菜单</label>
                            <div class="layui-input-inline">
                                <input type="text" name="menuName" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">IP地址</label>
                            <div class="layui-input-inline">
                                <input type="text" name="operIp" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">操作类型</label>
                            <div class="layui-input-inline">
                                <input type="text" name="typeName" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">状态</label>
                            <div class="layui-input-inline">
                                <select id="status" name="status">
                                    <option value="" selected>请选择</option>
                                    <option value="0">成功</option>
                                    <option value="-1">失败</option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">日期</label>
                            <div class="layui-input-inline">
                                <input type="text" name="startTime" id="startTime" autocomplete="off" class="layui-input">
                            </div>
                            <div class="layui-form-mid">至</div>
                            <div class="layui-input-inline">
                                <input type="text" name="endTime" id="endTime" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <a class="layui-btn layui-icon" lay-submit="" lay-filter="data-search-btn">&#xe615;搜索</a>
                        </div>
                    </div>
                </form>
            </div>
        </fieldset>
        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
    </div>
</div>
<script>
    layui.use(['form', 'table','laydate'], function () {
        var $ = layui.jquery,form = layui.form,table = layui.table,laydate = layui.laydate;
        laydate.render({
            elem: '#startTime'
        });
        laydate.render({
            elem: '#endTime'
        });
        var renderTable = function (){
            table.render({
                elem: '#currentTableId',
                url: path + "/sys/log/search.do",
                cols: [[
                    {field: 'userAccount', width: 100, title: '用户账号'},
                    {field: 'userName', width: 120, title: '用户名称'},
                    {field: 'menuName', minWidth: 200, title: '操作模块'},
                    {field: 'typeName', width: 100, title: '操作类型'},
                    {field: 'operIp', width: 150, title: '操作IP'},
                    {
                        field: 'status', width: 100, align: 'center', templet: function (d) {
                            if (d.status == 0) {
                                return '成功';
                            } else {
                                return '失败';
                            }
                        }, title: '操作状态'
                    },
                    {field: 'memo', title: '操作描述', minWidth: 120},
                    {field: 'operTime', width: 180, title: '操作时间', sort: true}
                ]],
                page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                    layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                        //,curr: 5 //设定初始在第 5 页
                        ,groups: 1 //只显示 1 个连续页码
                        ,first: false //不显示首页
                        ,last: false //不显示尾页
                },
                page: true,
                id: 'testReload'
            });
        }
        renderTable();
        /**监听搜索操作*/
        form.on('submit(data-search-btn)', function (data) {
            var result = data.field;
            /**执行搜索重载*/
            table.reload('testReload', {
                page: {
                    curr: 1
                }
                , where: {
                    menuName:  $.trim(result.menuName),
                    operIp: $.trim(result.operIp),
                    typeName: $.trim(result.typeName),
                    status: result.status,
                    startTime: result.startTime,
                    endTime: result.endTime
                }
            }, 'data');
            return false;
        });
    });
</script>
</body>
</html>