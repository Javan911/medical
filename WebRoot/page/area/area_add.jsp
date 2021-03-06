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
		 view: {
            dblClickExpand: true,//双击节点时，是否自动展开父节点的标识
            showLine: true,//是否显示节点之间的连线
            selectedMulti: false //设置是否允许同时选中多个节点
        },
		data: {
			simpleData: {
				enable: true,
			    idKey: "id",
			    pIdKey: "pid",
			    rootPId: 0,
			}
		},
		// 异步加载行政区域树
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
	// 判断区域名称是否为空
	var areaname = $("#areaname").val();
    if(areaname == null || areaname == "") {
     	alert("行政区域名不能为空！");
     	return false;
    }
	var tree = $.fn.zTree.getZTreeObj("zTree");
    var nodes = tree.getCheckedNodes(true);
    var areacode = "";
    // 判断所选行政区域是否符合要求
    if(nodes.length == 0){
      alert("没有选择任何的所属地区!");
      return false;
    }
     if(nodes.length > 1){
      alert("只能选择一个地区!");
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


// 校验行政区域名称是否已存在
function checkName() {
	$.ajax({
		type:"post",
		url:"<%=path%>/system/AreaServlet?m=check",
		data:{areaname:$("#areaname").val()},
		success:function(data){
			var span=document.getElementById("nameSpan");
			if(data == "trueName"){
				span.style.color = "red";
				span.innerHTML = "当前行政区域名称已存在，请重新输入！";
			}else if(data == "falseName"){
				span.style.color = "green";
				span.innerHTML = "当前行政区域名称可以使用";
			}
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
  <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>&nbsp;&nbsp;添加行政区域</strong></div>
  <div class="body-content">
    <form id="form-add" method="post" class="form-x" action="<%=path%>/system/AreaServlet?m=add">  
    
      <input id="areacode" name="areacode" value="" type="hidden"/>
      
      <div class="form-group">
        <div class="label">
          <label>行政区域名称：</label>
        </div>
        <div class="field">
          <input type="text" id="areaname" class="input w50" value="" name="areaname" onchange="checkName()" data-validate="required:请输入行政区域名称" />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="nameSpan"></span>
          <div class="tips"></div>
        </div>
      </div>
      
      <div class="form-group">
        <div class="label">
          <label>选择所属地区：</label>
        </div>
        <div class="field">
          <ul id="zTree"  class="ztree" style="border: 1px solid #ddd;"></ul>
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
