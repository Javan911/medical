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
  // zTree配置参数
  var setting = {
		check: {
			enable: true,
			chkStyle: "radio",
			radioType: "all",
		},
		data: {
			simpleData: {
				enable: true,
			    idKey: "id",
			    pIdKey: "pid",
			    rootPId: 0,
			}
		},
		// 异步加载权限菜单树
		async: {
			 type: "post",
             enable: true,
			 url:"<%=path%>/system/AreaServlet?m=tree",
			 otherParam: ["treeID", "0"]
		}
 };
 
$(document).ready(function(){
	//初始化ztree
	$.fn.zTree.init($("#zTree"), setting);
	//重新绑定表单提交
	$("#addArea_btn").bind("click",function(){
	// 判断农合机构编号是否为空
	var institutionCode = $("#institutionCode").val();
    if(institutionCode == null || institutionCode == "") {
     	alert("农合机构编号不能为空！");
     	return false;
    }
    // 判断农合机构名称是否为空
	var institutionName = $("#institutionName").val();
    if(institutionName == null || institutionName == "") {
     	alert("农合机构名称不能为空！");
     	return false;
    }
	var tree = $.fn.zTree.getZTreeObj("zTree");
    var nodes = tree.getCheckedNodes(true);
    var areacode="";
    // 判断行政区域是否为空
    if(nodes.length == 0){
      alert("没有选择任何的所属行政区域!");
      return false;
    }
    for (var i = 0; i < nodes.length; i++) {
        if(i == 0){
        	areacode = nodes[i].id;
        }else{
        	/* areacode += ","+nodes[i].id; */
        	areacode = nodes[(nodes.length-1)].id
        }
    }
     $("#areacode").val(areacode);
     $('form').submit();
     closeLayer();
	});
});

// 关闭layer弹出层
function closeLayer() {
	var index = parent.layer.getFrameIndex(window.name); // 获取父窗口索引
 	parent.layer.close(index) // 关闭layer
}

// 校验农合机构名称是否已存在
function checkName() {
	$.ajax({
		type:"post",
		url:"<%=path%>/system/InstitutionServlet?m=check",
		data:{institutionName:$("#institutionName").val()},
		success:function(data){
			var span=document.getElementById("nameSpan");
			if(data == "trueName"){
				span.style.color = "red";
				span.innerHTML = "当前农合机构名称已存在，请重新输入！";
			}else if(data == "falseName"){
				span.style.color = "green";
				span.innerHTML = "当前农合机构名称可以使用";
			}
		}
	});
}

// 校验同一级别的农合机构是否已存在
<%-- function checkArea() {
	$.ajax({
		type:"post",
		url:"<%=path%>/system/InstitutionServlet?m=check",
		data:{areacode:$("#areacode").val()},
		success:function(data){
			if(data == "trueArea"){
				alert("当前行政区域下的农合机构已存在，请重新选择行政区域！")
			}else if(data == "falseArea"){
				alert("当前行政区域下的农合机构可以使用！")
			}else if(data == "failArea"){
				alert("当前选中的行政区域级别不能添加农合机构！需选择镇级以上行政区域。")
			}
		}
	});
}  --%>
</script>

</head>
  <body>
  <div class="panel admin-panel">
  <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>&nbsp;&nbsp;添加农合机构</strong></div>
  <div class="body-content">
    <form id="form-add" method="post" class="form-x" action="<%=path%>/system/InstitutionServlet?m=add">  
    
      <input id="areacode" name="areacode" value="" type="hidden"/>
      
      <div class="form-group">
        <div class="label">
          <label>农合机构编号：</label>
        </div>
        <div class="field">
          <input type="text" id="institutionCode" class="input w50" value="" name="institutionCode" data-validate="required:请输入农合机构编号" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>农合机构名称：</label>
        </div>
        <div class="field">
          <input type="text" id="institutionName" class="input w50" value="" name="institutionName" onchange="checkName()" data-validate="required:请输入农合机构名称" />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="nameSpan"></span>
          <div class="tips"></div>
        </div>
      </div>
      
      <div class="form-group">
        <div class="label">
          <label>所属行政区域：</label>
        </div>
        <div class="field">
          <ul id="zTree"  class="ztree" onclick="checkArea()" style="border: 1px solid #ddd;"></ul>
        </div>
      </div>
   
      <div class="form-group">
        <div class="label">
          <label></label>
        </div>
        <div class="field">
          <button id="addArea_btn" class="button bg-main icon-check-square-o" type="button" >确认</button>
        </div>
      </div>
    </form>
  </div>
</div>
  
  </body>
</html>
