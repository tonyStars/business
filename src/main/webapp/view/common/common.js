/**
 * 公共js方法
 *
 * @author Tom
 * @date 2019-11-21
 * @type {Storage}
 */
var storage = window.sessionStorage;

/**
 * ztree异步加载请求成功后的数据过滤函数
 * @param treeId
 * @param parentNode
 * @param childNodes
 * @returns {null|*}
 */
function ztreeFilter(treeId, parentNode, childNodes) {
    if (!childNodes){
        return null;
    }
    for (var i=0, l=childNodes.length; i<l; i++) {
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
    }
    return childNodes.treeNodes;
}

/**
 * 将2个json对象进行合并
 * @param jsonbject1 第一个json对象
 * @param jsonbject2 第二个json对象
 */
function mergeJsonObject(jsonbject1, jsonbject2) {
    var resultJsonObject = {};
    for(var attr in jsonbject1){
        resultJsonObject[attr]=jsonbject1[attr];
    }
    for(var attr in jsonbject2){
        resultJsonObject[attr]=jsonbject2[attr];
    }
    return resultJsonObject;
}

/**
 * 判断数组中是否存在相同的参数
 * @param arr json数组
 * @returns {boolean} 存在相同数据返回true,不存在相同的数据则返回false
 */
function isRepeat(arr){
    var hash = {};
    for(var i in arr) {
        if(hash[arr[i]]){
            return true;
        }
        hash[arr[i]] = true;
    }
    return false;
}

/**
 * 根据已有数据字典数组渲染下拉框
 * @param mySelect 需要加载的字段选择
 * @param dataList 数组
 */
function renerSelectByData(mySelect,dataList) {
    mySelect.append("<option value=''>--请选择--</option>");
    for (var i=0;i<dataList.length;i++){
        var myOption = $("<option value='" + dataList[i].value + "'>" + dataList[i].itemName + "</option>");
        mySelect.append(myOption);
    }
    layui.use('form',function(){
        var form = layui.form;
        form.render();
    });
}

/**
 * 根据已有数据字典数组渲染下拉框并且选择默认选择值
 * @param mySelect 需要加载的字段选择
 * @param dataList 数组
 * @param value 默认需要选择的值
 */
function renerSelectByDataValue(mySelect,dataList,value) {
    mySelect.append("<option value=''>--请选择--</option>");
    for (var i=0;i<dataList.length;i++){
        if(dataList[i].value == value){
            var myOption = $("<option value='" + dataList[i].value + "' selected>" + dataList[i].itemName + "</option>");
        }else{
            var myOption = $("<option value='" + dataList[i].value + "'>" + dataList[i].itemName + "</option>");
        }
        mySelect.append(myOption);
    }
    layui.use('form',function(){
        var form = layui.form;
        form.render();
    });
}

/**
 * 加载数据字典下拉列表
 * @param mySelect 需要加载的字段选择
 * @param typeName 类型名称
 * @param ignoreValue 忽略的字段值
 * @returns {Array}
 */
function initDicSelect(mySelect,typeName,ignoreValue) {
    var idreplaceByTypeName = storage.getItem("idreplaceByTypeName"+typeName);
    var dataList = [];
    if(idreplaceByTypeName == "" || idreplaceByTypeName == null || idreplaceByTypeName == undefined){
        $.ajax({
            async:false,
            type: "GET",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: path+"/sys/dictionary/queryByName.do",
            data: {typeName: typeName},
            success: function (result) {
                if (result.msg != undefined) {
                    var errorIcon = 2;
                    var icon = result.icon;
                    if(icon != undefined){
                        errorIcon = icon;
                    }
                    layer.alert(result.msg,{icon:errorIcon});
                } else {
                    var data = result.data;
                    storage.setItem("idreplaceByTypeName"+typeName,JSON.stringify(data));
                    dataList = data;
                    mySelect.append("<option value=''>--请选择--</option>");
                    for (var i=0;i<dataList.length;i++){
                        if (dataList[i].value == ignoreValue) {
                            continue;
                        }
                        var myOption = $("<option value='" + dataList[i].value + "'>" + dataList[i].itemName + "</option>");
                        mySelect.append(myOption);
                    }
                    layui.use('form',function(){
                        var form = layui.form;
                        form.render();
                    });
                }
            }
        });
    }else{
        dataList = JSON.parse(idreplaceByTypeName);
        mySelect.append("<option value=''>--请选择--</option>");
        for (var i=0;i<dataList.length;i++){
        	if (dataList[i].value == ignoreValue) {
        		continue;
        	}
            var myOption = $("<option value='" + dataList[i].value + "'>" + dataList[i].itemName + "</option>");
            mySelect.append(myOption);
        }
        layui.use('form',function(){
            var form = layui.form;
            form.render();
        });
    }
    return dataList;
}

/**
 * 数据字典加载下拉框,选择默认值
 * @param mySelect 需要加载的字段选择
 * @param typeName 类型名称
 * @param choseValue 默认选择的值
 * @returns {Array}
 */
