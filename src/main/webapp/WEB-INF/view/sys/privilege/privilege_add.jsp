<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>新增权限</title>
    <%@include file="/WEB-INF/common/common.jsp" %>
    <style>
        .layui-iconpicker-body.layui-iconpicker-body-page .hide {display: none;}
    </style>
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <div class="layui-fluid">
            <div class="layui-card">
                <div class="layui-card-body" style="padding-top: 40px;">
                    <div class="layui-carousel" id="stepForm" lay-filter="stepForm" style="margin: 0 auto;">
                        <div carousel-item>
                            <div>
                                <form class="layui-form" style="margin: 0 auto;max-width: 460px;padding-top: 40px;">
                                    <div class="layui-form-item">
                                        <label class="layui-form-label">功能类型</label>
                                        <div class="layui-input-block">
                                            <select lay-verify="required" id="tp" name="tp" lay-filter="tp">
                                                <option value="0" selected>菜单</option>
                                                <option value="1">按钮</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="layui-form-item">
                                        <label class="layui-form-label">功能图标</label>
                                        <div class="layui-input-block">
                                            <input type="text" id="icon" name="icon" lay-filter="icon" value="fa fa-adjust" class="hide" lay-verify="required">
                                        </div>
                                    </div>
                                    <!--根据选择菜单或者按钮进行控制隐藏或显示-->
                                    <div class="layui-form-item" id="gradeSign">
                                        <label class="layui-form-label">菜单等级</label>
                                        <div class="layui-input-block">
                                            <select id="grade" name="grade">
                                                <option value="1" selected>一级菜单</option>
                                                <option value="2">二级菜单</option>
                                                <option value="3">三级菜单</option>
                                            </select>
                                        </div>
                                    </div>
                                    <!--根据选择菜单或者按钮进行控制隐藏或显示-->
                                    <div class="layui-form-item">
                                        <div class="layui-input-block">
                                            <button class="layui-btn" lay-submit lay-filter="formStep">
                                                &emsp;下一步&emsp;
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div>
                                <form class="layui-form" style="margin: 0 auto;max-width: 460px;padding-top: 40px;">
                                    <!--菜单区-->
                                    <div id="muenArea">
                                        <!--一级菜单和二级和三级菜单显示内容-->
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">菜单名称</label>
                                            <div class="layui-input-block">
                                                <input type="text" id="privilegeName" name="privilegeName" placeholder="请填写菜单名称" class="layui-input" />
                                            </div>
                                        </div>
                                        <!--一级菜单和二级和三级菜单显示内容-->
                                        <!--若二级菜单显示内容(该内容获取所有的一级菜单列表),若三级菜单显示内容(该内容获取所有的二级菜单列表)-->
                                        <div class="layui-form-item" id="parentIdSign">
                                            <label class="layui-form-label">父级菜单</label>
                                            <div class="layui-input-block">
                                                <input type="hidden" id="parentName" name="parentName" class="layui-input"/>
                                                <select id="parentId" name="parentId" lay-search=""></select>
                                            </div>
                                        </div>
                                        <!--若二级菜单显示内容(该内容获取所有的一级菜单列表),若三级菜单显示内容(该内容获取所有的二级菜单列表)-->
                                        <!--仅三级菜单显示内容-->
                                        <div class="layui-form-item" id="urlSign">
                                            <label class="layui-form-label">菜单URL</label>
                                            <div class="layui-input-block">
                                                <input type="text" id="url" name="url" placeholder="请填写菜单URL" class="layui-input"/>
                                            </div>
                                        </div>
                                        <!--仅三级菜单显示内容-->
                                    </div>
                                    <!--菜单区-->
                                    <!--按钮区-->
                                    <div id="buttonArea">
                                        <!--考虑用tree来显示-->
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">父级菜单</label>
                                            <div class="layui-input-block">
                                                <input type="hidden" id="parentIdB" name="parentIdB" class="layui-input"/>
                                                <input type="text" id="parentNameB" name="parentNameB" placeholder="请选择父级菜单" class="layui-input" onclick="showDialog();"/>
                                            </div>
                                        </div>
                                        <!--考虑用tree来显示-->
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">按钮名称</label>
                                            <div class="layui-input-block">
                                                <input type="text" id="privilegeNameB" name="privilegeNameB" placeholder="请填写按钮名称" class="layui-input"/>
                                            </div>
                                        </div>
                                        <div class="layui-form-item">
                                            <label class="layui-form-label">按钮URL</label>
                                            <div class="layui-input-block">
                                                <input type="text" id="urlB" name="urlB" placeholder="请填写按钮URL" class="layui-input" />
                                            </div>
                                        </div>
                                    </div>
                                    <!--按钮区-->
                                    <div class="layui-form-item">
                                        <div class="layui-input-block">
                                            <button type="button" class="layui-btn layui-btn-primary pre">上一步</button>
                                            <button class="layui-btn" lay-submit lay-filter="formStep2">确认信息</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div>
                                <div style="text-align: center;margin-top: 90px;" id="successIcon">
                                    <i class="layui-icon layui-circle" style="color: white;font-size:30px;font-weight:bold;background: #52C41A;padding: 20px;line-height: 80px;">&#xe605;</i>
                                    <div style="font-size: 24px;color: #333;font-weight: 500;margin-top: 30px;">新增成功</div>
                                </div>
                                <div style="text-align: center;margin-top: 90px;" id="failIcon">
                                    <i class="layui-icon layui-circle" style="color: white;font-size:30px;font-weight:bold;background: #C4263C;padding: 20px;line-height: 80px;">&#x1006;</i>
                                    <div style="font-size: 24px;color: #333;font-weight: 500;margin-top: 30px;">新增失败</div>
                                </div>
                                <div style="color: #666;margin-top: 30px;margin-bottom: 40px; align-items: center; justify-content: center; display: flex;">
                                    <txt id="txtInfo" style="color: #C4263C"></txt>
                                </div>
                                <div style="text-align: center;margin-top: 50px;">
                                    <button class="layui-btn next">再次添加</button>
                                    <button class="layui-btn layui-btn-primary" onclick="cancel();">关闭</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var FORM_FiRST = {};
    var FIRST_MENU_LIST;
    var SECOND_MENU_LIST;
    layui.use([ 'form', 'step','iconPickerFa'], function () {
        var $ = layui.$,form = layui.form,step = layui.step,iconPickerFa = layui.iconPickerFa;
        /**渲染图标选择器*/
        iconPickerFa.render({
            /**选择器，推荐使用input*/
            elem: '#icon',
            /**fa 图标接口(本地图标库786图标,完全够用)*/
            url: path + "/plugin/layuimini/lib/font-awesome-4.7.0/less/variables.less",
            /**是否开启搜索：true/false，默认true*/
            search: true,
            /**是否开启分页：true/false，默认true*/
            page: true,
            /**每页显示数量，默认12*/
            limit: 12,
            /**点击回调*/
            click: function (data) {
                $("#icon").val(data.icon);
            },
            /**渲染成功后的回调*/
            success: function (d) {
                // console.log(d);
            }
        });
        /**加载数据*/
        firstGrade();
        secondGrade();
        step.render({
            elem: '#stepForm',
            filter: 'stepForm',
            width: '100%', //设置容器宽度
            stepWidth: '750px',
            height: '500px',
            stepItems: [{
                title: '选择功能类型及等级'
            }, {
                title: '填写功能内容'
            }, {
                title: '完成'
            }]
        });
        /***
         * 点击下一步按钮时,触发事件
         */
        form.on('submit(formStep)', function (data) {
            /**将下一步之后的页面某些条件进行设置隐藏*/
            /**①如果选择功能类型为菜单则显示菜单部分,反正则选择按钮部分*/
            var tp = $("#tp").val();
            if(tp == 0){
                $("#muenArea").show();
                $("#buttonArea").hide();
            }else{
                $("#muenArea").hide();
                $("#buttonArea").show();
            }
            /**如果选择的是菜单*/
            if(tp == 0){
                /**判断选择的是几级菜单*/
                var grade = $("#grade").val();
                /**选择的一级菜单时*/
                if(grade == 1){
                    $("#parentIdSign").hide();
                    $("#urlSign").hide();
                    $("#parentName").val("");
                    $("#parentId").find("option").remove();
                }
                /**选择的二级菜单时*/
                if(grade == 2){
                    $("#parentIdSign").show();
                    $("#urlSign").hide();
                    /**初始化一级菜单下拉列表(父级菜单)*/
                    $("#parentName").val("");
                    $("#parentId").find("option").remove();
                    initForS($("#parentId"),FIRST_MENU_LIST);
                }
                /**选择的三级菜单时*/
                if(grade == 3){
                    $("#parentIdSign").show();
                    $("#urlSign").show();
                    /**初始化二级菜单下拉列表(父级菜单)*/
                    $("#parentName").val("");
                    $("#parentId").find("option").remove();
                    initForS($("#parentId"),SECOND_MENU_LIST);
                }
            }
            /**将下一步之后的页面某些条件进行设置隐藏*/
            /**获取点击下一步的第一个From表单的数据*/
            FORM_FiRST = data.field;
            step.next('#stepForm');
            return false;
        });
        /***
         * 点击确认信息按钮时,触发事件
         */
        form.on('submit(formStep2)', function (data) {
            /**此处添加提交方法,成功则继续,不成功则弹出提示*/
            var resultJson = mergeJsonObject(FORM_FiRST,data.field);
            commit(resultJson);
            /**此处添加提交方法,成功则继续,不成功则弹出提示*/
            step.next('#stepForm');
            return false;
        });
        /**监听功能类型事件,tp的value为0菜单时,显示菜单等级,为1按钮时,不显示菜单等级*/
        form.on('select(tp)', function(data){
            var tp = data.value;
            if(tp == 0){
                $("#gradeSign").show();
            }else{
                $("#gradeSign").hide();
            }
        })
        /**
         * 点击上一步按钮时,触发事件
        */
        $('.pre').click(function () {
            /**首先清空部分数据*/
            $("#privilegeName").val("");
            $("#parentName").val("");
            $("#parentId").find("option").remove();
            $("#url").val("");
            $("#parentIdB").val("");
            $("#parentNameB").val("");
            $("#privilegeNameB").val("");
            $("#urlB").val("");
            step.pre('#stepForm');
        });
        /**
         * 点击再次添加时,出发信息
         */
        $('.next').click(function () {
            /**首先清空部分数据*/
            $("#icon").val("");
            $("#privilegeName").val("");
            $("#parentName").val("");
            $("#parentId").find("option").remove();
            $("#url").val("");
            $("#parentIdB").val("");
            $("#parentNameB").val("");
            $("#privilegeNameB").val("");
            $("#urlB").val("");
            step.next('#stepForm');
        });
    })
    /**关闭*/
    function cancel() {
        parent.layer.close(window.parent.layer.getFrameIndex(window.name));
    }
    /**加载父级菜单树*/
    function showDialog(){
        layer.open({
            title: "父级菜单",
            type: 2,
            area: ["300px", "400px"],
            fixed: false, // 鼠标滚动时，层是否固定在可视区域
            maxmin: false, // 放大缩小按钮
            content: path + "/sys/privilege/dialog.do",
            success: function(layero, index){ // 层弹出成功后回调参数：当前层DOM&当前层索引
                //键盘按键监听
                $(document).on("keydown", function(e){
                    if (e.keyCode == 13){ // 禁用回车事件 以免层重复弹出
                        return false;
                    }
                    if (e.keyCode == 27){ //ESC退出
                        parent.layer.close(index);
                    }
                })
            },
            end:function(){ // 层销毁后回调
            }
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
            var $ = layui.$,form = layui.form;
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
    /**根据权限id获取权限名称*/
    function getCodeVlue(value,date){
        var code;
        for(var i=0;i < date.length;i++){
            if(date[i].privilegeId == value){
                code = date[i].privilegeName;
            }
        }
        return code;
    }
    /**
     * 确认信息,提交方法
     */
    function commit(data){
        /**如果是选择的二级菜单,则父级菜单应该是一级菜单,所以这里的父级菜单名称从一级菜单中获取*/
        if($("#grade").val() == 2){
            data.parentName = getCodeVlue($("#parentId").val(),FIRST_MENU_LIST);
        }
        /**如果是选择的三级菜单,则父级菜单应该是二级菜单,所以这里的父级菜单名称从二级菜单中获取*/
        if($("#grade").val() == 3){
            data.parentName = getCodeVlue($("#parentId").val(),SECOND_MENU_LIST);
        }
        var index = layer.load(1, {shade: [0.2, "black"]}, {time: 5 * 1000});
        $.ajax({
            type: "post",
            url: path + "/sys/privilege/add.do",
            data: data,
            dataType: "json",
            async: false,
            cache: false,
            success: function(result){
                layer.close(index);
                if(result.msg != undefined){
                    $("#failIcon").show();
                    $("#successIcon").hide();
                    $("#txtInfo").text(result.msg);
                    return;
                }else{
                    $("#successIcon").show();
                    $("#failIcon").hide();
                    $("#txtInfo").text("功能权限新增成功,请重新登陆后查看!");
                    return;
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                layer.close(index);
                if(XMLHttpRequest.status != 0){
                    $("#failIcon").show();
                    $("#successIcon").hide();
                    $("#txtInfo").text("保存失败！错误码（" + XMLHttpRequest.status + "）");
                    return;
                }
            }
        });
    }
</script>
</body>
</html>