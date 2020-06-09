<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">

<title>家庭档案列表</title>
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

// ajax请求删除家庭档案
function deleteFamily(familyCode) {
	if(confirm("您确定要删除吗？")){
		$.ajax({
			url:"<%=path%>/system/FamilyServlet?m=del",
			data:{familyCode:familyCode},
			success:function(data){
					if(eval(data)) {
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

// 打开添加家庭档案页面
function openAdd() {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '添加家庭档案信息填写',
		content: '<%=path%>/system/FamilyServlet?m=input',
		end: function() {
			window.location.reload();
		}
	});
}

// 打开添加家庭成员页面
function openAddPerson(familyCode) {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '添加家庭成员信息填写',
		content: '<%=path%>/system/PersonServlet?m=input&familyCode='+familyCode,
		end: function() {
			window.location.reload();
		}
	});
}

// 打开修改家庭档案页面
function openEdit(familyCode) {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '更新家庭档案信息填写',
		content: '<%=path%>/system/FamilyServlet?m=get&familyCode='+familyCode,
		end: function() {
			window.location.reload();
		}
	});
}

// 打开家庭档案详情页面
function openDetail(familyCode) {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '家庭成员信息详情',
		content: '<%=path%>/system/FamilyServlet?m=detail&familyCode='+familyCode,
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
				<strong class="icon-reorder">&nbsp;&nbsp;家庭档案信息列表</strong>
			</div>
			<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
					<li><a class="button border-main icon-plus-square-o" href="javascript:void(0)" onclick="openAdd()">&nbsp;&nbsp;添加家庭档案</a></li>
					<li>搜索：</li>
					<li><input type="text" placeholder="请输入搜索关键字" name="keywords" class="input" style="width:250px; line-height:17px;display:inline-block" /> 
					<a href="javascript:void(0)" class="button border-main icon-search" onclick="changesearch()">搜索</a></li>
					<div><span style="font-size: 18px;text-align: center; color: red">${deleteArea}</span></div>
				</ul>
			</div>
			<table class="table table-hover text-center">
				<tr>	
					<th width="50">序号</th>
					<!-- <th>县级编码</th>
					<th>乡镇编码</th>
					<th>村编码</th>
					<th>组编号</th> -->
					<th>家庭编号</th>
					<th>户主</th>
					<th>家庭人口数</th>
					<th>农业人口数</th>
					<th>家庭住址</th>
					<th>创建档案时间</th>
					<th>登记员</th>
					<th>户属性</th>
					<th>户口性质</th>
					<th>状态</th>
					<th>备注</th>
					<th width="360">操作</th>
				</tr>
				<%
					String pageNo = request.getParameter("pageNo");
					int p = pageNo == null ? 1 : Integer.parseInt(pageNo);
					FamilyDao dao = new FamilyDao();
					String sql = "select * from t_family_archives where 1=1 order by countyCode ";
					Object[] params = {};
					Page pageBean = dao.queryOjectsByPage(sql, params, p, Constant.ROW);
					
				%>
				<%
					for (int i = 0; i < pageBean.getDatas().size(); i++) {
						Family  model= (Family) pageBean.getDatas().get(i);
				%>
				<tr>
					<td align="center"><input type="checkbox" name="ids" value="<%=model.getFamilyCode()%>" /><%=(i+1)%></td>
					<%-- <td align="center"><%=model.getCountyCode()%></td>
					<td><%=model.getTownCode()%></td>
					<td><%=model.getVillageCode()%></td>
					<td><%=model.getGroupCode()%></td> --%>
					<td><%=model.getFamilyCode()%></td>
					<td><%=model.getHouseHolder()%></td>
					<td><%=dao.getFamilySizeById(model.getFamilyCode())%></td>
					<td><%=model.getAgriculturalNum()%></td>
					<td><%=model.getAddress()%></td>
					<td><%=model.getCreateTime()%></td>
					<td><%=model.getRegistrar()%></td>
					<td><%=model.getHouseholdType()%></td>
					<td><%=model.getIsRural() == 1 ? "农业" : "非农业" %></td>
					<td><%=model.getStatus() == 1 ? "启用" : "禁用" %></td>
					<td><%=model.getRemark()%></td>
					<td>
						<div class="button-group">
							<a class="button border-main icon-plus-square-o" href="javascript:void(0)" onclick="openAddPerson('<%=model.getFamilyCode()%>')" >
								<span></span>新增成员
							</a> 
							<a class="button border-main icon-search" href="javascript:void(0)" onclick="openDetail('<%=model.getFamilyCode()%>')" >
								详情
							</a> 
							<a class="button border-main" href="javascript:void(0)" onclick="openEdit('<%=model.getFamilyCode()%>')" >
								<span class="icon-edit"></span>修改
							</a> 
							<a class="button border-red" href="javascript:void(0)" onclick="deleteFamily('<%=model.getFamilyCode()%>')">
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
				<a href="<%=path%>/system/FamilyServlet?m=list&pageNo=<%=pageBean.getFirstPage()%>">首页</a>
				<a href="<%=path%>/system/FamilyServlet?m=list&pageNo=<%=pageBean.prePage()%>">上一页</a>
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
				<a href="<%=path%>/system/FamilyServlet?m=list&pageNo=<%=num%>"><%=num%></a>
				<%
					}
					}
				%>
				<%
					if (pageBean.hasNext()) {
				%>
				<a href="<%=path%>/system/FamilyServlet?m=list&pageNo=<%=pageBean.lastPage()%>">下一页</a>
				<a href="<%=path%>/system/FamilyServlet?m=list&pageNo=<%=pageBean.getPages()%>">尾页</a>
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
