<%--标签 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="edge" />
<link rel="shortcut icon" href="${staticPath }/static/style/images/favicon.ico" />
<%-- [jQuery] --%>
<script type="text/javascript" src="${staticPath }/static/js/easyui/jquery.min.js" charset="utf-8"></script>
<%-- [EasyUI] --%>
<link id="easyuiTheme" rel="stylesheet" type="text/css" href="${staticPath }/static/js/easyui/themes/<c:out value="${cookie.easyuiThemeName.value}" default="default"/>/easyui.css" type="text/css"></link>
<link id="easyuiTheme" rel="stylesheet" type="text/css" href="${staticPath }/static/js/easyui/themes/icon.css" />
<script type="text/javascript" src="${staticPath }/static/js/easyui/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${staticPath }/static/js/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${staticPath }/static/js/djUtil.js" charset="utf-8"></script>
<script type="text/javascript" src="${staticPath }/static/js/jquery.cookie.js" charset="utf-8"></script>
<!-- 自定义库 -->
<link rel="stylesheet" href="${staticPath }/static/style/djCss.css" type="text/css"></link>
<link rel="stylesheet" href="${staticPath }/static/style/login.css" type="text/css"></link>
<%-- [扩展JS] --%>
<script type="text/javascript" src="${staticPath }/static/js/extJs.js" charset="utf-8"></script> 
<script type="text/javascript">
    var basePath = "${staticPath }";
</script>