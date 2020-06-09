<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">

<title>参合缴费记录列表</title>
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

// 异步条件查询
function search() {
	var houseHolder = $("#houseHolder").val();
	if(houseHolder != "" && houseHolder != null) {
		var data = houseHolder;
	}
	// 如果搜索的参数不为空则进行模糊查询
	if(data != "" && data != null) {
		var sql = "select * from t_family_archives where houseHolder like '%"+ data +"%' order by familyCode ";
		$.ajax({ 
			url:"<%=path%>/page/payRecord/payRecord_list.jsp",
			data:{sql:sql},
			success:function(data){
				$("#familyValues").remove();
			}
		});
	}else {
		$.ajax({ 
			url:"<%=path%>/page/payRecord/payRecord_list.jsp",
			success:function(data){
				window.location.reload();
			}
		});
	}
}

// 判断当前时间是否在缴费标准设置的时间段内
function checkTime(familyCode) {
	$.ajax({
		url:"<%=path%>/system/PayRecordServlet?m=check",
		success:function(data){
			if(eval(data)) {
				openAdd(familyCode);
			}else {
				alert("当前时间不在缴费规定的时间范围内，不能进行缴费！");
				return false;
			}
		}
	});
}

// 打开添加参合缴费页面
function openAdd(familyCode) {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '家庭成员缴费记录详情',
		content: '<%=path%>/system/PayRecordServlet?m=detail&familyCode='+familyCode,
		end: function() {
			window.location.reload();
		}
	});
}

// ajax请求删除家庭缴费信息
function deletePayRecord(familyCode) {
	if(confirm("一旦删除无法找回,您确定要删除吗？")){
		$.ajax({
			url:"<%=path%>/system/PayRecordServlet?m=del",
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

// 打开管理参合缴费标准页面
function openEdit() {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '当前年度缴费标准',
		content: '<%=path%>/system/PayStandardServlet?m=get',
		end: function() {
			window.location.reload();
		}
	});
}

// 打开参合缴费标准列表
function openList() {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '缴费标准历史记录详情',
		content: '<%=path%>/system/PayStandardServlet?m=list',
		end: function() {
			window.location.reload();
		}
	});
}

// 打开参合缴费统计页面
function openStatistics() {
	var index = layer.open({
		type: 2,
		area: ['90%', '90%'],
		fix: false,  // 不固定
		maxmin : true,
		title: '家庭参合缴费统计详情',
		content: '<%=path%>/system/PayRecordServlet?m=statistics',
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
				<strong class="icon-reorder">&nbsp;&nbsp;参合缴费记录列表</strong>
			</div>
			<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
					<li>搜索：</li>
					<li><input type="text" placeholder="请输入户主姓名" id="houseHolder" name="houseHolder" onchange="search()" value="" class="input" style="width:250px; line-height:17px;display:inline-block" /> 
					<a href="javascript:void(0)" class="button border-main icon-search" onclick="search()">搜索</a></li>
					
					<li style="margin-left: 20px;">参合缴费标准：</li>
					<li><a href="javascript:void(0)" class="button border-main" onclick="openEdit()">
					<span class="icon-edit"></span>管理</a></li>
					<li><a href="javascript:void(0)" class="button border-main" onclick="openList()">
					<span class="icon-list"></span>记录</a></li>
					
					<li style="margin-left: 20px;">参合缴费记录：</li>
					<li><a href="javascript:void(0)" class="button border-main" onclick="openStatistics()">
					<span class="icon-list"></span>统计</a></li>
				</ul>
			</div>
			<table class="table table-hover text-center">
				<tr>	
					<th width="50">序号</th>
					<th>家庭编号</th>
					<th>户主</th>
					<th>家庭人口数</th>
					<th>已缴费人数</th>
					<th>家庭住址</th>
					<th>户属性</th>
					<th>户口性质</th>
					<th>状态</th>
					<th>备注</th>
					<th>操作</th>
				</tr>
				<%
					String sql = request.getParameter("sql");
					if(sql == null || "".equals(sql)) {
						sql = "select * from t_family_archives where 1=1 order by familyCode ";
					}
					String pageNo = request.getParameter("pageNo");
					int p = pageNo == null ? 1 : Integer.parseInt(pageNo);
					FamilyDao dao = new FamilyDao();
					Object[] params = {};
					Page pageBean = dao.queryOjectsByPage(sql, params, p, Constant.ROW);
				%>
				<%
					PayRecordDao prd = new PayRecordDao();
					// 获取当前年度
					Calendar date = Calendar.getInstance();
					String payAnnual = String.valueOf(date.get(Calendar.YEAR));
					for (int i = 0; i < pageBean.getDatas().size(); i++) {
						Family  model = (Family) pageBean.getDatas().get(i);
						// 计算家庭已缴费人数
						int count = prd.getByFamily(model.getFamilyCode(), payAnnual);
				%>
				<tr id="familyValues">
					<td align="center"><input type="checkbox" name="ids" value="<%=model.getFamilyCode()%>" /><%=(i+1)%></td>
					<td><%=model.getFamilyCode()%></td>
					<td><%=model.getHouseHolder()%></td>
					<td><%=dao.getFamilySizeById(model.getFamilyCode())%></td>
					<td><%=count%></td>
					<td><%=model.getAddress()%></td>
					<td><%=model.getHouseholdType()%></td>
					<td><%=model.getIsRural() == 1 ? "农业" : "非农业" %></td>
					<td><%=model.getStatus() == 1 ? "启用" : "禁用" %></td>
					<td><%=model.getRemark()%></td>
					<td>
						<div class="button-group">
							<a class="button border-main icon-plus-square-o" href="javascript:void(0)" onclick="checkTime('<%=model.getFamilyCode()%>')" >
								参合缴费
							</a> 
							<%-- <a class="button border-red" href="javascript:void(0)" onclick="deletePayRecord('<%=model.getFamilyCode()%>')">
								<span class="icon-trash-o"></span>删除
							</a> --%>
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
