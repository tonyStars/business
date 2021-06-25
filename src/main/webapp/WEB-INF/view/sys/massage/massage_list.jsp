<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>系统消息管理</title>
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
                            <label class="layui-form-label">消息内容</label>
                            <div class="layui-input-inline">
                                <input type="text" name="massage" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">消息类型</label>
                            <div class="layui-input-inline">
                                <select id="type" name="type">
                                    <option value="" selected>请选择</option>
                                    <option value="1">单独推送</option>
                                    <option value="2">群发</option>
                                </select>
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
            <button class="layui-btn layui-icon data-online-btn">&#xe7ae;在线人数</button>
        </div>
        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
<%--        <script type="text/html" id="currentTableBar">--%>
<%--            <a class="layui-btn layui-btn-xs data-count-edit" lay-event="edit">编辑</a>--%>
<%--        </script>--%>
    </div>
</div>
<script>
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,form = layui.form,table = layui.table;
        var renderTable = function (){
            table.render({
                elem: '#currentTableId',
                url: path + "/sys/massage/search.do",
                cols: [[
                    {type: "checkbox", width: 50, fixed: "left"},
                    {field: 'createUserId', width: 120, title: '发送用户'},
                    {field: 'massage', minWidth: 200, title: '消息内容'},
                    {
                        field: 'type', width: 120, align: 'center', templet: function (d) {
                            if (d.type == 1) {
                                return '单独推送';
                            } else {
                                return '群发';
                            }
                        }, title: '消息类型'
                    },
                    {field: 'createTime', width: 180, title: '创建时间', sort: true},
                    // {title: '操作', width: 80, templet: '#currentTableBar', fixed: "right", align: "center"}
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
                    massage: result.massage,
                    type: result.type
                }
            }, 'data');
            return false;
        });
        /**监听添加操作*/
        $(".data-add-btn").on("click", function () {
            layer.open({
                title: "新增消息",
                type: 2,
                area: ["100%", "100%"],
                fixed: false, // 鼠标滚动时，层是否固定在可视区域
                maxmin: false, // 放大缩小按钮
                content: path + "/sys/massage/toAdd.do",
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
        /**监听获取在线人数操作*/
        $(".data-online-btn").on("click", function () {
            var index = layer.load(1, {shade: [0.2, "black"]}, {time: 5 * 1000});
            $.ajax({
                type: "get",
                url: path + "/sys/massage/getConnectCount.do",
                dataType: "json",
                async: false,
                cache: false,
                success: function(result){
                    layer.close(index);
                    if(result.msg != undefined){
                        layer.msg(result.msg);
                    }else{
                        layer.msg("当前在线：" + result + " 人!");
                        return;
                    }
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    layer.close(index);
                    if(XMLHttpRequest.status != 0){
                        layer.msg("失败！错误码（" + XMLHttpRequest.status + "）");
                        return;
                    }
                }
            });
        });
        /**监听表格复选框选择*/
        table.on('checkbox(currentTableFilter)', function (obj) {
            console.log(obj)
        });
        table.on('tool(currentTableFilter)', function (obj) {
            // var data = obj.data;
            // if (obj.event === 'edit') {
            //     layer.open({
            //         title: "修改消息",
            //         type: 2,
            //         area: ["100%", "100%"],
            //         fixed: false, // 鼠标滚动时，层是否固定在可视区域
            //         maxmin: false, // 放大缩小按钮
            //         content: path + "/sys/massage/toEdit.do?id=" + data.id,
            //         success: function(layero, index){ // 层弹出成功后回调参数：当前层DOM&当前层索引
            //             //键盘按键监听
            //             $(document).on("keydown", function(e){
            //                 if (e.keyCode == 13){ // 禁用回车事件 以免层重复弹出
            //                     return false;
            //                 }
            //                 if (e.keyCode == 27){ //ESC退出
            //                     layer.close(index);
            //                 }
            //             })
            //         },
            //         end: function () {
            //             renderTable();
            //         }
            //     });
            // }
        });
    });
    /**封装reload方法*/
    function reloadTable(){
        layui.use('table', function(){
            var table = layui.table;
            var massage = $.trim($('#massage').val());
            var type = $.trim($('#type').val());
            //执行重载
            table.reload('testReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    massage: massage,
                    type: type
                }
            });
        });
    }
</script>
</body>
</html>