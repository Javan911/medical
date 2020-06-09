<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">

<title>家庭成员档案列表</title>
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
</head>
<body>
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder">&nbsp;&nbsp;家庭成员档案列表</strong>
			</div>
			<table class="table table-hover text-center">
				<tr>	
					<th width="50">序号</th>
					<!-- <th>家庭编号</th> -->
					<th>个人编号</th>
					<th>农合卡号</th>
					<th>医疗证卡号</th>
					<!-- <th>家庭户主</th> -->
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
					<!-- <th>是否农村户口</th> -->
					<th>职业</th>
					<th>工作单位</th>
					<th>联系电话</th>
					<th>常住地址</th>
				</tr>
				<%
					String pageNo = request.getParameter("pageNo");
					String familyCode = request.getParameter("familyCode");
					int p = pageNo == null ? 1 : Integer.parseInt(pageNo);
					PersonDao dao = new PersonDao();
					String sql = "select * from t_person_archives where familyCode=? order by familyCode ";
					Object[] params = {familyCode};
					Page pageBean = dao.queryOjectsByPage(sql, params, p, Constant.ROW);
				%>
				<%
					for (int i = 0; i < pageBean.getDatas().size(); i++) {
						Person  model= (Person) pageBean.getDatas().get(i);
						Family family = new Family(model.getFamilyCode());
				%>
				<tr>
					<td align="center"><%=(i+1)%></td>
					<%-- <td align="center"><%=model.getFamilyCode()%></td> --%>
					<td><%=model.getHouseNum()%></td>
					<td><%=model.getNongheCard()%></td>
					<td><%=model.getMedicalCard()%></td>
					<%-- <td><%=family.getHouseHolder()%></td> --%>
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
					<%-- <td><%=family.getIsRural() == 1 ? "是" : "否" %></td> --%>
					<td><%=model.getCareer()%></td>
					<td><%=model.getWorkUnit()%></td>
					<td><%=model.getTelephone()%></td>
					<td><%=model.getLiveAddress()%></td>
				</tr>
				<%
					}
				%>
			</table>
		</div>
</body>
</html>
