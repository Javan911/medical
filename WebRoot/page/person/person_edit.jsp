<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp" %>

<%
	String idCard = request.getParameter("idCard");
	Person model = null;
	try {
		model = new Person(idCard);
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
<script src="<%=path%>/third/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="third/zTree_v3/js/jquery-1.4.4.min.js"></script>
<!-- 引入zTree插件 -->
<link rel="stylesheet" type="text/css" href="third/zTree_v3/css/zTreeStyle/zTreeStyle.css" >
<script type="text/javascript" src="third/zTree_v3/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="third/zTree_v3/js/jquery.ztree.excheck.js"></script>
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
  <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>&nbsp;&nbsp;更新参合人员档案信息</strong></div>
  <div class="body-content">
    <form id="form-add" method="post" class="form-x" action="<%=path%>/system/PersonServlet?m=edit">  
    
    	<input type="hidden" name="familyCode" value="<%=model.getFamilyCode() %>" />
    	<input type="hidden" name="oldName" value="<%=model.getName()%>" />
    	
       <div class="form-group">
        <div class="label">
          <label>与户主关系</label>
        </div>
        <div class="field">
          <input type="text" id="relationship" class="input w50" value="<%=model.getRelationship() %>" name="relationship" data-validate="required:请输入与户主关系" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>身份证号</label>
        </div>
        <div class="field">
          <input type="text" id="idCard" class="input w50" value="<%=model.getIdCard()%>" name="idCard" readonly="readonly" data-validate="required:请输入身份证号" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>姓名</label>
        </div>
        <div class="field">
          <input type="text" id="name" class="input w50" value="<%=model.getName()%>" name="name" data-validate="required:请输入姓名" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>性别</label>
        </div>
        <div class="field">
          <%
          	String gender = model.getGender();
          	if("1".equals(gender)) {
          		
           %>
          &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="gender" value="1" checked="checked" >男&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" name="gender" value="0" >女
          
           <%
          		}else {
           %>
           		&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="gender" value="1" >男&nbsp;&nbsp;&nbsp;&nbsp;
           		 <input type="radio" name="gender" value="0" checked="checked" >女
           <%
          		}
           %>
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>出生日期</label>
        </div>
        <div class="field">
          <input type="text" id="birthDate" class="Wdate input w50" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',errDealMode:0})"
				value="<%=model.getBirthDate()%>" name="birthDate" style="height: 40px;" data-validate="required:请选择出生日期" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>民族</label>
        </div>
        <div class="field">
          <input type="text" id="nation" class="input w50" value="<%=model.getNation()%>" name="nation" data-validate="required:请输入民族" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>健康状况</label>
        </div>
        <div class="field">
          <%
          	String health = model.getHealth();
          	if("1".equals(health)) {
          		
           %>
          &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="health" value="1" checked="checked" >健康&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" name="health" value="0" >患病
           <%
          		}else {
           %>
           		&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="health" value="1" >健康&nbsp;&nbsp;&nbsp;&nbsp;
           		 <input type="radio" name="health" value="0" checked="checked" >患病
           <%
          		}
           %>
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>文化程度</label>
        </div>
        <div class="field">
          <input type="text" id="education" class="input w50" value="<%=model.getEducation()%>" name="education" data-validate="required:请输入文化程度" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>人员属性</label>
        </div>
        <div class="field">
          <input type="text" id="workType" class="input w50" value="<%=model.getWorkType()%>" name="workType" data-validate="required:请输入人员属性" />
          <div class="tips"></div>
        </div>
      </div>
      
      <%--  <div class="form-group">
        <div class="label">
          <label>是否农村户口</label>
        </div>
        <div class="field">
          <%
          	int isRuralHukou = Integer.parseInt(model.getIsRuralHukou());
          	if(isRuralHukou == 1) {
          		
           %>
          &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="isRuralHukou" value="1" checked="checked" >是&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" name="isRuralHukou" value="0" >否
          
           <%
          		}else {
           %>
           		&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="isRuralHukou" value="1" >是&nbsp;&nbsp;&nbsp;&nbsp;
           		 <input type="radio" name="isRuralHukou" value="0" checked="checked" >否
           <%
          		}
           %>
          <div class="tips"></div>
        </div>
      </div> --%>
      
       <div class="form-group">
        <div class="label">
          <label>职业</label>
        </div>
        <div class="field">
          <input type="text" id="career" class="input w50" value="<%=model.getCareer()%>" name="career" data-validate="required:请输入职业" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>工作单位</label>
        </div>
        <div class="field">
          <input type="text" id="workUnit" class="input w50" value="<%=model.getWorkUnit()%>" name="workUnit" data-validate="required:请输入工作单位" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>联系电话</label>
        </div>
        <div class="field">
          <input type="text" id="telephone" class="input w50" value="<%=model.getTelephone()%>" name="telephone" data-validate="required:请输入联系电话" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>常住地址</label>
        </div>
        <div class="field">
          <input type="text" id="liveAddress" class="input w50" value="<%=model.getLiveAddress()%>" name="liveAddress" data-validate="required:请输入常住地址" />
          <div class="tips"></div>
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
