<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
	
</script>
<div style="padding: 5px;">
	<form method="post">
		<input name="id" type="hidden" />
		<table class="tableForm">
		<tr>
				<td>权限名称</td>
				<td><input id="permitName"  type ="text"  name="permitName" value="${permitName }" class="easyui-validatebox" placeholder="请输入权限名称"  data-options="required:true,missingMessage:'请输入权限名称'" style="width:98%;" /></td>
				<td><input type="hidden" name="Id" value=${id}  /></td>
			</tr>
			<tr>
				<td>权限编码</td>
				<td><input id="permitCode"  type ="text"  name="permitCode" value="${permitCode }" class="easyui-validatebox" placeholder="请输入权限编码" data-options="required:true,missingMessage:'请输入权限编码'" style="width:98%;" /></td>
			</tr>
			
			
			
		</table>
	</form>
</div>