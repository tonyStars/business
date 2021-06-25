<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>行政区域新增</title>
    <%@include file="/WEB-INF/common/common.jsp" %>
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <form class="layui-form" id="customForm">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">区域编码</label>
                    <div class="layui-input-inline">
                        <input type="text" name="code" lay-verify="required" placeholder="请输入区域编码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">区域名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="name" lay-verify="required" placeholder="请输入区域名称" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">父地区名称</label>
                    <div class="layui-input-inline">
                        <input type="hidden" id="parentId" name="parentId" class="layui-input"/>
                        <input type="text" id="parentName" name="parentName" placeholder="请选择父地区" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">所属大区</label>
                    <div class="layui-input-inline">
                        <select id="cityRegion" name="cityRegion">
                            <option value="" selected>--请选择--</option>
                            <option value="1">华中</option>
                            <option value="2">华南</option>
                            <option value="3">华北</option>
                            <option value="4">华东</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">类型</label>
                    <div class="layui-input-inline">
                        <select id="regionType" name="regionType" lay-verify="required">
                            <option value="" selected>--请选择--</option>
                            <option value="0">国家</option>
                            <option value="1">省</option>
                            <option value="2">市</option>
                            <option value="3">区</option>
                            <option value="4">直辖市</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">助记码</label>
                    <div class="layui-input-inline">
                        <input type="text" name="pinyin" placeholder="请输入助记码" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">经度</label>
                    <div class="layui-input-inline">
                        <input type="text" name="longitude" placeholder="请输入经度" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">纬度</label>
                    <div class="layui-input-inline">
                        <input type="text" name="latitude" placeholder="请输入纬度" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">可否操作</label>
                    <div class="layui-input-block">
                        <input type="hidden" id="isservice" name="isservice" value="1" class="layui-input"/>
                        <input type="checkbox" checked="" name="open" lay-skin="switch" lay-filter="switchTest" lay-text="ON|OFF">
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
                    <button class="layui-btn layui-btn-primary" onclick="parent.layer.close(window.parent.layer.getFrameIndex(window.name));">取消</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    layui.use(['form', 'layer','tableSelect'], function () {
        var form = layui.form, layer = layui.layer,tableSelect = layui.tableSelect;
        tableSelect.render({
            elem: '#parentName',
            checkedKey: 'regionId',
            table: {
                url: path + '/sys/region/searchSelect.do',
                cols: [[
                    { type: 'radio' },
                    { field: 'regionId', title: 'ID' },
                    { field: 'name', title: '区域名称' }
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
                    parentName = item.name;
                    parentId = item.regionId;
                })
                elem.val(parentName);
                $("#parentId").val(parentId);
            }
        })
        /**监听开关按钮*/
        form.on('switch(switchTest)', function (data) {
            if(this.checked){
                $("#isservice").val("1");
            }else{
                $("#isservice").val("0");
            }
        });
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
            url: path + "/sys/region/add.do",
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