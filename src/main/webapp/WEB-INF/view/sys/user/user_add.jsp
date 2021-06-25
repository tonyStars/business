<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>系统用户新增</title>
    <%@include file="/WEB-INF/common/common.jsp" %>
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <form class="layui-form" id="customForm">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">账号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="userCode" lay-verify="required" placeholder="请输入用户账号" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="userName" lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">性别</label>
                    <div class="layui-input-inline">
                        <select id="sex" name="sex" lay-verify="required">
                            <option value="" selected>--请选择--</option>
                            <option value="0">男</option>
                            <option value="1">女</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-inline">
                        <input type="password" name="password" lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">身份证</label>
                    <div class="layui-input-inline">
                        <input type="text" name="idCard" placeholder="请输入身份证号" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">类型</label>
                    <div class="layui-input-inline">
                        <select id="type" name="type" lay-verify="required"></select>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">手机号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="phone" placeholder="请输入手机号" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">职务</label>
                    <div class="layui-input-inline">
                        <select id="userDuty" name="userDuty" lay-verify="required" lay-search=""></select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">公司</label>
                    <div class="layui-input-inline">
                        <input type="hidden" id="companyId" name="companyId" class="layui-input"/>
                        <input type="text" id="companyName" name="companyName" lay-verify="required" placeholder="请选择公司" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <div class="layui-inline">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                        <textarea placeholder="请输入内容" class="layui-textarea" name="note"></textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    layui.use(['form', 'layer','tableSelect'], function () {
        var form = layui.form, layer = layui.layer,tableSelect = layui.tableSelect;
        initDicSelect($("#type"),"工作类型");
        initDicSelect($("#userDuty"),"员工职务");
        tableSelect.render({
            elem: '#companyName',
            checkedKey: 'companyId',
            table: {
                url: path + '/sys/company/searchSelect.do',
                cols: [[
                    { type: 'radio' },
                    { field: 'companyId', title: '公司ID' },
                    { field: 'companyName', title: '公司名称' }
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
                var companyName;
                var companyId;
                layui.each(data.data, function (index, item) {
                    companyName = item.companyName;
                    companyId = item.companyId;
                })
                elem.val(companyName);
                $("#companyId").val(companyId);
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
            url: path + "/sys/user/add.do",
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