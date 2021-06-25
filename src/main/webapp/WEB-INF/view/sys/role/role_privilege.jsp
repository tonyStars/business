<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>角色授权</title>
	<%@include file="/WEB-INF/common/common.jsp"%>
	<style>
	.l-layout-center{
	    border:none!important;
	}
	.center{
	  text-align: center !important;
	}
	</style>
	<!-- 树组件ztree -->
	<link type="text/css" rel="stylesheet" href="${ctx}/static/plugin/jquery.ztree/css/uimakerStyle/uimakerStyle.css"></link>
	<script type="text/javascript" src="${ctx}/static/plugin/jquery.ztree/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="${ctx}/static/plugin/jquery.ztree/js/jquery.ztree.excheck.js"></script>
	<!-- 树组件ztree -->
</head>
<body>
	<div id="layout1">
        <div>
			<div style="overflow:auto;padding-top:6px">
				<div style="overflow:auto;height:250px">
					<ul id="zTree" class="ztree"></ul>
				</div>
				<div style="width: 100%;padding-top: 15px;padding-bottom: 15px;" class="center">
					<button class="layui-btn" type="button" class="layui-btn" onclick="commit();">确定</button>
					<button type="button" class="layui-btn layui-btn-primary" onclick="parent.layer.close(window.parent.layer.getFrameIndex(window.name));">取消</button>
				</div>
			</div>
		</div>
		<input type="hidden" id="roleId" name="roleId" value="${model.roleId}"/>
	</div>
	<script type="text/javascript">
		/**键盘按键监听*/
		document.onkeydown = function (e) {
			if (e.keyCode == 13 && e.target.tagName != 'TEXTAREA'){ //回车确定
				commit();
			}
			if (e.keyCode == 27){ //ESC退出
				var index = window.parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			}
		};
		$(function(){
			renderTree();
		});
		function renderTree(){
			var setting = {
				check: {
					enable: true,
					chkboxType:   { "Y" : "s", "N" : "s" }
				},
				data: {
					simpleData: {
						enable: true
					}
				}
			};
			$.ajax({
				type: "post",
				url: path + "/sys/privilege/getFuncTree.do",
				data: {"roleId": $("#roleId").val()},
				dataType: "json",
				async: false,
				cache: false,
				success: function(result){
					if(result.msg != undefined){
						layer.msg("加载失败！"+result.msg);
						return;
					}else{
						$.fn.zTree.init($("#zTree"), setting, result.treeNodes);
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) { //ajax执行异常
					if(XMLHttpRequest.status != 0){
						layer.msg("加载失败！错误码（"+XMLHttpRequest.status+"）");
						return;
					}
				}
			});
		}
		//选择结果
		function commit(){
			//获取zTree对象
			var zTree = $.fn.zTree.getZTreeObj("zTree");
			// 遍历所有节点，恢复禁用状态为活动状态
			var dsblNodes = zTree.getNodesByParam("chkDisabled", true);
			// 遍历节点取消禁用状态
			for (var i=0, l=dsblNodes.length; i < l; i++) {
				// 取消禁用状态
				zTree.setChkDisabled(dsblNodes[i], false);
			}
			//得到选中的数据集
			var checkedNodes = zTree.getCheckedNodes(true);
			// 遍历节点恢复禁用状态
			for (var i=0, l=dsblNodes.length; i < l; i++) {
				// 恢复禁用状态
				zTree.setChkDisabled(dsblNodes[i], true);
			}
			var ids = "";
			for(var i = 0; i < checkedNodes.length; i++){
				if(checkedNodes[i].id != 0){
					ids += "," + checkedNodes[i].id;
				}
			}
			if(ids != ""){
				ids = ids.substring(1);
			}
			$.ajax({
				type: "post",
				url: path + "/sys/role/addMenuAuth.do",
				data: {"menuIds":ids,"roleId":$("#roleId").val()},
				dataType: "json",
				async: true,
				cache: false,
				success: function(result){
					if(result.msg != undefined){
						layer.msg("分配功能权限失败！"+result.msg);
						return;
					}else{
						parent.layer.close(window.parent.layer.getFrameIndex(window.name));
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) { //ajax执行异常
					if(XMLHttpRequest.status != 0){
						layer.msg("分配功能权限失败！错误码（"+XMLHttpRequest.status+"）");
						return;
					}
				}
			});
		}
	</script>
</body>
</html>