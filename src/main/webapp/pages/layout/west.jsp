 <%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div region="west" split="true" title="导航菜单" style="width: 200px;">
	<div id="aa" class="easyui-accordion"
		style="position: absolute; top: 27px; left: 0px; right: 0px; bottom: 0px;">
		
		<div title="用户管理" iconcls="icon-search"
			style="overflow: auto; padding: 10px;">
			<ul >
			<li>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-add'"
						plain="true" href="javascript:void(0);"
						onclick="addTab('用户管理','/userAction/index','icon-add')">用户管理</a>
				</li>
			<li>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-add'"
						plain="true" href="javascript:void(0);"
						onclick="addTab('角色管理','/roleAction/index','icon-add')">角色管理</a>
				</li>
			<li>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-add'"
						plain="true" href="javascript:void(0);"
						onclick="addTab('权限管理','/permitAction/index','icon-add')">权限管理</a>
				</li>
			</ul>
		</div>
	</div>
</div>