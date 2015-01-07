<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
<title>导航菜单</title>
	
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="javascript" src="<%=path%>/script/jquery.js"></script>
<script language="javascript" src="<%=path%>/script/pageCommon.js" charset="utf-8"></script>
<script language="javascript" src="<%=path%>/script/PageUtils.js" charset="utf-8"></script>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/blue/pageCommon.css" />

	<script language="JavaScript" src="<%=path%>/script/menu.js"></script>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/blue/menu.css" />
</head>
<body style="margin: 0">
<div id="Menu">
    <ul id="MenuUl">
        <li class="level1">
            <div onClick="menuClick(this);" class="level1Style"><img src="<%=path%>/style/images/MenuIcon/22.gif" class="Icon" /> 审批流转</div>
            <ul style="display: none;" class="MenuLevel2">
                <li class="level2">
                    <div class="level2Style"><img src="<%=path%>/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="<%=path%>/processDefinitionAction_list">审批流程管理</a></div>
                </li>
                <li class="level2">
                    <div class="level2Style"><img src="<%=path%>/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="<%=path%>/templateAction_list">表单模板管理</a></div>
                </li>
                <li class="level2">
                    <div class="level2Style"><img src="<%=path%>/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right"
			href="<%=path%>/flowAction_templateList">起草申请</a></div>
                </li>
                <li class="level2">
                    <div class="level2Style"><img src="<%=path%>/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="<%=path%>/flowAction_myTaskList"> 待我审批</a></div>
                </li>
                <li class="level2">
                    <div class="level2Style"><img src="<%=path%>/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right"
			href="<%=path%>/flowAction_myApplicationList">我的申请查询</a></div>
                </li>
                <!--
		<li class="level2">
			<div class="level2Style"><img
                  src="<%=path%>/style/images/MenuIcon/menu_arrow_single.gif" /> <a  target="right" href="Flow_FormQuery/myApprovedList.html">经我审批</a> </div>
		</li>
		-->
            </ul>
        </li>
    </ul>
</div>
</body>
</html>
