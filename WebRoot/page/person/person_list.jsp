<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">

<title>参合人员档案列表</title>
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

// ajax请求删除参合人员档案
function deletePerson(idCard) {
	if(confirm("您确定要删除吗？")){
		$.ajax({
			url:"<%=path%>/system/PersonServlet?m=del",
			data:{idCard:idCard},
			success:function(data){
				if(data == "pfalse"){
					alert("当前参合人员为户主，不能直接删除！");
				}else if(eval(data)) {
					alert("删除成功！");
					window.location.reload();
				} else {
					alert("删除出现异常！");
					window.location.reload();
				}
			}
		});
	}
}

// 打开添加参合人员档案页面
function openAdd() {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '添加参合人员信息填写',
		content: '<%=path%>/system/PersonServlet?m=input',
		end: function() {
			window.location.reload();
		}
	});
}

// 打开更新参合人员档案页面
function openEdit(idCard) {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '更新参合人员信息填写',
		content: '<%=path%>/system/PersonServlet?m=get&idCard='+idCard,
		end: function() {
			window.location.reload();
		}
	});
}

</script>
</head>
<body>
	<form method="post" action="" id="listform">
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder">&nbsp;&nbsp;参合人员档案信息列表</strong>
			</div>
			<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
					<!-- <li><a class="button border-main icon-plus-square-o" href="javascript:void(0)" onclick="openAdd()">&nbsp;&nbsp;添加参合人员档案</a></li> -->
					<li>搜索：</li>
					<li><input type="text" placeholder="请输入搜索关键字" name="keywords" class="input" style="width:250px; line-height:17px;display:inline-block" /> 
					<a href="javascript:void(0)" class="button border-main icon-search" onclick="changesearch()">搜索</a></li>
					<div><span style="font-size: 18px;text-align: center; color: red">${deleteArea}</span></div>
				</ul>
			</div>
			<table class="table table-hover text-center">
				<tr>	
					<th width="50">序号</th>
					<th>家庭编号</th>
					<th>个人编号</th>
					<!-- <th>农合卡号</th>
					<th>医疗证卡号</th> -->
					<th>家庭户主</th>
					<th>与户主关系</th>
					<th>身份证号</th>
					<th>姓名</th>
					<th>性别</th>
					<th>年龄</th>
					<th>出生日期</th>
					<th>民族</th>
					<th>健康状况</th>
					<th>文化程度</th>
					<th>人员属性</th>
					<th>是否农村户口</th>
					<th>职业</th>
					<th>工作单位</th>
					<th>联系电话</th>
					<th>常住地址</th>
					<th width="180">操作</th>
				</tr>
				<%
					String pageNo = request.getParameter("pageNo");
					int p = pageNo == null ? 1 : Integer.parseInt(pageNo);
					PersonDao dao = new PersonDao();
					String sql = "select * from t_person_archives where 1=1 order by familyCode ";
					Object[] params = {};
					Page pageBean = dao.queryOjectsByPage(sql, params, p, Constant.ROW);
				%>
				<%
					for (int i = 0; i < pageBean.getDatas().size(); i++) {
						Person  model= (Person) pageBean.getDatas().get(i);
						Family family = new Family(model.getFamilyCode());
				%>
				<tr>
					<td align="center"><input type="checkbox" name="ids" value="<%=model.getFamilyCode()%>" /><%=(i+1)%></td>
					<td align="center"><%=model.getFamilyCode()%></td>
					<td><%=model.getHouseNum()%></td>
				<%-- 	<td><%=model.getNongheCard()%></td>
					<td><%=model.getMedicalCard()%></td> --%>
					<td><%=family.getHouseHolder()%></td>
					<td><%=model.getRelationship()%></td>
					<td><%=model.getIdCard()%></td>
					<td><%=model.getName()%></td>
					<td><%="1".equals(model.getGender()) ? "男" : "女"%></td>
					<td><%=model.getAge()%></td>
					<td><%=model.getBirthDate()%></td>
					<td><%=model.getNation()%></td>
					<td><%="1".equals(model.getHealth()) ? "健康" : "患病" %></td>
					<td><%=model.getEducation()%></td>
					<td><%=model.getWorkType()%></td>
					<td><%=family.getIsRural() == 1 ? "是" : "否" %></td>
					<td><%=model.getCareer()%></td>
					<td><%=model.getWorkUnit()%></td>
					<td><%=model.getTelephone()%></td>
					<td><%=model.getLiveAddress()%></td>
					<td>
						<div class="button-group">
							<a class="button border-main" href="javascript:void(0)" onclick="openEdit('<%=model.getIdCard()%>')" >
								<span class="icon-edit"></span>修改
							</a> 
							<a class="button border-red" href="javascript:void(0)" onclick="deletePerson('<%=model.getIdCard()%>')">
								<span class="icon-trash-o"></span>删除
							</a>
						</div>
					</td>
				</tr>
				<%
					}
				%>
			</table>
			
			<div class="pagelist">
				<%
					if (pageBean.hasPre()) {
				%>
				<a href="<%=path%>/system/AreaServlet?m=list&pageNo=<%=pageBean.getFirstPage()%>">首页</a>
				<a href="<%=path%>/system/AreaServlet?m=list&pageNo=<%=pageBean.prePage()%>">上一页</a>
				<%
					} else {
				%>
				<a href="javascript:void(0)">首页</a>
				<a href="javascript:void(0)">上一页</a>
				<%
					}
				%>
				<%
					List<Integer> linkNums = pageBean.linkNumbers();
					for (int num : linkNums) {
					if (pageBean.isCurrent(num)) {
				%>
				<span class="current"><%=num%></span>
				<%
					} else {
				%>
				<a href="<%=path%>/system/AreaServlet?m=list&pageNo=<%=num%>"><%=num%></a>
				<%
					}
					}
				%>
				<%
					if (pageBean.hasNext()) {
				%>
				<a href="<%=path%>/system/AreaServlet?m=list&pageNo=<%=pageBean.lastPage()%>">下一页</a>
				<a href="<%=path%>/system/AreaServlet?m=list&pageNo=<%=pageBean.getPages()%>">尾页</a>
				<%
					} else {
				%>
				<a href="javascript:void(0)">下一页</a>
				<a href="javascript:void(0)">尾页</a>
				<%
					}
				%>
			</div>
		</div>
	</form>

</body>
</html>
