<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>系统用户管理</title>
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
                            <label class="layui-form-label">用户名</label>
                            <div class="layui-input-inline">
                                <input type="text" name="userName" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">性别</label>
                            <div class="layui-input-block">
                                <select id="sex" name="sex">
                                    <option value="" selected>请选择</option>
                                    <option value="0">男</option>
                                    <option value="1">女</option>
                                </select>
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
            <button class="layui-btn layui-icon data-export-btn">&#xe619;导出</button>
            <button class="layui-btn layui-icon data-import-btn" id="toImport">&#xe61a;导入</button>
            <button class="layui-btn layui-icon data-download-btn">&#xe601;下载模板</button>
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
                url: path + "/sys/user/search.do",
                cols: [[
                    {type: "checkbox", width: 50, fixed: "left"},
                    {field: 'userCode', width: 120, title: '账号'},
                    {field: 'userName', width: 150, title: '姓名'},
                    {field: 'idCard', minWidth: 200, title: '身份证'},
                    {field: 'phone', minWidth: 200, title: '手机号'},
                    {
                        field: 'sex', width: 80, align: 'center', templet: function (d) {
                            if (d.sex == 0) {
                                return '男';
                            } else {
                                return '女';
                            }
                        }, title: '性别'
                    },
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
                    userName: result.userName,
                    sex: result.sex,
                    status: result.status
                }
            }, 'data');
            return false;
        });
        /**监听添加操作*/
        $(".data-add-btn").on("click", function () {
            layer.open({
                title: "新增用户",
                type: 2,
                area: ["100%", "100%"],
                fixed: false, // 鼠标滚动时，层是否固定在可视区域
                maxmin: false, // 放大缩小按钮
                content: path + "/sys/user/toAdd.do",
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
                    ids = data[i].userId;
                }else{
                    ids = ids +","+ data[i].userId;
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
                    ids = data[i].userId;
                }else{
                    ids = ids +","+ data[i].userId;
                }
            }
            del(ids);
        });
        /**监听导出操作*/
        $(".data-export-btn").on("click", function () {
            toExport();
        });
        /**监听下载模板操作*/
        $(".data-download-btn").on("click", function () {
            toDownload();
        });
        /**监听表格复选框选择*/
        table.on('checkbox(currentTableFilter)', function (obj) {
            console.log(obj)
        });
        table.on('tool(currentTableFilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'edit') {
                layer.open({
                    title: "修改用户",
                    type: 2,
                    area: ["100%", "100%"],
                    fixed: false, // 鼠标滚动时，层是否固定在可视区域
                    maxmin: false, // 放大缩小按钮
                    content: path + "/sys/user/toEdit.do?userId=" + data.userId,
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
                    title: "授权角色",
                    type: 2,
                    area: ["23%", "62%"],
                    fixed: false, // 鼠标滚动时，层是否固定在可视区域
                    maxmin: false, // 放大缩小按钮
                    content: path + "/sys/user/toAuthorize.do?userId=" + data.userId,
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
    /**导入上传excel表*/
    layui.use('upload',function () {
        var upload = layui.upload;
        upload.render({
            elem: '#toImport'
            ,url: path+'/sys/user/toImport.do'
            ,accept:'file'
            ,method: 'post'
            ,before: function(obj){
                layerIndex= layer.load(1,{shade: [0.3, '#000']});
            }
            ,done: function(res, index, upload){
                layer.close(layerIndex);
                if(res.code == 0){
                    var excelData = res.data;
                    var excelJsonData = JSON.stringify(excelData);
                    layer.confirm("数据校验通过,点击确定将上传！", {
                            icon: 1, title: '提示',
                            cancel: function () {
                                // layer.msg("取消");
                            },
                            end:function () {
                                // layer.msg("完毕");
                            }
                        }, function (index) {
                            layerIndex= layer.load(1,{shade: [0.3, '#000']});
                            $.ajax({
                                type: "POST",//方法类型
                                dataType: "json",//预期服务器返回的数据类型
                                url: path+"/sys/user/saveExcelData.do",//url
                                data: {lists: excelJsonData},
                                success: function (result) {
                                    layer.close(layerIndex);
                                    if (result.code == 0) {
                                        //返回成功则加载数据
                                        layer.msg("上传成功！");
                                        reloadTable();
                                    } else {
                                        layer.msg(result.msg);
                                    }
                                    return;
                                },
                                error: function () {
                                    layer.close(layerIndex);
                                    layer.alert("保存异常！");
                                    return;
                                }
                            });
                        },
                        function () {
                            // layer.msg("否");
                        }
                    );
                }else{
                    layer.alert(res.msg);
                }
            }
            ,error: function(index, upload){
                layer.close(layerIndex);
                layer.alert('上传失败！');
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
            url: path + "/sys/user/ok.do",
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
            url: path + "/sys/user/del.do",
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
            var userName = $.trim($('#userName').val());
            var sex = $.trim($('#sex').val());
            var status = $.trim($('#status').val());
            //执行重载
            table.reload('testReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    userName: userName,
                    sex: sex,
                    status: status

                }
            });
        });
    }
    /**导出Excel*/
    function toExport(){
        var userName = $("#userName").val();
        var sex = $("#sex").val();
        var status = $("#status").val();
        var form = $("<form>");   //定义一个form表单
        form.attr('style', 'display:none');   //在form表单中添加查询参数
        form.attr('target', '');
        form.attr('method', 'post');
        form.attr('action', path+"/sys/user/toExport.do");
        var input = $('<input>');
        input.attr('type', 'hidden');
        input.attr('name', 'content');
        input.attr('value', JSON.stringify({"userName":userName,"sex":sex,"status":status}));
        $('body').append(form);  //将表单放置在web中
        form.append(input);   //将查询参数控件提交到表单上
        form.submit();
    }
    /**下载Excel模板*/
    function toDownload(){
        var form = $("<form>");   //定义一个form表单
        form.attr('style', 'display:none');   //在form表单中添加查询参数
        form.attr('target', '');
        form.attr('method', 'post');
        form.attr('action', path+"/sys/user/templetExport.do");
        var input1 = $('<input>');
        input1.attr('type', 'hidden');
        input1.attr('name', 'content');
        input1.attr('value', JSON.stringify({"":""}));
        $('body').append(form);  //将表单放置在web中
        form.append(input1);   //将查询参数控件提交到表单上
        form.submit();
    }
</script>
</body>
</html>