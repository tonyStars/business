<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>父级菜单</title>
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
					<button class="layui-btn" type="button" class="layui-btn layui-btn-primary" onclick="commit();">确定</button>
					<button type="button" class="layui-btn layui-btn-primary" onclick="parent.layer.close(window.parent.layer.getFrameIndex(window.name));">取消</button>
				</div>
			</div>
		</div>
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
			async:{
				autoParam:["id"],
				enable: true,
				type: "post",
				url: path + "/sys/privilege/getMenuTree.do",
				dataFilter: ztreeFilter
			},
			view: {
				selectedMulti: false,
				dblClickExpand: false,
			},
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onDblClick: function(event, treeId, treeNode) {
					parent.$("#parentIdB").val(treeNode.id);
					parent.$("#parentNameB").val(treeNode.name);
					parent.layer.close(window.parent.layer.getFrameIndex(window.name));
				},
			}
		};
		$.ajax({
			type: "post",
			url: path + "/sys/privilege/getMenuTree.do",
			data: {},
			dataType: "json",
			async: false,
			cache: false,
			success: function(result){
				if(result.treeNodes == null){
					parent.layer.msg("数据加载失败！");
					return;
				}else{
					$.fn.zTree.init($("#zTree"), setting, result.treeNodes).expandAll(true);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				if(XMLHttpRequest.status != 0){
					parent.layer.msg("数据加载失败！错误码（"+XMLHttpRequest.status+"）");
					return;
				}
			}
		});
	}
	//选择结果
	function commit(){
		var treeObj = $.fn.zTree.getZTreeObj("zTree");
		var nodes = treeObj.getSelectedNodes();
		if(nodes == ""){
			parent.layer.msg("请选择节点", {time:800});
			return;
		}
		parent.$("#parentIdB").val(nodes[0].id);
		parent.$("#parentNameB").val(nodes[0].name);
		parent.layer.close(window.parent.layer.getFrameIndex(window.name));
	}

</script>
</body>
</html>