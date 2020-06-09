<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>

<%
	String familyCode = request.getParameter("familyCode");
 %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">

<title>参合缴费统计详情</title>
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
function deletePayRecord(familyCode) {
	if(confirm("您确定要删除吗？")){
		$.ajax({
			url:"<%=path%>/system/PayRecordServlet?m=del",
			data:{familyCode:familyCode},
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
			<div class="panel-head">
				<strong class="icon-reorder">&nbsp;&nbsp;家庭成员参合缴费详情</strong>
			</div>
			<table class="table table-hover text-center">
				<tr>	
					<th>序号</th>
					<th>个人编号</th>
					<th>参合证号</th>
					<th>姓名</th>
					<th>缴费金额</th>
					<th>缴费年度</th>
					<th>缴费时间</th>
					<th>操作员</th>
				</tr>
				<%
					String pageNo = request.getParameter("pageNo");
					int p = pageNo == null ? 1 : Integer.parseInt(pageNo);
					PayRecordDao dao = new PayRecordDao();
					String sql = "select * from t_pay_record where familyCode=? order by familyCode ";
					Object[] params = {familyCode};
					Page pageBean = dao.queryOjectsByPage(sql, params, p, Constant.ROW);
					
				%>
				<%
					for (int i = 0; i < pageBean.getDatas().size(); i++) {
						PayRecord  model = (PayRecord) pageBean.getDatas().get(i);
				%>
				<tr>
					<td><%=(i+1)%></td>
					<td><%=model.getHouseNum()%></td>
					<td><%=model.getJoinNum()%></td>
					<td><%=model.getName()%></td>
					<td><%=model.getPayAccount()%></td>
					<td><%=model.getPayAnnual()%></td>
					<td><%=model.getPayTime()%></td>
					<td><%=model.getOperator()%></td>
				</tr>
				<%
					}
				%>
			</table>
</body>
</html>
