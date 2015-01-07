<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
%>
<html>
<head>
    <title>我的申请查询</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript" src="<%=path %>/script/jquery.js"></script>
    <script language="javascript" src="<%=path %>/script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="<%=path %>/script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="<%=path %>/style/blue/pageCommon.css" />
    <script type="text/javascript">
    	function init(){
			document.getElementById("status").value = '${status}';
    	}
	</script>
</head>
<body onload="init();">

<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="<%=path %>/style/images/title_arrow.gif"/> 我的申请查询
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="QueryArea">
	<div style="height: 30px">
		<form action="flowAction_myApplicationList" method="post">
		<table border=0 cellspacing=3 cellpadding=5>
			<tr>
				<td><select id="status" name="status" class="SelectStyle">
						<option value="">查看全部状态</option>
						<option value="审批中">审批中</option>
						<option value="未通过">未通过</option>
						<option value="已通过">已通过</option>
					</select>
				</td>
				<td><a href=""><input type="IMAGE" src="<%=path %>/style/blue/images/button/query.PNG"/></a></td>
			</tr>
		</table>
		</form>
	</div>
</div>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
        <!-- 表头-->
        <thead>
            <tr align="CENTER" valign="MIDDLE" id="TableTitle">
				<td width="250px">标题</td>
				<td width="115px">申请人</td>
				<td width="115px">申请日期</td>
				<td width="115px">当前状态</td>
				<td>相关操作</td>
			</tr>
		</thead>	
				
		<!--显示数据列表：被退回的我的表的单显示示例
		<tbody id="TableData" class="dataContainer" datakey="formList">
			-->
			<!-- 被退回的我的表的单显示示例 
			<tr class="TableDetail1 template">
				<td><a href="<%=path %>/Flow_Formflow/showForm.html">${form.title}</a></td>
				<td>${form.applicant.name}&nbsp;</td>
				<td>${form.applyTime}&nbsp;</td>
				<td>审批中&nbsp;</td>
				<td><a href="<%=path %>/Flow_Formflow/showForm.html">查看申请信息</a>
					<a href="<%=path %>/Flow_Formflow/approvedHistory.html">查看流转记录</a>
					<a href="<%=path %>/Flow_Formflow/editAndResubmitUI.html">修改后再次提交</a>
					<a href="#" onClick="return delConfirm()">删除</a>
				</td>
			</tr>
		</tbody>
		-->

		<!--显示数据列表：正在审批或审批完成的表单显示示例-->
        <tbody id="TableData" class="dataContainer" datakey="formList">
			<!-- 正在审批或审批完成的表单显示示例 -->
			<s:iterator value="applicationList">
				<tr class="TableDetail1 template">
					<td><a href="<%=path %>/Flow_Formflow/showForm.html">${title}</a></td>
					<td>${applicant.name}&nbsp;</td>
					<td>
						<s:date name="applyDate" format="yyyy-MM-dd HH:mm:ss"/>
					&nbsp;</td>
					<td>
						<s:if test="status == '审批中'">
							<a href="javascript:window.showModalDialog('flowAction_getCurrentImg?applicationId=${id }')">
								${status}&nbsp;
							</a>
						</s:if>
						<s:else>
							${status}&nbsp;
						</s:else>
					</td>
					<td><a href="<%=path %>/flowAction_download?applicationId=${id}">查看申请信息</a>
						<a href="<%=path %>/flowAction_findApproveInfosByApplicationId?applicationId=${id}">查看流转记录</a>
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
