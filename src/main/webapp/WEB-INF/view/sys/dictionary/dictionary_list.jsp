<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>系统数据字典</title>
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
                            <label class="layui-form-label">类型名称</label>
                            <div class="layui-input-inline">
                                <select id="typeName" name="typeName" lay-search=""></select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">状态</label>
                            <div class="layui-input-inline">
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
        </script>
    </div>
</div>
<script>
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;
        initTypeName($("#typeName"));
        var renderTable = function (){
            table.render({
                elem: '#currentTableId',
                url: path + "/sys/dictionary/search.do",
                cols: [[
                    {type: "checkbox", width: 50, fixed: "left"},
                    {field: 'typeName', minWidth: 150, title: '类型名称'},
                    {field: 'itemName', minWidth: 150, title: '子项目名称'},
                    {field: 'value', width: 200, title: '值'},
                    {field: 'orderId', width: 200, title: '排序'},
                    {field: 'note', width: 200, title: '备注'},
                    {
                        field: 'status', width: 80, align: 'center', templet: function (d) {
                            if (d.status == 0) {
                                return '启用';
                            } else {
                                return '停用';
                            }
                        }, title: '状态'
                    },
                    {title: '操作', width: 80, templet: '#currentTableBar', fixed: "right", align: "center"}
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
                    typeName: result.typeName,
                    itemName: result.itemName,
                    status: result.status
                }
            }, 'data');
            return false;
        });
        /**监听添加操作*/
        $(".data-add-btn").on("click", function () {
            layer.open({
                title: "新增数据字典",
                type: 2,
                area: ["100%", "100%"],
                fixed: false, // 鼠标滚动时，层是否固定在可视区域
                maxmin: false, // 放大缩小按钮
                scrollbar: false,//禁止出现滚动条
                content: path + "/sys/dictionary/toAdd.do",
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
                    ids = data[i].id;
                }else{
                    ids = ids +","+ data[i].id;
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
                    ids = data[i].id;
                }else{
                    ids = ids +","+ data[i].id;
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
                    title: "修改数据字典",
                    type: 2,
                    area: ["35%", "72%"],
                    fixed: false, // 鼠标滚动时，层是否固定在可视区域
                    maxmin: false, // 放大缩小按钮
                    content: path + "/sys/dictionary/toEdit.do?id=" + data.id,
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
            url: path + "/sys/dictionary/ok.do",
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
            url: path + "/sys/dictionary/del.do",
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
            var typeName = $.trim($('#typeName').val());
            var itemName = $.trim($('#itemName').val());
            var status = $.trim($('#status').val());
            //执行重载
            table.reload('testReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    typeName: typeName,
                    itemName: itemName,
                    status: status
                }
            });
        });
    }

    /**
     * 加载下拉类型
     * @param mySelect 加载字段
     */
    function initTypeName(mySelect){
        $.ajax({
            async:false,
            type: "GET",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: path+"/sys/dictionary/queryAllType.do",
            success: function (result) {
                if (result.msg != undefined) {
                    var errorIcon = 2;
                    var icon = result.icon;
                    if(icon != undefined){
                        errorIcon = icon;
                    }
                    layer.alert(result.msg,{icon:errorIcon});
                } else {
                    var dataList = result.data;
                    mySelect.append("<option value=''>--请选择--</option>");
                    for (var i=0;i<dataList.length;i++){
                        var myOption = $("<option value='" + dataList[i].typeName + "'>" + dataList[i].typeName + "</option>");
                        mySelect.append(myOption);
                    }
                    layui.use('form',function(){
                        var form = layui.form;
                        form.render();
                    });
                }
            }
        });
    }
</script>
</body>
</html>