<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css" href="css/styles.css">
<link rel="stylesheet" type="text/css" href="css/admin.css">
<script src="<%=path%>/third/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="third/zTree_v3/js/jquery-1.4.4.min.js"></script>
<!-- 引入zTree插件 -->
<script src="<%=path%>/third/jquery/jquery-1.9.1.js"></script>
<script src="<%=path%>/third/layer-v3.1.1/layer/layer.js"></script>
<script type="text/javascript" src="js/admin.js"></script>
<script type="text/javascript">

// 异步条件查询
function search() {
	var name = $("#name").val();
	// 如果搜索的参数不为空则进行模糊查询
	if(name != "" && name != null) {
		$.ajax({ 
			url:"<%=path%>/system/DiseaseCardServlet?m=search",
			data:{name:name},
			success:function(data){
				if(data == "false") {
					alert("该用户不存在，请重新输入用户名查找！");
				}
				window.location.reload();
			}
		});
	}
}

function openAdd(nongheCard) {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '添加慢性病建档信息填写',
		content: '<%=path%>/system/DiseaseCardServlet?m=input&nongheCard='+nongheCard,
		end: function() {
			window.location.reload();
		}
	});
}

</script>

</head>
  <body>
  <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>&nbsp;&nbsp;添加慢性病建档</strong></div>
  <div class="body-content">
  	<div class="padding border-bottom">
		<ul class="search" style="padding-left:10px;">
			<li>搜索：</li>
			<li><input type="text" placeholder="请输入用户姓名" id="name" name="name" onblur="search()" value="${name}" class="input" style="width:250px; line-height:17px;display:inline-block" /> 
			<a href="javascript:void(0)" onclick="search()" class="button border-main icon-search" >搜索</a></li>
		</ul>
	</div> 
	<div class="padding border-bottom" style="margin-bottom: 100px;">
	<table class="table table-hover text-center">
		<tr>	
			<th width="50">序号</th>
			<th>农合证号</th>
			<th>身份证号</th>
			<th>姓名</th>
			<th>家庭地址</th>
		</tr>
		<tbody id="tbody">
					
		</tbody>
		<c:forEach items="${personList}" var="person" varStatus="vs">
			<tr onclick="openAdd('${person.nongheCard}, ${person.idCard}')">
				<td align="center">${vs.index+1}</td>
				<td>${person.nongheCard}</td>
				<td>${person.idCard}</td>
				<td>${person.name}</td>
				<td>${person.liveAddress}</td>
			</tr>
		</c:forEach>
	</table>
	
	<div class="pagelist">
			<%
				String pageNo = request.getParameter("pageNo");
				int p = pageNo == null ? 1 : Integer.parseInt(pageNo);
				DiseaseCardDao dao = new DiseaseCardDao();
				String sql = "select * from t_disease_card where 1=1";
				Object[] params = {};
				Page pageBean = dao.queryOjectsByPage(sql, params, p, Constant.ROW);
				
				if (pageBean.hasPre()) {
			%>
			<a href="<%=path%>/system/DiseaseCardServlet?m=list&pageNo=<%=pageBean.getFirstPage()%>">首页</a>
			<a href="<%=path%>/system/DiseaseCardServlet?m=list&pageNo=<%=pageBean.prePage()%>">上一页</a>
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
			<a href="<%=path%>/system/DiseaseCardServlet?m=list&pageNo=<%=num%>"><%=num%></a>
			<%
				}
				}
			%>
			<%
				if (pageBean.hasNext()) {
			%>
			<a href="<%=path%>/system/DiseaseCardServlet?m=list&pageNo=<%=pageBean.lastPage()%>">下一页</a>
			<a href="<%=path%>/system/DiseaseCardServlet?m=list&pageNo=<%=pageBean.getPages()%>">尾页</a>
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
