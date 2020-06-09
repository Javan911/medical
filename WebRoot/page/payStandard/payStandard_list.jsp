<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">

<title>参合缴费标准列表</title>
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

// ajax请求删除家庭档案
function deletePayStandard(annual) {
	if(confirm("您确定要删除吗？")){
		$.ajax({
			url:"<%=path%>/system/PayStandardServlet?m=del",
			data:{annual:annual},
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

</script>
</head>
<body>
	<form method="post" action="" id="listform">
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder">&nbsp;&nbsp;参合缴费标准历史记录列表</strong>
			</div>
			<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
					<li>搜索：</li>
					<li><input type="text" placeholder="请输入年度" name="houseHolder" class="input" style="width:250px; line-height:17px;display:inline-block" /> 
					<a href="javascript:void(0)" class="button border-main icon-search" onclick="changesearch()">搜索</a></li>
				</ul>
			</div>
			<table class="table table-hover text-center">
				<tr>	
					<th>序号</th>
					<th>年度</th>
					<th>金额</th>
					<th>开始时间</th>
					<th>结束时间</th>
					<th>操作员</th>
					<th>操作</th>
				</tr>
				<%
					String pageNo = request.getParameter("pageNo");
					int p = pageNo == null ? 1 : Integer.parseInt(pageNo);
					PayStandardDao dao = new PayStandardDao();
					String sql = "select * from t_pay_standard where 1=1 order by annual desc ";
					Object[] params = {};
					Page pageBean = dao.queryOjectsByPage(sql, params, p, Constant.ROW);
					
				%>
				<%
					for (int i = 0; i < pageBean.getDatas().size(); i++) {
						PayStandard  model= (PayStandard) pageBean.getDatas().get(i);
				%>
				<tr>
					<td align="center"><input type="checkbox" name="ids" value="<%=model.getAnnual()%>" /><%=(i+1)%></td>
					<td><%=model.getAnnual()%></td>
					<td><%=model.getAccount()%></td>
					<td><%=model.getStartTime()%></td>
					<td><%=model.getEndTime()%></td>
					<td><%=model.getOperator()%></td>
					<td>
						<div class="button-group">
							<a class="button border-red" href="javascript:void(0)" onclick="deletePayStandard('<%=model.getAnnual()%>')">
								<span class="icon-trash-o"></span>删除记录
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
