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
    // 判断所选行政区域是否符合要求
    if(nodes.length == 0){
      alert("没有选择任何的所属地区!");
      return false;
    }
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

// 关闭layer弹出层
function closeLayer() {
	var index = parent.layer.getFrameIndex(window.name); // 获取父窗口索引
 	parent.layer.close(index) // 关闭layer
}

</script>

</head>
  <body>
  <div class="panel admin-panel">
  <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>&nbsp;&nbsp;家庭档案信息</strong></div>
  <div class="body-content">
    <form id="form-add" method="post" class="form-x" action="<%=path%>/system/FamilyServlet?m=add">  
    
      <input id="areacode" name="areacode" value="" type="hidden"/>
      
      <div class="form-group">
        <div class="label">
          <label>农业人口数</label>
        </div>
        <div class="field">
          <input type="text" id="agriculturalNum" class="input w50" value="" name="agriculturalNum" data-validate="required:请输入农业人口数" />
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
      
      <!--  <div class="form-group">
        <div class="label">
          <label>状态</label>
        </div>
        <div class="field">
	        &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="status" value="1" checked="checked" >启用&nbsp;&nbsp;&nbsp;&nbsp;
	        <input type="radio" name="status" value="0" >禁用
          <div class="tips"></div>
        </div>
      </div> -->
      
      <div class="form-group">
        <div class="label">
          <label>户口性质</label>
        </div>
        <div class="field">
	        &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="isRural" value="1" checked="checked" >农业&nbsp;&nbsp;&nbsp;&nbsp;
	        <input type="radio" name="isRural" value="0" >非农业
          <div class="tips"></div>
        </div>
      </div>
		
		<div class="form-group">
			<div class="label">
				<label>备注</label>
			</div>
			<div class="field">
				<textarea type="text" class="input w50" name="remark"
					data-validate="required:备注不超过100个字" style="height: 100px" ></textarea>
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
      
      <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>&nbsp;&nbsp;家庭户主信息</strong></div>
      
      <!-- <div class="form-group">
        <div class="label">
          <label>农合卡号</label>
        </div>
        <div class="field">
          <input type="text" id="nongheCard" class="input w50" value="" name="nongheCard" data-validate="required:请输入农合卡号" />
          <div class="tips"></div>
        </div>
      </div> -->
      
      <div class="form-group">
        <div class="label">
          <label>医疗证卡号</label>
        </div>
        <div class="field">
          <input type="text" id="medicalCard" class="input w50" value="" name="medicalCard" data-validate="required:请输入医疗证卡号" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>身份证号</label>
        </div>
        <div class="field">
          <input type="text" id="idCard" class="input w50" value="" name="idCard" data-validate="required:请输入身份证号" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>姓名</label>
        </div>
        <div class="field">
          <input type="text" id="name" class="input w50" value="" name="name" data-validate="required:请输入姓名" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>性别</label>
        </div>
        <div class="field">
        &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="gender" value="1" checked="checked" >男&nbsp;&nbsp;&nbsp;&nbsp;
       <input type="radio" name="gender" value="0" >女
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>出生日期</label>
        </div>
        <div class="field">
          <input type="text" id="birthDate" class="Wdate input w50" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',errDealMode:0})"
				name="birthDate" style="height: 40px;" data-validate="required:请选择出生日期" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>民族</label>
        </div>
        <div class="field">
          <input type="text" id="nation" class="input w50" value="" name="nation" data-validate="required:请输入民族" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>健康状况</label>
        </div>
        <div class="field">
        &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="health" value="1" checked="checked" >健康&nbsp;&nbsp;&nbsp;&nbsp;
       <input type="radio" name="health" value="0" >患病
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>文化程度</label>
        </div>
        <div class="field">
          <select name="education" class="input w50">
          	<option value="无" selected="selected">无</option>
          	<option value="小学">小学</option>
          	<option value="初中">初中</option>
          	<option value="高中">高中</option>
          	<option value="专科">专科</option>
          	<option value="本科">本科</option>
          	<option value="研究生">研究生</option>
          	<option value="博士">博士</option>
          	<option value="其它">其它</option>
          </select>
          
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>人员属性</label>
        </div>
        <div class="field">
        <select name="workType" class="input w50">
          	<option value="务农" selected="selected">务农</option>
          	<option value="编制员工">编制员工</option>
          	<option value="技术人员">技术人员</option>
          	<option value="正式职工">正式职工</option>
          	<option value="合同工">合同工</option>
          	<option value="兼职人员">兼职人员</option>
          	<option value="临时工">临时工</option>
          	<option value="其它">其它</option>
          </select>
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>职业</label>
        </div>
        <div class="field">
          <input type="text" id="career" class="input w50" value="" name="career" data-validate="required:请输入职业" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>工作单位</label>
        </div>
        <div class="field">
          <input type="text" id="workUnit" class="input w50" value="" name="workUnit" data-validate="required:请输入工作单位" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>联系电话</label>
        </div>
        <div class="field">
          <input type="text" id="telephone" class="input w50" value="" name="telephone" data-validate="required:请输入联系电话" />
          <div class="tips"></div>
        </div>
      </div>
      
       <div class="form-group">
        <div class="label">
          <label>常住地址</label>
        </div>
        <div class="field">
          <input type="text" id="liveAddress" class="input w50" value="" name="liveAddress" data-validate="required:请输入常住地址" />
          <div class="tips"></div>
        </div>
      </div>
      
      <div class="form-group" align="center">
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
