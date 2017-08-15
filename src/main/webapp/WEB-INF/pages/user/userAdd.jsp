<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#userAddRoleIds').combotree({
            url: '${pageContext.request.contextPath}/userAction/getRoleTree',
            multiple: true,
            required: true,
            panelHeight : 'auto'
            
        });
        $('#userAddForm').form({
            url : '${path }/userAction/add',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#userAddForm');
                    parent.$.messager.alert('提示', eval(result.msg), 'warning');
                }
            }
        });
        
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
	<form method="post" id="userAddForm">
		<table class="tableForm">
			<tr>
				<td>登录名称</td>
				<td><input id="userName"  type ="text"  name="userName" class="easyui-validatebox" placeholder="请输入登录名称" validType="length[0,19]" invalidMessage="超过了19个字符" data-options="required:true,missingMessage:'请填写登录名称'" style="width:98%;" /></td>
			</tr>
			<tr>
				<td>昵称</td>
				<td><input id="nickName" type ="text" name="nickName"  class="easyui-validatebox" placeholder="请输入昵称" validType="length[0,37]" invalidMessage="超过了37个字符" data-options="required:true,missingMessage:'请填写昵称'" style="width:98%;" /></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input id="password" name="password"  type="password" class="easyui-validatebox"  placeholder="请输入密码" validType="length[4,15]" invalidMessage="4-15个字符" data-options="required:true,missingMessage:'请填写密码'" style="width:98%;" /></td>
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
                 <td><select id="userAddRoleIds" name="roleIds" style="width: 140px; height: 29px;"></select></td>
			</tr>
			
		</table>
	</form>
	
	</div>
</div>