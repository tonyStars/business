<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>用户授权</title>
	<%@include file="/WEB-INF/common/common.jsp"%>
</head>
<body>
	<div id="layout1">
		<div id="demo1" style="width: 250px; margin: 20px;"></div>
		<div style="width: 100%;padding-top: 210px;padding-bottom: 15px; text-align: center">
			<button class="layui-btn" type="button" class="layui-btn" onclick="commit();">确定</button>
			<button type="button" class="layui-btn layui-btn-primary" onclick="parent.layer.close(window.parent.layer.getFrameIndex(window.name));">取消</button>
		</div>
		<input type="hidden" id="userId" name="userId" value="${model.userId}"/>
	</div>
	<script type="text/javascript">
        var datas;
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
            initRole();
		});
		/**加载角色列表*/
		function initRole() {
            $.ajax({
                type: "post",
                url: path + "/sys/role/searchList.do",
                data: {"userId": $("#userId").val()},
                dataType: "json",
                async: false,
                cache: false,
                success: function(result){
                    if(result.msg != undefined){
                        parent.layer.msg("获取角色失败！"+result.msg);
                        return;
                    }else{
                        /**加载组件*/
                        layui.config({
                            base: '${ctx}/static/plugin/xm-select/'
                        }).extend({
                            xmSelect: 'xm-select'
                        }).use(['xmSelect'], function(){
                            var xmSelect = layui.xmSelect;
                            /**渲染多选*/
							datas = xmSelect.render({
                                el: '#demo1',
                                data: result.data,
								theme: {
                                	color: '#5c88b1'
								},
								paging: true,
								pageSize: 5,
								pageEmptyShow: false
                            })
                        })
                    }
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) { //ajax执行异常
                    if(XMLHttpRequest.status != 0){
                        parent.layer.msg("获取角色失败！错误码（"+XMLHttpRequest.status+"）");
                        return;
                    }
                }
            });
        }
		//选择结果
		function commit(){
			$.ajax({
				type: "post",
				url: path + "/sys/user/addRoleAuth.do",
				data: {"userId":$("#userId").val(),"datas":JSON.stringify(datas.getValue(name))},
				dataType: "json",
				async: true,
				cache: false,
				success: function(result){
					if(result.msg != undefined){
                        parent.layer.msg("分配角色失败！"+result.msg);
						return;
					}else{
                        parent.layer.msg("分配角色成功！");
						parent.layer.close(window.parent.layer.getFrameIndex(window.name));
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) { //ajax执行异常
					if(XMLHttpRequest.status != 0){
                        parent.layer.msg("分配角色失败！错误码（"+XMLHttpRequest.status+"）");
						return;
					}
				}
			});
		}
	</script>
</body>
</html>