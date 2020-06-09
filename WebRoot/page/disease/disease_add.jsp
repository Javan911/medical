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

<script type="text/javascript" src="third/zTree_v3/js/jquery-1.4.4.min.js"></script>
<!-- 引入zTree插件 -->
<link rel="stylesheet" type="text/css" href="third/zTree_v3/css/zTreeStyle/zTreeStyle.css" >
<script type="text/javascript" src="third/zTree_v3/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="third/zTree_v3/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="js/admin.js"></script>
<script type="text/javascript">

 $(document).ready(function(){
	// 绑定表单提交
	$("#addDisease_btn").bind("click",function(){
	// 判断慢性病编号是否为空
	var diseaseCode = $("#diseaseCode").val();
    if(diseaseCode == null || diseaseCode == "") {
     	alert("慢性病编号不能为空！");
     	return false;
    }
    // 判断拼音码是否为空
	var pinyinCode = $("#pinyinCode").val();
    if(pinyinCode == null || pinyinCode == "") {
     	alert("拼音码不能为空！");
     	return false;
    }
    // 判断慢性病名称是否为空
	var diseaseName = $("#diseaseName").val();
    if(diseaseName == null || diseaseName == "") {
     	alert("慢性病名称不能为空！");
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

// 校验慢性病编码是否已存在
function checkCode() {
	$.ajax({
		type:"post",
		url:"<%=path%>/system/DiseaseServlet?m=check",
		data:{diseaseCode:$("#diseaseCode").val()},
		success:function(data){
			var span=document.getElementById("codeSpan");
			if(data == "trueCode"){
				span.style.color = "red";
				span.innerHTML = "当前慢性病编码已存在，请重新输入！";
			}else if(data == "falseCode"){
				span.style.color = "green";
				span.innerHTML = "当前慢性病编码可以使用";
			}
		}
	});
}

// 校验慢性病名称是否已存在
function checkName() {
	$.ajax({
		type:"post",
		url:"<%=path%>/system/DiseaseServlet?m=check",
		data:{diseaseName:$("#diseaseName").val()},
		success:function(data){
			var span=document.getElementById("nameSpan");
			if(data == "trueName"){
				span.style.color = "red";
				span.innerHTML = "当前慢性病名称已存在，请重新输入！";
			}else if(data == "falseName"){
				span.style.color = "green";
				span.innerHTML = "当前慢性病名称可以使用";
			}
		}
	});
}


</script>

</head>
  <body>
  <div class="panel admin-panel">
  <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>&nbsp;&nbsp;添加慢性病</strong></div>
  <div class="body-content">
    <form id="form-add" method="post" class="form-x" action="<%=path%>/system/DiseaseServlet?m=add">  
    
      <div class="form-group">
        <div class="label">
          <label>慢性病编码：</label>
        </div>
        <div class="field">
          <input type="text" id="diseaseCode" class="input w50" value="" name="diseaseCode" onchange="checkCode()" data-validate="required:请输入慢性病编码" />
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="codeSpan" ></span>
            <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>名称拼音码：</label>
        </div>
        <div class="field">
          <input type="text" id="pinyinCode" class="input w50" value="" name="pinyinCode" data-validate="required:请输入拼音码" />
          <div class="tips"></div>
        </div>
      </div>
      
      <div class="form-group">
        <div class="label">
          <label>慢性病名称：</label>
        </div>
        <div class="field">
          <input type="text" id="diseaseName" class="input w50" value="" name="diseaseName" onchange="checkName()" data-validate="required:请输入慢性病名称" />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="nameSpan"></span>
           <div class="tips"></div>
        </div>
      </div>
      
      <div class="form-group">
        <div class="label">
          <label></label>
        </div>
        <div class="field">
          <button id="addDisease_btn" class="button bg-main icon-check-square-o" type="button" >确认</button>
        </div>
      </div>
    </form>
  </div>
</div>
  
  </body>
</html>