function dicSelectChose(mySelect,typeName,choseValue) {
    var idreplaceByTypeName = storage.getItem("idreplaceByTypeName"+typeName);
    var dataList = [];
    if(idreplaceByTypeName == "" || idreplaceByTypeName == null || idreplaceByTypeName == undefined){
        $.ajax({
            async:false,
            type: "GET",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: path+"/sys/dictionary/queryByName.do",
            data: {typeName: typeName},
            success: function (result) {
                if (result.msg != undefined) {
                    var errorIcon = 2;
                    var icon = result.icon;
                    if(icon != undefined){
                        errorIcon = icon;
                    }
                    layer.alert(result.msg,{icon:errorIcon});
                } else {
                    var data = result.data;
                    storage.setItem("idreplaceByTypeName"+typeName,JSON.stringify(data));
                    dataList = data;
                    mySelect.append("<option value=''>--请选择--</option>");
                    for (var i=0;i<dataList.length;i++){
                        if(dataList[i].value == choseValue){
                            var myOption = $("<option value='" + dataList[i].value + "' selected>" + dataList[i].itemName + "</option>");
                        }else{
                            var myOption = $("<option value='" + dataList[i].value + "'>" + dataList[i].itemName + "</option>");
                        }
                        mySelect.append(myOption);
                    }
                    layui.use('form',function(){
                        var form = layui.form;
                        form.render();
                    });
                }
            }
        });
    }else{
        dataList = JSON.parse(idreplaceByTypeName);
        mySelect.append("<option value=''>--请选择--</option>");
        for (var i=0;i<dataList.length;i++){
            if(dataList[i].value == choseValue){
                var myOption = $("<option value='" + dataList[i].value + "' selected>" + dataList[i].itemName + "</option>");
            }else{
                var myOption = $("<option value='" + dataList[i].value + "'>" + dataList[i].itemName + "</option>");
            }
            mySelect.append(myOption);
        }
        layui.use('form',function(){
            var form = layui.form;
            form.render();
        });
    }
    return dataList;
}

/**
 * 树形菜单查找省市区(单选省市区，区往上级联 市 省)
 */
function getCascadeRegion(){
    layer.open({
        title: "省市区菜单",
        type: 2,
        area: ["300px", "400px"],
        fixed: false, // 鼠标滚动时，层是否固定在可视区域
        maxmin: false, // 放大缩小按钮
        content: path + "/sys/region/toRegionSingle.do",
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
        end:function(){ // 层销毁后回调
        }
    });
}

/**
 * 计算到期日期
 * interval  number的单位
 * number：加的数量
 * date:起始日期
 */
function DateAdd(interval, num, dateStart) {
    var date = new Date(dateStart);
    var number = parseInt(num);
    switch (interval) {
        case "y": {
            date.setFullYear(date.getFullYear() + number);
            break;
        }
        case "q": {
            date.setMonth(date.getMonth() + number * 3);
            break;
        }
        case "m": {
            date.setMonth(date.getMonth() + number);
            break;
        }
        case "w": {
            date.setDate(date.getDate() + number * 7);
            break;
        }
        case "d": {
            date.setDate(date.getDate() + number);
            break;
        }
        case "h": {
            date.setHours(date.getHours() + number);
            break;
        }
        case "m": {
            date.setMinutes(date.getMinutes() + number);
            break;
        }
        case "s": {
            date.setSeconds(date.getSeconds() + number);
            break;
        }
        default: {
            date.setDate(date.getDate() + number);
            break;
        }
    }
    var dateType = $("#expirationDate").attr("data-date");
    var fmt = "yyyy-MM-dd hh:mm:ss";
    if(dateType == "true"){
        fmt = "yyyy-MM-dd";
    }
    return dateFtt(fmt,date);
}

function dateFtt(fmt,myDate)
{ //author: meizz
    var date = new Date(myDate);
    var o = {
        "M+" : date.getMonth()+1,                 //月份
        "d+" : date.getDate(),                    //日
        "h+" : date.getHours(),                   //小时
        "m+" : date.getMinutes(),                 //分
        "s+" : date.getSeconds(),                 //秒
        "q+" : Math.floor((date.getMonth()+3)/3), //季度
        "S"  : date.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}



function isObj(object) {
    return object && typeof(object) == 'object' && Object.prototype.toString.call(object).toLowerCase() == "[object object]";
}

function isArray(object) {
    return object && typeof(object) == 'object' && object.constructor == Array;
}

function getLength(object) {
    var count = 0;
    for(var i in object) count++;
    return count;
}

/**
 * 获取数据字典值对应的名字
 * @param items 数据对象集合
 * @param value 对应的值
 * @returns {string}
 */
function getItemName(items,value){
	var itemName = '';
	$.each(items,function(index,item){
		if (item.value === value) {
			itemName = item.itemName;
			return false;
		}
	})
	return itemName
}

//初始化select
function initSelect(ele,date,value){
	if (typeof ele === 'string') {
		ele = $('#'+ele);
	}
	var html = '<option value="">--请选择--</option>'
	$.each(date,function(index,item){
		if (item.value === value) {
			html += '<option value="'+ item.value +' selected">'+ item.itemName +'</option>';
		} else {
			html += '<option value="'+ item.value +'">'+ item.itemName +'</option>';
		}
	});
	ele.append(html);
	
}

/**
 * 通用打印方法
 * @param printType 单据类型，见Public.print对象。（必填）
 * @param companyId 公司Id
 * @param billIds 单据Id集合，用‘,’隔开（必填）
 */
function commonPrint(printType,companyId,billIds) {
    layui.define(['layer'],function(exports){
        var layer = layui.layer;
        if (printType == null || printType == "") {
            layer.msg('打印单据类型不能为空！');
            return;
        }
        if (billIds == null || billIds == "") {
            layer.msg('打印单据Id不能为空');
            return;
        }
        var printUrl = path + '/print/printBill.do?billIds=' + billIds + '&printType=' + printType + '&companyId=' + companyId;
        window.open(printUrl);
    });
}

/**
 * 通用打印参数printType配置
 * @type {{}}
 */
var Public = Public || {};
Public.print = {
    SYSCOMPANY: 1,
    SYSUSER: 2
};