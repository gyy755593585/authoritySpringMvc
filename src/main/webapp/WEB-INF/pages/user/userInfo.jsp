<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div style="padding: 5px;">
	<form method="post">
		<table class="tableForm">
		<tr>
				<td>登录名称</td>
				<td><input id="userName"  type ="text"  name="userName" value="${user.userName}" readonly="readonly" class="easyui-validatebox" placeholder="请输入登录名称" validType="length[0,19]" invalidMessage="超过了19个字符" data-options="required:true,missingMessage:'请填写登录名称'" style="width:98%;" /></td>
				<td><input type="hidden" name="id" value=${id}  /></td>
			</tr>
			<tr>
				<td>昵称</td>
				<td><input id="nickName" type ="text" name="nickName" value="${user.nickName}" class="easyui-validatebox" placeholder="请输入昵称" validType="length[0,37]" invalidMessage="超过了37个字符" data-options="required:true,missingMessage:'请填写昵称'" style="width:98%;" /></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input id="password" name="password"  type="password" class="easyui-validatebox"  placeholder="请输入密码" style="width:98%;" /></td>
			</tr>
			
		</table>
	</form>
</div>