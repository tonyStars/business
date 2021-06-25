<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>大乐透管理</title>
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
                            <label class="layui-form-label">期号</label>
                            <div class="layui-input-inline">
                                <input type="text" name="issueNumber" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">开奖时间</label>
                            <div class="layui-input-inline">
                                <input type="text" name="startDate" id="startDate" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
                            </div>
                            <div class="layui-form-mid">至</div>
                            <div class="layui-input-inline">
                                <input type="text" name="endDate" id="endDate" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <a class="layui-btn layui-icon" lay-submit="" lay-filter="data-search-btn">&#xe615;搜索</a>
                        </div>
                    </div>
                </form>
            </div>
        </fieldset>
        <div class="layui-btn-group">
            <button class="layui-btn layui-icon data-add-btn">&#xe654;添加</button>
            <button class="layui-btn layui-icon data-luck-btn">&#xe659;幸运号码</button>
            <button class="layui-btn layui-icon data-rollluck-btn">&#xe659;随机抽奖</button>
        </div>
        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
        <script type="text/html" id="currentTableBar">
            <a class="layui-btn layui-btn-xs data-count-edit" lay-event="edit">编辑</a>
        </script>
    </div>
</div>
<script>
    layui.use(['form', 'table','laydate'], function () {
        var $ = layui.jquery,
            form = layui.form,
            laydate = layui.laydate,
            table = layui.table;
        laydate.render({
            elem: '#startDate'
        });
        laydate.render({
            elem: '#endDate'
        });
        var renderTable = function (){
            table.render({
                elem: '#currentTableId',
                url: path + "/luck/lotto/search.do",
                cols: [[
                    {type: "checkbox", width: 50, fixed: "left"},
                    {field: 'issueNumber', width: 150, title: '期号'},
                    {field: 'openingTime', width: 180, title: '开奖时间'},
                    {field: 'redNumber1', width: 120, title: '红号码1'},
                    {field: 'redNumber2', width: 120, title: '红号码2'},
                    {field: 'redNumber3', width: 120, title: '红号码3'},
                    {field: 'redNumber4', width: 120, title: '红号码4'},
                    {field: 'redNumber5', width: 120, title: '红号码5'},
                    {field: 'blueNumber6', width: 120, title: '蓝号码6'},
                    {field: 'blueNumber7', width: 120, title: '蓝号码7'},
                    {field: 'createTime', minWidth: 250, title: '创建时间', sort: true},
                    {title: '操作', width: 120, templet: '#currentTableBar', fixed: "right", align: "center"}
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
                    issueNumber: result.issueNumber,
                    startDate: result.startDate,
                    endDate: result.endDate
                }
            }, 'data');
            return false;
        });
        /**监听添加操作*/
        $(".data-add-btn").on("click", function () {
            layer.open({
                title: "新增号码",
                type: 2,
                area: ["100%", "100%"],
                fixed: false, // 鼠标滚动时，层是否固定在可视区域
                maxmin: false, // 放大缩小按钮
                content: path + "/luck/lotto/toAdd.do",
                success: function(layero, index){ // 层弹出成功后回调参数：当前层DOM&当前层索引
                    //键盘按键监听
                    $(document).on("keydown", function(e){
                        if (e.keyCode == 13){ // 禁用回车事件 以免层重复弹出
                            return false;
                        }
                        if (e.keyCode == 27){ //ESC退出
                            layer.close(index);
                        }
                    })
                },
                end: function () {
                    renderTable();
                }
            });
        });
        /**监听幸运号码操作*/
        $(".data-luck-btn").on("click", function () {
            layer.open({
                title: "大乐透幸运号码",
                type: 2,
                area: ["60%", "50%"],
                fixed: false, // 鼠标滚动时，层是否固定在可视区域
                maxmin: false, // 放大缩小按钮
                content: path + "/luck/lotto/toLuck.do",
                success: function(layero, index){ // 层弹出成功后回调参数：当前层DOM&当前层索引
                    //键盘按键监听
                    $(document).on("keydown", function(e){
                        if (e.keyCode == 13){ // 禁用回车事件 以免层重复弹出
                            return false;
                        }
                        if (e.keyCode == 27){ //ESC退出
                            layer.close(index);
                        }
                    })
                },
                end: function () {
                    renderTable();
                }
            });
        });
        /**监听随机抽奖操作*/
        $(".data-rollluck-btn").on("click", function () {
            layer.open({
                title: "大乐透号码抽奖",
                type: 2,
                area: ["19%", "19%"],
                fixed: false, // 鼠标滚动时，层是否固定在可视区域
                maxmin: false, // 放大缩小按钮
                content: path + "/luck/lotto/toRollLuck.do",
                success: function(layero, index){ // 层弹出成功后回调参数：当前层DOM&当前层索引
                    //键盘按键监听
                    $(document).on("keydown", function(e){
                        if (e.keyCode == 13){ // 禁用回车事件 以免层重复弹出
                            return false;
                        }
                        if (e.keyCode == 27){ //ESC退出
                            layer.close(index);
                        }
                    })
                },
                end: function () {
                    renderTable();
                }
            });
        });
        /**监听表格复选框选择*/
        table.on('checkbox(currentTableFilter)', function (obj) {
            console.log(obj)
        });
        table.on('tool(currentTableFilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'edit') {
                layer.open({
                    title: "修改号码",
                    type: 2,
                    area: ["100%", "100%"],
                    fixed: false, // 鼠标滚动时，层是否固定在可视区域
                    maxmin: false, // 放大缩小按钮
                    content: path + "/luck/lotto/toEdit.do?id=" + data.id,
                    success: function(layero, index){ // 层弹出成功后回调参数：当前层DOM&当前层索引
                        //键盘按键监听
                        $(document).on("keydown", function(e){
                            if (e.keyCode == 13){ // 禁用回车事件 以免层重复弹出
                                return false;
                            }
                            if (e.keyCode == 27){ //ESC退出
                                layer.close(index);
                            }
                        })
                    },
                    end: function () {
                        renderTable();
                    }
                });
            }
        });
    });
    /**封装reload方法*/
    function reloadTable(){
        layui.use('table', function(){
            var table = layui.table;
            var issueNumber = $.trim($('#issueNumber').val());
            var startDate = $.trim($('#startDate').val());
            var endDate = $.trim($('#endDate').val());
            //执行重载
            table.reload('testReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    issueNumber: issueNumber,
                    startDate: startDate,
                    endDate: endDate
                }
            });
        });
    }
</script>
</body>
</html>