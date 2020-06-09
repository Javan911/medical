<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<% 
  //读取隶属关系列表
  S201Dao s201Dao=new S201Dao();
  List<S201> s20102List=s201Dao.findListByType("02");
  List<S201> s20106List=s201Dao.findListByType("06");
  List<S201> s20104List=s201Dao.findListByType("04");
  List<S201> s20101List=s201Dao.findListByType("01");
  List<S201> s2010301List=s201Dao.findListByType("03");
  List<S201> s2010302List=s201Dao.findListByType("0301");
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
<script src="<%=path%>/third/jquery/jquery-1.9.1.js"></script>
<script src="<%=path%>/third/layer-v3.1.1/layer/layer.js"></script>
<script src="<%=path%>/third/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/admin.js"></script>
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
			<strong><span class="icon-pencil-square-o"></span>&nbsp;&nbsp;添加医疗机构</strong>
		</div>
		<div class="body-content">
			<form id="form-add" method="post" class="form-x"
				action="<%=path%>/system/MedicalServlet?m=add">
				<div class="form-group">
					<div class="label">
						<label>医疗机构编码：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="jgbm"
							data-validate="required:请输入医疗机构编码" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>组织机构编码：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="zzjgbm"
							data-validate="required:请输入组织机构编码" />
						<div class="tips"></div>
					</div>
				</div>
					<div class="form-group">
					<div class="label">
						<label>机构名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="jgmc"
							data-validate="required:请输入机构名称" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>地区编码：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="dqbm"
							data-validate="required:请输入地区编码" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>行政区域编码：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="areacode"
							data-validate="required:请输入行政区域编码" />
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>隶属关系：</label>
					</div>
					<div class="field">
						<select id="lsgx" name="lsgx" class="input w50">
							<% for(S201 m:s20102List){ %>
							<option value="<%=m.getItemcode()%>"><%=m.getItemname() %></option>
						    <% }%>
						</select>
						<div class="tips"></div>
					</div>
				</div>
							<div class="form-group">
					<div class="label">
						<label>机构级别：</label>
					</div>
					<div class="field">
						<select id="jgjb" name="jgjb" class="input w50">
							<% for(S201 m:s20106List){ %>
							<option value="<%=m.getItemcode()%>"><%=m.getItemname() %></option>
						    <% }%>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				
							<div class="form-group">
					<div class="label">
						<label>申请定点类型：</label>
					</div>
					<div class="field">
						<select id="lsgx" name="sbddlx" class="input w50">
							<% for(S201 m:s20104List){ %>
							<option value="<%=m.getItemcode()%>"><%=m.getItemname() %></option>
						    <% }%>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>批准定点类型：</label>
					</div>
					<div class="field">
						<select id="lsgx" name="pzddlx" class="input w50">
							<% for(S201 m:s20104List){ %>
							<option value="<%=m.getItemcode()%>"><%=m.getItemname() %></option>
						    <% }%>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>所属经济类型：</label>
					</div>
					<div class="field">
						<select id="lsgx" name="ssjjlx" class="input w50">
							<% for(S201 m:s20101List){ %>
							<option value="<%=m.getItemcode()%>"><%=m.getItemname() %></option>
						    <% }%>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>卫生机构大类：</label>
					</div>
					<div class="field">
						<select id="lsgx" name="wsjgdl" class="input w50">
							<% for(S201 m:s2010301List){ %>
							<option value="<%=m.getItemcode()%>"><%=m.getItemname() %></option>
						    <% }%>
						</select>
						<div class="tips"></div>
					</div>
				</div>
			
				<div class="form-group">
					<div class="label">
						<label>卫生机构小类：</label>
					</div>
					<div class="field">
						<select id="lsgx" name="wsjgxl" class="input w50">
							<% for(S201 m:s2010302List){ %>
							<option value="<%=m.getItemcode()%>"><%=m.getItemname() %></option>
						    <% }%>
						</select>
						<div class="tips"></div>
					</div>
				</div>
			
				<div class="form-group">
					<div class="label">
						<label>主管单位：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="zgdw"
							data-validate="required:请输入主管单位" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>开业时间：</label>
					</div>
					<div class="field">
						<input type="text" class="Wdate input w50" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',errDealMode:0})"
						 	name="kysj" style="height: 40px;" data-validate="required:请输入开业时间" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>法人代表：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="frdb"
							data-validate="required:请输入法人代表" />
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>注册资金：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" value="" name="zczj"
							data-validate="required:请输入注册资金" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label></label>
					</div>
					<div class="field">
						<button id="add_btn" class="button bg-main icon-check-square-o"
							type="button">确认</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>
