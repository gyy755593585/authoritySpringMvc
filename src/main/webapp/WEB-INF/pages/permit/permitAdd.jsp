<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
    $(function() {
        $('#resourceAddPid').combotree({
            url : '${pageContext.request.contextPath}/permitAction/getPermitTree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto'
        });

        
    });
</script>
<div style="padding: 1px;">
	<form method="post" id="permitAddForm">
		<table class="tableForm">
			<tr>
				<td>权限名称</td>
				<td><input id="permitName"  type ="text"  name="permitName" class="easyui-validatebox" placeholder="请输入权限名称"  data-options="required:true,missingMessage:'请输入权限名称'" style="width:98%;" /></td>
			</tr>
			<tr>
				<td>权限编码</td>
				<td><input id="permitCode"  type ="text"  name="permitCode" class="easyui-validatebox" placeholder="请输入URI"  data-options="required:true,missingMessage:'请输入URI'" style="width:98%;" /></td>
			</tr>
			
			<tr>
				<td>权限类型</td>
				<td>
                        <select id="resourceAddPid" name="pid"  style="width: 200px; height: 29px;">
                          <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#pid').combotree('clear');" >清空</a>
                        </select>
                    </td>
			</tr>
			
			
		</table>
	</form>
</div>