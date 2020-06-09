<%@page import="com.gxuwz.medical.domain.menu.Menu"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">
<title>用户列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet" href="css/admin.css">
<script src="<%=path%>/third/jquery/jquery-1.9.1.js"></script>
<script src="<%=path%>/third/layer-v3.1.1/layer/layer.js"></script>
<script src="js/jquery-1.4.4.min.js"></script>
<script src="js/admin.js"></script>

<script type="text/javascript">

// ajax请求删除用户
function deleteUser(userid){
	if(confirm("您确定要删除吗？")) {
		$.ajax({
			url:"<%=path%>/system/UserServlet?m=del",
			data:{userid:userid},
			success:function(data) {
				if(eval(data)) {
					alert("删除成功！");
					window.location.reload();
				} else {
					alert("删除出现异常！");
					window.location.reload();
				}
			}
		});
	}
}

// 打开添加用户页面
function openAdd() {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,	// 不固定
		maxmin: true,
		title: '添加用户信息填写',
		content: '<%=path%>/system/UserServlet?m=input',
		end: function() {
			window.location.reload();
		}
	});
}

// 打开修改用户页面
function openEdit(userid) {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false, // 不固定
		maxmin: true,
		title: '修改用户信息填写',
		content: '<%=path%>/system/UserServlet?m=get&userid='+userid,
		end: function() {
			window.location.reload();
		}
	});
}


</script>
</head>
<body>
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder">&nbsp;&nbsp;用户信息列表</strong>
			</div>
			<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
					<li><a class="button border-main icon-plus-square-o"
						href="javascript:void(0)" onclick="openAdd()">&nbsp;&nbsp;添加用户</a></li>
					<li>搜索：</li>
					<li>
						<form id="form1" action="system/UserServlet?m=search" method="post">
							<input type="text" placeholder="请输入工号" name="userid"  value="${userid}" class="input"style="width:250px; line-height:17px;display:inline-block" /> 
							<input type="text" placeholder="请输入姓名" name="fullname" value="${fullName}" class="input"style="width:250px; line-height:17px;display:inline-block" /> 
							<a href="javascript:void(0)" class="button border-main icon-search" onclick="document.getElementById('form1').submit()"> 搜索</a>
						</form>
					</li>
				</ul>
			</div>
			<table class="table table-hover text-center">
				<tr>
					<th width="100">序号</th>
					<th>用户工号</th>
					<th>姓名</th>
					<th>所在部门</th>
				    <th>状态</th>
					<th>操作</th>
				</tr>
				<%
					String pageNo = request.getParameter("pageNo");
					int p = pageNo == null ? 1 : Integer.parseInt(pageNo);
					UserDao userDao = new UserDao();
					String sql = "select * from t_user where 1=1 ";
					Object[] params = {};
					Page pageBean = userDao.getUserByPage(sql, params, p, Constant.ROW);
				%>
				<%
					for (int i = 0; i < pageBean.getDatas().size(); i++) {
						User  model= (User) pageBean.getDatas().get(i);
				%>
				<tr>
					<td><input type="checkbox" name="ids" value="<%=model.getUserid()%>" /><%=(i+1) %></td>
					<td align="center"><%=model.getUserid()%></td>
					<td><%=model.getFullname() %></td>
					<td>-</td>
					<td><%="1".equals(model.getStatus())?"正常":"禁用" %></td>
					<td><div class="button-group">
							<a class="button border-main" href="javascript:void(0)" onclick="openEdit('<%=model.getUserid()%>')">
							<span class="icon-edit"></span>修改</a> 
							<a class="button border-red" href="javascript:void(0)" onclick="deleteUser('<%=model.getUserid()%>')">
							<span class="icon-trash-o"></span>删除</a>
						</div></td>
				</tr>
				<%
					}
				%>
			</table>
			<div class="pagelist">
				<%
					if (pageBean.hasPre()) {
				%>
				<a href="<%=path%>/system/UserServlet?m=list&pageNo=<%=pageBean.getFirstPage()%>">首页</a>
				<a href="<%=path%>/system/UserServlet?m=list&pageNo=<%=pageBean.prePage()%>">上一页</a>
				<%
					} else {
				%>
				<a href="javascript:void(0)">首页</a>
				<a href="javascript:void(0)">上一页</a>
				<%
					}
				%>
				<%
					List<Integer> linkNums = pageBean.linkNumbers();
					for (int num : linkNums) {
					if (pageBean.isCurrent(num)) {
				%>
				<span class="current"><%=num%></span>
				<%
					} else {
				%>
				<a href="<%=path%>/system/UserServlet?m=list&pageNo=<%=num%>"><%=num%></a>
				<%
					}
					}
				%>
				<%
					if (pageBean.hasNext()) {
				%>
				<a href="<%=path%>/system/UserServlet?m=list&pageNo=<%=pageBean.lastPage()%>">下一页</a>
				<a href="<%=path%>/system/UserServlet?m=list&pageNo=<%=pageBean.getPages()%>">尾页</a>
				<%
					} else {
				%>
				<a href="javascript:void(0)">下一页</a>
				<a href="javascript:void(0)">尾页</a>
				<%
					}
				%>
			</div>
		</div>

</body>
</html>
