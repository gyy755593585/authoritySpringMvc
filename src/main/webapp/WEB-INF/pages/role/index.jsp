<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
</DOCTYPE html>
<html>
	<head>
		<%@ include file="/commons/basejs.jsp" %>
		<script type="text/javascript" charset="utf-8">
var datagrid;
$(function() {
	datagrid = $('#datagrid')
			.datagrid(
					{
						url : 'getRoleList',
						iconCls : 'icon-save',
                        pagination : true,
			            pagePosition : 'bottom',
			            pageSize : 10,
			            pageList : [ 10, 20, 30, 40 ],
						fit : true,
						fitColumns : false,
						rownumbers : true,
						singleSelect : false,
						border : false,
						idField : 'id',
						frozenColumns : [ [ {
							title : '编号',
							field : 'id',
							width : 50,
							sortable : true,
							checkbox : true
						},{
							title : '角色名',
							field : 'roleName',
							width : 100,
							sortable : true
						}, {
							title : '角色类型',
							field : 'type',
							width : 150,
							sortable : true,
							 formatter : function(value, row, index) {
				                    if(value == 1) {
				                        return "系统管理员";
				                    }else if(value == 0) {
				                        return "用户";
				                    }
				                    return "未知类型";
				                }
						}] ],
						toolbar : [ {
							text : '增加',
							iconCls : 'icon-add',
							handler : function() {
								append();
							}
						}, '-', {
							text : '删除',
							iconCls : 'icon-remove',
							handler : function() {
								remove();
							}
						}, '-', {
							text : '修改',
							iconCls : 'icon-edit',
							handler : function() {
								edit();
							}
						}, '-' ],
						columns : [ [ {
							title : '操作',
							field : 'll',
							width : 100,
							formatter : function(value, rowData, rowIndex) {
								return '<span class="icon-search" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span><a href="javascript:void(0);" onclick="changeStatus(' + rowIndex + ');">改变状态</a>';
							}
						},{
							title : '授权',
							field : 'l2',
							width : 100,
							formatter : function(value, rowData, rowIndex) {
								  var str = '';
								  str+='<span class="icon-search" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span><a href="javascript:void(0);" onclick="grantRoleFun(' + rowData.id + ');">授权</a>';
								 
								return str;
							}
						} ] ],
						onRowContextMenu : function(e, rowIndex, rowData) {
							e.preventDefault();
							$(this).datagrid('unselectAll');
							$(this).datagrid('selectRow', rowIndex);
							$('#menu').menu('show', {
								left : e.pageX,
								top : e.pageY
							});
						} 
					});
});
function edit() {
	var rows = datagrid.datagrid('getSelections');
	if (rows.length == 1) {
		var p = parent.dj
				.dialog( {
					title : '修改角色',
					href : '${pageContext.request.contextPath}/role/roleEdit?id=' + rows[0].id,
					width : 600,
					height : 450,
					buttons : [ {
						text : '修改',
						handler : function() {
							var f = p.find('form');
							f
									.form(
											'submit',
											{
												url : '${pageContext.request.contextPath}/role/edit',
												success : function(d) {
													var json = $.parseJSON(d);
													if (json.success) {
														datagrid
																.datagrid('reload');
														p.dialog('close');
													}
													parent.dj.messagerShow( {
														msg : json.msg,
														title : '提示'
													});
												}
											});
						}
					} ],
					onLoad : function() {
						var f = p.find('form');
						f.form('load', {
							id : rows[0].id,
							roleName : rows[0].roleName,
							type : rows[0].type
							
						});

					}
				});
	} else if (rows.length > 1) {
		parent.dj.messagerAlert('提示', '同一时间只能编辑一条记录！', 'error');
	} else {
		parent.dj.messagerAlert('提示', '请选择要编辑的记录！', 'error');
	}
}
function append() {
	var p = parent.dj
			.dialog( {
				title : '新增角色',
				href : '${pageContext.request.contextPath}/role/roleAdd',
				width : 600,
				height : 450,
				buttons : [ {
					text : '新增',
					handler : function() {
						var f = p.find('form');
						f.form( {
									url : '${pageContext.request.contextPath}/role/add',
									success : function(d) {
										var json = $.parseJSON(d);
										if (json.success) {
											datagrid.datagrid('reload');
											p.dialog('close');
										}
										parent.dj.messagerShow( {
											msg : json.msg,
											title : '提示'
										});
									}
								});
						f.submit();
					}
				} ]
			});
}
function remove() {
	var rows = datagrid.datagrid('getChecked');
	var ids = [];
	if (rows.length > 0) {
		parent.dj
				.messagerConfirm(
						'请确认',
						'您要删除当前所选项目？',
						function(r) {
							if (r) {
								for ( var i = 0; i < rows.length; i++) {
									ids.push(rows[i].id);
								}
								$
										.ajax( {
											url : '${pageContext.request.contextPath}/role/delete',
											data : {
												ids : ids.join(',')
											},
											dataType : 'json',
											success : function(d) {
												datagrid.datagrid('load');
												datagrid
														.datagrid('unselectAll');
												parent.dj.messagerShow( {
													title : '提示',
													msg : d.msg
												});
											}
										});
							}
						});
	} else {
		parent.dj.messagerAlert('提示', '请勾选要删除的记录！', 'error');
	}
}

function changeStatus(rowIndex) {
	var rows = datagrid.datagrid('getRows');
	var row = rows[rowIndex];
	var isAdmin =row.isAdmin;
	parent.dj
	.messagerConfirm(
			'请确认',
			isAdmin==0?'您要更改角色为管理员？':'您要更改角色为系统用户？',
			function(r) {
				if (r) {
					id=row.id;
					$.ajax( {
								url : '${pageContext.request.contextPath}/role/changeStatus',
								data : {
									id : id,
									isAdmin:isAdmin
								},
								dataType : 'json',
								success : function(d) {
									datagrid.datagrid('load');
									datagrid
											.datagrid('unselectAll');
									parent.dj.messagerShow( {
										title : '提示',
										msg : d.msg
									});
								}
							});
				}
			});
}
function grantRoleFun(id) {
    if (id == undefined) {
        var rows = datagrid.datagrid('getSelections');
        id = rows[0].id;
    } else {
    	datagrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    
    parent.$.modalDialog({
        title : '授权',
        width : 500,
        height : 500,
        href : '${path }/role/grantPage?id=' + id,
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = datagrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#roleGrantForm');
                f.submit();
            }
        } ]
    });
}

</script>
	</head>
	<body class="easyui-layout">

		<div data-options="region:'center',border:false"
			style="overflow: hidden;">
			<table id="datagrid"></table>
		</div>

		<div id="menu" class="easyui-menu"
			style="width: 120px; display: none;">
			<div onclick="append();" data-options="iconCls:'icon-add'">
				增加
			</div>
			<div onclick="remove();" data-options="iconCls:'icon-remove'">
				删除
			</div>
			<div onclick="edit();" data-options="iconCls:'icon-edit'">
				编辑
			</div>
		</div>
	</body>
</html>