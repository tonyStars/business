<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>公司编辑</title>
    <%@include file="/WEB-INF/common/common.jsp" %>
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <form class="layui-form" id="customForm">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">公司编码</label>
                    <div class="layui-input-inline">
                        <input type="hidden" id="companyId" name="companyId" value="${model.companyId}" class="layui-input"/>
                        <input type="text" name="companyCode" value="${model.companyCode}" lay-verify="required" placeholder="请输入公司编码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">公司全称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="companyName" value="${model.companyName}" lay-verify="required" placeholder="请输入公司全称" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">父公司名称</label>
                    <div class="layui-input-inline">
                        <input type="hidden" id="parentId" name="parentId" value="${model.parentId}" class="layui-input"/>
                        <input type="text" id="parentName" name="parentName" value="${model.parentName}" placeholder="请选择父公司" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">所属大区</label>
                    <div class="layui-input-inline">
                        <select id="cityRegion" name="cityRegion">
                            <option value="" ${model.cityRegion==''?'selected':''}>--请选择--</option>
                            <option value="1" ${model.cityRegion==1?'selected':''}>华中</option>
                            <option value="2" ${model.cityRegion==2?'selected':''}>华南</option>
                            <option value="3" ${model.cityRegion==3?'selected':''}>华北</option>
                            <option value="4" ${model.cityRegion==4?'selected':''}>华东</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">类型</label>
                    <div class="layui-input-inline">
                        <select id="typeCode" name="typeCode" lay-verify="required"></select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">级别</label>
                    <div class="layui-input-inline">
                        <select id="companyLevel" name="companyLevel" lay-verify="required"></select>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">联系人</label>
                    <div class="layui-input-inline">
                        <input type="text" name="contactPerson" value="${model.contactPerson}" placeholder="请输入联系人" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">联系电话</label>
                    <div class="layui-input-inline">
                        <input type="text" name="contactPhone" value="${model.contactPhone}" placeholder="请输入联系电话" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">地址</label>
                    <div class="layui-input-inline">
                        <input type="text" name="addr" value="${model.addr}" placeholder="请输入地址" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">省市区<span style="color: red">&nbsp;*</span></label>
                    <div class="layui-input-inline" >
                        <input type="hidden" name="addressIds" id="addressIds" value="${model.addressIds}"/>
                        <input type="text" name="addressNames" id="addressNames" value="${model.addressNames}" lay-verify="required" placeholder="请选择省市区" autocomplete="off" class="layui-input" readonly>
                        <span class="iconfont mir_but" id="findRegion" onclick="getCascadeRegion();"><i class="layui-icon ">&#xe615;</i></span>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">公司简称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="nameShort" value="${model.nameShort}" placeholder="请输入公司简称" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                        <textarea placeholder="请输入内容" class="layui-textarea" name="note">${model.note}</textarea>
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
        renerSelectByDataValue($("#typeCode"),parent.TYPE_CODE,${model.typeCode});
        renerSelectByDataValue($("#companyLevel"),parent.COMPANY_LEVEL,${model.companyLevel});
        tableSelect.render({
            elem: '#parentName',
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
                var parentName;
                var parentId;
                layui.each(data.data, function (index, item) {
                    parentName = item.companyName;
                    parentId = item.companyId;
                })
                elem.val(parentName);
                $("#parentId").val(parentId);
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
            url: path + "/sys/company/edit.do",
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