<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<%
  String menuid=request.getParameter("menuid");
  Menu model=new Menu(menuid);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css" href="css/styles.css">
<link rel="stylesheet" type="text/css" href="css/admin.css">

<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//重新绑定表单提交
	$("#edit_btn").bind("click",function(){
	 $('form').submit();
	});
	$("#chooseid").change(function() {
		var purl=$(this).find("option:selected").data("url");
		$("#url").val(purl);
		});	
});

// 关闭layer弹出层
function closeLayer() {
	var index = parent.layer.getFrameIndex(window.name); // 获取父窗口索引
 	parent.layer.close(index) // 关闭layer
}
</script>

</head>

<body>
	<div class="panel admin-panel">
		<div class="panel-head" id="add">
			<strong><span class="icon-pencil-square-o"></span>&nbsp;&nbsp;更新权限菜单</strong>
		</div>
		<div class="body-content">
			<form id="form-add" method="post" class="form-x"
				action="<%=path%>/system/MenuServlet?m=edit">
				<input id="fid" name="fid" value="" type="hidden" />
				<div class="form-group">
					<div class="label">
						<label>所属菜单编号：</label>
					</div>
					<div class="field">
					<input type="text" readonly="readonly" class="input w50" value="<%=model.getMenuid() %>" name="menuid"
							data-validate="required:请输入菜编号" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>菜单名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=model.getMenuname() %>" name="menuname"
							data-validate="required:请输入菜单名称" />
						<div class="tips"></div>
					</div>
				</div>
					<div class="form-group">
					<div class="label">
						<label>访问URL：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=model.getUrl() %>" name="url" id="url"
							data-validate="required:访问URL" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label></label>
					</div>
					<div class="field">
						<button id="edit_btn" class="button bg-main icon-check-square-o" type="button" onclick="closeLayer()">确认</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>
