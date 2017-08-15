<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>无极权限</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',href:'${pageContext.request.contextPath}/pages/layout/north.jsp'" style="height: 30px;overflow: hidden;" ></div>
	<div data-options="region:'west',title:'功能导航',href:'${pageContext.request.contextPath}/pages/layout/west.jsp'" style="width: 200px;overflow: hidden;"></div>
<!--  <div data-options="region:'east',title:'日历',split:true,href:'${pageContext.request.contextPath}/general/layout/east.jsp'" style="width: 200px;overflow: hidden;"></div>-->
	<div data-options="region:'center',title:'欢迎使权限管理平台',href:'${pageContext.request.contextPath}/pages/layout/center.jsp'" style="overflow: hidden;"></div>
	<div data-options="region:'south',href:'${pageContext.request.contextPath}/pages/layout/south.jsp'" style="height: 20px;overflow: hidden;"></div>

	
</body>
</html> 