<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<%
	// 获取系统当前年度
	Calendar date = Calendar.getInstance();
	String annual = String.valueOf(date.get(Calendar.YEAR));
	PayStandard model = new PayStandard(annual);
 %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">
<title>参合缴费标准管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet" href="css/admin.css">
<script src="<%=path%>/third/My97DatePicker/WdatePicker.js"></script>
<script src="<%=path%>/third/jquery/jquery-1.9.1.js"></script>
<script src="<%=path%>/third/layer-v3.1.1/layer/layer.js"></script>
<script src="js/jquery-1.4.4.min.js"></script>
<script src="js/admin.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	//重新绑定表单提交
	$("#add_btn").bind("click", function() {
		$('form').submit();
		closeLayer();
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
		<strong><span class="icon-pencil-square-o"></span>&nbsp;&nbsp;当前年度缴费标准管理</strong>
	</div>
	<div class="body-content">
		<form id="form-add" method="post" class="form-x" action="<%=path%>/system/PayStandardServlet?m=edit">
		
			<div class="form-group">
				<div class="label">
					<label>年度：</label>
				</div>
				<div class="field">
					<input type="text" class="input w50" value="<%=annual%>" name="annual" readonly="readonly" data-validate="required:年度不能为空" />
					<div class="tips"></div>
				</div>
			</div>
			
			<div class="form-group">
				<div class="label">
					<label>金额：</label>
				</div>
				<div class="field">
					<input type="text" class="input w50" value="<%=model.getAccount() == 0 ? "" : model.getAccount()%>" name="account" placeholder="未设置（单位：元）"
						data-validate="required:金额不能为空" />
					<div class="tips"></div>
				</div>
			</div>
			
			<div class="form-group">
				<div class="label">
					<label>开始时间：</label>
				</div>
				<div class="field">
					<input  id="d4321" type="text" class="input w50" value="<%=model.getStartTime() == null ? "" : model.getStartTime()%>" name="startTime"
						placeholder="未设置" data-validate="required:开始时间不能为空"  
						onclick="WdatePicker({minDate:'%y-%M-{%d}',maxDate:'#F{$dp.$D(\'d4322\',{d:-1});}'})"/>
					<div class="tips"></div>
				</div>
			</div>
			
			<div class="form-group">
				<div class="label">
					<label>结束时间：</label>
				</div>
				<div class="field">
					<input id="d4322" type="text" class="input w50" value="<%=model.getEndTime() == null ? "" : model.getEndTime()%>" name="endTime"
						placeholder="未设置" data-validate="required:结束时间不能为空"
						onclick="WdatePicker({minDate:'#F{$dp.$D(\'d4321\',{d:2});}',maxDate:'%y-12-%ld'})"/>
					<div class="tips"></div>
				</div>
			</div>

			<div class="form-group" align="center">
	        	<div class="label">
	          		<label></label>
	        	</div>
	        	<div class="field" align="center">
	          		<button id="add_btn" class="button bg-main icon-check-square-o" type="button" >确认</button>
	        	</div>
	      	</div>
		</form>
	</div>
</div>
</body>
</html>
