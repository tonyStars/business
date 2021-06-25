<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>功能权限排序</title>
    <%@include file="/WEB-INF/common/common.jsp" %>
    <style>
        .laytable-cell-1-0-5 {
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
        <fieldset class="layui-elem-field layuimini-search">
            <legend>搜索信息</legend>
            <div style="margin: 10px 10px 10px 10px">
                <form class="layui-form layui-form-pane">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">菜单等级</label>
                            <div class="layui-input-block">
                                <select id="grade" name="grade" lay-filter="grade">
                                    <option value="1" selected>一级菜单</option>
                                    <option value="2">二级菜单</option>
                                    <option value="3">三级菜单</option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline" id="privilegeSign">
                            <label class="layui-form-label">父级菜单</label>
                            <div class="layui-input-block">
                                <select id="privilegeId" name="privilegeId" lay-filter="privilegeId"></select>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </fieldset>
        <div class="layui-btn-group">
            <button class="layui-btn data-save-btn">保存</button>
        </div>
        <table class="layui-hide" id="currentTableId" lay-filter="currentTableId"></table>
    </div>
</div>
<script>
    var FIRST_MENU_LIST;
    var SECOND_MENU_LIST;
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,form = layui.form,table = layui.table;
        $("#privilegeSign").hide();
        firstGrade();
        secondGrade();
        table.render({
            elem: '#currentTableId',
            url: path + "/sys/privilege/sortSearch.do",
            cols: [[
                {field: 'privilegeId', width: 80, title: 'ID'},
                {field: 'privilegeName', width: 200, title: '权限名'},
                {field: 'parentName', width: 200, title: '父权限名'},
                {field: 'sort', width: 150, title: '排序(点击可编辑)','edit':'text'},
                {field: 'url', width: 200, title: 'URL'},
                {
                    field: 'tp', width: 150, align: 'center', templet: function (d) {
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
                {field: 'grade', width: 120, title: '菜单级别'}
            ]],
            where: {
                "grade" : 1
            }
        });
        /**监听table序号编辑*/
        table.on('edit(currentTableId)', function(obj){
            var value = obj.value //得到修改后的值
                ,data = obj.data //得到所在行所有键值
                ,field = obj.field; //得到字段
            var r = /^\+?[1-9][0-9]*$/;
            if (!r.test(value)) {
                layer.msg("请输入一个正整数,否则无法保存!");
                return;
            }
        });
        /**监听菜单等级事件,选择一级菜单则privilegeId不加载,选择二级菜单,则privilegeId加载一级菜单,选择三级菜单,则privilegeId加载二级菜单*/
        form.on('select(grade)', function(data){
            var grade = data.value;
            if(grade == 1){
                /**选择一级菜单,不加载父级菜单*/
                $("#privilegeSign").hide();
                $("#privilegeId").find("option").remove();
                reloadTable();
            }
            if(grade == 2){
                /**选择二级菜单,加载父级菜单*/
                $("#privilegeId").find("option").remove();
                initForS($("#privilegeId"),FIRST_MENU_LIST);
                $("#privilegeSign").show();
                reloadTable();
            }
            if(grade == 3){
                /**选择三级菜单,加载父级菜单*/
                $("#privilegeId").find("option").remove();
                initForS($("#privilegeId"),SECOND_MENU_LIST);
                $("#privilegeSign").show();
                reloadTable();
            }
        })
        /**监听父级菜单选择事件*/
        form.on('select(privilegeId)', function(data){
            reloadTable();
        })
        /**监听保存操作*/
        $(".data-save-btn").on("click", function () {
            /**获取数据转换为json字符串*/
            var tableValue = layui.table.cache["currentTableId"];
            if(tableValue == null || tableValue.length == 0){
                layer.msg("暂无可以保存的数据!");
                return;
            }
            var arr = [];
            var data = [];
            var r = /^\+?[1-9][0-9]*$/;
            for(var i = 0; i < tableValue.length; i++){
                if (!r.test(tableValue[i].sort)) {
                    layer.msg("保存字段只能为正整数!");
                    return;
                }
                arr.push(tableValue[i].sort);
                var tt = {"privilegeId":tableValue[i].privilegeId,"sort":tableValue[i].sort};
                data.push(tt);
            }
            if(isRepeat(arr)){
                layer.msg("保存的数据存在相同的排序!");
                return;
            }
            commit(JSON.stringify(data));
        });
    });
    /**重新渲染表格*/
    function reloadTable() {
        layui.use(['table'], function () {
            var $ = layui.jquery,table = layui.table;
            /**执行搜索重载*/
            table.reload('currentTableId', {
                page: {
                    curr: 1
                }
                , where: {
                    grade: $("#grade").val(),
                    privilegeId: $("#privilegeId").val()

                }
            }, 'data');
        });
    }
    /**
     * 获取一级菜单集合
     */
    function firstGrade() {
        $.ajax({
            async:false,
            type: "GET",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: path + "/sys/privilege/firstGrade.do",
            success: function (result) {
                if (result.msg != undefined) {
                    layer.msg("一级菜单下拉列表加载失败!")
                    return;
                } else {
                    FIRST_MENU_LIST = result.data;
                    return;
                }
            }
        });
    }
    /**
     * 获取二级菜单集合
     */
    function secondGrade() {
        $.ajax({
            async:false,
            type: "GET",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: path + "/sys/privilege/secondGrade.do",
            success: function (result) {
                if (result.msg != undefined) {
                    layer.msg("二级菜单下拉列表加载失败!")
                    return;
                } else {
                    SECOND_MENU_LIST = result.data;
                    return;
                }
            }
        });
    }
    /**重置下拉列表*/
    function initForS(mySelect,data){
        layui.use([ 'form'], function () {
            var form = layui.form;
            if(data != null && data.length > 0){
                mySelect.append("<option value=''>---请选择---</option>");
                for (var i=0;i<data.length;i++){
                    var myOption = "<option value='" + data[i].privilegeId + "'>" + data[i].privilegeName + "</option>";
                    mySelect.append(myOption);
                }
            }
            form.render();
        })
    }
    /**保存按钮*/
    function commit(data){
        var index = layer.load(1, {shade: [0.2, "black"]}, {time: 5 * 1000});
        $.ajax({
            type: "post",
            url: path + "/sys/privilege/sort.do",
            data: {"data":data},
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
                    reloadTable();
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