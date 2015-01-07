<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
<title>ItcastOA</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<frameset rows="100,*" framespacing="0" border="0" frameborder="0">
    <frame src="<%=path%>/jsp/home/top.jsp" name="TopMenu"  scrolling="no" noresize />
    <frameset cols="180,*" id="resize">
        <frame noresize name="menu" src="<%=path%>/jsp/home/left.jsp" scrolling="yes" />
        <frame noresize name="right" src="<%=path%>/jsp/home/right.jsp" scrolling="yes" />
    </frameset>
</frameset>
<noframes>
<body>
</body>
</noframes>
</html>
