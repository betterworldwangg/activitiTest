<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
%>
<html>
<head>
    <title>申请模板选择</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript" src="<%=path %>/script/jquery.js"></script>
    <script language="javascript" src="<%=path %>/script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="<%=path %>/script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="<%=path %>/style/blue/pageCommon.css" />
	<link type="text/css" rel="stylesheet" href="<%=path %>/style/blue/select.css" />
    <script type="text/javascript">
    </script>
</head>
<body>

<div id="Title_bar" style="margin-bottom: 20px;">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="<%=path %>/style/images/title_arrow.gif"/> 申请模板选择
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<table width="85%" cellspacing="0" cellpadding="1" border="0" class="TableBorder" style="margin-left: 15px;">
	<tbody>
		<tr>
			<td>
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="HeadRight">
							<table height="27" cellspacing="0" cellpadding="0" border="0">
								<tr>
									<td class="HeadLeft">请选择申请模板</td>
									<td width="35" class="HeadMiddle"></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td> 
		</tr>
		<tr>
			<td class="Detail dataContainer" datakey="formTemplateList">
				<!-- 显示表单模板列表 -->
				<s:iterator value="templateList">
					<div id="DetailBlock" class="template"> 
						<img width="16" height="16" src="<%=path %>/style/images/FileType/doc.gif"/> 
						<a href="flowAction_submitUI?templateId=${id }">${name}</a> 
					</div>
				</s:iterator>
			</td>
		</tr>
	</tbody>
</table>

<div class="Description">
	说明：此页面是列出所有的表单模板记录<br />
</div>

</body>
</html>
