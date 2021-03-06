<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>

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

// ajax请求删除家庭缴费记录
function deletePayRecord(familyCode) {
	if(confirm("一旦删除无法找回,您确定要删除吗？")){
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

// 打开缴费详情页面
function openDetails(familyCode) {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '家庭成员参合缴费详情',
		content: '<%=path%>/system/PayRecordServlet?m=details&familyCode='+familyCode,
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
				<strong class="icon-reorder">&nbsp;&nbsp;家庭参合缴费统计详情</strong>
			</div>
			<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
					<li>搜索：</li>
					<li><input type="text" placeholder="请输入缴费年份" name="houseHolder" class="input" style="width:150px; line-height:17px;display:inline-block" /> 
					<li><input type="text" placeholder="请输入行政区域" name="houseHolder" class="input" style="width:150px; line-height:17px;display:inline-block" /> 
					<li><input type="text" placeholder="请输入户主姓名" name="houseHolder" class="input" style="width:150px; line-height:17px;display:inline-block" /> 
					<li><input type="text" placeholder="请输入缴费起始时间" name="houseHolder" class="input" style="width:150px; line-height:17px;display:inline-block" /> 
					<li><input type="text" placeholder="请输入缴费结束时间" name="houseHolder" class="input" style="width:150px; line-height:17px;display:inline-block" /> 
					<a href="javascript:void(0)" class="button border-main icon-search" onclick="changesearch()">搜索</a></li>
					
					<li><a href="<%=path%>/system/PayRecordServlet?m=Excel" class="button border-main" >
					<span class="icon-search"></span>导出Excel</a></li>
				
				</ul>
			</div>
			<table class="table table-hover text-center">
				<tr>	
					<th>序号</th>
					<th>家庭编号</th>
					<th>户主</th>
					<th>家庭人口数</th>
					<th>已缴费人数</th>
					<th>缴费总金额</th>
					<th>参合发票号</th>
					<th>缴费年度</th>
					<th>缴费时间</th>
					<th>操作员</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${payRecordList}" var="model" varStatus="vs">
				<tr>
					<td align="center"><input type="checkbox" name="ids" value="${model.familyCode}" />${vs.index+1}</td>
					<td>${model.familyCode}</td>
					<td>${model.houseHolder}</td>
					<td>${personCount}</td>
					<td>${count}</td>
					<td>${amount}</td>
					<td>${model.invoiceNum}</td>
					<td>${model.payAnnual}</td>
					<td>${model.payTime}</td>
					<td>${model.operator}</td>
					<td>
						<div class="button-group">
							<a class="button border-main icon-list" href="javascript:void(0)" onclick="openDetails('${model.familyCode}')" >
								缴费详情
							</a> 
							<a class="button border-red" href="javascript:void(0)" onclick="deletePayRecord('${model.familyCode}')">
								<span class="icon-trash-o"></span>删除
							</a>
						</div>
					</td>
				</tr>
				</c:forEach>
			</table>
			
			<div class="pagelist">
				<c:if test="${pageBean.hasPre() == true}">
					<a href="<%=path%>/system/AreaServlet?m=list&pageNo=${pageBean.getFirstPage()}">首页</a>
					<a href="<%=path%>/system/AreaServlet?m=list&pageNo=${pageBean.prePage()}">上一页</a>
				</c:if>
				<c:if test="${pageBean.hasPre() != true}">
					<a href="javascript:void(0)">首页</a>
					<a href="javascript:void(0)">上一页</a>
				</c:if>
				
				<c:forEach items="${pageBean.linkNumbers()}" var="num">
					<c:if test="${pageBean.isCurrent(num) == true}">
						<span class="current">${num}</span>
					</c:if>
					<c:if test="${pageBean.isCurrent(num) != true}">
						<a href="<%=path%>/system/AreaServlet?m=list&pageNo=${num}">${num}</a>
					</c:if>
				</c:forEach>
				
				<c:if test="${pageBean.hasNext() == true}">
					<a href="<%=path%>/system/AreaServlet?m=list&pageNo=${pageBean.lastPage()}">下一页</a>
					<a href="<%=path%>/system/AreaServlet?m=list&pageNo=${pageBean.getPages()}">尾页</a>
				</c:if>
				<c:if test="${pageBean.hasNext() != true}">
					<a href="javascript:void(0)">下一页</a>
					<a href="javascript:void(0)">尾页</a>
				</c:if>
			</div>
		</div>
	</form>

</body>
</html>
