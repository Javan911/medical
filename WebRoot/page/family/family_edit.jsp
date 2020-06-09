<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp" %>

<%
	String familyCode = request.getParameter("familyCode");
	Family model = null;
	Person person = null;
	try {
		// 获取指定家庭编号的家庭档案信息
		model = new Family(familyCode);
		// 根据家庭编号和姓名获取参合人员信息
		String houseHolder = model.getHouseHolder();
		person = new Person(familyCode, houseHolder);
	}catch(Exception e) {
		e.printStackTrace();
		throw e;
	}
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
<script src="<%=path%>/third/jquery/jquery-1.9.1.js"></script>
<script src="<%=path%>/third/layer-v3.1.1/layer/layer.js"></script>
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
			chkStyle: "checkbox",
            chkboxType: { "Y": "p", "N": "ps" }
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
	$("#add_btn").bind("click",function(){
	var tree = $.fn.zTree.getZTreeObj("zTree");
    var nodes = tree.getCheckedNodes(true);
    var areacode = "";
    for (var i = 0; i < nodes.length; i++) {
        if(i == 0){
        	areacode = nodes[i].id;
        }else{
        	 areacode += ","+nodes[i].id; 
        	/*areacode = nodes[(nodes.length-1)].id*/
        }
    }
     $("#areacode").val(areacode);
     $('form').submit();
     closeLayer();
	});
	
});

// 打开修改户主信息页面
function openEditPerson(idCard) {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '修改户主信息填写',
		content: '<%=path%>/system/PersonServlet?m=get&idCard='+idCard,
		end: function() {
			window.location.reload();
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
  <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>&nbsp;&nbsp;更新家庭档案信息</strong></div>
  <div class="body-content">
    <form id="form-add" method="post" class="form-x" action="<%=path%>/system/FamilyServlet?m=edit">  
    
      <input id="areacode" name="areacode" value="" type="hidden"/>
      <input id="familyCode" name="familyCode" value="<%=model.getFamilyCode()%>" type="hidden"/>
      
      <div class="form-group">
        <div class="label">
          <label>户主姓名</label>
        </div>
        <div class="field">
          <input type="text" id="houseHolder" class="input w50" value="<%=model.getHouseHolder()%>" name="houseHolder" readonly="readonly" data-validate="required:请输入户主姓名" />&nbsp;&nbsp;&nbsp;&nbsp;
          <a class="button border-main" href="javascript:void(0)" 
          	onclick="openEditPerson('<%=person.getIdCard()%>')" > 
          	<span class="icon-edit"></span>修改户主信息
		  </a> 
          <div class="tips"></div>
        </div>
      </div>
      
      <div class="form-group">
        <div class="label">
          <label>农业人口数</label>
        </div>
        <div class="field">
          <input type="text" id="agriculturalNum" class="input w50" value="<%=model.getAgriculturalNum()%>" name="agriculturalNum" data-validate="required:请输入农业人口数" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>户属性</label>
        </div>
        <div class="field">
        <select name="householdType" class="input w50">
          	<option value="一般户" selected="selected">一般户</option>
          	<option value="五保户">五保户</option>
          	<option value="烈属户">烈属户</option>
          	<option value="其它">其它</option>
          </select>
          <div class="tips"></div>
        </div>
      </div>
      
      <div class="form-group">
        <div class="label">
          <label>户口性质</label>
        </div>
        <div class="field">
          <%
          	int isRural = model.getIsRural();
          	if(isRural == 1) {
           %>
          &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="isRural" value="1" checked="checked" >农业&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" name="isRural" value="0" >非农业
           <%
          		}else {
           %>
           		&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="isRural" value="1" >农业&nbsp;&nbsp;&nbsp;&nbsp;
           		 <input type="radio" name="isRural" value="0" checked="checked" >非农业
           <%
          		}
           %>
          <div class="tips"></div>
        </div>
      </div>
      
    <div class="form-group">
        <div class="label">
          <label>状态</label>
        </div>
        <div class="field">
          <%
          	int status = model.getStatus();
          	if(status == 1) {
           %>
          &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="status" value="1" checked="checked" >启用&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" name="status" value="0" >禁用
           <%
          		}else {
           %>
           		&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="status" value="1" >启用&nbsp;&nbsp;&nbsp;&nbsp;
           		 <input type="radio" name="status" value="0" checked="checked" >禁用
           <%
          		}
           %>
          <div class="tips"></div>
        </div>
      </div>
		
	 <div class="form-group">
        <div class="label">
          <label>家庭住址</label>
        </div>
        <div class="field">
          <input type="text" id="address" class="input w50" value="<%=model.getAddress()%>" name="address" readonly="readonly" data-validate="required:请输入家庭住址" />
          <div class="tips"></div>
        </div>
      </div>
		
		<div class="form-group">
			<div class="label">
				<label>备注</label>
			</div>
			<div class="field">
				<textarea type="text" class="input w50" name="remark"
					data-validate="required:备注不超过100个字" style="height: 100px" ><%=model.getRemark()%></textarea>
				<div class="tips"></div>
			</div>
		</div>
      
       <div class="form-group">
        <div class="label">
          <label>选择新的家庭住址:</label>
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
          <button id="add_btn" class="button bg-main icon-check-square-o" type="button" >确认</button>
        </div>
      </div>
    </form>
  </div>
</div>
  
  </body>
</html>
