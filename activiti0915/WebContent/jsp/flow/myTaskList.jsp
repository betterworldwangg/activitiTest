<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
%>
<html>
<head>
    <title>待我审批</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript" src="<%=path %>/script/jquery.js"></script>
    <script language="javascript" src="<%=path %>/script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="<%=path %>/script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="<%=path %>/style/blue/pageCommon.css" />
    <script type="text/javascript">
    </script>
</head>
<body> 

<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="<%=path %>/style/images/title_arrow.gif"/> 待我审批
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--
<div id="QueryArea">
	<div style="height: 30px">
		<form action="#">
		<table border=0 cellspacing=3 cellpadding=5>
			<tr>
				<td>按条件查询：</td>
				<td><select name="arg1" class="SelectStyle">
						<option value="">查看全部模板</option>
						<option value="0">领款单</option>
						<option value="10">工作报告</option>
						<option value="13">设备采购单</option>
						<option value="21">员工请假单</option>
						<option value="22">加班申请</option>
					</select>
				</td>
				<td><a href=""><input type="IMAGE" src="<%=path %>/style/blue/images/button/query.PNG"/></a></td>
			</tr>
		</table>
	
		</form>
	</div>
</div>
-->
<form>
	<input type="hidden" name="pageNum" value="1" />
</form>

<div id="MainArea">
    <table border="0" cellspacing="0" cellpadding="0" class="TableStyle">
        <!-- 表头-->
        <thead>
            <tr align="CENTER" valign="MIDDLE" id="TableTitle">
				<td width="350">标题</td>
				<td width="115">申请人</td>
				<td width="350">申请日期</td>
				<td>相关操作</td>
			</tr>
		</thead>		
		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="formList">
        	<s:iterator value="taskViewList">
				<tr class="TableDetail1 template">
					<td><a href="approveUI.html">${application.title}</a></td>
					<td>${application.applicant.name}&nbsp;</td>
					<td>
						<s:date name="application.applyDate" format="yyyy-MM-dd HH:mm:ss"/>
					&nbsp;</td>
					<td><a href="flowAction_approveUI?applicationId=${application.id }&taskId=${task.id}">审批处理</a>
						<!-- <a href="showForm.html">查看申请信息</a> -->
						<a href="<%=path %>/flowAction_findApproveInfosByApplicationId?applicationId=${application.id}">查看流转记录</a>
					</td>
				</tr>
        	</s:iterator>
        </tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    <div id="TableTail"><div id="TableTail_inside"></div></div>
</div>

</body>
</html>
