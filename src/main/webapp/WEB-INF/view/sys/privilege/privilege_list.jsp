<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>菜单权限管理</title>
    <%@include file="/WEB-INF/common/common.jsp" %>
    <style>
        .layui-btn:not(.layui-btn-lg ):not(.layui-btn-sm):not(.layui-btn-xs) {
            height: 34px;
            line-height: 34px;
            padding: 0 8px;
        }
        .laytable-cell-1-0-7 {
            width: 80px;
            display: flex;
            justify-content: center;
            align-items: center;
        }
    </style>
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <div>
            <div class="layui-btn-group">
                <button class="layui-btn layui-icon" id="btn-add">&#xe654;新增</button>
                <button class="layui-btn layui-icon" id="btn-sort">&#xe656;排序</button>
            </div>
            <div class="layui-btn-group" style="float:right">
                <button class="layui-btn layui-btn-primary layui-icon" id="btn-expand">&#xe61a;全部展开</button>
                <button class="layui-btn layui-btn-primary layui-icon" id="btn-fold">&#xe619;全部折叠</button>
            </div>
            <table id="munu-table" class="layui-table" lay-filter="munu-table"></table>
        </div>
    </div>
</div>
<!-- 操作列 -->
<script type="text/html" id="auth-state">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<script>
    layui.use(['table', 'treetable'], function () {
        var $ = layui.jquery;
        var table = layui.table;
        var treetable = layui.treetable;
        var renderTable = function () {
            /**渲染表格*/
            layer.load(2);
            treetable.render({
                treeColIndex: 1,
                treeSpid: 0,//顶级菜单父标签Id
                treeIdName: 'privilegeId',
                treePidName: 'parentId',
                elem: '#munu-table',
                url: path + "/sys/privilege/search.do",
                page: false,
                cols: [[
                    {type: 'numbers'},
                    {field: 'privilegeName', minWidth: 120, title: '权限名称'},
                    {field: 'parentName', minWidth: 120, title: '父权限名'},
                    {field: 'url',width: 180, title: 'URL'},
                    {field: 'grade', width: 100, align: 'center', title: '菜单级别'},
                    {field: 'icon', width: 150, align: 'center', title: '图标代码'},
                    {field: 'sort', width: 80, align: 'center', title: '排序号'},
                    {
                        field: 'tp', width: 80, align: 'center', templet: function (d) {
                            if (d.tp == 1) {
                                return '<span class="layui-badge layui-bg-gray">按钮</span>';
                            }
                            if (d.tp == 2) {
                                return '<span class="layui-badge layui-bg-blue">导航</span>';
                            } else {
                                return '<span class="layui-badge-rim">菜单</span>';
                            }
                        }, title: '类型'
                    },
                    {templet: '#auth-state', width: 120, align: 'center', title: '操作'}
                ]],
                done: function () {
                    layer.closeAll('loading');
                    treetable.foldAll('#munu-table');
                }
            });
        };
        renderTable();
        /**
         * 全部展开操作按钮
         */
        $('#btn-expand').click(function () {
            treetable.expandAll('#munu-table');
        });
        /**
         * 全部折叠操作按钮
         */
        $('#btn-fold').click(function () {
            treetable.foldAll('#munu-table');
        });
        /**新增按钮触发*/
        $('#btn-add').click(function () {
            layer.open({
                title: "新增权限",
                type: 2,
                area: ["100%", "100%"],
                fixed: false, // 鼠标滚动时，层是否固定在可视区域
                maxmin: false, // 放大缩小按钮
                content: path + "/sys/privilege/toAdd.do",
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
        /**排序按钮触发*/
        $('#btn-sort').click(function () {
            layer.open({
                title: "功能权限排序",
                type: 2,
                area: ["100%", "100%"],
                fixed: false, // 鼠标滚动时，层是否固定在可视区域
                maxmin: false, // 放大缩小按钮
                content: path + "/sys/privilege/toSort.do",
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
        /**监听工具条*/
        table.on('tool(munu-table)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            /**修改按钮操作*/
            if(layEvent === 'edit'){
                layer.open({
                    title: "修改权限",
                    type: 2,
                    area: ["100%", "100%"],
                    fixed: false, // 鼠标滚动时，层是否固定在可视区域
                    maxmin: false, // 放大缩小按钮
                    content: path + "/sys/privilege/toEdit.do?privilegeId=" + data.privilegeId,
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
            /**删除按钮操作*/
            if (layEvent === 'del') {
                layer.confirm('确定删除此功能?', function (index) {
                    obj.del();
                    /**确定则执行方法*/
                    $.ajax({
                        type: "post",
                        url: path + "/sys/privilege/del.do",
                        data: {"privilegeId":data.privilegeId},
                        dataType: "json",
                        async: false,
                        cache: false,
                        success: function(result){
                            /**关闭弹窗*/
                            layer.close(index);
                            if(result.msg != undefined){
                                layer.msg(result.msg);
                                renderTable();
                                return;
                            }else{
                                layer.msg("功能删除成功!");
                                renderTable();
                                return;
                            }
                        },
                        error: function(XMLHttpRequest, textStatus, errorThrown) {
                            /**关闭弹窗*/
                            layer.close(index);
                            if(XMLHttpRequest.status != 0){
                                layer.msg("保存失败！错误码（" + XMLHttpRequest.status + "）");
                                renderTable();
                                return;
                            }
                        }
                    });
                });
            }
        });
    });
</script>
</body>
</html>