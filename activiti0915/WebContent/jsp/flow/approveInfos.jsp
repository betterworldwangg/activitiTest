<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
%>
<html>
<head>
    <title>流转记录</title>
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
            <img border="0" width="13" height="13" src="<%=path %>/style/images/title_arrow.gif"/> 审批流转记录
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">

    <table cellspacing="0" cellpadding="0" class="TableStyle">
		<!-- 表头-->
        <tr align="CENTER" valign="MIDDLE" id="TableTitle">
            <td width="115">审批人</td>
			<td width="115">审批时间</td>
			<td width="100">是否通过</td>
            <td>审批意见</td>
        </tr>
	    <tbody id="TableData" class="dataContainer" datakey="approveInfoList">
	        <!--显示数据列表-->
	        <s:iterator value="approveInfos">
				<tr class="TableDetail1 template">
					<td>${approver.name}&nbsp;</td>
					<td>
						<s:date name="approveDate" format="yyyy-MM-dd HH:mm:ss"/>
					&nbsp;</td>
					<td>
						<%--
						<s:if test="approval">
							通过
						</s:if>
						<s:else>不通过</s:else>
						 --%>
						 <s:property value="approval?'通过':'不通过'"/>
					&nbsp;</td>
					<td>${comment}&nbsp;</td>
				</tr>
	        </s:iterator>
        </tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    <div id="TableTail">
        <div id="TableTail_inside">
            <a href="javascript:history.go(-1);"><img src="<%=path %>/style/images/goBack.png"/></a>
        </div>
    </div>
</div>

<div class="Description">
	说明：<br />
	1，审批信息列表按审批时间升序排列。<br />
</div>

</body>
</html>
