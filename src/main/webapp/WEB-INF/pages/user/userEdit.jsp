<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
$('#userEditRoleIds').combotree({
	 url: '${pageContext.request.contextPath}/user/getRoleTree',
    parentField : 'pid',
    lines : true,
    panelHeight : 'auto',
    multiple : true,
    required : true,
    cascadeCheck : false,
    value : ${roleIds}
});
</script>
<div style="padding: 5px;">
	<form method="post">
		<input name="id" type="hidden" />
		<table class="tableForm">
		<tr>
				<td>登录名称</td>
				<td><input id="userName"  type ="text"  name="userName" value="${ userName}" readonly="readonly" class="easyui-validatebox" placeholder="请输入登录名称" validType="length[0,19]" invalidMessage="超过了19个字符" data-options="required:true,missingMessage:'请填写登录名称'" style="width:98%;" /></td>
			</tr>
			<tr>
				<td>昵称</td>
				<td><input id="nickName" type ="text" name="nickName"  class="easyui-validatebox" placeholder="请输入昵称" validType="length[0,37]" invalidMessage="超过了37个字符" data-options="required:true,missingMessage:'请填写昵称'" style="width:98%;" /></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input id="password" name="password"  type="password" class="easyui-validatebox"  placeholder="请输入密码" style="width:98%;" /></td>
			</tr>
			<tr>
				<td>用户类型</td>
				<td>
                        <select name="type" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="0">系统管理员</option>
                            <option value="1" selected="selected">用户</option>
                        </select>
                    </td>
			</tr>
			<tr>
				<td>用户状态</td>
				<td>
                        <select name="status" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                                <option value="0">正常</option>
                                <option value="1">停用</option>
                        </select>
                    </td>
			</tr>
			<tr>
				<td>用户角色</td>
                 <td><select id="userEditRoleIds" name="roleIds" style="width: 140px; height: 29px;"></select></td>
			</tr>
		</table>
	</form>
</div>