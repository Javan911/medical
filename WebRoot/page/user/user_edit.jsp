<%@ page language="java" import="java.util.*"  pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<%
	//加载角色列表
	RoleDao dao = new RoleDao();
	String sql="select * from t_role";
	Object[]params={};
	List<Role> roleList=dao.getRoleList(sql, params);
	//加载用户原有信息
	String userid=request.getParameter("userid");
	User model=new User(userid);
	
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
<script type="text/javascript" src="js/admin.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		//重新绑定表单提交
		$("#add_btn").bind("click", function() {
			$('form').submit();
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
			<strong><span class="icon-pencil-square-o"></span>&nbsp;&nbsp;更新用户信息</strong>
		</div>
		<div class="body-content">
			<form id="form-add" method="post" class="form-x" action="<%=path%>/system/UserServlet?m=edit">
				<div class="form-group">
					<div class="label">
						<label>工号：</label>
					</div>
					<div class="field">
						<input type="text" readonly="readonly" class="input w50" value="<%=model.getUserid() %>" name="userid" data-validate="required:请输入用户名" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>姓名：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=model.getFullname() %>" name="fullname" data-validate="required:请输入姓名" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>密码：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="<%=model.getPwd() %>" name="pwd" id="pwd" data-validate="required:输入密码" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>状态：</label>
					</div>
					<div class="field">
						<select id="status" class="input w50" name="status">
							<option value="1">正常</option>
							<option value="0">禁用</option>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>所属机构：</label>
					</div>
					<div class="field">
						<select id="agencode" class="input w50" name="agencode">
							<option value="0">选择农合机构</option>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>角色列表：</label>
					</div>
					<div class="field" style="padding-top:8px;">
						<%  int i=0;
						  for(Role r:roleList){ 
						    i++;
						  %>
						   <input id="role<%=i%>" type="checkbox" name="roleids" value="<%=r.getRoleid()%>"/>
						<%=r.getRoleName() %>
						<% } %>
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label></label>
					</div>
					<div class="field">
						<button id="add_btn" class="button bg-main icon-check-square-o" type="button" onclick="closeLayer()">确认</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>
