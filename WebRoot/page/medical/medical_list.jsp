<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">

<title>医疗机构列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet" href="css/admin.css">
<script src="<%=path%>/third/jquery/jquery-1.9.1.js"></script>
<script src="<%=path%>/third/layer-v3.1.1/layer/layer.js"></script>
<script src="js/jquery-1.4.4.min.js"></script>
<script src="js/admin.js"></script>
<script type="text/javascript">

// ajax请求删除医疗机构
function del(jgbm) {
	if(confirm("您确定要删除吗？")){
		$.ajax({
			url:"<%=path%>/system/MedicalServlet?m=del",
			data:{jgbm:jgbm},
			success:function(data){
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

// 打开添加医疗机构页面
function openAdd() {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '添加医疗机构信息填写',
		content: '<%=path%>/system/MedicalServlet?m=input',
		end: function() {
			window.location.reload();
		}
	});
}

// 打开修改慢性病分类页面
function openEdit(jgbm) {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '修改医疗机构信息填写',
		content: '<%=path%>/system/MedicalServlet?m=get&jgbm='+jgbm,
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
				<strong class="icon-reorder">&nbsp;&nbsp;医疗机构信息列表</strong>
			</div>
			<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
					<li><a class="button border-main icon-plus-square-o" href="javascript:void(0)" onclick="openAdd()">&nbsp;&nbsp;添加医疗机构</a></li>
					<li>搜索：</li>
					<li>
						<input type="text" placeholder="请输入搜索关键字" name="keywords" class="input" style="width:250px; line-height:17px;display:inline-block" /> 
						<a href="javascript:void(0)" class="button border-main icon-search" onclick="changesearch()"> 搜索</a>
					</li>
				</ul>
			</div>
			<table class="table table-hover text-center">
				<tr>
					<th width="50">序号</th>
					<th>机构编码</th>
					<th>组织机构编号</th>
					<th>地区编码</th>
					<th>行政区域编码</th>
					<th>隶属关系</th>
					<th>机构级别</th>
					<th>申报定点类型</th>
					<th>批准定点类型</th>
					<th>所属经济类型</th>
					<th>卫生机构大类</th>
					<th>卫生机构小类</th>
					<th>主办（管）单位</th>
					<th>开业/成立时间</th>
					<th>法定代表人</th>
					<th>注册资金</th>
					<th>操作</th>
				</tr>
				<%
					String pageNo = request.getParameter("pageNo");
					int p = pageNo == null ? 1 : Integer.parseInt(pageNo);
					MedicalDao dao = new MedicalDao();
					String sql = "select * from t_medical where 1=1 ";
					Object[] params = {};
					Page pageBean = dao.queryOjectsByPage(sql, params, p, Constant.ROW);
				%>
				<%
					for (int i = 0; i < pageBean.getDatas().size(); i++) {
						Medical model = (Medical) pageBean.getDatas().get(i);
				%>
				<tr>

					<td align="center"><input type="checkbox" name="ids" value="<%=model.getJgbm()%>" /><%=(i+1)%></td>
					<td><%=model.getJgbm()%></td>
					<td><%=model.getZzjgbm()%></td>
					<td><%=model.getDqbm()%></td>
					<td><%=model.getAreacode()%></td>
					<td><%=model.getItemName(model.getLsgx(),"02")%></td>
					<td><%=model.getItemName(model.getJgjb(),"06")%></td>
					<td><%=model.getItemName(model.getSbddlx(),"04")%></td>
					<td><%=model.getItemName(model.getPzddlx(),"04")%></td>
					<td><%=model.getItemName(model.getSsjjlx(),"01")%></td>
					<td><%=model.getItemName(model.getWsjgdl(),"03")%></td>
					<td><%=model.getItemName(model.getWsjgxl(),"0301")%></td>
					<td><%=model.getZgdw()%></td>
					<td><%=model.getKysj()%></td>
					<td><%=model.getFrdb()%></td>
					<td><%=model.getZczj()%></td>
					<td>
						<div class="button-group">
							<a class="button border-main" href="javascript:void(0)" onclick="openEdit('<%=model.getJgbm()%>')">
							<span class="icon-edit"></span>修改</a> 
							<a class="button border-red" href="javascript:void(0)" onclick="del('<%=model.getJgbm()%>')">
							<span class="icon-trash-o"></span>删除</a>
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
				<a href="<%=path%>/system/DiseaseServlet?m=list&pageNo=<%=pageBean.getFirstPage()%>">首页</a>
				<a href="<%=path%>/system/MedicalServlet?m=list&pageNo=<%=pageBean.prePage()%>">上一页</a>
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
				<a href="<%=path%>/system/MedicalServlet?m=list&pageNo=<%=num%>"><%=num%></a>
				<%
					}
					}
				%>
				<%
					if (pageBean.hasNext()) {
				%>
				<a
					href="<%=path%>/system/MedicalServlet?m=list&pageNo=<%=pageBean.lastPage()%>">下一页</a>
				<%
					} else {
				%>
				<a href="javascript:void(0)">下一页</a>
				<%
					}
				%>
				<a
					href="<%=path%>/system/MedicalServlet?m=list&pageNo=<%=pageBean.getPages()%>">尾页</a>
			</div>
		</div>
	</form>

</body>
</html>
