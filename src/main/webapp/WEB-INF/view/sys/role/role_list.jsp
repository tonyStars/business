<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>系统角色管理</title>
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
                            <label class="layui-form-label">角色名</label>
                            <div class="layui-input-inline">
                                <input type="text" name="roleName" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">状态</label>
                            <div class="layui-input-block">
                                <select id="status" name="status">
                                    <option value="" selected>请选择</option>
                                    <option value="0">启用</option>
                                    <option value="-1">停用</option>
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
            <button class="layui-btn layui-icon layui-btn-danger data-open-btn">&#xe605;启用</button>
            <button class="layui-btn layui-icon layui-btn-danger data-delete-btn">&#xe640;停用</button>
        </div>
        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
        <script type="text/html" id="currentTableBar">
            <a class="layui-btn layui-btn-xs data-count-edit" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-xs layui-btn-danger data-count-authorize" lay-event="authorize">授权</a>
        </script>
    </div>
</div>
<script>
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;
        var renderTable = function (){
            table.render({
                elem: '#currentTableId',
                url: path + "/sys/role/search.do",
                cols: [[
                    {type: "checkbox", width: 50, fixed: "left"},
                    {field: 'roleId', width: 80, title: 'ID'},
                    {field: 'roleName', width: 150, title: '角色名称'},
                    {field: 'roleDescribe', minWidth: 200, title: '角色描述'},
                    {
                        field: 'status', width: 80, align: 'center', templet: function (d) {
                            if (d.status == 0) {
                                return '启用';
                            } else {
                                return '停用';
                            }
                        }, title: '状态'
                    },
                    {field: 'entryUserName', title: '创建人', width: 120},
                    {field: 'entryUserTime', width: 180, title: '创建时间', sort: true},
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
                    roleName: result.roleName,
                    status: result.status
                }
            }, 'data');
            return false;
        });
        /**监听添加操作*/
        $(".data-add-btn").on("click", function () {
            layer.open({
                title: "新增角色",
                type: 2,
                area: ["100%", "100%"],
                fixed: false, // 鼠标滚动时，层是否固定在可视区域
                maxmin: false, // 放大缩小按钮
                content: path + "/sys/role/toAdd.do",
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
        /**监听启用操作*/
        $(".data-open-btn").on("click", function () {
            var checkStatus = table.checkStatus('testReload'), data = checkStatus.data;
            if (data == "" || data == null) {
                layer.msg("请选中一行数据再操作！");
                return;
            };
            var ids;
            for(var i = 0;i < data.length;i++){
                if(data[i].status == 0){
                    layer.msg("请勿选择已启用的数据！");
                    return;
                }
                if(ids==null){
                    ids = data[i].roleId;
                }else{
                    ids = ids +","+ data[i].roleId;
                }
            }
            oK(ids);
        });
        /**监听停用操作*/
        $(".data-delete-btn").on("click", function () {
            var checkStatus = table.checkStatus('testReload'), data = checkStatus.data;
            if (data == "" || data == null) {
                layer.msg("请选中一行数据再操作！");
                return;
            };
            var ids;
            for(var i = 0;i < data.length;i++){
                if(data[i].status == -1){
                    layer.msg("请勿选择已停用的数据！");
                    return;
                }
                if(ids==null){
                    ids = data[i].roleId;
                }else{
                    ids = ids +","+ data[i].roleId;
                }
            }
            del(ids);
        });
        /**监听表格复选框选择*/
        table.on('checkbox(currentTableFilter)', function (obj) {
            console.log(obj)
        });
        table.on('tool(currentTableFilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'edit') {
                layer.open({
                    title: "修改角色",
                    type: 2,
                    area: ["100%", "100%"],
                    fixed: false, // 鼠标滚动时，层是否固定在可视区域
                    maxmin: false, // 放大缩小按钮
                    content: path + "/sys/role/toEdit.do?roleId=" + data.roleId,
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
            } else if (obj.event === 'authorize') {
                layer.open({
                    title: "授权",
                    type: 2,
                    area: ["300px", "400px"],
                    fixed: false, // 鼠标滚动时，层是否固定在可视区域
                    maxmin: false, // 放大缩小按钮
                    content: path + "/sys/role/toAuthorize.do?roleId=" + data.roleId,
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
    /**启用*/
    function oK(ids){
        layer.confirm("确定操作？", {
                success: function(layero, index){
                    //键盘按键监听
                    $(document).on("keydown", function(e){
                        if(e.keyCode == 13){
                            layer.close(index);
                            oKExecute(ids);
                        }
                        if(e.keyCode == 27){
                            layer.close(index);
                        }
                    })
                }
            },
            function(index, layero){ //点击确定时执行的方法
                oKExecute(ids);
            },
            function(index, layero){ //点击取消时执行的方法
            }
        );
    }
    /**启用执行*/
    function oKExecute(ids){
        var index = layer.load(1, {shade: [0.2, "black"]}, {time: 5 * 1000});
        $.ajax({
            type: "post",
            url: path + "/sys/role/ok.do",
            data: {"ids":ids},
            dataType: "json",
            async: true,
            cache: false,
            success: function(result){
                layer.close(index);
                if(result.msg != undefined){
                    layer.msg("操作失败！"+result.msg);
                    return;
                }else{
                    layer.msg("操作成功！");
                    reloadTable();
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) { //ajax执行异常
                layer.close(index);
                if(XMLHttpRequest.status != 0){
                    layer.msg("操作失败！错误码（"+XMLHttpRequest.status+"）");
                    return;
                }
            }
        });
    }
    /**停用*/
    function del(ids){
        layer.confirm("确定操作？", {
                success: function(layero, index){
                    /**键盘按键监听*/
                    $(document).on("keydown", function(e){
                        if(e.keyCode == 13){
                            layer.close(index);
                            delExecute(ids);
                        }
                        if(e.keyCode == 27){
                            layer.close(index);
                        }
                    })
                }
            },
            function(index, layero){ //点击确定时执行的方法
                delExecute(ids);
            },
            function(index, layero){ //点击取消时执行的方法
            }
        );
    }
    /**停用执行*/
    function delExecute(ids){
        var index = layer.load(1, {shade: [0.2, "black"]}, {time: 5 * 1000});
        $.ajax({
            type: "post",
            url: path + "/sys/role/del.do",
            data: {"ids":ids},
            dataType: "json",
            async: true,
            cache: false,
            success: function(result){
                layer.close(index);
                if(result.msg != undefined){
                    layer.msg("操作失败！"+result.msg);
                    return;
                }else{
                    layer.msg("操作成功！");
                    reloadTable();
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) { //ajax执行异常
                layer.close(index);
                if(XMLHttpRequest.status != 0){
                    layer.msg("操作失败！错误码（"+XMLHttpRequest.status+"）");
                    return;
                }
            }
        });
    }
    /**封装reload方法*/
    function reloadTable(){
        layui.use('table', function(){
            var table = layui.table;
            var roleName = $.trim($('#roleName').val());
            var status = $.trim($('#status').val());
            //执行重载
            table.reload('testReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    status: status,
                    roleName: roleName
                }
            });
        });
    }
</script>
</body>
</html>