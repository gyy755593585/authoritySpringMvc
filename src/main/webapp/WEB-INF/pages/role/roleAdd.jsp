<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="padding: 1px;">
	<form method="post" id="roleAddForm">
		<table class="tableForm">
			<tr>
				<td>角色名称</td>
				<td><input id="roleName"  type ="text"  name="roleName" class="easyui-validatebox" placeholder="请输入角色名称" validType="length[0,19]" invalidMessage="超过了19个字符" data-options="required:true,missingMessage:'请填写登录名称'" style="width:98%;" /></td>
			</tr>
			
			<tr>
				<td>角色类型</td>
				<td>
                        <select name="type" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="0" selected="selected">用户</option>
                            <option value="1">系统管理员</option>
                        </select>
                    </td>
			</tr>
			
		</table>
	</form>
</div>