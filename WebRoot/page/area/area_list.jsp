<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">

<title>行政区域列表</title>
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

// ajax请求删除行政区域
function deleteArea(areacode) {
	if(confirm("您确定要删除吗？")){
		$.ajax({
			url:"<%=path%>/system/AreaServlet?m=del",
			data:{areacode:areacode},
			success:function(data){
				if(data == "gfalse") {
					alert("当前行政区域为顶级行政区域，不能删除！")
				} else if(data == "afalse") {
					alert("当前行政区域包含有子行政区域，不能删除！")
				} else if(data == "true") {
					if(eval(data)) {
						alert("删除成功！");
						window.location.reload();
					}
				} else {
					alert("删除出现异常！");
					window.location.reload();
				}
			}
		});
	}
}

// 打开添加行政区域页面
function openAdd() {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '添加行政区域信息填写',
		content: '<%=path%>/system/AreaServlet?m=input',
		end: function() {
			window.location.reload();
		}
	});
}

// 打开修改行政区域页面
function openEdit(areacode) {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '修改行政区域信息填写',
		content: '<%=path%>/system/AreaServlet?m=get&areacode='+areacode,
		end: function() {
			window.location.reload();
		}
	});
}

</script>
</head>
<body>
	<form method="post" action="" id="listform">
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder">&nbsp;&nbsp;行政区域信息列表</strong>
			</div>
			<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
					<li><a class="button border-main icon-plus-square-o" href="javascript:void(0)" onclick="openAdd()">&nbsp;&nbsp;添加行政区域</a></li>
					<li>搜索：</li>
					<li><input type="text" placeholder="请输入搜索关键字" name="keywords" class="input" style="width:250px; line-height:17px;display:inline-block" /> 
					<a href="javascript:void(0)" class="button border-main icon-search" onclick="changesearch()">搜索</a></li>
					<div><span style="font-size: 18px;text-align: center; color: red">${deleteArea}</span></div>
				</ul>
			</div>
			<table class="table table-hover text-center">
				<tr>	
					<th width="100">序号</th>
					<th>行政区域编号</th>
					<th>行政区域名称</th>
					<th>行政区域级别</th>
					<th>操作</th>
				</tr>
				<%
					String pageNo = request.getParameter("pageNo");
					int p = pageNo == null ? 1 : Integer.parseInt(pageNo);
					AreaDao dao = new AreaDao();
					String sql = "select * from t_area where 1=1 order by areacode";
					Object[] params = {};
					Page pageBean = dao.queryOjectsByPage(sql, params, p, Constant.ROW);
				%>
				<%
					for (int i = 0; i < pageBean.getDatas().size(); i++) {
						Area  model= (Area) pageBean.getDatas().get(i);
				%>
				<tr>
					<td align="center"><input type="checkbox" name="ids" value="<%=model.getAreacode()%>" /><%=(i+1)%></td>
					<td align="center"><%=model.getAreacode()%></td>
					<td><%=model.getAreaname() %></td>
					<td><%=model.getGrade() %></td>
					<td>
						<div class="button-group">
							<a class="button border-main" href="javascript:void(0)" onclick="openEdit('<%=model.getAreacode()%>')" >
								<span class="icon-edit"></span>修改
							</a> 
							<a class="button border-red" href="javascript:void(0)" onclick="deleteArea('<%=model.getAreacode()%>')">
								<span class="icon-trash-o"></span>删除
							</a>
						</div>
					</td>
				</tr>
				<%
					}
				%>
			</table>
			
			<div class="pagelist">
				<%
					if (pageBean.hasPre()) {
				%>
				<a href="<%=path%>/system/AreaServlet?m=list&pageNo=<%=pageBean.getFirstPage()%>">首页</a>
				<a href="<%=path%>/system/AreaServlet?m=list&pageNo=<%=pageBean.prePage()%>">上一页</a>
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
				<a href="<%=path%>/system/AreaServlet?m=list&pageNo=<%=num%>"><%=num%></a>
				<%
					}
					}
				%>
				<%
					if (pageBean.hasNext()) {
				%>
				<a href="<%=path%>/system/AreaServlet?m=list&pageNo=<%=pageBean.lastPage()%>">下一页</a>
				<a href="<%=path%>/system/AreaServlet?m=list&pageNo=<%=pageBean.getPages()%>">尾页</a>
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
	</form>

</body>
</html>
