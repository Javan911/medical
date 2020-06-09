<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp" %>

<%
	// 接收页面传过来的字符串并进行分割得到农合证号和身份证号
	String params = request.getParameter("nongheCard");
	String[] str = params.split(",");
	String nongheCard = str[0];
	String idCard = str[1];
 %>

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
<link rel="stylesheet" type="text/css" href="third/zTree_v3/css/zTreeStyle/zTreeStyle.css" >
<script type="text/javascript" src="third/zTree_v3/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="third/zTree_v3/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="js/admin.js"></script>
<script type="text/javascript">

 $(document).ready(function(){
	// 绑定表单提交
	$("#add_btn").bind("click",function(){
	
	// 判断慢性病名称是否为空
	var diseaseName = $("#diseaseName").val();
    if(diseaseName == null || diseaseName == "") {
     	alert("慢性病开始时间不能为空！");
     	return false;
    }
	// 判断慢性病开始时间是否为空
	var startTime = $("#startTime").val();
    if(startTime == null || startTime == "") {
     	alert("慢性病开始时间不能为空！");
     	return false;
    }
    // 判断慢性病结束时间是否为空
	var endTime = $("#endTime").val();
    if(endTime == null || endTime == "") {
     	alert("慢性病结束时间不能为空！");
     	return false;
    }
    // 判断医院证明附件是否为空
	var attachment = $("#attachment").val();
    if(attachment == null || attachment == "") {
     	alert("医院证明附件不能为空！");
     	return false;
    }
     $('form').submit();
     	closeLayer();
	});
});
// 关闭layer弹出层
function closeLayer() {
	var index = parent.layer.getFrameIndex(window.name); // 获取父窗口索引
 	parent.layer.close(index) // 关闭layer
	}

	// 校验行政区域名称是否已存在
function check(diseaseCode, nongheCard) {
	$.ajax({
		type:"post",
		url:"<%=path%>/system/DiseaseCardServlet?m=add",
		data:{diseaseCode:diseaseCode, nongheCard:nongheCard},
		success:function(data){
			var span = document.getElementById("nameSpan");
			if(data == "exist"){
				span.style.color = "red";
				span.innerHTML = "当前慢性病证已存在，请重新选择慢性病！";
			}else {
				span.style.color = "green";
				span.innerHTML = "当前慢性病证可以建档";
			}
		}
	});
}


</script>

</head>
  <body>
  <div class="panel admin-panel">
  <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>&nbsp;&nbsp;添加慢性病建档</strong></div>
  <div class="body-content">
	<form id="form-add" method="post" class="form-x" action="<%=path%>/system/DiseaseCardServlet?m=add" >  
	<input type="hidden" name="idCard" value="<%=idCard%>" />
	
	<div class="form-group">
        <div class="label">
          <label>农合证号</label>
        </div>
        <div class="field">
          <input type="text" id="nongheCard" class="input w50" name="nongheCard" value="<%=nongheCard%>" readonly="readonly" />
        </div>
      </div>
	
	<div class="form-group">
        <div class="label">
          <label>慢性病名称</label>
        </div>
        <div class="field">
        	<select name="diseaseCode" class="input w50" id="diseaseName" onblur="check('${disease.diseaseCode}, <%=nongheCard%>')">
	        	<c:forEach items="${diseaseList}" var="disease">
	        		<option value="${disease.diseaseCode}" onclick="check('${disease.diseaseCode}, <%=nongheCard%>')">${disease.diseaseName}</option>
	        	</c:forEach>
        	</select>
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="nameSpan"></span>
        </div>
      </div>
      
		
      <div class="form-group">
        <div class="label">
          <label>慢性病开始时间</label>
        </div>
        <div class="field">
          <input type="text" id="startTime" class="Wdate input w50" name="startTime" style="height: 50px;"
          		onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',errDealMode:0})" data-validate="required:请选择慢性病开始时间" />
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>慢性病结束时间</label>
        </div>
        <div class="field">
          <input type="text" id="endTime" class="Wdate input w50" name="endTime" style="height: 50px;"
          onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',errDealMode:0})" data-validate="required:请选择慢性病结束时间" />
          <div class="tips"></div>
        </div>
      </div>
      
      <div class="form-group">
        <div class="label">
          <label>医院证明附件</label>
        </div>
        <div class="field">
          <input type="file" id="attachment" class="input w50" name="attachment" data-validate="required:请上传医院证明附件"/>
        </div>
      </div>
      
      <div class="form-group">
        <div class="label">
          <label></label>
        </div>
        <div class="field">
          <button id="add_btn" class="button bg-main icon-check-square-o" type="button" >确认</button>
        </div>
      </div>
    </form>
  </div>
</div>
  </body>
</html>
