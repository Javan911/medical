<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>

<%
	// 接收请求转发过来的家庭编号
	String familyCode = request.getParameter("familyCode");
	// 根据家庭编号得到户主信息
	Family family = new Family(familyCode); 
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">
<title>未缴费的家庭成员列表</title>
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
$(document).ready(function() {
	//重新绑定表单提交
	$("#add_btn").bind("click", function() {
		// 获取span标签中的缴费金额，并添加至input标签进行提交
		var payAccountStr = document.getElementById("amountSpan").innerHTML;
		var payAccount = payAccountStr.substring(payAccountStr.indexOf("￥")+1);
		$("#payAccount").attr("value", payAccount);
		if($("#invoiceNumValue").val() == null || $("#invoiceNumValue").val() == "") {
			alert("参合发票号不能为空！");
			return false;
		}
		// 将form表单之外的invoiceNum的值赋给表单内的invoiceNum进行提交（为了样式美观）
		var invoiceNumValue = $("#invoiceNumValue").val();
		$("#invoiceNum").attr("value", invoiceNumValue);
		
		// 判断是否已选择要缴费的家庭成员
		var payNum = $(":checkbox[name='ids']:checked").length;
		if(payNum == 0) {
			alert("请至少选择一名家庭成员进行缴费！");
			return false;
		}
		$('form').submit();
		closeLayer();
	});
	// 复选框的全选和全不选
    $("#selectAll").click(function() {
        $(":checkbox[name='ids']").attr("checked", this.checked); // this指代的你当前选择的这个元素的JS对象
    });
    calAccount();     
});

// 计算缴费总金额
function calAccount() {
  	// 获取复选框选中的个数
    var payNum = $(":checkbox[name='ids']:checked").length;
    // 异步请求计算需缴费总金额
	$.ajax({
		type:"post",
		url:"<%=path%>/system/PayRecordServlet?m=calAccount",
		data:{payNum:payNum},
		success:function(data){
			var span = document.getElementById("amountSpan");
			span.innerHTML = "￥ " + data;
		}
	});
}

// 关闭layer弹出层
function closeLayer() {
	var index = parent.layer.getFrameIndex(window.name); // 获取父窗口索引
 	parent.layer.close(index) // 关闭layer
}
</script>

</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head">
			<strong class="icon-reorder">&nbsp;&nbsp;未缴费的家庭成员列表</strong>
			<div align="center">
				<strong>参合发票号：</strong>
				<input type="text" id="invoiceNumValue" name="invoiceNum" style="width: 200px; height: 30px;" placeholder="请输入参合发票号" data-validate="required:请输入参合发票号 " />
			</div>
		</div>
		<form id="form-add" method="post" class="form-x" action="<%=path%>/system/PayRecordServlet?m=add">  
			<!-- 提交表单数据 -->
			<input type="hidden" name="familyCode" value="<%=familyCode%>" />
			<input type="hidden" name="houseHolder" value="<%=family.getHouseHolder()%>" />
			<input type="hidden" id="payAccount" name="payAccount" />
			<input type="hidden" id="invoiceNum" name="invoiceNum" />
			
		<table class="table table-hover text-center">
			<tr>	
				<th><input type="checkbox" id="selectAll" onchange="calAccount()" />序号</th>
				<th>个人编号</th>
				<th>与户主关系</th>
				<th>身份证号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>年龄</th>
				<th>健康状况</th>
				<th>联系电话</th>
			</tr>
			<%
					// 当有家庭成员未缴费时，获取该家庭成员的信息
					List<Person> personList = new ArrayList<Person>();
					PersonDao dao = new PersonDao();
					// 获取当前年份
					Calendar date = Calendar.getInstance();        
					String payAnnual = String.valueOf(date.get(Calendar.YEAR)); 
					// 获取指定个人编号的家庭成员
					String sql = "select * from t_person_archives where familyCode=? and houseNum not in(select houseNum from t_pay_record where familyCode=? and payAnnual=?) order by houseNum ";
					Object[] params = {familyCode,familyCode,payAnnual};
					personList = dao.queryOjects(sql, params);
			 %>
			<%
				for (int i = 0; i < personList.size(); i++) {
					Person  model = (Person) personList.get(i);
			%>
			<tr>
				<td align="center"><input type="checkbox" id="checkbox" onchange="calAccount()" name="ids" value="<%=model.getHouseNum()%>" /><%=(i+1)%></td>
				<td><%=model.getHouseNum()%></td>
				<td><%=model.getRelationship()%></td>
				<td><%=model.getIdCard()%></td>
				<td><%=model.getName()%></td>
				<td><%="1".equals(model.getGender()) ? "男" : "女"%></td>
				<td><%=model.getAge()%></td>
				<td><%="1".equals(model.getHealth()) ? "健康" : "患病" %></td>
				<td><%=model.getTelephone()%></td>
			</tr>
			<%
				}
			%>
		</table>
			<!-- 当家庭成员已全部完成参合缴费时,进行提示 -->
		<%
			if(personList == null || "".equals(personList)) {
		 %>
			<h1 style="margin-left: 600px">家庭成员已全部完成参合缴费</h1>
		<%
			}
		 %>
		 </form>
	</div>
	<div align="center" style="margin-top: 300px">	
		<span style="font-size: 16px;">参合缴费总金额：<span id="amountSpan" style="font-size: 16px; color: red; margin-right: 50px"></span></span>
		<button id="add_btn" class="button bg-main icon-check-square-o" type="button" >确认缴费</button>
	</div>
</body>
</html>